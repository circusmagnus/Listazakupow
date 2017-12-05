package pl.wojtach.listazakupow.details

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import pl.wojtach.listazakupow.shared.ShoppingItemsAdapter

/**
 * Created by Lukasz on 05.12.2017.
 */
class ShoppingDetailsRecycler @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    init {
        adapter = ShoppingItemsAdapter(emptyList())
        layoutManager = LinearLayoutManager(context)
    }

    override fun getAdapter(): ShoppingItemsAdapter = super.getAdapter() as ShoppingItemsAdapter
}