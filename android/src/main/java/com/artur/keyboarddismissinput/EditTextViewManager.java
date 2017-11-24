package com.artur.keyboarddismissinput;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputType;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.uimanager.BaseViewManager;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class EditTextViewManager extends SimpleViewManager<EditTextViewManager.MyEditText> {

    public static final String REACT_CLASS = "MyEditText";

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    private ThemedReactContext context;
    private MyEditText thisEditText;
    private int lines = 1;
    private boolean simulatedEdition = false;
    @Override
    protected MyEditText createViewInstance(final ThemedReactContext reactContext) {
        context = reactContext;
        thisEditText = new MyEditText(reactContext);
        thisEditText.setTextSize(14);
        thisEditText.setHint("Type a message...");
        thisEditText.setBackgroundResource(android.R.color.transparent);

        Typeface face;
        face = Typeface.createFromAsset(reactContext.getAssets(), "fonts/CircularStd-Book.otf");
        thisEditText.setTypeface(face);
        thisEditText.setSingleLine(false);
        thisEditText.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);

        thisEditText.setHintTextColor(ContextCompat.getColor(context, R.color.text));
        thisEditText.setTextColor(Color.BLACK);
        thisEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS | InputType.TYPE_TEXT_FLAG_MULTI_LINE);


        thisEditText.setVerticalScrollBarEnabled(true);
        thisEditText.setMovementMethod(ScrollingMovementMethod.getInstance());
        thisEditText.setScrollBarStyle(View.SCROLLBARS_INSIDE_INSET);

        int paddingPixel = 20;
        float density = context.getResources().getDisplayMetrics().density;
        int paddingDp = (int)(paddingPixel * density);
        thisEditText.setPadding(paddingDp,0,paddingDp,0);
        thisEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(view.getId() == thisEditText.getId() && b){
                    context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class) .emit("EditTextFocused", 1);
                }
            }
        });

        thisEditText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                if(lines != thisEditText.getLineCount() && thisEditText.getLineCount() != 0){
                    lines = thisEditText.getLineCount();
                    context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class) .emit("EditTextLinesChanged",  lines);
                }
                if(setSelectionToEnd){
                    setSelectionToEnd = false;
                    thisEditText.setSelection(thisEditText.getText().length());
                }
            }

            public void beforeTextChanged(CharSequence cs, int s, int c, int a) {}

            public void onTextChanged(CharSequence cs, int s, int b, int c) {
                if(!simulatedEdition) {
                    context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit("EditTextChangedText", cs.toString());
                }
                simulatedEdition = false;

            }
        });
        return thisEditText;
    }
    @Override
    public void onDropViewInstance(MyEditText view) {
        super.onDropViewInstance(view);
    }


    @ReactProp(name = "customval")
    public void setDay(MyEditText view, String getet) {
    }

    @ReactProp(name = "blured")
    public void blured(MyEditText view, boolean blur) {
        if(blur){
            thisEditText.clearFocus();
            InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private boolean setSelectionToEnd = false;

    @ReactProp(name = "text")
    public void text(MyEditText view, String text) {
        if(text.length() == 0 && this.thisEditText.getText().toString().length() != 0){
            thisEditText.getText().clear();
        }else if(text.length() != 0 && !text.equals(thisEditText.getText().toString())){
            Editable editable = new SpannableStringBuilder(text);
            thisEditText.setText(editable, TextView.BufferType.EDITABLE);
            BaseInputConnection inputConnection = new BaseInputConnection(thisEditText, true);
            inputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_A));

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    thisEditText.setSelection(thisEditText.getText().length());
                    simulatedEdition = true;
                    thisEditText.dispatchKeyEvent(new KeyEvent(1, 0, KeyEvent.ACTION_DOWN,
                            KeyEvent.KEYCODE_SPACE, 0));
                    thisEditText.dispatchKeyEvent(new KeyEvent(2, 0, KeyEvent.ACTION_UP,
                            KeyEvent.KEYCODE_SPACE, 0));
                    thisEditText.dispatchKeyEvent(new KeyEvent(1, 0, KeyEvent.ACTION_DOWN,
                            KeyEvent.KEYCODE_DEL, 0));
                    thisEditText.dispatchKeyEvent(new KeyEvent(2, 0, KeyEvent.ACTION_UP,
                            KeyEvent.KEYCODE_DEL, 0));
                }
            }, 0);

        }
    }



    /*@ReactMethod
    public void someMethod(final int viewId, final String str) {
        withMyView(viewId, promise, new MyViewHandler() {
            @Override
            public void handle(MyView view) {
                String value = view.someMethod();
                promise.resolve(value)
            }
        });
    }*/



    @Override
    public void receiveCommand( MyEditText view, int commandType, @Nullable ReadableArray args) {
        //Assertions.assertNotNull(view);
        //Assertions.assertNotNull(args);
        switch (commandType) {
            case COMMAND_BLUR: {
                thisEditText.clearFocus();
                InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                return;
            }
            case COMMAND_FOCUS:{
                //Toast.makeText(context, "FOUCS GET", Toast.LENGTH_SHORT).show();
                thisEditText.requestFocus();

                thisEditText.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        InputMethodManager keyboard = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
                        keyboard.showSoftInput(thisEditText, 0);
                    }
                },200);
                return;
            }
            default:

        }
    }


    public static final int COMMAND_BLUR = 1;
    public static final int COMMAND_FOCUS = 2;

    @Override
    public Map<String,Integer> getCommandsMap() {
        Log.d("React"," View manager getCommandsMap:");
        return MapBuilder.of(
                "blur",
                COMMAND_BLUR, "focus", COMMAND_FOCUS);
    }


    public class MyEditText extends android.support.v7.widget.AppCompatEditText {

        public MyEditText(Context context) {
            super(context);
            this.context = context;
            init();
        }

        public MyEditText(Context context, AttributeSet attrs) {
            super(context, attrs);
            this.context = context;
            init();
        }

        public MyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            this.context = context;
            init();
        }
        private Context context;

        private void init(){

        }

        @Override
        public boolean onKeyPreIme (int keyCode, KeyEvent event)
        {
            //Toast.makeText(context, "bakc pressde", Toast.LENGTH_SHORT).show();
            if(keyCode == KeyEvent.KEYCODE_BACK){
              //  Toast.makeText(context, "Kliace Keyboard", Toast.LENGTH_SHORT).show();
                sendEvent("keyboardWillHide", 1);
            }
            return false;
        }

        private void sendEvent(String eventName, Object params) {
            ((ReactContext)getContext())
                    .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class) .emit(eventName, params);
        }
    }
}
