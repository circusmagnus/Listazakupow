package pl.wojtach.listazakupow

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import pl.wojtach.listazakupow.database.DatabaseHolder
import pl.wojtach.listazakupow.list.*
import pl.wojtach.listazakupow.list.ShoppingListsMainView.STATE.CURRENT_LISTS
import pl.wojtach.listazakupow.list.ShoppingListsMainView.STATE.values

class MainActivity : AppCompatActivity(), ShoppingListsMainView {
    override val appContext
        get() = applicationContext
    override val shoppingLists
        get() = shoppingListsTable

    override val title: TextView
        get() = message

    override var state = CURRENT_LISTS

    private val STATE_KEY = "STATE_KEY"

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                onShowActiveListsClicked(this)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                onShowArchivedListsClicked(this)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        state = values()[savedInstanceState?.getInt(STATE_KEY, 0) ?: 0]
        onActivityCreate(this)


    }

    override fun onStart() {
        super.onStart()
        onActivityStart()(this)

    }

    override fun onResume() {
        super.onResume()
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        addNewShoppingListButton
                .setOnClickListener { _ ->
                    onAddNewShoppingList(shoppingListsTable, this).invoke()
                }
    }

    override fun onStop() {
        //TODO: count StartedActivities in Applicatiion Component and close db connection only if none are started
        DatabaseHolder.getInstance(applicationContext).close()
        super.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putInt(STATE_KEY, state.ordinal)
        super.onSaveInstanceState(outState)
    }
}
