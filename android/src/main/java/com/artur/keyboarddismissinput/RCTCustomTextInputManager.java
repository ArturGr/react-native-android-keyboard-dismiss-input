package com.artur.keyboarddismissinput;

import com.facebook.react.uimanager.LayoutShadowNode;


public class RCTCustomTextInputManager extends CustomEditTextManager {

    /* package */ static final String REACT_CLASS = CustomEditTextManager.REACT_CLASS;

    @Override
    public LayoutShadowNode createShadowNodeInstance() {
        return new com.facebook.react.views.textinput.ReactTextInputShadowNode();
    }

    @Override
    public Class<? extends LayoutShadowNode> getShadowNodeClass() {
        return com.facebook.react.views.textinput.ReactTextInputShadowNode.class;
    }

}
