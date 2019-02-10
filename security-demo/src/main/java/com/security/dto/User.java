package com.security.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

import com.security.validate.MyValidateConstant;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@ApiModelProperty("用户id")
	private Integer id;

	@ApiModelProperty("用户昵称")
	@MyValidateConstant(message = "这是一个自定义验证测试")
	private String nickName;

	@ApiModelProperty("密码")
	@NotBlank
	@NotEmpty
	private String password;

	@ApiModelProperty("生日")
	@Past
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date birthday;

}
