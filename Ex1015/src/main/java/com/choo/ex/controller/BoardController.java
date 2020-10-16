package com.choo.ex.controller;

import java.io.IOException;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.choo.ex.dto.BoardDTO;
import com.choo.ex.service.BoardService;

@Controller
public class BoardController {

	@Autowired
	public BoardService bsvc;
	
	public ModelAndView mav;
	
	
	
	//
	
	
	
	
	//검색
	@RequestMapping(value="/boardsearch")
	public ModelAndView boardsearch(@ModelAttribute("searchtype") String searchtype,
			@ModelAttribute("keyword") String keyword) {
		mav = bsvc.boardsearch(searchtype,keyword);
		return mav;
	}
	
	//글 조회순 정렬
	@RequestMapping(value="/boardarrange")
	public List<BoardDTO> boardarrange() {
		List<BoardDTO> blist = bsvc.boardarrange();
		return blist;
	}
	
	//글 수정 프로세스
	@RequestMapping(value="/boardupdatep", produces = "application/json; charset=utf8")
	public ModelAndView boardupdatep(@ModelAttribute("bjson") JSONObject bjson) throws IllegalStateException, IOException {
		mav = bsvc.boardupdatep(bjson);
		return mav;
	}
	
	//글 수정
	@RequestMapping(value="/boardupdate")
	public ModelAndView boardupdate(@ModelAttribute("bnumber") int bnumber,@ModelAttribute("page") int page ) {
		mav = bsvc.boardupdate(bnumber,page);
		return mav;
	}
	
	//글 삭제
	@RequestMapping(value="/boarddelete")
	public String boarddelete(@RequestParam("bnumber") int bnumber) {
		String result = bsvc.boarddelete(bnumber);
		return result;
	}
	
	//글 목록
	@RequestMapping(value="/boardlistpaging")
	public ModelAndView boardlist(@RequestParam(value="page",required =false,defaultValue="1") int page) {
		mav = bsvc.boardlistpaging(page);
		return mav;
	}

	//글 쓰기
	@RequestMapping(value="/boardwrite")
	public ModelAndView boardwrite(@ModelAttribute BoardDTO bdto) throws IllegalStateException, IOException {
		System.out.println("첫"+bdto);
		mav = bsvc.boardwrite(bdto);
		return mav;
	}
	
	//글 상세 조회
	@RequestMapping(value="/boardview")
	public @ResponseBody BoardDTO boardview(@RequestParam("bnum") int bnum) {
		BoardDTO bdto = bsvc.boardview(bnum);
		return bdto; 
	}
	
	//글 작성 창으로 가기
	@RequestMapping(value="/boardwritego")
	public String boardwritego() {
		return "boardv/boardwrite2"; 
	}
	
}
