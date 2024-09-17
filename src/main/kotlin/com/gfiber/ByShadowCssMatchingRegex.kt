package com.gfiber

import org.openqa.selenium.SearchContext
import org.openqa.selenium.WebElement

/** Similar to ByShadowCss with ability to assert text value of matching css element.  */
//TODO: Get this to pass tests before making public
internal class ByShadowCssMatchingRegex(private val cssSelector: String, private val regex: String) : ByShadowBase() {
    override fun handleFindElements(parent: WebElement?, context: SearchContext): List<WebElement> {
        val js = getExecutor(context)
        val script = "return window.gfiber.shadowCssMatchingRegex.apply(null, arguments);"
        return js.executeScript(script, parent, cssSelector, regex) as List<WebElement>
    }

    override fun toString(): String {
        return String.format("ByShadowCssMatchingRegex: %s %s", cssSelector, regex)
    }
}