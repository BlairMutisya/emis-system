package com.eduvod.eduvod.service.superadmin;

import com.eduvod.eduvod.dto.request.superadmin.TypeRequest;
import lombok.Data;
import java.util.List;

public interface TypeService<T> {
    T create(TypeRequest request);
    List<T> getAll();
}
