package com.example.miniproject.constants;

import android.content.Context;
import android.content.Intent;

public class Global {
    public static void navigate (Context context,Class<?> nextActivity,boolean isFlagSet){
//        Intent i = new Intent(context, nextActivity);
//        if(isFlagSet) {
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        }
//        context.startActivity(i);
        context.startActivity(new Intent(context,nextActivity).setFlags(isFlagSet ? Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK : 0));
    }
}
