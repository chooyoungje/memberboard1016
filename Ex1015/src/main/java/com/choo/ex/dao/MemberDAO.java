package com.choo.ex.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.choo.ex.dto.MemberDTO;
import com.choo.ex.dto.PageDTO;

@Repository
public class MemberDAO {

	@Autowired
	public SqlSessionTemplate sql;
	//아이디 중복체크
	public String idcheck(String mid) {
		return sql.selectOne("Member.idcheck",mid);
	}
	
	//회원가입
	public int memberjoin(MemberDTO mdto) {
		return sql.insert("Member.memberjoin",mdto);
	}
	
	//로그인
	public String memberlogin(MemberDTO mdto) {
		return sql.selectOne("Member.memberlogin", mdto);
	}
	
	//전체 회원수 
	public int listcount() {
		return sql.selectOne("Member.mlistcount");
	}

	//시작행 과 끝행을 가지고 회원 가져오기
	public List<PageDTO> memberlistpaging(PageDTO pdto) {
		return sql.selectList("Member.memberlistpaging",pdto);
	}
	
}
