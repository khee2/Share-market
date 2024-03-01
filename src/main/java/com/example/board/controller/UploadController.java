package com.example.board.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.board.beans.vo.AttachFileVO;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnailator;

@Controller
@Slf4j
@RequestMapping("/upload/*")
public class UploadController {

	@GetMapping("uploadForm")
	public void uploadForm() {
		log.info("upload form");
	}
// consumes는 브라우저가 서버에게 보낼때 거르는 것이고, produces는 반대로 서버에서 브라우저에게 보낼 때 거르는 것이다.
	@PostMapping(value="uploadAjaxAction", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<AttachFileVO> uploadAjaxPost(MultipartFile[] uploadFile) {
		log.info("----------------");
		log.info("Upload File Name : ");
		List<AttachFileVO> fileList = new ArrayList<>(); //파일 리스트를 만들어서 돌려주는
		String uploadFolder = "C:/upload";
		String uploadFolderPath = getFolder();

		// yyyy/mm/dd 폴더 만들기
		File uploadPath = new File(uploadFolder, uploadFolderPath);
			if(!uploadPath.exists()) { // 이 경로가 존재하는지 물어봄. 없을 때만 만들면 됨.
				uploadPath.mkdirs();
			}
			log.info("upload path : " + uploadPath);
		for (MultipartFile mf : uploadFile) {
			log.info("Upload File Name : " + mf.getOriginalFilename());
			log.info("Upload File Size : " + mf.getSize());

			AttachFileVO attachFileVO = new AttachFileVO();
			String uploadFileName = mf.getOriginalFilename();
			// UUID는
			// 동일한 이름의 파일을 방지하기 위한 방법으로 
			// 네트워크 상에 개체들을 식별하기 위해 만들어짐.
			// 10의 38제곱 수 중 하나를 선택한다. (만억조경해자양구, 360간) => 360간 중 두 개를 뽑았는데 같았다 까지는....
			UUID uuid = UUID.randomUUID(); // UUID 하나 채벌? 체벌? 뽑기
			// 파일 이름 조회 시 _뒤 내용으로 조회한다.
			
			uploadFileName = uuid.toString() + "_" + uploadFileName; // UUID_원래파일이름 꼴
			
			// IE에서는 파일 이름을 포함한 전체 경로가 나옴. => 수정 필요
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);

			log.info("file name: " + uploadFileName); 
			attachFileVO.setFileName(uploadFileName); // attachFileVO에 업로드한 파일 이름이 이때 저장이 된다.
			// UUID를 쓴 file name 예시: e83fafd5-65e2-4f44-a411-cc2ca7dd626d_사랑과 헌법 강의계획서 (1).pdf
			try {
				File saveFile = new File(uploadPath, uploadFileName);
				mf.transferTo(saveFile);
				InputStream in = new FileInputStream(saveFile);
				
				attachFileVO.setUuid(uuid.toString()); // uuid setting
				attachFileVO.setUploadPath(uploadFolderPath); // uploadpath setting
				if(checkImageType(saveFile)) {
					attachFileVO.setImage(true); // image가 맞으므로 image 필드 변수에 true 저장.
					FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_" + uploadFileName));
					// inputstream을 통해서 파일을 생성하고, height, width를 지정한다. 
					// 이미지 파일이면 s_로 썸네일 이미지가 한 개 더 생성한다. 
					// mf.getInputStream() : 입력을 쓸 파일
					// thumbnail : output으로 나갈 파일
					// 20, 20: 너비, 높이인데 그림이 깨지지 않게 비율대로 줄임. 높이, 너비 중 큰게 60일때까지 줄임. 
					Thumbnailator.createThumbnail(mf.getInputStream(), thumbnail, 60, 60); // 썸네일 생성해줌.
					thumbnail.close();
				}
				in.close();
				fileList.add(attachFileVO); 
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			
		}
		return fileList;
	}
	@GetMapping("display")
	@ResponseBody
	public ResponseEntity<byte[]> getFile(String fileName){
		log.info("fileName: "+ fileName);
		File file = new File("C:/upload/" + fileName);
		log.info("file: " + file); // 새로 디렉터리 설정한 그 파일
		ResponseEntity<byte[]> result = null;
		
		HttpHeaders header = new HttpHeaders();
		try {
			// 헤더에 적절한 파일 타입을 probeContentType을 통해 포함시킨다.
			// png이면 image/png 타입, jpg 파일은 image/jpeg 타입으로 포함시킨다.
			header.add("Content-Type", Files.probeContentType(file.toPath()));
			// 응답데이터, 헤더, 상태코드 담는 생성자 존재
			result = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	private String getFolder() { // 날짜 타입의 디렉터리를 가져다 줌.
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // MM은 대문자로 써줘야 함.
		Date date = new Date();// 실제로 오늘 날짜 집어넣기 (import는 java.util 선택)
		String str = sdf.format(date); // 오늘 날짜가 yyyy-mm-dd 형식으로 나옴.
		
		return str.replace("-", "/"); // -를 /로 바꿔서 리턴 => C 업로드 밑에 붙이면 된다.
	}
	private boolean checkImageType(File file) { // 이미지냐 아니냐를 판단
		try {
			String contentType = Files.probeContentType(file.toPath());
			log.info("Content Type: "+ contentType);
			return contentType.startsWith("image"); // contentType이 image로 시작하는지
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return false; // return이 여기까지 갔으면 false 먹어줌.
	}
}
