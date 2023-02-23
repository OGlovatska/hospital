package com.epam.hospital.command;

import com.epam.hospital.appcontext.ApplicationContext;
import com.epam.hospital.command.impl.appointment.*;
import com.epam.hospital.command.impl.hospitalisation.*;
import com.epam.hospital.command.impl.patient.*;
import com.epam.hospital.command.impl.staff.*;
import com.epam.hospital.command.impl.user.*;

import java.util.HashMap;
import java.util.Map;

import static com.epam.hospital.command.constant.Command.*;

public class CommandFactory {
    private static final Map<String, Command> COMMAND_MAP = new HashMap<>();

    private CommandFactory() {
    }

    private static final ApplicationContext APPLICATION_CONTEXT = ApplicationContext.getInstance();

    static {
        COMMAND_MAP.put(APPOINTMENTS, new AppointmentsListCommand(APPLICATION_CONTEXT));
        COMMAND_MAP.put(CREATE_APPOINTMENT, new CreateAppointmentCommand(APPLICATION_CONTEXT));
        COMMAND_MAP.put(SAVE_APPOINTMENT, new SaveAppointmentCommand(APPLICATION_CONTEXT));
        COMMAND_MAP.put(DETERMINE_DIAGNOSIS, new DetermineDiagnosisCommand(APPLICATION_CONTEXT));
        COMMAND_MAP.put(DISCHARGE_PATIENT, new DischargePatientCommand(APPLICATION_CONTEXT));
        COMMAND_MAP.put(HOSPITALISATIONS, new HospitalisationsListCommand(APPLICATION_CONTEXT));
        COMMAND_MAP.put(SAVE_HOSPITALISATION, new SaveHospitalisationCommand(APPLICATION_CONTEXT));
        COMMAND_MAP.put(ASSIGN_STAFF_TO_PATIENT, new AssignStaffToPatientCommand(APPLICATION_CONTEXT));
        COMMAND_MAP.put(PATIENT_DETAILS, new PatientDetailsCommand(APPLICATION_CONTEXT));
        COMMAND_MAP.put(PATIENTS_LIST, new PatientsListCommand(APPLICATION_CONTEXT));
        COMMAND_MAP.put(SAVE_PATIENT, new SavePatientCommand(APPLICATION_CONTEXT));
        COMMAND_MAP.put(ASSIGN_PATIENT_TO_STAFF, new AssignPatientToStaffCommand(APPLICATION_CONTEXT));
        COMMAND_MAP.put(SAVE_STAFF, new SaveStaffCommand(APPLICATION_CONTEXT));
        COMMAND_MAP.put(STAFF_DETAILS, new StaffDetailsCommand(APPLICATION_CONTEXT));
        COMMAND_MAP.put(STAFF_LIST, new StaffListCommand(APPLICATION_CONTEXT));
        COMMAND_MAP.put(ABOUT, new AboutCommand());
        COMMAND_MAP.put(EMPTY, new EmptyCommand());
        COMMAND_MAP.put(LOGIN, new LoginCommand(APPLICATION_CONTEXT));
        COMMAND_MAP.put(LOGOUT, new LogoutCommand());
        COMMAND_MAP.put(MAIN, new MainPageCommand());
    }

    public static Command getCommand(String command) {
        return COMMAND_MAP.getOrDefault(command, new EmptyCommand());
    }
}
