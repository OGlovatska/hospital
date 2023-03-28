package com.epam.hospital.command.impl.patient;

import com.epam.hospital.appcontext.ApplicationContext;
import com.epam.hospital.command.Command;
import com.epam.hospital.command.CommandResult;
import com.epam.hospital.command.constant.Attribute;
import com.epam.hospital.command.constant.Page;
import com.epam.hospital.command.constant.Parameter;
import com.epam.hospital.exception.ApplicationException;
import com.epam.hospital.service.PatientService;
import com.epam.hospital.to.PatientTo;
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
import static com.epam.hospital.util.RequestUtil.*;

public class PatientsListCommand implements Command {
    private static final Logger LOG = LoggerFactory.getLogger(SavePatientCommand.class);
    private final PatientService service;

    public PatientsListCommand() {
        this.service = ApplicationContext.getInstance().getPatientService();
    }

    public PatientsListCommand(PatientService service) {
        this.service = service;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        UserTo user = (UserTo) session.getAttribute(USER);

        List<PatientTo> patients = new ArrayList<>();
        List<String> genders = new ArrayList<>();
        int totalCount = 0;
        Map<String, Object> paginationAttributes = getPaginationAttributes(request);
        try {
            Map<Integer, List<PatientTo>> patientTos = service.getAllPatients(user, (int) paginationAttributes.get(OFFSET),
                    (int) paginationAttributes.get(LIMIT), (String) paginationAttributes.get(ORDER_BY),
                    (String) paginationAttributes.get(DIRECTION));
            patients = patientTos.values().stream().findFirst().orElse(new ArrayList<>());
            totalCount = patientTos.keySet().stream().findFirst().orElse(0);
            genders = service.getAllGenders();
        } catch (ApplicationException e) {
            LOG.error("Exception has occurred during executing patients list command, message = {}",
                    e.getMessage());
            request.getSession().setAttribute(MESSAGE, e.getType().getErrorCode());
            request.getSession().setAttribute(IS_ERROR, true);
        }
        setRequestAttributes(request, patients, genders, totalCount, paginationAttributes);
        return new CommandResult(Page.PATIENTS);
    }

    private void setRequestAttributes(HttpServletRequest request, List<PatientTo> patients, List<String> genders, int totalCount, Map<String, Object> paginationAttributes) {
        request.setAttribute(PATIENTS, patients);
        request.setAttribute(GENDERS, genders);
        setPaginationAttributes(request, totalCount, (int) paginationAttributes.get(LIMIT),
                (int) paginationAttributes.get(OFFSET), (int) paginationAttributes.get(CURRENT_PAGE),
                (String) paginationAttributes.get(ORDER_BY), (String) paginationAttributes.get(DIRECTION));
    }
}
