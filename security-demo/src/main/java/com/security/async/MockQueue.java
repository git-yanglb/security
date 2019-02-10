package com.security.async;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
@Component
public class MockQueue {

	private String placeOrder;

	private String completeOrder;

	public void setPlaceOrder(String placeOrder) {
		log.info("接到下单请求：" + placeOrder);
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.completeOrder = placeOrder;
		log.info("下单请求处理完毕。");
	}
}
