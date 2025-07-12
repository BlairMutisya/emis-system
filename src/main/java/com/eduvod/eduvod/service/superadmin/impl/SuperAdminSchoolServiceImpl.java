package com.eduvod.eduvod.service.superadmin.impl;

import com.eduvod.eduvod.dto.request.superadmin.SchoolRequest;
import com.eduvod.eduvod.dto.response.BaseApiResponse;
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
        var curriculum = curriculumTypeRepository.findById(request.getCurriculumId())
                .orElseThrow(() -> new RuntimeException("Invalid curriculum ID"));

        var category = schoolCategoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Invalid category ID"));

        var type = schoolTypeRepository.findById(request.getTypeId())
                .orElseThrow(() -> new RuntimeException("Invalid school type ID"));

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
//                .logoUrl(request.getLogoUrl())
                .build();

        school = schoolRepository.save(school);

        return SchoolResponse.builder()
                .id(school.getId())
                .moeRegNo(school.getMoeRegNo())
                .kpsaRegNo(school.getKpsaRegNo())
                .name(school.getName())
                .email(school.getEmail())
                .phone(school.getPhone())
                .curriculumType(curriculum.getName())
                .category(category.getName())
                .type(type.getName())
                .composition(school.getComposition())
                .location(school.getLocation())
                .address(school.getAddress())
                .website(school.getWebsite())
                .region(region.getName())
                .county(county.getName())
                .subCounty(subCounty.getName())
                .build();
}


    @Override
    public List<SchoolResponse> getAllSchools() {
        return schoolRepository.findAll().stream().map(school -> SchoolResponse.builder()
                .id(school.getId())
                .moeRegNo(school.getMoeRegNo())
                .kpsaRegNo(school.getKpsaRegNo())
                .name(school.getName())
                .email(school.getEmail())
                .phone(school.getPhone())
                .curriculumType(school.getCurriculum().getName())
                .category(school.getCategory().getName())
                .type(school.getType().getName())
                .composition(school.getComposition())
                .location(school.getLocation())
                .address(school.getAddress())
                .website(school.getWebsite())
                .region(school.getRegion().getName())
                .county(school.getCounty().getName())
                .subCounty(school.getSubCounty().getName())
                .build()).collect(Collectors.toList());
    }
    @Override
    public BaseApiResponse<SchoolResponse> getSchoolById(Long id) {
        School school = schoolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("School not found with ID: " + id));

        SchoolResponse response = SchoolResponse.builder()
                .id(school.getId())
                .moeRegNo(school.getMoeRegNo())
                .kpsaRegNo(school.getKpsaRegNo())
                .name(school.getName())
                .email(school.getEmail())
                .phone(school.getPhone())
                .curriculumType(school.getCurriculum().getName())
                .category(school.getCategory().getName())
                .type(school.getType().getName())
                .composition(school.getComposition())
                .region(school.getRegion().getName())
                .county(school.getCounty().getName())
                .subCounty(school.getSubCounty().getName())
                .location(school.getLocation())
                .address(school.getAddress())
                .website(school.getWebsite())
                .build();

        return new BaseApiResponse<>(200, "School fetched successfully", response);
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

    @Override
    public BaseApiResponse<SchoolResponse> updateSchool(SchoolRequest request) {
        var school = schoolRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("School not found"));

        var curriculum = curriculumTypeRepository.findById(request.getCurriculumId())
                .orElseThrow(() -> new RuntimeException("Invalid curriculum ID"));

        var category = schoolCategoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Invalid category ID"));

        var type = schoolTypeRepository.findById(request.getTypeId())
                .orElseThrow(() -> new RuntimeException("Invalid school type ID"));

        var region = regionRepository.findById(request.getRegionId())
                .orElseThrow(() -> new RuntimeException("Invalid region ID"));

        var county = countyRepository.findById(request.getCountyId())
                .orElseThrow(() -> new RuntimeException("Invalid county ID"));

        var subCounty = subCountyRepository.findById(request.getSubCountyId())
                .orElseThrow(() -> new RuntimeException("Invalid sub-county ID"));

        // Update school fields
        school.setMoeRegNo(request.getMoeRegNo());
        school.setKpsaRegNo(request.getKpsaRegNo());
        school.setName(request.getName());
        school.setCurriculum(curriculum);
        school.setCategory(category);
        school.setType(type);
        school.setComposition(request.getComposition());
        school.setPhone(request.getPhone());
        school.setEmail(request.getEmail());
        school.setRegion(region);
        school.setCounty(county);
        school.setSubCounty(subCounty);
        school.setLocation(request.getLocation());
        school.setAddress(request.getAddress());
        school.setWebsite(request.getWebsite());

        schoolRepository.save(school);

        // Full detailed response
        var response = SchoolResponse.builder()
                .id(school.getId())
                .moeRegNo(school.getMoeRegNo())
                .kpsaRegNo(school.getKpsaRegNo())
                .name(school.getName())
                .email(school.getEmail())
                .phone(school.getPhone())
                .curriculumType(curriculum.getName())
                .category(category.getName())
                .type(type.getName())
                .composition(school.getComposition())
                .region(region.getName())
                .county(county.getName())
                .subCounty(subCounty.getName())
                .location(school.getLocation())
                .address(school.getAddress())
                .website(school.getWebsite())
                .build();

        return BaseApiResponse.success("School updated successfully", response);
    }

    @Override
    public Resource exportSchools() {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Schools");

            String[] headers = {
                    "MoeRegNo", "KpsaRegNo", "Name", "CurriculumType", "Category",
                    "Type", "Composition", "Phone", "Email", "Region",
                    "County", "SubCounty", "Location", "Address", "Website"
            };

            // Header row
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            // Data rows
            List<School> schools = schoolRepository.findAll();
            int rowIdx = 1;
            for (School school : schools) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(school.getMoeRegNo());
                row.createCell(1).setCellValue(school.getKpsaRegNo());
                row.createCell(2).setCellValue(school.getName());
                row.createCell(3).setCellValue(school.getCurriculum().getName());
                row.createCell(4).setCellValue(school.getCategory().getName());
                row.createCell(5).setCellValue(school.getType().getName());
                row.createCell(6).setCellValue(school.getComposition());
                row.createCell(7).setCellValue(school.getPhone());
                row.createCell(8).setCellValue(school.getEmail());
                row.createCell(9).setCellValue(school.getRegion().getName());
                row.createCell(10).setCellValue(school.getCounty().getName());
                row.createCell(11).setCellValue(school.getSubCounty().getName());
                row.createCell(12).setCellValue(school.getLocation());
                row.createCell(13).setCellValue(school.getAddress());
                row.createCell(14).setCellValue(school.getWebsite());
            }

            // Save file
            Path tempFile = Files.createTempFile("schools_export", ".xlsx");
            try (OutputStream out = Files.newOutputStream(tempFile)) {
                workbook.write(out);
            }

            return new UrlResource(tempFile.toUri());

        } catch (IOException e) {
            throw new RuntimeException("Failed to export schools", e);
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
