/*
 * Developed by Razil Minneakhmetov on 11/19/18 9:28 PM.
 * Last modified 11/19/18 9:28 PM.
 * Copyright © 2018. All rights reserved.
 */

package forms;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class PgbenchResultForm implements ResultForm {
    private String transactions;
    private String latencyAverage;
    private String tpsIn;
    private String tpsEx;
}