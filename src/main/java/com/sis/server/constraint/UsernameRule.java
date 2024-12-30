package com.sis.server.constraint;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import java.lang.annotation.Retention;
import java.util.regex.Pattern;

@Retention(RUNTIME)
@Constraint(validatedBy = UsernameRuleValidator.class)
public @interface UsernameRule {
  String message() default "Username must be a valid student number or email address";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}

class UsernameRuleValidator implements ConstraintValidator<UsernameRule, String> {
  private static final Pattern EMAIL_PATTERN =
      Pattern.compile(
          "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
  private static final Pattern STUDENT_NUMBER_PATTERN =
      Pattern.compile("^[1-9][0-9]*$"); // Only positive integers (no leading zeros)

  @Override
  public boolean isValid(String username, ConstraintValidatorContext context) {
    if (username == null) return false;
    return EMAIL_PATTERN.matcher(username).matches()
        || STUDENT_NUMBER_PATTERN.matcher(username).matches();
  }
}
