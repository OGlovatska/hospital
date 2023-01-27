package com.epam.hospital.command.impl.patient;

import com.epam.hospital.command.Command;
import com.epam.hospital.command.CommandResult;
import com.epam.hospital.command.constant.Page;
import com.epam.hospital.exception.ApplicationException;
import com.epam.hospital.service.PatientService;
import com.epam.hospital.to.PatientTo;
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

public class PatientsListCommand implements Command {
    private static final Logger LOG = LoggerFactory.getLogger(SavePatientCommand.class);
    private final PatientService service = new PatientService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        UserTo user = (UserTo) session.getAttribute(USER);

        List<PatientTo> patients = new ArrayList<>();
        List<String> genders = new ArrayList<>();
        int totalCount = 0;
        Map<String, String> attributes = getPaginationAttributes(request);
        try {
            Map<Integer, List<PatientTo>> patientTos = service.getAllPatients(user, attributes.get(OFFSET),
                    attributes.get(LIMIT), attributes.get(ORDER_BY), attributes.get(DIRECTION));
            patients = patientTos.values().stream().findFirst().orElseThrow();
            totalCount = patientTos.keySet().stream().findFirst().orElseThrow();
            genders = service.getAllGenders();
        } catch (ApplicationException e) {
            e.printStackTrace();
        }

        request.setAttribute(PATIENTS, patients);
        request.setAttribute(GENDERS, genders);
        setPaginationAttributes(request, totalCount, attributes.get(LIMIT), attributes.get(OFFSET),
                attributes.get(CURRENT_PAGE), attributes.get(ORDER_BY), attributes.get(DIRECTION));
        return new CommandResult(Page.PATIENTS);
    }
}
