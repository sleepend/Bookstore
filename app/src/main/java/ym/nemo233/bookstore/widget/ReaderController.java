package ym.nemo233.bookstore.widget;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ym.nemo233.bookstore.R;

public class ReaderController {
    //
    private static List<Map<String, Integer>> textKind;
    private static List<Map<String, Integer>> textDrawable;
    private static List<Map<String, Typeface>> textTypeface;
    //字体与背景
    private int textSize;
    private int textExtra;
    private int textColor;
    private int textBackground;
    //
    private Typeface typeface;
    //
    private int textKindIndex;
    private int textDrawableIndex;
    //音量键翻页+点击左右边切换
    private boolean canClickTurn;
    private boolean canKeyTurn;
    //翻页方式 0 关闭 1 川字型 2 三字型
    private int turnThePageModel;
    //夜间模式
    private boolean nightMode;//夜间模式
    //
    private SharedPreferences preferences;


    private static ReaderController instance;

    private ReaderController() {
    }

    public void init(Context context) {
        if (null == textKind) {//加载默认设置模板
            textKind = new ArrayList<>();
            int[] textSize = {14, 16, 17, 20, 22};
            float[] textExtra = {6, 7, 8, 10, 12};
            for (int i = 0; i < textSize.length; i++) {
                Map<String, Integer> temp1 = new HashMap<>();
                temp1.put("textSize", textSize[i]);
                temp1.put("textExtra", (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, textExtra[i], context.getResources().getDisplayMetrics()));
                textKind.add(temp1);
            }
        }
        if (null == textDrawable) {
            textDrawable = new ArrayList<>();
            int[] color = {
                    Color.parseColor("#3E3B3D"),
                    Color.parseColor("#5E432E"),
                    Color.parseColor("#22482C"),
                    Color.parseColor("#808080")
            };
            int[] bg = {
                    R.drawable.bg_white,
                    R.drawable.bg_readbook_yellow,
                    R.drawable.bg_readbook_green,
                    R.drawable.bg_readbook_black
            };
            for (int i = 0; i < color.length; i++) {
                Map<String, Integer> temp1 = new HashMap<>();
                temp1.put("textColor", color[i]);
                temp1.put("textBackground", bg[i]);
                textDrawable.add(temp1);
            }
        }
        preferences = context.getSharedPreferences("reader", 0);
        this.textKindIndex = preferences.getInt("textKindIndex", 1);
        this.textSize = textKind.get(this.textKindIndex).get("textSize");
        this.textExtra = textKind.get(this.textKindIndex).get("textExtra");
        this.textDrawableIndex = preferences.getInt("textDrawableIndex", 1);
        this.textColor = textDrawable.get(this.textDrawableIndex).get("textColor");
        this.textBackground = textDrawable.get(this.textDrawableIndex).get("textBackground");

        this.canClickTurn = preferences.getBoolean("canClickTurn", true);
        this.canKeyTurn = preferences.getBoolean("canKeyTurn", true);
        this.turnThePageModel = preferences.getInt("turnThePageModel", 1);
        this.nightMode = preferences.getBoolean("nightMode", false);
    }

    public static ReaderController getInstance() {
        if (instance == null) {
            synchronized (ReaderController.class) {
                if (instance == null) {
                    instance = new ReaderController();
                }
            }
        }
        return instance;
    }

    public boolean getCanClickTurn() {
        return this.canClickTurn;
    }


    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public int getTextExtra() {
        return textExtra;
    }

    public void setTextExtra(int textExtra) {
        this.textExtra = textExtra;
    }

    public int getTextColor() {
        if (null != textDrawable && isNightMode()) {
            return textDrawable.get(3).get("textColor");
        }
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getTextBackground() {
        if (null != textDrawable && isNightMode()) {
            return textDrawable.get(3).get("textBackground");
        }
        return textBackground;
    }

    public void setTextBackground(int textBackground) {
        this.textBackground = textBackground;
    }

    public int getTextKindIndex() {
        return textKindIndex;
    }

    public void setTextKindIndex(int textKindIndex) {
        this.textKindIndex = textKindIndex;
    }

    public int getTextDrawableIndex() {
        return textDrawableIndex;
    }

    public void setTextDrawableIndex(int textDrawableIndex) {
        this.textDrawableIndex = textDrawableIndex;
    }

    public boolean getCanKeyTurn() {
        return this.canKeyTurn;
    }

    public Typeface getTextTypeface() {
        return this.typeface;
    }

    public int getTurnThePageModel() {
        return turnThePageModel;
    }

    public void setTurnThePageModel(int turnThePageModel) {
        this.turnThePageModel = turnThePageModel;
    }

    public boolean isNightMode() {
        return nightMode;
    }

    public void setNightMode(boolean nightMode) {
        this.nightMode = nightMode;
    }

    public void nightModeReversal() {
        this.nightMode = !nightMode;
    }
}
