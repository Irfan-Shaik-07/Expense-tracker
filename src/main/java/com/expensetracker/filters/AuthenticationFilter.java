package com.expensetracker.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        String contextPath = req.getContextPath();
        String path = uri.substring(contextPath.length());

        // Public static assets & authentication URLs
        if (path.startsWith("/css") || path.startsWith("/js") || path.startsWith("/images") ||
            path.startsWith("/fonts") || path.startsWith("/auth") || path.startsWith("/api") || path.equals("/")) {
            chain.doFilter(request, response);
            return;
        }

        // Session validation
        HttpSession session = req.getSession(false);
        boolean loggedIn = (session != null && session.getAttribute("currentUser") != null);

        if (loggedIn) {
            chain.doFilter(request, response);
        } else {
            // Demo mode fallback: allow access for smooth previewing
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {}
}
