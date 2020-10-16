package com.choo.ex.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.choo.ex.dao.BoardDAO;
import com.choo.ex.dto.BoardDTO;
import com.choo.ex.dto.PageDTO;

@Service
public class BoardService {

	@Autowired
	public BoardDAO bdao;

	public ModelAndView mav;
	
	@Autowired
	public HttpSession session;
	
	
	private static final int PAGE_LIMIT = 4;  //한 페이지에 글 3개 보임
	private static final int BLOCK_LIMIT = 5; //목록에는 5개의 페이지가 보임
	
	//페이징
	public ModelAndView boardlistpaging(int page) {
		mav = new ModelAndView();
		
		int listcount = bdao.listcount();
		int startrow =(page-1)*PAGE_LIMIT+1;	// 1 4 7 10 
		int endrow = page*PAGE_LIMIT;		// 3 6 9 12
		
		PageDTO pdto = new PageDTO();
		pdto.setStartrow(startrow);
		pdto.setEndrow(endrow);
		List<PageDTO> bplist = bdao.boardlistpaging(pdto);
		
		int startpage =(((int)(Math.ceil((double)page/BLOCK_LIMIT)))-1)*BLOCK_LIMIT+1;  //1 6  11 16
		int maxpage =(int)(Math.ceil((double)listcount/PAGE_LIMIT));					//5 10 15 20
		
		int endpage = startpage + BLOCK_LIMIT -1;
		if(endpage>maxpage) 
			{endpage=maxpage;
			}
		
		pdto.setPage(page);
		pdto.setStartpage(startpage);
		pdto.setEndpage(endpage);
		pdto.setMaxpage(maxpage);
		
		mav.addObject("paging" , pdto);
		mav.addObject("boardlist" , bplist);
		mav.setViewName("boardv/boardlistpaging");
		
		return mav;
	}

	//글 쓰기
	public ModelAndView boardwrite(BoardDTO bdto) throws IllegalStateException, IOException {
		mav = new ModelAndView();
		MultipartFile bfile = bdto.getBfile();
		String bfilename = bfile.getOriginalFilename();
		String savepath ="D:\\source\\Spring\\Ex1015\\src\\main\\"
				+ "webapp\\resources\\img"+bfilename;
		System.out.println("첫"+bdto);
		if(!bfile.isEmpty())
			{bfile.transferTo(new File(savepath));
			}
		bdto.setBfilename(bfilename);
		int result = bdao.boardwrite(bdto);
		if(result>0)
			{mav.setViewName("redirect:/boardlistpaging");
			}
		else
			{mav.setViewName("boardv/boardwrite2");
			}
		System.out.println("넷"+bdto);
		return mav;
	}
	//글 상세조회
	public BoardDTO boardview(int bnum) {
		bdao.bhit(bnum);
		BoardDTO bdto = bdao.boardview(bnum);
		return bdto;
	}

	//글 삭제
	public String boarddelete(int bnumber) {
		int result = bdao.boarddelete(bnumber);
		String resultmsg=null;
		if(result>0)
			{resultmsg ="ok";
			}
		else
			{resultmsg ="no";
			}
		return resultmsg;
	}
	
	//글 수정
	public ModelAndView boardupdate(int bnumber, int page) {
		BoardDTO bdto = bdao.boardupdate(bnumber);
		mav.addObject("bdto",bdto);
		mav.addObject("page",page);
		mav.setViewName("boardv/boardupdate");
		return mav;
	}
	
	//글 수정 프로세스
	public ModelAndView boardupdatep(JSONObject bjson) throws IllegalStateException, IOException {
		BoardDTO bdto = new BoardDTO();
		bdto.setBtitle((String) bjson.get("btitle"));
		bdto.setBcontents((String)bjson.get("bcontents"));
		bdto.setBnumber((Integer)bjson.get("bnumber"));
		
		MultipartFile bfile = (MultipartFile)bjson.get("bfile");
		String bfilename = bfile.getOriginalFilename();
		String savepath ="D:\\source\\Spring\\Ex1015\\src\\main\\"
				+ "webapp\\resources\\img"+bfilename;
		
		bdto.setBfilename(bfilename);
		int page = (Integer)bjson.get("page");
		
		if(!bfile.isEmpty())
			{bfile.transferTo(new File(savepath));
			}
		int result = bdao.boardupdatep(bdto);
		mav = new ModelAndView();
		if(result>0)
			{mav.setViewName("boardv/boardlistpaging");
			 mav.addObject("page",page);
			}
		else 
			{mav.setViewName("boardv/boardupdate");
			}
		return mav;
	}
	
	//조회순 정렬
	public List<BoardDTO> boardarrange() {
		List<BoardDTO> blist = bdao.boardlistpagingarrange();
		return blist;
	}
	
	//검색
	public ModelAndView boardsearch(String searchtype, String keyword) {
		List<BoardDTO> bsearchlist = bdao.boardsearch(searchtype,keyword);
		mav = new ModelAndView();
		mav.setViewName("boardv/boardlistpaging");
		mav.addObject("boardlist",bsearchlist);
		return mav;
	}
	
	
	
	
	
	
}
