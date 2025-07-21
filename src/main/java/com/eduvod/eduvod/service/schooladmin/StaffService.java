package com.eduvod.eduvod.service.schooladmin;

import com.eduvod.eduvod.dto.request.schooladmin.StaffRequest;
import com.eduvod.eduvod.dto.response.common.BaseApiResponse;
import com.eduvod.eduvod.dto.response.schooladmin.StaffResponse;

import java.util.List;

public interface StaffService {
    BaseApiResponse<StaffResponse> createStaff(StaffRequest request);
    BaseApiResponse<StaffResponse> updateStaff(Long id, StaffRequest request);
    BaseApiResponse<Void> disableStaff(Long id);
    BaseApiResponse<List<StaffResponse>> getAllStaff();

    BaseApiResponse<Void> deleteStaff(Long id);

}
