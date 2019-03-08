package br.com.londrisoft.uniqueweb.security;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author Lucas Duarte for X-Brain in 07/11/18
 */
public class MyGrantedAuthority implements GrantedAuthority {

    private String authority;

    public MyGrantedAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
