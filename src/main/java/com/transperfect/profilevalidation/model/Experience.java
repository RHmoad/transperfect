package com.transperfect.profilevalidation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;
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
    @NotNull(message = "The title should not be empty")
    private String title;

    @NotBlank(message = "The company should not be empty")
    @NotNull(message = "The company should not be empty")
    private String company;

    @Past(message = "The start date should be in the past")
    @NotNull(message = "The start date should be in the past")
    private LocalDate startDate;

    @PastOrPresent(message ="The start date should be in past or present")
    @NotNull(message = "The start date should be in the past")
    private LocalDate endDate;

    private Boolean isCurrent;
}
