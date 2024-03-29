package com.epam.hospital.command.impl.hospitalisation;

import com.epam.hospital.command.CommandResult;
import com.epam.hospital.command.constant.Page;
import com.epam.hospital.service.HospitalisationService;
import com.epam.hospital.util.PdfUtil;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.ArrayList;

import static com.epam.hospital.TestData.*;
import static com.epam.hospital.command.constant.Parameter.LANGUAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ExportHospitalCardTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final HttpSession session = mock(HttpSession.class);
    private final HospitalisationService hospitalisationService = mock(HospitalisationService.class);
    private final ExportHospitalCardCommand command = new ExportHospitalCardCommand(hospitalisationService);

    @Test
    public void testExecute() throws Exception {
        when(request.getSession()).thenReturn(session);
        when(hospitalisationService.getAllHospitalisationsWithAppointments(getPatientUserTo()))
                .thenReturn(getHospitalisationsWithAppointments());
        when(request.getSession().getAttribute(LANGUAGE)).thenReturn(LOCALE);

        ServletOutputStream outputStream = mock(ServletOutputStream.class);
        when(response.getOutputStream()).thenReturn(outputStream);

        ServletContext servletContext = mock(ServletContext.class);
        try (MockedStatic<PdfUtil> utilities = Mockito.mockStatic(PdfUtil.class)) {
            utilities.when(() -> PdfUtil.getHospitalCardPdf(getPatientTo(), new ArrayList<>(), LOCALE, servletContext)).thenReturn(BYTES);

            CommandResult result = command.execute(request, response);
            assertEquals(new CommandResult(Page.HOSPITALISATIONS).getPage(), result.getPage());
        }
    }
}