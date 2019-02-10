package com.security.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MyValidateConstanter implements ConstraintValidator<MyValidateConstant, Object> {

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		return true;
	}

}
