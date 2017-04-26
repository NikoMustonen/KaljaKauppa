package fi.tamk.beerbros.kaljakauppa.security;

import io.jsonwebtoken.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import javax.servlet.http.*;
import java.util.Date;

import static java.util.Collections.emptyList;

class TokenAuthenticationService {

    static final long EXPIRATIONTIME = 1000000;
    static final String SECRET = "superDuberExtraPextraSecretPecret";
    static final String TOKEN_PREFIX = "Bearer";
    static final String HEADER_STRING = "Authorization";

    static void addAuthentication(HttpServletResponse res, String username) {
        String JWT = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
    }

    static Authentication getAuthentication(HttpServletRequest req) {
        String token = req.getHeader(HEADER_STRING);
        if (token != null) {
            String user = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody()
                    .getSubject();

            return user != null
                    ? new UsernamePasswordAuthenticationToken(user, null, emptyList())
                    : null;
        }
        return null;
    }
}
