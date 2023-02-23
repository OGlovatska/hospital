package com.epam.hospital.util;

import com.epam.hospital.model.Staff;
import com.epam.hospital.model.User;
import com.epam.hospital.to.StaffTo;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.epam.hospital.command.constant.Parameter.SPECIALISATION;

public class StaffUtil {

    public static StaffTo createStaffTo(Staff staff, int patients) {
        return new StaffTo.Builder().id(staff.getId()).firstName(staff.getFirstName())
                .lastName(staff.getLastName()).email(staff.getEmail()).role(staff.getRole())
                .userId(staff.getUserId()).specialisation(staff.getSpecialisation())
                .patients(patients).build();
    }

    public static List<StaffTo> getStaffTos(Map<Staff, Integer> staff){
        return staff
                .entrySet()
                .stream()
                .map(e -> createStaffTo(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

    public static List<StaffTo> getStaffTos(List<Staff> staff){
        return staff
                .stream()
                .map(staff1 -> createStaffTo(staff1, 0))
                .collect(Collectors.toList());
    }

    public static Staff createStaff(HttpServletRequest request){
        return new Staff.Builder().specialisation(request.getParameter(SPECIALISATION)).build();
    }
}
