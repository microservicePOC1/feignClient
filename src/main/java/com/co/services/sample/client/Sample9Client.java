package com.co.services.sample.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "sample9-service")
public interface Sample9Client {

	@GetMapping("/")
	String getService();
	
}
