package zoowebapp;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

@Configuration
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final String ADMIN_PAGE = "/admin";
    private final String USER_PAGE = "/user";

    @Getter
    @Setter
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        handle(httpServletRequest, httpServletResponse, authentication);
        clearAuthenticationAttribute(httpServletRequest);
    }

    private void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String targetUrl = determinateTargetUrl(authentication);
        if (response.isCommitted()) return;
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    private String determinateTargetUrl(Authentication authentication) {
        boolean isAdmin = false;
        boolean isUser = false;

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals("ROLE_ADMIN")) {
                isAdmin = true;
                break;
            } else if (authority.getAuthority().equals("ROLE_USER")) {
                isUser = true;
                break;
            }
        }

        if (isAdmin) {
//            return ADMIN_PAGE;
            return "/";
        } else if (isUser) {
//            return USER_PAGE;
            return "/";
        }

        return null;
    }

    private void clearAuthenticationAttribute(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) return;
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}