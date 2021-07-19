package com.weblab.springex.di;

import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.factory.config.CustomEditorConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan("com.weblab.springex.di")
@Configuration
public class AppConfig {

	@Bean
	public CustomEditorConfigurer customEditorConfigurar() {
		CustomEditorConfigurer configurar = 
			new CustomEditorConfigurer();
		
		PropertyEditorRegistrar[] list = { new CustomDateEditorRegistrar() };
		
		configurar.setPropertyEditorRegistrars(list);
		
		return configurar;
	}
}
