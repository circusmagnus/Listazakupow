package pl.wojtach.listazakupow

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import pl.wojtach.listazakupow.database.DatabaseHolder
import pl.wojtach.listazakupow.list.*

class MainActivity : AppCompatActivity(), ShoppingListsMainView {
    override val appContext
        get() = applicationContext
    override val shoppingLists
        get() = shoppingListsTable

    private var mTextMessage: TextView? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                mTextMessage!!.setText("Aktywne listy")
                onShowActiveListsClicked(this)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                mTextMessage!!.setText("Listy archiwalne")
                onShowArchivedListsClicked(this)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mTextMessage = findViewById<TextView>(R.id.message)
        val navigation = findViewById<BottomNavigationView>(R.id.navigation) as BottomNavigationView
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        addNewShoppingListButton
                .setOnClickListener{ _ ->
                    onAddNewShoppingList(shoppingListsTable, this).invoke() }

    }

    override fun onStart() {
        super.onStart()
        onActivityStart(this, shoppingListsTable).invoke()
    }

    override fun onStop() {
        DatabaseHolder.getInstance(applicationContext).close()
        Log.d(this::class.java.simpleName, "onStop")
        super.onStop()
    }

}
