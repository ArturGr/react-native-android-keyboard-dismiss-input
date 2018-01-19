package com.artur.keyboarddismissinput;

import com.facebook.react.flat.RCTTextInput;


public class RCTCustomTextInputManager extends CustomEditTextManager {

    /* package */ static final String REACT_CLASS = CustomEditTextManager.REACT_CLASS;

    @Override
    public RCTTextInput createShadowNodeInstance() {
        return new RCTTextInput();
    }

    @Override
    public Class<RCTTextInput> getShadowNodeClass() {
        return RCTTextInput.class;
    }
}