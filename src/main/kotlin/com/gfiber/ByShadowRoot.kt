package com.gfiber

import org.openqa.selenium.SearchContext
import org.openqa.selenium.WebElement


/** Searches only direct elements of shadowroot.  */
class ByShadowRoot(private val cssSelector: String) : ByShadowBase() {
    override fun handleFindElements(parent: WebElement?, context: SearchContext): List<WebElement> {
        val js = getExecutor(context)
        val script = "return window.gfiber.shadowRoot.apply(null, arguments);"
        return js.executeScript(script, parent, cssSelector) as List<WebElement>
    }

    override fun toString(): String {
        return "ByShadowRoot: $cssSelector"
    }

}