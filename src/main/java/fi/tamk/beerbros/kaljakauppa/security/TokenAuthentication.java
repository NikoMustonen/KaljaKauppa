package fi.tamk.beerbros.kaljakauppa.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Collections;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication
        .UsernamePasswordAuthenticationToken;

public class TokenAuthentication {

    public static final long EXPIRES_IN = 1000000;
    static final String SECRET = "SUPERDUBERsecret";
    public static final String HTTP_TOKEN_ID = "Bearer";
    public static final String HTTP_HEADER_ID = "Authorization";
    
    static void addAuthentication(HttpServletResponse res, String username) {
        final String JSON_WEB_TOKEN = Jwts.builder()
                .setSubject(username)
                .setExpiration(
                        new Date(System.currentTimeMillis() + EXPIRES_IN))
                .signWith(SignatureAlgorithm.HS512, SECRET).compact();
        res.addHeader(HTTP_HEADER_ID, HTTP_TOKEN_ID + " " + JSON_WEB_TOKEN);
    }
    
    static Authentication getAuthentication(HttpServletRequest req) {
        String token = req.getHeader(HTTP_HEADER_ID);
        if(token == null) {
            return null;
        }
        
        String user = Jwts.parser().setSigningKey(SECRET)
                .parseClaimsJws(token.replace(HTTP_TOKEN_ID, ""))
                .getBody().getSubject();
        
        if(user == null) return null;
        else return new UsernamePasswordAuthenticationToken(
                user, null, Collections.emptyList());
    }
}
