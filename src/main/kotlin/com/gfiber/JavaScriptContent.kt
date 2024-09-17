package com.gfiber

internal class JavaScriptContent {

    companion object {
        val gfiberWindow = """
                            if(!window.gfiber) {
                                window.gfiber = {}
                            }
                        """.trimIndent()

        val byshadowcss = """
                        /**
                         * @fileoverview By locator to search the shadow dom with a css selector.
                         */
                
                        if (!window.gfiber.shadowCss){
                
                          /**
                           * @param {!HTMLElement} parentElement A webElement.
                           * @param {string} selector Selector of element to search for.
                           * @return {!Array<!HTMLElement>}
                           */
                          window.gfiber.shadowCss = function shadowCss(parentElement, selector) {
                            /**
                             * Known HTML tags that can be skipped to see if it has shadowDOM.  We are
                             * only interested in custom html tags.
                             * @type {!Array<string>}
                             */
                            const skipTags = [
                              'a',       'abbr',     'address',    'area',     'article',    'aside',
                              'audio',   'b',        'bdi',        'bdo',      'blockquote', 'body',
                              'br',      'button',   'canvas',     'caption',  'cite',       'code',
                              'col',     'colgroup', 'command',    'datalist', 'dd',         'del',
                              'details', 'dfn',      'div',        'dl',       'dt',         'em',
                              'embed',   'fieldset', 'figcaption', 'figure',   'footer',     'form',
                              'h1',      'h2',       'h3',         'h4',       'h5',         'h6',
                              'header',  'hr',       'html',       'i',        'iframe',     'img',
                              'input',   'ins',      'kbd',        'keygen',   'label',      'legend',
                              'li',      'main',     'map',        'mark',     'menu',       'meter',
                              'nav',     'object',   'ol',         'optgroup', 'option',     'output',
                              'p',       'param',    'pre',        'progress', 'q',          'rp',
                              'rt',      'ruby',     's',          'samp',     'section',    'select',
                              'small',   'source',   'span',       'strong',   'sub',        'summary',
                              'sup',     'table',    'tbody',      'td',       'textarea',   'tfoot',
                              'th',      'thead',    'time',       'tr',       'track',      'u',
                              'ul',      'var',      'video',      'wbr'
                            ];
                
                            /**
                             * @param {string} selector Selector of element to search for.
                             * @param {!HTMLElement|!ShadowRoot} target A webElement.
                             * @return {!Array<!HTMLElement>}
                             */
                            const findAllMatches = (selector, target) => {
                              const results = Array.from(target.querySelectorAll(selector));
                
                              const elementsWithShadowRoots =
                                  Array.from(target.querySelectorAll('*'))
                                      .filter((element) => !!element.shadowRoot);
                
                              for (const element of elementsWithShadowRoots) {
                                if (element.shadowRoot) {
                                  results.push(...findAllMatches(selector, element.shadowRoot));
                                }
                              }
                
                              return results;
                            };
                
                            const start = parentElement || document.documentElement;
                            return findAllMatches(
                                selector, start.shadowRoot ? start.shadowRoot : start);
                          }
                
                        }
                    """.trimIndent()

        val byshadowcssclosest = """
                                /**
                                 * @fileoverview Provides shadowRoot helpers.
                                 */
                                if (!window.gfiber.shadowCssClosest) {
                                  /**
                                   * @param {!HTMLElement} parentElement A webElement.
                                   * @param {string} selector Selector of element to search for.
                                   * @return {!Array<!HTMLElement>}
                                   */
                                  window.gfiber.shadowCssClosest = function shadowCssClosest(parentElement, selector) {
                                    const el = parentElement;
                                    if (!el) {
                                      return [];
                                    }
                        
                                    const closest = el.closest(selector);
                                    return closest ? [closest] : [];
                                  }
                        
                                }
                            """.trimIndent()

        val byshadowcssmatchingregex = """
                                    /**
                                     * @fileoverview  By locator to search the shadow dom using a regex for text
                                     * content.
                                     */
                            
                                    if (!window.gfiber.shadowCssMatchingRegex) {
                                      /**
                                       * @param {!HTMLElement} parentElement A webElement.
                                       * @param {string} selector Selector of element to search for.
                                       * @param {string} regex Regular expression used to find a match.
                                       * @return {!Array<!HTMLElement>}
                                       */
                                      window.gfiber.shadowCssMatchingRegex = function shadowCssMatchingRegex(parentElement, selector, regex) {
                                        /**
                                         * Known HTML tags that can be skipped to see if it has shadowDOM.  We are
                                         * only interested in custom html tags.
                                         * @type {!Array<string>}
                                         */
                                        const skipTags = [
                                          'a',       'abbr',     'address',    'area',     'article',    'aside',
                                          'audio',   'b',        'bdi',        'bdo',      'blockquote', 'body',
                                          'br',      'button',   'canvas',     'caption',  'cite',       'code',
                                          'col',     'colgroup', 'command',    'datalist', 'dd',         'del',
                                          'details', 'dfn',      'div',        'dl',       'dt',         'em',
                                          'embed',   'fieldset', 'figcaption', 'figure',   'footer',     'form',
                                          'h1',      'h2',       'h3',         'h4',       'h5',         'h6',
                                          'header',  'hr',       'html',       'i',        'iframe',     'img',
                                          'input',   'ins',      'kbd',        'keygen',   'label',      'legend',
                                          'li',      'main',     'map',        'mark',     'menu',       'meter',
                                          'nav',     'object',   'ol',         'optgroup', 'option',     'output',
                                          'p',       'param',    'pre',        'progress', 'q',          'rp',
                                          'rt',      'ruby',     's',          'samp',     'section',    'select',
                                          'small',   'source',   'span',       'strong',   'sub',        'summary',
                                          'sup',     'table',    'tbody',      'td',       'textarea',   'tfoot',
                                          'th',      'thead',    'time',       'tr',       'track',      'u',
                                          'ul',      'var',      'video',      'wbr'
                                        ];
                            
                                        /**
                                         * @param {string} selector Selector of element to search for.
                                         * @param {string} regexAsString Regex that should match text content.
                                         * @param {!HTMLElement|!ShadowRoot} target A webElement.
                                         * @return {!Array<!HTMLElement>}
                                         */
                                        const shadowMatchingRegex = (selector, regexAsString, target) => {
                                          const els = target.querySelectorAll(selector);
                                          const matches = Array.from(els).filter(el => {
                                            if (el.shadowRoot) {
                                              const shadowRootText =
                                                  (el.shadowRoot.textContent || el.shadowRoot.innerText ||
                                                   '').trim();
                                              return new RegExp(regexAsString).test(shadowRootText);
                                            }
                            
                                            const text = (el.textContent || el.innerText || '').trim();
                                            return new RegExp(regexAsString).test(text);
                                          });
                            
                                          return matches;
                                        };
                            
                                        /**
                                         * @param {string} selector Selector of element to search for.
                                         * @param {string} regexAsString Regex that should match text content.
                                         * @param {!HTMLElement|!ShadowRoot} target A webElement.
                                         * @return {!Array<!HTMLElement>}
                                         */
                                        const findAllMatches = (selector, regexAsString, target) => {
                                          const results = shadowMatchingRegex(selector, regexAsString, target);
                            
                                          const elementsWithShadowRoots =
                                              Array.from(target.querySelectorAll('*'))
                                                  .filter(
                                                      (element) => !skipTags.some(
                                                          tag => tag === element.tagName.toLocaleLowerCase()))
                                                  .filter((element) => !!element.shadowRoot);
                            
                                          const elementsWithSlots =
                                              Array.from(target.querySelectorAll('slot'))
                                                  .concat(
                                                      target.tagName?.toLocaleLowerCase() === 'slot' ? [target] :
                                                                                                       [])
                                                  .flatMap((element) => Array.from(element.assignedNodes()));
                            
                                          for (const element of elementsWithShadowRoots) {
                                            results.push(
                                                ...findAllMatches(selector, regexAsString, element.shadowRoot));
                                          }
                                          for (const element of elementsWithSlots) {
                                            results.push(...findAllMatches(selector, regexAsString, element));
                                          }
                            
                                          return results;
                                        };
                            
                                        let start = parentElement || document;
                                        if (start.shadowRoot) {
                                          start = start.shadowRoot;
                                        }
                            
                                        return [...new Set(findAllMatches(selector, regex, start))];
                                      }
                            
                                    }
                                """.trimIndent()

        val byshadowcssroot = """      
                            /**
                             * @fileoverview By locator to search the shadowRoot.
                             */
                            if (!window.gfiber.shadowRoot) {
                              /**
                               * @param {!HTMLElement|!ShadowRoot} parentElement A webElement.
                               * @param {string} selector Selector of element to search for.
                               * @return {!Array<!HTMLElement>}
                               */
                              const shadowRoot = (parentElement, selector) => {
                                return Array.from(
                                    (parentElement?.shadowRoot || document).querySelectorAll(selector) ||
                                    []);
                              };
                                window.gfiber.shadowRoot = shadowRoot
                    
                            }
                        """.trimIndent()
    }

}