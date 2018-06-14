package com.avengers.appgalaxy;

import com.spinytech.macore.MaProvider;

public class GalaxyProvider extends MaProvider {


    @Override
    protected void registerActions() {
        registerAction("GalaxyAction", new GalaxyAction());
    }
}
