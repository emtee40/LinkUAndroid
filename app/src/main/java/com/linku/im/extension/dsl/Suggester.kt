package com.thxbrop.suggester

import com.thxbrop.suggester.Suggester.AllSuggester
import com.thxbrop.suggester.Suggester.AnySuggester

/**
 * DSL representation of boolean expressions
 */
abstract class Suggester {
    /**
     * Result flag, null default
     */
    var result: Boolean? = null
        protected set

    /**
     * Run complete() then return no-null result
     */
    val completedResult: Boolean
        get() {
            complete()
            return result!!
        }


    /**
     * Suggest a lambda which return Boolean value,
     * This function should affect result value when it should be completed.
     */
    abstract fun suggest(block: () -> Boolean)

    /**
     * Completed flag, false default
     */
    protected var completed: Boolean = false

    /**
     * Completed these two flags: [result] and [completed]
     */
    abstract fun complete()

    /**
     * Provide an easy way to invoke [block] if it is not completed,
     * If that was invoked, the [callback] will be invoked with [block] return value
     */
    protected fun uncompleted(block: () -> Boolean, callback: (Boolean) -> Unit) {
        if (!completed) block().also(callback)
    }

    companion object {
        /**
         * Make a new instance of [AllSuggester]
         */
        fun newAllSuggester() = AllSuggester()

        /**
         * Make a new instance of [AnySuggester]
         */
        fun newAnySuggester() = AnySuggester()
    }

    /**
     * Default Implement of Suggester
     *
     * It is same with '&&' operation
     */
    class AllSuggester internal constructor() : Suggester() {
        override fun suggest(block: () -> Boolean) = uncompleted(block) {
            if (!it) {
                result = false
                completed = true
            }
        }


        override fun complete() {
            completed = true
            result = result ?: true
        }
    }

    /**
     * Default Implement of Suggester
     *
     * It is same with '||' operation
     */
    class AnySuggester internal constructor() : Suggester() {
        override fun suggest(block: () -> Boolean) = uncompleted(block) {
            if (it) {
                result = true
                completed = true
            }
        }

        override fun complete() {
            completed = true
            result = result ?: false
        }
    }
}

/**
 * Top-level Suggester Builder
 * @param suggester Subclass of Suggester
 * @param block Code Blocking to invoke suggested functions
 */
inline fun suggest(suggester: Suggester, block: Suggester.() -> Unit): Boolean {
    block(suggester)
    return suggester.completedResult
}

/**
 * Top-level [AllSuggester] Builder
 * @param block Code Blocking to invoke suggested functions
 */
inline fun all(block: Suggester.() -> Unit): Boolean = suggest(Suggester.newAllSuggester(), block)

/**
 * Top-level [AnySuggester] Builder
 * @param block Code Blocking to invoke suggested functions
 */
inline fun any(block: Suggester.() -> Unit): Boolean = suggest(Suggester.newAnySuggester(), block)

/**
 * Nested AllSuggester Builder
 *
 * This function make another new [AllSuggester] builder and provide its result to the parent [Suggester]
 * @param block Code Blocking to invoke suggested functions
 */
inline fun Suggester.suggestAll(crossinline block: (Suggester) -> Unit) = suggest { all(block) }

/**
 * Nested AnySuggester Builder
 *
 * This function make another new [AnySuggester] builder and provide its result to the parent [Suggester]
 * @param block Code Blocking to invoke suggested functions
 */
inline fun Suggester.suggestAny(crossinline block: (Suggester) -> Unit) = suggest { any(block) }
