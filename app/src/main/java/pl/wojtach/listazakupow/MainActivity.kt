package pl.wojtach.listazakupow

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import pl.wojtach.listazakupow.database.SQLiteDatabase
import pl.wojtach.listazakupow.list.onActivityCreate
import pl.wojtach.listazakupow.list.onAddNewShoppingList

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

        onActivityCreate(this, shoppingListsTable).invoke()

        addNewShoppingListButton
                .setOnClickListener{ _ ->
                    onAddNewShoppingList(shoppingListsTable, this).invoke() }

    }

    override fun onStop() {
        SQLiteDatabase.getInstance(applicationContext).close()
        Log.d(this::class.java.simpleName, "onStop")
        super.onStop()
    }

}
