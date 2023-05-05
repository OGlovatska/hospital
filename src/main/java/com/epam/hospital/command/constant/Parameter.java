package com.epam.hospital.command.constant;

public class Parameter {
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String MESSAGE = "message";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String ROLE = "role";
    public static final String SPECIALISATION = "specialisation";
    public static final String DATE_OF_BIRTH = "date_of_birth";
    public static final String GENDER = "gender";
    public static final String STAFF_ID = "staffId";
    public static final String PATIENT_ID = "patientId";
    public static final String HOSPITALISATION_ID = "hospitalisationId";
    public static final String USER = "user";
    public static final String OFFSET = "offset";
    public static final String LIMIT = "limit";
    public static final String ORDER_BY = "orderBy";
    public static final String DIRECTION = "dir";
    public static final String STAFF = "staff";
    public static final String PATIENTS = "patients";
    public static final String ASSIGNED_PATIENTS = "assignedPatients";
    public static final String NOT_ASSIGNED_PATIENTS = "notAssignedPatients";
    public static final String ASSIGNED_STAFF = "assignedStaff";
    public static final String NOT_ASSIGNED_STAFF = "notAssignedStaff";
    public static final String TOTAL_COUNT = "totalCount";
    public static final String NUMBER_OF_PAGES = "numberOfPages";
    public static final String CURRENT_PAGE = "page";
    public static final String SPECIALISATIONS = "specialisations";
    public static final String GENDERS = "genders";
    public static final String ROLES = "roles";
    public static final String LANGUAGE = "lang";
    public static final String APPOINTMENTS = "appointments";
    public static final String HOSPITALISATIONS = "hospitalisations";
    public static final String DATE = "date";
    public static final String HOSPITALISATION_START_DATE = "startDate";
    public static final String HOSPITALISATION_END_DATE = "endDate";
    public static final String ACTIVE_TAB = "activeTab";
    public static final String HOSPITALISATIONS_TAB = "nav-hospitalisations";
    public static final String STAFF_TAB = "nav-staff";
    public static final String PATIENTS_TAB = "nav-patients";
    public static final String DATE_TIME = "dateTime";
    public static final String TYPE = "type";
    public static final String DESCRIPTION = "description";
    public static final String STATUS = "status";
    public static final String DIAGNOSIS = "diagnosis";
    public static final String CURRENT_STAFF = "currentStaff";
    public static final String CURRENT_PATIENT = "currentPatient";
    public static final String HOSPITALISATION = "hospitalisation";
    public static final String IS_ERROR = "isError";

    public static final String CURRENT_STAFF_PAGE = "staffPage";
    public static final String STAFF_LIMIT = "staffLimit";
    public static final String STAFF_OFFSET = "staffOffset";
    public static final String STAFF_ORDER_BY = "staffOrderBy";
    public static final String STAFF_SORT_DIRECTION = "staffDir";
    public static final String STAFF_COUNT = "staffCount";
    public static final String STAFF_NUMBER_OF_PAGES = "staffNumberOfPages";

    public static final String HOSPITALISATION_STATUSES = "hospitalisationStatuses";
    public static final String CURRENT_HOSPITALISATIONS_PAGE = "hospitalisationsPage";
    public static final String HOSPITALISATIONS_LIMIT = "hospitalisationsLimit";
    public static final String HOSPITALISATIONS_OFFSET = "hospitalisationsOffset";
    public static final String HOSPITALISATIONS_ORDER_BY = "hospitalisationsOrderBy";
    public static final String HOSPITALISATIONS_SORT_DIRECTION = "hospitalisationsDir";
    public static final String HOSPITALISATIONS_COUNT = "hospitalisationsCount";
    public static final String HOSPITALISATIONS_NUMBER_OF_PAGES = "hospitalisationsNumberOfPages";

    public static final String EXCLUDED_COMMAND = "excludedCommand";

    public static String getParameter(String name, String value){
        return name + "=" + value;
    }
    private Parameter() {
    }
}