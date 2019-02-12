package com.security.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.app.social.AppSignUpUtils;
import com.security.core.properties.SecurityProperties;
import com.security.dto.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@RestController
@Api(value = "用户服务", tags = { "用户服务" })
@Slf4j
public class UserController {

	@Autowired
	private ObjectMapper mapper;

	private List<User> users;

	{
		users = new ArrayList<>();
		users.add(new User(1, "aa", "aa", null));
		users.add(new User(2, "bb", "bb", null));
		users.add(new User(3, "cc", "cc", null));
	}

	// @Autowired
	// private ProviderSignInUtils providerSignInUtils;

	@Autowired
	private AppSignUpUtils appSignUpUtils;

	@Autowired
	private SecurityProperties securityProperties;

	@GetMapping("/user/me")
	public Object userInfo() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	@GetMapping("/user/me/1")
	public Object userInfo1(Authentication authentication, HttpServletRequest request) throws Exception {

		String authorization = request.getHeader("Authorization");
		String token = StringUtils.substringAfter(authorization, "bearer ");

		Claims claims = Jwts.parser().setSigningKey(securityProperties.getOauth2()
				.getJwtSigningKey().getBytes("UTF-8")).parseClaimsJws(token).getBody();

		String company = String.valueOf(claims.get("company"));

		log.info("---> {}", company);
		
		return authentication;
	}

	// @GetMapping("/user/me/2")
	// public Object userInfo2(@AuthenticationPrincipal(expression = "user")
	// UserDetails userDetails) {
	// return userDetails;
	// }

	@ApiOperation("用户列表")
	@GetMapping("/users")
	public List<User> users() {
		return users;
	}

	@ApiOperation("查询用户信息")
	@GetMapping("/user/{id:\\d+}")
	public User getUserInfo(@PathVariable("id") @ApiParam(value = "用户id", example = "1") Integer id) {
		Integer userIndex = IntStream.range(0, users.size()).mapToObj(index -> {
			if (index == id - 1) {
				return index;
			} else {
				return -1;
			}
		}).filter(index -> index >= 0).findFirst().get();
		return users.get(userIndex);
	}

	@ApiOperation("创建用户")
	@PostMapping("/user")
	public User create(@RequestBody @Validated User user, BindingResult errors) throws Exception {

		if (errors.hasErrors()) {
			errors.getAllErrors().stream().forEach(e -> {
				FieldError fe = (FieldError) e;
				System.out.println(fe.getField() + "=" + fe.getDefaultMessage());
			});
		}

		System.out.println(mapper.writeValueAsString(user));
		return user.setId(1);
	}

	@ApiOperation("更新用户")
	@PutMapping("/user/{id:\\d+}")
	public User update(@PathVariable @ApiParam(value = "用户id", example = "1") Integer id,
			@RequestBody @Validated User user, BindingResult errors) throws Exception {
		if (errors.hasErrors()) {
			errors.getAllErrors().stream().forEach(e -> {
				FieldError fe = (FieldError) e;
				System.out.println(fe.getField() + "=" + fe.getDefaultMessage());
			});
		}

		System.out.println(mapper.writeValueAsString(user));
		return user.setId(1);
	}

	@PostMapping("/user/regist")
	public void regist(User user, HttpServletRequest request) {
		String userId = user.getNickName();
		// providerSignInUtils.doPostSignUp(userId, new
		// ServletWebRequest(request));

		appSignUpUtils.doPostSignUp(new ServletWebRequest(request), userId);
	}

	@GetMapping("/user/test")
	public ModelAndView test() {
		return new ModelAndView("forward:/users");
	}

}
