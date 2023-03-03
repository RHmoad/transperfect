package com.transperfect.profilevalidation.controller;

import com.transperfect.profilevalidation.model.UserProfile;
import com.transperfect.profilevalidation.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v1")
public class UserProfileController {


    @Autowired
    UserProfileService userProfileService;

    // the get mapping for testing the save users into the database.
    @GetMapping("/profile")
    public List<UserProfile> getUsersProfiles() {
        return userProfileService.getUsersProfiles();
    }

    // The post method
    @PostMapping("/profile")
    public ResponseEntity<UserProfile> createUserProfile(@Valid @RequestBody UserProfile userProfile) {

        return ResponseEntity.ok(userProfileService.save(userProfile));
    }

    @PutMapping("/profile/{id}")
    public ResponseEntity<UserProfile> updateUserProfile(@PathVariable Long id, @Valid @RequestBody UserProfile userProfile) {
        if (userProfileService.isUserProfileExist(id)) {
            userProfile.setId(id);
            return ResponseEntity.ok(userProfileService.save(userProfile));
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public Map<String, String> handleValidationExceptions(
            Exception exception) {
        Map<String, String> errors = new HashMap<>();
        if (exception instanceof MethodArgumentNotValidException) {
            var ex = (MethodArgumentNotValidException) exception;
            ex.getBindingResult().getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
        }
        if (exception instanceof ConstraintViolationException) {
            var ex = (ConstraintViolationException) exception;
            for (ConstraintViolation violation : ex.getConstraintViolations()) {
                errors.put(
                        violation.getPropertyPath().toString(), violation.getMessage());
            }
        }

        return errors;
    }


}
