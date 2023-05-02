package co.sukbinggo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageDto{
		
	// 하단에 출력 될 페이지 사이즈
	private int pageCount = 10;
	// 시작 페이지 숫자
	private int startPage;
	// 종료 페이지 숫자
	private int endPage;
	// 게시글 총 갯수
	private int total;
	// next, prev
	private boolean prev;
	private boolean next;
	
	private boolean doublePrev;
	private boolean doubleNext;
	
	// Criteria
	private Criteria cri;
	
	public PageDto(int total, Criteria cri) {
		this(10, total, cri);
	}
	public PageDto(int pageCount, int total, Criteria cri) {
		this.pageCount = pageCount;
		this.total = total;
		this.cri = cri;
		// cri.getAmount();
		// cri.getPageNum();
	
		// 연산으로만 페이징 처리하는법
		// 사용자 입장에서 고려했을 때 발생할 수 있는 
		endPage = (cri.getPageNum() + (pageCount - 1)) / pageCount * pageCount;
		startPage = endPage - (pageCount - 1);
		int realEnd = (total + (cri.getAmount() - 1)) / cri.getAmount();
		if(endPage > realEnd) {
			endPage = realEnd;
		}
		prev = cri.getPageNum() > 1;
		next = cri.getPageNum() < realEnd;
		
		doublePrev = startPage > 1;
		doubleNext = endPage < realEnd;
		
		
		// 페이지의 올림 처리
//		endPage = (total + (cri.getAmount() - 1)) / cri.getAmount();
	}
	// 예정 <<, >>
	
}
