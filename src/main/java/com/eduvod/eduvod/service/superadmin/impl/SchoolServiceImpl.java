package com.eduvod.eduvod.service.superadmin.impl;

import com.eduvod.eduvod.dto.request.superadmin.SchoolRequest;
import com.eduvod.eduvod.dto.response.superadmin.SchoolResponse;
import com.eduvod.eduvod.model.superadmin.*;
import com.eduvod.eduvod.repository.superadmin.*;
import com.eduvod.eduvod.service.superadmin.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SchoolServiceImpl implements SchoolService {

    private final SchoolRepository schoolRepository;
    private final CurriculumTypeRepository curriculumTypeRepository;
    private final SchoolCategoryRepository schoolCategoryRepository;
    private final SchoolTypeRepository schoolTypeRepository;

    @Override
    public SchoolResponse createSchool(SchoolRequest request) {
        var curriculum = curriculumTypeRepository.findByName(request.getCurriculumType())
                .orElseThrow(() -> new RuntimeException("Invalid curriculum type"));
        var category = schoolCategoryRepository.findByName(request.getCategory())
                .orElseThrow(() -> new RuntimeException("Invalid category"));
        var type = schoolTypeRepository.findByName(request.getType())
                .orElseThrow(() -> new RuntimeException("Invalid school type"));

        var school = School.builder()
                .moeRegNo(request.getMoeRegNo())
                .kpsaRegNo(request.getKpsaRegNo())
                .name(request.getName())
                .curriculum(curriculum)
                .category(category)
                .type(type)
                .composition(request.getComposition())
                .mobile(request.getMobile())
                .email(request.getEmail())
                .region(request.getRegion())
                .diocese(request.getDiocese())
                .county(request.getCounty())
                .subCounty(request.getSubCounty())
                .location(request.getLocation())
                .address(request.getAddress())
                .website(request.getWebsite())
                .build();

        school = schoolRepository.save(school);

        return SchoolResponse.builder()
                .id(school.getId())
                .moeRegNo(school.getMoeRegNo())
                .name(school.getName())
                .email(school.getEmail())
                .mobile(school.getMobile())
                .curriculumType(curriculum.getName())
                .category(category.getName())
                .type(type.getName())
                .build();
    }

    @Override
    public List<SchoolResponse> getAllSchools() {
        return schoolRepository.findAll().stream().map(school -> SchoolResponse.builder()
                .id(school.getId())
                .moeRegNo(school.getMoeRegNo())
                .name(school.getName())
                .email(school.getEmail())
                .mobile(school.getMobile())
                .curriculumType(school.getCurriculum().getName())
                .category(school.getCategory().getName())
                .type(school.getType().getName())
                .build()).collect(Collectors.toList());
    }

    @Override
    public void importSchools(MultipartFile file) {
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header

                String moeRegNo = getCellValue(row.getCell(0));
                String kpsaRegNo = getCellValue(row.getCell(1));
                String name = getCellValue(row.getCell(2));
                String curriculumName = getCellValue(row.getCell(3));
                String categoryName = getCellValue(row.getCell(4));
                String typeName = getCellValue(row.getCell(5));
                String composition = getCellValue(row.getCell(6));
                String mobile = getCellValue(row.getCell(7));
                String email = getCellValue(row.getCell(8));
                String region = getCellValue(row.getCell(9));
                String diocese = getCellValue(row.getCell(10));
                String county = getCellValue(row.getCell(11));
                String subCounty = getCellValue(row.getCell(12));
                String location = getCellValue(row.getCell(13));
                String address = getCellValue(row.getCell(14));
                String website = getCellValue(row.getCell(15));

                var curriculum = curriculumTypeRepository.findByName(curriculumName).orElse(null);
                var category = schoolCategoryRepository.findByName(categoryName).orElse(null);
                var type = schoolTypeRepository.findByName(typeName).orElse(null);

                if (curriculum == null || category == null || type == null) continue;

                School school = School.builder()
                        .moeRegNo(moeRegNo)
                        .kpsaRegNo(kpsaRegNo)
                        .name(name)
                        .curriculum(curriculum)
                        .category(category)
                        .type(type)
                        .composition(composition)
                        .mobile(mobile)
                        .email(email)
                        .region(region)
                        .diocese(diocese)
                        .county(county)
                        .subCounty(subCounty)
                        .location(location)
                        .address(address)
                        .website(website)
                        .build();

                schoolRepository.save(school);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to import schools: " + e.getMessage(), e);
        }
    }

    private String getCellValue(Cell cell) {
        if (cell == null) return "";
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf((long) cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            default -> "";
        };
    }

    @Override
    public Resource getImportTemplate() {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Schools");

            String[] headers = {
                    "MoeRegNo", "KpsaRegNo", "Name", "CurriculumType", "Category",
                    "Type", "Composition", "Mobile", "Email", "Region", "Diocese",
                    "County", "SubCounty", "Location", "Address", "Website"
            };

            //Create header style
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.WHITE.getIndex());
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
                sheet.autoSizeColumn(i);
            }


            // Save the workbook to a temp file
            Path tempFile = Files.createTempFile("school_import_template", ".xlsx");
            try (OutputStream out = Files.newOutputStream(tempFile)) {
                workbook.write(out);
            }

            return new UrlResource(tempFile.toUri());

        } catch (IOException e) {
            throw new RuntimeException("Failed to generate import template", e);
        }
    }

}
