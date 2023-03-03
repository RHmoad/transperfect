package com.transperfect.profilevalidation.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Pattern;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "BACHELOR|MASTER|ASSOCIATE|DOCTORAL", flags = Pattern.Flag.CASE_INSENSITIVE, message = "The degree isn't valid")
    private String degree;

    @Pattern(regexp = "COMPUTER_SCIENCE|SOFTWARE_ENGINEER|DATA_SCIENCE|FINANCE|RESEARCH_DEVELOPMENT", flags = Pattern.Flag.CASE_INSENSITIVE, message = "The speciality isn't valid")
    private String speciality;
}
