package com.epam.hospital.command.impl.patient;

import com.epam.hospital.appcontext.ApplicationContext;
import com.epam.hospital.command.Command;
import com.epam.hospital.command.CommandResult;
import com.epam.hospital.exception.ApplicationException;
import com.epam.hospital.model.Patient;
import com.epam.hospital.model.User;
import com.epam.hospital.service.PatientService;
import com.epam.hospital.to.UserTo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.epam.hospital.command.constant.Command.PATIENTS_LIST;
import static com.epam.hospital.command.constant.Parameter.MESSAGE;
import static com.epam.hospital.command.constant.Parameter.USER;
import static com.epam.hospital.util.CommandUtil.getPageToRedirect;
import static com.epam.hospital.util.PasswordUtil.generatePassword;
import static com.epam.hospital.util.PatientUtil.createPatient;
import static com.epam.hospital.util.UserUtil.createUser;

public class SavePatientCommand implements Command {
    private static final Logger LOG = LoggerFactory.getLogger(SavePatientCommand.class);
    private final PatientService service;

    public SavePatientCommand(ApplicationContext applicationContext) {
        this.service = applicationContext.getPatientService();
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        UserTo user = (UserTo) session.getAttribute(USER);

        Patient newPatient = createPatient(request);
        String password = generatePassword();
        User newUser = createUser(request, password);
        try {
            service.savePatient(user, password, newUser, newPatient);
            return new CommandResult(getPageToRedirect(PATIENTS_LIST), true);
        } catch (ApplicationException e){
            LOG.error("Exception has occurred during executing save patient command, message = {}",
                    e.getMessage());
            request.setAttribute(MESSAGE, e.getType().getErrorMessage());
            return null;
        }
    }
}
