package pl.wojtach.listazakupow

import android.content.Context
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import pl.wojtach.listazakupow.database.DbHelper
import pl.wojtach.listazakupow.list.getAllShoppingLists
import pl.wojtach.listazakupow.list.draw
import pl.wojtach.listazakupow.shared.ShoppingList
import pl.wojtach.listazakupow.shared.writeShoppingList


class MainActivity : AppCompatActivity() {

    private var mTextMessage: TextView? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                mTextMessage!!.setText(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                mTextMessage!!.setText(R.string.title_dashboard)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                mTextMessage!!.setText(R.string.title_notifications)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mTextMessage = findViewById<TextView>(R.id.message)
        val navigation = findViewById<TextView>(R.id.navigation) as BottomNavigationView
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        val testShoppingList = ShoppingList(name = "testowa", timestamp = System.currentTimeMillis())

        writeShoppingList(applicationContext, testShoppingList)



        getAllShoppingLists().draw()
                .let { if(isFinishing.not()) it.invoke(applicationContext, shoppingListsTable) }
    }

    override fun onDestroy() {
        DbHelper(applicationContext).close()
        super.onDestroy()
    }

}
