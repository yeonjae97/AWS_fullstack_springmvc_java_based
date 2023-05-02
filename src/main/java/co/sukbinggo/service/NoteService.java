package co.sukbinggo.service;

import java.util.List;

import co.sukbinggo.domain.NoteVO;
import co.sukbinggo.mapper.NoteMapper;

public interface NoteService {
	
	int send(NoteVO vo);
	
	NoteVO get(Long noteno);
	
	int receive(Long noteno);
	
	int remove(Long noteno);
	
	List<NoteVO> getSendList(String id);
	
	List<NoteVO> getReceiveList(String id);
	List<NoteVO> getReceiveUncheckedList(String id);
}
