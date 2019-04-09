package com.spinach.models;

import lombok.*;

@Getter
@Setter
@Data
@EqualsAndHashCode
@Builder
public class GroupsShortModel {
    long id;
    String name;
    String activity;
    String photoUrl50;
}
