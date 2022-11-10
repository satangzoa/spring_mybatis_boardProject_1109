package com.pengsoo.freeboard.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pengsoo.freeboard.dao.mapper.IDao;
import com.pengsoo.freeboard.dto.FreeBoardDto;
import com.pengsoo.freeboard.dto.MemberDto;

@Controller
public class FboardController {

	@Autowired
	private SqlSession sqlSession;
	private String fnum;
	

	@RequestMapping(value = "/")
	public String goList() {
		return "redirect:list";
	}
	
	
	
	@RequestMapping(value = "joinMember")
	public String joinMember() {
		
		return "joinMember";
	}
	
	@RequestMapping(value = "joinOk", method = RequestMethod.POST)
	public String joinOk(HttpServletRequest request, Model model) {
		
		IDao dao = sqlSession.getMapper(IDao.class);
		
		int checkIdFlag = dao.checkIdDao(request.getParameter("mid"));
		//checkIdFlag 값이 1이면 이미 가입하려는 아이디가 db에 존재, 0이면 가입 가능
		model.addAttribute("checkIdFlag", checkIdFlag);
		
		if (checkIdFlag == 0) {
			dao.joinMemberDao(request.getParameter("mid"), request.getParameter("mpw"), request.getParameter("mname"), request.getParameter("memail"));
			model.addAttribute("mname", request.getParameter("mname"));
		}
		return "joinOk";
	}
	
	@RequestMapping(value = "checkId")
	public String checkId(HttpServletRequest request, Model model) {
		
		IDao dao = sqlSession.getMapper(IDao.class);
		
		int checkIdFlag = dao.checkIdDao(request.getParameter("checkId"));
		//checkIdFlag 값이 1이면 이미 가입하려는 아이디가 db에 존재, 0이면 가입 가능
		model.addAttribute("checkIdFlag", checkIdFlag);
		
		return "checkId";
	}
	
	@RequestMapping(value = "login")
	public String login() {
		
		return "login";
	}
	
	@RequestMapping(value = "loginOk", method = RequestMethod.POST)
	public String loginOk(HttpServletRequest request, Model model) {
		
		String mid = request.getParameter("mid");
		String mpw = request.getParameter("mpw");
		
		IDao dao = sqlSession.getMapper(IDao.class);
		
		int checkIdFlag = dao.checkIdDao(mid);//1이면 아이디 존재
		int checkPwFlag = dao.checkPwDao(mid, mpw);//1이면 아이디 비번 모두 일치
		
		model.addAttribute("checkIdFlag", checkIdFlag);//프론트에서 값을 받는다.
		model.addAttribute("checkPwFlag", checkPwFlag);
		model.addAttribute("mid", mid);
		
		if(checkPwFlag == 1) {//로그인 성공시 세션에 아이디와 로그인유효값을 설정

			HttpSession session = request.getSession();

			session.setAttribute("sessionId", mid);
			session.setAttribute("ValidMem", "yes");

			MemberDto dto = dao.memberInfoDao(mid);
			String mname= dto.getMname();
			model.addAttribute("mname", mname);
			
		} else {
			
			model.addAttribute("mname", "guest");
		}
		
		return "loginOk";
	}
	
	@RequestMapping(value = "writeForm")//요청만 넣어서 넘어온다
	public String writeForm(HttpServletRequest request, Model model) {//세션이 레퀘스트 객체에 있다
		
		IDao dao = sqlSession.getMapper(IDao.class);
		
		HttpSession session = request.getSession();
		String sid = (String)session.getAttribute("sessionId");//객체타입으로 변환하니까  (String)session.을 만들어준다.
		
		
		if(sid==null) {
			return "redirect:login";
		} else {
		
		MemberDto dto = dao.memberInfoDao(sid);
		String mname= dto.getMname();
		String mid= dto.getMid();
		model.addAttribute("mname", mname);
		
		model.addAttribute("mid", mid);
		return "writeForm";
	}}
	
