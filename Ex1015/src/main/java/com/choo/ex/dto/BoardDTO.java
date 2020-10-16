package com.choo.ex.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BoardDTO {

	private int bnumber; 
	private String bwriter; 
	private String btitle;
	private String bcontents;
	private String bdate; 
	private int bhits; 
    private MultipartFile bfile; 
    private String bfilename; 
}
