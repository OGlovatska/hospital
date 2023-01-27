package com.epam.hospital.command.impl.staff;

import com.epam.hospital.command.Command;
import com.epam.hospital.command.CommandResult;
import com.epam.hospital.exception.ApplicationException;
import com.epam.hospital.model.Staff;
import com.epam.hospital.model.User;
import com.epam.hospital.service.StaffService;
import com.epam.hospital.util.PasswordUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.epam.hospital.command.constant.Command.STAFF_LIST;
import static com.epam.hospital.util.CommandUtil.getPageToRedirect;
import static com.epam.hospital.util.StaffUtil.createStaff;
import static com.epam.hospital.util.UserUtil.createUser;

public class SaveStaffCommand implements Command {
    private static final Logger LOG = LoggerFactory.getLogger(SaveStaffCommand.class);
    private final StaffService service = new StaffService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        Staff staff = createStaff(request);
        String password = PasswordUtil.generatePassword();
        User user = createUser(request, password);
        try {
            service.saveStaff(password, user, staff);
        } catch (ApplicationException e){
            e.printStackTrace();
        }
        return new CommandResult(getPageToRedirect(STAFF_LIST), true);
    }
}