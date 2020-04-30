package ym.nemo233.bookstore.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class BookTextView extends AppCompatTextView {

    public BookTextView(Context context) {
        super(context);
    }

    public BookTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BookTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        TextPaint paint = getPaint();
        paint.setColor(getTextColors().getDefaultColor());
        Layout layout = new StaticLayout(getText(), paint, canvas.getWidth(),
                Layout.Alignment.ALIGN_NORMAL, getLineSpacingMultiplier(),
                getLineSpacingExtra(), false);
        layout.draw(canvas);

    }
}
