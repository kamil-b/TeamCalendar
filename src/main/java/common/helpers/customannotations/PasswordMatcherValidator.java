package common.helpers.customannotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import common.entities.dto.UserDto;

public class PasswordMatcherValidator implements ConstraintValidator<PasswordMatches, Object> {

	@Override
	public void initialize(PasswordMatches constraintAnnotation) {
		
	}

	@Override
	public boolean isValid(Object obj, ConstraintValidatorContext arg1) {
		UserDto user = (UserDto) obj;
		return user.getPassword().equals(user.getMatchingPassword());
	}

}
