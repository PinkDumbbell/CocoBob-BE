package com.pinkdumbell.cocobob.domain.common;

import java.util.Map;

public interface EmailUtil {
    Map<String, Object> sendEmail(String toAddress, String subject, String body);
}