package pl.wojtach.listazakupow.list

import kotlinx.android.synthetic.main.shopping_list_small_card.view.*
import org.amshove.kluent.shouldEqual
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

/**
 * Created by Lukasz on 10.12.2017.
 */

@RunWith(RobolectricTestRunner::class)
class HowToKtTest {

    private val testList = listOf(ShoppingList(name = "test", timestamp = 1512941251000))

    @Test
    fun drawListView_setsProperListInAdapter() {
        val view = ShoppingListsView(RuntimeEnvironment.application)

        drawListView(testList, view)

        view.adapter.shoppingLists shouldEqual testList
    }

    @Test
    @Ignore("view should be properly mocked")
    fun drawSmallShoppingListView_setsProperDataOnViews() {
        val view = ShoppingListSmallView(RuntimeEnvironment.application)

        drawSmallShoppingListView(testList.first(), view)

        view.name.text shouldEqual testList.first().name
        view.date.text shouldEqual "10-12-2017"

    }

    @Test
    fun addNewShoppingList_addsNewItem_onFirstPosition() {
        val expectedName = "Lista zakupów"
        val expectedTimestamp = 666L

        addNewShoppingList(oldData = testList, timeProvider = { expectedTimestamp }) shouldEqual listOf(
                ShoppingList(name = expectedName, timestamp = expectedTimestamp),
                testList.first()
        )
    }

}