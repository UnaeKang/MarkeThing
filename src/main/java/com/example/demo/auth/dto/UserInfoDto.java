package com.example.demo.auth.dto;

import com.example.demo.siteuser.entity.SiteUser;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoDto {

    private String nickname;
    private List<String> mannerScore;
    private String profileImg;

//    public SiteUser toEntity(){
//        return SiteUser.builder()
//
//                .build()
//    }



}
