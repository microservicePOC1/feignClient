package com.co.services.sample.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "sample8-service")
public interface Sample8Client {

	@GetMapping("/")
	String getService();
	
}
