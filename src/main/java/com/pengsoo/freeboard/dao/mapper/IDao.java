package com.pengsoo.freeboard.dao.mapper;

import java.util.ArrayList;

import com.pengsoo.freeboard.dto.FreeBoardDto;
import com.pengsoo.freeboard.dto.MemberDto;

public interface IDao {

	//member 관련 메소드
	public void joinMemberDao(String mid, String mpw, String mname, String memail);//매개변수는 회원가입
	
	public int checkIdDao(String mid);//회원 가입 여부 확인(1반환이면 이미 존재, 0이면 가입 가능)//회원 가입 여부 확인 // 1아니면 0 반환하니까 int
	
	public int checkPwDao(String mid, String mpw);//아이디와 비밀번호 일치여부 체크
	
	public MemberDto memberInfoDao(String mid);//가입된 회원정보를 불러옴. 반환타입은 MemberDto
	
	//board 관련 메소드
	public void writeDao(String mid, String mname, String ftitle, String fcontent ); //게시판 글쓰기 반환타입이 없으므로 void

	public ArrayList<FreeBoardDto> listDao();//게시판 리스트 불러오기

	public FreeBoardDto contentDao(String fnum); //반환은 FreeBoardDto .글내용보기(클릭한 번호의 글 1개 불러오기)
	
	public void deleteDao(String fnum);//글삭제
	
	public void modifyDao(String fnum, String fname, String ftitle,String fcontent);//글수정

	public void upHit(String fnum);//조회수 증가 함수
	
}
