package calculator

class Stack<T> {
    private val list = mutableListOf<T>()

    fun push(element: T) {
        list.add(element)
    }

    fun pop() = list.removeLast()

    fun top() = list.last()

    fun size() = list.size

    fun isEmpty() = list.isEmpty()

    fun clear() = list.clear()
}