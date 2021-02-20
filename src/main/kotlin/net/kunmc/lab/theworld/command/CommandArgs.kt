package net.kunmc.lab.theworld.command

class CommandArgs(private val args: Array<String>) {
    operator fun get(num: Int): String? {
        return try {
            args[num]
        } catch (e: ArrayIndexOutOfBoundsException) {
            null
        }
    }
}
