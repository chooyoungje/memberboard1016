package com.choo.ex.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.choo.ex.dto.MemberDTO;
import com.choo.ex.service.MemberService;

@Controller
public class MemberController {

	@Autowired
	public MemberService msvc;
	
	public ModelAndView mav;
	
	//회원목록
	@RequestMapping(value="/memberlistpaging")
	public ModelAndView memberlistpaging(@RequestParam(value="page",required=false,defaultValue="1") int page) {
		mav = msvc.memberlistpaging(page);
		return mav;
	}

	//로그인
	@RequestMapping(value="/memberlogingo")
	public ModelAndView memberlogingo(@ModelAttribute MemberDTO mdto) {
		mav = msvc.memberlogin(mdto);
		return mav;
	}
	
	//회원가입
	@RequestMapping(value="/memberjoingo")
	public ModelAndView memberjoingo(@ModelAttribute MemberDTO mdto) throws IllegalStateException, IOException {
		mav = msvc.memberjoin(mdto);
		return mav;
	}
	
	//아이디 체크
	@RequestMapping(value="/idcheck")
	public @ResponseBody String idcheck(@RequestParam("mid") String mid){
		String result = msvc.idcheck(mid);
		return result;
	}
	
	//홈으로
	@RequestMapping(value="/")
	public String home() {
		return "home";
	}
	//회원가입 창으로
	@RequestMapping(value="/memberjoin")
	public String memberjoin() {
		return "memberv/memberjoin";
	}
	//로그인 창으로
	@RequestMapping(value="/memberlogin")
	public String memberlogin() {
		return "memberv/memberlogin";
	}
}
