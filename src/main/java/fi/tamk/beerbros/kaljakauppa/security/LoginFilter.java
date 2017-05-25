package fi.tamk.beerbros.kaljakauppa.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Collections;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Entity class for high score entities.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version %I%, %G%
 * @since 1.7
 */
public class LoginFilter extends AbstractAuthenticationProcessingFilter {

    public LoginFilter(String path, AuthenticationManager am) {
        super(new AntPathRequestMatcher(path));
        setAuthenticationManager(am);
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException, IOException, ServletException {
        
        Account acc = new ObjectMapper().readValue(
                req.getInputStream(), Account.class);

        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                        acc.getUsername(), acc.getPassword(),
                        Collections.emptyList()
                )
        );
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest req, HttpServletResponse res, FilterChain chain,
            Authentication auth) throws IOException, ServletException {
        
        TokenAuthentication
                .addAuthentication(res, auth.getName());
    }
}
