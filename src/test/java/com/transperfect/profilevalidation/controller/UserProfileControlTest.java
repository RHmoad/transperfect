package com.transperfect.profilevalidation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.transperfect.profilevalidation.model.UserProfile;
import com.transperfect.profilevalidation.service.UserProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserProfileController.class)
@ActiveProfiles("test")
public class UserProfileControlTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserProfileService userProfileService;

    @Autowired
    private ObjectMapper objectMapper;

    private List<UserProfile> userProfileList;

    @BeforeEach
    void setUp() {
        this.userProfileList = new ArrayList<>();
        this.userProfileList.add(new UserProfile(1L, "maod", "moad.rhannoumi@gmail.com", "Moad123@lm", null, null));
        this.userProfileList.add(new UserProfile(2L, "thierry", "thierry.lagueux@transperfect.com", "Thierry@perfect", null, null));
        this.userProfileList.add(new UserProfile(3L, "user", "user.user@transperfect.com", "Thierry@perfect", null, null));
    }

    @Test
    void shouldFetchAllUsersProfiles() throws Exception {

        given(userProfileService.getUsersProfiles()).willReturn(userProfileList);

        this.mockMvc.perform(get("/api/v1/profile"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(userProfileList.size())));
    }

    @Test
    void shouldCreateNewUserProfile() throws Exception {
        UserProfile userProfile = new UserProfile(1L, "maod", "moad.rhannoumi@gmail.com", "Moad123@lm", null, null);
        given(userProfileService.save(any(UserProfile.class))).willReturn(userProfile);


        this.mockMvc.perform(post("/api/v1/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userProfile)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is(userProfile.getEmail())))
                .andExpect(jsonPath("$.password", is(userProfile.getPassword())))
                .andExpect(jsonPath("$.name", is(userProfile.getName())))
        ;
    }


}
