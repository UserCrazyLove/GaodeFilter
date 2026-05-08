package com.bushi.gaodefilter;

import android.app.Application;
import android.content.Context;

import com.bushi.gaodefilter.hooks.AmapHook;
import com.bushi.gaodefilter.hooks.BaseHook;
import com.bushi.gaodefilter.hooks.DilanHook;
import com.bushi.gaodefilter.utils.Log;
import com.bushi.gaodefilter.utils.Prefs;

import java.util.ArrayList;
import java.util.List;

import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_InitPackageResources;
import de.robv.android.xposed.callbacks.XC_LoadPackage;


public class MainHook implements IXposedHookLoadPackage, IXposedHookInitPackageResources {

    private static Context mContext;
    private static int mCachedValue;
    private final List<BaseHook> hooks = new ArrayList<>();

    public MainHook() {
        // 在这里注册所有的功能模块
        hooks.add(new AmapHook());
        hooks.add(new DilanHook());

    }

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (lpparam.packageName.equals("com.bushi.gaodefilter")) {
            XposedHelpers.findAndHookMethod(
                    "com.bushi.gaodefilter.MainActivity",
                    lpparam.classLoader,
                    "isModuleActive",
                    XC_MethodReplacement.returnConstant(true)
            );
            return;
        }

        if (!lpparam.packageName.equals("com.autonavi.minimap")) return;

        Log.i("Module loaded for: " + lpparam.packageName);

        // 初始化上下文和配置
        hookApplicationInit(lpparam);

        // 分发到各个功能模块
        for (BaseHook hook : hooks) {
            try {
                hook.handleLoadPackage(lpparam);
            } catch (Throwable t) {
                Log.e("Error in hook (LoadPackage) " + hook.getClass().getSimpleName() + ": " + t.getMessage());
            }
        }
    }

    @Override
    public void handleInitPackageResources(XC_InitPackageResources.InitPackageResourcesParam resparam) throws Throwable {
        if (!resparam.packageName.equals("com.autonavi.minimap")) return;

        for (BaseHook hook : hooks) {
            try {
                hook.handleInitPackageResources(resparam);
            } catch (Throwable t) {
                Log.e("Error in hook (InitResources) " + hook.getClass().getSimpleName() + ": " + t.getMessage());
            }
        }
    }

    private void hookApplicationInit(XC_LoadPackage.LoadPackageParam lpparam) {
        try {
            XposedHelpers.findAndHookMethod(
                    Application.class,
                    "onCreate",
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            if (mContext == null) {
                                mContext = ((Application) param.thisObject).getApplicationContext();
                                mCachedValue = Prefs.getMinValue(mContext);
                                Log.i("Context initialized. Config value: " + mCachedValue);
                            }
                        }
                    }
            );
        } catch (Throwable t) {
            Log.e("Failed to hook Application.onCreate: " + t.getMessage());
        }
    }
    
    public static Context getContext() {
        return mContext;
    }
    
    public static int getCachedValue() {
        return mCachedValue;
    }
}
