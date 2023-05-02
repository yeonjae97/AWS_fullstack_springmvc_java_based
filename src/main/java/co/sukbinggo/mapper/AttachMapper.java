package co.sukbinggo.mapper;

import java.util.List;

import co.sukbinggo.domain.AttachVO;

public interface AttachMapper {
	
	// 추가
	void insert(AttachVO vo);
	
	// 삭제
	void delete(String uuid);
	
	// 목록 조회
	List<AttachVO> findBy(Long bno);
	
	void deleteAll(Long bno);
	
	List<AttachVO> getOldFiles();
}
