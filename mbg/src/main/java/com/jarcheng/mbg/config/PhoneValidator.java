package com.jarcheng.mbg.config;

import com.jarcheng.mbg.annotation.CheckPhone;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PhoneValidator implements ConstraintValidator<CheckPhone, String> {
    private static final Pattern CHINA_PATTERN = Pattern.compile("^((13[0-9])|(14[0,1,4-9])|(15[0-3,5-9])|(16[2,5,6,7])|(17[0-8])|(18[0-9])|(19[0-3,5-9]))\\d{8}$");
    private static final Pattern HK_PATTERN = Pattern.compile("^((13[0-9])|(14[0,1,4-9])|(15[0-3,5-9])|(16[2,5,6,7])|(17[0-8])|(18[0-9])|(19[0-3,5-9]))\\d{8}$");


    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {

        return CHINA_PATTERN.matcher(s).matches() || HK_PATTERN.matcher(s).matches();
    }
}
