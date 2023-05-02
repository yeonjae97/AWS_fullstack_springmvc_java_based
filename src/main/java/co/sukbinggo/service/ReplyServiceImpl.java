package co.sukbinggo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.sukbinggo.domain.ReplyVO;
import co.sukbinggo.mapper.BoardMapper;
import co.sukbinggo.mapper.ReplyMapper;
import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class ReplyServiceImpl implements ReplyService{
	private BoardMapper boardMapper;
	private ReplyMapper replyMapper;
	
	@Override
	@Transactional
	public int register(ReplyVO vo) {
		boardMapper.updateReplyCnt(vo.getBno(), 1);
		return replyMapper.insert(vo);
	}

	@Override
	public ReplyVO get(Long rno) {
		return replyMapper.read(rno);
	}

	@Override
	@Transactional
	public int remove(Long rno) {
		boardMapper.updateReplyCnt(get(rno).getBno(), -1);
		return replyMapper.delete(rno);
	}

	@Override
	public int modify(ReplyVO vo) {
		return replyMapper.update(vo);
	}

	@Override
	public List<ReplyVO> getList(Long bno, Long rno) {
		return replyMapper.getList(bno, rno);
	}

	
}
