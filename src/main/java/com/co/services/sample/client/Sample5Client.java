package com.co.services.sample.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "sample-service5")
public interface Sample5Client {

	@GetMapping("/")
	String getService();
	
}
