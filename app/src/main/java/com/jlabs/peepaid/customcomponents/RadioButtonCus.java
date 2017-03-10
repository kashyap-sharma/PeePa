package com.jlabs.peepaid.customcomponents;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.RadioButton;

/**
 * Created by JLabs on 05/31/16.
 */
public class RadioButtonCus extends RadioButton {
    public RadioButtonCus(Context context) {
        super(context);
    }

    public RadioButtonCus(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RadioButtonCus(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public RadioButtonCus(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
//
//    @Override
//    protected void onFinishInflate() {
//        super.onFinishInflate();
//        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService( Context. LAYOUT_INFLATER_SERVICE );
//        View v = inflater.inflate(R.layout.custom_button1,null);
//        return v;
//    }


}
