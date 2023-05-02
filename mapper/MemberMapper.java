package co.sukbinggo.mapper;

import java.util.List;

import co.sukbinggo.domain.AuthVO;
import co.sukbinggo.domain.MemberVO;

public interface MemberMapper {
	MemberVO read(String userid);
}
