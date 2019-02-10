package com.security.async;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class QueueListener implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private MockQueue mockQueue;

	@Autowired
	private DeferredResultHolder deferredResultHolder;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

		Thread thread = new Thread(() -> {

			while (true) {

				if (StringUtils.isNotBlank(mockQueue.getCompleteOrder())) {

					String orderNumber = mockQueue.getCompleteOrder();
					log.info("返回订单处理结果：" + orderNumber);
					deferredResultHolder.getDeferredResults().get(orderNumber).setResult("place order sucess");
					mockQueue.setCompleteOrder(null);

				} else {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}

		});

		thread.setDaemon(true);
		thread.start();

	}

}
