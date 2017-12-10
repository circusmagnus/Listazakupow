package pl.wojtach.listazakupow.shared

/**
 * Created by Lukasz on 09.12.2017.
 */
typealias StateProvider<T> = () -> T

fun <T>getInitialState(howToGet: () -> T): StateProvider<T> = { howToGet() }

fun <T> StateProvider<T>.mutate(howToMutate: (T) -> T): StateProvider<T> =
        { howToMutate(this()) }

fun <T> StateProvider<T>.use(howToUse: (T) -> Unit): StateProvider<T> =
        { this().also { howToUse(this()) } }

//fun GetShoppingLists.filter(howToFilter: (List<ShoppingList>) -> Boolean) :GetShoppingLists =
//        { if(howToFilter(this())) this() else emptyList<ShoppingList>()}

//fun <T>GetShoppingList<T>.filter(shouldPass: (ShoppingListsView) -> Boolean) =
//        { dataSource: T, view: ShoppingListsView -> if(shouldPass(view)) this(dataSource) else null }