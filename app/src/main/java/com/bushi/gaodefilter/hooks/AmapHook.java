package com.bushi.gaodefilter.hooks;

import android.view.View;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class AmapHook implements BaseHook {

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {

        if (!lpparam.packageName.equals("com.autonavi.minimap")) {
            return;
        }

        XposedHelpers.findAndHookMethod(View.class, "onAttachedToWindow", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                View view = (View) param.thisObject;
                String className = view.getClass().getName();

                if (className.contains("ajxtemplate.AjxTemplateListView")) {
                    view.setVisibility(View.GONE);
                }


                try {
                    int lottieId = view.getResources().getIdentifier("widget_lottie", "id", "com.autonavi.minimap");
                    if (view.getId() == lottieId) {
                        view.setVisibility(View.GONE);
                    }
                } catch (Exception ignored) {
                }

                Object tag = view.getTag();
                if (tag != null && tag.toString().contains("lottieUrl") && tag.toString().contains("3746d8f8a")) {
                    view.setVisibility(View.GONE);
                }
            }
        });
    }
}
