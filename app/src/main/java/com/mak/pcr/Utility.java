package com.mak.pcr;

import android.content.Context;
import android.widget.Toast;

public class Utility {
    public static void MakeToast(Context context, String msg, final int duration){
        Toast.makeText(context, msg, duration).show();
    }
}

