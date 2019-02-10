package com.security.async;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class DeferredResultHolder {

	private Map<String, DeferredResult<String>> deferredResults = new ConcurrentHashMap<>();

}
