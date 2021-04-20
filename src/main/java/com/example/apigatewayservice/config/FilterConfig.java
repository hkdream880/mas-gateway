package com.example.apigatewayservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


//application.yml의 내용을 java 코드로 구현
@Configuration
public class FilterConfig {
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
//	@Bean
	public RouteLocator gatewayRouters(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(r -> r.path("/first-service/**")	
							.filters(f -> f.addRequestHeader("first-request", "first-request-header")
							.addResponseHeader("first-response", "first-response-header"))
							.uri("http://localhost:8081"))
				.route(r -> r.path("/second-service/**")
						.filters(f -> f.addRequestHeader("second-request", "second-request-header")
						.addResponseHeader("second-response", "second-response-header"))
						.uri("http://localhost:8082"))
				.build();
	}

}
