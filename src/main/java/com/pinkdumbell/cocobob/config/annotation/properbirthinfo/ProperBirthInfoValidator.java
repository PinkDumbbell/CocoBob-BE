package com.pinkdumbell.cocobob.config.annotation.properbirthinfo;

import com.pinkdumbell.cocobob.domain.pet.dto.PetCreateRequestAgeDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ProperBirthInfoValidator implements ConstraintValidator<ProperBirthInfo, PetCreateRequestAgeDto> {
    @Override
    public boolean isValid(PetCreateRequestAgeDto value, ConstraintValidatorContext context) {
        if (value == null || value.getMonths() == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("반려동물의 나이(개월 수)가 필요합니다.").addConstraintViolation();
            return false;
        } else if (value.getMonths() < 0) {
            context.disableDefaultConstraintViolation();;
            context.buildConstraintViolationWithTemplate("반려동물의 나이(개월 수)는 0보다 작을 수 없습니다.").addConstraintViolation();
            return false;
        } else if (value.getBirthday() == null && value.getMonths() == 0) {
            context.disableDefaultConstraintViolation();;
            context.buildConstraintViolationWithTemplate("반려동물의 생일과 나이(개월 수)를 확인해주세요.").addConstraintViolation();
            return false;
        }
        return true;
    }
}
