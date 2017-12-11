package pl.wojtach.listazakupow.list

import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import pl.wojtach.listazakupow.shared.getAllShoppingListsFromSQLite
import pl.wojtach.listazakupow.shared.getShoppingListByIdFromSQLIte
import pl.wojtach.listazakupow.shared.saveShoppingListToSqlDb

/**
 * Created by Lukasz on 10.12.2017.
 */

@RunWith(RobolectricTestRunner::class)
class DbReadersKtTest {

    val shoppingList_1 = ShoppingList(name = "1", timestamp = 1, isArchived = false)
    val shoppingList_2 = ShoppingList(name = "2", timestamp = 2, isArchived = true)
    val shoppingList_3 = ShoppingList(name = "3", timestamp = 3, isArchived = false)

    @Before
    fun setup() {
        listOf(shoppingList_2, shoppingList_1, shoppingList_3)
                .forEach { saveShoppingListToSqlDb(RuntimeEnvironment.application, it) }
    }

    @Test
    fun getAllShoppingListsFromSQLite_returnsAllElements_newestFirst() {
        val result = getAllShoppingListsFromSQLite(RuntimeEnvironment.application)
        result shouldEqual listOf(shoppingList_3, shoppingList_2, shoppingList_1)
    }

    @Test
    fun getShoppingListByIdFromSQLIte_returnsCorrectShoppingList_ifPresent() {
        val result = getShoppingListByIdFromSQLIte(RuntimeEnvironment.application, 3L)
        result shouldEqual shoppingList_3

    }

    @Test
    fun getShoppingListByIdFromSQLIte_returnsNull_ifElementNotPresent() {
        val result = getShoppingListByIdFromSQLIte(RuntimeEnvironment.application, 6L)
        result shouldBe null

    }

}