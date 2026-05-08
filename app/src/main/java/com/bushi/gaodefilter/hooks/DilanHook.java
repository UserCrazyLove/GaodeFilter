package com.bushi.gaodefilter.hooks;

import android.content.res.Resources;
import android.view.View;
import java.util.ArrayList;

import com.bushi.gaodefilter.utils.Log;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class DilanHook implements BaseHook {

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        try {
            XposedHelpers.findAndHookMethod(
                    "android.view.View",
                    lpparam.classLoader,
                    "onAttachedToWindow",
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            View view = (View) param.thisObject;
                            try {
                                Resources res = view.getResources();
                                int id = view.getId();
                                if (id == View.NO_ID) return;
                                
                                String idName = res.getResourceEntryName(id);

                                if ("tab_bar_item_parent".equals(idName)) {
                                    ArrayList<View> outViews = new ArrayList<>();
                                    String[] targetTexts = {"打车", "探索", "长按说话", "语音搜索"};
                                    
                                    for (String text : targetTexts) {
                                        outViews.clear();
                                        view.findViewsWithText(outViews, text, View.FIND_VIEWS_WITH_TEXT);
                                        if (!outViews.isEmpty()) {
                                            view.setVisibility(View.GONE);
                                            break;
                                        }
                                    }
                                }

                                CharSequence contentDesc = view.getContentDescription();
                                if (contentDesc != null) {
                                    String description = contentDesc.toString();
                                    if ("长按说话".equals(description) || "语音搜索".equals(description)) {
                                        view.setVisibility(View.GONE);
                                    }
                                }
                            } catch (Exception e) {
                                // Ignore
                            }
                        }
                    }
            );
            Log.i("DilanHook initialized");
        } catch (Throwable t) {
            Log.e("Failed to init DilanHook: " + t.getMessage());
        }
    }
}
