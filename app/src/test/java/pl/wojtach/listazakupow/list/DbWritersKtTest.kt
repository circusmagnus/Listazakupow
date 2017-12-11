package pl.wojtach.listazakupow.list

import org.amshove.kluent.shouldEqual
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import pl.wojtach.listazakupow.details.ShoppingItem
import pl.wojtach.listazakupow.shared.getShoppingItemsForId
import pl.wojtach.listazakupow.shared.getShoppingListByIdFromSQLIte
import pl.wojtach.listazakupow.shared.saveShoppingItemToSqlDb
import pl.wojtach.listazakupow.shared.saveShoppingListToSqlDb

/**
 * Created by Lukasz on 10.12.2017.
 */
@RunWith(RobolectricTestRunner::class)
class DbWritersKtTest {

    private val listToSave = ShoppingList(id = 1, name = "test", timestamp = 6, isArchived = true)
    private val itemToSave = ShoppingItem(id = 1, shoppingListId = 1, item = "")

    @Test
    fun saveShoppingListToSQL_savesElement_whichCanBeRetrievedAfterwards() {
        saveShoppingListToSqlDb(RuntimeEnvironment.application, listToSave)
        val result = getShoppingListByIdFromSQLIte(RuntimeEnvironment.application, listToSave.id)
        result shouldEqual listToSave
    }

    @Test
    fun saveShoppingItemToSqlDb_savesElement_whichCanBeRetrievedAfterwards(){
        saveShoppingItemToSqlDb(RuntimeEnvironment.application, itemToSave)
        val result = getShoppingItemsForId(appContext = RuntimeEnvironment.application, id = listToSave.id)
        result shouldEqual listOf(itemToSave)
    }

}