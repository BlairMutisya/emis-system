package com.eduvod.eduvod.service.schooladmin;

import com.eduvod.eduvod.dto.request.schooladmin.StreamRequest;
import com.eduvod.eduvod.dto.response.schooladmin.StreamResponse;
import com.eduvod.eduvod.dto.response.BaseApiResponse;

import java.util.List;

public interface StreamService {
    BaseApiResponse<StreamResponse> createStream(StreamRequest request);
    BaseApiResponse<List<StreamResponse>> getStreamsByClassId(Long classId);
}
