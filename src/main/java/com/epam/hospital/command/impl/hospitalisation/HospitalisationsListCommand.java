package com.epam.hospital.command.impl.hospitalisation;

import com.epam.hospital.command.Command;
import com.epam.hospital.command.CommandResult;
import com.epam.hospital.command.constant.Page;
import com.epam.hospital.exception.ApplicationException;
import com.epam.hospital.model.Hospitalisation;
import com.epam.hospital.service.HospitalisationService;
import com.epam.hospital.to.HospitalisationTo;
import com.epam.hospital.to.PatientTo;
import com.epam.hospital.to.UserTo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.epam.hospital.command.constant.Parameter.*;
import static com.epam.hospital.command.constant.Parameter.DIRECTION;
import static com.epam.hospital.util.PaginationUtil.getPaginationAttributes;
import static com.epam.hospital.util.PaginationUtil.setPaginationAttributes;

public class HospitalisationsListCommand implements Command {
    private static final Logger LOG = LoggerFactory.getLogger(HospitalisationsListCommand.class);
    private final HospitalisationService service = new HospitalisationService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        UserTo user = (UserTo) session.getAttribute(USER);

        List<HospitalisationTo> hospitalisations = new ArrayList<>();
        int totalCount = 0;
        Map<String, String> attributes = getPaginationAttributes(request);
        try {
            Map<Integer, List<HospitalisationTo>> hospitalisationTos = service.getAllHospitalisations(user, attributes.get(OFFSET),
                    attributes.get(LIMIT), attributes.get(ORDER_BY), attributes.get(DIRECTION));
            hospitalisations = hospitalisationTos.values().stream().findFirst().orElseThrow();
            totalCount = hospitalisationTos.keySet().stream().findFirst().orElseThrow();
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
        request.setAttribute(HOSPITALISATIONS, hospitalisations);

        setPaginationAttributes(request, totalCount, attributes.get(LIMIT), attributes.get(OFFSET),
                attributes.get(CURRENT_PAGE), attributes.get(ORDER_BY), attributes.get(DIRECTION));
        return new CommandResult(Page.HOSPITALISATIONS);
    }
}
