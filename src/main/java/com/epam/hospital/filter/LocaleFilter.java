package com.epam.hospital.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static com.epam.hospital.command.constant.Parameter.LANGUAGE;

@WebFilter(urlPatterns = "/*",
        initParams =  @WebInitParam(name = "default", value = "en"))
public class LocaleFilter implements Filter{
    private static final Logger LOG = LoggerFactory.getLogger(LocaleFilter.class);
    private static final String CURRENT_PAGE = "Referer";
    private String defaultLocale;

    @Override
    public void init(FilterConfig config) {
        defaultLocale = config.getInitParameter("default");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String locale = httpRequest.getParameter(LANGUAGE);
        if (locale != null && !locale.isEmpty()) {
            httpRequest.getSession().setAttribute(LANGUAGE, locale);
            ((HttpServletResponse) response).sendRedirect(httpRequest.getHeader(CURRENT_PAGE));
        } else {
            String sessionLocale = (String) httpRequest.getSession().getAttribute(LANGUAGE);
            if (sessionLocale == null || sessionLocale.isEmpty()) {
                httpRequest.getSession().setAttribute(LANGUAGE, defaultLocale);
            }
        }
        chain.doFilter(request, response);
    }
}
