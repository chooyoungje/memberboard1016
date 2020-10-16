package com.choo.ex.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.choo.ex.dao.ReplyDAO;

@Service
public class ReplyService {

	@Autowired
	public ReplyDAO rdao;
}
