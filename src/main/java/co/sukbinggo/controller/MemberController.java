package co.sukbinggo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.javassist.compiler.ast.Member;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import co.sukbinggo.domain.MemberVO;
import co.sukbinggo.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("member")
@Log4j
@AllArgsConstructor
public class MemberController {
	private MemberService memberService;
//	
//	
//	@GetMapping("chat")
//	public void chat(){}
//	
//	
	@GetMapping("login")
	public void login(){}
//	@PostMapping("login")
//	public void login(HttpServletRequest req){
//		
//	}
//	@PostMapping("login")
//	public String login(MemberVO vo, HttpSession session, RedirectAttributes rttr){ // 세션 유지 목적으로 인자를 하나 더 둠
//		log.info(vo);
//		MemberVO memberVO = memberService.get(vo);
//		log.info(session);
//		if(memberVO == null){
//			rttr.addFlashAttribute("msg", "로그인 실패");
//		}else{
//			
//			session.setAttribute("member", memberVO);
//			log.info("로그인 성공");
//		}
////		return "redirect:/member/login";
//		return "redirect:/member/login"; // chat.jsp 용
//	}
//	
//	@RequestMapping("logout")
//	public String logout(HttpSession session){
//		log.info("로그아웃 처리");
//		session.invalidate();
////		return "redirect:/member/login";
//		return "redirect:/member/login"; // chat.jsp
//		
//	}
//	
//	@GetMapping("getList")
//	@ResponseBody // 응답 시 
//	public List<MemberVO> getList(){
//		return memberService.getList();
//	}
}
