package co.sukbinggo.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;

import co.sukbinggo.domain.BoardVO;
import co.sukbinggo.domain.SampleVO;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml", "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})

// 테스트 코드에 활용할려면 반드시 이 어노테이션이 존재해야한다.
@WebAppConfiguration

@Log4j
public class SampleControllerTests {
	
	// Mock MVC는 이 Context를 필요로 한다.
	@Autowired
	private WebApplicationContext context;
	
	// 가짜 => prototyping => MOCK UP
	private MockMvc mockMvc;
	
	
	// 나중에 테스트 코드를 초기화하기 전의 일
	@Before
	public void init(){
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	
	// 여기까지 했으면 테스트에 대한 준비는 끝난 것이다.
	// ================================================================================
	// ================================================================================
	// ================================================================================
	
	@Test
	public void testExist(){
//		org.springframework.web.context.support.GenericWebApplicationContext@167fdd33: startup date [Tue Mar 28 11:57:06 KST 2023]; root of context hierarchy
		log.info(context);
		
//		org.springframework.test.web.servlet.MockMvc@77b21474
		log.info(mockMvc);
	}
	
	@Test
	public void testSample() throws Exception{
		SampleVO sampleVO = new SampleVO(10, "길동", "홍");
		
		Gson gson = new Gson();
		String jsonStr= gson.toJson(sampleVO);
		
		RequestBuilder builder = MockMvcRequestBuilders.get("/sample/sample")
				.content(jsonStr)
				.contentType(MediaType.APPLICATION_JSON);
			
		log.info(mockMvc.perform(builder).andReturn());
		
		
	}
	
	@Test
	public void testGet() throws Exception{
		RequestBuilder builder = MockMvcRequestBuilders
				.get("/board/get")
				.param("bno", "1");
				
		ModelMap map = mockMvc.perform(builder).andReturn().getModelAndView().getModelMap();
		
		log.info(map.get("board"));
	}
	
	@Test
	public void testRegister() throws Exception{
		RequestBuilder builder = MockMvcRequestBuilders
				.post("/board/register")
				.param("title", "컨트롤러 테스트 제목")
				.param("content", "컨트롤러 테스트 내용")
				.param("writer", "컨트롤러 테스트 작가");
		
		String result = mockMvc.perform(builder).andReturn().getModelAndView().getViewName();
		log.info(builder);
		
		// redirect:/board/list
		log.info(result);
	}
	
	@Test
	public void testModify() throws Exception{
		RequestBuilder builder = MockMvcRequestBuilders
				.post("/board/modify")
				.param("bno", "13")
				.param("title", "컨트롤러 테스트 제목 수정")
				.param("content", "컨트롤러 테스트 내용 수정")
				.param("writer", "컨트롤러 테스트 작가 수정");
		
		String result = mockMvc.perform(builder).andReturn().getModelAndView().getViewName();
		log.info(builder);
		
		// redirect:/board/list
		log.info(result);
	}
	@Test
	public void testRemove() throws Exception{
		RequestBuilder builder = MockMvcRequestBuilders
				.post("/board/remove")
				.param("bno", "12");
				
		
		String result = mockMvc.perform(builder).andReturn().getModelAndView().getViewName();
		log.info(builder);
		
		// redirect:/board/list
		log.info(result);
	}
	
	
	
	
	
}
