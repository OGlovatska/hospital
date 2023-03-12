package com.epam.hospital.util;

import com.epam.hospital.command.constant.Attribute;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

import static com.epam.hospital.command.constant.Parameter.*;
import static com.epam.hospital.command.constant.Parameter.DIRECTION;
import static com.epam.hospital.util.ValidationUtil.*;

public class RequestUtil {

    public static int numberOfPages(int totalCount, int limit) {
        int numberOfPages = totalCount / limit;
        if (totalCount % limit > 0) {
            numberOfPages++;
        }
        return numberOfPages;
    }

    public static Map<String, Object> getPaginationAttributes(HttpServletRequest request) {
        Map<String, Object> attributes = new HashMap<>();
        int page = validateCurrentPageValue(request.getParameter(CURRENT_PAGE));
        int limit = validateLimitValue(request.getParameter(LIMIT));
        int offset = page * limit - limit;
        String orderBy = validateOrderByValue(request.getParameter(ORDER_BY));
        String direction = validateDirectionValue(request.getParameter(DIRECTION));

        attributes.put(CURRENT_PAGE, page);
        attributes.put(LIMIT, limit);
        attributes.put(OFFSET, offset);
        attributes.put(ORDER_BY, orderBy);
        attributes.put(DIRECTION, direction);
        return attributes;
    }

    public static Map<String, Object> getPaginationAttributes(String page, String limit, String orderBy, String direction) {
        Map<String, Object> attributes = new HashMap<>();
        int validatedPage = validateCurrentPageValue(page);
        int validatedLimit = validateLimitValue(limit);
        int offset = validatedPage * validatedPage - validatedPage;

        attributes.put(CURRENT_PAGE, validatedPage);
        attributes.put(LIMIT, validatedLimit);
        attributes.put(OFFSET, offset);
        attributes.put(ORDER_BY, validateOrderByValue(orderBy));
        attributes.put(DIRECTION, validateDirectionValue(direction));
        return attributes;
    }

    public static void setPaginationAttributes(HttpServletRequest request, int totalCount, int limit, int offset,
                                               int page, String orderBy, String direction) {
        request.setAttribute(TOTAL_COUNT, totalCount);
        request.setAttribute(NUMBER_OF_PAGES, numberOfPages(totalCount, limit));
        request.setAttribute(CURRENT_PAGE, page);
        request.setAttribute(OFFSET, offset);
        request.setAttribute(LIMIT, limit);
        request.setAttribute(ORDER_BY, orderBy);
        request.setAttribute(DIRECTION, direction);
    }

    public static void setRequestAttributes(HttpServletRequest request, Attribute... attributes) {
        for (Attribute attribute : attributes) {
            request.setAttribute(attribute.getName(), attribute.getValue());
        }
    }

    private RequestUtil() {
    }
}
