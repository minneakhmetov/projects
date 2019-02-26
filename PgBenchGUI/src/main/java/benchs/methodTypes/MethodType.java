/*
 * Developed by Razil Minneakhmetov on 11/19/18 1:38 PM.
 * Last modified 11/19/18 1:35 PM.
 * Copyright © 2018. All rights reserved.
 */

package benchs.methodTypes;

import forms.ResultForm;

import java.io.File;

public interface MethodType {

    void bench(int time, File script);
    ResultForm getResult();
}
