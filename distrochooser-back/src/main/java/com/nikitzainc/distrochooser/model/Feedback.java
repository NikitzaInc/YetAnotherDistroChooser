package com.nikitzainc.distrochooser.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 300)
    @NotNull
    @Column(name = "result_link", nullable = false, length = 300)
    private String resultLink;

    @Size(max = 20)
    @Column(name = "selected_distro", length = 20)
    private String selectedDistro;

}