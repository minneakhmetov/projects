package com.spinach.models;

import lombok.*;

@Getter
@Setter
@Data
@EqualsAndHashCode
@Builder
public class UserShortModel {
    long id;
    String photoUrl;
    String firstName;
    String lastName;
}
