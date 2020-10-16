package com.choo.ex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import com.choo.ex.service.ReplyService;

@Controller
public class ReplyController {

	@Autowired
	public ReplyService rsvc;
	
	public ModelAndView mav;
}
