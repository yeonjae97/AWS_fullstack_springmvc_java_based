package co.sukbinggo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import co.sukbinggo.domain.NoteVO;
import co.sukbinggo.mapper.NoteMapper;
import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class NoteServiceImpl implements NoteService{

	private NoteMapper mapper;
	@Override
	public int send(NoteVO vo) {
		// TODO Auto-generated method stub
		return mapper.insert(vo);
	}

	@Override
	public NoteVO get(Long noteno) {
		// TODO Auto-generated method stub
		return mapper.selectOne(noteno);
	}

	@Override
	public int receive(Long noteno) {
		// TODO Auto-generated method stub
		return mapper.update(noteno);
	}

	@Override
	public int remove(Long noteno) {
		// TODO Auto-generated method stub
		return mapper.delete(noteno);
	}

	@Override
	public List<NoteVO> getSendList(String id) {
		// TODO Auto-generated method stub
		return mapper.sendList(id);
	}

	@Override
	public List<NoteVO> getReceiveList(String id) {
		// TODO Auto-generated method stub
		return mapper.receiveList(id);
	}

	@Override
	public List<NoteVO> getReceiveUncheckedList(String id) {
		// TODO Auto-generated method stub
		return mapper.receiveUncheckedList(id);
	}

}
