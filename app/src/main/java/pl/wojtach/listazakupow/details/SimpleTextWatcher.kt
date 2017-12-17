package pl.wojtach.listazakupow.details

import android.text.Editable
import android.text.TextWatcher

/**
 * Created by Lukasz on 17.12.2017.
 */
class SimpleTextWatcher(val afterEdit: () -> Unit) : TextWatcher {

    override fun afterTextChanged(s: Editable?) {
        afterEdit()
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        // no action
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        // no action
    }
}