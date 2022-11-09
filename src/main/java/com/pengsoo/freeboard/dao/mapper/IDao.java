package com.pengsoo.freeboard.dao.mapper;

public interface IDao {

	//member 관련 메소드
	public void joinMemberDao(String mid, String mpw, String mname, String memail);//매개변수는 회원가입
	
	public int checkIdDao(String mid);//회원 가입 여부 확인(1반환이면 이미 존재, 0이면 가입 가능)//회원 가입 여부 확인 // 1아니면 0 반환하니까 int
	
	public int checkPwDao(String mid, String mpw);//아이디와 비밀번호 일치여부 체크
	
}
