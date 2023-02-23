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

    public PatientsListCommand(ApplicationContext applicationContext) {
        this.service = applicationContext.getPatientService();
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        UserTo user = (UserTo) session.getAttribute(USER);

        List<PatientTo> patients = new ArrayList<>();
        List<String> genders = new ArrayList<>();
        int totalCount = 0;
        Map<String, Object> paginationAttributes = getPaginationAttributes(request);
        int offset = (int) paginationAttributes.get(OFFSET);
        int limit = (int) paginationAttributes.get(LIMIT);
        String orderBy = (String) paginationAttributes.get(ORDER_BY);
        String direction = (String) paginationAttributes.get(DIRECTION);
        try {
            Map<Integer, List<PatientTo>> patientTos = service.getAllPatients(user, offset, limit, orderBy, direction);
            patients = patientTos.values().stream().findFirst().orElse(new ArrayList<>());
            totalCount = patientTos.keySet().stream().findFirst().orElse(0);
            genders = service.getAllGenders();
        } catch (ApplicationException e) {
            LOG.error("Exception has occurred during executing patients list command, message = {}",
                    e.getMessage());
            request.setAttribute(MESSAGE, e.getType().getErrorMessage());
        }

        setRequestAttributes(request, new Attribute(TOTAL_COUNT, totalCount), new Attribute(LIMIT, limit),
                new Attribute(NUMBER_OF_PAGES, numberOfPages(totalCount, limit)), new Attribute(ORDER_BY, orderBy),
                new Attribute(CURRENT_PAGE, paginationAttributes.get(CURRENT_PAGE)), new Attribute(OFFSET, offset),
                new Attribute(DIRECTION, direction), new Attribute(PATIENTS, patients), new Attribute(GENDERS, genders));
        return new CommandResult(Page.PATIENTS);
    }
}
