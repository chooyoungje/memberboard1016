package com.choo.ex.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.choo.ex.dao.MemberDAO;
import com.choo.ex.dto.MemberDTO;
import com.choo.ex.dto.PageDTO;

@Service
public class MemberService {

	@Autowired
	public MemberDAO mdao;
	
	public ModelAndView mav;
	
	@Autowired
	public HttpSession session;

	//아이디 중복확인
	public String idcheck(String mid) {
		String result = mdao.idcheck(mid);
		String resultmsg = null;
		if(result == null)
			{resultmsg="ok";
			}
		else
			{resultmsg="no";
			}
		return resultmsg;
	}

	//회원가입
	public ModelAndView memberjoin(MemberDTO mdto) throws IllegalStateException, IOException {
		mav = new ModelAndView();
		MultipartFile mphoto = mdto.getMphoto();
		String mphotoname = mphoto.getOriginalFilename();
		String savepath ="D:\\source\\Spring\\Ex1015\\src\\main\\"
				+ "webapp\\resources\\mphoto"+mphotoname;
		if(!mphoto.isEmpty())
			{mphoto.transferTo(new File(savepath));
			}
		mdto.setMphotoname(mphotoname);
		int result = mdao.memberjoin(mdto);
		if(result==1)
			{mav.setViewName("memberv/memberlogin");
			}
		else
			{mav.setViewName("memberv/memberloginfail");
			}
		return mav;
	}

	//로그인
	public ModelAndView memberlogin(MemberDTO mdto) {
		String loginid = mdao.memberlogin(mdto);
		mav = new ModelAndView();
		if(loginid!=null)
			{mav.setViewName("redirect:/boardlistpaging");
			 session.setAttribute("loginid",loginid);
			}
		else
			{mav.setViewName("memberv/memberloginfail");
			}
		return mav;
	}

	private static final int PAGE_LIMIT = 4;  //한 페이지에 글 3개 보임
	private static final int BLOCK_LIMIT = 5; //목록에는 5개의 페이지가 보임
	
	//회원목록
	public ModelAndView memberlistpaging(int page) {
		mav = new ModelAndView();
		
		int listcount = mdao.listcount();
		int startrow =(page-1)*PAGE_LIMIT+1;	// 1 4 7 10 
		int endrow = page*PAGE_LIMIT;		// 3 6 9 12
		
		PageDTO pdto = new PageDTO();
		pdto.setStartrow(startrow);
		pdto.setEndrow(endrow);
		List<PageDTO> bplist = mdao.memberlistpaging(pdto);
		
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
		mav.addObject("memberlist" , bplist);
		mav.setViewName("memebrv/memberlistpaging");
		
		return mav;
	}
	
	
	
	
	
	
}
