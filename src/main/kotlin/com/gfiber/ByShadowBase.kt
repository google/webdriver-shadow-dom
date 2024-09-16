package com.gfiber

import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.SearchContext
import org.openqa.selenium.WebElement
import org.openqa.selenium.remote.RemoteWebElement
import java.io.IOException

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
            //  sequence of code matters
            val jsContent = JavaScriptContent()
            js.executeScript(jsContent.gfiberWindow)
            js.executeScript(jsContent.byshadowcss)
            js.executeScript(jsContent.byshadowcssclosest)
            js.executeScript(jsContent.byshadowcssmatchingregex)
            js.executeScript(jsContent.byshadowcssroot)
            println("JS INJECTED")

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