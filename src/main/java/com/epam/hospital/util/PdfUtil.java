package com.epam.hospital.util;

import java.io.*;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import com.epam.hospital.to.AppointmentTo;
import com.epam.hospital.to.HospitalisationTo;
import com.epam.hospital.to.PatientTo;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import static com.epam.hospital.listener.DBContextListener.getServletContext;

public class PdfUtil {

    public static byte[] getHospitalCardPdf(PatientTo patientTo, List<HospitalisationTo> hospitalisationTos,
                                            String locale) throws IOException, URISyntaxException {
        try {
            Document document = new Document();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter writer = PdfWriter.getInstance(document, baos);
            ResourceBundle bundle = ResourceBundle.getBundle("application", Locale.forLanguageTag(locale));

            document.open();
            writeHead(document, writer, bundle);
            writePatientInformation(document, patientTo, bundle);
            writeHospitalisationsTable(document, patientTo, hospitalisationTos, bundle);
            document.close();

            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void writeHead(Document document, PdfWriter writer, ResourceBundle bundle) throws IOException, DocumentException {
        String path = getServletContext().getRealPath("/resources/images/logo.png");
        Image image = Image.getInstance(path);
        image.setAbsolutePosition(36, document.top() - image.getHeight());

        PdfContentByte content = writer.getDirectContent();
        content.addImage(image);

        ColumnText ct = new ColumnText(content);
        ct.setSimpleColumn(new Phrase("Hospital, Inc\nhospital.task@gmail.com"),
                document.right() - 140,
                document.top(),
                document.right(),
                document.top() - image.getHeight() - 36,
                15,
                Element.ALIGN_RIGHT);
        ct.go();

        document.add(new Paragraph("\n\n"));
        Paragraph title = new Paragraph(bundle.getString("main.hospital.card"), getFont(24));
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
    }

    private static void writePatientInformation(Document document, PatientTo patientTo, ResourceBundle bundle) {
        String name = bundle.getString("common.name") + ": " + patientTo.getFirstName() + " " + patientTo.getLastName();
        addParagraph(name, document);

        String dateOfBirth = bundle.getString("patient.date.of.birth") + ": " + patientTo.getDateOfBirth();
        addParagraph(dateOfBirth, document);

        String gender = bundle.getString("patient.gender") + ": " + patientTo.getGender().getName();
        addParagraph(gender, document);

        String email = bundle.getString("placeholder.email") + ": " + patientTo.getEmail();
        addParagraph(email, document);
    }

    private static void addParagraph(String parameter, Document document) {
        Paragraph element = new Paragraph(parameter, getFont(14));
        element.setAlignment(Element.ALIGN_LEFT);
        addToDocument(document, element);
    }

    private static void writeHospitalisationsTable(Document document, PatientTo patientTo, List<HospitalisationTo> hospitalisationTos, ResourceBundle bundle) {
        Paragraph title = new Paragraph(bundle.getString("hospitalisation.hospitalisations"), getFont(14));
        title.setAlignment(Element.ALIGN_CENTER);
        addToDocument(document, title);

        for (HospitalisationTo hospitalisation : hospitalisationTos) {
            fillHospitalisationInformation(document, hospitalisation, bundle);
            fillAppointmentsTable(document, hospitalisation, bundle);
        }
    }

    private static void fillHospitalisationInformation(Document document, HospitalisationTo hospitalisation, ResourceBundle bundle) {
        String startDate = bundle.getString("hospitalisation.date") + ": " + hospitalisation.getStartDate();
        addParagraph(startDate, document);

        String status = bundle.getString("common.status") + ": " + hospitalisation.getStatus();
        addParagraph(status, document);

        String endDate = bundle.getString("hospitalisation.discharging.date") + ": " + hospitalisation.getEndDate();
        addParagraph(endDate, document);

        String diagnosis = bundle.getString("hospitalisation.diagnosis") + ": " + hospitalisation.getDiagnosis();
        addParagraph(diagnosis, document);

        String appointments = bundle.getString("appointment.appointments") + ": ";
        addParagraph(appointments, document);
        addToDocument(document, new Paragraph("\n"));
    }

    private static void fillAppointmentsTable(Document document, HospitalisationTo hospitalisation, ResourceBundle bundle) {
        PdfPTable table = new PdfPTable(new float[]{2, 9, 7, 7, 7, 7, 6});
        addAppointmentsTableHead(bundle, table);

        for (AppointmentTo appointment : hospitalisation.getAppointments()) {
            table.addCell(String.valueOf(appointment.getId()));
            table.addCell(appointment.getDateTime().format(DateTimeUtil.DATE_TIME_FORMATTER));
            table.addCell(appointment.getStaffFirstName() + " " + appointment.getStaffLastName());
            table.addCell(appointment.getType().name());
            table.addCell(appointment.getDescription());
            table.addCell(appointment.getConclusion());
            table.addCell(appointment.getStatus());
        }
        addToDocument(document, table);
    }

    private static void addAppointmentsTableHead(ResourceBundle bundle, PdfPTable table) {
        table.setWidthPercentage(100);
        table.setHeaderRows(1);
        table.addCell(new PdfPCell(new Phrase("#", getFont(14))));
        table.addCell(new PdfPCell(new Phrase(bundle.getString("appointment.date.time"), getFont(14))));
        table.addCell(new PdfPCell(new Phrase(bundle.getString("common.staff"), getFont(14))));
        table.addCell(new PdfPCell(new Phrase(bundle.getString("common.type"), getFont(14))));
        table.addCell(new PdfPCell(new Phrase(bundle.getString("appointment.description"), getFont(14))));
        table.addCell(new PdfPCell(new Phrase(bundle.getString("appointment.conclusion"), getFont(14))));
        table.addCell(new PdfPCell(new Phrase(bundle.getString("common.status"), getFont(14))));
    }

    private static void addToDocument(Document document, Element title) {
        try {
            document.add(title);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    private static Font getFont(int size) {
        BaseFont baseFont = getBaseFont();
        if (baseFont != null) {
            return new Font(baseFont, size);
        }
        return new Font(Font.FontFamily.TIMES_ROMAN, size);
    }

    private static BaseFont getBaseFont() {
        try {
            return BaseFont.createFont("times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
