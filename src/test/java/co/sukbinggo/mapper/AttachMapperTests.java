package co.sukbinggo.mapper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import co.sukbinggo.domain.AttachVO;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class AttachMapperTests {

	@Autowired
	private AttachMapper mapper;
	
	
	
	// 현재 AttachVO의 리스트들
	@Test
	public void check(){
		log.warn("file check");
	}
	@Test
	public void checkFiles(){
		mapper.getOldFiles().forEach(log::info);
		
	}
	
	

}
