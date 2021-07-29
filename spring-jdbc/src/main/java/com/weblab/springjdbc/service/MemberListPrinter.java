package com.weblab.springjdbc.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weblab.springjdbc.dao.Member;
import com.weblab.springjdbc.dao.MemberDao;

@Service("listPrinter")
public class MemberListPrinter {

	@Autowired
	private MemberDao memberDao;
	@Autowired
	private MemberPrinter printer;

	public MemberListPrinter(MemberDao memberDao, MemberPrinter printer) {
		this.memberDao = memberDao;
		this.printer = printer;
	}

	public void printAll() {
		Collection<Member> members = memberDao.selectAll();
		members.forEach(m -> printer.print(m));
	}

}
