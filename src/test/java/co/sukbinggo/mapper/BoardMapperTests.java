package co.sukbinggo.mapper;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import co.sukbinggo.domain.BoardVO;
import co.sukbinggo.domain.Criteria;
import co.sukbinggo.domain.PageDto;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardMapperTests {
	@Autowired
	private BoardMapper boardMapper;
	
	@Test
	public void testGetList(){
		boardMapper.getList().forEach(log::info);
	}
	@Test
	public void testGetListWithPaging(){
		boardMapper.getListWithPaging(new Criteria()).forEach(log::info);
	}
	
	@Test
	public void testGetTotalCnt(){
		boardMapper.getTotalCnt(new Criteria(3, 10, "T","1"));
	}
	
	
	@Test
	public void testInsert(){
		BoardVO vo = new BoardVO();
		vo.setTitle("테스트 코드 작성 insertSelectKey() 제목");
		vo.setContent("테스트 코드 작성 insertSelectKey() 내용");
		vo.setWriter("작성자");
		boardMapper.insertSelectKey(vo);
		log.info(vo);
	
	}
	
//	@Test
//	public void testInsert(){
//		BoardVO vo = new BoardVO();
//		vo.setTitle("테스트 코드 작성 insertSelectKey() 제목");
//		vo.setContent("테스트 코드 작성 insertSelectKey() 내용");
//		vo.setWriter("작성자");
//		boardMapper.insertSelectKey(vo);
//		log.info(vo);
//	
//	}
	
	@Test
	public void testRead(){
//		Long bno = 163780L;
		Long bno = 163881L;
		log.info(boardMapper.read(bno));
		
	}
	@Test
	public void testDelete(){
		Long bno = 2L;
		log.info(boardMapper.read(bno));
		log.info(boardMapper.delete(bno));
		log.info(boardMapper.read(bno));
		
		
	}
	
	@Test
	public void testUpdate(){
		BoardVO vo =boardMapper.read(3L);
		vo.setTitle("수정된 제목");
		vo.setContent("수정된 내용");
		vo.setWriter("user00");
		
		log.info(vo);
		boardMapper.update(vo);
		
		BoardVO vo2 = boardMapper.read(vo.getBno());
		log.info(vo2);
		
		// assertEqual은 주소값이 다르므로 
//		assertSame(vo, vo2);
	}
	
	
	
	
}
