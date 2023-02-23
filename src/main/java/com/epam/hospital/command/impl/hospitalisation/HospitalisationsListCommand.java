package com.epam.hospital.command.impl.hospitalisation;

import com.epam.hospital.appcontext.ApplicationContext;
import com.epam.hospital.command.Command;
import com.epam.hospital.command.CommandResult;
import com.epam.hospital.command.constant.Attribute;
import com.epam.hospital.command.constant.Page;
import com.epam.hospital.exception.ApplicationException;
import com.epam.hospital.service.HospitalisationService;
import com.epam.hospital.service.PatientService;
import com.epam.hospital.to.HospitalisationTo;
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
import static com.epam.hospital.command.constant.Parameter.DIRECTION;
import static com.epam.hospital.util.RequestUtil.*;

public class HospitalisationsListCommand implements Command {
    private static final Logger LOG = LoggerFactory.getLogger(HospitalisationsListCommand.class);
    private final HospitalisationService hospitalisationService;
    private final PatientService patientService;

    public HospitalisationsListCommand(ApplicationContext applicationContext) {
        this.hospitalisationService = applicationContext.getHospitalisationService();
        this.patientService = applicationContext.getPatientService();
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        UserTo user = (UserTo) session.getAttribute(USER);

        List<HospitalisationTo> hospitalisations = new ArrayList<>();
        int totalCount = 0;
        Map<String, Object> paginationAttributes = getPaginationAttributes(request);
        int offset = (int) paginationAttributes.get(OFFSET);
        int limit = (int) paginationAttributes.get(LIMIT);
        String orderBy = (String) paginationAttributes.get(ORDER_BY);
        String direction = (String) paginationAttributes.get(DIRECTION);
        try {
            PatientTo patient = patientService.getPatient(user);
            hospitalisations = hospitalisationService.getAllHospitalisationsOfPatient(patient.getId(),
                    offset, limit, orderBy, direction);
            totalCount = hospitalisationService.getAllHospitalisationsOfPatientCount(patient.getId());
        } catch (ApplicationException e) {
            LOG.error("Exception has occurred during executing hospitalisations list command, message = {}",
                    e.getType().getErrorMessage());
            request.setAttribute(MESSAGE, e.getType().getErrorMessage());
        }

        setRequestAttributes(request, new Attribute(HOSPITALISATIONS, hospitalisations),
                new Attribute(TOTAL_COUNT, totalCount), new Attribute(LIMIT, limit),
                new Attribute(OFFSET, offset), new Attribute(NUMBER_OF_PAGES, numberOfPages(totalCount, limit)),
                new Attribute(CURRENT_PAGE, paginationAttributes.get(CURRENT_PAGE)),
                new Attribute(ORDER_BY, orderBy), new Attribute(DIRECTION, direction));
        return new CommandResult(Page.HOSPITALISATIONS);
    }
}
