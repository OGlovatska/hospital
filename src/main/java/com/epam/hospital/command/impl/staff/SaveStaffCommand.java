package com.epam.hospital.command.impl.staff;

import com.epam.hospital.appcontext.ApplicationContext;
import com.epam.hospital.command.Command;
import com.epam.hospital.command.CommandResult;
import com.epam.hospital.exception.ApplicationException;
import com.epam.hospital.model.Staff;
import com.epam.hospital.model.User;
import com.epam.hospital.service.StaffService;
import com.epam.hospital.to.UserTo;
import com.epam.hospital.util.PasswordUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.epam.hospital.command.constant.Command.STAFF_LIST;
import static com.epam.hospital.command.constant.Parameter.MESSAGE;
import static com.epam.hospital.command.constant.Parameter.USER;
import static com.epam.hospital.util.CommandUtil.getPageToRedirect;
import static com.epam.hospital.util.StaffUtil.createStaff;
import static com.epam.hospital.util.UserUtil.createUser;

public class SaveStaffCommand implements Command {
    private static final Logger LOG = LoggerFactory.getLogger(SaveStaffCommand.class);
    private final StaffService service;

    public SaveStaffCommand() {
        this.service = ApplicationContext.getInstance().getStaffService();
    }

    public SaveStaffCommand(StaffService service) {
        this.service = service;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        UserTo user = (UserTo) session.getAttribute(USER);

        Staff newStaff = createStaff(request);
        String password = PasswordUtil.generatePassword();
        User newUser = createUser(request, password);
        try {
            service.saveStaff(user, password, newUser, newStaff);
        } catch (ApplicationException e) {
            LOG.error("Exception has occurred during executing save staff command, message = {}",
                    e.getMessage());
            request.setAttribute(MESSAGE, e.getType().getErrorMessage());
        }
        return new CommandResult(getPageToRedirect(STAFF_LIST), true);
    }
}