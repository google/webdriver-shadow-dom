package com.gfiber

import org.openqa.selenium.SearchContext
import org.openqa.selenium.WebElement

/** Enables trasversing up the dom tree to reach a css selector.  */
//TODO: Get this to pass tests before making public
internal class ByShadowCssClosest(private val cssSelector: String) : ByShadowBase() {
    override fun handleFindElements(parent: WebElement?, context: SearchContext): List<WebElement> {
        val js = getExecutor(context)
        val script = "return window.gfiber.shadowCssClosest.apply(null, arguments);"
        return js.executeScript(script, parent, cssSelector) as List<WebElement>
    }

    override fun toString(): String {
        return "ByShadowCssClosest: $cssSelector"
    }
}