package com.eduvod.eduvod.service.superadmin;

import com.eduvod.eduvod.dto.request.superadmin.SchoolRequest;
import com.eduvod.eduvod.dto.response.common.BaseApiResponse;
import com.eduvod.eduvod.dto.response.common.PagedResponse;
import com.eduvod.eduvod.dto.response.superadmin.SchoolResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SchoolService {
    SchoolResponse createSchool(SchoolRequest request);
    List<SchoolResponse> getAllSchools();

    void importSchools(MultipartFile file);
//    Resource getImportTemplate();
    Resource exportSchools();
    BaseApiResponse<SchoolResponse> updateSchool(SchoolRequest request);
    BaseApiResponse<SchoolResponse> getSchoolById(Long id);

    PagedResponse<SchoolResponse> getAllSchools(int page, int size);


}
