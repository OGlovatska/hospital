package com.epam.hospital.tag;

import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;
import jakarta.servlet.jsp.tagext.TagSupport;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import static com.epam.hospital.tag.Constant.*;

public class PaginationTag extends SimpleTagSupport {
    private int offsetValue;
    private String limitName = "limit";
    private int limitValue;
    private String orderByName = "orderBy";
    private String orderByValue;
    private String dirName = "dir";
    private String dirValue;
    private String pageName = "page";
    private int pageValue;
    private int numberOfPagesValue;
    private int totalCountValue;
    private String api;
    private int listSize;
    private String locale;

    public void setOffsetValue(int offsetValue) {
        this.offsetValue = offsetValue;
    }

    public void setLimitName(String limitName) {
        this.limitName = limitName;
    }

    public void setLimitValue(int limitValue) {
        this.limitValue = limitValue;
    }

    public void setOrderByName(String orderByName) {
        this.orderByName = orderByName;
    }

    public void setOrderByValue(String orderByValue) {
        this.orderByValue = orderByValue;
    }

    public void setDirName(String dirName) {
        this.dirName = dirName;
    }

    public void setDirValue(String dirValue) {
        this.dirValue = dirValue;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public void setPageValue(int pageValue) {
        this.pageValue = pageValue;
    }

    public void setNumberOfPagesValue(int numberOfPagesValue) {
        this.numberOfPagesValue = numberOfPagesValue;
    }

    public void setTotalCountValue(int totalCountValue) {
        this.totalCountValue = totalCountValue;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public void setListSize(int listSize) {
        this.listSize = listSize;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    @Override
    public void doTag() throws IOException {
        JspWriter out = getJspContext().getOut();
        Map<String, String> bundleMessages = getPaginationBundleMessages();

        try {
            out.write("<div class=\"row\">");
            out.write("<div class=\"col-sm-12 col-md-5\">");
            out.write("<div class=\"dataTables_info\" role=\"status\" aria-live=\"polite\">");
            out.write(bundleMessages.get(SHOWING) + " ");
            if (listSize == 0) {
                out.write("0 " + bundleMessages.get(ENTRIES));
            } else {
                out.write(bundleMessages.get(FROM) + " ");
                out.write(String.valueOf(offsetValue + 1));
                out.write(" " + bundleMessages.get(TO) + " ");

                if (listSize < limitValue) {
                    out.write(String.valueOf(totalCountValue));
                } else {
                    out.write(String.valueOf(offsetValue + limitValue));
                }

                out.write(" " + bundleMessages.get(OF) + " ");
                out.write(String.valueOf(totalCountValue));
                out.write(" " + bundleMessages.get(ENTRIES));
            }

            out.write("</div>");
            out.write("</div>");
            out.write("<div class=\"col-sm-12 col-md-7\">");
            out.write("<div class=\"dataTables_paginate paging_simple_numbers\">");
            out.write("<ul class=\"pagination\">");

            if (totalCountValue > limitValue) {
                if (pageValue > 1) {
                    out.write("<li class=\"paginate_button page-item previous\" id=\"example_previous\">");
                    out.write("<a href=");
                    out.write(api);
                    out.write("&" + pageName + "=");
                    out.write(String.valueOf(pageValue - 1));
                    out.write("&" + limitName + "=");
                    out.write(String.valueOf(limitValue));
                    out.write("&" + orderByName + "=");
                    out.write(orderByValue);
                    out.write("&" + dirName + "=");
                    out.write(dirValue);
                    out.write("\" aria-controls=\"example\" data-dt-idx=\"previous\" tabindex=\"0\" class=\"page-link\">");
                    out.write(bundleMessages.get(PREVIOUS));
                    out.write("</a></li>");
                } else {
                    out.write("<li class=\"paginate_button page-item previous disabled\" id=\"example_previous\">");
                    out.write("<a href=\"#\" aria-controls=\"example\" data-dt-idx=\"previous\" tabindex=\"0\" class=\"page-link\">");
                    out.write(bundleMessages.get(PREVIOUS));
                    out.write("</a></li>");
                }

                for (int i = 1; i <= numberOfPagesValue; i++) {
                    if (i == pageValue) {
                        out.write("<li class=\"paginate_button page-item active\">");
                        out.write("<a href=\"#\" aria-controls=\"example\" data-dt-idx=\"1\" tabindex=\"0\" class=\"page-link\">");
                        out.write(String.valueOf(i));
                        out.write("</a></li>");
                    } else {
                        out.write("<li class=\"paginate_button page-item\">");
                        out.write("<a href=");
                        out.write(api);
                        out.write("&" + pageName + "=");
                        out.write(String.valueOf(i));
                        out.write("&" + limitName + "=");
                        out.write(String.valueOf(limitValue));
                        out.write("&" + orderByName + "=");
                        out.write(orderByValue);
                        out.write("&" + dirName + "=");
                        out.write(dirValue);
                        out.write("\" aria-controls=\"example\" data-dt-idx=\"1\" tabindex=\"0\" class=\"page-link\">");
                        out.write(String.valueOf(i));
                        out.write("</a></li>");
                    }
                }

                if (pageValue < numberOfPagesValue) {
                    out.write("<li class=\"paginate_button page-item next\" id=\"example_next\">");
                    out.write("<a href=");
                    out.write(api);
                    out.write("&" + pageName + "=");
                    out.write(String.valueOf(pageValue + 1));
                    out.write("&" + limitName + "=");
                    out.write(String.valueOf(limitValue));
                    out.write("&" + orderByName + "=");
                    out.write(orderByValue);
                    out.write("&" + dirName + "=");
                    out.write(dirValue);
                    out.write("\" aria-controls=\"example\" data-dt-idx=\"next\" tabindex=\"0\" class=\"page-link\">");
                    out.write(bundleMessages.get(NEXT));
                    out.write("</a></li>");
                } else {
                    out.write("<li class=\"paginate_button page-item next disabled\" id=\"example_next\">");
                    out.write("<a href=\"#\" aria-controls=\"example\" data-dt-idx=\"next\" tabindex=\"0\" class=\"page-link\">");
                    out.write(bundleMessages.get(NEXT));
                    out.write("</a></li>");
                }
            }

            out.write("</ul>");
            out.write("</div>");
            out.write("</div>");
            out.write("</div>");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Map<String, String> getPaginationBundleMessages() {
        ResourceBundle bundle = ResourceBundle.getBundle("application", Locale.forLanguageTag(locale));

        Map<String, String> messages = new HashMap<>();
        messages.put(SHOWING, bundle.getString("pagination.showing"));
        messages.put(ENTRIES, bundle.getString("pagination.entries"));
        messages.put(FROM, bundle.getString("pagination.from"));
        messages.put(TO, bundle.getString("pagination.to"));
        messages.put(OF, bundle.getString("pagination.of"));
        messages.put(PREVIOUS, bundle.getString("pagination.previous"));
        messages.put(NEXT, bundle.getString("pagination.next"));
        return messages;
    }
}