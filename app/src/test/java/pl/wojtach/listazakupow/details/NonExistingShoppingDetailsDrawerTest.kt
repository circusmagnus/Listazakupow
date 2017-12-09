package pl.wojtach.listazakupow.details

import android.content.Context
import android.widget.TextView
import org.amshove.kluent.*
import org.junit.Test

import org.junit.Before

/**
 * Created by Lukasz on 04.12.2017.
 */
class NonExistingShoppingDetailsDrawerTest {

    private val drawer = NonExistingShoppingDetailsDrawer()
    private val mockedView = mock<ShoppingDetailsView>()
    private val mockedName = mock<TextView>()
    private val mockedDate = mock<TextView>()
    private val mockedContext = mock<Context>()
    private val mockedList = mock<ShoppingDetailsRecycler>()
    private val mockedAdapter = mock<ShoppingItemsAdapter>()

    @Before
    fun setup(){
        When calling mockedView.appContext itReturns mockedContext
        When calling mockedView.shoppingListName itReturns mockedName
        When calling mockedView.shoppingListDate itReturns mockedDate
        When calling mockedView.shoppingListItems itReturns mockedList
        When calling mockedList.adapter itReturns mockedAdapter
    }

    @Test
    fun draw_setsShoppingDetailsNametoCorrectMessage() {
        val expectedName = "Nowa lista"
        When calling mockedContext.getString(any()) itReturns expectedName

                drawer.draw(mockedView)

        Verify on mockedName that mockedName.setText(expectedName) was called
    }

    @Test
    fun draw_setsShoppingDetailsDateToCorrectValue() {
        val expectedDate = "05-12-2017"

        NonExistingShoppingDetailsDrawer({ 1512431081130 } )
                .draw(mockedView)

        Verify on mockedDate that mockedDate.setText(expectedDate) was called
    }

    @Test
    fun draw_setsItemListEmpty(){
        var wasCorrect = false
        When calling mockedAdapter.set
        drawer.draw(mockedView)
        Verify on mockedAdapter that mockedAdapter.items.clear() was called
    }

}