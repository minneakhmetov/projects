package com.spinach.models;

import lombok.*;

@Getter
@Setter
@Data
@EqualsAndHashCode
@Builder

public class GroupsModel {
    long id;
    long userVkId;
    long userId;
    long groupVkId;
    String name;
    Short isClosed;
    String deactivated;
    String type;
    String photoUrl50;
    String photoUrl100;
    String photoUrl200;
    String activity;
    String city;
    String country;
    String description;
    String status;
    String site;
    Boolean isFavourite;
}
