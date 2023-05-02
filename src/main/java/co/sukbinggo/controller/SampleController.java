package co.sukbinggo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.sukbinggo.domain.SampleVO;
import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping("sample")
@Log4j
public class SampleController {
	@GetMapping(value="getText", produces="text/plain; charset=utf-8")
	public String getText(){
		return "안녕하세요";
	}
	
	@GetMapping("getSample")
	public SampleVO getSample(){
		return new SampleVO(112, "스타", "로드");
	}
	
	@GetMapping("getList")
	public List<SampleVO> getList(){
		return IntStream.rangeClosed(1,  10).mapToObj(i -> new SampleVO(i, "first " + i, "last " + i)).collect(Collectors.toList());
	}
	
	@GetMapping("getMap")
	public Map<String, SampleVO> getMap(){
		Map<String, SampleVO> map = new HashMap<String, SampleVO>();
		map.put("First", new SampleVO(111, "그루트", "주니어"));
		return map;
	}
	
	@GetMapping(value="check", params={"height", "weight"})
	public ResponseEntity<SampleVO> check(double height, double weight){
		
		// 로그인도 비동기로 처리할 수 있겠지만 대부분은 동기화이다.
		// 비동기식으로 처리할거면 밑에 있는 코드를 제대로 처리해야한다.
		
		SampleVO vo = new SampleVO(0, String.valueOf(height), String.valueOf(weight));
		
		ResponseEntity<SampleVO> result = null;
		
		if(height < 150){
			result = ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(vo);
		}
		else{
//			result = ResponseEntity.status(HttpStatus.OK).body(vo);
			result = ResponseEntity.ok(vo);
		}
		
		return result;
		
	}
	
	@GetMapping("product/{cat}/{pid}")
	public String[] getPath(@PathVariable String cat,@PathVariable String pid){
		return new String[] {"category :" + cat, "product : " + pid};
	}
	@GetMapping("product/{cat2}/{pid2}/{test}")
	public String[] getPath2(@PathVariable String cat2,@PathVariable String pid2){
		return new String[] {"category :" + cat2, "product : " + pid2};
	}
	
	@GetMapping("sample")
	public SampleVO convert(@RequestBody SampleVO sampleVO){
		log.info(sampleVO);
		return sampleVO;
	}
	@PostMapping("sample")
	public SampleVO convert2(@RequestBody SampleVO sampleVO){
		log.info(sampleVO);
		return sampleVO;
	}
}
