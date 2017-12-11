package pl.wojtach.listazakupow.list

import org.amshove.kluent.shouldEqual
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import pl.wojtach.listazakupow.shared.getShoppingListByIdFromSQLIte
import pl.wojtach.listazakupow.shared.saveShoppingListToSqlDb

/**
 * Created by Lukasz on 10.12.2017.
 */
@RunWith(RobolectricTestRunner::class)
class DbWritersKtTest {

    private val itemToSave = ShoppingList(name = "test", timestamp = 6, isArchived = true)

    @Test
    fun saveShoppingListToSQL_savesElement_whichCanBeRetrievedAfterwards() {
        val expectedItemFromDb = itemToSave.copy(id = saveShoppingListToSqlDb(RuntimeEnvironment.application, itemToSave))
        val result = getShoppingListByIdFromSQLIte(RuntimeEnvironment.application, expectedItemFromDb.id)
        result shouldEqual expectedItemFromDb
    }

}