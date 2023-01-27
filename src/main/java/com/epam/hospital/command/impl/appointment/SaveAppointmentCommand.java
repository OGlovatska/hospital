package com.epam.hospital.command.impl.appointment;

import com.epam.hospital.command.Command;
import com.epam.hospital.command.CommandResult;
import com.epam.hospital.exception.ApplicationException;
import com.epam.hospital.model.Appointment;
import com.epam.hospital.service.AppointmentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.epam.hospital.command.constant.Command.APPOINTMENTS;
import static com.epam.hospital.command.constant.Parameter.STAFF_ID;
import static com.epam.hospital.command.constant.Parameter.getParameter;
import static com.epam.hospital.util.AppointmentUtil.createAppointment;
import static com.epam.hospital.util.CommandUtil.getPageToRedirect;

public class SaveAppointmentCommand implements Command {
    private static final Logger LOG = LoggerFactory.getLogger(SaveAppointmentCommand.class);
    private final AppointmentService service = new AppointmentService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        int staffId = Integer.parseInt(request.getParameter(STAFF_ID));

        Appointment appointment = createAppointment(request);
        try {
            service.saveAppointment(appointment);
        } catch (ApplicationException e){
            e.printStackTrace();
        }

        return new CommandResult(getPageToRedirect(APPOINTMENTS,
                getParameter(STAFF_ID, String.valueOf(staffId))), true);
    }
}
