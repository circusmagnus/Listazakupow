package pl.wojtach.listazakupow.details

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import pl.wojtach.listazakupow.details.archived.NonEditableShoppingItemsAdapter
import pl.wojtach.listazakupow.details.editable.EditableShoppingItemsAdapter

/**
 * Created by Lukasz on 05.12.2017.
 */
class ShoppingDetailsRecycler @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    init {
        layoutManager = LinearLayoutManager(context)
    }

    fun getEditableItemsAdapter(): EditableShoppingItemsAdapter? =
            super.getAdapter() as? EditableShoppingItemsAdapter

    fun getNonEditableItemsAdapter(): NonEditableShoppingItemsAdapter? =
            super.getAdapter() as? NonEditableShoppingItemsAdapter

}