package com.artur.keyboarddismissinput;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.facebook.react.LazyReactPackage;
import com.facebook.react.bridge.ModuleSpec;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.module.model.ReactModuleInfoProvider;
import com.facebook.react.shell.MainPackageConfig;
import com.facebook.react.uimanager.ViewManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CustomTextInputPackage extends LazyReactPackage {


    private MainPackageConfig mConfig;

    public CustomTextInputPackage() {
    }

    /**
     * Create a new package with configuration
     */
    public CustomTextInputPackage(MainPackageConfig config) {
        mConfig = config;
    }


   /* @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Collections.<ViewManager>singletonList( new EditTextViewManager() );
    }*/

    @Override
    public List<ModuleSpec> getNativeModules(final ReactApplicationContext context) {
        return Collections.emptyList();
    }



    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        List<ViewManager> viewManagers = new ArrayList<>();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(reactContext);
        boolean useFlatUi = preferences.getBoolean("flat_uiimplementation", false);
        if (useFlatUi) {
            // Flat managers
            viewManagers.add(new RCTCustomTextInputManager());
        } else {
            // Native equivalents
            viewManagers.add(new CustomEditTextManager());
        }

        return viewManagers;
    }

    @Override
    public ReactModuleInfoProvider getReactModuleInfoProvider() {
        // This has to be done via reflection or we break open source.
        return LazyReactPackage.getReactModuleInfoProviderViaReflection(this);
    }



}
