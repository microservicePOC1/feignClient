package com.co.services.sample.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "sample-service3")
public interface Sample3Client {

	@GetMapping("/")
	String getService();
	
}
