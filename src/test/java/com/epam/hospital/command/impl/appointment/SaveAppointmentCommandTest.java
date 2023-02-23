package com.epam.hospital.command.impl.appointment;

import com.epam.hospital.appcontext.ApplicationContext;
import com.epam.hospital.command.CommandResult;
import com.epam.hospital.model.Appointment;
import com.epam.hospital.service.AppointmentService;
import com.epam.hospital.TestData;
import com.epam.hospital.to.UserTo;
import com.epam.hospital.util.DateTimeUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static com.epam.hospital.command.constant.Command.APPOINTMENTS;
import static com.epam.hospital.command.constant.Parameter.*;
import static com.epam.hospital.command.constant.Parameter.PATIENT_ID;
import static com.epam.hospital.TestData.*;
import static com.epam.hospital.util.CommandUtil.getPageToRedirect;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SaveAppointmentCommandTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final HttpSession session = mock(HttpSession.class);
    private final AppointmentService service = mock(AppointmentService.class);
    private final ApplicationContext applicationContext = mock(ApplicationContext.class);

    @Test
    public void testExecute() {
        when(applicationContext.getAppointmentService()).thenReturn(service);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(USER)).thenReturn(getNurseUserTo());
        when(request.getParameter(STAFF_ID)).thenReturn(String.valueOf(NURSE_STAFF_ID));
        when(request.getParameter(PATIENT_ID)).thenReturn(String.valueOf(TestData.PATIENT_ID));
        when(request.getParameter(DATE_TIME)).thenReturn(DateTimeUtil.toString(APPOINTMENT_DATE_TIME));
        when(request.getParameter(TYPE)).thenReturn(APPOINTMENT_TYPE);
        when(request.getParameter(DESCRIPTION)).thenReturn(APPOINTMENT_DESCRIPTION);
        when(request.getParameter(STATUS)).thenReturn(APPOINTMENT_STATUS);

        CommandResult result = new SaveAppointmentCommand(applicationContext).execute(request, response);
        assertEquals(getPageToRedirect(APPOINTMENTS), result.getPage());
        Mockito.verify(service, Mockito.times(1)).saveAppointment(any(UserTo.class),any(Appointment.class));
    }
}
