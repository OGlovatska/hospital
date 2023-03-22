package com.epam.hospital.tag;

import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import static com.epam.hospital.tag.Constant.*;
import static com.epam.hospital.tag.Constant.PATIENTS;

public class FilterTag extends SimpleTagSupport {
    private String nameLimit;
    private String selectedLimit;
    private String nameOrderBy;
    private String selectedOrderBy;
    private String[] optionsOrderBy;
    private String nameDirection;
    private String selectedDirection;
    private String locale;

    public void setNameLimit(String nameLimit) {
        this.nameLimit = nameLimit;
    }

    public void setSelectedLimit(String selectedLimit) {
        this.selectedLimit = selectedLimit;
    }

    public void setNameOrderBy(String nameOrderBy) {
        this.nameOrderBy = nameOrderBy;
    }

    public void setSelectedOrderBy(String selectedOrderBy) {
        this.selectedOrderBy = selectedOrderBy;
    }

    public void setOptionsOrderBy(String optionsOrderBy) {
        this.optionsOrderBy = optionsOrderBy.split(", ");
    }

    public void setNameDirection(String nameDirection) {
        this.nameDirection = nameDirection;
    }

    public void setSelectedDirection(String selectedDirection) {
        this.selectedDirection = selectedDirection;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public void doTag() throws IOException {
        JspWriter out = getJspContext().getOut();
        writeLimit(out);
        writeOrderBy(out);
        writeDirection(out);
    }

    private void writeLimit(JspWriter out) throws IOException {
        out.write("<div class=\"col-auto\">\n");
        out.write("<select name=\"" + nameLimit + "\" aria-controls=\"example\" class=\"form-select form-select-sm\" onchange=\"submit()\">\n");
        generateLimitOption(out, "10");
        generateLimitOption(out, "25");
        generateLimitOption(out, "50");
        generateLimitOption(out, "100");
        out.write("</select>\n");
        out.write("</div>\n");
    }

    private void generateLimitOption(JspWriter out, String value) throws IOException {
        out.write("<option value=\"" + value + "\"");
        if (selectedLimit != null && selectedLimit.equals(value)) {
            out.write(" selected");
        }
        out.write(">" + value + "</option>\n");
    }

    private void writeOrderBy(JspWriter out) throws IOException {
        out.write("<div class=\"col-auto\">\n");
        out.write("<select name=\"" + nameOrderBy + "\" aria-controls=\"example\" class=\"form-select form-select-sm\" onchange=\"submit()\">\n");
        out.write("<option value=\"\" selected disabled>" + getDirectionBundleMessages().get(ORDER_BY) + "<fmt:message key=\"common.order.by\"/></option>");
        generateOrderByOption(out);
        out.write("</select>\n");
        out.write("</div>\n");
    }

    private void generateOrderByOption(JspWriter out) throws IOException {
        for (String value : optionsOrderBy){
            out.write("<option value=\"" + value + "\"");
            if (selectedOrderBy != null && selectedOrderBy.equals(value)) {
                out.write(" selected");
            }
            out.write(">" + getDirectionBundleMessages().get(value) + "</option>\n");
        }
    }

    private void writeDirection(JspWriter out) throws IOException {
        out.write("<div class=\"col-auto\">\n");
        out.write("<select name=\"" + nameDirection + "\" aria-controls=\"example\" class=\"form-select form-select-sm\" onchange=\"submit()\">\n");
        out.write("<option value=\"\" selected disabled>" + getDirectionBundleMessages().get(DIRECTION) + "</option>");
        generateDirectionOption(out, ASC);
        generateDirectionOption(out, DESC);
        out.write("</select>\n");
        out.write("</div>\n");
    }

    private void generateDirectionOption(JspWriter out, String value) throws IOException {
        out.write("<option value=\"" + value + "\"");
        if (selectedDirection != null && selectedDirection.equals(value)) {
            out.write(" selected");
        }
        out.write(">" + getDirectionBundleMessages().get(value) + "</option>\n");
    }

    private Map<String, String> getDirectionBundleMessages() {
        ResourceBundle bundle = ResourceBundle.getBundle("application", Locale.forLanguageTag(locale));

        Map<String, String> messages = new HashMap<>();
        messages.put(ORDER_BY, bundle.getString("common.order.by"));
        messages.put(ID, bundle.getString("common.default"));
        messages.put(DATE_TIME, bundle.getString("common.date"));
        messages.put(TYPE, bundle.getString("common.type"));
        messages.put(HOSPITALISATION_DATE, bundle.getString("hospitalisation.date"));
        messages.put(DISCHARGING_DATE, bundle.getString("hospitalisation.discharging.date"));
        messages.put(STATUS, bundle.getString("common.status"));
        messages.put(FIRST_NAME, bundle.getString("common.first.name"));
        messages.put(LAST_NAME, bundle.getString("common.last.name"));
        messages.put(SPECIALISATION, bundle.getString("staff.specialisation"));
        messages.put(DATE_OF_BIRTH, bundle.getString("patient.date.of.birth"));
        messages.put(PATIENTS, bundle.getString("staff.patients"));
        messages.put(DIRECTION, bundle.getString("common.direction"));
        messages.put(ASC, bundle.getString("common.ascending"));
        messages.put(DESC, bundle.getString("common.descending"));
        return messages;
    }
}