package com.pinkdumbell.cocobob.domain.common;

import com.pinkdumbell.cocobob.domain.common.dto.EmailSendResultDto;
import java.util.Map;

public interface EmailUtil {
    EmailSendResultDto sendEmail(String toAddress, String subject, String body);
}