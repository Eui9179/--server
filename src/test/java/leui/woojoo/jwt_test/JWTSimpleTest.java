package leui.woojoo.jwt_test;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Base64;
import java.util.Date;
import java.util.Map;

public class JWTSimpleTest {
    private final byte[] SEC_KEY = Base64.getDecoder().decode("leui");

    private void printToken(String token) {
        String[] tokens = token.split("\\.");
        System.out.println("header: " + new String(Base64.getDecoder().decode(tokens[0])));
        System.out.println("body: " + new String(Base64.getDecoder().decode(tokens[1])));
    }

    @DisplayName("1. jjwt")
    @Test
    void t1() {
        String oktaToken = Jwts.builder().addClaims(
                        Map.of("name", "leui", "price", 3000)
                ).signWith(SignatureAlgorithm.HS256, SEC_KEY)
                .compact();

        System.out.println("oktaToken = " + oktaToken);
        printToken(oktaToken);

        Jws<Claims> tokenInfo = Jwts.parser().setSigningKey("leui").parseClaimsJws(oktaToken);
        System.out.println("tokenInfo = " + tokenInfo);
    }

    @DisplayName("2. java-jwt")
    @Test
    void t2() {
        String oauthToken = JWT.create()
                .withClaim("name", "leui")
                .withClaim("price", 3000)
                .sign(Algorithm.HMAC256(SEC_KEY));

        System.out.println("oauthToken = " + oauthToken);
        printToken(oauthToken);

        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(SEC_KEY))
                .build()
                .verify(oauthToken);

        System.out.println("ver = " + decodedJWT.getClaims().get("id"));
    }

    @DisplayName("3. ext")
    @Test
    void t3() throws InterruptedException {
        final Algorithm AL = Algorithm.HMAC256("leui");

        String jwt = JWT.create()
                .withSubject("a1234")
                .withExpiresAt(new Date(System.currentTimeMillis() + 3000))
                .sign(AL);

        Thread.sleep(2000);

        DecodedJWT verify = JWT.require(AL)
                .build()
                .verify(jwt);
        System.out.println(verify.getClaims().get("sub"));
    }
}
