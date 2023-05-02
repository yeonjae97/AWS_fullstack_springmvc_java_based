package co.sukbinggo.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import co.sukbinggo.domain.MemberVO;
import co.sukbinggo.domain.NoteVO;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class NoteMapperTests {
//	
//	@Autowired
//	private NoteMapper mapper;
//	@Autowired
//	private MemberMapper memberMapper;
//	
//	
//	@Test
//	public void testInsert2(){
//		List<MemberVO> members = memberMapper.getList();
//		int i = 1;
//		for(MemberVO vo : members){
//			for(MemberVO vo2 : members){
//				NoteVO noteVO = new NoteVO();
//				noteVO.setSender(vo.getId());
//				noteVO.setReceiver(vo2.getId());
//				noteVO.setMessage("mapper 테스트 발송 :: " + i++);
//				mapper.insert(noteVO);
//			}
//			
//		}
//	}
//	
//	@Test
//	public void testSender(){
//		mapper.sendList("id1");
//	}
//	
//	@Test
//	public void testReviewList(){
//		mapper.receiveList("id1");
//		
//	}
//	@Test
//	public void testReviewUncheckedList(){
//		mapper.receiveUncheckedList("id1");
//		
//	}
//	@Test
//	public void testReview(){
//		List<MemberVO> members = memberMapper.getList();
//		int i = 1;
//		for(MemberVO vo : members){
//			for(MemberVO vo2 : members){
//				NoteVO noteVO = new NoteVO();
//				noteVO.setSender(vo.getId());
//				noteVO.setReceiver(vo2.getId());
//				noteVO.setMessage("mapper 테스트 발송 :: " + i++);
//				mapper.insert(noteVO);
//			}
//			
//		}
//	}
//	
//	
//	@Test
//	public void insertTest(){
//		
//		NoteVO vo = new NoteVO();
//		
//		vo.setSender("id1");
//		vo.setReceiver("id3");
//		vo.setMessage("mapper 테스트 발송");
//		mapper.insert(vo);
//	}
//	@Test
//	public void testSelectOne(){
//		
//		NoteVO vo = mapper.selectOne(23L);
//		log.info(vo);
//	}
//	@Test
//	public void testUpdate(){
//		
//		mapper.update(24L);
//		
//		
//	}
//	@Test
//	public void testDelete(){
//		
//		mapper.delete(23L);
//	}
}
