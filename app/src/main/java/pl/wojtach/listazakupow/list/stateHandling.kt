package pl.wojtach.listazakupow.list

import android.content.Context

/**
 * Created by Lukasz on 09.12.2017.
 */
typealias GetShoppingList<T> = ((T) -> List<ShoppingList>)

fun getAllShoppingLists(): GetShoppingList<Context> =
        {context -> getAllShoppingListsFromSQLite(context) }

fun <T>GetShoppingList<T>.mutate(howToMutate: (List<ShoppingList>) -> List<ShoppingList>) =
{ dataSource: T -> howToMutate(this(dataSource))}

fun <T>GetShoppingList<T>.draw() =
        { dataSource: T, view: ShoppingListsView -> setupShoppingListsAdapter(this(dataSource), view) }

//fun <T>GetShoppingList<T>.filter(shouldPass: (ShoppingListsView) -> Boolean) =
//        { dataSource: T, view: ShoppingListsView -> if(shouldPass(view)) this(dataSource) else null }