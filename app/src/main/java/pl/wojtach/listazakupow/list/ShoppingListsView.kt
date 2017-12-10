package pl.wojtach.listazakupow.list

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet

/**
 * Created by Lukasz on 09.12.2017.
 */

class ShoppingListsView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    val adapter = ShoppingListsAdapter(emptyList())

    init {
        layoutManager = LinearLayoutManager(context)
        setAdapter(adapter)
    }

}