package com.epam.hospital.command.impl.hospitalisation;

import com.epam.hospital.appcontext.ApplicationContext;
import com.epam.hospital.command.Command;
import com.epam.hospital.command.CommandResult;
import com.epam.hospital.command.constant.Page;
import com.epam.hospital.exception.ApplicationException;
import com.epam.hospital.service.AppointmentService;
import com.epam.hospital.service.HospitalisationService;
import com.epam.hospital.to.AppointmentTo;
import com.epam.hospital.to.HospitalisationTo;
import com.epam.hospital.to.UserTo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

import static com.epam.hospital.command.constant.Parameter.*;
import static com.epam.hospital.util.RequestUtil.getPaginationAttributes;
import static com.epam.hospital.util.RequestUtil.setPaginationAttributes;

public class HospitalisationDetailsCommand implements Command {
    private static final Logger LOG = LoggerFactory.getLogger(HospitalisationDetailsCommand.class);
    private final HospitalisationService hospitalisationService;
    private final AppointmentService appointmentService;

    public HospitalisationDetailsCommand() {
        this.hospitalisationService = ApplicationContext.getInstance().getHospitalisationService();
        this.appointmentService = ApplicationContext.getInstance().getAppointmentService();
    }

    public HospitalisationDetailsCommand(HospitalisationService hospitalisationService, AppointmentService appointmentService) {
        this.hospitalisationService = hospitalisationService;
        this.appointmentService = appointmentService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        UserTo user = (UserTo) session.getAttribute(USER);

        int hospitalisationId = Integer.parseInt(request.getParameter(HOSPITALISATION_ID));
        request.setAttribute(HOSPITALISATION_ID, hospitalisationId);

        Map<String, Object> paginationAttributes = getPaginationAttributes(request);
        HospitalisationTo hospitalisation = null;
        int totalCount = 0;
        List<AppointmentTo> appointments = null;
        try {
            hospitalisation = hospitalisationService.getHospitalisation(hospitalisationId);
            Map<Integer, List<AppointmentTo>> appointmentsTos = appointmentService
                    .getAllAppointments(user, hospitalisationId, (int) paginationAttributes.get(OFFSET), (int) paginationAttributes.get(LIMIT),
                            (String) paginationAttributes.get(ORDER_BY), (String) paginationAttributes.get(DIRECTION));
            appointments = appointmentsTos.values().stream().findFirst().orElse(null);
            totalCount = appointmentsTos.keySet().stream().findFirst().orElse(0);
        } catch (ApplicationException e) {
            LOG.error("Exception has occurred during executing hospitalisation details command, message = {}",
                    e.getMessage());
            request.setAttribute(MESSAGE, e.getType().getErrorMessage());
        }
        setRequestAttributes(request, paginationAttributes, hospitalisation, totalCount, appointments);
        return new CommandResult(Page.HOSPITALISATION_DETAILS);
    }

    private void setRequestAttributes(HttpServletRequest request, Map<String, Object> paginationAttributes, HospitalisationTo hospitalisation, int totalCount, List<AppointmentTo> appointments) {
        request.setAttribute(HOSPITALISATION, hospitalisation);
        request.setAttribute(APPOINTMENTS, appointments);
        setPaginationAttributes(request, totalCount, (int) paginationAttributes.get(LIMIT), (int) paginationAttributes.get(OFFSET),
                (int) paginationAttributes.get(CURRENT_PAGE), (String) paginationAttributes.get(ORDER_BY),
                (String) paginationAttributes.get(DIRECTION));
    }
}
