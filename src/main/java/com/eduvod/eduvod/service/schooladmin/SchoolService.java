package com.eduvod.eduvod.service.schooladmin;

import com.eduvod.eduvod.dto.response.common.PagedResponse;
import com.eduvod.eduvod.dto.response.schooladmin.SchoolResponse;
import org.springframework.web.multipart.MultipartFile;

public interface SchoolService {
    SchoolResponse getSchoolForLoggedInAdmin();
    SchoolResponse updateSchoolLogo(MultipartFile file);


}
