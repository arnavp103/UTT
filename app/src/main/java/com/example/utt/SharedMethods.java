package com.example.utt;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;

public class SharedMethods {
/** This method collapses the keyboard after the return key is pressed
 *
 * @param context the context of the current fragment
 */
    public static void collapseKeyboard(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager)
                context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0, 0);
    }
}
