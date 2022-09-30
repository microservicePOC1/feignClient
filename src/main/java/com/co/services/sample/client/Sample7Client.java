package com.co.services.sample.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "sample-service7")
public interface Sample7Client {

	@GetMapping("/")
	String getService();
	
}
