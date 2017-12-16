package pl.wojtach.listazakupow.details

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_shopping_details.*
import pl.wojtach.listazakupow.R
import pl.wojtach.listazakupow.database.SQLiteDatabase

val shoppingListIdKey = "SHOPPING_LIST_KEY"

class ShoppingDetailsActivity : AppCompatActivity(), ShoppingDetailsFrag.ShoppingListIdProvider {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_details)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun getSelectedShoppingListId(): Long =
        intent.getLongExtra(shoppingListIdKey, -1).also { Log.d(this::class.java.simpleName, "gettingId: $it") }

    override fun onStop() {
        Log.d(this::class.java.simpleName, "onStop")
        SQLiteDatabase.getInstance(applicationContext).close()
        super.onStop()
    }

}
