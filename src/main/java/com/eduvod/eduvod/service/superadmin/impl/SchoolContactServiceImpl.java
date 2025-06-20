package com.eduvod.eduvod.service.superadmin.impl;

import com.eduvod.eduvod.dto.request.superadmin.SchoolContactRequest;
import com.eduvod.eduvod.dto.response.superadmin.SchoolContactResponse;
import com.eduvod.eduvod.model.superadmin.SchoolContact;
import com.eduvod.eduvod.repository.superadmin.SchoolContactRepository;
import com.eduvod.eduvod.service.superadmin.SchoolContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SchoolContactServiceImpl implements SchoolContactService {

    private final SchoolContactRepository repository;

    @Override
    public SchoolContactResponse create(SchoolContactRequest request) {
        SchoolContact contact = SchoolContact.builder()
                .moeRegNo(request.getMoeRegNo())
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .designation(request.getDesignation())
                .build();

        contact = repository.save(contact);

        return SchoolContactResponse.builder()
                .id(contact.getId())
                .moeRegNo(contact.getMoeRegNo())
                .name(contact.getName())
                .email(contact.getEmail())
                .phone(contact.getPhone())
                .designation(contact.getDesignation())
                .build();
    }

    @Override
    public List<SchoolContactResponse> getByMoeRegNo(String moeRegNo) {
        return repository.findByMoeRegNo(moeRegNo).stream()
                .map(contact -> SchoolContactResponse.builder()
                        .id(contact.getId())
                        .moeRegNo(contact.getMoeRegNo())
                        .name(contact.getName())
                        .email(contact.getEmail())
                        .phone(contact.getPhone())
                        .designation(contact.getDesignation())
                        .build())
                .collect(Collectors.toList());
    }
}