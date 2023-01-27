package com.epam.hospital.command.impl.patient;

import com.epam.hospital.command.Command;
import com.epam.hospital.command.CommandResult;
import com.epam.hospital.exception.ApplicationException;
import com.epam.hospital.model.Patient;
import com.epam.hospital.model.User;
import com.epam.hospital.service.PatientService;
import com.epam.hospital.util.PasswordUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.epam.hospital.command.constant.Command.PATIENTS_LIST;
import static com.epam.hospital.command.constant.Parameter.DATE_OF_BIRTH;
import static com.epam.hospital.command.constant.Parameter.ROLE;
import static com.epam.hospital.util.CommandUtil.getPageToRedirect;
import static com.epam.hospital.util.PatientUtil.createPatient;
import static com.epam.hospital.util.UserUtil.createUser;

public class SavePatientCommand implements Command {
    private static final Logger LOG = LoggerFactory.getLogger(SavePatientCommand.class);
    private final PatientService service = new PatientService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        Patient patient = createPatient(request);
        String password = PasswordUtil.generatePassword();
        User user = createUser(request, password);
        try {
            service.savePatient(password, user, patient);
        } catch (ApplicationException e){
            e.printStackTrace();
        }
        return new CommandResult(getPageToRedirect(PATIENTS_LIST), true);
    }
}
