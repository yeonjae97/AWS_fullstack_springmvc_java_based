package co.sukbinggo.domain;

import org.springframework.web.util.UriComponentsBuilder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Criteria {
	private int pageNum = 1;
	private int amount = 10;
	private String type; 
	private String keyword;
	
	public int getOffset(){
		return (pageNum - 1) * amount;
	}
	
	public Criteria(int pageNum, int amount) {
		super();
		this.pageNum = pageNum;
		this.amount = amount;
	}

	
	public String getQueryString(){
		return UriComponentsBuilder.fromPath("")
//				.queryParam("pageNum", pageNum)
				.queryParam("amount", amount)
				.queryParam("type", type)
				.queryParam("keyword", keyword)
				.build().toUriString();
	}
	public String getFullQueryString(){
		return UriComponentsBuilder.fromPath("")
				.queryParam("pageNum", pageNum)
				.queryParam("amount", amount)
				.queryParam("type", type)
				.queryParam("keyword", keyword)
				.build().toUriString();
	}
	
	
	// 문자열들을 순회하기 위한 메서드
	public String[] getTypeArr(){
		// null체크가 우선적으로 들어가는 것을 확인!
		return type == null ? new String[]{} : type.split("");
	}
}
