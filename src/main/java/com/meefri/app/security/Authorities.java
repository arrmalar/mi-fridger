package com.meefri.app.security;

import org.springframework.security.core.GrantedAuthority;

public class Authorities implements GrantedAuthority {

    private String authority;

    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
