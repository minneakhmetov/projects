/*
 * Developed by Razil Minneakhmetov on 12/26/18 10:10 PM.
 * Last modified 12/26/18 10:10 PM.
 * Copyright © 2018. All rights reserved.
 */

package models;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@EqualsAndHashCode
public class SimilarityUsers {
    int no;
    long id;
    long vkId;
    String name;
    String surname;
    String photoUrl;
    double similarity;
}