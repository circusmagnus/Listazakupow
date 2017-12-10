package pl.wojtach.listazakupow.list

import android.content.Context

/**
 * Created by Lukasz on 09.12.2017.
 */
typealias GetShoppingLists<T> = ((T) -> List<ShoppingList>)

fun getAllShoppingLists(): GetShoppingLists<Context> =
        {context -> getAllShoppingListsFromSQLite(context) }

fun <T> GetShoppingLists<T>.mutate(howToMutate: (List<ShoppingList>) -> List<ShoppingList>) =
{ dataSource: T -> howToMutate(this(dataSource))}

fun <T> GetShoppingLists<T>.draw() =
        { dataSource: T, view: ShoppingListsView -> drawListView(this(dataSource), view) }

fun <T> GetShoppingLists<T>.save(saveToPersistence: (List<ShoppingList>) -> Unit) =
        { dataSource: T -> this.also { saveToPersistence(this(dataSource)) } }

//fun <T>GetShoppingList<T>.filter(shouldPass: (ShoppingListsView) -> Boolean) =
//        { dataSource: T, view: ShoppingListsView -> if(shouldPass(view)) this(dataSource) else null }