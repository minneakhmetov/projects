/*
 * Developed by Razil Minneakhmetov on 12/26/18 8:11 PM.
 * Last modified 12/26/18 8:11 PM.
 * Copyright © 2018. All rights reserved.
 */

package models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class Similarity {
    long id;
    long userVkId;
    long friendVkId;
    double similarity;
}