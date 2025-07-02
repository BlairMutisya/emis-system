package com.eduvod.eduvod.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ExcelStudentTemplateUtil {

    public static ByteArrayInputStream generateTemplate() throws IOException {
        String[] HEADERS = {
                "Admission No", "Nemis No", "Admission Date", "First Name", "Middle Name", "Last Name",
                "Date of Birth", "Email", "Gender", "Blood Group", "Nationality", "City", "Address Line1", "Phone",
                "Differently Abled", "Guardian First Name", "Guardian Last Name", "Guardian Relationship",
                "Guardian Email", "Guardian Phone", "Guardian Gender", "Emergency Contact"
        };

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Students");

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < HEADERS.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(HEADERS[i]);
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}
