package ym.nemo233.bookstore.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ContentSwitchView extends FrameLayout implements BookContentView.BookContentController {
    private int screenWidth = 0;
    private long animDuration = 300;
    private ReaderController readerController;
    private int scrollX;//切换页面滑动阀值
    private Long beforeShowMenu = 0L;
    private boolean isShowMenu = false;
    //
    private int state = NONE;
    //
    private static final int NONE = -1;
    private static final int PRE_AND_NEXT = 0;
    private static final int ONLY_PRE = 1;
    private static final int ONLY_NEXT = 2;
    //
    public static final int DURPAGEINDEXBEGIN = -1;
    public static final int DURPAGEINDEXEND = -2;
    //
    private BookContentView durPageView;
    private List<BookContentView> viewContents;
    //
    private OnBookReadInitListener bookReadInitListener;


    public interface OnBookReadInitListener {
        void success();

        void initData(int lineCount);

        void updateProgress(int durChapterIndex, int durPageIndex);

        void noPrePage();

        void noNextPage();

        String getChapterTitle(int chapterIndex);

        void showOrHideMenu(boolean isShowMenu);

        void loadData(BookContentView bookContentView, int durChapterIndex, int durPageIndex);

        void noChapter(int durChapterIndex, int chapterAll);
    }

    public ContentSwitchView(@NonNull Context context) {
        super(context);
        init();
    }

    public ContentSwitchView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ContentSwitchView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public ContentSwitchView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        //
        readerController = ReaderController.getInstance();
        readerController.init(getContext());

        scrollX = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30f, getContext().getResources().getDisplayMetrics());
        durPageView = new BookContentView(getContext());
        durPageView.setReaderController(readerController);

        viewContents = new ArrayList<>();
        viewContents.add(durPageView);

        addView(durPageView);
    }

    public void bookReaderInit(OnBookReadInitListener bookReadInitListener) {
        this.bookReadInitListener = bookReadInitListener;
        durPageView.getBookTextView().getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);
    }

    private int durHeight = 0;

    public void startLoading() {
        int height = durPageView.getBookTextView().getHeight();
        if (height > 0) {
            if (bookReadInitListener != null && durHeight != height) {
                durHeight = height;
                bookReadInitListener.initData(durPageView.getLineCount(height));
            }
        }
        durPageView.getBookTextView().getViewTreeObserver().addOnGlobalLayoutListener(layoutListener);
    }

    private ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            if (bookReadInitListener != null) {
                bookReadInitListener.success();
            }
            durPageView.getBookTextView().getViewTreeObserver().removeOnGlobalLayoutListener(onGlobalLayoutListener);
        }
    };

    private ViewTreeObserver.OnGlobalLayoutListener layoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {//主要用来获取控件高度
            int height = durPageView.getBookTextView().getHeight();
            if (height > 0) {
                if (bookReadInitListener != null && durHeight > height) {
                    durHeight = height;
                    bookReadInitListener.initData(durPageView.getLineCount(height));
                }
            }
        }
    };

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (viewContents.size() > 0) {
            if (state == NONE) {
                viewContents.get(0).layout(0, top, getWidth(), bottom);
            } else if (state == PRE_AND_NEXT && viewContents.size() >= 3) {
                viewContents.get(0).layout(-getWidth(), top, 0, bottom);
                viewContents.get(1).layout(0, top, getWidth(), bottom);
                viewContents.get(2).layout(0, top, getWidth(), bottom);
            } else if (state == ONLY_PRE && viewContents.size() >= 2) {
                viewContents.get(0).layout(-getWidth(), top, 0, bottom);
                viewContents.get(1).layout(0, top, getWidth(), bottom);
            } else if (viewContents.size() >= 2) {
                viewContents.get(0).layout(0, top, getWidth(), bottom);
                viewContents.get(1).layout(0, top, getWidth(), bottom);
            }
        } else {
            super.onLayout(changed, left, top, right, bottom);
        }
    }

    private boolean isMoving = false;//手指是否在滑动

    private float startX = -1;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (!isMoving) {
            int durWidth = screenWidth > 1400 ? 40 : 0;
//            int durWidth = screenWidth / 6;
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    startX = event.getRawX();
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (viewContents.size() > 1) {
                        if (startX == -1) {
                            startX = event.getRawX();
                        }
                        if (null != bookReadInitListener && isShowMenu) {
                            isShowMenu = false;
                            bookReadInitListener.showOrHideMenu(false);
                        }
                        int durX = (int) (event.getRawX() - startX);
                        if (durX > durWidth) {
                            durX = durX - durWidth;
                        } else if (durX < -durWidth) {
                            durX = durX + durWidth;
                        } else {
                            durX = 0;
                        }
                        //
                        if (durX > 0 && (state == PRE_AND_NEXT || state == ONLY_PRE)) {//预览上一页
                            int tempX = durX - getWidth();
                            if (tempX < -getWidth()) {
                                tempX = -getWidth();
                            } else if (tempX > 0) {
                                tempX = 0;
                            }
                            viewContents.get(0).layout(tempX, viewContents.get(0).getTop(), tempX + getWidth(), viewContents.get(0).getBottom());
                        } else if (durX < 0 && (state == PRE_AND_NEXT || state == ONLY_NEXT)) {//预览下一页
                            int tempX = durX;
                            if (tempX > 0)
                                tempX = 0;
                            else if (tempX < -getWidth())
                                tempX = -getWidth();
                            int tempIndex = (state == PRE_AND_NEXT ? 1 : 0);
                            viewContents.get(tempIndex).layout(tempX, viewContents.get(tempIndex).getTop(), tempX + getWidth(), viewContents.get(tempIndex).getBottom());
                        }
                    }
                    break;
                case MotionEvent.ACTION_CANCEL://小米8长按传送门会引导手势进入action_cancel
                case MotionEvent.ACTION_UP:
                    if (startX == -1) {
                        startX = event.getRawX();
                    }
                    if (event.getRawX() - startX > durWidth) {
                        if (state == PRE_AND_NEXT || state == ONLY_PRE) {
                            if (event.getRawX() - startX + durWidth > scrollX) {
                                movingSuccessAnim(viewContents.get(0), 0);
                            } else {
                                movingFailAnim(viewContents.get(0), -getWidth());
                            }
                        } else {
                            if (null != bookReadInitListener) {
                                bookReadInitListener.noPrePage();
                            }
                        }
                    } else if (event.getRawX() - startX < -durWidth) {
                        if (state == PRE_AND_NEXT || state == ONLY_NEXT) {
                            int tempIndex = (state == PRE_AND_NEXT ? 1 : 0);
                            if (startX - event.getRawX() - durWidth > scrollX) {
                                movingSuccessAnim(viewContents.get(tempIndex), -getWidth());
                            } else {
                                movingFailAnim(viewContents.get(tempIndex), 0);
                            }
                        } else {
                            if (null != bookReadInitListener) {
                                bookReadInitListener.noNextPage();
                            }
                        }
                    } else {//点击事件
                        if (readerController.getCanClickTurn() && readerController.getTurnThePageModel() == 1 && event.getRawX() <= getWidth() * 0.33f) {//支持点击翻页
                            if (state == PRE_AND_NEXT || state == ONLY_PRE) {
                                movingSuccessAnim(viewContents.get(0), 0);
                            } else {
                                if (null != bookReadInitListener)
                                    bookReadInitListener.noPrePage();
                            }
                        } else if (readerController.getCanClickTurn() && readerController.getTurnThePageModel() == 1 && event.getRawX() >= getWidth() * 0.66f) {//川字型/下一页
                            if (state == PRE_AND_NEXT || state == ONLY_NEXT) {
                                int tempIndex = (state == PRE_AND_NEXT ? 1 : 0);
                                movingSuccessAnim(viewContents.get(tempIndex), -getWidth());
                            } else {
                                if (null != bookReadInitListener)
                                    bookReadInitListener.noNextPage();
                            }
                        } else if (readerController.getCanClickTurn() && readerController.getTurnThePageModel() == 2 && event.getRawY() <= getHeight() * 0.33f) {//三字型 上一页
                            if (state == PRE_AND_NEXT || state == ONLY_PRE) {
                                movingSuccessAnim(viewContents.get(0), 0);
                            } else {
                                if (null != bookReadInitListener)
                                    bookReadInitListener.noPrePage();
                            }
                        } else if (readerController.getCanClickTurn() && readerController.getTurnThePageModel() == 2 && event.getRawY() >= getHeight() * 0.66f) {//三字型 下一页
                            if (state == PRE_AND_NEXT || state == ONLY_NEXT) {
                                int tempIndex = (state == PRE_AND_NEXT ? 1 : 0);
                                movingSuccessAnim(viewContents.get(tempIndex), -getWidth());
                            } else {
                                if (null != bookReadInitListener)
                                    bookReadInitListener.noNextPage();
                            }
                        } else {//呼出菜单&支持翻页时,点击中部呼出菜单
                            if (null != bookReadInitListener) {
                                long now = System.currentTimeMillis();
                                if (now - beforeShowMenu > 200) {
                                    isShowMenu = !isShowMenu;
                                    bookReadInitListener.showOrHideMenu(isShowMenu);
                                    beforeShowMenu = now;
                                }
                            }
                        }
                    }
                    startX = -1;
                    break;
                default:
                    break;
            }
        }
        return true;
    }

    private void movingFailAnim(final View view, int orderX) {
        if (null != view) {
            long temp = Math.abs(view.getLeft() - orderX) / (getWidth() / animDuration);
            ValueAnimator tempAnim = ValueAnimator.ofInt(view.getLeft(), orderX);
            tempAnim.setDuration(temp);
            tempAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int value = (int) valueAnimator.getAnimatedValue();
                    view.layout(value, view.getTop(), value + getWidth(), view.getBottom());
                }
            });
            tempAnim.start();
        }
    }

    private void movingSuccessAnim(final View view, final int orderX) {
        if (null != view) {
            long temp = Math.abs(view.getLeft() - orderX) / (getWidth() / animDuration);
            ValueAnimator tempAnim = ValueAnimator.ofInt(view.getLeft(), orderX);
            tempAnim.setDuration(temp);
            tempAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int value = (int) valueAnimator.getAnimatedValue();
                    view.layout(value, view.getTop(), value + getWidth(), view.getBottom());
                }
            });
            tempAnim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                    isMoving = true;
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    isMoving = false;
                    if (orderX == 0) {//上一页
                        durPageView = viewContents.get(0);
                        if (state == PRE_AND_NEXT) {
                            ContentSwitchView.this.removeView(viewContents.get(viewContents.size() - 1));
                            viewContents.remove(viewContents.size() - 1);
                        }
                        state = ONLY_NEXT;
                        if (durPageView.getDurChapterIndex() - 1 >= 0 || durPageView.getDurPageIndex() - 1 >= 0) {
                            addPrePage(durPageView.getDurChapterIndex(), durPageView.getChapterAll(), durPageView.getDurPageIndex(), durPageView.getPageAll());
                            if (state == NONE) {
                                state = ONLY_PRE;
                            } else state = PRE_AND_NEXT;
                        }
                    } else {//下一页
                        if (state == ONLY_NEXT) {
                            durPageView = viewContents.get(1);
                        } else {
                            durPageView = viewContents.get(2);
                            ContentSwitchView.this.removeView(viewContents.get(0));
                            viewContents.remove(0);
                        }
                        state = ONLY_PRE;
                        if (durPageView.getDurChapterIndex() + 1 <= durPageView.getChapterAll() - 1 || durPageView.getDurPageIndex() + 1 <= durPageView.getPageAll() - 1) {
                            addNextPage(durPageView.getDurChapterIndex(), durPageView.getChapterAll(), durPageView.getDurPageIndex(), durPageView.getPageAll());
                            if (state == NONE) {
                                state = ONLY_NEXT;
                            } else state = PRE_AND_NEXT;
                        }
                    }
                    if (null != bookReadInitListener) {
                        bookReadInitListener.updateProgress(durPageView.getDurChapterIndex(), durPageView.getDurPageIndex());
                    }
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            tempAnim.start();
        }
    }

    private void addNextPage(int durChapterIndex, int chapterAll, int durPageIndex, int pageAll) {
        if (state == ONLY_NEXT || state == PRE_AND_NEXT) {
            int temp = (state == ONLY_NEXT ? 1 : 2);
            if (pageAll > 0 && durPageIndex >= 0 && durPageIndex < pageAll - 1) {
                viewContents.get(temp).loadData(null != bookReadInitListener ? bookReadInitListener.getChapterTitle(durChapterIndex) : "", durChapterIndex, chapterAll, durPageIndex + 1);
            } else {
                viewContents.get(temp).loadData(null != bookReadInitListener ? bookReadInitListener.getChapterTitle(durChapterIndex + 1) : "", durChapterIndex + 1, chapterAll, DURPAGEINDEXBEGIN);
            }
        } else if (state == ONLY_PRE || state == NONE) {
            BookContentView next = new BookContentView(getContext());
            next.setReaderController(readerController);
            next.setContentController(this);
            if (pageAll > 0 && durPageIndex >= 0 && durPageIndex < pageAll - 1) {
                next.loadData(null != bookReadInitListener ? bookReadInitListener.getChapterTitle(durChapterIndex) : "", durChapterIndex, chapterAll, durPageIndex + 1);
            } else {
                next.loadData(null != bookReadInitListener ? bookReadInitListener.getChapterTitle(durChapterIndex + 1) : "", durChapterIndex + 1, chapterAll, DURPAGEINDEXBEGIN);
            }
            viewContents.add(next);
            this.addView(next, 0);
        }
    }

    private void addPrePage(int durChapterIndex, int chapterAll, int durPageIndex, int pageAll) {
        if (state == ONLY_NEXT || state == NONE) {
            BookContentView pre = new BookContentView(getContext());
            pre.setContentController(this);
            pre.setReaderController(readerController);
            if (pageAll > 0 && durPageIndex > 0) {
                String title = null != bookReadInitListener ? bookReadInitListener.getChapterTitle(durChapterIndex) : "";
                pre.loadData(title, durChapterIndex, chapterAll, durPageIndex - 1);
            } else {
                String title = null != bookReadInitListener ? bookReadInitListener.getChapterTitle(durChapterIndex - 1) : "";
                pre.loadData(title, durChapterIndex - 1, chapterAll, DURPAGEINDEXEND);
            }
            viewContents.add(0, pre);
            this.addView(pre);
        } else if (state == ONLY_PRE || state == PRE_AND_NEXT) {
            if (pageAll > 0 && durPageIndex > 0) {
                String title = null != bookReadInitListener ? bookReadInitListener.getChapterTitle(durChapterIndex) : "";
                viewContents.get(0).loadData(title, durChapterIndex, chapterAll, durPageIndex - 1);
            } else {
                String title = null != bookReadInitListener ? bookReadInitListener.getChapterTitle(durChapterIndex - 1) : "";
                viewContents.get(0).loadData(title, durChapterIndex - 1, chapterAll, DURPAGEINDEXEND);
            }
        }
    }

    public int getCurrentChapterIndex() {
        return durPageView.getDurChapterIndex();
    }

    public void moveToPreChapter(int pageAll) {
        if (state == PRE_AND_NEXT || state == ONLY_PRE) {
            if (null != bookReadInitListener) {
                if (durPageView.getDurChapterIndex() > 0) {
                    int durChapterIndex = durPageView.getDurChapterIndex() - 1;
                    bookReadInitListener.loadData(durPageView, durChapterIndex, 0);
                    updateOtherPage(durPageView.getDurChapterIndex(), durPageView.getChapterAll(), 0, pageAll);
                } else {
                    state = ONLY_NEXT;
                }
                bookReadInitListener.noChapter(durPageView.getDurChapterIndex(), durPageView.getChapterAll());
            }
        } else {
            if (null != bookReadInitListener) {
                bookReadInitListener.noChapter(durPageView.getDurChapterIndex(), durPageView.getChapterAll());
            }
        }
    }

    public void moveToNextChapter(int pageAll) {
        if (state == PRE_AND_NEXT || state == ONLY_NEXT) {
            if (null != bookReadInitListener) {
                if (durPageView.getDurChapterIndex() < durPageView.getChapterAll() - 1) {
                    int durChapterIndex = durPageView.getDurChapterIndex() + 1;
                    bookReadInitListener.loadData(durPageView, durChapterIndex, 0);
                    updateOtherPage(durChapterIndex, durPageView.getChapterAll(), 0, pageAll);
                } else {
                    state = ONLY_PRE;
                }
                bookReadInitListener.noChapter(durPageView.getDurChapterIndex(), durPageView.getChapterAll());
            }
        } else {
            if (null != bookReadInitListener) {
                bookReadInitListener.noChapter(durPageView.getDurChapterIndex(), durPageView.getChapterAll());
            }
        }
    }

    @Override
    public void loadData(BookContentView bookContentView, int durChapterIndex, int durPageIndex) {
        if (null != bookReadInitListener) {
            bookReadInitListener.loadData(bookContentView, durChapterIndex, durPageIndex);
        }
    }

    @Override
    public void setDataFinish(BookContentView bookContentView, int durChapterIndex, int chapterAll, int durPageIndex, int pageAll, int fromPageIndex) {
        if (null != durPageView && bookContentView == durPageView && chapterAll > 0 && pageAll > 0) {
            updateOtherPage(durChapterIndex, chapterAll, durPageIndex, pageAll);
        }
    }

    private void updateOtherPage(int durChapterIndex, int chapterAll, int durPageIndex, int pageAll) {// 0/6 0/8
        Log.i("[log-position]", String.format("state=%d | durChapterIndex in %d/%d | page in %d/%d", state, durChapterIndex, chapterAll, durPageIndex, pageAll));
        if (chapterAll > 1 || pageAll > 1) {
            if ((durChapterIndex == 0 && pageAll == -1) || (durChapterIndex == 0 && durPageIndex == 0 && pageAll != -1)) {
                Log.i("[log-position]", "加载next page");
                addNextPage(durChapterIndex, chapterAll, durPageIndex, pageAll);
                if (state == ONLY_PRE || state == PRE_AND_NEXT) {
                    this.removeView(viewContents.get(0));
                    viewContents.remove(0);
                }
                state = ONLY_NEXT;
            } else if ((durChapterIndex == chapterAll - 1 && pageAll == -1) || (durChapterIndex == chapterAll - 1 && durPageIndex == pageAll - 1 && pageAll != -1)) {
                //ONLYPRE
                Log.i("[log-position]", "加载pre page");
                addPrePage(durChapterIndex, chapterAll, durPageIndex, pageAll);
                if (state == ONLY_NEXT || state == PRE_AND_NEXT) {
                    this.removeView(viewContents.get(2));
                    viewContents.remove(2);
                }
                state = ONLY_PRE;
            } else {
                //PREANDNEXT
                Log.i("[log-position]", "加载 pre and next page");
                addNextPage(durChapterIndex, chapterAll, durPageIndex, pageAll);
                addPrePage(durChapterIndex, chapterAll, durPageIndex, pageAll);
                state = PRE_AND_NEXT;
            }
        } else {
            //NONE
            if (state == ONLY_PRE) {
                this.removeView(viewContents.get(0));
                viewContents.remove(0);
            } else if (state == ONLY_NEXT) {
                this.removeView(viewContents.get(1));
                viewContents.remove(1);
            } else if (state == PRE_AND_NEXT) {
                this.removeView(viewContents.get(0));
                this.removeView(viewContents.get(2));
                viewContents.remove(2);
                viewContents.remove(0);
            }
            state = NONE;
        }
    }

    public void changeBackground() {
        for (BookContentView item : viewContents) {
            item.setBg(readerController);
        }
    }

    public void changeTextSize() {
        for (BookContentView item : viewContents) {
            item.setTextKind(readerController);
        }
        bookReadInitListener.initData(durPageView.getLineCount(durHeight));
    }

    public int getLineCount() {
        return durPageView.getLineCount(durHeight);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (readerController.getCanKeyTurn() && keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            if (state == PRE_AND_NEXT || state == ONLY_NEXT) {
                movingSuccessAnim(viewContents.get(state == PRE_AND_NEXT ? 1 : 0), -getWidth());
            } else {
                if (null != bookReadInitListener)
                    bookReadInitListener.noNextPage();
            }
            return true;
        } else if (readerController.getCanKeyTurn() && keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            if (state == PRE_AND_NEXT || state == ONLY_PRE) {
                movingSuccessAnim(viewContents.get(0), 0);
            } else {
                if (null != bookReadInitListener)
                    bookReadInitListener.noPrePage();
            }
        }
        return onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (readerController.getCanKeyTurn() && keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            return true;
        } else if (readerController.getCanKeyTurn() && keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    public void setInitData(int durChapterIndex, int chapterAll, int durPageIndex) {
        updateOtherPage(durChapterIndex, chapterAll, durPageIndex, -1);
        durPageView.setContentController(this);
        durPageView.loadData(null != bookReadInitListener ? bookReadInitListener.getChapterTitle(durChapterIndex) : "", durChapterIndex, chapterAll, durPageIndex);
        if (null != bookReadInitListener) {
            bookReadInitListener.updateProgress(durPageView.getDurChapterIndex(), durPageView.getDurPageIndex());
        }
    }

    public TextPaint getTextPaint() {
        return durPageView.getBookTextView().getPaint();
    }

    public int getContentWidth() {
        return durPageView.getBookTextView().getWidth();
    }

    public void loadError() {
        if (durPageView != null) {
            durPageView.loadError();
        }
    }
}
