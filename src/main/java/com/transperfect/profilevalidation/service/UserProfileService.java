package com.transperfect.profilevalidation.service;

import com.transperfect.profilevalidation.model.UserProfile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface UserProfileService {
    UserProfile save(UserProfile userProfile);
    List<UserProfile> getUsersProfiles();

    Optional<UserProfile> userProfileExist(Long id);

    boolean isUserProfileExist(Long id);
}
