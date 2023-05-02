package co.sukbinggo.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import co.sukbinggo.domain.AttachVO;
import co.sukbinggo.domain.BoardVO;
import co.sukbinggo.domain.Criteria;
import co.sukbinggo.domain.PageDto;
import co.sukbinggo.domain.SampleVO;
import co.sukbinggo.mapper.AttachMapper;
import co.sukbinggo.service.BoardService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/board/*")
@AllArgsConstructor
@Data // 기본적으로 생길건 다 생김
public class BoardController {

	private BoardService boardService;
	
	@GetMapping("list")
	public void list(Model model, Criteria cri){
		log.info("list()");
		log.info(cri);
		model.addAttribute("list", boardService.getList(cri));
		
		// 갯수 메서드를 서비스에서 만들면 더 이상 고정된 값을 사용하지 않아도 된다. = > boardService.getTotalCnt(cri)
		model.addAttribute("page", new PageDto(boardService.getTotalCnt(cri), cri));
		
		
	}
	@GetMapping({"get", "modify"}) // 
	public void get(Model model, Long bno,@ModelAttribute("cri") Criteria cri){
		log.info("get() or modify()");
		log.info(bno);
		log.info(cri);
		model.addAttribute("cri", cri);
		model.addAttribute("board", boardService.get(bno));
	}
	
	// rest uri 방식
	@GetMapping("{bno}")
	public String getByPath(Model model, @PathVariable Long bno){
		log.info("get() or modify()");
		log.info(bno);
		model.addAttribute("board", boardService.get(bno));
		return "board/get";
	}
	
	
	@PostMapping("register")
	@PreAuthorize("isAuthenticated()")
	public String register(BoardVO vo, RedirectAttributes rttr, Criteria cri){
		log.info("register");
		log.info(vo);
		
		boardService.register(vo);
		rttr.addFlashAttribute("result",vo.getBno());
		rttr.addAttribute("pageNum", 1);
		rttr.addAttribute("amount", cri.getAmount());
		return "redirect:/board/list" + cri.getFullQueryString();
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("register")
	public void register(){}
	
	@PreAuthorize("isAuthenticated() and principal.username eq #vo.writer")
	@PostMapping("modify")
	public String modify(BoardVO vo,RedirectAttributes rttr, Criteria cri){
		log.info("modify");
		log.info(vo);
		log.info(cri);
		if(boardService.modify(vo)){
			rttr.addFlashAttribute("result","success");
		}
		return "redirect:/board/list" + cri.getFullQueryString();
	}
	
	@PreAuthorize("isAuthenticated() and principal.username eq #writer")
	@PostMapping("remove")
	public String remove(Long bno,RedirectAttributes rttr, Criteria cri){
		log.info("remove");
		log.info(bno);
		log.info(cri);
		
		// 글이 삭제되기 전에 해당 Attach들 list로 불러오기
		List<AttachVO> list = boardService.get(bno).getAttachs();
		if(boardService.remove(bno)){
			
			// 1. 람다식을 활용해 VO 삭제
//			list.forEach(boardService::deleteFile);
			
			// 2. 향상 for문을 활용해 VO 삭제
			for(AttachVO vo : list){
				boardService.deleteFile(vo);
				
			}
			rttr.addFlashAttribute("result","success");
		}
		return "redirect:/board/list" + cri.getFullQueryString();
	}
	
//	@GetMapping("sample")
//	@ResponseBody
//	public SampleVO getSample(){
//		return new SampleVO(10, "길동", "홍");
//	}
}
