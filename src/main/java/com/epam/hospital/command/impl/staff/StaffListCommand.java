package com.epam.hospital.command.impl.staff;

import com.epam.hospital.appcontext.ApplicationContext;
import com.epam.hospital.command.Command;
import com.epam.hospital.command.CommandResult;
import com.epam.hospital.command.constant.Attribute;
import com.epam.hospital.command.constant.Page;
import com.epam.hospital.exception.ApplicationException;
import com.epam.hospital.service.StaffService;
import com.epam.hospital.to.StaffTo;
import com.epam.hospital.to.UserTo;
import com.epam.hospital.util.RequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.epam.hospital.command.constant.Parameter.*;
import static com.epam.hospital.util.RequestUtil.getPaginationAttributes;
import static com.epam.hospital.util.RequestUtil.setRequestAttributes;

public class StaffListCommand implements Command {
    private static final Logger LOG = LoggerFactory.getLogger(StaffListCommand.class);
    private final StaffService service;

    public StaffListCommand(ApplicationContext applicationContext) {
        this.service = applicationContext.getStaffService();
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        UserTo user = (UserTo) session.getAttribute(USER);

        List<String> specialisations = new ArrayList<>();
        List<String> roles = new ArrayList<>();
        List<StaffTo> staff = new ArrayList<>();
        int totalCount = 0;
        Map<String, Object> paginationAttributes = getPaginationAttributes(request);
        int offset = (int) paginationAttributes.get(OFFSET);
        int limit = (int) paginationAttributes.get(LIMIT);
        String orderBy = (String) paginationAttributes.get(ORDER_BY);
        String direction = (String) paginationAttributes.get(DIRECTION);
        try {
            staff = service.getAllStaff(user, offset, limit, orderBy, direction);
            totalCount = service.getStaffCount();
            specialisations = service.getAllSpecialisations();
            roles = service.getStaffRoles();
        } catch (ApplicationException e) {
            LOG.error("Exception has occurred during executing patients list command, message = {}",
                    e.getMessage());
            request.setAttribute(MESSAGE, e.getType().getErrorMessage());
        }

        setRequestAttributes(request, new Attribute(TOTAL_COUNT, totalCount), new Attribute(LIMIT, limit),
                new Attribute(OFFSET, offset), new Attribute(ORDER_BY, orderBy), new Attribute(DIRECTION, direction),
                new Attribute(STAFF, staff), new Attribute(SPECIALISATIONS, specialisations), new Attribute(ROLES, roles));
        return new CommandResult(Page.STAFF);
    }
}