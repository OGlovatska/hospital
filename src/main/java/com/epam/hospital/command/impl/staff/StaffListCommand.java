package com.epam.hospital.command.impl.staff;

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
import static com.epam.hospital.util.PaginationUtil.getPaginationAttributes;
import static com.epam.hospital.util.PaginationUtil.setPaginationAttributes;

public class StaffListCommand implements Command {
    private static final Logger LOG = LoggerFactory.getLogger(StaffListCommand.class);
    private final StaffService service = new StaffService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        UserTo user = (UserTo) session.getAttribute(USER);

        List<String> specialisations = new ArrayList<>();
        List<String> roles = new ArrayList<>();
        List<StaffTo> staff = new ArrayList<>();
        int totalCount = 0;
        Map<String, String> attributes = getPaginationAttributes(request);
        try {
            staff = service.getAllStaff(user, attributes.get(OFFSET),
                    attributes.get(LIMIT), attributes.get(ORDER_BY), attributes.get(DIRECTION));
            totalCount = service.getStaffCount();

            specialisations = service.getAllSpecialisations();
            roles = service.getStaffRoles();
        } catch (ApplicationException e) {
            e.printStackTrace();
        }

        request.setAttribute(STAFF, staff);
        request.setAttribute(SPECIALISATIONS, specialisations);
        request.setAttribute(ROLES, roles);
        setPaginationAttributes(request, totalCount, attributes.get(LIMIT), attributes.get(OFFSET),
                attributes.get(CURRENT_PAGE), attributes.get(ORDER_BY), attributes.get(DIRECTION));
        return new CommandResult(Page.STAFF);
    }
}