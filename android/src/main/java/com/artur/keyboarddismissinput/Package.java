package com.artur.keyboarddismissinput;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Package implements ReactPackage {
    public Package(){
        //test commit
    }


    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactApplicationContext) {
        return Collections.<NativeModule>singletonList(new Module(reactApplicationContext));
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactApplicationContext) {
        return Arrays.<ViewManager>asList();
    }

    @Override
    public List<ViewManager> createViewManagers(
            ReactApplicationContext reactContext) {
        return Collections.<ViewManager>singletonList(
                new ProgressBarViewManager()
        );
    }


}
