package com.gfiber

import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedCondition

class ExpectShadowCssElements(
    private val cssSelector: String,
    private val parent: WebElement? = null
        ): ExpectedCondition<List<WebElement>> {

    private val script = "return window.gfiber.shadowCss(arguments[0], '$cssSelector');"
    override fun apply(driver: WebDriver): List<WebElement>? {
        val js: JavascriptExecutor = driver as JavascriptExecutor
        ByShadowBase.injectShadowLocatorScripts(js)
        val elements = js.executeScript(script, parent) as List<WebElement>
        return elements.ifEmpty { null }
    }
}