package com.gfiber

import org.openqa.selenium.*
import org.openqa.selenium.remote.RemoteWebElement
import org.openqa.selenium.support.ui.ExpectedCondition
import java.io.IOException
import kotlin.Any
import kotlin.IllegalArgumentException
import kotlin.NoSuchElementException
import kotlin.String


/** Base class for all custom shadow dom locators.  */
abstract class ByShadowBase : By() {
    protected abstract fun handleFindElements(parent: WebElement?, context: SearchContext): List<WebElement>

    override fun findElement(context: SearchContext): WebElement {
        val items: List<WebElement> = findElements(context)
        if (items.isEmpty()) {
            throw NoSuchElementException("Cannot locate an element using $this")
        }
        return items[0]
    }

    override fun findElements(context: SearchContext): List<WebElement> {
        try {
            val js: JavascriptExecutor = getExecutor(context)
            injectShadowLocatorScripts(js)
            return if (context is WebElement) {
                handleFindElements(context, context)
            } else {
                handleFindElements(null, context)
            }
        } catch (ex: IOException) {
            println("Exception Log : $ex")
            return ArrayList()
        }
    }

    companion object {

        @JvmStatic
        fun injectShadowLocatorScripts(executor: JavascriptExecutor) {
            // The gfiberWindow code line execute first
            executor.executeScript(JavaScriptContent.gfiberWindow)
            executor.executeScript(JavaScriptContent.byshadowcss)
            executor.executeScript(JavaScriptContent.byshadowcssclosest)
            executor.executeScript(JavaScriptContent.byshadowcssmatchingregex)
            executor.executeScript(JavaScriptContent.byshadowcssroot)
        }

        @JvmStatic
        protected fun getExecutor(context: SearchContext): JavascriptExecutor {
            return when (context) {
                is JavascriptExecutor -> {
                    context
                }

                is RemoteWebElement -> {
                    context.wrappedDriver as JavascriptExecutor
                }

                else -> {
                    throw IllegalArgumentException (
                        String.format("No JavaScriptExecutor available from context %s", context)
                    )
                }
            }
        }
    }
}