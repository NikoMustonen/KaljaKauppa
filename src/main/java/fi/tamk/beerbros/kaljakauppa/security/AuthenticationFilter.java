package fi.tamk.beerbros.kaljakauppa.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

/**
 * Filters authentication attempts.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version %I%, %G%
 * @since 1.7
 */
public class AuthenticationFilter
        extends GenericFilterBean {

    /**
     * Filters authentication.
     *
     * @param req Http request trying authentication.
     * @param res If authentication succesfull add token to response.
     * @param fc FilterChain object.
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(
            ServletRequest req, ServletResponse res, FilterChain fc)
            throws IOException, ServletException {

        Authentication a = TokenAuthentication.getAuthentication(
                (HttpServletRequest) req);

        SecurityContextHolder.getContext().setAuthentication(a);
        fc.doFilter(req, res);
    }
}
