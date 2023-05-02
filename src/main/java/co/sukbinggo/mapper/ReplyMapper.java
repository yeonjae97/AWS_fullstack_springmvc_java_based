package co.sukbinggo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.sukbinggo.domain.ReplyVO;

public interface ReplyMapper {
	int insert(ReplyVO vo);
	
	ReplyVO read(Long rno);
	
	List<ReplyVO> getList(@Param("bno") Long bno,@Param("rno") Long rno);
	
	int update(ReplyVO vo);
	
	int delete(Long rno);
	
	int deleteByBno(Long bno);
}
