package com.eduvod.eduvod.service.schooladmin.impl;

import com.eduvod.eduvod.constants.ErrorMessages;
import com.eduvod.eduvod.dto.request.schooladmin.StaffRequest;
import com.eduvod.eduvod.dto.response.common.BaseApiResponse;
import com.eduvod.eduvod.dto.response.schooladmin.StaffResponse;
import com.eduvod.eduvod.exception.ResourceNotFoundException;
import com.eduvod.eduvod.model.schooladmin.Staff;
import com.eduvod.eduvod.repository.schooladmin.StaffRepository;
import com.eduvod.eduvod.security.util.AuthUtil;
import com.eduvod.eduvod.service.schooladmin.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService {

    private final StaffRepository staffRepository;
    private final AuthUtil authUtil;

    @Override
    public BaseApiResponse<StaffResponse> createStaff(StaffRequest request) {
        var school = authUtil.getCurrentSchoolAdmin().getSchool();

        Staff staff = Staff.builder()
                .staffNumber(request.getStaffNumber())
                .firstName(request.getFirstName())
                .middleName(request.getMiddleName())
                .lastName(request.getLastName())
                .gender(request.getGender())
                .phone(request.getPhone())
                .staffCategory(request.getStaffCategory())
                .staffDepartment(request.getStaffDepartment())
                .joiningDate(request.getJoiningDate())
                .dateOfBirth(request.getDateOfBirth())
                .isTeacher(request.isTeacher())
                .disabled(false)
                .school(school)
                .build();

        staff = staffRepository.save(staff);
        return BaseApiResponse.success(mapToResponse(staff));
    }

    @Override
    public BaseApiResponse<StaffResponse> updateStaff(Long id, StaffRequest request) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.STAFF_NOT_FOUND));


        staff.setStaffNumber(request.getStaffNumber());
        staff.setFirstName(request.getFirstName());
        staff.setMiddleName(request.getMiddleName());
        staff.setLastName(request.getLastName());
        staff.setGender(request.getGender());
        staff.setPhone(request.getPhone());
        staff.setStaffCategory(request.getStaffCategory());
        staff.setStaffDepartment(request.getStaffDepartment());
        staff.setJoiningDate(request.getJoiningDate());
        staff.setDateOfBirth(request.getDateOfBirth());
        staff.setTeacher(request.isTeacher());

        staff = staffRepository.save(staff);
        return BaseApiResponse.success(mapToResponse(staff));
    }

    @Override
    public BaseApiResponse<Void> disableStaff(Long id) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.STAFF_NOT_FOUND));

        staff.setDisabled(true);
        staffRepository.save(staff);
        return BaseApiResponse.success("Staff disabled", null);
    }

    @Override
    public BaseApiResponse<List<StaffResponse>> getAllStaff() {
        var school = authUtil.getCurrentSchoolAdmin().getSchool();

        List<Staff> staffList = staffRepository.findBySchool(school);

        return BaseApiResponse.success(
                staffList.stream().map(this::mapToResponse).collect(Collectors.toList())
        );
    }
    @Override
    public BaseApiResponse<Void> deleteStaff(Long id) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(ErrorMessages.STAFF_NOT_FOUND));


        staff.setDeleted(true);
        staffRepository.save(staff);

        return BaseApiResponse.success("Staff deleted successfully", null);
    }


    private StaffResponse mapToResponse(Staff staff) {
        return StaffResponse.builder()
                .id(staff.getId())
                .staffNumber(staff.getStaffNumber())
                .firstName(staff.getFirstName())
                .middleName(staff.getMiddleName())
                .lastName(staff.getLastName())
                .gender(staff.getGender())
                .phone(staff.getPhone())
                .staffCategory(staff.getStaffCategory())
                .staffDepartment(staff.getStaffDepartment())
                .joiningDate(staff.getJoiningDate())
                .dateOfBirth(staff.getDateOfBirth())
                .isTeacher(staff.isTeacher())
                .disabled(staff.isDisabled())
                .build();
    }
}
