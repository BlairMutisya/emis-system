package com.eduvod.eduvod.model.superadmin;

import com.eduvod.eduvod.model.shared.BaseEntity;
import com.eduvod.eduvod.model.shared.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "schools")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class School extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String moeRegNo;

    private String kpsaRegNo;
    private String name;
    @ManyToOne
    @JoinColumn(name = "curriculum_id")
    private CurriculumType curriculum;

    @Column(name = "curriculum")
    private String curriculumName;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private SchoolCategory category;

    @Column(name = "category")
    private String categoryName;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private SchoolType type;

    @Column(name = "school_type")
    private String typeName;

    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region;

    @Column(name = "region")
    private String regionName;

    @ManyToOne
    @JoinColumn(name = "county_id")
    private County county;

    @Column(name = "county")
    private String countyName;

    @ManyToOne
    @JoinColumn(name = "sub_county_id")
    private SubCounty subCounty;

    @Column(name = "sub_county")
    private String subCountyName;

    private String location;
    private String phone;
    private String email;
    private String address;
    private String website;
    private String composition;
    private String logoUrl;

    @ManyToMany
    @JoinTable(
            name = "school_admins",
            joinColumns = @JoinColumn(name = "school_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> admins = new HashSet<>();
}
