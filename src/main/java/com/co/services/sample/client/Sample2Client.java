package com.co.services.sample.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "sample-service2")
public interface Sample2Client {

	@GetMapping("/")
	String getService();
	
}
