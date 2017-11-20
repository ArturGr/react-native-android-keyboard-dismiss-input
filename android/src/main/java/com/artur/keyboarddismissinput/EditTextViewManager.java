package com.artur.keyboarddismissinput;


import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.facebook.react.uimanager.BaseViewManager;
import com.facebook.react.uimanager.ThemedReactContext;

public class EditTextViewManager extends SimpleViewManager<EditText> {

    public static final String REACT_CLASS = "KeyboardDismissEditText";

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    protected EditText createViewInstance(
            ThemedReactContext reactContext) {
        return new MyEditText(reactContext);
    }

    public class MyEditText extends android.support.v7.widget.AppCompatEditText{

        public MyEditText(Context context) {
            super(context);
            this.context = context;
        }

        public MyEditText(Context context, AttributeSet attrs) {
            super(context, attrs);
            this.context = context;
        }

        public MyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            this.context = context;
        }
        private Context context;

        @Override
        public boolean onKeyPreIme (int keyCode, KeyEvent event)
        {

            // Return true if I handle the event:
            // In my case i want the keyboard to not be dismissible so i simply return true
            // Other people might want to handle the event differently
            System.out.println("Code = " + keyCode);
            System.out.println("onKeyPreIme " +event);
            Toast.makeText(context,"Back pressed, send Event", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
