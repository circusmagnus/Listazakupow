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
