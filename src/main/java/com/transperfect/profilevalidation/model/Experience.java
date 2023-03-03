package com.transperfect.profilevalidation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "The title should not be empty")
    private String title;

    @NotBlank(message = "The company should not be empty")
    private String company;

    @Past(message = "The start date should be in the past")
    private LocalDate startDate;

    @PastOrPresent(message ="The start date should be in past or present")
    private LocalDate endDate;

    private Boolean isCurrent;
}
