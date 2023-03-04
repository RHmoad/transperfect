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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserProfileController.class)
@ActiveProfiles("test")
public class UserProfileControlTest {

    public static final String NAME_FIELD_INVALID_MESSAGE = "The name field is required and must be between 2 and 50 characters";
    private static final  String PASSWORD_FIELD_INVALID_MESSAGE = "The password must be between 8 and 20 characters long, and must contain at least one uppercase letter, one lowercase letter, one digit, and one special character";
    private static final String EMAIL_FIELD_INVALID_MESSAGE = "The email field is not valid email address";
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


    @Test
    void shouldReturn400WhenCreateNewUserProfileWithInvalidName() throws Exception {
        UserProfile userProfile = new UserProfile(1L, "m", "moad.rhannoumi@gmail.com", "Moad123@lm", null, null);
        given(userProfileService.save(any(UserProfile.class))).willReturn(userProfile);


        this.mockMvc.perform(post("/api/v1/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userProfile)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name", is(NAME_FIELD_INVALID_MESSAGE)))
        ;
    }

    @Test
    void shouldReturn400WhenCreateNewUserProfileWithInvalidEmail() throws Exception {
        UserProfile userProfile = new UserProfile(1L, "moad", "moad.rhannoumi", "Moad123@lm", null, null);
        given(userProfileService.save(any(UserProfile.class))).willReturn(userProfile);


        this.mockMvc.perform(post("/api/v1/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userProfile)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.email", is(EMAIL_FIELD_INVALID_MESSAGE)))
        ;
    }

    @Test
    void shouldReturn400WhenCreateNewUserProfileWithInvalidPassword() throws Exception {
        UserProfile userProfile = new UserProfile(1L, "moad", "moad.rhannoumi@gmail.com", "oad123@lm", null, null);
        given(userProfileService.save(any(UserProfile.class))).willReturn(userProfile);


        this.mockMvc.perform(post("/api/v1/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userProfile)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.password", is(PASSWORD_FIELD_INVALID_MESSAGE)))
        ;
    }

    @Test
    void shouldUpdateUserProfile() throws Exception{
        long userId=1L;
        UserProfile userProfile=new UserProfile(1L,"maod","moad.rhannoumi@gmail.com","Moad123@lm",null,null);
        given(userProfileService.isUserProfileExist(userId)).willReturn(true);
        given(userProfileService.save(any(UserProfile.class))).willReturn(userProfile);

        this.mockMvc.perform(put("/api/v1/profile/{id}",userProfile.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userProfile)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email",is(userProfile.getEmail())))
                .andExpect(jsonPath("$.password",is(userProfile.getPassword())))
                .andExpect(jsonPath("$.name",is(userProfile.getName())));

    }

    @Test
    void shouldReturn400WhenUpdateUserProfileWithUnfoundUserProfile() throws Exception{
        UserProfile userProfile=new UserProfile(1L,"maod","moad.rhannoumi@gmail.com","Moad123@lm",null,null);
        given(userProfileService.isUserProfileExist(anyLong())).willReturn(false);
        this.mockMvc.perform(put("/api/v1/profile/{id}",anyLong())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userProfile)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn400WhenUpdateUserProfileWithInvalidData() throws Exception{

        given(userProfileService.isUserProfileExist(anyLong())).willReturn(true);


        this.mockMvc.perform(put("/api/v1/profile/{id}",Long.MAX_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new UserProfile())))
                .andExpect(status().isBadRequest());

    }






}
