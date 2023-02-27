package com.epam.hospital.command.impl.hospitalisation;

import com.epam.hospital.appcontext.ApplicationContext;
import com.epam.hospital.command.Command;
import com.epam.hospital.command.CommandResult;
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

    public HospitalisationsListCommand() {
        this.hospitalisationService = ApplicationContext.getInstance().getHospitalisationService();
        this.patientService = ApplicationContext.getInstance().getPatientService();
    }

    public HospitalisationsListCommand(HospitalisationService hospitalisationService, PatientService patientService) {
        this.hospitalisationService = hospitalisationService;
        this.patientService = patientService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        UserTo user = (UserTo) session.getAttribute(USER);

        List<HospitalisationTo> hospitalisations = new ArrayList<>();
        int totalCount = 0;
        Map<String, Object> paginationAttributes = getPaginationAttributes(request);
        try {
            PatientTo patient = patientService.getPatient(user);
            hospitalisations = hospitalisationService.getAllHospitalisationsOfPatient(patient.getId(),
                    (int) paginationAttributes.get(OFFSET), (int) paginationAttributes.get(LIMIT),
                    (String) paginationAttributes.get(ORDER_BY), (String) paginationAttributes.get(DIRECTION));
            totalCount = hospitalisationService.getAllHospitalisationsOfPatientCount(patient.getId());
        } catch (ApplicationException e) {
            LOG.error("Exception has occurred during executing hospitalisations list command, message = {}",
                    e.getMessage());
            request.setAttribute(MESSAGE, e.getType().getErrorMessage());
        }
        setRequestAttributes(request, hospitalisations, totalCount, paginationAttributes);
        return new CommandResult(Page.HOSPITALISATIONS);
    }

    private void setRequestAttributes(HttpServletRequest request, List<HospitalisationTo> hospitalisations, int totalCount, Map<String, Object> paginationAttributes) {
        request.setAttribute(HOSPITALISATIONS, hospitalisations);
        setPaginationAttributes(request, totalCount, (int) paginationAttributes.get(LIMIT),
                (int) paginationAttributes.get(OFFSET), (int) paginationAttributes.get(CURRENT_PAGE),
                (String) paginationAttributes.get(ORDER_BY), (String) paginationAttributes.get(DIRECTION));
    }
}
