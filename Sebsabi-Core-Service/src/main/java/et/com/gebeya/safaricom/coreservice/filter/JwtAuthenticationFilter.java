package et.com.gebeya.safaricom.coreservice.filter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Component
@Slf4j
public class AuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Retrieve authentication object from SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            // Extract username and roles from authentication object
            String username = authentication.getName();
            String[] roles = authentication.getAuthorities().stream()
                    .map(Object::toString)
                    .toArray(String[]::new);

            // Log the extracted roles
            log.info("User '{}' has roles: {}", username, Arrays.toString(roles));

            // Perform authorization based on user details
            String requestURI = request.getRequestURI();
            log.info("Request URI: {}", requestURI);

            if (requestURI.startsWith("/api/core/client/") && containsRole(roles, "CLIENT")) {
                // Allow access for CLIENT to routes under /api/core/client/**
                filterChain.doFilter(request, response);
                return;
            } else if (requestURI.startsWith("/api/core/gig-worker/") && containsRole(roles, "Gigworker")) {
                // Allow access for Gigworker to routes under /api/core/gig-worker/**
                filterChain.doFilter(request, response);
                return;
            } else if (requestURI.startsWith("/api/core/admin") && containsRole(roles, "ADMIN")) {
                // Allow access for ADMIN to routes under /api/core/**
                filterChain.doFilter(request, response);
                return;
            }
        }

        // If user information is missing or unauthorized, deny access
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }

    // Helper method to check if roles array contains a specific role
    private boolean containsRole(String[] roles, String roleToCheck) {
        for (String role : roles) {
            if (roleToCheck.equals(role)) {
                return true;
            }
        }
        return false;
    }
}
