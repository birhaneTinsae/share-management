package com.enat.sharemanagement.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class MinSizeConstraintValidator implements ConstraintValidator<MinSizeConstraint, List<Object>> {
   public void initialize(MinSizeConstraint constraint) {
   }

   public boolean isValid(List<Object> obj, ConstraintValidatorContext context) {
      return obj.size()>0;
   }
}
