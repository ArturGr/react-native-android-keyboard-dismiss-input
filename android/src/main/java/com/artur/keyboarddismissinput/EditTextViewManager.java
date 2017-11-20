package com.artur.keyboarddismissinput;


public class ProgressBarViewManager extends SimpleViewManager<ProgressBar> {

    public static final String REACT_CLASS = "KeyboardDismissEditText";

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    protected ProgressBar createViewInstance(
            ThemedReactContext reactContext) {
        return new ProgressBar(reactContext);
    }
}
