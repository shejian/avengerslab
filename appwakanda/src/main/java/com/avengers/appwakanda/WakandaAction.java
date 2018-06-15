package com.avengers.appwakanda;

import android.content.Context;

import com.spinytech.macore.MaAction;
import com.spinytech.macore.MaActionResult;

import java.util.HashMap;

public class WakandaAction extends MaAction {
    @Override
    public boolean isAsync(Context context, HashMap<String, String> requestData) {
        return false;
    }

    @Override
    public MaActionResult invoke(Context context, HashMap<String, String> requestData) {
        String key1 = requestData.get("params1");
        String key2 = requestData.get("params2");

        MaActionResult result = new MaActionResult.Builder()
                .code(MaActionResult.CODE_SUCCESS)
                .msg("success")
                .data(key2 + "invoke,from galaxy module" + key1)
                .object(null)
                .build();

        return result;
    }

    @Override
    public MaActionResult invoke(Context context, HashMap<String, String> requestData, Object object) {
        String key1 = requestData.get("params1");
        String key2 = requestData.get("params2");

        MaActionResult result = new MaActionResult.Builder()
                .code(MaActionResult.CODE_SUCCESS)
                .msg("success")
                .data(key2 + "invoke" + key1)
                .object(null)
                .build();

        return result;
    }
}
