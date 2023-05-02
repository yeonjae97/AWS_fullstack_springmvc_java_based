package co.sukbinggo.mapper;

import org.apache.ibatis.javassist.compiler.ast.Member;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import co.sukbinggo.config.RootConfig;
import co.sukbinggo.domain.MemberVO;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={RootConfig.class})
@Log4j
public class MemberMapperTests {
	
	@Autowired
	private MemberMapper mapper;
//	
//	@Test
//	public void testGetList(){
//		mapper.getList().forEach(log::info);
//		
//	}
//	
	@Test
	public void testLogin(){
		MemberVO vo = new MemberVO();
//		vo.setUserid("admin99");
//		vo.setUserpw("pw99");
		log.info(mapper.read("admin99"));
	}
}
