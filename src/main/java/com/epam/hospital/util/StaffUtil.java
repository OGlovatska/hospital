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
        return new StaffTo(staff.getId(), staff.getFirstName(), staff.getLastName(),
                staff.getEmail(), staff.getRole().name(), staff.getUserId(), staff.getSpecialisation(), patients);
    }

    public static StaffTo createNewStaffTo(User user, Staff staff) {
        return new StaffTo(staff.getId(), user.getFirstName(), user.getLastName(),
                user.getEmail(), user.getRole().name(), staff.getUserId(), staff.getSpecialisation(), 0);
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
        Staff staff = new Staff();
        staff.setSpecialisation(request.getParameter(SPECIALISATION));
        return staff;
    }
}
