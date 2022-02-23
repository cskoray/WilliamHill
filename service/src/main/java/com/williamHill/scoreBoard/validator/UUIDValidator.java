package com.williamHill.scoreBoard.validator;

import static java.util.regex.Pattern.compile;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UUIDValidator implements
    ConstraintValidator<ValidUUID, String> {

  private Pattern pattern;
  private Matcher matcher;
  private static final String UUID_V4_PATTERN =
      "[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}";

  @Override
  public void initialize(ValidUUID constraintAnnotation) {
  }

  @Override
  public boolean isValid(String uuid, ConstraintValidatorContext context) {
    return (validateUUID(uuid));
  }

  private boolean validateUUID(String verificationCode) {
    pattern = compile(UUID_V4_PATTERN);
    matcher = pattern.matcher(verificationCode);
    return matcher.matches();
  }
}
