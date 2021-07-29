package com.weblab.springjdbc.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.weblab.springjdbc.config.AppConfig;
import com.weblab.springjdbc.exception.MemberNotFoundException;
import com.weblab.springjdbc.exception.WrongIdPasswordException;
import com.weblab.springjdbc.service.ChangePasswordService;

public class MainForCPS {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = 
				new AnnotationConfigApplicationContext(AppConfig.class);
//		AbstractApplicationContext ctx =
//				new ClassPathXmlApplicationContext("classpath:com/weblab/springjdbc/config/AppContext.xml");
		
        ChangePasswordService cps = 
                ctx.getBean("changePwdSvc", ChangePasswordService.class);
        try {
            cps.changePassword("madvirus@madvirus.net", "1234", "1111");
            System.out.println("암호를 변경했습니다.");
        } catch (MemberNotFoundException e) {
            System.out.println("회원 데이터가 존재하지 않습니다.");
        } catch (WrongIdPasswordException e) {
            System.out.println("암호가 올바르지 않습니다.");
        }

		ctx.close();

	}
}
