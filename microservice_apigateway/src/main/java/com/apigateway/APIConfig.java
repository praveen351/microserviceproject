package com.apigateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.apigateway.filter.PreFilter;

@Configuration
public class APIConfig {
	@Bean
	public PreFilter preFilter() {
		return new PreFilter();
	}
}
