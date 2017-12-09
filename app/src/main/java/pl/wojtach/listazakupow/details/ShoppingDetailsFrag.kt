package pl.wojtach.listazakupow.details

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_shopping_list_details.*

import pl.wojtach.listazakupow.R

class ShoppingDetailsFrag : Fragment(), ShoppingDetailsView {

    override val appContext: Context
        get() = context.applicationContext

    override val shoppingListName: TextView
        get() = details_name

    override val shoppingListDate: TextView
        get() = details_date

    override val shoppingListItems: ShoppingDetailsRecycler
        get() = shopping_items_list

    override fun onCreateView(
            inflater: LayoutInflater?,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fragment_shopping_list_details, container, false)


}
