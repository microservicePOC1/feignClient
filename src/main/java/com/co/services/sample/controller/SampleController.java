package com.co.services.sample.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.co.parallel.ExecutionUtils;
import com.co.services.sample.client.Sample2Client;
import com.co.services.sample.client.Sample3Client;
import com.co.services.sample.client.Sample4Client;
import com.co.services.sample.client.Sample5Client;
import com.co.services.sample.client.Sample6Client;
import com.co.services.sample.client.Sample7Client;
import com.co.services.sample.client.Sample8Client;
import com.co.services.sample.client.Sample9Client;

@RestController
public class SampleController {
	Boolean runParallel = true;

	Sample2Client sample2Client;
	Sample3Client sample3Client;
	Sample4Client sample4Client;
	Sample5Client sample5Client;
	Sample6Client sample6Client;
	Sample7Client sample7Client;
	Sample8Client sample8Client;
	Sample9Client sample9Client;

	public SampleController(
		Sample2Client sample2Client,
		Sample3Client sample3Client,
		Sample4Client sample4Client,
		Sample5Client sample5Client,
		Sample6Client sample6Client,
		Sample7Client sample7Client,
		Sample8Client sample8Client,
		Sample9Client sample9Client
	) {
		this.sample2Client = sample2Client;
		this.sample3Client = sample3Client;
		this.sample4Client = sample4Client;
		this.sample5Client = sample5Client;
		this.sample6Client = sample6Client;
		this.sample7Client = sample7Client;
		this.sample8Client = sample8Client;
		this.sample9Client = sample9Client;
	}
	
	@GetMapping("/")
	public String sampleApi() {
		if (runParallel) {
			List<Object> pResults = ExecutionUtils.runParallel(
				() -> sample2Client.getService(),
				() -> sample3Client.getService(),
				() -> sample4Client.getService(),
				() -> sample5Client.getService(),
				() -> sample6Client.getService(),
				() -> sample7Client.getService(),
				() -> sample8Client.getService(),
				() -> sample9Client.getService()
			);

			String result = "";
			for (int i = 0; i < 5; ++i) {
				result += (String)pResults.get(i) + "<br/>";
			}
			return result;
		}

		return (
			sample2Client.getService() + "<br/>" +
			sample3Client.getService() + "<br/>" +
			sample4Client.getService() + "<br/>" +
			sample5Client.getService() + "<br/>" +
			sample6Client.getService() + "<br/>" +
			sample7Client.getService() + "<br/>" +
			sample8Client.getService() + "<br/>" +
			sample9Client.getService()
		);
}
	
}
