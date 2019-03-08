package br.com.londrisoft.uniqueweb.security;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

import static br.com.londrisoft.configuracaoapi.security.SecurityConstants.CHAVE_SECRETA;
import static br.com.londrisoft.configuracaoapi.security.SecurityConstants.TEMPO_EXPIRACAO;

/**
 * @author Lucas Duarte for X-Brain in 07/11/18
 */
@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    public String generateToken(String email) {

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + TEMPO_EXPIRACAO);

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, CHAVE_SECRETA)
                .compact();
    }

    public String getSubject(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(CHAVE_SECRETA)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(CHAVE_SECRETA).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }
}