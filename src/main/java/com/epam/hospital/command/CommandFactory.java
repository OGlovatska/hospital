package com.epam.hospital.command;

import com.epam.hospital.command.impl.appointment.AppointmentsListCommand;
import com.epam.hospital.command.impl.appointment.CreateAppointmentCommand;
import com.epam.hospital.command.impl.appointment.SaveAppointmentCommand;
import com.epam.hospital.command.impl.hospitalisation.DetermineDiagnosisCommand;
import com.epam.hospital.command.impl.hospitalisation.DischargePatientCommand;
import com.epam.hospital.command.impl.hospitalisation.HospitalisationsListCommand;
import com.epam.hospital.command.impl.hospitalisation.SaveHospitalisationCommand;
import com.epam.hospital.command.impl.patient.AssignStaffToPatientCommand;
import com.epam.hospital.command.impl.patient.PatientDetailsCommand;
import com.epam.hospital.command.impl.patient.PatientsListCommand;
import com.epam.hospital.command.impl.patient.SavePatientCommand;
import com.epam.hospital.command.impl.staff.*;
import com.epam.hospital.command.impl.user.*;

import java.util.HashMap;
import java.util.Map;

import static com.epam.hospital.command.constant.Command.*;

public class CommandFactory {
    private static final Map<String, Command> COMMAND_MAP = new HashMap<>();

    private CommandFactory() {
    }

    static {
        COMMAND_MAP.put(EMPTY, new EmptyCommand());
        COMMAND_MAP.put(LOGIN, new LoginCommand());
        COMMAND_MAP.put(LOGOUT, new LogoutCommand());
        COMMAND_MAP.put(MAIN, new MainPageCommand());
        COMMAND_MAP.put(ABOUT, new AboutCommand());
        COMMAND_MAP.put(STAFF_LIST, new StaffListCommand());
        COMMAND_MAP.put(SAVE_STAFF, new SaveStaffCommand());
        COMMAND_MAP.put(STAFF_DETAILS, new StaffDetailsCommand());
        COMMAND_MAP.put(ASSIGN_PATIENT_TO_STAFF, new AssignPatientToStaffCommand());
        COMMAND_MAP.put(PATIENTS_LIST, new PatientsListCommand());
        COMMAND_MAP.put(SAVE_PATIENT, new SavePatientCommand());
        COMMAND_MAP.put(PATIENT_DETAILS, new PatientDetailsCommand());
        COMMAND_MAP.put(ASSIGN_STAFF_TO_PATIENT, new AssignStaffToPatientCommand());
        COMMAND_MAP.put(APPOINTMENTS, new AppointmentsListCommand());
        COMMAND_MAP.put(HOSPITALISATIONS, new HospitalisationsListCommand());
        COMMAND_MAP.put(CREATE_APPOINTMENT, new CreateAppointmentCommand());
        COMMAND_MAP.put(SAVE_APPOINTMENT, new SaveAppointmentCommand());
        COMMAND_MAP.put(SAVE_HOSPITALISATION, new SaveHospitalisationCommand());
        COMMAND_MAP.put(DISCHARGE_PATIENT, new DischargePatientCommand());
        COMMAND_MAP.put(DETERMINE_DIAGNOSIS, new DetermineDiagnosisCommand());
    }

    public static Command getCommand(String command) {
        return COMMAND_MAP.getOrDefault(command, new EmptyCommand());
    }
}
