package com.pinkdumbell.cocobob.config.annotation.properbirthinfo;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ProperBirthInfoValidator.class)
public @interface ProperBirthInfo {
    String message() default "반려동물의 생일과 개월 수를 다시 확인해주세요.";
    Class[] groups() default {};
    Class[] payload() default {};
}
