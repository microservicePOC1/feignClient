package com.co.services.sample.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "sample7-service")
public interface Sample7Client {

	@GetMapping("/")
	String getService();
	
}
