package co.sukbinggo.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import co.sukbinggo.domain.AttachFileDTO;
import co.sukbinggo.service.BoardService;
import lombok.Getter;
import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import net.coobird.thumbnailator.tasks.UnsupportedFormatException;
import oracle.jdbc.proxy.annotation.Post;

@Controller
@Log4j
public class UploadController {
	
	@Getter
	private static final String PATH = "c:/upload";
	
	@Autowired
	private BoardService boardService;
	
	@GetMapping("upload")
	public void upload(){
		log.info("upload");
	}
	@PostMapping("upload")
	public void upload(MultipartFile[] files) throws IllegalStateException, IOException{ // jsp내의 name 명을 따라감
		log.info("upload post");
		
		for(MultipartFile m : files){
			log.info(m.getOriginalFilename());
			log.info(m.getSize());
			
			// 실제 스트림 전송
			m.transferTo(new File(PATH, m.getOriginalFilename()));
		}
	}
	
	@GetMapping("uploadAjax")
	public void uploadAjax(){
		log.info("uploadAjax");
	}
	@PostMapping(value = "uploadAjax", produces = MediaType.APPLICATION_JSON_VALUE) 
	@ResponseBody
	@PreAuthorize("isAuthenticated()")
	public List<AttachFileDTO> uploadAjax(MultipartFile[] files){ // jsp내의 name 명을 따라감
		log.info("uploadAjax post");
		
		List<AttachFileDTO> list = new ArrayList<>();
		
		File uploadPath = new File(PATH, getFolder());
		if(!uploadPath.exists()){
			uploadPath.mkdirs();
		}
		for(MultipartFile m : files){
			log.info(m.getOriginalFilename());
			log.info(m.getSize());
			
//			File f = null;
			// 실제 스트림 전송
			String uuidStr = UUID.randomUUID().toString();
			String tName = uuidStr + "_" + m.getOriginalFilename();
			File f = new File(uploadPath,tName);
			boolean image = false;
			try {
				image = isImage(f); 
				m.transferTo(f);
				if(image){
					// 썸네일 처리 => 안그러면 트래픽이 기하급수적으로 증가하므로 반드시 처리
					File f2 = new File(uploadPath,"s_" + tName);
					Thumbnails.of(f).crop(Positions.CENTER).size(200, 200).toFile(f2);
				}
			}catch(UnsupportedFormatException e){
				image = false;
			}catch (IOException e) {
				e.printStackTrace();
			}
			AttachFileDTO dto = new AttachFileDTO();
			dto.setUuid(uuidStr);
			dto.setPath(getFolder());
			dto.setImage(image);
			dto.setName(m.getOriginalFilename());
			list.add(dto);
		}
		return list;
	}
	
	
	// path + uuid + name 을 활용한 썸네일 여부
//	produces=MediaType.IMAGE_PNG_VALUE를 따로 추가하는 경우에는 올 타입이 뭔지 모르므로 문제가 될 여지가 생긴다.
	@GetMapping(value = "display") @ResponseBody
	public ResponseEntity<byte[]> getFile(AttachFileDTO dto, Boolean thumb){ // thumb에 대한 여부
		// fileName : path + uuid + name
		
//		log.info(dto);
		String s = PATH + "/" + dto.getPath() + "/" + (thumb != null && thumb ? "s_" : "") + dto.getUuid() + "_" + dto.getName();
		System.out.println(s);
		
		File file = new File(s);
		log.info("exist() : " + file.exists());
		log.info(thumb);
		
		
		
		ResponseEntity<byte[]> result = null;
		try {
			
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", Files.probeContentType(file.toPath()));
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	@GetMapping("deleteFile") 
	@ResponseBody
	@PreAuthorize("isAuthenticated()")
	public String deleteFile(AttachFileDTO dto){
		return boardService.deleteFile(dto);
	}
	
	
	
	@GetMapping("download") @ResponseBody
	public ResponseEntity<byte[]> download(AttachFileDTO dto){ // thumb에 대한 여부
		
		String s = PATH + "/" + dto.getPath() + "/" + dto.getUuid() + "_" + dto.getName();
		System.out.println(s);
		
		File file = new File(s);	
		System.out.println(file);
		ResponseEntity<byte[]> result = null;
		try {
			
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", MediaType.APPLICATION_OCTET_STREAM_VALUE);
			headers.add("Content-Disposition","attachment; filename=" +new String(dto.getName().getBytes("utf-8"), "iso-8859-1"));
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	
	@GetMapping("list") @ResponseBody
	public List<String> test() {
		List<String> list = new ArrayList<>();
		list.add("A");
		list.add("B");
		list.add("C");
		return list;
	}
	
	// @Responsebody => viewResolver로 따로 구현해야 했던 것을 이 어노테이션을 이용하여 응답에 대한 타입을 제어
	@GetMapping(value = "dto", produces=MediaType.APPLICATION_JSON_VALUE) @ResponseBody 
	public AttachFileDTO getDto(){
		AttachFileDTO dto = new AttachFileDTO();
		dto.setImage(true);
		dto.setName("파일명.png");
		return dto;
	}
	
	private String getFolder(){
		return new SimpleDateFormat("yyyy/MM/dd").format(new Date());
	}
	
	// file >> mime
	private boolean isImage(File file) throws IOException{
		List<String> excludes = Arrays.asList("ico", "webp");
		int idx = file.toString().lastIndexOf(".");
		if(idx == -1){
			return false;
		}
		String ext = file.toString().substring(idx + 1);
		if(excludes.contains(ext)){
			return false;
		}
		String mime = Files.probeContentType(file.toPath());
		
		// mime image/png image/jpeg image/x-icon
		// webp null
		return mime != null && mime.startsWith("image");
	}
	
	@PostMapping("/ckImage") @ResponseBody
	public Map<String, Object> uploadAjax(MultipartHttpServletRequest request) throws IllegalStateException, IOException{ // jsp내의 name 명을 따라감
		log.info(request);
		MultipartFile multipartFile = request.getFile("upload");
		
		String origin = multipartFile.getOriginalFilename();
		
		String ext = origin.substring(origin.indexOf("."));
		
		String uuidStr = UUID.randomUUID().toString();
		String tName = uuidStr + "_" + origin;
		File f = new File(PATH,tName);
		AttachFileDTO dto = new AttachFileDTO();
		dto.setUuid(uuidStr);
		dto.setPath("");
		dto.setName(origin);
		
		multipartFile.transferTo(f);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("uploaded", true);
		map.put("url", "/display" + dto.getUrl());
		log.info(map);
		
		return map;
	}
	
}
