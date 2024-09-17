package com.gfiber

import org.junit.jupiter.api.*
import org.openqa.selenium.By
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ShadowLocatorTest {

    private val driver = ChromeDriver()
    private val wait = WebDriverWait(driver, Duration.ofSeconds(3))

    @BeforeAll
    fun `Before All`() {
        driver.manage().timeouts().implicitlyWait(Duration.ZERO)
        driver.get("about:blank")
        driver.executeScript("""document.body.setHTMLUnsafe(`
            <div id="host">
                <span id="noShadow">I'm not in the shadow DOM</span>
                 <template shadowrootmode="open">
                    <span id="shadowSpan">I'm in the shadow DOM</span>
                 </template>
            </div>
            `);
            """)
    }

    @AfterAll
    fun `After All`() {
        driver.close()
    }

    @Test
    fun `ExpectShadowElement WITHOUT parent should wait to find element`() {
        wait.until(ExpectShadowCssElements("#shadowSpan"))
    }

    @Test
    fun `ExpectShadowElement WITH parent should wait to find element`() {
        wait.until(ExpectShadowCssElements("#shadowSpan", driver.findElement(By.tagName("body"))))
    }

    @Test
    fun `ByShadowCss should find things in the shadow DOM`() {
       val element = driver.findElement(ByShadowCss("#shadowSpan"))
        assert(element.getAttribute("textContent")!!.trim() == "I'm in the shadow DOM")
    }

    @Test
    fun `ByShadowCss should find things NOT in the shadow DOM`() {
        val element = driver.findElement(ByShadowCss("#noShadow"))
        assert(element.getAttribute("textContent")!!.trim() == "I'm not in the shadow DOM")
    }

    @Test
    fun `ByShadowRoot should find element that exists`() {
        val element = driver.findElement(ByShadowRoot("#host"))
        assert(element.getAttribute("textContent")!!.trim() == "I'm not in the shadow DOM")
    }

    @Disabled // TODO: debug and fix
    @Test
    fun `ByShadowCssClosest should find element that exists`() {
        val element = driver.findElement(ByShadowCssClosest("#host"))
        assert(element.getAttribute("textContent")!!.trim() == "I'm not in the shadow DOM")
    }

    @Disabled // TODO: debug and fix
    @Test
    fun `ByShadowCssMatchingRegex should find element that exists`() {
        val element = driver.findElement(ByShadowCssMatchingRegex("body", "^I'm in.*"))
        assert(element.getAttribute("textContent")!!.trim() == "I'm not in the shadow DOM")
    }


}
