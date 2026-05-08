package com.bushi.gaodefilter.hooks;

import de.robv.android.xposed.callbacks.XC_InitPackageResources;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public interface BaseHook {
    default void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {}
    default void handleInitPackageResources(XC_InitPackageResources.InitPackageResourcesParam resparam) throws Throwable {}
}
