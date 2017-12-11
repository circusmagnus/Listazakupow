package pl.wojtach.listazakupow.list

import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment.application
import pl.wojtach.listazakupow.details.ShoppingItem
import pl.wojtach.listazakupow.shared.*

/**
 * Created by Lukasz on 10.12.2017.
 */

@RunWith(RobolectricTestRunner::class)
class DbReadersKtTest {

    val shoppingList_1 = ShoppingList(id = 1, name = "1", timestamp = 1, isArchived = false)
    val shoppingList_2 = ShoppingList(id = 2, name = "2", timestamp = 2, isArchived = true)
    val shoppingList_3 = ShoppingList(id = 3, name = "3", timestamp = 3, isArchived = false)

    val shoppingItem_1 = ShoppingItem(shoppingListId = 3, item = "")
    val shoppingItem_2 = ShoppingItem(shoppingListId = 3, item = "")
    val shoppingItem_3 = ShoppingItem(shoppingListId = 2, item = "")

    @Before
    fun setup() {
        listOf(shoppingList_2, shoppingList_1, shoppingList_3)
                .forEach { saveShoppingListToSqlDb(application, it) }
        listOf(shoppingItem_1, shoppingItem_2, shoppingItem_3)
                .forEach { saveShoppingItemToSqlDb(application, it) }
    }

    @Test
    fun getAllShoppingListsFromSQLite_returnsAllElements_newestFirst() {
        val result = getAllShoppingListsFromSQLite(application)
        result shouldEqual listOf(shoppingList_3, shoppingList_2, shoppingList_1)
    }

    @Test
    fun getShoppingListByIdFromSQLIte_returnsCorrectShoppingList_ifPresent() {
        val result = getShoppingListByIdFromSQLIte(application, 3L)
        result shouldEqual shoppingList_3

    }

    @Test
    fun getShoppingListByIdFromSQLIte_returnsNull_ifElementNotPresent() {
        val result = getShoppingListByIdFromSQLIte(application, 6L)
        result shouldBe null

    }

    @Test
    fun getShoppingItemsForId_returnsAllItemsForSpecifiedList() {
        val result = getShoppingItemsForId(shoppingList_3.id, application)
        result shouldEqual listOf(shoppingItem_1, shoppingItem_2)
    }

}