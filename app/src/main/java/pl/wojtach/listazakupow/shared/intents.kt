package pl.wojtach.listazakupow.shared

/**
 * Created by Lukasz on 09.12.2017.
 */
typealias StateProvider<T> = () -> T

fun <T> initProcedureWith(what: () -> T): StateProvider<T> = { what() }

fun <T> StateProvider<T>.compose(what: (T) -> T): StateProvider<T> =
        { what(this()) }

fun <T> StateProvider<T>.use(how: (T) -> Any) =
        { how(this()).let { Unit } }

fun <T>StateProvider<T>.filter(predicate: (T) -> Boolean): StateProvider<T>? =
        if(predicate(this()))  { this } else null

//fun GetShoppingLists.filter(howToFilter: (List<ShoppingList>) -> Boolean) :GetShoppingLists =
//        { if(howToFilter(this())) this() else emptyList<ShoppingList>()}

//fun <T>GetShoppingList<T>.filter(shouldPass: (ShoppingListsView) -> Boolean) =
//        { dataSource: T, view: ShoppingListsView -> if(shouldPass(view)) this(dataSource) else null }