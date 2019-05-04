/*
 * Developed by Razil Minneakhmetov on 10/24/18 9:52 PM.
 * Last modified 10/24/18 9:52 PM.
 * Copyright © 2018. All rights reserved.
 */

package com.kursach.models;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Data
@EqualsAndHashCode
@Entity
@Table(name = "user_table")
public class User {
    @Column(name = "name")
    String name;
    @Column(name = "surname")
    String surname;
    @Column(name = "photo_url")
    String photoURL;
    @Id
    @Column(name = "vk_id")
    Integer vkId;
}