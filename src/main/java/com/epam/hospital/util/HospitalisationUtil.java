package com.epam.hospital.util;

import com.epam.hospital.model.Hospitalisation;
import com.epam.hospital.model.Patient;
import com.epam.hospital.to.HospitalisationTo;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDate;

import static com.epam.hospital.command.constant.Parameter.*;

public class HospitalisationUtil {

    public static HospitalisationTo createHospitalisationTo(Hospitalisation hospitalisation, Patient patient) {
        return new HospitalisationTo(hospitalisation.getId(), hospitalisation.getPatientId(), patient.getFirstName(),
                patient.getLastName(), hospitalisation.getStartDate(), hospitalisation.getEndDate(), hospitalisation.getStatus(),
                hospitalisation.getDiagnosis());
    }

    public static HospitalisationTo createHospitalisationTo(Hospitalisation hospitalisation) {
        return new HospitalisationTo(hospitalisation.getId(), hospitalisation.getPatientId(),
                hospitalisation.getStartDate(), hospitalisation.getEndDate(), hospitalisation.getStatus(),
                hospitalisation.getDiagnosis());
    }

    public static Hospitalisation createHospitalisation(HttpServletRequest request){
        Hospitalisation hospitalisation = new Hospitalisation();
        hospitalisation.setPatientId(Integer.parseInt(request.getParameter(PATIENT_ID)));
        hospitalisation.setStartDate(request.getParameter(START_DATE) != null ? LocalDate.parse(request.getParameter(START_DATE)) : null);
        hospitalisation.setEndDate(request.getParameter(END_DATE) != null ? LocalDate.parse(request.getParameter(END_DATE)) : null);
        hospitalisation.setStatus(request.getParameter(STATUS));
        hospitalisation.setDiagnosis(request.getParameter(DIAGNOSIS));
        return hospitalisation;
    }
}