package com.weblab.springaop;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan({"com.weblab.springaop", "com.weblab.springaop.aspectj"})
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class AopAppConfig {

}
