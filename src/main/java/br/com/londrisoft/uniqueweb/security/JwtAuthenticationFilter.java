package br.com.londrisoft.uniqueweb.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static br.com.londrisoft.uniqueweb.security.SecurityConstants.HEADER_TOKEN;
import static br.com.londrisoft.uniqueweb.security.SecurityConstants.PREFIXO_TOKEN;

/**
 * @author Lucas Duarte for X-Brain in 07/11/18
 */
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    @Autowired
    private JwtAuthenticationProvider authenticationProvider;

    @Autowired
    private JwtTokenProvider tokenProvider;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {

        // Fix: Autowired não cria instancia automaticamente em Servlet Filters
        if(authenticationProvider == null) {
            ServletContext servletContext = req.getServletContext();
            WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            authenticationProvider = applicationContext.getBean(JwtAuthenticationProvider.class);
            tokenProvider = applicationContext.getBean(JwtTokenProvider.class);
        }

        String header = req.getHeader(HEADER_TOKEN);
        if (header == null || !header.startsWith(PREFIXO_TOKEN)) {
            chain.doFilter(req, res);
            return;
        }

        String accessToken = header.replace(PREFIXO_TOKEN, "");
        if(!tokenProvider.validateToken(accessToken)) {
            chain.doFilter(req, res);
            return;
        }

        Authentication auth = authenticationProvider.fromToken(accessToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
        chain.doFilter(req, res);
    }
}