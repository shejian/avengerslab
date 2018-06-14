package com.avengers.intfacebase;

import com.avengers.zombiebase.UtilProvider;
import com.spinytech.macore.router.LocalRouter;

public class RoaterBaseProviderManager {

    public static void initRegProvider(LocalRouter localRouter) {
        localRouter.registerProvider(UtilProvider.class.getName(), new UtilProvider());
    }

}
