package com.nikitzainc.distrochooser.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "distro")
public class Distro {
    //Basic info
    @Id
    @Size(max = 30)
    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Size(max = 255)
    @Column(name = "homepage")
    private String homepage;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @Size(max = 255)
    @Column(name = "distrowatch_url")
    private String distrowatchUrl;

    @Size(max = 255)
    @Column(name = "image_url")
    private String imageUrl;

    //Summary
    @Size(max = 30)
    @Column(name = "based_on", length = 30)
    private String basedOn;

    @Column(name = "architectures")
    private List<String> architectures;

    @Column(name = "categories")
    private List<String> categories;

    @Size(max = 50)
    @Column(name = "popularity", length = 50)
    private String popularity;

    //Packages
    @Size(max = 100)
    @Column(name = "package_management", length = 100)
    private String packageManagement;

    //Resources
    @Size(max = 512)
    @Column(name = "documentation", length = 512)
    private String documentation;

    @Size(max = 512)
    @Column(name = "user_forum", length = 512)
    private String userForum;

    @Column(name = "alt_user_forums")
    private List<String> altUserForums;

    @Size(max = 512)
    @Column(name = "bug_tracker", length = 512)
    private String bugTracker;

    //Other
    @Size(max = 50)
    @Column(name = "last_version", length = 50)
    private String lastVersion;

    @Size(max = 10)
    @Column(name = "release_date", length = 10)
    private String releaseDate;

    @Size(max = 50)
    @Column(name = "origin", length = 50)
    private String origin;

    @Size(max = 50)
    @Column(name = "status", length = 50)
    private String status;
}