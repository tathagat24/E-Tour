package com.etour.tour_service_api.utils;

import com.etour.tour_service_api.dto.PassengerDto;
import com.etour.tour_service_api.dto.TourBookingDto;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;

/**
 * @version 1.0
 * @project tour-service-api
 * @since 10-02-2025
 */

public class PdfDocumentUtils {

    public static byte[] generateBookingPdf(TourBookingDto tourBookingDto) throws DocumentException {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        Document document = new Document();
        PdfWriter.getInstance(document, byteArrayOutputStream);
        document.open();

        document.add(new Paragraph("Booking Confirmation"));

        document.add(new Paragraph("Tour Name: " + tourBookingDto.getTourName()));
        document.add(new Paragraph("Booking Date: " + tourBookingDto.getBookingDate()));
        document.add(new Paragraph("Booking Status: " + tourBookingDto.getBookingStatus()));
        document.add(new Paragraph("Total Price: " + tourBookingDto.getTotalPrice()));
        document.add(new Paragraph("Category: " + tourBookingDto.getCategoryName()));
        document.add(new Paragraph("Subcategory: " + tourBookingDto.getSubCategoryName()));
        document.add(new Paragraph("Tour Duration: " + tourBookingDto.getDuration()));
        document.add(new Paragraph("Start Date: " + tourBookingDto.getStartDate()));
        document.add(new Paragraph("End Date: " + tourBookingDto.getEndDate()));

        document.add(new Paragraph("Passenger List: "));

        document.add(new Paragraph("         "));

        PdfPTable table = new PdfPTable(6); // 4 columns for First Name, Last Name, Age, and Passenger Cost
        table.setWidthPercentage(100f);

        table.addCell("Name");
        table.addCell("Date Of Birth");
        table.addCell("Age");
        table.addCell("Gender");
        table.addCell("Passenger Type");
        table.addCell("Passenger Cost");

        for (PassengerDto passengerDto : tourBookingDto.getPassengers()) {
            table.addCell(passengerDto.getFirstName() + " " + passengerDto.getLastName());
            table.addCell(passengerDto.getDateOfBirth());
            table.addCell(passengerDto.getAge().toString());
            table.addCell(passengerDto.getGender().toString());
            table.addCell(passengerDto.getPassengerType().toString());
            table.addCell(passengerDto.getPassengerCost().toString());
        }

        document.add(table);

        document.close();

        return byteArrayOutputStream.toByteArray();
    }

}
