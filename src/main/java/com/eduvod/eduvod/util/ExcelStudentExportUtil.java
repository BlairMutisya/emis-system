package com.eduvod.eduvod.util;

import com.eduvod.eduvod.model.schooladmin.Student;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelStudentExportUtil {

    public static ByteArrayInputStream exportStudentsToExcel(List<Student> students) throws IOException {
        String[] HEADERS = {
                "Admission No", "Nemis No", "Admission Date", "First Name", "Middle Name", "Last Name",
                "Date of Birth", "Email", "Gender", "Blood Group", "Nationality", "City", "Address Line1", "Phone",
                "Differently Abled", "Guardian First Name", "Guardian Last Name", "Guardian Relationship",
                "Guardian Email", "Guardian Phone", "Guardian Gender", "Emergency Contact"
        };

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Students");

            // === Header Style ===
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.BLACK.getIndex());
            headerFont.setFontHeightInPoints((short) 12);
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);

            // === Create Header Row ===
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < HEADERS.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(HEADERS[i]);
                cell.setCellStyle(headerStyle);
                sheet.autoSizeColumn(i);
            }

            // === Data Rows ===
            int rowIdx = 1;
            for (Student student : students) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(student.getAdmissionNo());
                row.createCell(1).setCellValue(student.getNemisNo());
                row.createCell(2).setCellValue(
                        student.getAdmissionDate() != null ? student.getAdmissionDate().toString() : ""
                );
                row.createCell(3).setCellValue(student.getFirstName());
                row.createCell(4).setCellValue(student.getMiddleName());
                row.createCell(5).setCellValue(student.getLastName());
                row.createCell(6).setCellValue(
                        student.getDateOfBirth() != null ? student.getDateOfBirth().toString() : ""
                );
                row.createCell(7).setCellValue(student.getEmail());
                row.createCell(8).setCellValue(student.getGender() != null ? student.getGender().name() : "");
                row.createCell(9).setCellValue(student.getBloodGroup());
                row.createCell(10).setCellValue(student.getNationality());
                row.createCell(11).setCellValue(student.getCity());
                row.createCell(12).setCellValue(student.getAddressLine1());
                row.createCell(13).setCellValue(student.getPhone());
                row.createCell(14).setCellValue(student.isDifferentlyAbled() ? "Yes" : "No");

                if (student.getGuardian() != null) {
                    row.createCell(15).setCellValue(student.getGuardian().getFirstName());
                    row.createCell(16).setCellValue(student.getGuardian().getLastName());
                    row.createCell(17).setCellValue(student.getGuardian().getRelationship());
                    row.createCell(18).setCellValue(student.getGuardian().getEmail());
                    row.createCell(19).setCellValue(student.getGuardian().getPhone());
                    row.createCell(20).setCellValue(
                            student.getGuardian().getGender() != null ? student.getGuardian().getGender().name() : ""
                    );
                    row.createCell(21).setCellValue(student.getGuardian().getEmergencyContact());
                }
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}
