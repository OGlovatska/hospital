package com.epam.hospital.command.impl.appointment;

import com.epam.hospital.appcontext.ApplicationContext;
import com.epam.hospital.command.CommandResult;
import com.epam.hospital.command.constant.Page;
import com.epam.hospital.service.AppointmentService;
import com.epam.hospital.service.PatientService;
import com.epam.hospital.service.StaffService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;

import static com.epam.hospital.command.constant.Parameter.USER;
import static com.epam.hospital.TestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CreateAppointmentCommandTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final HttpSession session = mock(HttpSession.class);
    private final AppointmentService appointmentService = mock(AppointmentService.class);
    private final PatientService patientService = mock(PatientService.class);
    private final StaffService staffService = mock(StaffService.class);
    private final ApplicationContext applicationContext = mock(ApplicationContext.class);

    @Test
    public void testExecute() {
        when(applicationContext.getAppointmentService()).thenReturn(appointmentService);
        when(applicationContext.getPatientService()).thenReturn(patientService);
        when(applicationContext.getStaffService()).thenReturn(staffService);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(USER)).thenReturn(getDoctorUserTo());
        when(staffService.getStaff(getDoctorUserTo())).thenReturn(getStaffTo());
        CommandResult result = new CreateAppointmentCommand(applicationContext).execute(request, response);

        assertEquals(new CommandResult(Page.ADD_APPOINTMENT).getPage(), result.getPage());
    }
}
