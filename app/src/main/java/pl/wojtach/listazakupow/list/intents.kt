package pl.wojtach.listazakupow.list

/**
 * Created by Lukasz on 09.12.2017.
 */
typealias GetShoppingLists = () -> List<ShoppingList>

fun getShoppingLists(howToGet: () -> List<ShoppingList>): GetShoppingLists = { howToGet() }

fun GetShoppingLists.mutate(howToMutate: (List<ShoppingList>) -> List<ShoppingList>): GetShoppingLists =
        { howToMutate(this()) }

fun GetShoppingLists.act(howToDraw: (List<ShoppingList>) -> Unit): GetShoppingLists =
        { this().also { howToDraw(this()) } }

//fun GetShoppingLists.filter(howToFilter: (List<ShoppingList>) -> Boolean) :GetShoppingLists =
//        { if(howToFilter(this())) this() else emptyList<ShoppingList>()}

//fun <T>GetShoppingList<T>.filter(shouldPass: (ShoppingListsView) -> Boolean) =
//        { dataSource: T, view: ShoppingListsView -> if(shouldPass(view)) this(dataSource) else null }