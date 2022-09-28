package com.co.services.sample.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "sample4-service")
public interface Sample4Client {

	@GetMapping("/")
	String getService();
	
}
