package com.eduvod.eduvod.service.superadmin.impl;

import com.eduvod.eduvod.dto.request.superadmin.SchoolRequest;
import com.eduvod.eduvod.dto.response.superadmin.SchoolResponse;
import com.eduvod.eduvod.model.superadmin.*;
import com.eduvod.eduvod.repository.superadmin.*;
import com.eduvod.eduvod.service.superadmin.SchoolService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SuperAdminSchoolServiceImpl implements SchoolService {

    private final SchoolRepository schoolRepository;
    private final CurriculumTypeRepository curriculumTypeRepository;
    private final SchoolCategoryRepository schoolCategoryRepository;
    private final SchoolTypeRepository schoolTypeRepository;
    private final RegionRepository regionRepository;
    private final CountyRepository countyRepository;
    private final SubCountyRepository subCountyRepository;

    @Override
    public SchoolResponse createSchool(SchoolRequest request) {
        var curriculum = curriculumTypeRepository.findByName(request.getCurriculumType())
                .orElseThrow(() -> new RuntimeException("Invalid curriculum type"));
        var category = schoolCategoryRepository.findByName(request.getCategory())
                .orElseThrow(() -> new RuntimeException("Invalid category"));
        var type = schoolTypeRepository.findByName(request.getType())
                .orElseThrow(() -> new RuntimeException("Invalid school type"));
        var region = regionRepository.findById(request.getRegionId())
                .orElseThrow(() -> new RuntimeException("Invalid region ID"));
        var county = countyRepository.findById(request.getCountyId())
                .orElseThrow(() -> new RuntimeException("Invalid county ID"));
        var subCounty = subCountyRepository.findById(request.getSubCountyId())
                .orElseThrow(() -> new RuntimeException("Invalid sub-county ID"));

        var school = School.builder()
                .moeRegNo(request.getMoeRegNo())
                .kpsaRegNo(request.getKpsaRegNo())
                .name(request.getName())
                .curriculum(curriculum)
                .category(category)
                .type(type)
                .composition(request.getComposition())
                .phone(request.getPhone())
                .email(request.getEmail())
                .region(region)
                .county(county)
                .subCounty(subCounty)
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
                .phone(school.getPhone())
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
                .phone(school.getPhone())
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
                String phone = getCellValue(row.getCell(7));
                String email = getCellValue(row.getCell(8));
                String regionName = getCellValue(row.getCell(9));
                String countyName = getCellValue(row.getCell(10));
                String subCountyName = getCellValue(row.getCell(11));
                String location = getCellValue(row.getCell(12));
                String address = getCellValue(row.getCell(13));
                String website = getCellValue(row.getCell(14));

                var curriculum = curriculumTypeRepository.findByName(curriculumName).orElse(null);
                var category = schoolCategoryRepository.findByName(categoryName).orElse(null);
                var type = schoolTypeRepository.findByName(typeName).orElse(null);
                var region = regionRepository.findByName(regionName).orElse(null);
                var county = countyRepository.findByName(countyName).orElse(null);
                var subCounty = subCountyRepository.findByName(subCountyName).orElse(null);

                if (curriculum == null || category == null || type == null ||
                        region == null || county == null || subCounty == null) continue;

                School school = School.builder()
                        .moeRegNo(moeRegNo)
                        .kpsaRegNo(kpsaRegNo)
                        .name(name)
                        .curriculum(curriculum)
                        .category(category)
                        .type(type)
                        .composition(composition)
                        .phone(phone)
                        .email(email)
                        .region(region)
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
                    "Type", "Composition", "Phone", "Email", "Region",
                    "County", "SubCounty", "Location", "Address", "Website"
            };

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
