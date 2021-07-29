package com.weblab.springjdbc.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.weblab.springjdbc.dao.Member;
import com.weblab.springjdbc.dao.MemberDao;
import com.weblab.springjdbc.dao.RegisterRequest;
import com.weblab.springjdbc.exception.DuplicateMemberException;

@Service("memberRegSvc")
public class MemberRegisterService {
	@Autowired
	private MemberDao memberDao;

	public MemberRegisterService(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

	@Transactional
	public Long regist(RegisterRequest req) {
		Member member = memberDao.selectByEmail(req.getEmail());
		if (member != null) {
			throw new DuplicateMemberException("dup email " + req.getEmail());
		}
		Member newMember = new Member(
				req.getEmail(), req.getPassword(), req.getName(), 
				LocalDateTime.now());
		memberDao.insert(newMember);
		return newMember.getId();
	}
}
