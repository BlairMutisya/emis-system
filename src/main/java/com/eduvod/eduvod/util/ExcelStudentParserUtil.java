package com.eduvod.eduvod.util;

import com.eduvod.eduvod.dto.request.schooladmin.StudentRequest;
import com.eduvod.eduvod.dto.request.schooladmin.GuardianRequest;
import com.eduvod.eduvod.enums.Gender;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ExcelStudentParserUtil {

    public static List<StudentRequest> parse(MultipartFile file) throws Exception {
        List<StudentRequest> students = new ArrayList<>();
        try (InputStream is = file.getInputStream(); Workbook workbook = WorkbookFactory.create(is)) {
            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) { // skip header
                Row row = sheet.getRow(i);
                if (row == null) continue;

                StudentRequest req = new StudentRequest();
                req.setAdmissionNo(getString(row, 0));
                req.setNemisNo(getString(row, 1));
                req.setAdmissionDate(LocalDate.parse(getString(row, 2)));
                req.setFirstName(getString(row, 3));
                req.setMiddleName(getString(row, 4));
                req.setLastName(getString(row, 5));
                req.setDateOfBirth(LocalDate.parse(getString(row, 6)));
                req.setEmail(getString(row, 7));
                req.setGender(Gender.valueOf(getString(row, 8).toUpperCase()));
                req.setBloodGroup(getString(row, 9));
                req.setNationality(getString(row, 10));
                req.setCity(getString(row, 11));
                req.setAddressLine1(getString(row, 12));
                req.setPhone(getString(row, 13));
                req.setDifferentlyAbled(Boolean.parseBoolean(getString(row, 14)));
                req.setGuardianFirstName(getString(row, 15));
                req.setGuardianLastName(getString(row, 16));
                req.setGuardianRelationship(getString(row, 17));
                req.setGuardianEmail(getString(row, 18));
                req.setGuardianPhone(getString(row, 19));
                req.setGuardianGender(Gender.valueOf(getString(row, 20).toUpperCase()));
                req.setGuardianEmergencyContact(getString(row, 21));



                students.add(req);
            }
        }
        return students;
    }

    private static String getString(Row row, int cellIdx) {
        Cell cell = row.getCell(cellIdx);
        return cell != null ? cell.toString().trim() : "";
    }
}
