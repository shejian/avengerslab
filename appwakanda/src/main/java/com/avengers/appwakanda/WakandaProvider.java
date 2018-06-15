package com.avengers.appwakanda;

import com.spinytech.macore.MaProvider;

public class WakandaProvider extends MaProvider {


    @Override
    protected void registerActions() {
        registerAction("WakandaAction", new WakandaAction());
    }
}
