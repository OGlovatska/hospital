package com.epam.hospital.command.impl.patient;

import com.epam.hospital.appcontext.ApplicationContext;
import com.epam.hospital.command.CommandResult;
import com.epam.hospital.service.PatientService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;

import static com.epam.hospital.command.constant.Command.PATIENTS_LIST;
import static com.epam.hospital.command.constant.Parameter.*;
import static com.epam.hospital.command.constant.Parameter.EMAIL;
import static com.epam.hospital.TestData.*;
import static com.epam.hospital.util.CommandUtil.getPageToRedirect;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class SavePatientCommandTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final HttpSession session = mock(HttpSession.class);
    private final PatientService service = mock(PatientService.class);
    private final SavePatientCommand command = new SavePatientCommand(service);

    @Test
    public void testExecute() {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(USER)).thenReturn(getAdminUserTo());
        when(request.getParameter(FIRST_NAME)).thenReturn(PATIENT_FIRST_NAME);
        when(request.getParameter(LAST_NAME)).thenReturn(PATIENT_LAST_NAME);
        when(request.getParameter(EMAIL)).thenReturn(PATIENT_EMAIL);
        when(request.getParameter(ROLE)).thenReturn(PATIENT_ROLE.name());
        when(request.getParameter(DATE_OF_BIRTH)).thenReturn(String.valueOf(PATIENT_DATE_OF_BIRTH));
        when(request.getParameter(GENDER)).thenReturn(String.valueOf(PATIENT_GENDER));

        CommandResult result = command.execute(request, response);
        assertEquals(getPageToRedirect(PATIENTS_LIST), result.getPage());
    }

    //@Test
    void savePatient(){
        /*Mockito.when(request.getParameter(FIRST_NAME)).thenReturn(PatientTestData.FIRST_NAME);
        Mockito.when(request.getParameter(LAST_NAME)).thenReturn(PatientTestData.LAST_NAME);
        Mockito.when(request.getParameter(EMAIL)).thenReturn(PatientTestData.EMAIL);
        Mockito.when(request.getParameter(ROLE)).thenReturn(PatientTestData.ROLE.name());
        Mockito.when(request.getParameter(DATE_OF_BIRTH)).thenReturn(String.valueOf(PatientTestData.DATE_OF_BIRTH));
        Mockito.when(request.getParameter(GENDER)).thenReturn(Gender.MALE.getName());*/

        /*try (MockedStatic<PatientUtil> utilities = Mockito.mockStatic(PatientUtil.class)) {
            utilities.when(() -> PatientUtil.createPatient(request))
                    .thenReturn(PatientTestData.getNew());

            System.out.println(PatientUtil.createPatient(request));
            System.out.println("################");
            System.out.println(PatientTestData.getNew());
            //PATIENT_MATCHER.assertMatch(PatientUtil.createPatient(request), PatientTestData.getNew());

            assertEquals(PatientUtil.createPatient(request), PatientTestData.getNew());
        }*/

    }
}
/*

  @Test
  public void testExecute() {
    Mockito.when(req.getParameter("name")).thenReturn("tarifName");
    Mockito.when(req.getParameter("price")).thenReturn("10");
    Mockito.when(req.getParameter("description")).thenReturn("tariffDescr");
    Mockito.when(req.getParameter("serviceId")).thenReturn("1");
    String result = cut.execute(req,resp);
    assertEquals(Path.COMMAND_REDIRECT, result);
    Mockito.verify(service, Mockito.times(1)).save(any(Tariff.class));

  }

@Test
void givenStaticMethodWithArgs_whenMocked_thenReturnsMockSuccessfully() {
    assertThat(StaticUtils.range(2, 6)).containsExactly(2, 3, 4, 5);

    try (MockedStatic<StaticUtils> utilities = Mockito.mockStatic(StaticUtils.class)) {
        utilities.when(() -> StaticUtils.range(2, 6))
          .thenReturn(Arrays.asList(10, 11, 12));

        assertThat(StaticUtils.range(2, 6)).containsExactly(10, 11, 12);
    }

    assertThat(StaticUtils.range(2, 6)).containsExactly(2, 3, 4, 5);
}
 */