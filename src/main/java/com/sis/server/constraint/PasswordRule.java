package com.sis.server.constraint;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import java.lang.annotation.Retention;
import java.util.regex.Pattern;

@Retention(RUNTIME)
@Constraint(validatedBy = PasswordRuleValidator.class)
public @interface PasswordRule {
  String message() default "Password does not meet the policy requirements";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}

class PasswordRuleValidator implements ConstraintValidator<PasswordRule, String> {
  /**
   * This regex pattern ensures that the password adheres to the following rules:
   *
   * <ol>
   *   <li>Contains at least one lowercase letter (a-z).
   *   <li>Contains at least one uppercase letter (A-Z).
   *   <li>Contains at least one digit (0-9).
   *   <li>Contains at least one special character from the set: #@$!%*?&.
   *   <li>The password is at least 8 characters long.
   * </ol>
   */
  private static final Pattern PASSWORD_PATTERN =
      Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#@$!%*?&])[A-Za-z\\d#@$!%*?&]{8,}$");

  @Override
  public boolean isValid(String password, ConstraintValidatorContext context) {
    return password != null && PASSWORD_PATTERN.matcher(password).matches();
  }
}
