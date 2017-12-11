package pl.wojtach.listazakupow.list

import org.amshove.kluent.shouldEqual
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import pl.wojtach.listazakupow.shared.getShoppingListByIdFromSQLIte
import pl.wojtach.listazakupow.shared.saveShoppingListToSQL

/**
 * Created by Lukasz on 10.12.2017.
 */
@RunWith(RobolectricTestRunner::class)
class DbWritersKtTest {

    private val itemToSave = ShoppingList(name = "test", timestamp = 6)

    @Test
    fun saveShoppingListToSQL_savesElement_whichCanBeRetrievedAfterwards() {
        saveShoppingListToSQL(RuntimeEnvironment.application, itemToSave)
        val result = getShoppingListByIdFromSQLIte(RuntimeEnvironment.application, itemToSave.timestamp)
        result shouldEqual itemToSave
    }

}