package com.InstaSpehere.Post_Add_Service.service;


import com.InstaSpehere.Post_Add_Service.model.UserprofileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-profile-service", url = "${USER_PROFILE_SERVICE_URL:user-profile-service-url}")
public interface PostServiceClient {



    @GetMapping("/validate/{profileId}")
    UserprofileResponse validateProfile(@PathVariable int profileId);


    // New method to get username by userId
    @GetMapping("/username/{userId}")
    String getUsername(@PathVariable int userId);


}
