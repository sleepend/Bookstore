package ym.nemo233.bookstore.widget.dialog

import android.content.Context
import android.os.Bundle
import ym.nemo233.bookstore.R

class LoadingDialog(context: Context) : IDialog(context, R.style.loading_dialog) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_loading)
        setScreenWH(0.4f, 0.3f)
    }

}