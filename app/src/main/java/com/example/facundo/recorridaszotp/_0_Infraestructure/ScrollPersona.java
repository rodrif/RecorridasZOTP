package com.example.facundo.recorridaszotp._0_Infraestructure;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by GoRodriguez on 13/11/2015.
 */
public class ScrollPersona extends ScrollView {
    public ScrollPersona(Context context) {
        super(context);
    }

    public ScrollPersona(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollPersona(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

/*    public ScrollPersona(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }*/

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return false;
    }
}
