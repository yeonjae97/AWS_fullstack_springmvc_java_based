package co.sukbinggo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.sukbinggo.domain.BoardVO;
import co.sukbinggo.domain.Criteria;
import co.sukbinggo.domain.PageDto;

public interface BoardMapper {
	
	// 목록 조회
	List<BoardVO> getList();
	List<BoardVO> getListWithPaging(Criteria criteria);
	
	
	
	int getTotalCnt(Criteria criteria);
	
	// 글 등록
	void insert(BoardVO vo);
	
	// 글 등록
	void insertSelectKey(BoardVO vo);
	
	// 조회
	BoardVO read(Long bno);
	
	// 삭제
	int delete(Long bno);
	
	// 수정
	int update(BoardVO vo);
	
	// 댓글 갯수 반영
	void updateReplyCnt(@Param("bno") Long bno,@Param("amount") int amount);
}
