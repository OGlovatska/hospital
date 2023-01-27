package com.epam.hospital.util;

import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

import static com.epam.hospital.command.constant.Parameter.*;
import static com.epam.hospital.command.constant.Parameter.DIRECTION;
import static com.epam.hospital.util.ValidationUtil.validateCurrentPageValue;
import static com.epam.hospital.util.ValidationUtil.validateLimitValue;

public class PaginationUtil {

    public static int numberOfPages(int totalCount, int limit) {
        int numberOfPages = totalCount / limit;
        if (totalCount % limit > 0) {
            numberOfPages++;
        }
        return numberOfPages;
    }

    public static Map<String, String> getPaginationAttributes(HttpServletRequest request){
        Map<String, String> attributes = new HashMap<>();
        int page = validateCurrentPageValue(request.getParameter(CURRENT_PAGE));
        int limit = validateLimitValue(request.getParameter(LIMIT));
        int offset = page * limit - limit;
        String orderBy = request.getParameter(ORDER_BY);
        String direction = request.getParameter(DIRECTION);

        attributes.put(CURRENT_PAGE, String.valueOf(page));
        attributes.put(LIMIT, String.valueOf(limit));
        attributes.put(OFFSET, String.valueOf(offset));
        attributes.put(ORDER_BY, orderBy);
        attributes.put(DIRECTION, direction);
        return attributes;
    }


    public static void setPaginationAttributes(HttpServletRequest request, int totalCount, String limit, String offset,
                                               String page, String orderBy, String direction) {
        request.setAttribute(TOTAL_COUNT, totalCount);
        request.setAttribute(NUMBER_OF_PAGES, numberOfPages(totalCount, Integer.parseInt(limit)));
        request.setAttribute(CURRENT_PAGE, page);
        request.setAttribute(OFFSET, offset);
        request.setAttribute(LIMIT, limit);
        request.setAttribute(ORDER_BY, orderBy);
        request.setAttribute(DIRECTION, direction);
    }

    private PaginationUtil() {
    }
}
