package com.surfer.codes.order_service.domain;

import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    public String getLoggedInUserName() {
        return "surfer-git";
    }
}
