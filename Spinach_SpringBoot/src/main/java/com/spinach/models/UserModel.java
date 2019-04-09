package com.spinach.models;


import lombok.*;

@Getter
@Setter
@Data
@EqualsAndHashCode
@Builder
public class UserModel {
    long id;
    Long vkId;
    String about;
    String activities;
    String activity;
    String birthday;
    String books;
    String city;
    String country;
    String photoUrl50;
    String photoUrl100;
    String photoUrl200;
    String photoUrl400;
    String domainVk;
    String facebook;
    String facebookName;
    String facultyName;
    String firstName;
    String instagram;
    String interests;
    String lastName;
    String mobilePhone;
    String movies;
    String music;
    String nickname;
    String quotes;
    String sex;
    String site;
    String skype;
    String status;
    String tv;
    String universityName;
    Integer political;
    String lang;
    String religion;
    String inspiredBy;
    Integer peopleMain;
    Integer lifeMain;
    Integer smoking;
    Integer alcohol;
    String email;
}
