package com.security.async;

import java.util.concurrent.Callable;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class AsyncController {

	@Autowired
	private MockQueue mockQueue;

	@Autowired
	private DeferredResultHolder deferredResultHolder;

	@GetMapping("/dorder")
	public DeferredResult<String> dorder() {
		log.info("主线程开始");

		String orderNumber = RandomStringUtils.randomNumeric(8);
		mockQueue.setPlaceOrder(orderNumber);

		DeferredResult<String> deferredResult = new DeferredResult<String>();
		deferredResultHolder.getDeferredResults().put(orderNumber, deferredResult);

		log.info("主线程结束");
		return deferredResult;
	}

	@GetMapping("/order")
	public Callable<String> async() {
		log.info("主线程开始");
		Callable<String> async = new Callable<String>() {

			@Override
			public String call() throws Exception {
				log.info("副线程开始");
				Thread.sleep(1000);
				log.info("副线程结束");
				return "sucess";
			}
		};
		log.info("主线程结束");
		return async;
	}

}
