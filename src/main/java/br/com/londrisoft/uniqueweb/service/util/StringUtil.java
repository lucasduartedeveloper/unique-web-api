package br.com.londrisoft.uniqueweb.service.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;

public class StringUtil {

    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static SecureRandom rnd = new SecureRandom();
    static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static String randomString( int len ){
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
        return sb.toString();
    }

    public static BCryptPasswordEncoder passwordEncoder() {
        return passwordEncoder;
    }

    public static String addWildCards(String source) {
        return '%' + source + '%';
    }
}
