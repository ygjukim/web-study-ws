package com.weblab.springjdbc.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.weblab.springjdbc.config.AppConfig;
import com.weblab.springjdbc.test.DbQuery;

public class MainUsingDbQuery {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = 
			new AnnotationConfigApplicationContext(AppConfig.class);

		DbQuery dbQuery = ctx.getBean(DbQuery.class);
		int count = dbQuery.count();
		System.out.println(count);
		ctx.close();
	}
}
