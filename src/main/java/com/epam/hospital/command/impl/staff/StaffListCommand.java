package com.epam.hospital.command.impl.staff;

import com.epam.hospital.appcontext.ApplicationContext;
import com.epam.hospital.command.Command;
import com.epam.hospital.command.CommandResult;
import com.epam.hospital.command.constant.Page;
import com.epam.hospital.exception.ApplicationException;
import com.epam.hospital.service.StaffService;
import com.epam.hospital.to.StaffTo;
import com.epam.hospital.to.UserTo;
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
import static com.epam.hospital.util.RequestUtil.setPaginationAttributes;

public class StaffListCommand implements Command {
    private static final Logger LOG = LoggerFactory.getLogger(StaffListCommand.class);
    private final StaffService service;

    public StaffListCommand() {
        this.service = ApplicationContext.getInstance().getStaffService();
    }

    public StaffListCommand(StaffService service) {
        this.service = service;
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
        try {
            staff = service.getAllStaff(user, (int) paginationAttributes.get(OFFSET), (int) paginationAttributes.get(LIMIT),
                    (String) paginationAttributes.get(ORDER_BY), (String) paginationAttributes.get(DIRECTION));
            totalCount = service.getStaffCount();
            specialisations = service.getAllSpecialisations();
            roles = service.getStaffRoles();
        } catch (ApplicationException e) {
            LOG.error("Exception has occurred during executing patients list command, message = {}",
                    e.getMessage());
            request.getSession().setAttribute(MESSAGE, e.getType().getErrorCode());
            request.getSession().setAttribute(IS_ERROR, true);
        }

        setRequestAttributes(request, specialisations, roles, staff, totalCount, paginationAttributes);
        return new CommandResult(Page.STAFF);
    }

    private void setRequestAttributes(HttpServletRequest request, List<String> specialisations, List<String> roles,
                                      List<StaffTo> staff, int totalCount, Map<String, Object> paginationAttributes) {
        setPaginationAttributes(request, totalCount, (int) paginationAttributes.get(LIMIT),
                (int) paginationAttributes.get(OFFSET), (int) paginationAttributes.get(CURRENT_PAGE),
                (String) paginationAttributes.get(ORDER_BY), (String) paginationAttributes.get(DIRECTION));
        request.setAttribute(STAFF, staff);
        request.setAttribute(SPECIALISATIONS, specialisations);
        request.setAttribute(ROLES, roles);
    }
}