	@RequestMapping(value = "write")
	public String write(HttpServletRequest request) {
		
		IDao dao = sqlSession.getMapper(IDao.class);
		
		HttpSession session = request.getSession();
		String sid = (String) session.getAttribute("sessionId");

//		String mname="";
//		String mid="";
//		
//		if(sid.equals(null)) {
//			mname="손님";
//			mid="guest";
//		} else {

			MemberDto dto = dao.memberInfoDao(sid);
			String mname= dto.getMname();
			String mid = dto.getMid();		
//		}
		String ftitle = request.getParameter("ftitle");
		String fcontent = request.getParameter("fcontent");

		dao.writeDao(mid, mname, ftitle, fcontent);

		return "redirect:list";
	}
	
	@RequestMapping(value = "logout")
	public String logout(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		session.invalidate();//섹션삭제 즉 로그아웃
		
		return"logout";
	}
	
	@RequestMapping(value = "list")
	public String list( Model model) {

		IDao dao = sqlSession.getMapper(IDao.class);//dao를 만들어야 list호출 가능
		
		ArrayList<FreeBoardDto> dtos = dao.listDao();
		
//		int count = dtos.size();//size 가 총 글의 갯수
//		model.addAttribute("boardCount", count);
		
		model.addAttribute("boardCount", dtos.size());
		model.addAttribute("list", dtos);
		
		return "list";
	}
	

	@RequestMapping(value = "content_view")
	public String content_view(HttpServletRequest request, Model model) {
		
		 String fnum = request.getParameter("fnum");//반드시 문자열로 넣어야한다.="fnum"
		 
		 IDao dao = sqlSession.getMapper(IDao.class);
		
		 //int fhit = content.getFhit();
		 //fhit - ghit+1;
		 //upHit()함수 호출.조회수 증가 함수
		 
		 dao.upHit(fnum);//요청이 아니다.리스트 클릭할때만 증가.
		 FreeBoardDto  dtos = dao.contentDao(fnum);
		 
		  HttpSession session = request.getSession();//현재 세션 가져오기
		  
		  String sid = (String) session.getAttribute("sessionId");// 현재 세션에 로그인 되어 있는 아이디 가져오기
		  
		 String fid = content.getFid(); //현재 보고 있는 글을 쓴 아이디
		 
	    
	     
	     int idflag = 0;
	     
	     if((sid != null ) && sid.equals(fid)) {
	    	 int idflag = 1;
	     } 
	    	 model.addAttribute("idflag",idflag);//idflag==1 이면 수정 삭제 권한 설정
	   
	     
	     model.addAttribute("content", dtos);//content_view에서 ${content.}적어준다
	     
		return "content_view";
		
	}
	
	@RequestMapping(value = "modify_view")
	public String modify_view(HttpServletRequest request, Model model) {
		
		String fnum = request.getParameter("fnum");
		 
		 IDao dao = sqlSession.getMapper(IDao.class);
		
		 FreeBoardDto  dtos = dao.contentDao(fnum);
	     model.addAttribute("content", dtos);
		
		return "modify_view";
	}
	
	@RequestMapping(value = "delete")
	public String delete(HttpServletRequest request) {
		
		String fnum = request.getParameter("fnum");
		IDao dao = sqlSession.getMapper(IDao.class);
		dao.deleteDao(fnum);//반환할게 없다.
		return "redirect:list";
	}
	
	@RequestMapping(value = "modify")
	public String modify(HttpServletRequest request) {
		String fnum = request.getParameter("fnum");
		String fname = request.getParameter("fname");
		String ftitle = request.getParameter("ftitle");
		String fcontent = request.getParameter("fcontent");
		 
		 IDao dao = sqlSession.getMapper(IDao.class);
		
		 dao.modifyDao(fnum,fname, ftitle, fcontent );//반환할게 없다.
		 
		
		return "redirect:list";
	}
  
	      
}
