/*
 * Developed by Razil Minneakhmetov on 11/19/18 11:01 PM.
 * Last modified 11/19/18 11:01 PM.
 * Copyright © 2018. All rights reserved.
 */

package forms;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class LogResult {
    private int iteration;
    private int time;
}