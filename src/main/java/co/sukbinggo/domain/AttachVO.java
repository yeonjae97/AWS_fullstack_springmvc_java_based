package co.sukbinggo.domain;

import org.apache.ibatis.type.Alias;

import lombok.Data;
import lombok.ToString;

@Data
@Alias("attach")
// callSuper을 선언할 경우 구상 클래스의 toString까지 함께 불러온다.
@ToString(callSuper=true)
public class AttachVO extends AttachFileDTO{
	private Long bno;
	
	
	
	
	
}
