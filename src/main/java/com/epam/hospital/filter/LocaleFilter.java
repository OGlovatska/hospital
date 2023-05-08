package com.epam.hospital.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Stream;

import static com.epam.hospital.command.constant.Parameter.LANGUAGE;

@WebFilter(urlPatterns = "/*",
        initParams = @WebInitParam(name = "default", value = "en"))
public class LocaleFilter extends HttpFilter {
    private static final String CURRENT_PAGE = "Referer";
    private String defaultLocale;

    @Override
    public void init(FilterConfig config) {
        defaultLocale = config.getInitParameter("default");
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String locale = request.getParameter(LANGUAGE);
        if (locale != null && !locale.isEmpty()) {
            request.getSession().setAttribute(LANGUAGE, locale);
            response.addCookie(new Cookie(LANGUAGE, locale));
            response.sendRedirect(request.getHeader(CURRENT_PAGE));
        } else {
            String sessionLocale = (String) request.getSession().getAttribute(LANGUAGE);
            if (sessionLocale == null || sessionLocale.isEmpty()) {
                request.getSession().setAttribute(LANGUAGE, getLanguageFromCookies(request));
            }
        }
        chain.doFilter(request, response);
    }

    private String getLanguageFromCookies(HttpServletRequest request) {
        return Stream.ofNullable(request.getCookies())
                .flatMap(Arrays::stream)
                .filter(cookie -> cookie.getName().equals(LANGUAGE))
                .map(Cookie::getValue)
                .findAny().orElse(defaultLocale);
    }
}