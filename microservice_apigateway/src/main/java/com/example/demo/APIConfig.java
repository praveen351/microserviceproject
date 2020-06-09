package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.filter.ErrorFilter;
import com.example.demo.filter.PreFilter;

@Configuration
public class APIConfig {
	@Bean
	public PreFilter preFilter() {
		return new PreFilter();
	}
	@Bean
	public ErrorFilter errorFilter() {
		return new ErrorFilter();
	}
}
