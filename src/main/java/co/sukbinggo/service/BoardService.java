package co.sukbinggo.service;

import java.util.List;

import co.sukbinggo.domain.AttachFileDTO;
import co.sukbinggo.domain.BoardVO;
import co.sukbinggo.domain.Criteria;

public interface BoardService {
	void register(BoardVO vo);
	
	BoardVO get(Long bno);
	
	boolean modify(BoardVO vo);
	
	boolean remove(Long bno);
	
	// 파라미터가 없었는데 페이징을 도입한 뒤로 파라미터가 생김
	List<BoardVO> getList(Criteria cri);
	
	
	// 갯수 추가
	int getTotalCnt(Criteria cri);
	
	
	// dto는 반환타입에 맞춰서 파일 삭제
	String deleteFile(AttachFileDTO dto);
}
