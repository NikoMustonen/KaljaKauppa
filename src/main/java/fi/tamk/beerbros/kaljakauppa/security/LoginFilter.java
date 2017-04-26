package fi.tamk.beerbros.kaljakauppa.security;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Collections;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.web.authentication.*;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class LoginFilter extends AbstractAuthenticationProcessingFilter {

    public LoginFilter(String url, AuthenticationManager authManager) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException, IOException, ServletException {
        Credentials creds = new ObjectMapper()
                .readValue(req.getInputStream(), Credentials.class);
        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                        creds.getUsername(),
                        creds.getPassword(),
                        Collections.emptyList()
                )
        );
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest req,
            HttpServletResponse res, FilterChain chain,
            Authentication auth) throws IOException, ServletException {
        TokenAuthenticationService
                .addAuthentication(res, auth.getName());
    }
}
