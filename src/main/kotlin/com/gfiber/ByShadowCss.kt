package com.gfiber

import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.SearchContext
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

/** ByShadowCss searches for css selectors works similarly to ByCss but with shadowdom support.  */
class ByShadowCss(private val cssSelector: String) : ByShadowBase() {

    override fun handleFindElements(parent: WebElement?, context: SearchContext): List<WebElement> {
        val js: JavascriptExecutor = getExecutor(context)
        val script = "return window.gfiber.shadowCss.apply(null, arguments);"
        return js.executeScript(script, parent, cssSelector) as List<WebElement>
    }

    override fun toString(): String {
        return "ByShadowCss: $cssSelector"
    }
}