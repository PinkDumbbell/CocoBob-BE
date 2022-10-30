package com.pinkdumbell.cocobob.common.apilog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "apilog")
public class ApiLog {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Long id;
    private String userEmail;
    private String method;
    private LocalDateTime time;
    private String requestUrl;
    private String handlerName;
    private String queryString;
    private String userAgent;
}
