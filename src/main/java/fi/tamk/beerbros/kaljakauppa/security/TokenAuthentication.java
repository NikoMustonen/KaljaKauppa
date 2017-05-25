package fi.tamk.beerbros.kaljakauppa.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Collections;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.*;

/**
 * Security class for JSON web token authentication..
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version %I%, %G%
 * @since 1.8
 */
public class TokenAuthentication {

    /**
     * Exprisation time.
     */
    public static final long EXPIRES_IN = 1000000;

    /**
     * Secret text for authentication.
     */
    static final String SECRET = "SUPERDUBERsecret";

    /**
     * Authorization header key.
     */
    public static final String HTTP_TOKEN_ID = "Bearer";

    /**
     * Header name.
     */
    public static final String HTTP_HEADER_ID = "Authorization";

    /**
     * Adds authentication values to response.
     *
     * @param res Response to be added values.
     * @param username Current users username.
     */
    static void addAuthentication(HttpServletResponse res, String username) {
        final String JSON_WEB_TOKEN = Jwts.builder()
                .setSubject(username)
                .setExpiration(
                        new Date(System.currentTimeMillis() + EXPIRES_IN))
                .signWith(SignatureAlgorithm.HS512, SECRET).compact();
        res.addHeader(HTTP_HEADER_ID, HTTP_TOKEN_ID + " " + JSON_WEB_TOKEN);
    }

    /**
     * Gets authentication from request if there is one.
     *
     * @param req Checked request.
     * @return Authentication for current try.
     */
    static Authentication getAuthentication(HttpServletRequest req) {
        String token = req.getHeader(HTTP_HEADER_ID);
        if (token == null) {
            return null;
        }

        String user = Jwts.parser().setSigningKey(SECRET)
                .parseClaimsJws(token.replace(HTTP_TOKEN_ID, ""))
                .getBody().getSubject();

        if (user == null) {
            return null;
        } else {
            return new UsernamePasswordAuthenticationToken(
                    user, null, Collections.emptyList());
        }
    }
}
