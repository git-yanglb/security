package com.security.core.social;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component("connect/status")
public class ConenctStatusView extends AbstractView {

	@Autowired
	private ObjectMapper mapper;

	@SuppressWarnings("unchecked")
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, List<Connection<?>>> connections = (Map<String, List<Connection<?>>>) model.get("connectionMap");

		Map<String, Boolean> result = new HashMap<String, Boolean>();
		connections.forEach((k, v) -> {
			if (v != null && v.size() > 0) {
				result.put(k, true);
			} else {
				result.put(k, false);
			}
		});

		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(mapper.writeValueAsString(result));

	}

}
