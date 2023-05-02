package co.sukbinggo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import co.sukbinggo.domain.AuthVO;
import co.sukbinggo.domain.MemberVO;

public interface MemberMapper {
	MemberVO read(String userid);
}
