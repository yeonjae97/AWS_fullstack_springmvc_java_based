package co.sukbinggo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import co.sukbinggo.domain.NoteVO;
import co.sukbinggo.service.NoteService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("note")
@ResponseBody
@AllArgsConstructor
public class NoteController {
	
	// 단순 js 뷰는 없음
	private NoteService service;
	
	@PostMapping("new")
	public int send(@RequestBody NoteVO vo){ // @RequestBody는 왜쓰지? => 요청 파라미터를 JSON으로 받기 위해서
//		log.info(service.send(vo));
		return service.send(vo);
	}
	@GetMapping("{noteno}")
	public NoteVO getNote(@PathVariable Long noteno){
		log.info(noteno);
		return service.get(noteno);
	}
	@PutMapping("{noteno}")
	public int receive(@PathVariable Long noteno){
		log.info(noteno);
		return service.receive(noteno);
	}
	@DeleteMapping("{noteno}")
	public int remove(@PathVariable Long noteno){
		log.info(noteno);
		return service.remove(noteno);
	}
	@GetMapping("s/{id}") 
	public List<NoteVO> getSendList(@PathVariable String id){
//		log.info(id);
		return service.getSendList(id);
	}
	@GetMapping("r/{id}")
	public List<NoteVO> getReceiveList(@PathVariable String id){
//		log.info(id);
		return service.getReceiveList(id);
	}
	@GetMapping("r/c/{id}")
	public List<NoteVO> getReceiveUncheckedList(@PathVariable String id){
//		log.info(id);
		return service.getReceiveUncheckedList(id);
	}
}
