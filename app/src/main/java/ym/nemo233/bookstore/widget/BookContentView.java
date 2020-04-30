package ym.nemo233.bookstore.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import ym.nemo233.bookstore.R;


public class BookContentView extends FrameLayout {
    private ImageView ivBg;
    private TextView tvTitle;
    private BookTextView bookTextView;
    private TextView tvDatetime, tvPosition;
    private TextView tvError;
    private ProgressBar pbReload;
    //
    private String title;
    //
    private BookContentController contentController;


    public BookContentView(@NonNull Context context) {
        this(context, null);
    }

    public BookContentView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BookContentView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public BookContentView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.view_reader, null, false);
        addView(rootView);
        ivBg = rootView.findViewById(R.id.reader_bg);
        tvTitle = rootView.findViewById(R.id.reader_title);
        bookTextView = rootView.findViewById(R.id.reader_content);
        tvDatetime = rootView.findViewById(R.id.reader_datetime);
        tvPosition = rootView.findViewById(R.id.reader_position);
        pbReload = rootView.findViewById(R.id.reader_progress);
        tvError = rootView.findViewById(R.id.reader_reload);
        tvError.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                loading();
            }
        });
    }

    private int durChapterIndex;
    private int chapterAll;
    private int durPageIndex;
    private int pageAll;

    public void loading() {
        tvError.setVisibility(View.GONE);
        pbReload.setVisibility(View.VISIBLE);
        bookTextView.setVisibility(View.GONE);
        if (contentController != null) {
            contentController.loadData(this, durChapterIndex, durPageIndex);
        }
    }

    public void finishLoading() {
        tvError.setVisibility(View.GONE);
        pbReload.setVisibility(View.GONE);
        bookTextView.setVisibility(View.VISIBLE);
    }

    public void loadError() {
        tvError.setVisibility(View.VISIBLE);
        pbReload.setVisibility(View.GONE);
        bookTextView.setVisibility(View.GONE);
    }

    @SuppressLint("SetTextI18n")
    public void setNoData(String contentLines) {
        tvPosition.setText((this.durPageIndex + 1) + "/" + this.pageAll);
        finishLoading();
    }

    public void updateData(String title, List<String> contentLines, int durChapterIndex, int chapterAll, int durPageIndex, int pageAll) {
        if (contentController != null) {
            contentController.setDataFinish(this, durChapterIndex, chapterAll, durPageIndex, pageAll, this.durPageIndex);
        }
        if (contentLines != null) {
            SpannableStringBuilder ssb = new SpannableStringBuilder();
            for (int i = 0; i < contentLines.size(); i++) {
                ssb.append(contentLines.get(i));
                if (durPageIndex == 0 && i == 0) {//章节第一页特殊化
                    ssb.setSpan(new RelativeSizeSpan(1.5f), 0, contentLines.get(i).length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    ssb.append("\n");
                }
            }
            bookTextView.setText(ssb);
        }
        if (durPageIndex == 0) {
            tvTitle.setVisibility(View.INVISIBLE);
        } else {
            tvTitle.setVisibility(View.VISIBLE);
        }
        this.title = title;
        this.durChapterIndex = durChapterIndex;
        this.chapterAll = chapterAll;
        this.durPageIndex = durPageIndex;
        this.pageAll = pageAll;
        //
        tvTitle.setText(this.title);
        tvPosition.setText((this.durPageIndex + 1) + "/" + this.pageAll);

        finishLoading();
    }

    public void loadData(String title, int durChapterIndex, int chapterAll, int durPageIndex) {
        this.title = title;
        this.durChapterIndex = durChapterIndex;
        this.chapterAll = chapterAll;
        this.durPageIndex = durPageIndex;
        tvTitle.setText(title);
        tvPosition.setText("");
        loading();
    }

    public int getLineCount(int height) {
        float ascent = bookTextView.getPaint().ascent();
        float descent = bookTextView.getPaint().descent();
        float textHeight = descent - ascent;
        return (int) ((height * 1f - bookTextView.getLineSpacingExtra()) / (textHeight + bookTextView.getLineSpacingExtra()));
    }

    public void setReaderController(ReaderController controller) {
        setBg(controller);
        setTextKind(controller);
        setTextTypeface(controller);
    }

    public void setBg(ReaderController readerController) {
        ivBg.setImageResource(readerController.getTextBackground());
        tvTitle.setTextColor(readerController.getTextColor());
        bookTextView.setTextColor(readerController.getTextColor());
        tvPosition.setTextColor(readerController.getTextColor());
        tvDatetime.setTextColor(readerController.getTextColor());
        tvError.setTextColor(readerController.getTextColor());
    }

    public void setTextKind(ReaderController readerController) {
        bookTextView.setTextSize(readerController.getTextSize());
        bookTextView.setLineSpacing(readerController.getTextExtra(), 1);
    }

    public void setTextTypeface(ReaderController readerController) {
        Typeface tf = readerController.getTextTypeface();
        bookTextView.setTypeface(tf == null ? Typeface.DEFAULT : tf);
    }

    public void setContentController(BookContentController contentController) {
        this.contentController = contentController;
    }

    public TextView getBookTextView() {
        return this.bookTextView;
    }

    public int getDurChapterIndex() {
        return this.durChapterIndex;
    }

    public int getChapterAll() {
        return this.chapterAll;
    }

    public int getDurPageIndex() {
        return this.durPageIndex;
    }

    public int getPageAll() {
        return this.pageAll;
    }

    public interface BookContentController {
        void loadData(BookContentView bookContentView, int durChapterIndex, int durPageIndex);

        void setDataFinish(BookContentView bookContentView, int durChapterIndex, int chapterAll, int durPageIndex, int pageAll, int fromPageIndex);
    }

}
