package com.example.demo.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "companies")
@Getter @Setter
public class Company extends BaseEntity {
    @Column(nullable = false)
    private String name;

    private String description;
    private String website;
    private String location;

    @OneToMany(mappedBy = "company")
    private List<Job> jobs;
}
