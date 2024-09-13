# Webdriver Shadow DOM

The shadow DOM is an HTML construct used to enable encapsulation for web
applications. Unfortunately it was never designed with testability in mind. This
makes writing selenium tests profoundly difficult for these sorts of
applications using traditional methods.

Googlers working on apps like this were disappointed with existing solutions to
this, which seem to consist of listing out the individual nodes in the shadow
DOM tree. This creates deeply brittle tests.

This library constains Selenium locators that free engineers from having to
consider the shadow DOM tree. Simply use CSS selectors like a normal project and
stop fearing the shadow DOM!

## Locators

### ByShadowCss

Locate using a normal CSS selector:

```java
By shadowCss = new ShadowCss("[aria-label='foo']");
webdriver.findElement(shadowCss);
```

### ByShadowCssMatchingRegex

Locate an element with text matching a regular expression

By shadowCssMatchingRegex = new
ShadowCssMatchingRegex(someParentWebElementOrNull, "#css-selector", "^a
regex$");

### ByShadowCssClosest

Find an element in the HTML tree that is in close proximity to some other node.
This is useful if the element you're searching for has ambiguous nodes nested
within it.

```java
WebElement someParent = /* locate parent somehow*/;
By shadowCssClosest = someParent.findElement(new ByShadowCssClosest(null, "[aria-label='some-child'"));
```

### ByShadowRoot

If you have a specific element that has a shadow root and you want to search
elements within it WITHOUT recursively searching nested shadow roots, use this.
In other words, this breaks open the shadow root for the element you pass it,
but not it's children. It only looks at the direct elements under an element.

```java
WebElement someParent = new ShadowRoot(elementWithShadowDom, "#child-css
selector");
```

