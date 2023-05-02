package co.sukbinggo.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sun.xml.internal.ws.policy.spi.AssertionCreationException;

import co.sukbinggo.domain.BoardVO;
import co.sukbinggo.domain.Criteria;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardServiceTests {
	
	// 빈 등록
	@Autowired
	private BoardService boardService;
	
	
	@Test
	public void textExists(){
		
		// assertNotNull로 먼저 서비스의 null 여부를 따진다.
		assertNotNull(boardService);
		log.info(boardService);
	}
	
	@Test
	public void testRegister(){
		BoardVO vo = new BoardVO();
		vo.setTitle("등록된 제목");
		vo.setContent("등록된 내용");
		vo.setWriter("서버");
		
		log.info(vo);
		boardService.register(vo);
	}
	@Test
	public void testGet(){
		BoardVO vo = boardService.get(4L);
		log.info(vo);
		
	}
	@Test
	public void testGetList(){
		
		// 두 방식 다 사용 가능
		boardService.getList(new Criteria()).forEach(log::info);
//		log.info(boardService.getList());
		
		
		
	}
	
	@Test
	public void testRemove(){
		Long bno = 5L;
		BoardVO vo = boardService.get(bno);
		log.info(vo);
		assertTrue(boardService.remove(bno));
		vo = boardService.get(bno);
		assertNull(vo);
		
	}
	
	@Test
	public void testUpdate(){
		Long bno = 13L;
		BoardVO vo = boardService.get(bno);
		log.info(vo);
		vo.setTitle("수정 제목");
		vo.setContent("수정된 내용");
		assertTrue(boardService.modify(vo));
		BoardVO vo2 = boardService.get(bno);
		log.info(vo2);
	}
	
	
	
}
