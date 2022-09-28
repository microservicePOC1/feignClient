package com.co.services.sample.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "sample6-service")
public interface Sample6Client {

	@GetMapping("/")
	String getService();
	
}
