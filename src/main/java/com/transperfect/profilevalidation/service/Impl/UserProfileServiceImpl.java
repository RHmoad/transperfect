package com.transperfect.profilevalidation.service.Impl;

import com.transperfect.profilevalidation.model.UserProfile;
import com.transperfect.profilevalidation.repository.UserProfileRepository;
import com.transperfect.profilevalidation.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    UserProfileRepository userProfileRepository;

    @Override
    public UserProfile save(UserProfile userProfile) {
        return userProfileRepository.save(userProfile);
    }

    @Override
    public List<UserProfile> getUsersProfiles() {
        return userProfileRepository.findAll();
    }

    @Override
    public Optional<UserProfile> userProfileExist(Long id) {

        return userProfileRepository.findById(id);

    }

    @Override
    public boolean isUserProfileExist(Long id) {
        return userProfileRepository.existsById(id);
    }
}
