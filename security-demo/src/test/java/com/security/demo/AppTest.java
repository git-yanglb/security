package com.security.demo;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest("com.security.AppApplication")
public class AppTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Test
	public void whenUpdate() throws Exception {
		Date date = new Date();
		String result = mockMvc
				.perform(MockMvcRequestBuilders.put("/user/1").contentType(MediaType.APPLICATION_JSON_UTF8)
						.content("{\"nickName\":\"aa\",\"password\":null,\"birthday\": " + date.getTime() + " }"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn().getResponse().getContentAsString();
		System.out.println(result);
	}

	public void whenPost() throws Exception {

		Date date = new Date();

		String result = mockMvc
				.perform(MockMvcRequestBuilders.post("/user").contentType(MediaType.APPLICATION_JSON_UTF8)
						.content("{\"nickName\":\"aa\",\"password\":null,\"birthday\": " + date.getTime() + " }"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1")).andReturn().getResponse()
				.getContentAsString();
		System.out.println(result);
	}

	public void whenGetInfoFail() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/user/a").contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	public void whenGetName() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/user/1").contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.nickName").value("aa"));
	}

	public void shouldAnswerWithTrue() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/usrs").contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3));
	}

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

}
