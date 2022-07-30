package com.pinkdumbell.cocobob.common;


import com.pinkdumbell.cocobob.common.dto.EmailSendResultDto;

public interface EmailUtil {
    EmailSendResultDto sendEmail(String toAddress, String subject, String body);
}