package com.transperfect.profilevalidation.repository;

import com.transperfect.profilevalidation.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserProfileRepository extends JpaRepository<UserProfile,Long> {
}
