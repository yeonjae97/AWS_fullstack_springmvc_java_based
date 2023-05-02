package co.sukbinggo.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.sukbinggo.controller.UploadController;
import co.sukbinggo.domain.AttachFileDTO;
import co.sukbinggo.domain.AttachVO;
import co.sukbinggo.domain.BoardVO;
import co.sukbinggo.domain.Criteria;
import co.sukbinggo.mapper.AttachMapper;
import co.sukbinggo.mapper.BoardMapper;
import co.sukbinggo.mapper.ReplyMapper;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j;

@Log4j
@Service 
@ToString
@AllArgsConstructor
public class BoardServiceImpl implements BoardService{

//	@Setter
//	@Autowired

	private final BoardMapper boardMapper;
	private final AttachMapper attachMapper;
	private final ReplyMapper replyMapper;
	
	@Override
	@Transactional
	public void register(BoardVO vo) {
		boardMapper.insertSelectKey(vo);
		Long bno = vo.getBno();
		List<AttachVO> list = vo.getAttachs();
		int idx = 0;
		for(AttachVO attach : list){
			attach.setBno(bno);
			attach.setOdr(idx++);
			attachMapper.insert(attach); // DB에 들어갈 재료
		}
	}

	@Override
	public BoardVO get(Long bno) {
		
		return boardMapper.read(bno);
	}

	@Override
	@Transactional
	public boolean modify(BoardVO vo) {
		
		attachMapper.deleteAll(vo.getBno());
		
		List<AttachVO> list = vo.getAttachs();
		
		int idx=0;
		for(AttachVO attach : list){
			attach.setBno(vo.getBno());
			attach.setOdr(idx++);
			attachMapper.insert(attach); // DB에 들어갈 재료
		}
	
		
		return boardMapper.update(vo) > 0;
	}

	@Override
	@Transactional
	public boolean remove(Long bno) {
		replyMapper.deleteByBno(bno);
		attachMapper.deleteAll(bno);
		return boardMapper.delete(bno) > 0;
	}

	@Override
	public List<BoardVO> getList(Criteria cri) {
		// TODO Auto-generated method stub
		return boardMapper.getListWithPaging(cri);
	}

	@Override
	public int getTotalCnt(Criteria cri) {
		return boardMapper.getTotalCnt(cri);
	}

	@Override
	public String deleteFile(AttachFileDTO dto) {
		log.info(dto);
		
		String s = UploadController.getPATH() + "/" + dto.getPath() + "/" + dto.getUuid() + "_" + dto.getName();
		
		File file = new File(s);	
		file.delete();
		if(dto.isImage()){
			s = UploadController.getPATH() + "/" + dto.getPath() + "/s_" + dto.getUuid() + "_" + dto.getName();
			file = new File(s);
			file.delete();
		}
		log.info(dto);
		return dto.getUuid();
	}

	
}
