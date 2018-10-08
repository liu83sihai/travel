/*! ---- * 2015-08-12 00:50:23 ---- */

/*! Respond.js v1.4.2: min/max-width media query polyfill
 * Copyright 2014 Scott Jehl
 * Licensed under MIT
 * http://j.mp/respondjs */

/*! matchMedia() polyfill - Test a CSS media type/query in JS. Authors & copyright (c) 2012: Scott Jehl, Paul Irish, Nicholas Zakas. Dual MIT/BSD license */
/*! NOTE: If you're already including a window.matchMedia polyfill via Modernizr or otherwise, you don't need this part */
(function(w) {
  "use strict";
  w.matchMedia = w.matchMedia || function(doc, undefined) {
    var bool, docElem = doc.documentElement, refNode = docElem.firstElementChild || docElem.firstChild, fakeBody = doc.createElement("body"), div = doc.createElement("div");
    div.id = "mq-test-1";
    div.style.cssText = "position:absolute;top:-100em";
    fakeBody.style.background = "none";
    fakeBody.appendChild(div);
    return function(q) {
      div.innerHTML = '&shy;<style media="' + q + '"> #mq-test-1 { width: 42px; }</style>';
      docElem.insertBefore(fakeBody, refNode);
      bool = div.offsetWidth === 42;
      docElem.removeChild(fakeBody);
      return {
        matches: bool,
        media: q
      };
    };
  }(w.document);
})(this);

(function(w) {
  "use strict";
  var respond = {};
  w.respond = respond;
  respond.update = function() {};
  var requestQueue = [], xmlHttp = function() {
    var xmlhttpmethod = false;
    try {
      xmlhttpmethod = new w.XMLHttpRequest();
    } catch (e) {
      xmlhttpmethod = new w.ActiveXObject("Microsoft.XMLHTTP");
    }
    return function() {
      return xmlhttpmethod;
    };
  }(), ajax = function(url, callback) {
    var req = xmlHttp();
    if (!req) {
      return;
    }
    req.open("GET", url, true);
    req.onreadystatechange = function() {
      if (req.readyState !== 4 || req.status !== 200 && req.status !== 304) {
        return;
      }
      callback(req.responseText);
    };
    if (req.readyState === 4) {
      return;
    }
    req.send(null);
  }, isUnsupportedMediaQuery = function(query) {
    return query.replace(respond.regex.minmaxwh, "").match(respond.regex.other);
  };
  respond.ajax = ajax;
  respond.queue = requestQueue;
  respond.unsupportedmq = isUnsupportedMediaQuery;
  respond.regex = {
    media: /@media[^\{]+\{([^\{\}]*\{[^\}\{]*\})+/gi,
    keyframes: /@(?:\-(?:o|moz|webkit)\-)?keyframes[^\{]+\{(?:[^\{\}]*\{[^\}\{]*\})+[^\}]*\}/gi,
    comments: /\/\*[^*]*\*+([^/][^*]*\*+)*\//gi,
    urls: /(url\()['"]?([^\/\)'"][^:\)'"]+)['"]?(\))/g,
    findStyles: /@media *([^\{]+)\{([\S\s]+?)$/,
    only: /(only\s+)?([a-zA-Z]+)\s?/,
    minw: /\(\s*min\-width\s*:\s*(\s*[0-9\.]+)(px|em)\s*\)/,
    maxw: /\(\s*max\-width\s*:\s*(\s*[0-9\.]+)(px|em)\s*\)/,
    minmaxwh: /\(\s*m(in|ax)\-(height|width)\s*:\s*(\s*[0-9\.]+)(px|em)\s*\)/gi,
    other: /\([^\)]*\)/g
  };
  respond.mediaQueriesSupported = w.matchMedia && w.matchMedia("only all") !== null && w.matchMedia("only all").matches;
  if (respond.mediaQueriesSupported) {
    return;
  }
  var doc = w.document, docElem = doc.documentElement, mediastyles = [], rules = [], appendedEls = [], parsedSheets = {}, resizeThrottle = 30, head = doc.getElementsByTagName("head")[0] || docElem, base = doc.getElementsByTagName("base")[0], links = head.getElementsByTagName("link"), lastCall, resizeDefer, eminpx, getEmValue = function() {
    var ret, div = doc.createElement("div"), body = doc.body, originalHTMLFontSize = docElem.style.fontSize, originalBodyFontSize = body && body.style.fontSize, fakeUsed = false;
    div.style.cssText = "position:absolute;font-size:1em;width:1em";
    if (!body) {
      body = fakeUsed = doc.createElement("body");
      body.style.background = "none";
    }
    docElem.style.fontSize = "100%";
    body.style.fontSize = "100%";
    body.appendChild(div);
    if (fakeUsed) {
      docElem.insertBefore(body, docElem.firstChild);
    }
    ret = div.offsetWidth;
    if (fakeUsed) {
      docElem.removeChild(body);
    } else {
      body.removeChild(div);
    }
    docElem.style.fontSize = originalHTMLFontSize;
    if (originalBodyFontSize) {
      body.style.fontSize = originalBodyFontSize;
    }
    ret = eminpx = parseFloat(ret);
    return ret;
  }, applyMedia = function(fromResize) {
    var name = "clientWidth", docElemProp = docElem[name], currWidth = doc.compatMode === "CSS1Compat" && docElemProp || doc.body[name] || docElemProp, styleBlocks = {}, lastLink = links[links.length - 1], now = new Date().getTime();
    if (fromResize && lastCall && now - lastCall < resizeThrottle) {
      w.clearTimeout(resizeDefer);
      resizeDefer = w.setTimeout(applyMedia, resizeThrottle);
      return;
    } else {
      lastCall = now;
    }
    for (var i in mediastyles) {
      if (mediastyles.hasOwnProperty(i)) {
        var thisstyle = mediastyles[i], min = thisstyle.minw, max = thisstyle.maxw, minnull = min === null, maxnull = max === null, em = "em";
        if (!!min) {
          min = parseFloat(min) * (min.indexOf(em) > -1 ? eminpx || getEmValue() : 1);
        }
        if (!!max) {
          max = parseFloat(max) * (max.indexOf(em) > -1 ? eminpx || getEmValue() : 1);
        }
        if (!thisstyle.hasquery || (!minnull || !maxnull) && (minnull || currWidth >= min) && (maxnull || currWidth <= max)) {
          if (!styleBlocks[thisstyle.media]) {
            styleBlocks[thisstyle.media] = [];
          }
          styleBlocks[thisstyle.media].push(rules[thisstyle.rules]);
        }
      }
    }
    for (var j in appendedEls) {
      if (appendedEls.hasOwnProperty(j)) {
        if (appendedEls[j] && appendedEls[j].parentNode === head) {
          head.removeChild(appendedEls[j]);
        }
      }
    }
    appendedEls.length = 0;
    for (var k in styleBlocks) {
      if (styleBlocks.hasOwnProperty(k)) {
        var ss = doc.createElement("style"), css = styleBlocks[k].join("\n");
        ss.type = "text/css";
        ss.media = k;
        head.insertBefore(ss, lastLink.nextSibling);
        if (ss.styleSheet) {
          ss.styleSheet.cssText = css;
        } else {
          ss.appendChild(doc.createTextNode(css));
        }
        appendedEls.push(ss);
      }
    }
  }, translate = function(styles, href, media) {
    var qs = styles.replace(respond.regex.comments, "").replace(respond.regex.keyframes, "").match(respond.regex.media), ql = qs && qs.length || 0;
    href = href.substring(0, href.lastIndexOf("/"));
    var repUrls = function(css) {
      return css.replace(respond.regex.urls, "$1" + href + "$2$3");
    }, useMedia = !ql && media;
    if (href.length) {
      href += "/";
    }
    if (useMedia) {
      ql = 1;
    }
    for (var i = 0; i < ql; i++) {
      var fullq, thisq, eachq, eql;
      if (useMedia) {
        fullq = media;
        rules.push(repUrls(styles));
      } else {
        fullq = qs[i].match(respond.regex.findStyles) && RegExp.$1;
        rules.push(RegExp.$2 && repUrls(RegExp.$2));
      }
      eachq = fullq.split(",");
      eql = eachq.length;
      for (var j = 0; j < eql; j++) {
        thisq = eachq[j];
        if (isUnsupportedMediaQuery(thisq)) {
          continue;
        }
        mediastyles.push({
          media: thisq.split("(")[0].match(respond.regex.only) && RegExp.$2 || "all",
          rules: rules.length - 1,
          hasquery: thisq.indexOf("(") > -1,
          minw: thisq.match(respond.regex.minw) && parseFloat(RegExp.$1) + (RegExp.$2 || ""),
          maxw: thisq.match(respond.regex.maxw) && parseFloat(RegExp.$1) + (RegExp.$2 || "")
        });
      }
    }
    applyMedia();
  }, makeRequests = function() {
    if (requestQueue.length) {
      var thisRequest = requestQueue.shift();
      ajax(thisRequest.href, function(styles) {
        translate(styles, thisRequest.href, thisRequest.media);
        parsedSheets[thisRequest.href] = true;
        w.setTimeout(function() {
          makeRequests();
        }, 0);
      });
    }
  }, ripCSS = function() {
    for (var i = 0; i < links.length; i++) {
      var sheet = links[i], href = sheet.href, media = sheet.media, isCSS = sheet.rel && sheet.rel.toLowerCase() === "stylesheet";
      if (!!href && isCSS && !parsedSheets[href]) {
        if (sheet.styleSheet && sheet.styleSheet.rawCssText) {
          translate(sheet.styleSheet.rawCssText, href, media);
          parsedSheets[href] = true;
        } else {
          if (!/^([a-zA-Z:]*\/\/)/.test(href) && !base || href.replace(RegExp.$1, "").split("/")[0] === w.location.host) {
            if (href.substring(0, 2) === "//") {
              href = w.location.protocol + href;
            }
            requestQueue.push({
              href: href,
              media: media
            });
          }
        }
      }
    }
    makeRequests();
  };
  ripCSS();
  respond.update = ripCSS;
  respond.getEmValue = getEmValue;
  function callMedia() {
    applyMedia(true);
  }
  if (w.addEventListener) {
    w.addEventListener("resize", callMedia, false);
  } else if (w.attachEvent) {
    w.attachEvent("onresize", callMedia);
  }
})(this);
/**
* @preserve HTML5 Shiv 3.7.3 | @afarkas @jdalton @jon_neal @rem | MIT/GPL2 Licensed
*/
;(function(window, document) {
/*jshint evil:true */
  /** version */
  var version = '3.7.3';

  /** Preset options */
  var options = window.html5 || {};

  /** Used to skip problem elements */
  var reSkip = /^<|^(?:button|map|select|textarea|object|iframe|option|optgroup)$/i;

  /** Not all elements can be cloned in IE **/
  var saveClones = /^(?:a|b|code|div|fieldset|h1|h2|h3|h4|h5|h6|i|label|li|ol|p|q|span|strong|style|table|tbody|td|th|tr|ul)$/i;

  /** Detect whether the browser supports default html5 styles */
  var supportsHtml5Styles;

  /** Name of the expando, to work with multiple documents or to re-shiv one document */
  var expando = '_html5shiv';

  /** The id for the the documents expando */
  var expanID = 0;

  /** Cached data for each document */
  var expandoData = {};

  /** Detect whether the browser supports unknown elements */
  var supportsUnknownElements;

  (function() {
    try {
        var a = document.createElement('a');
        a.innerHTML = '<xyz></xyz>';
        //if the hidden property is implemented we can assume, that the browser supports basic HTML5 Styles
        supportsHtml5Styles = ('hidden' in a);

        supportsUnknownElements = a.childNodes.length == 1 || (function() {
          // assign a false positive if unable to shiv
          (document.createElement)('a');
          var frag = document.createDocumentFragment();
          return (
            typeof frag.cloneNode == 'undefined' ||
            typeof frag.createDocumentFragment == 'undefined' ||
            typeof frag.createElement == 'undefined'
          );
        }());
    } catch(e) {
      // assign a false positive if detection fails => unable to shiv
      supportsHtml5Styles = true;
      supportsUnknownElements = true;
    }

  }());

  /*--------------------------------------------------------------------------*/

  /**
   * Creates a style sheet with the given CSS text and adds it to the document.
   * @private
   * @param {Document} ownerDocument The document.
   * @param {String} cssText The CSS text.
   * @returns {StyleSheet} The style element.
   */
  function addStyleSheet(ownerDocument, cssText) {
    var p = ownerDocument.createElement('p'),
        parent = ownerDocument.getElementsByTagName('head')[0] || ownerDocument.documentElement;

    p.innerHTML = 'x<style>' + cssText + '</style>';
    return parent.insertBefore(p.lastChild, parent.firstChild);
  }

  /**
   * Returns the value of `html5.elements` as an array.
   * @private
   * @returns {Array} An array of shived element node names.
   */
  function getElements() {
    var elements = html5.elements;
    return typeof elements == 'string' ? elements.split(' ') : elements;
  }

  /**
   * Extends the built-in list of html5 elements
   * @memberOf html5
   * @param {String|Array} newElements whitespace separated list or array of new element names to shiv
   * @param {Document} ownerDocument The context document.
   */
  function addElements(newElements, ownerDocument) {
    var elements = html5.elements;
    if(typeof elements != 'string'){
      elements = elements.join(' ');
    }
    if(typeof newElements != 'string'){
      newElements = newElements.join(' ');
    }
    html5.elements = elements +' '+ newElements;
    shivDocument(ownerDocument);
  }

   /**
   * Returns the data associated to the given document
   * @private
   * @param {Document} ownerDocument The document.
   * @returns {Object} An object of data.
   */
  function getExpandoData(ownerDocument) {
    var data = expandoData[ownerDocument[expando]];
    if (!data) {
        data = {};
        expanID++;
        ownerDocument[expando] = expanID;
        expandoData[expanID] = data;
    }
    return data;
  }

  /**
   * returns a shived element for the given nodeName and document
   * @memberOf html5
   * @param {String} nodeName name of the element
   * @param {Document|DocumentFragment} ownerDocument The context document.
   * @returns {Object} The shived element.
   */
  function createElement(nodeName, ownerDocument, data){
    if (!ownerDocument) {
        ownerDocument = document;
    }
    if(supportsUnknownElements){
        return ownerDocument.createElement(nodeName);
    }
    if (!data) {
        data = getExpandoData(ownerDocument);
    }
    var node;

    if (data.cache[nodeName]) {
        node = data.cache[nodeName].cloneNode();
    } else if (saveClones.test(nodeName)) {
        node = (data.cache[nodeName] = data.createElem(nodeName)).cloneNode();
    } else {
        node = data.createElem(nodeName);
    }

    // Avoid adding some elements to fragments in IE < 9 because
    // * Attributes like `name` or `type` cannot be set/changed once an element
    //   is inserted into a document/fragment
    // * Link elements with `src` attributes that are inaccessible, as with
    //   a 403 response, will cause the tab/window to crash
    // * Script elements appended to fragments will execute when their `src`
    //   or `text` property is set
    return node.canHaveChildren && !reSkip.test(nodeName) && !node.tagUrn ? data.frag.appendChild(node) : node;
  }

  /**
   * returns a shived DocumentFragment for the given document
   * @memberOf html5
   * @param {Document} ownerDocument The context document.
   * @returns {Object} The shived DocumentFragment.
   */
  function createDocumentFragment(ownerDocument, data){
    if (!ownerDocument) {
        ownerDocument = document;
    }
    if(supportsUnknownElements){
        return ownerDocument.createDocumentFragment();
    }
    data = data || getExpandoData(ownerDocument);
    var clone = data.frag.cloneNode(),
        i = 0,
        elems = getElements(),
        l = elems.length;
    for(;i<l;i++){
        clone.createElement(elems[i]);
    }
    return clone;
  }

  /**
   * Shivs the `createElement` and `createDocumentFragment` methods of the document.
   * @private
   * @param {Document|DocumentFragment} ownerDocument The document.
   * @param {Object} data of the document.
   */
  function shivMethods(ownerDocument, data) {
    if (!data.cache) {
        data.cache = {};
        data.createElem = ownerDocument.createElement;
        data.createFrag = ownerDocument.createDocumentFragment;
        data.frag = data.createFrag();
    }


    ownerDocument.createElement = function(nodeName) {
      //abort shiv
      if (!html5.shivMethods) {
          return data.createElem(nodeName);
      }
      return createElement(nodeName, ownerDocument, data);
    };

    ownerDocument.createDocumentFragment = Function('h,f', 'return function(){' +
      'var n=f.cloneNode(),c=n.createElement;' +
      'h.shivMethods&&(' +
        // unroll the `createElement` calls
        getElements().join().replace(/[\w\-:]+/g, function(nodeName) {
          data.createElem(nodeName);
          data.frag.createElement(nodeName);
          return 'c("' + nodeName + '")';
        }) +
      ');return n}'
    )(html5, data.frag);
  }

  /*--------------------------------------------------------------------------*/

  /**
   * Shivs the given document.
   * @memberOf html5
   * @param {Document} ownerDocument The document to shiv.
   * @returns {Document} The shived document.
   */
  function shivDocument(ownerDocument) {
    if (!ownerDocument) {
        ownerDocument = document;
    }
    var data = getExpandoData(ownerDocument);

    if (html5.shivCSS && !supportsHtml5Styles && !data.hasCSS) {
      data.hasCSS = !!addStyleSheet(ownerDocument,
        // corrects block display not defined in IE6/7/8/9
        'article,aside,dialog,figcaption,figure,footer,header,hgroup,main,nav,section{display:block}' +
        // adds styling not present in IE6/7/8/9
        'mark{background:#FF0;color:#000}' +
        // hides non-rendered elements
        'template{display:none}'
      );
    }
    if (!supportsUnknownElements) {
      shivMethods(ownerDocument, data);
    }
    return ownerDocument;
  }

  /*--------------------------------------------------------------------------*/

  /**
   * The `html5` object is exposed so that more elements can be shived and
   * existing shiving can be detected on iframes.
   * @type Object
   * @example
   *
   * // options can be changed before the script is included
   * html5 = { 'elements': 'mark section', 'shivCSS': false, 'shivMethods': false };
   */
  var html5 = {

    /**
     * An array or space separated string of node names of the elements to shiv.
     * @memberOf html5
     * @type Array|String
     */
    'elements': options.elements || 'abbr article aside audio bdi canvas data datalist details dialog figcaption figure footer header hgroup main mark meter nav output picture progress section summary template time video',

    /**
     * current version of html5shiv
     */
    'version': version,

    /**
     * A flag to indicate that the HTML5 style sheet should be inserted.
     * @memberOf html5
     * @type Boolean
     */
    'shivCSS': (options.shivCSS !== false),

    /**
     * Is equal to true if a browser supports creating unknown/HTML5 elements
     * @memberOf html5
     * @type boolean
     */
    'supportsUnknownElements': supportsUnknownElements,

    /**
     * A flag to indicate that the document's `createElement` and `createDocumentFragment`
     * methods should be overwritten.
     * @memberOf html5
     * @type Boolean
     */
    'shivMethods': (options.shivMethods !== false),

    /**
     * A string to describe the type of `html5` object ("default" or "default print").
     * @memberOf html5
     * @type String
     */
    'type': 'default',

    // shivs the document according to the specified `html5` object options
    'shivDocument': shivDocument,

    //creates a shived element
    createElement: createElement,

    //creates a shived documentFragment
    createDocumentFragment: createDocumentFragment,

    //extends list of elements
    addElements: addElements
  };

  /*--------------------------------------------------------------------------*/

  // expose html5
  window.html5 = html5;

  // shiv the document
  shivDocument(document);

  if(typeof module == 'object' && module.exports){
    module.exports = html5;
  }

}(typeof window !== "undefined" ? window : this, document));

// Copyright 2006 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.


// Known Issues:
//
// * Patterns only support repeat.
// * Radial gradient are not implemented. The VML version of these look very
//   different from the canvas one.
// * Clipping paths are not implemented.
// * Coordsize. The width and height attribute have higher priority than the
//   width and height style values which isn't correct.
// * Painting mode isn't implemented.
// * Canvas width/height should is using content-box by default. IE in
//   Quirks mode will draw the canvas using border-box. Either change your
//   doctype to HTML5
//   (http://www.whatwg.org/specs/web-apps/current-work/#the-doctype)
//   or use Box Sizing Behavior from WebFX
//   (http://webfx.eae.net/dhtml/boxsizing/boxsizing.html)
// * Non uniform scaling does not correctly scale strokes.
// * Optimize. There is always room for speed improvements.

// Only add this code if we do not already have a canvas implementation
if (!document.createElement('canvas').getContext) {

(function() {

  // alias some functions to make (compiled) code shorter
  var m = Math;
  var mr = m.round;
  var ms = m.sin;
  var mc = m.cos;
  var abs = m.abs;
  var sqrt = m.sqrt;

  // this is used for sub pixel precision
  var Z = 10;
  var Z2 = Z / 2;

  var IE_VERSION = +navigator.userAgent.match(/MSIE ([\d.]+)?/)[1];

  /**
   * This funtion is assigned to the <canvas> elements as element.getContext().
   * @this {HTMLElement}
   * @return {CanvasRenderingContext2D_}
   */
  function getContext() {
    return this.context_ ||
        (this.context_ = new CanvasRenderingContext2D_(this));
  }

  var slice = Array.prototype.slice;

  /**
   * Binds a function to an object. The returned function will always use the
   * passed in {@code obj} as {@code this}.
   *
   * Example:
   *
   *   g = bind(f, obj, a, b)
   *   g(c, d) // will do f.call(obj, a, b, c, d)
   *
   * @param {Function} f The function to bind the object to
   * @param {Object} obj The object that should act as this when the function
   *     is called
   * @param {*} var_args Rest arguments that will be used as the initial
   *     arguments when the function is called
   * @return {Function} A new function that has bound this
   */
  function bind(f, obj, var_args) {
    var a = slice.call(arguments, 2);
    return function() {
      return f.apply(obj, a.concat(slice.call(arguments)));
    };
  }

  function encodeHtmlAttribute(s) {
    return String(s).replace(/&/g, '&amp;').replace(/"/g, '&quot;');
  }

  function addNamespace(doc, prefix, urn) {
    if (!doc.namespaces[prefix]) {
      doc.namespaces.add(prefix, urn, '#default#VML');
    }
  }

  function addNamespacesAndStylesheet(doc) {
    addNamespace(doc, 'g_vml_', 'urn:schemas-microsoft-com:vml');
    addNamespace(doc, 'g_o_', 'urn:schemas-microsoft-com:office:office');

    // Setup default CSS.  Only add one style sheet per document
    if (!doc.styleSheets['ex_canvas_']) {
      var ss = doc.createStyleSheet();
      ss.owningElement.id = 'ex_canvas_';
      ss.cssText = 'canvas{display:inline-block;overflow:hidden;' +
          // default size is 300x150 in Gecko and Opera
          'text-align:left;width:300px;height:150px}';
    }
  }

  // Add namespaces and stylesheet at startup.
  addNamespacesAndStylesheet(document);

  var G_vmlCanvasManager_ = {
    init: function(opt_doc) {
      var doc = opt_doc || document;
      // Create a dummy element so that IE will allow canvas elements to be
      // recognized.
      doc.createElement('canvas');
      doc.attachEvent('onreadystatechange', bind(this.init_, this, doc));
    },

    init_: function(doc) {
      // find all canvas elements
      var els = doc.getElementsByTagName('canvas');
      for (var i = 0; i < els.length; i++) {
        this.initElement(els[i]);
      }
    },

    /**
     * Public initializes a canvas element so that it can be used as canvas
     * element from now on. This is called automatically before the page is
     * loaded but if you are creating elements using createElement you need to
     * make sure this is called on the element.
     * @param {HTMLElement} el The canvas element to initialize.
     * @return {HTMLElement} the element that was created.
     */
    initElement: function(el) {
      if (!el.getContext) {
        el.getContext = getContext;

        // Add namespaces and stylesheet to document of the element.
        addNamespacesAndStylesheet(el.ownerDocument);

        // Remove fallback content. There is no way to hide text nodes so we
        // just remove all childNodes. We could hide all elements and remove
        // text nodes but who really cares about the fallback content.
        el.innerHTML = '';

        // do not use inline function because that will leak memory
        el.attachEvent('onpropertychange', onPropertyChange);
        el.attachEvent('onresize', onResize);

        var attrs = el.attributes;
        if (attrs.width && attrs.width.specified) {
          // TODO: use runtimeStyle and coordsize
          // el.getContext().setWidth_(attrs.width.nodeValue);
          el.style.width = attrs.width.nodeValue + 'px';
        } else {
          el.width = el.clientWidth;
        }
        if (attrs.height && attrs.height.specified) {
          // TODO: use runtimeStyle and coordsize
          // el.getContext().setHeight_(attrs.height.nodeValue);
          el.style.height = attrs.height.nodeValue + 'px';
        } else {
          el.height = el.clientHeight;
        }
        //el.getContext().setCoordsize_()
      }
      return el;
    }
  };

  function onPropertyChange(e) {
    var el = e.srcElement;

    switch (e.propertyName) {
      case 'width':
        el.getContext().clearRect();
        el.style.width = el.attributes.width.nodeValue + 'px';
        // In IE8 this does not trigger onresize.
        el.firstChild.style.width =  el.clientWidth + 'px';
        break;
      case 'height':
        el.getContext().clearRect();
        el.style.height = el.attributes.height.nodeValue + 'px';
        el.firstChild.style.height = el.clientHeight + 'px';
        break;
    }
  }

  function onResize(e) {
    var el = e.srcElement;
    if (el.firstChild) {
      el.firstChild.style.width =  el.clientWidth + 'px';
      el.firstChild.style.height = el.clientHeight + 'px';
    }
  }

  G_vmlCanvasManager_.init();

  // precompute "00" to "FF"
  var decToHex = [];
  for (var i = 0; i < 16; i++) {
    for (var j = 0; j < 16; j++) {
      decToHex[i * 16 + j] = i.toString(16) + j.toString(16);
    }
  }

  function createMatrixIdentity() {
    return [
      [1, 0, 0],
      [0, 1, 0],
      [0, 0, 1]
    ];
  }

  function matrixMultiply(m1, m2) {
    var result = createMatrixIdentity();

    for (var x = 0; x < 3; x++) {
      for (var y = 0; y < 3; y++) {
        var sum = 0;

        for (var z = 0; z < 3; z++) {
          sum += m1[x][z] * m2[z][y];
        }

        result[x][y] = sum;
      }
    }
    return result;
  }

  function copyState(o1, o2) {
    o2.fillStyle     = o1.fillStyle;
    o2.lineCap       = o1.lineCap;
    o2.lineJoin      = o1.lineJoin;
    o2.lineWidth     = o1.lineWidth;
    o2.miterLimit    = o1.miterLimit;
    o2.shadowBlur    = o1.shadowBlur;
    o2.shadowColor   = o1.shadowColor;
    o2.shadowOffsetX = o1.shadowOffsetX;
    o2.shadowOffsetY = o1.shadowOffsetY;
    o2.strokeStyle   = o1.strokeStyle;
    o2.globalAlpha   = o1.globalAlpha;
    o2.font          = o1.font;
    o2.textAlign     = o1.textAlign;
    o2.textBaseline  = o1.textBaseline;
    o2.arcScaleX_    = o1.arcScaleX_;
    o2.arcScaleY_    = o1.arcScaleY_;
    o2.lineScale_    = o1.lineScale_;
  }

  var colorData = {
    aliceblue: '#F0F8FF',
    antiquewhite: '#FAEBD7',
    aquamarine: '#7FFFD4',
    azure: '#F0FFFF',
    beige: '#F5F5DC',
    bisque: '#FFE4C4',
    black: '#000000',
    blanchedalmond: '#FFEBCD',
    blueviolet: '#8A2BE2',
    brown: '#A52A2A',
    burlywood: '#DEB887',
    cadetblue: '#5F9EA0',
    chartreuse: '#7FFF00',
    chocolate: '#D2691E',
    coral: '#FF7F50',
    cornflowerblue: '#6495ED',
    cornsilk: '#FFF8DC',
    crimson: '#DC143C',
    cyan: '#00FFFF',
    darkblue: '#00008B',
    darkcyan: '#008B8B',
    darkgoldenrod: '#B8860B',
    darkgray: '#A9A9A9',
    darkgreen: '#006400',
    darkgrey: '#A9A9A9',
    darkkhaki: '#BDB76B',
    darkmagenta: '#8B008B',
    darkolivegreen: '#556B2F',
    darkorange: '#FF8C00',
    darkorchid: '#9932CC',
    darkred: '#8B0000',
    darksalmon: '#E9967A',
    darkseagreen: '#8FBC8F',
    darkslateblue: '#483D8B',
    darkslategray: '#2F4F4F',
    darkslategrey: '#2F4F4F',
    darkturquoise: '#00CED1',
    darkviolet: '#9400D3',
    deeppink: '#FF1493',
    deepskyblue: '#00BFFF',
    dimgray: '#696969',
    dimgrey: '#696969',
    dodgerblue: '#1E90FF',
    firebrick: '#B22222',
    floralwhite: '#FFFAF0',
    forestgreen: '#228B22',
    gainsboro: '#DCDCDC',
    ghostwhite: '#F8F8FF',
    gold: '#FFD700',
    goldenrod: '#DAA520',
    grey: '#808080',
    greenyellow: '#ADFF2F',
    honeydew: '#F0FFF0',
    hotpink: '#FF69B4',
    indianred: '#CD5C5C',
    indigo: '#4B0082',
    ivory: '#FFFFF0',
    khaki: '#F0E68C',
    lavender: '#E6E6FA',
    lavenderblush: '#FFF0F5',
    lawngreen: '#7CFC00',
    lemonchiffon: '#FFFACD',
    lightblue: '#ADD8E6',
    lightcoral: '#F08080',
    lightcyan: '#E0FFFF',
    lightgoldenrodyellow: '#FAFAD2',
    lightgreen: '#90EE90',
    lightgrey: '#D3D3D3',
    lightpink: '#FFB6C1',
    lightsalmon: '#FFA07A',
    lightseagreen: '#20B2AA',
    lightskyblue: '#87CEFA',
    lightslategray: '#778899',
    lightslategrey: '#778899',
    lightsteelblue: '#B0C4DE',
    lightyellow: '#FFFFE0',
    limegreen: '#32CD32',
    linen: '#FAF0E6',
    magenta: '#FF00FF',
    mediumaquamarine: '#66CDAA',
    mediumblue: '#0000CD',
    mediumorchid: '#BA55D3',
    mediumpurple: '#9370DB',
    mediumseagreen: '#3CB371',
    mediumslateblue: '#7B68EE',
    mediumspringgreen: '#00FA9A',
    mediumturquoise: '#48D1CC',
    mediumvioletred: '#C71585',
    midnightblue: '#191970',
    mintcream: '#F5FFFA',
    mistyrose: '#FFE4E1',
    moccasin: '#FFE4B5',
    navajowhite: '#FFDEAD',
    oldlace: '#FDF5E6',
    olivedrab: '#6B8E23',
    orange: '#FFA500',
    orangered: '#FF4500',
    orchid: '#DA70D6',
    palegoldenrod: '#EEE8AA',
    palegreen: '#98FB98',
    paleturquoise: '#AFEEEE',
    palevioletred: '#DB7093',
    papayawhip: '#FFEFD5',
    peachpuff: '#FFDAB9',
    peru: '#CD853F',
    pink: '#FFC0CB',
    plum: '#DDA0DD',
    powderblue: '#B0E0E6',
    rosybrown: '#BC8F8F',
    royalblue: '#4169E1',
    saddlebrown: '#8B4513',
    salmon: '#FA8072',
    sandybrown: '#F4A460',
    seagreen: '#2E8B57',
    seashell: '#FFF5EE',
    sienna: '#A0522D',
    skyblue: '#87CEEB',
    slateblue: '#6A5ACD',
    slategray: '#708090',
    slategrey: '#708090',
    snow: '#FFFAFA',
    springgreen: '#00FF7F',
    steelblue: '#4682B4',
    tan: '#D2B48C',
    thistle: '#D8BFD8',
    tomato: '#FF6347',
    turquoise: '#40E0D0',
    violet: '#EE82EE',
    wheat: '#F5DEB3',
    whitesmoke: '#F5F5F5',
    yellowgreen: '#9ACD32'
  };


  function getRgbHslContent(styleString) {
    var start = styleString.indexOf('(', 3);
    var end = styleString.indexOf(')', start + 1);
    var parts = styleString.substring(start + 1, end).split(',');
    // add alpha if needed
    if (parts.length != 4 || styleString.charAt(3) != 'a') {
      parts[3] = 1;
    }
    return parts;
  }

  function percent(s) {
    return parseFloat(s) / 100;
  }

  function clamp(v, min, max) {
    return Math.min(max, Math.max(min, v));
  }

  function hslToRgb(parts){
    var r, g, b, h, s, l;
    h = parseFloat(parts[0]) / 360 % 360;
    if (h < 0)
      h++;
    s = clamp(percent(parts[1]), 0, 1);
    l = clamp(percent(parts[2]), 0, 1);
    if (s == 0) {
      r = g = b = l; // achromatic
    } else {
      var q = l < 0.5 ? l * (1 + s) : l + s - l * s;
      var p = 2 * l - q;
      r = hueToRgb(p, q, h + 1 / 3);
      g = hueToRgb(p, q, h);
      b = hueToRgb(p, q, h - 1 / 3);
    }

    return '#' + decToHex[Math.floor(r * 255)] +
        decToHex[Math.floor(g * 255)] +
        decToHex[Math.floor(b * 255)];
  }

  function hueToRgb(m1, m2, h) {
    if (h < 0)
      h++;
    if (h > 1)
      h--;

    if (6 * h < 1)
      return m1 + (m2 - m1) * 6 * h;
    else if (2 * h < 1)
      return m2;
    else if (3 * h < 2)
      return m1 + (m2 - m1) * (2 / 3 - h) * 6;
    else
      return m1;
  }

  var processStyleCache = {};

  function processStyle(styleString) {
    if (styleString in processStyleCache) {
      return processStyleCache[styleString];
    }

    var str, alpha = 1;

    styleString = String(styleString);
    if (styleString.charAt(0) == '#') {
      str = styleString;
    } else if (/^rgb/.test(styleString)) {
      var parts = getRgbHslContent(styleString);
      var str = '#', n;
      for (var i = 0; i < 3; i++) {
        if (parts[i].indexOf('%') != -1) {
          n = Math.floor(percent(parts[i]) * 255);
        } else {
          n = +parts[i];
        }
        str += decToHex[clamp(n, 0, 255)];
      }
      alpha = +parts[3];
    } else if (/^hsl/.test(styleString)) {
      var parts = getRgbHslContent(styleString);
      str = hslToRgb(parts);
      alpha = parts[3];
    } else {
      str = colorData[styleString] || styleString;
    }
    return processStyleCache[styleString] = {color: str, alpha: alpha};
  }

  var DEFAULT_STYLE = {
    style: 'normal',
    variant: 'normal',
    weight: 'normal',
    size: 10,
    family: 'sans-serif'
  };

  // Internal text style cache
  var fontStyleCache = {};

  function processFontStyle(styleString) {
    if (fontStyleCache[styleString]) {
      return fontStyleCache[styleString];
    }

    var el = document.createElement('div');
    var style = el.style;
    try {
      style.font = styleString;
    } catch (ex) {
      // Ignore failures to set to invalid font.
    }

    return fontStyleCache[styleString] = {
      style: style.fontStyle || DEFAULT_STYLE.style,
      variant: style.fontVariant || DEFAULT_STYLE.variant,
      weight: style.fontWeight || DEFAULT_STYLE.weight,
      size: style.fontSize || DEFAULT_STYLE.size,
      family: style.fontFamily || DEFAULT_STYLE.family
    };
  }

  function getComputedStyle(style, element) {
    var computedStyle = {};

    for (var p in style) {
      computedStyle[p] = style[p];
    }

    // Compute the size
    var canvasFontSize = parseFloat(element.currentStyle.fontSize),
        fontSize = parseFloat(style.size);

    if (typeof style.size == 'number') {
      computedStyle.size = style.size;
    } else if (style.size.indexOf('px') != -1) {
      computedStyle.size = fontSize;
    } else if (style.size.indexOf('em') != -1) {
      computedStyle.size = canvasFontSize * fontSize;
    } else if(style.size.indexOf('%') != -1) {
      computedStyle.size = (canvasFontSize / 100) * fontSize;
    } else if (style.size.indexOf('pt') != -1) {
      computedStyle.size = fontSize / .75;
    } else {
      computedStyle.size = canvasFontSize;
    }

    // Different scaling between normal text and VML text. This was found using
    // trial and error to get the same size as non VML text.
    computedStyle.size *= 0.981;

    return computedStyle;
  }

  function buildStyle(style) {
    return style.style + ' ' + style.variant + ' ' + style.weight + ' ' +
        style.size + 'px ' + style.family;
  }

  var lineCapMap = {
    'butt': 'flat',
    'round': 'round'
  };

  function processLineCap(lineCap) {
    return lineCapMap[lineCap] || 'square';
  }

  /**
   * This class implements CanvasRenderingContext2D interface as described by
   * the WHATWG.
   * @param {HTMLElement} canvasElement The element that the 2D context should
   * be associated with
   */
  function CanvasRenderingContext2D_(canvasElement) {
    this.m_ = createMatrixIdentity();

    this.mStack_ = [];
    this.aStack_ = [];
    this.currentPath_ = [];

    // Canvas context properties
    this.strokeStyle = '#000';
    this.fillStyle = '#000';

    this.lineWidth = 1;
    this.lineJoin = 'miter';
    this.lineCap = 'butt';
    this.miterLimit = Z * 1;
    this.globalAlpha = 1;
    this.font = '10px sans-serif';
    this.textAlign = 'left';
    this.textBaseline = 'alphabetic';
    this.canvas = canvasElement;

    var cssText = 'width:' + canvasElement.clientWidth + 'px;height:' +
        canvasElement.clientHeight + 'px;overflow:hidden;position:absolute';
    var el = canvasElement.ownerDocument.createElement('div');
    el.style.cssText = cssText;
    canvasElement.appendChild(el);

    var overlayEl = el.cloneNode(false);
    // Use a non transparent background.
    overlayEl.style.backgroundColor = 'red';
    overlayEl.style.filter = 'alpha(opacity=0)';
    canvasElement.appendChild(overlayEl);

    this.element_ = el;
    this.arcScaleX_ = 1;
    this.arcScaleY_ = 1;
    this.lineScale_ = 1;
  }

  var contextPrototype = CanvasRenderingContext2D_.prototype;
  contextPrototype.clearRect = function() {
    if (this.textMeasureEl_) {
      this.textMeasureEl_.removeNode(true);
      this.textMeasureEl_ = null;
    }
    this.element_.innerHTML = '';
  };

  contextPrototype.beginPath = function() {
    // TODO: Branch current matrix so that save/restore has no effect
    //       as per safari docs.
    this.currentPath_ = [];
  };

  contextPrototype.moveTo = function(aX, aY) {
    var p = getCoords(this, aX, aY);
    this.currentPath_.push({type: 'moveTo', x: p.x, y: p.y});
    this.currentX_ = p.x;
    this.currentY_ = p.y;
  };

  contextPrototype.lineTo = function(aX, aY) {
    var p = getCoords(this, aX, aY);
    this.currentPath_.push({type: 'lineTo', x: p.x, y: p.y});

    this.currentX_ = p.x;
    this.currentY_ = p.y;
  };

  contextPrototype.bezierCurveTo = function(aCP1x, aCP1y,
                                            aCP2x, aCP2y,
                                            aX, aY) {
    var p = getCoords(this, aX, aY);
    var cp1 = getCoords(this, aCP1x, aCP1y);
    var cp2 = getCoords(this, aCP2x, aCP2y);
    bezierCurveTo(this, cp1, cp2, p);
  };

  // Helper function that takes the already fixed cordinates.
  function bezierCurveTo(self, cp1, cp2, p) {
    self.currentPath_.push({
      type: 'bezierCurveTo',
      cp1x: cp1.x,
      cp1y: cp1.y,
      cp2x: cp2.x,
      cp2y: cp2.y,
      x: p.x,
      y: p.y
    });
    self.currentX_ = p.x;
    self.currentY_ = p.y;
  }

  contextPrototype.quadraticCurveTo = function(aCPx, aCPy, aX, aY) {
    // the following is lifted almost directly from
    // http://developer.mozilla.org/en/docs/Canvas_tutorial:Drawing_shapes

    var cp = getCoords(this, aCPx, aCPy);
    var p = getCoords(this, aX, aY);

    var cp1 = {
      x: this.currentX_ + 2.0 / 3.0 * (cp.x - this.currentX_),
      y: this.currentY_ + 2.0 / 3.0 * (cp.y - this.currentY_)
    };
    var cp2 = {
      x: cp1.x + (p.x - this.currentX_) / 3.0,
      y: cp1.y + (p.y - this.currentY_) / 3.0
    };

    bezierCurveTo(this, cp1, cp2, p);
  };

  contextPrototype.arc = function(aX, aY, aRadius,
                                  aStartAngle, aEndAngle, aClockwise) {
    aRadius *= Z;
    var arcType = aClockwise ? 'at' : 'wa';

    var xStart = aX + mc(aStartAngle) * aRadius - Z2;
    var yStart = aY + ms(aStartAngle) * aRadius - Z2;

    var xEnd = aX + mc(aEndAngle) * aRadius - Z2;
    var yEnd = aY + ms(aEndAngle) * aRadius - Z2;

    // IE won't render arches drawn counter clockwise if xStart == xEnd.
    if (xStart == xEnd && !aClockwise) {
      xStart += 0.125; // Offset xStart by 1/80 of a pixel. Use something
                       // that can be represented in binary
    }

    var p = getCoords(this, aX, aY);
    var pStart = getCoords(this, xStart, yStart);
    var pEnd = getCoords(this, xEnd, yEnd);

    this.currentPath_.push({type: arcType,
                           x: p.x,
                           y: p.y,
                           radius: aRadius,
                           xStart: pStart.x,
                           yStart: pStart.y,
                           xEnd: pEnd.x,
                           yEnd: pEnd.y});

  };

  contextPrototype.rect = function(aX, aY, aWidth, aHeight) {
    this.moveTo(aX, aY);
    this.lineTo(aX + aWidth, aY);
    this.lineTo(aX + aWidth, aY + aHeight);
    this.lineTo(aX, aY + aHeight);
    this.closePath();
  };

  contextPrototype.strokeRect = function(aX, aY, aWidth, aHeight) {
    var oldPath = this.currentPath_;
    this.beginPath();

    this.moveTo(aX, aY);
    this.lineTo(aX + aWidth, aY);
    this.lineTo(aX + aWidth, aY + aHeight);
    this.lineTo(aX, aY + aHeight);
    this.closePath();
    this.stroke();

    this.currentPath_ = oldPath;
  };

  contextPrototype.fillRect = function(aX, aY, aWidth, aHeight) {
    var oldPath = this.currentPath_;
    this.beginPath();

    this.moveTo(aX, aY);
    this.lineTo(aX + aWidth, aY);
    this.lineTo(aX + aWidth, aY + aHeight);
    this.lineTo(aX, aY + aHeight);
    this.closePath();
    this.fill();

    this.currentPath_ = oldPath;
  };

  contextPrototype.createLinearGradient = function(aX0, aY0, aX1, aY1) {
    var gradient = new CanvasGradient_('gradient');
    gradient.x0_ = aX0;
    gradient.y0_ = aY0;
    gradient.x1_ = aX1;
    gradient.y1_ = aY1;
    return gradient;
  };

  contextPrototype.createRadialGradient = function(aX0, aY0, aR0,
                                                   aX1, aY1, aR1) {
    var gradient = new CanvasGradient_('gradientradial');
    gradient.x0_ = aX0;
    gradient.y0_ = aY0;
    gradient.r0_ = aR0;
    gradient.x1_ = aX1;
    gradient.y1_ = aY1;
    gradient.r1_ = aR1;
    return gradient;
  };

  contextPrototype.drawImage = function(image, var_args) {
    var dx, dy, dw, dh, sx, sy, sw, sh;

    // to find the original width we overide the width and height
    var oldRuntimeWidth = image.runtimeStyle.width;
    var oldRuntimeHeight = image.runtimeStyle.height;
    image.runtimeStyle.width = 'auto';
    image.runtimeStyle.height = 'auto';

    // get the original size
    var w = image.width;
    var h = image.height;

    // and remove overides
    image.runtimeStyle.width = oldRuntimeWidth;
    image.runtimeStyle.height = oldRuntimeHeight;

    if (arguments.length == 3) {
      dx = arguments[1];
      dy = arguments[2];
      sx = sy = 0;
      sw = dw = w;
      sh = dh = h;
    } else if (arguments.length == 5) {
      dx = arguments[1];
      dy = arguments[2];
      dw = arguments[3];
      dh = arguments[4];
      sx = sy = 0;
      sw = w;
      sh = h;
    } else if (arguments.length == 9) {
      sx = arguments[1];
      sy = arguments[2];
      sw = arguments[3];
      sh = arguments[4];
      dx = arguments[5];
      dy = arguments[6];
      dw = arguments[7];
      dh = arguments[8];
    } else {
      throw Error('Invalid number of arguments');
    }

    var d = getCoords(this, dx, dy);

    var w2 = sw / 2;
    var h2 = sh / 2;

    var vmlStr = [];

    var W = 10;
    var H = 10;

    // For some reason that I've now forgotten, using divs didn't work
    vmlStr.push(' <g_vml_:group',
                ' coordsize="', Z * W, ',', Z * H, '"',
                ' coordorigin="0,0"' ,
                ' style="width:', W, 'px;height:', H, 'px;position:absolute;');

    // If filters are necessary (rotation exists), create them
    // filters are bog-slow, so only create them if abbsolutely necessary
    // The following check doesn't account for skews (which don't exist
    // in the canvas spec (yet) anyway.

    if (this.m_[0][0] != 1 || this.m_[0][1] ||
        this.m_[1][1] != 1 || this.m_[1][0]) {
      var filter = [];

      // Note the 12/21 reversal
      filter.push('M11=', this.m_[0][0], ',',
                  'M12=', this.m_[1][0], ',',
                  'M21=', this.m_[0][1], ',',
                  'M22=', this.m_[1][1], ',',
                  'Dx=', mr(d.x / Z), ',',
                  'Dy=', mr(d.y / Z), '');

      // Bounding box calculation (need to minimize displayed area so that
      // filters don't waste time on unused pixels.
      var max = d;
      var c2 = getCoords(this, dx + dw, dy);
      var c3 = getCoords(this, dx, dy + dh);
      var c4 = getCoords(this, dx + dw, dy + dh);

      max.x = m.max(max.x, c2.x, c3.x, c4.x);
      max.y = m.max(max.y, c2.y, c3.y, c4.y);

      vmlStr.push('padding:0 ', mr(max.x / Z), 'px ', mr(max.y / Z),
                  'px 0;filter:progid:DXImageTransform.Microsoft.Matrix(',
                  filter.join(''), ", sizingmethod='clip');");

    } else {
      vmlStr.push('top:', mr(d.y / Z), 'px;left:', mr(d.x / Z), 'px;');
    }

    vmlStr.push(' ">' ,
                '<g_vml_:image src="', image.src, '"',
                ' style="width:', Z * dw, 'px;',
                ' height:', Z * dh, 'px"',
                ' cropleft="', sx / w, '"',
                ' croptop="', sy / h, '"',
                ' cropright="', (w - sx - sw) / w, '"',
                ' cropbottom="', (h - sy - sh) / h, '"',
                ' />',
                '</g_vml_:group>');

    this.element_.insertAdjacentHTML('BeforeEnd', vmlStr.join(''));
  };

  contextPrototype.stroke = function(aFill) {
    var lineStr = [];
    var lineOpen = false;

    var W = 10;
    var H = 10;

    lineStr.push('<g_vml_:shape',
                 ' filled="', !!aFill, '"',
                 ' style="position:absolute;width:', W, 'px;height:', H, 'px;"',
                 ' coordorigin="0,0"',
                 ' coordsize="', Z * W, ',', Z * H, '"',
                 ' stroked="', !aFill, '"',
                 ' path="');

    var newSeq = false;
    var min = {x: null, y: null};
    var max = {x: null, y: null};

    for (var i = 0; i < this.currentPath_.length; i++) {
      var p = this.currentPath_[i];
      var c;

      switch (p.type) {
        case 'moveTo':
          c = p;
          lineStr.push(' m ', mr(p.x), ',', mr(p.y));
          break;
        case 'lineTo':
          lineStr.push(' l ', mr(p.x), ',', mr(p.y));
          break;
        case 'close':
          lineStr.push(' x ');
          p = null;
          break;
        case 'bezierCurveTo':
          lineStr.push(' c ',
                       mr(p.cp1x), ',', mr(p.cp1y), ',',
                       mr(p.cp2x), ',', mr(p.cp2y), ',',
                       mr(p.x), ',', mr(p.y));
          break;
        case 'at':
        case 'wa':
          lineStr.push(' ', p.type, ' ',
                       mr(p.x - this.arcScaleX_ * p.radius), ',',
                       mr(p.y - this.arcScaleY_ * p.radius), ' ',
                       mr(p.x + this.arcScaleX_ * p.radius), ',',
                       mr(p.y + this.arcScaleY_ * p.radius), ' ',
                       mr(p.xStart), ',', mr(p.yStart), ' ',
                       mr(p.xEnd), ',', mr(p.yEnd));
          break;
      }


      // TODO: Following is broken for curves due to
      //       move to proper paths.

      // Figure out dimensions so we can do gradient fills
      // properly
      if (p) {
        if (min.x == null || p.x < min.x) {
          min.x = p.x;
        }
        if (max.x == null || p.x > max.x) {
          max.x = p.x;
        }
        if (min.y == null || p.y < min.y) {
          min.y = p.y;
        }
        if (max.y == null || p.y > max.y) {
          max.y = p.y;
        }
      }
    }
    lineStr.push(' ">');

    if (!aFill) {
      appendStroke(this, lineStr);
    } else {
      appendFill(this, lineStr, min, max);
    }

    lineStr.push('</g_vml_:shape>');

    this.element_.insertAdjacentHTML('beforeEnd', lineStr.join(''));
  };

  function appendStroke(ctx, lineStr) {
    var a = processStyle(ctx.strokeStyle);
    var color = a.color;
    var opacity = a.alpha * ctx.globalAlpha;
    var lineWidth = ctx.lineScale_ * ctx.lineWidth;

    // VML cannot correctly render a line if the width is less than 1px.
    // In that case, we dilute the color to make the line look thinner.
    if (lineWidth < 1) {
      opacity *= lineWidth;
    }

    lineStr.push(
      '<g_vml_:stroke',
      ' opacity="', opacity, '"',
      ' joinstyle="', ctx.lineJoin, '"',
      ' miterlimit="', ctx.miterLimit, '"',
      ' endcap="', processLineCap(ctx.lineCap), '"',
      ' weight="', lineWidth, 'px"',
      ' color="', color, '" />'
    );
  }

  function appendFill(ctx, lineStr, min, max) {
    var fillStyle = ctx.fillStyle;
    var arcScaleX = ctx.arcScaleX_;
    var arcScaleY = ctx.arcScaleY_;
    var width = max.x - min.x;
    var height = max.y - min.y;
    if (fillStyle instanceof CanvasGradient_) {
      // TODO: Gradients transformed with the transformation matrix.
      var angle = 0;
      var focus = {x: 0, y: 0};

      // additional offset
      var shift = 0;
      // scale factor for offset
      var expansion = 1;

      if (fillStyle.type_ == 'gradient') {
        var x0 = fillStyle.x0_ / arcScaleX;
        var y0 = fillStyle.y0_ / arcScaleY;
        var x1 = fillStyle.x1_ / arcScaleX;
        var y1 = fillStyle.y1_ / arcScaleY;
        var p0 = getCoords(ctx, x0, y0);
        var p1 = getCoords(ctx, x1, y1);
        var dx = p1.x - p0.x;
        var dy = p1.y - p0.y;
        angle = Math.atan2(dx, dy) * 180 / Math.PI;

        // The angle should be a non-negative number.
        if (angle < 0) {
          angle += 360;
        }

        // Very small angles produce an unexpected result because they are
        // converted to a scientific notation string.
        if (angle < 1e-6) {
          angle = 0;
        }
      } else {
        var p0 = getCoords(ctx, fillStyle.x0_, fillStyle.y0_);
        focus = {
          x: (p0.x - min.x) / width,
          y: (p0.y - min.y) / height
        };

        width  /= arcScaleX * Z;
        height /= arcScaleY * Z;
        var dimension = m.max(width, height);
        shift = 2 * fillStyle.r0_ / dimension;
        expansion = 2 * fillStyle.r1_ / dimension - shift;
      }

      // We need to sort the color stops in ascending order by offset,
      // otherwise IE won't interpret it correctly.
      var stops = fillStyle.colors_;
      stops.sort(function(cs1, cs2) {
        return cs1.offset - cs2.offset;
      });

      var length = stops.length;
      var color1 = stops[0].color;
      var color2 = stops[length - 1].color;
      var opacity1 = stops[0].alpha * ctx.globalAlpha;
      var opacity2 = stops[length - 1].alpha * ctx.globalAlpha;

      var colors = [];
      for (var i = 0; i < length; i++) {
        var stop = stops[i];
        colors.push(stop.offset * expansion + shift + ' ' + stop.color);
      }

      // When colors attribute is used, the meanings of opacity and o:opacity2
      // are reversed.
      lineStr.push('<g_vml_:fill type="', fillStyle.type_, '"',
                   ' method="none" focus="100%"',
                   ' color="', color1, '"',
                   ' color2="', color2, '"',
                   ' colors="', colors.join(','), '"',
                   ' opacity="', opacity2, '"',
                   ' g_o_:opacity2="', opacity1, '"',
                   ' angle="', angle, '"',
                   ' focusposition="', focus.x, ',', focus.y, '" />');
    } else if (fillStyle instanceof CanvasPattern_) {
      if (width && height) {
        var deltaLeft = -min.x;
        var deltaTop = -min.y;
        lineStr.push('<g_vml_:fill',
                     ' position="',
                     deltaLeft / width * arcScaleX * arcScaleX, ',',
                     deltaTop / height * arcScaleY * arcScaleY, '"',
                     ' type="tile"',
                     // TODO: Figure out the correct size to fit the scale.
                     //' size="', w, 'px ', h, 'px"',
                     ' src="', fillStyle.src_, '" />');
       }
    } else {
      var a = processStyle(ctx.fillStyle);
      var color = a.color;
      var opacity = a.alpha * ctx.globalAlpha;
      lineStr.push('<g_vml_:fill color="', color, '" opacity="', opacity,
                   '" />');
    }
  }

  contextPrototype.fill = function() {
    this.stroke(true);
  };

  contextPrototype.closePath = function() {
    this.currentPath_.push({type: 'close'});
  };

  function getCoords(ctx, aX, aY) {
    var m = ctx.m_;
    return {
      x: Z * (aX * m[0][0] + aY * m[1][0] + m[2][0]) - Z2,
      y: Z * (aX * m[0][1] + aY * m[1][1] + m[2][1]) - Z2
    };
  };

  contextPrototype.save = function() {
    var o = {};
    copyState(this, o);
    this.aStack_.push(o);
    this.mStack_.push(this.m_);
    this.m_ = matrixMultiply(createMatrixIdentity(), this.m_);
  };

  contextPrototype.restore = function() {
    if (this.aStack_.length) {
      copyState(this.aStack_.pop(), this);
      this.m_ = this.mStack_.pop();
    }
  };

  function matrixIsFinite(m) {
    return isFinite(m[0][0]) && isFinite(m[0][1]) &&
        isFinite(m[1][0]) && isFinite(m[1][1]) &&
        isFinite(m[2][0]) && isFinite(m[2][1]);
  }

  function setM(ctx, m, updateLineScale) {
    if (!matrixIsFinite(m)) {
      return;
    }
    ctx.m_ = m;

    if (updateLineScale) {
      // Get the line scale.
      // Determinant of this.m_ means how much the area is enlarged by the
      // transformation. So its square root can be used as a scale factor
      // for width.
      var det = m[0][0] * m[1][1] - m[0][1] * m[1][0];
      ctx.lineScale_ = sqrt(abs(det));
    }
  }

  contextPrototype.translate = function(aX, aY) {
    var m1 = [
      [1,  0,  0],
      [0,  1,  0],
      [aX, aY, 1]
    ];

    setM(this, matrixMultiply(m1, this.m_), false);
  };

  contextPrototype.rotate = function(aRot) {
    var c = mc(aRot);
    var s = ms(aRot);

    var m1 = [
      [c,  s, 0],
      [-s, c, 0],
      [0,  0, 1]
    ];

    setM(this, matrixMultiply(m1, this.m_), false);
  };

  contextPrototype.scale = function(aX, aY) {
    this.arcScaleX_ *= aX;
    this.arcScaleY_ *= aY;
    var m1 = [
      [aX, 0,  0],
      [0,  aY, 0],
      [0,  0,  1]
    ];

    setM(this, matrixMultiply(m1, this.m_), true);
  };

  contextPrototype.transform = function(m11, m12, m21, m22, dx, dy) {
    var m1 = [
      [m11, m12, 0],
      [m21, m22, 0],
      [dx,  dy,  1]
    ];

    setM(this, matrixMultiply(m1, this.m_), true);
  };

  contextPrototype.setTransform = function(m11, m12, m21, m22, dx, dy) {
    var m = [
      [m11, m12, 0],
      [m21, m22, 0],
      [dx,  dy,  1]
    ];

    setM(this, m, true);
  };

  /**
   * The text drawing function.
   * The maxWidth argument isn't taken in account, since no browser supports
   * it yet.
   */
  contextPrototype.drawText_ = function(text, x, y, maxWidth, stroke) {
    var m = this.m_,
        delta = 1000,
        left = 0,
        right = delta,
        offset = {x: 0, y: 0},
        lineStr = [];

    var fontStyle = getComputedStyle(processFontStyle(this.font),
                                     this.element_);

    var fontStyleString = buildStyle(fontStyle);

    var elementStyle = this.element_.currentStyle;
    var textAlign = this.textAlign.toLowerCase();
    switch (textAlign) {
      case 'left':
      case 'center':
      case 'right':
        break;
      case 'end':
        textAlign = elementStyle.direction == 'ltr' ? 'right' : 'left';
        break;
      case 'start':
        textAlign = elementStyle.direction == 'rtl' ? 'right' : 'left';
        break;
      default:
        textAlign = 'left';
    }

    // 1.75 is an arbitrary number, as there is no info about the text baseline
    switch (this.textBaseline) {
      case 'hanging':
      case 'top':
        offset.y = fontStyle.size / 1.75;
        break;
      case 'middle':
        break;
      default:
      case null:
      case 'alphabetic':
      case 'ideographic':
      case 'bottom':
        offset.y = -fontStyle.size / 2.25;
        break;
    }

    switch(textAlign) {
      case 'right':
        left = delta;
        right = 0.05;
        break;
      case 'center':
        left = right = delta / 2;
        break;
    }

    var d = getCoords(this, x + offset.x, y + offset.y);

    lineStr.push('<g_vml_:line from="', -left ,' 0" to="', right ,' 0.05" ',
                 ' coordsize="100 100" coordorigin="0 0"',
                 ' filled="', !stroke, '" stroked="', !!stroke,
                 '" style="position:absolute;width:1px;height:1px;">');

    if (stroke) {
      appendStroke(this, lineStr);
    } else {
      // TODO: Fix the min and max params.
      appendFill(this, lineStr, {x: -left, y: 0},
                 {x: right, y: fontStyle.size});
    }

    var skewM = m[0][0].toFixed(3) + ',' + m[1][0].toFixed(3) + ',' +
                m[0][1].toFixed(3) + ',' + m[1][1].toFixed(3) + ',0,0';

    var skewOffset = mr(d.x / Z) + ',' + mr(d.y / Z);

    lineStr.push('<g_vml_:skew on="t" matrix="', skewM ,'" ',
                 ' offset="', skewOffset, '" origin="', left ,' 0" />',
                 '<g_vml_:path textpathok="true" />',
                 '<g_vml_:textpath on="true" string="',
                 encodeHtmlAttribute(text),
                 '" style="v-text-align:', textAlign,
                 ';font:', encodeHtmlAttribute(fontStyleString),
                 '" /></g_vml_:line>');

    this.element_.insertAdjacentHTML('beforeEnd', lineStr.join(''));
  };

  contextPrototype.fillText = function(text, x, y, maxWidth) {
    this.drawText_(text, x, y, maxWidth, false);
  };

  contextPrototype.strokeText = function(text, x, y, maxWidth) {
    this.drawText_(text, x, y, maxWidth, true);
  };

  contextPrototype.measureText = function(text) {
    if (!this.textMeasureEl_) {
      var s = '<span style="position:absolute;' +
          'top:-20000px;left:0;padding:0;margin:0;border:none;' +
          'white-space:pre;"></span>';
      this.element_.insertAdjacentHTML('beforeEnd', s);
      this.textMeasureEl_ = this.element_.lastChild;
    }
    var doc = this.element_.ownerDocument;
    this.textMeasureEl_.innerHTML = '';
    this.textMeasureEl_.style.font = this.font;
    // Don't use innerHTML or innerText because they allow markup/whitespace.
    this.textMeasureEl_.appendChild(doc.createTextNode(text));
    return {width: this.textMeasureEl_.offsetWidth};
  };

  /******** STUBS ********/
  contextPrototype.clip = function() {
    // TODO: Implement
  };

  contextPrototype.arcTo = function() {
    // TODO: Implement
  };

  contextPrototype.createPattern = function(image, repetition) {
    return new CanvasPattern_(image, repetition);
  };

  // Gradient / Pattern Stubs
  function CanvasGradient_(aType) {
    this.type_ = aType;
    this.x0_ = 0;
    this.y0_ = 0;
    this.r0_ = 0;
    this.x1_ = 0;
    this.y1_ = 0;
    this.r1_ = 0;
    this.colors_ = [];
  }

  CanvasGradient_.prototype.addColorStop = function(aOffset, aColor) {
    aColor = processStyle(aColor);
    this.colors_.push({offset: aOffset,
                       color: aColor.color,
                       alpha: aColor.alpha});
  };

  function CanvasPattern_(image, repetition) {
    assertImageIsValid(image);
    switch (repetition) {
      case 'repeat':
      case null:
      case '':
        this.repetition_ = 'repeat';
        break
      case 'repeat-x':
      case 'repeat-y':
      case 'no-repeat':
        this.repetition_ = repetition;
        break;
      default:
        throwException('SYNTAX_ERR');
    }

    this.src_ = image.src;
    this.width_ = image.width;
    this.height_ = image.height;
  }

  function throwException(s) {
    throw new DOMException_(s);
  }

  function assertImageIsValid(img) {
    if (!img || img.nodeType != 1 || img.tagName != 'IMG') {
      throwException('TYPE_MISMATCH_ERR');
    }
    if (img.readyState != 'complete') {
      throwException('INVALID_STATE_ERR');
    }
  }

  function DOMException_(s) {
    this.code = this[s];
    this.message = s +': DOM Exception ' + this.code;
  }
  var p = DOMException_.prototype = new Error;
  p.INDEX_SIZE_ERR = 1;
  p.DOMSTRING_SIZE_ERR = 2;
  p.HIERARCHY_REQUEST_ERR = 3;
  p.WRONG_DOCUMENT_ERR = 4;
  p.INVALID_CHARACTER_ERR = 5;
  p.NO_DATA_ALLOWED_ERR = 6;
  p.NO_MODIFICATION_ALLOWED_ERR = 7;
  p.NOT_FOUND_ERR = 8;
  p.NOT_SUPPORTED_ERR = 9;
  p.INUSE_ATTRIBUTE_ERR = 10;
  p.INVALID_STATE_ERR = 11;
  p.SYNTAX_ERR = 12;
  p.INVALID_MODIFICATION_ERR = 13;
  p.NAMESPACE_ERR = 14;
  p.INVALID_ACCESS_ERR = 15;
  p.VALIDATION_ERR = 16;
  p.TYPE_MISMATCH_ERR = 17;

  // set up externs
  G_vmlCanvasManager = G_vmlCanvasManager_;
  CanvasRenderingContext2D = CanvasRenderingContext2D_;
  CanvasGradient = CanvasGradient_;
  CanvasPattern = CanvasPattern_;
  DOMException = DOMException_;
})();

} // if

//! moment.js
//! version : 2.10.6
//! authors : Tim Wood, Iskren Chernev, Moment.js contributors
//! license : MIT
//! momentjs.com
!function(a,b){"object"==typeof exports&&"undefined"!=typeof module?module.exports=b():"function"==typeof define&&define.amd?define(b):a.moment=b()}(this,function(){"use strict";function a(){return Hc.apply(null,arguments)}function b(a){Hc=a}function c(a){return"[object Array]"===Object.prototype.toString.call(a)}function d(a){return a instanceof Date||"[object Date]"===Object.prototype.toString.call(a)}function e(a,b){var c,d=[];for(c=0;c<a.length;++c)d.push(b(a[c],c));return d}function f(a,b){return Object.prototype.hasOwnProperty.call(a,b)}function g(a,b){for(var c in b)f(b,c)&&(a[c]=b[c]);return f(b,"toString")&&(a.toString=b.toString),f(b,"valueOf")&&(a.valueOf=b.valueOf),a}function h(a,b,c,d){return Ca(a,b,c,d,!0).utc()}function i(){return{empty:!1,unusedTokens:[],unusedInput:[],overflow:-2,charsLeftOver:0,nullInput:!1,invalidMonth:null,invalidFormat:!1,userInvalidated:!1,iso:!1}}function j(a){return null==a._pf&&(a._pf=i()),a._pf}function k(a){if(null==a._isValid){var b=j(a);a._isValid=!(isNaN(a._d.getTime())||!(b.overflow<0)||b.empty||b.invalidMonth||b.invalidWeekday||b.nullInput||b.invalidFormat||b.userInvalidated),a._strict&&(a._isValid=a._isValid&&0===b.charsLeftOver&&0===b.unusedTokens.length&&void 0===b.bigHour)}return a._isValid}function l(a){var b=h(NaN);return null!=a?g(j(b),a):j(b).userInvalidated=!0,b}function m(a,b){var c,d,e;if("undefined"!=typeof b._isAMomentObject&&(a._isAMomentObject=b._isAMomentObject),"undefined"!=typeof b._i&&(a._i=b._i),"undefined"!=typeof b._f&&(a._f=b._f),"undefined"!=typeof b._l&&(a._l=b._l),"undefined"!=typeof b._strict&&(a._strict=b._strict),"undefined"!=typeof b._tzm&&(a._tzm=b._tzm),"undefined"!=typeof b._isUTC&&(a._isUTC=b._isUTC),"undefined"!=typeof b._offset&&(a._offset=b._offset),"undefined"!=typeof b._pf&&(a._pf=j(b)),"undefined"!=typeof b._locale&&(a._locale=b._locale),Jc.length>0)for(c in Jc)d=Jc[c],e=b[d],"undefined"!=typeof e&&(a[d]=e);return a}function n(b){m(this,b),this._d=new Date(null!=b._d?b._d.getTime():NaN),Kc===!1&&(Kc=!0,a.updateOffset(this),Kc=!1)}function o(a){return a instanceof n||null!=a&&null!=a._isAMomentObject}function p(a){return 0>a?Math.ceil(a):Math.floor(a)}function q(a){var b=+a,c=0;return 0!==b&&isFinite(b)&&(c=p(b)),c}function r(a,b,c){var d,e=Math.min(a.length,b.length),f=Math.abs(a.length-b.length),g=0;for(d=0;e>d;d++)(c&&a[d]!==b[d]||!c&&q(a[d])!==q(b[d]))&&g++;return g+f}function s(){}function t(a){return a?a.toLowerCase().replace("_","-"):a}function u(a){for(var b,c,d,e,f=0;f<a.length;){for(e=t(a[f]).split("-"),b=e.length,c=t(a[f+1]),c=c?c.split("-"):null;b>0;){if(d=v(e.slice(0,b).join("-")))return d;if(c&&c.length>=b&&r(e,c,!0)>=b-1)break;b--}f++}return null}function v(a){var b=null;if(!Lc[a]&&"undefined"!=typeof module&&module&&module.exports)try{b=Ic._abbr,require("./locale/"+a),w(b)}catch(c){}return Lc[a]}function w(a,b){var c;return a&&(c="undefined"==typeof b?y(a):x(a,b),c&&(Ic=c)),Ic._abbr}function x(a,b){return null!==b?(b.abbr=a,Lc[a]=Lc[a]||new s,Lc[a].set(b),w(a),Lc[a]):(delete Lc[a],null)}function y(a){var b;if(a&&a._locale&&a._locale._abbr&&(a=a._locale._abbr),!a)return Ic;if(!c(a)){if(b=v(a))return b;a=[a]}return u(a)}function z(a,b){var c=a.toLowerCase();Mc[c]=Mc[c+"s"]=Mc[b]=a}function A(a){return"string"==typeof a?Mc[a]||Mc[a.toLowerCase()]:void 0}function B(a){var b,c,d={};for(c in a)f(a,c)&&(b=A(c),b&&(d[b]=a[c]));return d}function C(b,c){return function(d){return null!=d?(E(this,b,d),a.updateOffset(this,c),this):D(this,b)}}function D(a,b){return a._d["get"+(a._isUTC?"UTC":"")+b]()}function E(a,b,c){return a._d["set"+(a._isUTC?"UTC":"")+b](c)}function F(a,b){var c;if("object"==typeof a)for(c in a)this.set(c,a[c]);else if(a=A(a),"function"==typeof this[a])return this[a](b);return this}function G(a,b,c){var d=""+Math.abs(a),e=b-d.length,f=a>=0;return(f?c?"+":"":"-")+Math.pow(10,Math.max(0,e)).toString().substr(1)+d}function H(a,b,c,d){var e=d;"string"==typeof d&&(e=function(){return this[d]()}),a&&(Qc[a]=e),b&&(Qc[b[0]]=function(){return G(e.apply(this,arguments),b[1],b[2])}),c&&(Qc[c]=function(){return this.localeData().ordinal(e.apply(this,arguments),a)})}function I(a){return a.match(/\[[\s\S]/)?a.replace(/^\[|\]$/g,""):a.replace(/\\/g,"")}function J(a){var b,c,d=a.match(Nc);for(b=0,c=d.length;c>b;b++)Qc[d[b]]?d[b]=Qc[d[b]]:d[b]=I(d[b]);return function(e){var f="";for(b=0;c>b;b++)f+=d[b]instanceof Function?d[b].call(e,a):d[b];return f}}function K(a,b){return a.isValid()?(b=L(b,a.localeData()),Pc[b]=Pc[b]||J(b),Pc[b](a)):a.localeData().invalidDate()}function L(a,b){function c(a){return b.longDateFormat(a)||a}var d=5;for(Oc.lastIndex=0;d>=0&&Oc.test(a);)a=a.replace(Oc,c),Oc.lastIndex=0,d-=1;return a}function M(a){return"function"==typeof a&&"[object Function]"===Object.prototype.toString.call(a)}function N(a,b,c){dd[a]=M(b)?b:function(a){return a&&c?c:b}}function O(a,b){return f(dd,a)?dd[a](b._strict,b._locale):new RegExp(P(a))}function P(a){return a.replace("\\","").replace(/\\(\[)|\\(\])|\[([^\]\[]*)\]|\\(.)/g,function(a,b,c,d,e){return b||c||d||e}).replace(/[-\/\\^$*+?.()|[\]{}]/g,"\\$&")}function Q(a,b){var c,d=b;for("string"==typeof a&&(a=[a]),"number"==typeof b&&(d=function(a,c){c[b]=q(a)}),c=0;c<a.length;c++)ed[a[c]]=d}function R(a,b){Q(a,function(a,c,d,e){d._w=d._w||{},b(a,d._w,d,e)})}function S(a,b,c){null!=b&&f(ed,a)&&ed[a](b,c._a,c,a)}function T(a,b){return new Date(Date.UTC(a,b+1,0)).getUTCDate()}function U(a){return this._months[a.month()]}function V(a){return this._monthsShort[a.month()]}function W(a,b,c){var d,e,f;for(this._monthsParse||(this._monthsParse=[],this._longMonthsParse=[],this._shortMonthsParse=[]),d=0;12>d;d++){if(e=h([2e3,d]),c&&!this._longMonthsParse[d]&&(this._longMonthsParse[d]=new RegExp("^"+this.months(e,"").replace(".","")+"$","i"),this._shortMonthsParse[d]=new RegExp("^"+this.monthsShort(e,"").replace(".","")+"$","i")),c||this._monthsParse[d]||(f="^"+this.months(e,"")+"|^"+this.monthsShort(e,""),this._monthsParse[d]=new RegExp(f.replace(".",""),"i")),c&&"MMMM"===b&&this._longMonthsParse[d].test(a))return d;if(c&&"MMM"===b&&this._shortMonthsParse[d].test(a))return d;if(!c&&this._monthsParse[d].test(a))return d}}function X(a,b){var c;return"string"==typeof b&&(b=a.localeData().monthsParse(b),"number"!=typeof b)?a:(c=Math.min(a.date(),T(a.year(),b)),a._d["set"+(a._isUTC?"UTC":"")+"Month"](b,c),a)}function Y(b){return null!=b?(X(this,b),a.updateOffset(this,!0),this):D(this,"Month")}function Z(){return T(this.year(),this.month())}function $(a){var b,c=a._a;return c&&-2===j(a).overflow&&(b=c[gd]<0||c[gd]>11?gd:c[hd]<1||c[hd]>T(c[fd],c[gd])?hd:c[id]<0||c[id]>24||24===c[id]&&(0!==c[jd]||0!==c[kd]||0!==c[ld])?id:c[jd]<0||c[jd]>59?jd:c[kd]<0||c[kd]>59?kd:c[ld]<0||c[ld]>999?ld:-1,j(a)._overflowDayOfYear&&(fd>b||b>hd)&&(b=hd),j(a).overflow=b),a}function _(b){a.suppressDeprecationWarnings===!1&&"undefined"!=typeof console&&console.warn&&console.warn("Deprecation warning: "+b)}function aa(a,b){var c=!0;return g(function(){return c&&(_(a+"\n"+(new Error).stack),c=!1),b.apply(this,arguments)},b)}function ba(a,b){od[a]||(_(b),od[a]=!0)}function ca(a){var b,c,d=a._i,e=pd.exec(d);if(e){for(j(a).iso=!0,b=0,c=qd.length;c>b;b++)if(qd[b][1].exec(d)){a._f=qd[b][0];break}for(b=0,c=rd.length;c>b;b++)if(rd[b][1].exec(d)){a._f+=(e[6]||" ")+rd[b][0];break}d.match(ad)&&(a._f+="Z"),va(a)}else a._isValid=!1}function da(b){var c=sd.exec(b._i);return null!==c?void(b._d=new Date(+c[1])):(ca(b),void(b._isValid===!1&&(delete b._isValid,a.createFromInputFallback(b))))}function ea(a,b,c,d,e,f,g){var h=new Date(a,b,c,d,e,f,g);return 1970>a&&h.setFullYear(a),h}function fa(a){var b=new Date(Date.UTC.apply(null,arguments));return 1970>a&&b.setUTCFullYear(a),b}function ga(a){return ha(a)?366:365}function ha(a){return a%4===0&&a%100!==0||a%400===0}function ia(){return ha(this.year())}function ja(a,b,c){var d,e=c-b,f=c-a.day();return f>e&&(f-=7),e-7>f&&(f+=7),d=Da(a).add(f,"d"),{week:Math.ceil(d.dayOfYear()/7),year:d.year()}}function ka(a){return ja(a,this._week.dow,this._week.doy).week}function la(){return this._week.dow}function ma(){return this._week.doy}function na(a){var b=this.localeData().week(this);return null==a?b:this.add(7*(a-b),"d")}function oa(a){var b=ja(this,1,4).week;return null==a?b:this.add(7*(a-b),"d")}function pa(a,b,c,d,e){var f,g=6+e-d,h=fa(a,0,1+g),i=h.getUTCDay();return e>i&&(i+=7),c=null!=c?1*c:e,f=1+g+7*(b-1)-i+c,{year:f>0?a:a-1,dayOfYear:f>0?f:ga(a-1)+f}}function qa(a){var b=Math.round((this.clone().startOf("day")-this.clone().startOf("year"))/864e5)+1;return null==a?b:this.add(a-b,"d")}function ra(a,b,c){return null!=a?a:null!=b?b:c}function sa(a){var b=new Date;return a._useUTC?[b.getUTCFullYear(),b.getUTCMonth(),b.getUTCDate()]:[b.getFullYear(),b.getMonth(),b.getDate()]}function ta(a){var b,c,d,e,f=[];if(!a._d){for(d=sa(a),a._w&&null==a._a[hd]&&null==a._a[gd]&&ua(a),a._dayOfYear&&(e=ra(a._a[fd],d[fd]),a._dayOfYear>ga(e)&&(j(a)._overflowDayOfYear=!0),c=fa(e,0,a._dayOfYear),a._a[gd]=c.getUTCMonth(),a._a[hd]=c.getUTCDate()),b=0;3>b&&null==a._a[b];++b)a._a[b]=f[b]=d[b];for(;7>b;b++)a._a[b]=f[b]=null==a._a[b]?2===b?1:0:a._a[b];24===a._a[id]&&0===a._a[jd]&&0===a._a[kd]&&0===a._a[ld]&&(a._nextDay=!0,a._a[id]=0),a._d=(a._useUTC?fa:ea).apply(null,f),null!=a._tzm&&a._d.setUTCMinutes(a._d.getUTCMinutes()-a._tzm),a._nextDay&&(a._a[id]=24)}}function ua(a){var b,c,d,e,f,g,h;b=a._w,null!=b.GG||null!=b.W||null!=b.E?(f=1,g=4,c=ra(b.GG,a._a[fd],ja(Da(),1,4).year),d=ra(b.W,1),e=ra(b.E,1)):(f=a._locale._week.dow,g=a._locale._week.doy,c=ra(b.gg,a._a[fd],ja(Da(),f,g).year),d=ra(b.w,1),null!=b.d?(e=b.d,f>e&&++d):e=null!=b.e?b.e+f:f),h=pa(c,d,e,g,f),a._a[fd]=h.year,a._dayOfYear=h.dayOfYear}function va(b){if(b._f===a.ISO_8601)return void ca(b);b._a=[],j(b).empty=!0;var c,d,e,f,g,h=""+b._i,i=h.length,k=0;for(e=L(b._f,b._locale).match(Nc)||[],c=0;c<e.length;c++)f=e[c],d=(h.match(O(f,b))||[])[0],d&&(g=h.substr(0,h.indexOf(d)),g.length>0&&j(b).unusedInput.push(g),h=h.slice(h.indexOf(d)+d.length),k+=d.length),Qc[f]?(d?j(b).empty=!1:j(b).unusedTokens.push(f),S(f,d,b)):b._strict&&!d&&j(b).unusedTokens.push(f);j(b).charsLeftOver=i-k,h.length>0&&j(b).unusedInput.push(h),j(b).bigHour===!0&&b._a[id]<=12&&b._a[id]>0&&(j(b).bigHour=void 0),b._a[id]=wa(b._locale,b._a[id],b._meridiem),ta(b),$(b)}function wa(a,b,c){var d;return null==c?b:null!=a.meridiemHour?a.meridiemHour(b,c):null!=a.isPM?(d=a.isPM(c),d&&12>b&&(b+=12),d||12!==b||(b=0),b):b}function xa(a){var b,c,d,e,f;if(0===a._f.length)return j(a).invalidFormat=!0,void(a._d=new Date(NaN));for(e=0;e<a._f.length;e++)f=0,b=m({},a),null!=a._useUTC&&(b._useUTC=a._useUTC),b._f=a._f[e],va(b),k(b)&&(f+=j(b).charsLeftOver,f+=10*j(b).unusedTokens.length,j(b).score=f,(null==d||d>f)&&(d=f,c=b));g(a,c||b)}function ya(a){if(!a._d){var b=B(a._i);a._a=[b.year,b.month,b.day||b.date,b.hour,b.minute,b.second,b.millisecond],ta(a)}}function za(a){var b=new n($(Aa(a)));return b._nextDay&&(b.add(1,"d"),b._nextDay=void 0),b}function Aa(a){var b=a._i,e=a._f;return a._locale=a._locale||y(a._l),null===b||void 0===e&&""===b?l({nullInput:!0}):("string"==typeof b&&(a._i=b=a._locale.preparse(b)),o(b)?new n($(b)):(c(e)?xa(a):e?va(a):d(b)?a._d=b:Ba(a),a))}function Ba(b){var f=b._i;void 0===f?b._d=new Date:d(f)?b._d=new Date(+f):"string"==typeof f?da(b):c(f)?(b._a=e(f.slice(0),function(a){return parseInt(a,10)}),ta(b)):"object"==typeof f?ya(b):"number"==typeof f?b._d=new Date(f):a.createFromInputFallback(b)}function Ca(a,b,c,d,e){var f={};return"boolean"==typeof c&&(d=c,c=void 0),f._isAMomentObject=!0,f._useUTC=f._isUTC=e,f._l=c,f._i=a,f._f=b,f._strict=d,za(f)}function Da(a,b,c,d){return Ca(a,b,c,d,!1)}function Ea(a,b){var d,e;if(1===b.length&&c(b[0])&&(b=b[0]),!b.length)return Da();for(d=b[0],e=1;e<b.length;++e)(!b[e].isValid()||b[e][a](d))&&(d=b[e]);return d}function Fa(){var a=[].slice.call(arguments,0);return Ea("isBefore",a)}function Ga(){var a=[].slice.call(arguments,0);return Ea("isAfter",a)}function Ha(a){var b=B(a),c=b.year||0,d=b.quarter||0,e=b.month||0,f=b.week||0,g=b.day||0,h=b.hour||0,i=b.minute||0,j=b.second||0,k=b.millisecond||0;this._milliseconds=+k+1e3*j+6e4*i+36e5*h,this._days=+g+7*f,this._months=+e+3*d+12*c,this._data={},this._locale=y(),this._bubble()}function Ia(a){return a instanceof Ha}function Ja(a,b){H(a,0,0,function(){var a=this.utcOffset(),c="+";return 0>a&&(a=-a,c="-"),c+G(~~(a/60),2)+b+G(~~a%60,2)})}function Ka(a){var b=(a||"").match(ad)||[],c=b[b.length-1]||[],d=(c+"").match(xd)||["-",0,0],e=+(60*d[1])+q(d[2]);return"+"===d[0]?e:-e}function La(b,c){var e,f;return c._isUTC?(e=c.clone(),f=(o(b)||d(b)?+b:+Da(b))-+e,e._d.setTime(+e._d+f),a.updateOffset(e,!1),e):Da(b).local()}function Ma(a){return 15*-Math.round(a._d.getTimezoneOffset()/15)}function Na(b,c){var d,e=this._offset||0;return null!=b?("string"==typeof b&&(b=Ka(b)),Math.abs(b)<16&&(b=60*b),!this._isUTC&&c&&(d=Ma(this)),this._offset=b,this._isUTC=!0,null!=d&&this.add(d,"m"),e!==b&&(!c||this._changeInProgress?bb(this,Ya(b-e,"m"),1,!1):this._changeInProgress||(this._changeInProgress=!0,a.updateOffset(this,!0),this._changeInProgress=null)),this):this._isUTC?e:Ma(this)}function Oa(a,b){return null!=a?("string"!=typeof a&&(a=-a),this.utcOffset(a,b),this):-this.utcOffset()}function Pa(a){return this.utcOffset(0,a)}function Qa(a){return this._isUTC&&(this.utcOffset(0,a),this._isUTC=!1,a&&this.subtract(Ma(this),"m")),this}function Ra(){return this._tzm?this.utcOffset(this._tzm):"string"==typeof this._i&&this.utcOffset(Ka(this._i)),this}function Sa(a){return a=a?Da(a).utcOffset():0,(this.utcOffset()-a)%60===0}function Ta(){return this.utcOffset()>this.clone().month(0).utcOffset()||this.utcOffset()>this.clone().month(5).utcOffset()}function Ua(){if("undefined"!=typeof this._isDSTShifted)return this._isDSTShifted;var a={};if(m(a,this),a=Aa(a),a._a){var b=a._isUTC?h(a._a):Da(a._a);this._isDSTShifted=this.isValid()&&r(a._a,b.toArray())>0}else this._isDSTShifted=!1;return this._isDSTShifted}function Va(){return!this._isUTC}function Wa(){return this._isUTC}function Xa(){return this._isUTC&&0===this._offset}function Ya(a,b){var c,d,e,g=a,h=null;return Ia(a)?g={ms:a._milliseconds,d:a._days,M:a._months}:"number"==typeof a?(g={},b?g[b]=a:g.milliseconds=a):(h=yd.exec(a))?(c="-"===h[1]?-1:1,g={y:0,d:q(h[hd])*c,h:q(h[id])*c,m:q(h[jd])*c,s:q(h[kd])*c,ms:q(h[ld])*c}):(h=zd.exec(a))?(c="-"===h[1]?-1:1,g={y:Za(h[2],c),M:Za(h[3],c),d:Za(h[4],c),h:Za(h[5],c),m:Za(h[6],c),s:Za(h[7],c),w:Za(h[8],c)}):null==g?g={}:"object"==typeof g&&("from"in g||"to"in g)&&(e=_a(Da(g.from),Da(g.to)),g={},g.ms=e.milliseconds,g.M=e.months),d=new Ha(g),Ia(a)&&f(a,"_locale")&&(d._locale=a._locale),d}function Za(a,b){var c=a&&parseFloat(a.replace(",","."));return(isNaN(c)?0:c)*b}function $a(a,b){var c={milliseconds:0,months:0};return c.months=b.month()-a.month()+12*(b.year()-a.year()),a.clone().add(c.months,"M").isAfter(b)&&--c.months,c.milliseconds=+b-+a.clone().add(c.months,"M"),c}function _a(a,b){var c;return b=La(b,a),a.isBefore(b)?c=$a(a,b):(c=$a(b,a),c.milliseconds=-c.milliseconds,c.months=-c.months),c}function ab(a,b){return function(c,d){var e,f;return null===d||isNaN(+d)||(ba(b,"moment()."+b+"(period, number) is deprecated. Please use moment()."+b+"(number, period)."),f=c,c=d,d=f),c="string"==typeof c?+c:c,e=Ya(c,d),bb(this,e,a),this}}function bb(b,c,d,e){var f=c._milliseconds,g=c._days,h=c._months;e=null==e?!0:e,f&&b._d.setTime(+b._d+f*d),g&&E(b,"Date",D(b,"Date")+g*d),h&&X(b,D(b,"Month")+h*d),e&&a.updateOffset(b,g||h)}function cb(a,b){var c=a||Da(),d=La(c,this).startOf("day"),e=this.diff(d,"days",!0),f=-6>e?"sameElse":-1>e?"lastWeek":0>e?"lastDay":1>e?"sameDay":2>e?"nextDay":7>e?"nextWeek":"sameElse";return this.format(b&&b[f]||this.localeData().calendar(f,this,Da(c)))}function db(){return new n(this)}function eb(a,b){var c;return b=A("undefined"!=typeof b?b:"millisecond"),"millisecond"===b?(a=o(a)?a:Da(a),+this>+a):(c=o(a)?+a:+Da(a),c<+this.clone().startOf(b))}function fb(a,b){var c;return b=A("undefined"!=typeof b?b:"millisecond"),"millisecond"===b?(a=o(a)?a:Da(a),+a>+this):(c=o(a)?+a:+Da(a),+this.clone().endOf(b)<c)}function gb(a,b,c){return this.isAfter(a,c)&&this.isBefore(b,c)}function hb(a,b){var c;return b=A(b||"millisecond"),"millisecond"===b?(a=o(a)?a:Da(a),+this===+a):(c=+Da(a),+this.clone().startOf(b)<=c&&c<=+this.clone().endOf(b))}function ib(a,b,c){var d,e,f=La(a,this),g=6e4*(f.utcOffset()-this.utcOffset());return b=A(b),"year"===b||"month"===b||"quarter"===b?(e=jb(this,f),"quarter"===b?e/=3:"year"===b&&(e/=12)):(d=this-f,e="second"===b?d/1e3:"minute"===b?d/6e4:"hour"===b?d/36e5:"day"===b?(d-g)/864e5:"week"===b?(d-g)/6048e5:d),c?e:p(e)}function jb(a,b){var c,d,e=12*(b.year()-a.year())+(b.month()-a.month()),f=a.clone().add(e,"months");return 0>b-f?(c=a.clone().add(e-1,"months"),d=(b-f)/(f-c)):(c=a.clone().add(e+1,"months"),d=(b-f)/(c-f)),-(e+d)}function kb(){return this.clone().locale("en").format("ddd MMM DD YYYY HH:mm:ss [GMT]ZZ")}function lb(){var a=this.clone().utc();return 0<a.year()&&a.year()<=9999?"function"==typeof Date.prototype.toISOString?this.toDate().toISOString():K(a,"YYYY-MM-DD[T]HH:mm:ss.SSS[Z]"):K(a,"YYYYYY-MM-DD[T]HH:mm:ss.SSS[Z]")}function mb(b){var c=K(this,b||a.defaultFormat);return this.localeData().postformat(c)}function nb(a,b){return this.isValid()?Ya({to:this,from:a}).locale(this.locale()).humanize(!b):this.localeData().invalidDate()}function ob(a){return this.from(Da(),a)}function pb(a,b){return this.isValid()?Ya({from:this,to:a}).locale(this.locale()).humanize(!b):this.localeData().invalidDate()}function qb(a){return this.to(Da(),a)}function rb(a){var b;return void 0===a?this._locale._abbr:(b=y(a),null!=b&&(this._locale=b),this)}function sb(){return this._locale}function tb(a){switch(a=A(a)){case"year":this.month(0);case"quarter":case"month":this.date(1);case"week":case"isoWeek":case"day":this.hours(0);case"hour":this.minutes(0);case"minute":this.seconds(0);case"second":this.milliseconds(0)}return"week"===a&&this.weekday(0),"isoWeek"===a&&this.isoWeekday(1),"quarter"===a&&this.month(3*Math.floor(this.month()/3)),this}function ub(a){return a=A(a),void 0===a||"millisecond"===a?this:this.startOf(a).add(1,"isoWeek"===a?"week":a).subtract(1,"ms")}function vb(){return+this._d-6e4*(this._offset||0)}function wb(){return Math.floor(+this/1e3)}function xb(){return this._offset?new Date(+this):this._d}function yb(){var a=this;return[a.year(),a.month(),a.date(),a.hour(),a.minute(),a.second(),a.millisecond()]}function zb(){var a=this;return{years:a.year(),months:a.month(),date:a.date(),hours:a.hours(),minutes:a.minutes(),seconds:a.seconds(),milliseconds:a.milliseconds()}}function Ab(){return k(this)}function Bb(){return g({},j(this))}function Cb(){return j(this).overflow}function Db(a,b){H(0,[a,a.length],0,b)}function Eb(a,b,c){return ja(Da([a,11,31+b-c]),b,c).week}function Fb(a){var b=ja(this,this.localeData()._week.dow,this.localeData()._week.doy).year;return null==a?b:this.add(a-b,"y")}function Gb(a){var b=ja(this,1,4).year;return null==a?b:this.add(a-b,"y")}function Hb(){return Eb(this.year(),1,4)}function Ib(){var a=this.localeData()._week;return Eb(this.year(),a.dow,a.doy)}function Jb(a){return null==a?Math.ceil((this.month()+1)/3):this.month(3*(a-1)+this.month()%3)}function Kb(a,b){return"string"!=typeof a?a:isNaN(a)?(a=b.weekdaysParse(a),"number"==typeof a?a:null):parseInt(a,10)}function Lb(a){return this._weekdays[a.day()]}function Mb(a){return this._weekdaysShort[a.day()]}function Nb(a){return this._weekdaysMin[a.day()]}function Ob(a){var b,c,d;for(this._weekdaysParse=this._weekdaysParse||[],b=0;7>b;b++)if(this._weekdaysParse[b]||(c=Da([2e3,1]).day(b),d="^"+this.weekdays(c,"")+"|^"+this.weekdaysShort(c,"")+"|^"+this.weekdaysMin(c,""),this._weekdaysParse[b]=new RegExp(d.replace(".",""),"i")),this._weekdaysParse[b].test(a))return b}function Pb(a){var b=this._isUTC?this._d.getUTCDay():this._d.getDay();return null!=a?(a=Kb(a,this.localeData()),this.add(a-b,"d")):b}function Qb(a){var b=(this.day()+7-this.localeData()._week.dow)%7;return null==a?b:this.add(a-b,"d")}function Rb(a){return null==a?this.day()||7:this.day(this.day()%7?a:a-7)}function Sb(a,b){H(a,0,0,function(){return this.localeData().meridiem(this.hours(),this.minutes(),b)})}function Tb(a,b){return b._meridiemParse}function Ub(a){return"p"===(a+"").toLowerCase().charAt(0)}function Vb(a,b,c){return a>11?c?"pm":"PM":c?"am":"AM"}function Wb(a,b){b[ld]=q(1e3*("0."+a))}function Xb(){return this._isUTC?"UTC":""}function Yb(){return this._isUTC?"Coordinated Universal Time":""}function Zb(a){return Da(1e3*a)}function $b(){return Da.apply(null,arguments).parseZone()}function _b(a,b,c){var d=this._calendar[a];return"function"==typeof d?d.call(b,c):d}function ac(a){var b=this._longDateFormat[a],c=this._longDateFormat[a.toUpperCase()];return b||!c?b:(this._longDateFormat[a]=c.replace(/MMMM|MM|DD|dddd/g,function(a){return a.slice(1)}),this._longDateFormat[a])}function bc(){return this._invalidDate}function cc(a){return this._ordinal.replace("%d",a)}function dc(a){return a}function ec(a,b,c,d){var e=this._relativeTime[c];return"function"==typeof e?e(a,b,c,d):e.replace(/%d/i,a)}function fc(a,b){var c=this._relativeTime[a>0?"future":"past"];return"function"==typeof c?c(b):c.replace(/%s/i,b)}function gc(a){var b,c;for(c in a)b=a[c],"function"==typeof b?this[c]=b:this["_"+c]=b;this._ordinalParseLenient=new RegExp(this._ordinalParse.source+"|"+/\d{1,2}/.source)}function hc(a,b,c,d){var e=y(),f=h().set(d,b);return e[c](f,a)}function ic(a,b,c,d,e){if("number"==typeof a&&(b=a,a=void 0),a=a||"",null!=b)return hc(a,b,c,e);var f,g=[];for(f=0;d>f;f++)g[f]=hc(a,f,c,e);return g}function jc(a,b){return ic(a,b,"months",12,"month")}function kc(a,b){return ic(a,b,"monthsShort",12,"month")}function lc(a,b){return ic(a,b,"weekdays",7,"day")}function mc(a,b){return ic(a,b,"weekdaysShort",7,"day")}function nc(a,b){return ic(a,b,"weekdaysMin",7,"day")}function oc(){var a=this._data;return this._milliseconds=Wd(this._milliseconds),this._days=Wd(this._days),this._months=Wd(this._months),a.milliseconds=Wd(a.milliseconds),a.seconds=Wd(a.seconds),a.minutes=Wd(a.minutes),a.hours=Wd(a.hours),a.months=Wd(a.months),a.years=Wd(a.years),this}function pc(a,b,c,d){var e=Ya(b,c);return a._milliseconds+=d*e._milliseconds,a._days+=d*e._days,a._months+=d*e._months,a._bubble()}function qc(a,b){return pc(this,a,b,1)}function rc(a,b){return pc(this,a,b,-1)}function sc(a){return 0>a?Math.floor(a):Math.ceil(a)}function tc(){var a,b,c,d,e,f=this._milliseconds,g=this._days,h=this._months,i=this._data;return f>=0&&g>=0&&h>=0||0>=f&&0>=g&&0>=h||(f+=864e5*sc(vc(h)+g),g=0,h=0),i.milliseconds=f%1e3,a=p(f/1e3),i.seconds=a%60,b=p(a/60),i.minutes=b%60,c=p(b/60),i.hours=c%24,g+=p(c/24),e=p(uc(g)),h+=e,g-=sc(vc(e)),d=p(h/12),h%=12,i.days=g,i.months=h,i.years=d,this}function uc(a){return 4800*a/146097}function vc(a){return 146097*a/4800}function wc(a){var b,c,d=this._milliseconds;if(a=A(a),"month"===a||"year"===a)return b=this._days+d/864e5,c=this._months+uc(b),"month"===a?c:c/12;switch(b=this._days+Math.round(vc(this._months)),a){case"week":return b/7+d/6048e5;case"day":return b+d/864e5;case"hour":return 24*b+d/36e5;case"minute":return 1440*b+d/6e4;case"second":return 86400*b+d/1e3;case"millisecond":return Math.floor(864e5*b)+d;default:throw new Error("Unknown unit "+a)}}function xc(){return this._milliseconds+864e5*this._days+this._months%12*2592e6+31536e6*q(this._months/12)}function yc(a){return function(){return this.as(a)}}function zc(a){return a=A(a),this[a+"s"]()}function Ac(a){return function(){return this._data[a]}}function Bc(){return p(this.days()/7)}function Cc(a,b,c,d,e){return e.relativeTime(b||1,!!c,a,d)}function Dc(a,b,c){var d=Ya(a).abs(),e=ke(d.as("s")),f=ke(d.as("m")),g=ke(d.as("h")),h=ke(d.as("d")),i=ke(d.as("M")),j=ke(d.as("y")),k=e<le.s&&["s",e]||1===f&&["m"]||f<le.m&&["mm",f]||1===g&&["h"]||g<le.h&&["hh",g]||1===h&&["d"]||h<le.d&&["dd",h]||1===i&&["M"]||i<le.M&&["MM",i]||1===j&&["y"]||["yy",j];return k[2]=b,k[3]=+a>0,k[4]=c,Cc.apply(null,k)}function Ec(a,b){return void 0===le[a]?!1:void 0===b?le[a]:(le[a]=b,!0)}function Fc(a){var b=this.localeData(),c=Dc(this,!a,b);return a&&(c=b.pastFuture(+this,c)),b.postformat(c)}function Gc(){var a,b,c,d=me(this._milliseconds)/1e3,e=me(this._days),f=me(this._months);a=p(d/60),b=p(a/60),d%=60,a%=60,c=p(f/12),f%=12;var g=c,h=f,i=e,j=b,k=a,l=d,m=this.asSeconds();return m?(0>m?"-":"")+"P"+(g?g+"Y":"")+(h?h+"M":"")+(i?i+"D":"")+(j||k||l?"T":"")+(j?j+"H":"")+(k?k+"M":"")+(l?l+"S":""):"P0D"}var Hc,Ic,Jc=a.momentProperties=[],Kc=!1,Lc={},Mc={},Nc=/(\[[^\[]*\])|(\\)?(Mo|MM?M?M?|Do|DDDo|DD?D?D?|ddd?d?|do?|w[o|w]?|W[o|W]?|Q|YYYYYY|YYYYY|YYYY|YY|gg(ggg?)?|GG(GGG?)?|e|E|a|A|hh?|HH?|mm?|ss?|S{1,9}|x|X|zz?|ZZ?|.)/g,Oc=/(\[[^\[]*\])|(\\)?(LTS|LT|LL?L?L?|l{1,4})/g,Pc={},Qc={},Rc=/\d/,Sc=/\d\d/,Tc=/\d{3}/,Uc=/\d{4}/,Vc=/[+-]?\d{6}/,Wc=/\d\d?/,Xc=/\d{1,3}/,Yc=/\d{1,4}/,Zc=/[+-]?\d{1,6}/,$c=/\d+/,_c=/[+-]?\d+/,ad=/Z|[+-]\d\d:?\d\d/gi,bd=/[+-]?\d+(\.\d{1,3})?/,cd=/[0-9]*['a-z\u00A0-\u05FF\u0700-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]+|[\u0600-\u06FF\/]+(\s*?[\u0600-\u06FF]+){1,2}/i,dd={},ed={},fd=0,gd=1,hd=2,id=3,jd=4,kd=5,ld=6;H("M",["MM",2],"Mo",function(){return this.month()+1}),H("MMM",0,0,function(a){return this.localeData().monthsShort(this,a)}),H("MMMM",0,0,function(a){return this.localeData().months(this,a)}),z("month","M"),N("M",Wc),N("MM",Wc,Sc),N("MMM",cd),N("MMMM",cd),Q(["M","MM"],function(a,b){b[gd]=q(a)-1}),Q(["MMM","MMMM"],function(a,b,c,d){var e=c._locale.monthsParse(a,d,c._strict);null!=e?b[gd]=e:j(c).invalidMonth=a});var md="January_February_March_April_May_June_July_August_September_October_November_December".split("_"),nd="Jan_Feb_Mar_Apr_May_Jun_Jul_Aug_Sep_Oct_Nov_Dec".split("_"),od={};a.suppressDeprecationWarnings=!1;var pd=/^\s*(?:[+-]\d{6}|\d{4})-(?:(\d\d-\d\d)|(W\d\d$)|(W\d\d-\d)|(\d\d\d))((T| )(\d\d(:\d\d(:\d\d(\.\d+)?)?)?)?([\+\-]\d\d(?::?\d\d)?|\s*Z)?)?$/,qd=[["YYYYYY-MM-DD",/[+-]\d{6}-\d{2}-\d{2}/],["YYYY-MM-DD",/\d{4}-\d{2}-\d{2}/],["GGGG-[W]WW-E",/\d{4}-W\d{2}-\d/],["GGGG-[W]WW",/\d{4}-W\d{2}/],["YYYY-DDD",/\d{4}-\d{3}/]],rd=[["HH:mm:ss.SSSS",/(T| )\d\d:\d\d:\d\d\.\d+/],["HH:mm:ss",/(T| )\d\d:\d\d:\d\d/],["HH:mm",/(T| )\d\d:\d\d/],["HH",/(T| )\d\d/]],sd=/^\/?Date\((\-?\d+)/i;a.createFromInputFallback=aa("moment construction falls back to js Date. This is discouraged and will be removed in upcoming major release. Please refer to https://github.com/moment/moment/issues/1407 for more info.",function(a){a._d=new Date(a._i+(a._useUTC?" UTC":""))}),H(0,["YY",2],0,function(){return this.year()%100}),H(0,["YYYY",4],0,"year"),H(0,["YYYYY",5],0,"year"),H(0,["YYYYYY",6,!0],0,"year"),z("year","y"),N("Y",_c),N("YY",Wc,Sc),N("YYYY",Yc,Uc),N("YYYYY",Zc,Vc),N("YYYYYY",Zc,Vc),Q(["YYYYY","YYYYYY"],fd),Q("YYYY",function(b,c){c[fd]=2===b.length?a.parseTwoDigitYear(b):q(b)}),Q("YY",function(b,c){c[fd]=a.parseTwoDigitYear(b)}),a.parseTwoDigitYear=function(a){return q(a)+(q(a)>68?1900:2e3)};var td=C("FullYear",!1);H("w",["ww",2],"wo","week"),H("W",["WW",2],"Wo","isoWeek"),z("week","w"),z("isoWeek","W"),N("w",Wc),N("ww",Wc,Sc),N("W",Wc),N("WW",Wc,Sc),R(["w","ww","W","WW"],function(a,b,c,d){b[d.substr(0,1)]=q(a)});var ud={dow:0,doy:6};H("DDD",["DDDD",3],"DDDo","dayOfYear"),z("dayOfYear","DDD"),N("DDD",Xc),N("DDDD",Tc),Q(["DDD","DDDD"],function(a,b,c){c._dayOfYear=q(a)}),a.ISO_8601=function(){};var vd=aa("moment().min is deprecated, use moment.min instead. https://github.com/moment/moment/issues/1548",function(){var a=Da.apply(null,arguments);return this>a?this:a}),wd=aa("moment().max is deprecated, use moment.max instead. https://github.com/moment/moment/issues/1548",function(){var a=Da.apply(null,arguments);return a>this?this:a});Ja("Z",":"),Ja("ZZ",""),N("Z",ad),N("ZZ",ad),Q(["Z","ZZ"],function(a,b,c){c._useUTC=!0,c._tzm=Ka(a)});var xd=/([\+\-]|\d\d)/gi;a.updateOffset=function(){};var yd=/(\-)?(?:(\d*)\.)?(\d+)\:(\d+)(?:\:(\d+)\.?(\d{3})?)?/,zd=/^(-)?P(?:(?:([0-9,.]*)Y)?(?:([0-9,.]*)M)?(?:([0-9,.]*)D)?(?:T(?:([0-9,.]*)H)?(?:([0-9,.]*)M)?(?:([0-9,.]*)S)?)?|([0-9,.]*)W)$/;Ya.fn=Ha.prototype;var Ad=ab(1,"add"),Bd=ab(-1,"subtract");a.defaultFormat="YYYY-MM-DDTHH:mm:ssZ";var Cd=aa("moment().lang() is deprecated. Instead, use moment().localeData() to get the language configuration. Use moment().locale() to change languages.",function(a){return void 0===a?this.localeData():this.locale(a)});H(0,["gg",2],0,function(){return this.weekYear()%100}),H(0,["GG",2],0,function(){return this.isoWeekYear()%100}),Db("gggg","weekYear"),Db("ggggg","weekYear"),Db("GGGG","isoWeekYear"),Db("GGGGG","isoWeekYear"),z("weekYear","gg"),z("isoWeekYear","GG"),N("G",_c),N("g",_c),N("GG",Wc,Sc),N("gg",Wc,Sc),N("GGGG",Yc,Uc),N("gggg",Yc,Uc),N("GGGGG",Zc,Vc),N("ggggg",Zc,Vc),R(["gggg","ggggg","GGGG","GGGGG"],function(a,b,c,d){b[d.substr(0,2)]=q(a)}),R(["gg","GG"],function(b,c,d,e){c[e]=a.parseTwoDigitYear(b)}),H("Q",0,0,"quarter"),z("quarter","Q"),N("Q",Rc),Q("Q",function(a,b){b[gd]=3*(q(a)-1)}),H("D",["DD",2],"Do","date"),z("date","D"),N("D",Wc),N("DD",Wc,Sc),N("Do",function(a,b){return a?b._ordinalParse:b._ordinalParseLenient}),Q(["D","DD"],hd),Q("Do",function(a,b){b[hd]=q(a.match(Wc)[0],10)});var Dd=C("Date",!0);H("d",0,"do","day"),H("dd",0,0,function(a){return this.localeData().weekdaysMin(this,a)}),H("ddd",0,0,function(a){return this.localeData().weekdaysShort(this,a)}),H("dddd",0,0,function(a){return this.localeData().weekdays(this,a)}),H("e",0,0,"weekday"),H("E",0,0,"isoWeekday"),z("day","d"),z("weekday","e"),z("isoWeekday","E"),N("d",Wc),N("e",Wc),N("E",Wc),N("dd",cd),N("ddd",cd),N("dddd",cd),R(["dd","ddd","dddd"],function(a,b,c){var d=c._locale.weekdaysParse(a);null!=d?b.d=d:j(c).invalidWeekday=a}),R(["d","e","E"],function(a,b,c,d){b[d]=q(a)});var Ed="Sunday_Monday_Tuesday_Wednesday_Thursday_Friday_Saturday".split("_"),Fd="Sun_Mon_Tue_Wed_Thu_Fri_Sat".split("_"),Gd="Su_Mo_Tu_We_Th_Fr_Sa".split("_");H("H",["HH",2],0,"hour"),H("h",["hh",2],0,function(){return this.hours()%12||12}),Sb("a",!0),Sb("A",!1),z("hour","h"),N("a",Tb),N("A",Tb),N("H",Wc),N("h",Wc),N("HH",Wc,Sc),N("hh",Wc,Sc),Q(["H","HH"],id),Q(["a","A"],function(a,b,c){c._isPm=c._locale.isPM(a),c._meridiem=a}),Q(["h","hh"],function(a,b,c){b[id]=q(a),j(c).bigHour=!0});var Hd=/[ap]\.?m?\.?/i,Id=C("Hours",!0);H("m",["mm",2],0,"minute"),z("minute","m"),N("m",Wc),N("mm",Wc,Sc),Q(["m","mm"],jd);var Jd=C("Minutes",!1);H("s",["ss",2],0,"second"),z("second","s"),N("s",Wc),N("ss",Wc,Sc),Q(["s","ss"],kd);var Kd=C("Seconds",!1);H("S",0,0,function(){return~~(this.millisecond()/100)}),H(0,["SS",2],0,function(){return~~(this.millisecond()/10)}),H(0,["SSS",3],0,"millisecond"),H(0,["SSSS",4],0,function(){return 10*this.millisecond()}),H(0,["SSSSS",5],0,function(){return 100*this.millisecond()}),H(0,["SSSSSS",6],0,function(){return 1e3*this.millisecond()}),H(0,["SSSSSSS",7],0,function(){return 1e4*this.millisecond()}),H(0,["SSSSSSSS",8],0,function(){return 1e5*this.millisecond()}),H(0,["SSSSSSSSS",9],0,function(){return 1e6*this.millisecond()}),z("millisecond","ms"),N("S",Xc,Rc),N("SS",Xc,Sc),N("SSS",Xc,Tc);var Ld;for(Ld="SSSS";Ld.length<=9;Ld+="S")N(Ld,$c);for(Ld="S";Ld.length<=9;Ld+="S")Q(Ld,Wb);var Md=C("Milliseconds",!1);H("z",0,0,"zoneAbbr"),H("zz",0,0,"zoneName");var Nd=n.prototype;Nd.add=Ad,Nd.calendar=cb,Nd.clone=db,Nd.diff=ib,Nd.endOf=ub,Nd.format=mb,Nd.from=nb,Nd.fromNow=ob,Nd.to=pb,Nd.toNow=qb,Nd.get=F,Nd.invalidAt=Cb,Nd.isAfter=eb,Nd.isBefore=fb,Nd.isBetween=gb,Nd.isSame=hb,Nd.isValid=Ab,Nd.lang=Cd,Nd.locale=rb,Nd.localeData=sb,Nd.max=wd,Nd.min=vd,Nd.parsingFlags=Bb,Nd.set=F,Nd.startOf=tb,Nd.subtract=Bd,Nd.toArray=yb,Nd.toObject=zb,Nd.toDate=xb,Nd.toISOString=lb,Nd.toJSON=lb,Nd.toString=kb,Nd.unix=wb,Nd.valueOf=vb,Nd.year=td,Nd.isLeapYear=ia,Nd.weekYear=Fb,Nd.isoWeekYear=Gb,Nd.quarter=Nd.quarters=Jb,Nd.month=Y,Nd.daysInMonth=Z,Nd.week=Nd.weeks=na,Nd.isoWeek=Nd.isoWeeks=oa,Nd.weeksInYear=Ib,Nd.isoWeeksInYear=Hb,Nd.date=Dd,Nd.day=Nd.days=Pb,Nd.weekday=Qb,Nd.isoWeekday=Rb,Nd.dayOfYear=qa,Nd.hour=Nd.hours=Id,Nd.minute=Nd.minutes=Jd,Nd.second=Nd.seconds=Kd,
Nd.millisecond=Nd.milliseconds=Md,Nd.utcOffset=Na,Nd.utc=Pa,Nd.local=Qa,Nd.parseZone=Ra,Nd.hasAlignedHourOffset=Sa,Nd.isDST=Ta,Nd.isDSTShifted=Ua,Nd.isLocal=Va,Nd.isUtcOffset=Wa,Nd.isUtc=Xa,Nd.isUTC=Xa,Nd.zoneAbbr=Xb,Nd.zoneName=Yb,Nd.dates=aa("dates accessor is deprecated. Use date instead.",Dd),Nd.months=aa("months accessor is deprecated. Use month instead",Y),Nd.years=aa("years accessor is deprecated. Use year instead",td),Nd.zone=aa("moment().zone is deprecated, use moment().utcOffset instead. https://github.com/moment/moment/issues/1779",Oa);var Od=Nd,Pd={sameDay:"[Today at] LT",nextDay:"[Tomorrow at] LT",nextWeek:"dddd [at] LT",lastDay:"[Yesterday at] LT",lastWeek:"[Last] dddd [at] LT",sameElse:"L"},Qd={LTS:"h:mm:ss A",LT:"h:mm A",L:"MM/DD/YYYY",LL:"MMMM D, YYYY",LLL:"MMMM D, YYYY h:mm A",LLLL:"dddd, MMMM D, YYYY h:mm A"},Rd="Invalid date",Sd="%d",Td=/\d{1,2}/,Ud={future:"in %s",past:"%s ago",s:"a few seconds",m:"a minute",mm:"%d minutes",h:"an hour",hh:"%d hours",d:"a day",dd:"%d days",M:"a month",MM:"%d months",y:"a year",yy:"%d years"},Vd=s.prototype;Vd._calendar=Pd,Vd.calendar=_b,Vd._longDateFormat=Qd,Vd.longDateFormat=ac,Vd._invalidDate=Rd,Vd.invalidDate=bc,Vd._ordinal=Sd,Vd.ordinal=cc,Vd._ordinalParse=Td,Vd.preparse=dc,Vd.postformat=dc,Vd._relativeTime=Ud,Vd.relativeTime=ec,Vd.pastFuture=fc,Vd.set=gc,Vd.months=U,Vd._months=md,Vd.monthsShort=V,Vd._monthsShort=nd,Vd.monthsParse=W,Vd.week=ka,Vd._week=ud,Vd.firstDayOfYear=ma,Vd.firstDayOfWeek=la,Vd.weekdays=Lb,Vd._weekdays=Ed,Vd.weekdaysMin=Nb,Vd._weekdaysMin=Gd,Vd.weekdaysShort=Mb,Vd._weekdaysShort=Fd,Vd.weekdaysParse=Ob,Vd.isPM=Ub,Vd._meridiemParse=Hd,Vd.meridiem=Vb,w("en",{ordinalParse:/\d{1,2}(th|st|nd|rd)/,ordinal:function(a){var b=a%10,c=1===q(a%100/10)?"th":1===b?"st":2===b?"nd":3===b?"rd":"th";return a+c}}),a.lang=aa("moment.lang is deprecated. Use moment.locale instead.",w),a.langData=aa("moment.langData is deprecated. Use moment.localeData instead.",y);var Wd=Math.abs,Xd=yc("ms"),Yd=yc("s"),Zd=yc("m"),$d=yc("h"),_d=yc("d"),ae=yc("w"),be=yc("M"),ce=yc("y"),de=Ac("milliseconds"),ee=Ac("seconds"),fe=Ac("minutes"),ge=Ac("hours"),he=Ac("days"),ie=Ac("months"),je=Ac("years"),ke=Math.round,le={s:45,m:45,h:22,d:26,M:11},me=Math.abs,ne=Ha.prototype;ne.abs=oc,ne.add=qc,ne.subtract=rc,ne.as=wc,ne.asMilliseconds=Xd,ne.asSeconds=Yd,ne.asMinutes=Zd,ne.asHours=$d,ne.asDays=_d,ne.asWeeks=ae,ne.asMonths=be,ne.asYears=ce,ne.valueOf=xc,ne._bubble=tc,ne.get=zc,ne.milliseconds=de,ne.seconds=ee,ne.minutes=fe,ne.hours=ge,ne.days=he,ne.weeks=Bc,ne.months=ie,ne.years=je,ne.humanize=Fc,ne.toISOString=Gc,ne.toString=Gc,ne.toJSON=Gc,ne.locale=rb,ne.localeData=sb,ne.toIsoString=aa("toIsoString() is deprecated. Please use toISOString() instead (notice the capitals)",Gc),ne.lang=Cd,H("X",0,0,"unix"),H("x",0,0,"valueOf"),N("x",_c),N("X",bd),Q("X",function(a,b,c){c._d=new Date(1e3*parseFloat(a,10))}),Q("x",function(a,b,c){c._d=new Date(q(a))}),a.version="2.10.6",b(Da),a.fn=Od,a.min=Fa,a.max=Ga,a.utc=h,a.unix=Zb,a.months=jc,a.isDate=d,a.locale=w,a.invalid=l,a.duration=Ya,a.isMoment=o,a.weekdays=lc,a.parseZone=$b,a.localeData=y,a.isDuration=Ia,a.monthsShort=kc,a.weekdaysMin=nc,a.defineLocale=x,a.weekdaysShort=mc,a.normalizeUnits=A,a.relativeTimeThreshold=Ec;var oe=a;return oe});

/* ========================================================================
 * Bootstrap: transition.js v3.3.5
 * http://getbootstrap.com/javascript/#transitions
 * ========================================================================
 * Copyright 2011-2015 Twitter, Inc.
 * Licensed under MIT (https://github.com/twbs/bootstrap/blob/master/LICENSE)
 * ======================================================================== */


+function ($) {
  'use strict';

  // CSS TRANSITION SUPPORT (Shoutout: http://www.modernizr.com/)
  // ============================================================

  function transitionEnd() {
    var el = document.createElement('bootstrap')

    var transEndEventNames = {
      WebkitTransition : 'webkitTransitionEnd',
      MozTransition    : 'transitionend',
      OTransition      : 'oTransitionEnd otransitionend',
      transition       : 'transitionend'
    }

    for (var name in transEndEventNames) {
      if (el.style[name] !== undefined) {
        return { end: transEndEventNames[name] }
      }
    }

    return false // explicit for ie8 (  ._.)
  }

  // http://blog.alexmaccaw.com/css-transitions
  $.fn.emulateTransitionEnd = function (duration) {
    var called = false
    var $el = this
    $(this).one('bsTransitionEnd', function () { called = true })
    var callback = function () { if (!called) $($el).trigger($.support.transition.end) }
    setTimeout(callback, duration)
    return this
  }

  $(function () {
    $.support.transition = transitionEnd()

    if (!$.support.transition) return

    $.event.special.bsTransitionEnd = {
      bindType: $.support.transition.end,
      delegateType: $.support.transition.end,
      handle: function (e) {
        if ($(e.target).is(this)) return e.handleObj.handler.apply(this, arguments)
      }
    }
  })

}(jQuery);

/* ========================================================================
 * Bootstrap: alert.js v3.3.5
 * http://getbootstrap.com/javascript/#alerts
 * ========================================================================
 * Copyright 2011-2015 Twitter, Inc.
 * Licensed under MIT (https://github.com/twbs/bootstrap/blob/master/LICENSE)
 * ======================================================================== */


+function ($) {
  'use strict';

  // ALERT CLASS DEFINITION
  // ======================

  var dismiss = '[data-dismiss="alert"]'
  var Alert   = function (el) {
    $(el).on('click', dismiss, this.close)
  }

  Alert.VERSION = '3.3.5'

  Alert.TRANSITION_DURATION = 150

  Alert.prototype.close = function (e) {
    var $this    = $(this)
    var selector = $this.attr('data-target')

    if (!selector) {
      selector = $this.attr('href')
      selector = selector && selector.replace(/.*(?=#[^\s]*$)/, '') // strip for ie7
    }

    var $parent = $(selector)

    if (e) e.preventDefault()

    if (!$parent.length) {
      $parent = $this.closest('.alert')
    }

    $parent.trigger(e = $.Event('close.bs.alert'))

    if (e.isDefaultPrevented()) return

    $parent.removeClass('in')

    function removeElement() {
      // detach from parent, fire event then clean up data
      $parent.detach().trigger('closed.bs.alert').remove()
    }

    $.support.transition && $parent.hasClass('fade') ?
      $parent
        .one('bsTransitionEnd', removeElement)
        .emulateTransitionEnd(Alert.TRANSITION_DURATION) :
      removeElement()
  }


  // ALERT PLUGIN DEFINITION
  // =======================

  function Plugin(option) {
    return this.each(function () {
      var $this = $(this)
      var data  = $this.data('bs.alert')

      if (!data) $this.data('bs.alert', (data = new Alert(this)))
      if (typeof option == 'string') data[option].call($this)
    })
  }

  var old = $.fn.alert

  $.fn.alert             = Plugin
  $.fn.alert.Constructor = Alert


  // ALERT NO CONFLICT
  // =================

  $.fn.alert.noConflict = function () {
    $.fn.alert = old
    return this
  }


  // ALERT DATA-API
  // ==============

  $(document).on('click.bs.alert.data-api', dismiss, Alert.prototype.close)

}(jQuery);

/* ========================================================================
 * Bootstrap: button.js v3.3.5
 * http://getbootstrap.com/javascript/#buttons
 * ========================================================================
 * Copyright 2011-2015 Twitter, Inc.
 * Licensed under MIT (https://github.com/twbs/bootstrap/blob/master/LICENSE)
 * ======================================================================== */


+function ($) {
  'use strict';

  // BUTTON PUBLIC CLASS DEFINITION
  // ==============================

  var Button = function (element, options) {
    this.$element  = $(element)
    this.options   = $.extend({}, Button.DEFAULTS, options)
    this.isLoading = false
  }

  Button.VERSION  = '3.3.5'

  Button.DEFAULTS = {
    loadingText: 'loading...'
  }

  Button.prototype.setState = function (state) {
    var d    = 'disabled'
    var $el  = this.$element
    var val  = $el.is('input') ? 'val' : 'html'
    var data = $el.data()

    state += 'Text'

    if (data.resetText == null) $el.data('resetText', $el[val]())

    // push to event loop to allow forms to submit
    setTimeout($.proxy(function () {
      $el[val](data[state] == null ? this.options[state] : data[state])

      if (state == 'loadingText') {
        this.isLoading = true
        $el.addClass(d).attr(d, d)
      } else if (this.isLoading) {
        this.isLoading = false
        $el.removeClass(d).removeAttr(d)
      }
    }, this), 0)
  }

  Button.prototype.toggle = function () {
    var changed = true
    var $parent = this.$element.closest('[data-toggle="buttons"]')

    if ($parent.length) {
      var $input = this.$element.find('input')
      if ($input.prop('type') == 'radio') {
        if ($input.prop('checked')) changed = false
        $parent.find('.active').removeClass('active')
        this.$element.addClass('active')
      } else if ($input.prop('type') == 'checkbox') {
        if (($input.prop('checked')) !== this.$element.hasClass('active')) changed = false
        this.$element.toggleClass('active')
      }
      $input.prop('checked', this.$element.hasClass('active'))
      if (changed) $input.trigger('change')
    } else {
      this.$element.attr('aria-pressed', !this.$element.hasClass('active'))
      this.$element.toggleClass('active')
    }
  }


  // BUTTON PLUGIN DEFINITION
  // ========================

  function Plugin(option) {
    return this.each(function () {
      var $this   = $(this)
      var data    = $this.data('bs.button')
      var options = typeof option == 'object' && option

      if (!data) $this.data('bs.button', (data = new Button(this, options)))

      if (option == 'toggle') data.toggle()
      else if (option) data.setState(option)
    })
  }

  var old = $.fn.button

  $.fn.button             = Plugin
  $.fn.button.Constructor = Button


  // BUTTON NO CONFLICT
  // ==================

  $.fn.button.noConflict = function () {
    $.fn.button = old
    return this
  }


  // BUTTON DATA-API
  // ===============

  $(document)
    .on('click.bs.button.data-api', '[data-toggle^="button"]', function (e) {
      var $btn = $(e.target)
      if (!$btn.hasClass('btn')) $btn = $btn.closest('.btn')
      Plugin.call($btn, 'toggle')
      if (!($(e.target).is('input[type="radio"]') || $(e.target).is('input[type="checkbox"]'))) e.preventDefault()
    })
    .on('focus.bs.button.data-api blur.bs.button.data-api', '[data-toggle^="button"]', function (e) {
      $(e.target).closest('.btn').toggleClass('focus', /^focus(in)?$/.test(e.type))
    })

}(jQuery);

/* ========================================================================
 * Bootstrap: carousel.js v3.3.5
 * http://getbootstrap.com/javascript/#carousel
 * ========================================================================
 * Copyright 2011-2015 Twitter, Inc.
 * Licensed under MIT (https://github.com/twbs/bootstrap/blob/master/LICENSE)
 * ======================================================================== */


+function ($) {
  'use strict';

  // CAROUSEL CLASS DEFINITION
  // =========================

  var Carousel = function (element, options) {
    this.$element    = $(element)
    this.$indicators = this.$element.find('.carousel-indicators')
    this.options     = options
    this.paused      = null
    this.sliding     = null
    this.interval    = null
    this.$active     = null
    this.$items      = null

    this.options.keyboard && this.$element.on('keydown.bs.carousel', $.proxy(this.keydown, this))

    this.options.pause == 'hover' && !('ontouchstart' in document.documentElement) && this.$element
      .on('mouseenter.bs.carousel', $.proxy(this.pause, this))
      .on('mouseleave.bs.carousel', $.proxy(this.cycle, this))
  }

  Carousel.VERSION  = '3.3.5'

  Carousel.TRANSITION_DURATION = 600

  Carousel.DEFAULTS = {
    interval: 5000,
    pause: 'hover',
    wrap: true,
    keyboard: true
  }

  Carousel.prototype.keydown = function (e) {
    if (/input|textarea/i.test(e.target.tagName)) return
    switch (e.which) {
      case 37: this.prev(); break
      case 39: this.next(); break
      default: return
    }

    e.preventDefault()
  }

  Carousel.prototype.cycle = function (e) {
    e || (this.paused = false)

    this.interval && clearInterval(this.interval)

    this.options.interval
      && !this.paused
      && (this.interval = setInterval($.proxy(this.next, this), this.options.interval))

    return this
  }

  Carousel.prototype.getItemIndex = function (item) {
    this.$items = item.parent().children('.item')
    return this.$items.index(item || this.$active)
  }

  Carousel.prototype.getItemForDirection = function (direction, active) {
    var activeIndex = this.getItemIndex(active)
    var willWrap = (direction == 'prev' && activeIndex === 0)
                || (direction == 'next' && activeIndex == (this.$items.length - 1))
    if (willWrap && !this.options.wrap) return active
    var delta = direction == 'prev' ? -1 : 1
    var itemIndex = (activeIndex + delta) % this.$items.length
    return this.$items.eq(itemIndex)
  }

  Carousel.prototype.to = function (pos) {
    var that        = this
    var activeIndex = this.getItemIndex(this.$active = this.$element.find('.item.active'))

    if (pos > (this.$items.length - 1) || pos < 0) return

    if (this.sliding)       return this.$element.one('slid.bs.carousel', function () { that.to(pos) }) // yes, "slid"
    if (activeIndex == pos) return this.pause().cycle()

    return this.slide(pos > activeIndex ? 'next' : 'prev', this.$items.eq(pos))
  }

  Carousel.prototype.pause = function (e) {
    e || (this.paused = true)

    if (this.$element.find('.next, .prev').length && $.support.transition) {
      this.$element.trigger($.support.transition.end)
      this.cycle(true)
    }

    this.interval = clearInterval(this.interval)

    return this
  }

  Carousel.prototype.next = function () {
    if (this.sliding) return
    return this.slide('next')
  }

  Carousel.prototype.prev = function () {
    if (this.sliding) return
    return this.slide('prev')
  }

  Carousel.prototype.slide = function (type, next) {
    var $active   = this.$element.find('.item.active')
    var $next     = next || this.getItemForDirection(type, $active)
    var isCycling = this.interval
    var direction = type == 'next' ? 'left' : 'right'
    var that      = this

    if ($next.hasClass('active')) return (this.sliding = false)

    var relatedTarget = $next[0]
    var slideEvent = $.Event('slide.bs.carousel', {
      relatedTarget: relatedTarget,
      direction: direction
    })
    this.$element.trigger(slideEvent)
    if (slideEvent.isDefaultPrevented()) return

    this.sliding = true

    isCycling && this.pause()

    if (this.$indicators.length) {
      this.$indicators.find('.active').removeClass('active')
      var $nextIndicator = $(this.$indicators.children()[this.getItemIndex($next)])
      $nextIndicator && $nextIndicator.addClass('active')
    }

    var slidEvent = $.Event('slid.bs.carousel', { relatedTarget: relatedTarget, direction: direction }) // yes, "slid"
    if ($.support.transition && this.$element.hasClass('slide')) {
      $next.addClass(type)
      $next[0].offsetWidth // force reflow
      $active.addClass(direction)
      $next.addClass(direction)
      $active
        .one('bsTransitionEnd', function () {
          $next.removeClass([type, direction].join(' ')).addClass('active')
          $active.removeClass(['active', direction].join(' '))
          that.sliding = false
          setTimeout(function () {
            that.$element.trigger(slidEvent)
          }, 0)
        })
        .emulateTransitionEnd(Carousel.TRANSITION_DURATION)
    } else {
      $active.removeClass('active')
      $next.addClass('active')
      this.sliding = false
      this.$element.trigger(slidEvent)
    }

    isCycling && this.cycle()

    return this
  }


  // CAROUSEL PLUGIN DEFINITION
  // ==========================

  function Plugin(option) {
    return this.each(function () {
      var $this   = $(this)
      var data    = $this.data('bs.carousel')
      var options = $.extend({}, Carousel.DEFAULTS, $this.data(), typeof option == 'object' && option)
      var action  = typeof option == 'string' ? option : options.slide

      if (!data) $this.data('bs.carousel', (data = new Carousel(this, options)))
      if (typeof option == 'number') data.to(option)
      else if (action) data[action]()
      else if (options.interval) data.pause().cycle()
    })
  }

  var old = $.fn.carousel

  $.fn.carousel             = Plugin
  $.fn.carousel.Constructor = Carousel


  // CAROUSEL NO CONFLICT
  // ====================

  $.fn.carousel.noConflict = function () {
    $.fn.carousel = old
    return this
  }


  // CAROUSEL DATA-API
  // =================

  var clickHandler = function (e) {
    var href
    var $this   = $(this)
    var $target = $($this.attr('data-target') || (href = $this.attr('href')) && href.replace(/.*(?=#[^\s]+$)/, '')) // strip for ie7
    if (!$target.hasClass('carousel')) return
    var options = $.extend({}, $target.data(), $this.data())
    var slideIndex = $this.attr('data-slide-to')
    if (slideIndex) options.interval = false

    Plugin.call($target, options)

    if (slideIndex) {
      $target.data('bs.carousel').to(slideIndex)
    }

    e.preventDefault()
  }

  $(document)
    .on('click.bs.carousel.data-api', '[data-slide]', clickHandler)
    .on('click.bs.carousel.data-api', '[data-slide-to]', clickHandler)

  $(window).on('load', function () {
    $('[data-ride="carousel"]').each(function () {
      var $carousel = $(this)
      Plugin.call($carousel, $carousel.data())
    })
  })

}(jQuery);

/* ========================================================================
 * Bootstrap: collapse.js v3.3.5
 * http://getbootstrap.com/javascript/#collapse
 * ========================================================================
 * Copyright 2011-2015 Twitter, Inc.
 * Licensed under MIT (https://github.com/twbs/bootstrap/blob/master/LICENSE)
 * ======================================================================== */


+function ($) {
  'use strict';

  // COLLAPSE PUBLIC CLASS DEFINITION
  // ================================

  var Collapse = function (element, options) {
    this.$element      = $(element)
    this.options       = $.extend({}, Collapse.DEFAULTS, options)
    this.$trigger      = $('[data-toggle="collapse"][href="#' + element.id + '"],' +
                           '[data-toggle="collapse"][data-target="#' + element.id + '"]')
    this.transitioning = null

    if (this.options.parent) {
      this.$parent = this.getParent()
    } else {
      this.addAriaAndCollapsedClass(this.$element, this.$trigger)
    }

    if (this.options.toggle) this.toggle()
  }

  Collapse.VERSION  = '3.3.5'

  Collapse.TRANSITION_DURATION = 350

  Collapse.DEFAULTS = {
    toggle: true
  }

  Collapse.prototype.dimension = function () {
    var hasWidth = this.$element.hasClass('width')
    return hasWidth ? 'width' : 'height'
  }

  Collapse.prototype.show = function () {
    if (this.transitioning || this.$element.hasClass('in')) return

    var activesData
    var actives = this.$parent && this.$parent.children('.panel').children('.in, .collapsing')

    if (actives && actives.length) {
      activesData = actives.data('bs.collapse')
      if (activesData && activesData.transitioning) return
    }

    var startEvent = $.Event('show.bs.collapse')
    this.$element.trigger(startEvent)
    if (startEvent.isDefaultPrevented()) return

    if (actives && actives.length) {
      Plugin.call(actives, 'hide')
      activesData || actives.data('bs.collapse', null)
    }

    var dimension = this.dimension()

    this.$element
      .removeClass('collapse')
      .addClass('collapsing')[dimension](0)
      .attr('aria-expanded', true)

    this.$trigger
      .removeClass('collapsed')
      .attr('aria-expanded', true)

    this.transitioning = 1

    var complete = function () {
      this.$element
        .removeClass('collapsing')
        .addClass('collapse in')[dimension]('')
      this.transitioning = 0
      this.$element
        .trigger('shown.bs.collapse')
    }

    if (!$.support.transition) return complete.call(this)

    var scrollSize = $.camelCase(['scroll', dimension].join('-'))

    this.$element
      .one('bsTransitionEnd', $.proxy(complete, this))
      .emulateTransitionEnd(Collapse.TRANSITION_DURATION)[dimension](this.$element[0][scrollSize])
  }

  Collapse.prototype.hide = function () {
    if (this.transitioning || !this.$element.hasClass('in')) return

    var startEvent = $.Event('hide.bs.collapse')
    this.$element.trigger(startEvent)
    if (startEvent.isDefaultPrevented()) return

    var dimension = this.dimension()

    this.$element[dimension](this.$element[dimension]())[0].offsetHeight

    this.$element
      .addClass('collapsing')
      .removeClass('collapse in')
      .attr('aria-expanded', false)

    this.$trigger
      .addClass('collapsed')
      .attr('aria-expanded', false)

    this.transitioning = 1

    var complete = function () {
      this.transitioning = 0
      this.$element
        .removeClass('collapsing')
        .addClass('collapse')
        .trigger('hidden.bs.collapse')
    }

    if (!$.support.transition) return complete.call(this)

    this.$element
      [dimension](0)
      .one('bsTransitionEnd', $.proxy(complete, this))
      .emulateTransitionEnd(Collapse.TRANSITION_DURATION)
  }

  Collapse.prototype.toggle = function () {
    this[this.$element.hasClass('in') ? 'hide' : 'show']()
  }

  Collapse.prototype.getParent = function () {
    return $(this.options.parent)
      .find('[data-toggle="collapse"][data-parent="' + this.options.parent + '"]')
      .each($.proxy(function (i, element) {
        var $element = $(element)
        this.addAriaAndCollapsedClass(getTargetFromTrigger($element), $element)
      }, this))
      .end()
  }

  Collapse.prototype.addAriaAndCollapsedClass = function ($element, $trigger) {
    var isOpen = $element.hasClass('in')

    $element.attr('aria-expanded', isOpen)
    $trigger
      .toggleClass('collapsed', !isOpen)
      .attr('aria-expanded', isOpen)
  }

  function getTargetFromTrigger($trigger) {
    var href
    var target = $trigger.attr('data-target')
      || (href = $trigger.attr('href')) && href.replace(/.*(?=#[^\s]+$)/, '') // strip for ie7

    return $(target)
  }


  // COLLAPSE PLUGIN DEFINITION
  // ==========================

  function Plugin(option) {
    return this.each(function () {
      var $this   = $(this)
      var data    = $this.data('bs.collapse')
      var options = $.extend({}, Collapse.DEFAULTS, $this.data(), typeof option == 'object' && option)

      if (!data && options.toggle && /show|hide/.test(option)) options.toggle = false
      if (!data) $this.data('bs.collapse', (data = new Collapse(this, options)))
      if (typeof option == 'string') data[option]()
    })
  }

  var old = $.fn.collapse

  $.fn.collapse             = Plugin
  $.fn.collapse.Constructor = Collapse


  // COLLAPSE NO CONFLICT
  // ====================

  $.fn.collapse.noConflict = function () {
    $.fn.collapse = old
    return this
  }


  // COLLAPSE DATA-API
  // =================

  $(document).on('click.bs.collapse.data-api', '[data-toggle="collapse"]', function (e) {
    var $this   = $(this)

    if (!$this.attr('data-target')) e.preventDefault()

    var $target = getTargetFromTrigger($this)
    var data    = $target.data('bs.collapse')
    var option  = data ? 'toggle' : $this.data()

    Plugin.call($target, option)
  })

}(jQuery);

/* ========================================================================
 * Bootstrap: dropdown.js v3.3.5
 * http://getbootstrap.com/javascript/#dropdowns
 * ========================================================================
 * Copyright 2011-2015 Twitter, Inc.
 * Licensed under MIT (https://github.com/twbs/bootstrap/blob/master/LICENSE)
 * ======================================================================== */


/*+function ($) {
  'use strict';

  // DROPDOWN CLASS DEFINITION
  // =========================

  var backdrop = '.dropdown-backdrop'
  var toggle   = '[data-toggle="dropdown"]'
  var Dropdown = function (element) {
    $(element).on('click.bs.dropdown', this.toggle)
  }

  Dropdown.VERSION = '3.3.5'

  function getParent($this) {
    var selector = $this.attr('data-target')

    if (!selector) {
      selector = $this.attr('href')
      selector = selector && /#[A-Za-z]/.test(selector) && selector.replace(/.*(?=#[^\s]*$)/, '') // strip for ie7
    }

    var $parent = selector && $(selector)

    return $parent && $parent.length ? $parent : $this.parent()
  }

  function clearMenus(e) {
    if (e && e.which === 3) return
    $(backdrop).remove()
    $(toggle).each(function () {
      var $this         = $(this)
      var $parent       = getParent($this)
      var relatedTarget = { relatedTarget: this }

      if (!$parent.hasClass('open')) return

      if (e && e.type == 'click' && /input|textarea/i.test(e.target.tagName) && $.contains($parent[0], e.target)) return

      $parent.trigger(e = $.Event('hide.bs.dropdown', relatedTarget))

      if (e.isDefaultPrevented()) return

      $this.attr('aria-expanded', 'false')
      $parent.removeClass('open').trigger('hidden.bs.dropdown', relatedTarget)
    })
  }

  Dropdown.prototype.toggle = function (e) {
    var $this = $(this)

    if ($this.is('.disabled, :disabled')) return

    var $parent  = getParent($this)
    var isActive = $parent.hasClass('open')

    clearMenus()

    if (!isActive) {
      if ('ontouchstart' in document.documentElement && !$parent.closest('.navbar-nav').length) {
        // if mobile we use a backdrop because click events don't delegate
        $(document.createElement('div'))
          .addClass('dropdown-backdrop')
          .insertAfter($(this))
          .on('click', clearMenus)
      }

      var relatedTarget = { relatedTarget: this }
      $parent.trigger(e = $.Event('show.bs.dropdown', relatedTarget))

      if (e.isDefaultPrevented()) return

      $this
        .trigger('focus')
        .attr('aria-expanded', 'true')

      $parent
        .toggleClass('open')
        .trigger('shown.bs.dropdown', relatedTarget)
    }

    return false
  }

  Dropdown.prototype.keydown = function (e) {
    if (!/(38|40|27|32)/.test(e.which) || /input|textarea/i.test(e.target.tagName)) return

    var $this = $(this)

    e.preventDefault()
    e.stopPropagation()

    if ($this.is('.disabled, :disabled')) return

    var $parent  = getParent($this)
    var isActive = $parent.hasClass('open')

    if (!isActive && e.which != 27 || isActive && e.which == 27) {
      if (e.which == 27) $parent.find(toggle).trigger('focus')
      return $this.trigger('click')
    }

    var desc = ' li:not(.disabled):visible a'
    var $items = $parent.find('.dropdown-menu' + desc)

    if (!$items.length) return

    var index = $items.index(e.target)

    if (e.which == 38 && index > 0)                 index--         // up
    if (e.which == 40 && index < $items.length - 1) index++         // down
    if (!~index)                                    index = 0

    $items.eq(index).trigger('focus')
  }


  // DROPDOWN PLUGIN DEFINITION
  // ==========================

  function Plugin(option) {
    return this.each(function () {
      var $this = $(this)
      var data  = $this.data('bs.dropdown')

      if (!data) $this.data('bs.dropdown', (data = new Dropdown(this)))
      if (typeof option == 'string') data[option].call($this)
    })
  }

  var old = $.fn.dropdown

  $.fn.dropdown             = Plugin
  $.fn.dropdown.Constructor = Dropdown


  // DROPDOWN NO CONFLICT
  // ====================

  $.fn.dropdown.noConflict = function () {
    $.fn.dropdown = old
    return this
  }


  // APPLY TO STANDARD DROPDOWN ELEMENTS
  // ===================================

  $(document)
    .on('click.bs.dropdown.data-api', clearMenus)
    .on('click.bs.dropdown.data-api', '.dropdown form', function (e) { e.stopPropagation() })
    .on('click.bs.dropdown.data-api', toggle, Dropdown.prototype.toggle)
    .on('keydown.bs.dropdown.data-api', toggle, Dropdown.prototype.keydown)
    .on('keydown.bs.dropdown.data-api', '.dropdown-menu', Dropdown.prototype.keydown)

}(jQuery);
*/
/* ========================================================================
 * Bootstrap: modal.js v3.3.5
 * http://getbootstrap.com/javascript/#modals
 * ========================================================================
 * Copyright 2011-2015 Twitter, Inc.
 * Licensed under MIT (https://github.com/twbs/bootstrap/blob/master/LICENSE)
 * ======================================================================== */


+function ($) {
  'use strict';

  // MODAL CLASS DEFINITION
  // ======================

  var Modal = function (element, options) {
    this.options             = options
    this.$body               = $(document.body)
    this.$element            = $(element)
    this.$dialog             = this.$element.find('.modal-dialog')
    this.$backdrop           = null
    this.isShown             = null
    this.originalBodyPad     = null
    this.scrollbarWidth      = 0
    this.ignoreBackdropClick = false

    if (this.options.remote) {
      this.$element
        .find('.modal-content')
        .load(this.options.remote, $.proxy(function () {
          this.$element.trigger('loaded.bs.modal')
        }, this))
    }
  }

  Modal.VERSION  = '3.3.5'

  Modal.TRANSITION_DURATION = 300
  Modal.BACKDROP_TRANSITION_DURATION = 150

  Modal.DEFAULTS = {
    backdrop: true,
    keyboard: true,
    show: true
  }

  Modal.prototype.toggle = function (_relatedTarget) {
    return this.isShown ? this.hide() : this.show(_relatedTarget)
  }

  Modal.prototype.show = function (_relatedTarget) {
    var that = this
    var e    = $.Event('show.bs.modal', { relatedTarget: _relatedTarget })

    this.$element.trigger(e)

    if (this.isShown || e.isDefaultPrevented()) return

    this.isShown = true

    this.checkScrollbar()
    this.setScrollbar()
    this.$body.addClass('modal-open')

    this.escape()
    this.resize()

    this.$element.on('click.dismiss.bs.modal', '[data-dismiss="modal"]', $.proxy(this.hide, this))

    this.$dialog.on('mousedown.dismiss.bs.modal', function () {
      that.$element.one('mouseup.dismiss.bs.modal', function (e) {
        if ($(e.target).is(that.$element)) that.ignoreBackdropClick = true
      })
    })

    this.backdrop(function () {
      var transition = $.support.transition && that.$element.hasClass('fade')

      if (!that.$element.parent().length) {
        that.$element.appendTo(that.$body) // don't move modals dom position
      }

      that.$element
        .show()
        .scrollTop(0)

      that.adjustDialog()

      if (transition) {
        that.$element[0].offsetWidth // force reflow
      }

      that.$element.addClass('in')

      that.enforceFocus()

      var e = $.Event('shown.bs.modal', { relatedTarget: _relatedTarget })

      transition ?
        that.$dialog // wait for modal to slide in
          .one('bsTransitionEnd', function () {
            that.$element.trigger('focus').trigger(e)
          })
          .emulateTransitionEnd(Modal.TRANSITION_DURATION) :
        that.$element.trigger('focus').trigger(e)
    })
  }

  Modal.prototype.hide = function (e) {
    if (e) e.preventDefault()

    e = $.Event('hide.bs.modal')

    this.$element.trigger(e)

    if (!this.isShown || e.isDefaultPrevented()) return

    this.isShown = false

    this.escape()
    this.resize()

    $(document).off('focusin.bs.modal')

    this.$element
      .removeClass('in')
      .off('click.dismiss.bs.modal')
      .off('mouseup.dismiss.bs.modal')

    this.$dialog.off('mousedown.dismiss.bs.modal')

    $.support.transition && this.$element.hasClass('fade') ?
      this.$element
        .one('bsTransitionEnd', $.proxy(this.hideModal, this))
        .emulateTransitionEnd(Modal.TRANSITION_DURATION) :
      this.hideModal()
  }

  Modal.prototype.enforceFocus = function () {
    $(document)
      .off('focusin.bs.modal') // guard against infinite focus loop
      .on('focusin.bs.modal', $.proxy(function (e) {
        if (this.$element[0] !== e.target && !this.$element.has(e.target).length) {
          this.$element.trigger('focus')
        }
      }, this))
  }

  Modal.prototype.escape = function () {
    if (this.isShown && this.options.keyboard) {
      this.$element.on('keydown.dismiss.bs.modal', $.proxy(function (e) {
        e.which == 27 && this.hide()
      }, this))
    } else if (!this.isShown) {
      this.$element.off('keydown.dismiss.bs.modal')
    }
  }

  Modal.prototype.resize = function () {
    if (this.isShown) {
      $(window).on('resize.bs.modal', $.proxy(this.handleUpdate, this))
    } else {
      $(window).off('resize.bs.modal')
    }
  }

  Modal.prototype.hideModal = function () {
    var that = this
    this.$element.hide()
    this.backdrop(function () {
      that.$body.removeClass('modal-open')
      that.resetAdjustments()
      that.resetScrollbar()
      that.$element.trigger('hidden.bs.modal')
    })
  }

  Modal.prototype.removeBackdrop = function () {
    this.$backdrop && this.$backdrop.remove()
    this.$backdrop = null
  }

  Modal.prototype.backdrop = function (callback) {
    var that = this
    var animate = this.$element.hasClass('fade') ? 'fade' : ''

    if (this.isShown && this.options.backdrop) {
      var doAnimate = $.support.transition && animate

      this.$backdrop = $(document.createElement('div'))
        .addClass('modal-backdrop ' + animate)
        .appendTo(this.$body)

      this.$element.on('click.dismiss.bs.modal', $.proxy(function (e) {
        if (this.ignoreBackdropClick) {
          this.ignoreBackdropClick = false
          return
        }
        if (e.target !== e.currentTarget) return
        this.options.backdrop == 'static'
          ? this.$element[0].focus()
          : this.hide()
      }, this))

      if (doAnimate) this.$backdrop[0].offsetWidth // force reflow

      this.$backdrop.addClass('in')

      if (!callback) return

      doAnimate ?
        this.$backdrop
          .one('bsTransitionEnd', callback)
          .emulateTransitionEnd(Modal.BACKDROP_TRANSITION_DURATION) :
        callback()

    } else if (!this.isShown && this.$backdrop) {
      this.$backdrop.removeClass('in')

      var callbackRemove = function () {
        that.removeBackdrop()
        callback && callback()
      }
      $.support.transition && this.$element.hasClass('fade') ?
        this.$backdrop
          .one('bsTransitionEnd', callbackRemove)
          .emulateTransitionEnd(Modal.BACKDROP_TRANSITION_DURATION) :
        callbackRemove()

    } else if (callback) {
      callback()
    }
  }

  // these following methods are used to handle overflowing modals

  Modal.prototype.handleUpdate = function () {
    this.adjustDialog()
  }

  Modal.prototype.adjustDialog = function () {
    var modalIsOverflowing = this.$element[0].scrollHeight > document.documentElement.clientHeight

    this.$element.css({
      paddingLeft:  !this.bodyIsOverflowing && modalIsOverflowing ? this.scrollbarWidth : '',
      paddingRight: this.bodyIsOverflowing && !modalIsOverflowing ? this.scrollbarWidth : ''
    })
  }

  Modal.prototype.resetAdjustments = function () {
    this.$element.css({
      paddingLeft: '',
      paddingRight: ''
    })
  }

  Modal.prototype.checkScrollbar = function () {
    var fullWindowWidth = window.innerWidth
    if (!fullWindowWidth) { // workaround for missing window.innerWidth in IE8
      var documentElementRect = document.documentElement.getBoundingClientRect()
      fullWindowWidth = documentElementRect.right - Math.abs(documentElementRect.left)
    }
    this.bodyIsOverflowing = document.body.clientWidth < fullWindowWidth
    this.scrollbarWidth = this.measureScrollbar()
  }

  Modal.prototype.setScrollbar = function () {
    var bodyPad = parseInt((this.$body.css('padding-right') || 0), 10)
    this.originalBodyPad = document.body.style.paddingRight || ''
    if (this.bodyIsOverflowing) this.$body.css('padding-right', bodyPad + this.scrollbarWidth)
  }

  Modal.prototype.resetScrollbar = function () {
    this.$body.css('padding-right', this.originalBodyPad)
  }

  Modal.prototype.measureScrollbar = function () { // thx walsh
    var scrollDiv = document.createElement('div')
    scrollDiv.className = 'modal-scrollbar-measure'
    this.$body.append(scrollDiv)
    var scrollbarWidth = scrollDiv.offsetWidth - scrollDiv.clientWidth
    this.$body[0].removeChild(scrollDiv)
    return scrollbarWidth
  }


  // MODAL PLUGIN DEFINITION
  // =======================

  function Plugin(option, _relatedTarget) {
    return this.each(function () {
      var $this   = $(this)
      var data    = $this.data('bs.modal')
      var options = $.extend({}, Modal.DEFAULTS, $this.data(), typeof option == 'object' && option)

      if (!data) $this.data('bs.modal', (data = new Modal(this, options)))
      if (typeof option == 'string') data[option](_relatedTarget)
      else if (options.show) data.show(_relatedTarget)
    })
  }

  var old = $.fn.modal

  $.fn.modal             = Plugin
  $.fn.modal.Constructor = Modal


  // MODAL NO CONFLICT
  // =================

  $.fn.modal.noConflict = function () {
    $.fn.modal = old
    return this
  }


  // MODAL DATA-API
  // ==============

  $(document).on('click.bs.modal.data-api', '[data-toggle="modal"]', function (e) {
    var $this   = $(this)
    var href    = $this.attr('href')
    var $target = $($this.attr('data-target') || (href && href.replace(/.*(?=#[^\s]+$)/, ''))) // strip for ie7
    var option  = $target.data('bs.modal') ? 'toggle' : $.extend({ remote: !/#/.test(href) && href }, $target.data(), $this.data())

    if ($this.is('a')) e.preventDefault()

    $target.one('show.bs.modal', function (showEvent) {
      if (showEvent.isDefaultPrevented()) return // only register focus restorer if modal will actually get shown
      $target.one('hidden.bs.modal', function () {
        $this.is(':visible') && $this.trigger('focus')
      })
    })
    Plugin.call($target, option, this)
  })

}(jQuery);

/* ========================================================================
 * Bootstrap: tooltip.js v3.3.5
 * http://getbootstrap.com/javascript/#tooltip
 * Inspired by the original jQuery.tipsy by Jason Frame
 * ========================================================================
 * Copyright 2011-2015 Twitter, Inc.
 * Licensed under MIT (https://github.com/twbs/bootstrap/blob/master/LICENSE)
 * ======================================================================== */


+function ($) {
  'use strict';

  // TOOLTIP PUBLIC CLASS DEFINITION
  // ===============================

  var Tooltip = function (element, options) {
    this.type       = null
    this.options    = null
    this.enabled    = null
    this.timeout    = null
    this.hoverState = null
    this.$element   = null
    this.inState    = null

    this.init('tooltip', element, options)
  }

  Tooltip.VERSION  = '3.3.5'

  Tooltip.TRANSITION_DURATION = 150

  Tooltip.DEFAULTS = {
    animation: true,
    placement: 'top',
    selector: false,
    template: '<div class="tooltip" role="tooltip"><div class="tooltip-arrow"></div><div class="tooltip-inner"></div></div>',
    trigger: 'hover focus',
    title: '',
    delay: 0,
    html: false,
    container: false,
    viewport: {
      selector: 'body',
      padding: 0
    }
  }

  Tooltip.prototype.init = function (type, element, options) {
    this.enabled   = true
    this.type      = type
    this.$element  = $(element)
    this.options   = this.getOptions(options)
    this.$viewport = this.options.viewport && $($.isFunction(this.options.viewport) ? this.options.viewport.call(this, this.$element) : (this.options.viewport.selector || this.options.viewport))
    this.inState   = { click: false, hover: false, focus: false }

    if (this.$element[0] instanceof document.constructor && !this.options.selector) {
      throw new Error('`selector` option must be specified when initializing ' + this.type + ' on the window.document object!')
    }

    var triggers = this.options.trigger.split(' ')

    for (var i = triggers.length; i--;) {
      var trigger = triggers[i]

      if (trigger == 'click') {
        this.$element.on('click.' + this.type, this.options.selector, $.proxy(this.toggle, this))
      } else if (trigger != 'manual') {
        var eventIn  = trigger == 'hover' ? 'mouseenter' : 'focusin'
        var eventOut = trigger == 'hover' ? 'mouseleave' : 'focusout'

        this.$element.on(eventIn  + '.' + this.type, this.options.selector, $.proxy(this.enter, this))
        this.$element.on(eventOut + '.' + this.type, this.options.selector, $.proxy(this.leave, this))
      }
    }

    this.options.selector ?
      (this._options = $.extend({}, this.options, { trigger: 'manual', selector: '' })) :
      this.fixTitle()
  }

  Tooltip.prototype.getDefaults = function () {
    return Tooltip.DEFAULTS
  }

  Tooltip.prototype.getOptions = function (options) {
    options = $.extend({}, this.getDefaults(), this.$element.data(), options)

    if (options.delay && typeof options.delay == 'number') {
      options.delay = {
        show: options.delay,
        hide: options.delay
      }
    }

    return options
  }

  Tooltip.prototype.getDelegateOptions = function () {
    var options  = {}
    var defaults = this.getDefaults()

    this._options && $.each(this._options, function (key, value) {
      if (defaults[key] != value) options[key] = value
    })

    return options
  }

  Tooltip.prototype.enter = function (obj) {
    var self = obj instanceof this.constructor ?
      obj : $(obj.currentTarget).data('bs.' + this.type)

    if (!self) {
      self = new this.constructor(obj.currentTarget, this.getDelegateOptions())
      $(obj.currentTarget).data('bs.' + this.type, self)
    }

    if (obj instanceof $.Event) {
      self.inState[obj.type == 'focusin' ? 'focus' : 'hover'] = true
    }

    if (self.tip().hasClass('in') || self.hoverState == 'in') {
      self.hoverState = 'in'
      return
    }

    clearTimeout(self.timeout)

    self.hoverState = 'in'

    if (!self.options.delay || !self.options.delay.show) return self.show()

    self.timeout = setTimeout(function () {
      if (self.hoverState == 'in') self.show()
    }, self.options.delay.show)
  }

  Tooltip.prototype.isInStateTrue = function () {
    for (var key in this.inState) {
      if (this.inState[key]) return true
    }

    return false
  }

  Tooltip.prototype.leave = function (obj) {
    var self = obj instanceof this.constructor ?
      obj : $(obj.currentTarget).data('bs.' + this.type)

    if (!self) {
      self = new this.constructor(obj.currentTarget, this.getDelegateOptions())
      $(obj.currentTarget).data('bs.' + this.type, self)
    }

    if (obj instanceof $.Event) {
      self.inState[obj.type == 'focusout' ? 'focus' : 'hover'] = false
    }

    if (self.isInStateTrue()) return

    clearTimeout(self.timeout)

    self.hoverState = 'out'

    if (!self.options.delay || !self.options.delay.hide) return self.hide()

    self.timeout = setTimeout(function () {
      if (self.hoverState == 'out') self.hide()
    }, self.options.delay.hide)
  }

  Tooltip.prototype.show = function () {
    var e = $.Event('show.bs.' + this.type)

    if (this.hasContent() && this.enabled) {
      this.$element.trigger(e)

      var inDom = $.contains(this.$element[0].ownerDocument.documentElement, this.$element[0])
      if (e.isDefaultPrevented() || !inDom) return
      var that = this

      var $tip = this.tip()

      var tipId = this.getUID(this.type)

      this.setContent()
      $tip.attr('id', tipId)
      this.$element.attr('aria-describedby', tipId)

      if (this.options.animation) $tip.addClass('fade')

      var placement = typeof this.options.placement == 'function' ?
        this.options.placement.call(this, $tip[0], this.$element[0]) :
        this.options.placement

      var autoToken = /\s?auto?\s?/i
      var autoPlace = autoToken.test(placement)
      if (autoPlace) placement = placement.replace(autoToken, '') || 'top'

      $tip
        .detach()
        .css({ top: 0, left: 0, display: 'block' })
        .addClass(placement)
        .data('bs.' + this.type, this)

      this.options.container ? $tip.appendTo(this.options.container) : $tip.insertAfter(this.$element)
      this.$element.trigger('inserted.bs.' + this.type)

      var pos          = this.getPosition()
      var actualWidth  = $tip[0].offsetWidth
      var actualHeight = $tip[0].offsetHeight

      if (autoPlace) {
        var orgPlacement = placement
        var viewportDim = this.getPosition(this.$viewport)

        placement = placement == 'bottom' && pos.bottom + actualHeight > viewportDim.bottom ? 'top'    :
                    placement == 'top'    && pos.top    - actualHeight < viewportDim.top    ? 'bottom' :
                    placement == 'right'  && pos.right  + actualWidth  > viewportDim.width  ? 'left'   :
                    placement == 'left'   && pos.left   - actualWidth  < viewportDim.left   ? 'right'  :
                    placement

        $tip
          .removeClass(orgPlacement)
          .addClass(placement)
      }

      var calculatedOffset = this.getCalculatedOffset(placement, pos, actualWidth, actualHeight)

      this.applyPlacement(calculatedOffset, placement)

      var complete = function () {
        var prevHoverState = that.hoverState
        that.$element.trigger('shown.bs.' + that.type)
        that.hoverState = null

        if (prevHoverState == 'out') that.leave(that)
      }

      $.support.transition && this.$tip.hasClass('fade') ?
        $tip
          .one('bsTransitionEnd', complete)
          .emulateTransitionEnd(Tooltip.TRANSITION_DURATION) :
        complete()
    }
  }

  Tooltip.prototype.applyPlacement = function (offset, placement) {
    var $tip   = this.tip()
    var width  = $tip[0].offsetWidth
    var height = $tip[0].offsetHeight

    // manually read margins because getBoundingClientRect includes difference
    var marginTop = parseInt($tip.css('margin-top'), 10)
    var marginLeft = parseInt($tip.css('margin-left'), 10)

    // we must check for NaN for ie 8/9
    if (isNaN(marginTop))  marginTop  = 0
    if (isNaN(marginLeft)) marginLeft = 0

    offset.top  += marginTop
    offset.left += marginLeft

    // $.fn.offset doesn't round pixel values
    // so we use setOffset directly with our own function B-0
    $.offset.setOffset($tip[0], $.extend({
      using: function (props) {
        $tip.css({
          top: Math.round(props.top),
          left: Math.round(props.left)
        })
      }
    }, offset), 0)

    $tip.addClass('in')

    // check to see if placing tip in new offset caused the tip to resize itself
    var actualWidth  = $tip[0].offsetWidth
    var actualHeight = $tip[0].offsetHeight

    if (placement == 'top' && actualHeight != height) {
      offset.top = offset.top + height - actualHeight
    }

    var delta = this.getViewportAdjustedDelta(placement, offset, actualWidth, actualHeight)

    if (delta.left) offset.left += delta.left
    else offset.top += delta.top

    var isVertical          = /top|bottom/.test(placement)
    var arrowDelta          = isVertical ? delta.left * 2 - width + actualWidth : delta.top * 2 - height + actualHeight
    var arrowOffsetPosition = isVertical ? 'offsetWidth' : 'offsetHeight'

    $tip.offset(offset)
    this.replaceArrow(arrowDelta, $tip[0][arrowOffsetPosition], isVertical)
  }

  Tooltip.prototype.replaceArrow = function (delta, dimension, isVertical) {
    this.arrow()
      .css(isVertical ? 'left' : 'top', 50 * (1 - delta / dimension) + '%')
      .css(isVertical ? 'top' : 'left', '')
  }

  Tooltip.prototype.setContent = function () {
    var $tip  = this.tip()
    var title = this.getTitle()

    $tip.find('.tooltip-inner')[this.options.html ? 'html' : 'text'](title)
    $tip.removeClass('fade in top bottom left right')
  }

  Tooltip.prototype.hide = function (callback) {
    var that = this
    var $tip = $(this.$tip)
    var e    = $.Event('hide.bs.' + this.type)

    function complete() {
      if (that.hoverState != 'in') $tip.detach()
      that.$element
        .removeAttr('aria-describedby')
        .trigger('hidden.bs.' + that.type)
      callback && callback()
    }

    this.$element.trigger(e)

    if (e.isDefaultPrevented()) return

    $tip.removeClass('in')

    $.support.transition && $tip.hasClass('fade') ?
      $tip
        .one('bsTransitionEnd', complete)
        .emulateTransitionEnd(Tooltip.TRANSITION_DURATION) :
      complete()

    this.hoverState = null

    return this
  }

  Tooltip.prototype.fixTitle = function () {
    var $e = this.$element
    if ($e.attr('title') || typeof $e.attr('data-original-title') != 'string') {
      $e.attr('data-original-title', $e.attr('title') || '').attr('title', '')
    }
  }

  Tooltip.prototype.hasContent = function () {
    return this.getTitle()
  }

  Tooltip.prototype.getPosition = function ($element) {
    $element   = $element || this.$element

    var el     = $element[0]
    var isBody = el.tagName == 'BODY'

    var elRect    = el.getBoundingClientRect()
    if (elRect.width == null) {
      // width and height are missing in IE8, so compute them manually; see https://github.com/twbs/bootstrap/issues/14093
      elRect = $.extend({}, elRect, { width: elRect.right - elRect.left, height: elRect.bottom - elRect.top })
    }
    var elOffset  = isBody ? { top: 0, left: 0 } : $element.offset()
    var scroll    = { scroll: isBody ? document.documentElement.scrollTop || document.body.scrollTop : $element.scrollTop() }
    var outerDims = isBody ? { width: $(window).width(), height: $(window).height() } : null

    return $.extend({}, elRect, scroll, outerDims, elOffset)
  }

  Tooltip.prototype.getCalculatedOffset = function (placement, pos, actualWidth, actualHeight) {
    return placement == 'bottom' ? { top: pos.top + pos.height,   left: pos.left + pos.width / 2 - actualWidth / 2 } :
           placement == 'top'    ? { top: pos.top - actualHeight, left: pos.left + pos.width / 2 - actualWidth / 2 } :
           placement == 'left'   ? { top: pos.top + pos.height / 2 - actualHeight / 2, left: pos.left - actualWidth } :
        /* placement == 'right' */ { top: pos.top + pos.height / 2 - actualHeight / 2, left: pos.left + pos.width }

  }

  Tooltip.prototype.getViewportAdjustedDelta = function (placement, pos, actualWidth, actualHeight) {
    var delta = { top: 0, left: 0 }
    if (!this.$viewport) return delta

    var viewportPadding = this.options.viewport && this.options.viewport.padding || 0
    var viewportDimensions = this.getPosition(this.$viewport)

    if (/right|left/.test(placement)) {
      var topEdgeOffset    = pos.top - viewportPadding - viewportDimensions.scroll
      var bottomEdgeOffset = pos.top + viewportPadding - viewportDimensions.scroll + actualHeight
      if (topEdgeOffset < viewportDimensions.top) { // top overflow
        delta.top = viewportDimensions.top - topEdgeOffset
      } else if (bottomEdgeOffset > viewportDimensions.top + viewportDimensions.height) { // bottom overflow
        delta.top = viewportDimensions.top + viewportDimensions.height - bottomEdgeOffset
      }
    } else {
      var leftEdgeOffset  = pos.left - viewportPadding
      var rightEdgeOffset = pos.left + viewportPadding + actualWidth
      if (leftEdgeOffset < viewportDimensions.left) { // left overflow
        delta.left = viewportDimensions.left - leftEdgeOffset
      } else if (rightEdgeOffset > viewportDimensions.right) { // right overflow
        delta.left = viewportDimensions.left + viewportDimensions.width - rightEdgeOffset
      }
    }

    return delta
  }

  Tooltip.prototype.getTitle = function () {
    var title
    var $e = this.$element
    var o  = this.options

    title = $e.attr('data-original-title')
      || (typeof o.title == 'function' ? o.title.call($e[0]) :  o.title)

    return title
  }

  Tooltip.prototype.getUID = function (prefix) {
    do prefix += ~~(Math.random() * 1000000)
    while (document.getElementById(prefix))
    return prefix
  }

  Tooltip.prototype.tip = function () {
    if (!this.$tip) {
      this.$tip = $(this.options.template)
      if (this.$tip.length != 1) {
        throw new Error(this.type + ' `template` option must consist of exactly 1 top-level element!')
      }
    }
    return this.$tip
  }

  Tooltip.prototype.arrow = function () {
    return (this.$arrow = this.$arrow || this.tip().find('.tooltip-arrow'))
  }

  Tooltip.prototype.enable = function () {
    this.enabled = true
  }

  Tooltip.prototype.disable = function () {
    this.enabled = false
  }

  Tooltip.prototype.toggleEnabled = function () {
    this.enabled = !this.enabled
  }

  Tooltip.prototype.toggle = function (e) {
    var self = this
    if (e) {
      self = $(e.currentTarget).data('bs.' + this.type)
      if (!self) {
        self = new this.constructor(e.currentTarget, this.getDelegateOptions())
        $(e.currentTarget).data('bs.' + this.type, self)
      }
    }

    if (e) {
      self.inState.click = !self.inState.click
      if (self.isInStateTrue()) self.enter(self)
      else self.leave(self)
    } else {
      self.tip().hasClass('in') ? self.leave(self) : self.enter(self)
    }
  }

  Tooltip.prototype.destroy = function () {
    var that = this
    clearTimeout(this.timeout)
    this.hide(function () {
      that.$element.off('.' + that.type).removeData('bs.' + that.type)
      if (that.$tip) {
        that.$tip.detach()
      }
      that.$tip = null
      that.$arrow = null
      that.$viewport = null
    })
  }


  // TOOLTIP PLUGIN DEFINITION
  // =========================

  function Plugin(option) {
    return this.each(function () {
      var $this   = $(this)
      var data    = $this.data('bs.tooltip')
      var options = typeof option == 'object' && option

      if (!data && /destroy|hide/.test(option)) return
      if (!data) $this.data('bs.tooltip', (data = new Tooltip(this, options)))
      if (typeof option == 'string') data[option]()
    })
  }

  var old = $.fn.tooltip

  $.fn.tooltip             = Plugin
  $.fn.tooltip.Constructor = Tooltip


  // TOOLTIP NO CONFLICT
  // ===================

  $.fn.tooltip.noConflict = function () {
    $.fn.tooltip = old
    return this
  }

}(jQuery);

/* ========================================================================
 * Bootstrap: popover.js v3.3.5
 * http://getbootstrap.com/javascript/#popovers
 * ========================================================================
 * Copyright 2011-2015 Twitter, Inc.
 * Licensed under MIT (https://github.com/twbs/bootstrap/blob/master/LICENSE)
 * ======================================================================== */


+function ($) {
  'use strict';

  // POPOVER PUBLIC CLASS DEFINITION
  // ===============================

  var Popover = function (element, options) {
    this.init('popover', element, options)
  }

  if (!$.fn.tooltip) throw new Error('Popover requires tooltip.js')

  Popover.VERSION  = '3.3.5'

  Popover.DEFAULTS = $.extend({}, $.fn.tooltip.Constructor.DEFAULTS, {
    placement: 'right',
    trigger: 'click',
    content: '',
    template: '<div class="popover" role="tooltip"><div class="arrow"></div><h3 class="popover-title"></h3><div class="popover-content"></div></div>'
  })


  // NOTE: POPOVER EXTENDS tooltip.js
  // ================================

  Popover.prototype = $.extend({}, $.fn.tooltip.Constructor.prototype)

  Popover.prototype.constructor = Popover

  Popover.prototype.getDefaults = function () {
    return Popover.DEFAULTS
  }

  Popover.prototype.setContent = function () {
    var $tip    = this.tip()
    var title   = this.getTitle()
    var content = this.getContent()

    $tip.find('.popover-title')[this.options.html ? 'html' : 'text'](title)
    $tip.find('.popover-content').children().detach().end()[ // we use append for html objects to maintain js events
      this.options.html ? (typeof content == 'string' ? 'html' : 'append') : 'text'
    ](content)

    $tip.removeClass('fade top bottom left right in')

    // IE8 doesn't accept hiding via the `:empty` pseudo selector, we have to do
    // this manually by checking the contents.
    if (!$tip.find('.popover-title').html()) $tip.find('.popover-title').hide()
  }

  Popover.prototype.hasContent = function () {
    return this.getTitle() || this.getContent()
  }

  Popover.prototype.getContent = function () {
    var $e = this.$element
    var o  = this.options

    return $e.attr('data-content')
      || (typeof o.content == 'function' ?
            o.content.call($e[0]) :
            o.content)
  }

  Popover.prototype.arrow = function () {
    return (this.$arrow = this.$arrow || this.tip().find('.arrow'))
  }


  // POPOVER PLUGIN DEFINITION
  // =========================

  function Plugin(option) {
    return this.each(function () {
      var $this   = $(this)
      var data    = $this.data('bs.popover')
      var options = typeof option == 'object' && option

      if (!data && /destroy|hide/.test(option)) return
      if (!data) $this.data('bs.popover', (data = new Popover(this, options)))
      if (typeof option == 'string') data[option]()
    })
  }

  var old = $.fn.popover

  $.fn.popover             = Plugin
  $.fn.popover.Constructor = Popover


  // POPOVER NO CONFLICT
  // ===================

  $.fn.popover.noConflict = function () {
    $.fn.popover = old
    return this
  }

}(jQuery);

/* ========================================================================
 * Bootstrap: scrollspy.js v3.3.5
 * http://getbootstrap.com/javascript/#scrollspy
 * ========================================================================
 * Copyright 2011-2015 Twitter, Inc.
 * Licensed under MIT (https://github.com/twbs/bootstrap/blob/master/LICENSE)
 * ======================================================================== */


+function ($) {
  'use strict';

  // SCROLLSPY CLASS DEFINITION
  // ==========================

  function ScrollSpy(element, options) {
    this.$body          = $(document.body)
    this.$scrollElement = $(element).is(document.body) ? $(window) : $(element)
    this.options        = $.extend({}, ScrollSpy.DEFAULTS, options)
    this.selector       = (this.options.target || '') + ' .nav li > a'
    this.offsets        = []
    this.targets        = []
    this.activeTarget   = null
    this.scrollHeight   = 0

    this.$scrollElement.on('scroll.bs.scrollspy', $.proxy(this.process, this))
    this.refresh()
    this.process()
  }

  ScrollSpy.VERSION  = '3.3.5'

  ScrollSpy.DEFAULTS = {
    offset: 10
  }

  ScrollSpy.prototype.getScrollHeight = function () {
    return this.$scrollElement[0].scrollHeight || Math.max(this.$body[0].scrollHeight, document.documentElement.scrollHeight)
  }

  ScrollSpy.prototype.refresh = function () {
    var that          = this
    var offsetMethod  = 'offset'
    var offsetBase    = 0

    this.offsets      = []
    this.targets      = []
    this.scrollHeight = this.getScrollHeight()

    if (!$.isWindow(this.$scrollElement[0])) {
      offsetMethod = 'position'
      offsetBase   = this.$scrollElement.scrollTop()
    }

    this.$body
      .find(this.selector)
      .map(function () {
        var $el   = $(this)
        var href  = $el.data('target') || $el.attr('href')
        var $href = /^#./.test(href) && $(href)

        return ($href
          && $href.length
          && $href.is(':visible')
          && [[$href[offsetMethod]().top + offsetBase, href]]) || null
      })
      .sort(function (a, b) { return a[0] - b[0] })
      .each(function () {
        that.offsets.push(this[0])
        that.targets.push(this[1])
      })
  }

  ScrollSpy.prototype.process = function () {
    var scrollTop    = this.$scrollElement.scrollTop() + this.options.offset
    var scrollHeight = this.getScrollHeight()
    var maxScroll    = this.options.offset + scrollHeight - this.$scrollElement.height()
    var offsets      = this.offsets
    var targets      = this.targets
    var activeTarget = this.activeTarget
    var i

    if (this.scrollHeight != scrollHeight) {
      this.refresh()
    }

    if (scrollTop >= maxScroll) {
      return activeTarget != (i = targets[targets.length - 1]) && this.activate(i)
    }

    if (activeTarget && scrollTop < offsets[0]) {
      this.activeTarget = null
      return this.clear()
    }

    for (i = offsets.length; i--;) {
      activeTarget != targets[i]
        && scrollTop >= offsets[i]
        && (offsets[i + 1] === undefined || scrollTop < offsets[i + 1])
        && this.activate(targets[i])
    }
  }

  ScrollSpy.prototype.activate = function (target) {
    this.activeTarget = target

    this.clear()

    var selector = this.selector +
      '[data-target="' + target + '"],' +
      this.selector + '[href="' + target + '"]'

    var active = $(selector)
      .parents('li')
      .addClass('active')

    if (active.parent('.dropdown-menu').length) {
      active = active
        .closest('li.dropdown')
        .addClass('active')
    }

    active.trigger('activate.bs.scrollspy')
  }

  ScrollSpy.prototype.clear = function () {
    $(this.selector)
      .parentsUntil(this.options.target, '.active')
      .removeClass('active')
  }


  // SCROLLSPY PLUGIN DEFINITION
  // ===========================

  function Plugin(option) {
    return this.each(function () {
      var $this   = $(this)
      var data    = $this.data('bs.scrollspy')
      var options = typeof option == 'object' && option

      if (!data) $this.data('bs.scrollspy', (data = new ScrollSpy(this, options)))
      if (typeof option == 'string') data[option]()
    })
  }

  var old = $.fn.scrollspy

  $.fn.scrollspy             = Plugin
  $.fn.scrollspy.Constructor = ScrollSpy


  // SCROLLSPY NO CONFLICT
  // =====================

  $.fn.scrollspy.noConflict = function () {
    $.fn.scrollspy = old
    return this
  }


  // SCROLLSPY DATA-API
  // ==================

  $(window).on('load.bs.scrollspy.data-api', function () {
    $('[data-spy="scroll"]').each(function () {
      var $spy = $(this)
      Plugin.call($spy, $spy.data())
    })
  })

}(jQuery);

/* ========================================================================
 * Bootstrap: tab.js v3.3.5
 * http://getbootstrap.com/javascript/#tabs
 * ========================================================================
 * Copyright 2011-2015 Twitter, Inc.
 * Licensed under MIT (https://github.com/twbs/bootstrap/blob/master/LICENSE)
 * ======================================================================== */


+function ($) {
  'use strict';

  // TAB CLASS DEFINITION
  // ====================

  var Tab = function (element) {
    // jscs:disable requireDollarBeforejQueryAssignment
    this.element = $(element)
    // jscs:enable requireDollarBeforejQueryAssignment
  }

  Tab.VERSION = '3.3.5'

  Tab.TRANSITION_DURATION = 150

  Tab.prototype.show = function () {
    var $this    = this.element
    var $ul      = $this.closest('ul:not(.dropdown-menu)')
    var selector = $this.data('target')

    if (!selector) {
      selector = $this.attr('href')
      selector = selector && selector.replace(/.*(?=#[^\s]*$)/, '') // strip for ie7
    }

    if ($this.parent('li').hasClass('active')) return

    var $previous = $ul.find('.active:last a')
    var hideEvent = $.Event('hide.bs.tab', {
      relatedTarget: $this[0]
    })
    var showEvent = $.Event('show.bs.tab', {
      relatedTarget: $previous[0]
    })

    $previous.trigger(hideEvent)
    $this.trigger(showEvent)

    if (showEvent.isDefaultPrevented() || hideEvent.isDefaultPrevented()) return

    var $target = $(selector)

    this.activate($this.closest('li'), $ul)
    this.activate($target, $target.parent(), function () {
      $previous.trigger({
        type: 'hidden.bs.tab',
        relatedTarget: $this[0]
      })
      $this.trigger({
        type: 'shown.bs.tab',
        relatedTarget: $previous[0]
      })
    })
  }

  Tab.prototype.activate = function (element, container, callback) {
    var $active    = container.find('> .active')
    var transition = callback
      && $.support.transition
      && ($active.length && $active.hasClass('fade') || !!container.find('> .fade').length)

    function next() {
      $active
        .removeClass('active')
        .find('> .dropdown-menu > .active')
          .removeClass('active')
        .end()
        .find('[data-toggle="tab"]')
          .attr('aria-expanded', false)

      element
        .addClass('active')
        .find('[data-toggle="tab"]')
          .attr('aria-expanded', true)

      if (transition) {
        element[0].offsetWidth // reflow for transition
        element.addClass('in')
      } else {
        element.removeClass('fade')
      }

      if (element.parent('.dropdown-menu').length) {
        element
          .closest('li.dropdown')
            .addClass('active')
          .end()
          .find('[data-toggle="tab"]')
            .attr('aria-expanded', true)
      }

      callback && callback()
    }

    $active.length && transition ?
      $active
        .one('bsTransitionEnd', next)
        .emulateTransitionEnd(Tab.TRANSITION_DURATION) :
      next()

    $active.removeClass('in')
  }


  // TAB PLUGIN DEFINITION
  // =====================

  function Plugin(option) {
    return this.each(function () {
      var $this = $(this)
      var data  = $this.data('bs.tab')

      if (!data) $this.data('bs.tab', (data = new Tab(this)))
      if (typeof option == 'string') data[option]()
    })
  }

  var old = $.fn.tab

  $.fn.tab             = Plugin
  $.fn.tab.Constructor = Tab


  // TAB NO CONFLICT
  // ===============

  $.fn.tab.noConflict = function () {
    $.fn.tab = old
    return this
  }


  // TAB DATA-API
  // ============

  var clickHandler = function (e) {
    e.preventDefault()
    Plugin.call($(this), 'show')
  }

  $(document)
    .on('click.bs.tab.data-api', '[data-toggle="tab"]', clickHandler)
    .on('click.bs.tab.data-api', '[data-toggle="pill"]', clickHandler)

}(jQuery);

/* ========================================================================
 * Bootstrap: affix.js v3.3.5
 * http://getbootstrap.com/javascript/#affix
 * ========================================================================
 * Copyright 2011-2015 Twitter, Inc.
 * Licensed under MIT (https://github.com/twbs/bootstrap/blob/master/LICENSE)
 * ======================================================================== */


+function ($) {
  'use strict';

  // AFFIX CLASS DEFINITION
  // ======================

  var Affix = function (element, options) {
    this.options = $.extend({}, Affix.DEFAULTS, options)

    this.$target = $(this.options.target)
      .on('scroll.bs.affix.data-api', $.proxy(this.checkPosition, this))
      .on('click.bs.affix.data-api',  $.proxy(this.checkPositionWithEventLoop, this))

    this.$element     = $(element)
    this.affixed      = null
    this.unpin        = null
    this.pinnedOffset = null

    this.checkPosition()
  }

  Affix.VERSION  = '3.3.5'

  Affix.RESET    = 'affix affix-top affix-bottom'

  Affix.DEFAULTS = {
    offset: 0,
    target: window
  }

  Affix.prototype.getState = function (scrollHeight, height, offsetTop, offsetBottom) {
    var scrollTop    = this.$target.scrollTop()
    var position     = this.$element.offset()
    var targetHeight = this.$target.height()

    if (offsetTop != null && this.affixed == 'top') return scrollTop < offsetTop ? 'top' : false

    if (this.affixed == 'bottom') {
      if (offsetTop != null) return (scrollTop + this.unpin <= position.top) ? false : 'bottom'
      return (scrollTop + targetHeight <= scrollHeight - offsetBottom) ? false : 'bottom'
    }

    var initializing   = this.affixed == null
    var colliderTop    = initializing ? scrollTop : position.top
    var colliderHeight = initializing ? targetHeight : height

    if (offsetTop != null && scrollTop <= offsetTop) return 'top'
    if (offsetBottom != null && (colliderTop + colliderHeight >= scrollHeight - offsetBottom)) return 'bottom'

    return false
  }

  Affix.prototype.getPinnedOffset = function () {
    if (this.pinnedOffset) return this.pinnedOffset
    this.$element.removeClass(Affix.RESET).addClass('affix')
    var scrollTop = this.$target.scrollTop()
    var position  = this.$element.offset()
    return (this.pinnedOffset = position.top - scrollTop)
  }

  Affix.prototype.checkPositionWithEventLoop = function () {
    setTimeout($.proxy(this.checkPosition, this), 1)
  }

  Affix.prototype.checkPosition = function () {
    if (!this.$element.is(':visible')) return

    var height       = this.$element.height()
    var offset       = this.options.offset
    var offsetTop    = offset.top
    var offsetBottom = offset.bottom
    var scrollHeight = Math.max($(document).height(), $(document.body).height())

    if (typeof offset != 'object')         offsetBottom = offsetTop = offset
    if (typeof offsetTop == 'function')    offsetTop    = offset.top(this.$element)
    if (typeof offsetBottom == 'function') offsetBottom = offset.bottom(this.$element)

    var affix = this.getState(scrollHeight, height, offsetTop, offsetBottom)

    if (this.affixed != affix) {
      if (this.unpin != null) this.$element.css('top', '')

      var affixType = 'affix' + (affix ? '-' + affix : '')
      var e         = $.Event(affixType + '.bs.affix')

      this.$element.trigger(e)

      if (e.isDefaultPrevented()) return

      this.affixed = affix
      this.unpin = affix == 'bottom' ? this.getPinnedOffset() : null

      this.$element
        .removeClass(Affix.RESET)
        .addClass(affixType)
        .trigger(affixType.replace('affix', 'affixed') + '.bs.affix')
    }

    if (affix == 'bottom') {
      this.$element.offset({
        top: scrollHeight - height - offsetBottom
      })
    }
  }


  // AFFIX PLUGIN DEFINITION
  // =======================

  function Plugin(option) {
    return this.each(function () {
      var $this   = $(this)
      var data    = $this.data('bs.affix')
      var options = typeof option == 'object' && option

      if (!data) $this.data('bs.affix', (data = new Affix(this, options)))
      if (typeof option == 'string') data[option]()
    })
  }

  var old = $.fn.affix

  $.fn.affix             = Plugin
  $.fn.affix.Constructor = Affix


  // AFFIX NO CONFLICT
  // =================

  $.fn.affix.noConflict = function () {
    $.fn.affix = old
    return this
  }


  // AFFIX DATA-API
  // ==============

  $(window).on('load', function () {
    $('[data-spy="affix"]').each(function () {
      var $spy = $(this)
      var data = $spy.data()

      data.offset = data.offset || {}

      if (data.offsetBottom != null) data.offset.bottom = data.offsetBottom
      if (data.offsetTop    != null) data.offset.top    = data.offsetTop

      Plugin.call($spy, data)
    })
  })

}(jQuery);


$(function() {
    //jQuery弹窗提醒插件
    $.alertMessager = function(message, type, time) {
        if($.bootstrapGrowl){
            $.bootstrapGrowl(message, {
                type: type,
                align: 'center',
                width: 'auto'
            });
        }else{
            type = type ? type : 'danger';
            var messager = '<div style="width:380px;height:auto;margin:0 auto;max-width: 80%;top:52px;left:0;right:0;z-index:99999;"' +
                'class="messager navbar-fixed-top border-none alert alert-' + type + '"><button type="button" class="close" ' +
                'data-dismiss="alert"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>' + message + '</div>';
            $('.messager').remove();
            $('body').prepend(messager);
            setTimeout(function() {
                $('.messager').remove();
            }, time ? time : 2000);
        }
    };



    //ajax get请求
    $('body').on('click', '.ajax-get', function() {
        var target;
        var that = this;
        if ($(this).hasClass('confirm')) {
            if (!confirm('确认要执行该操作吗?')) {
                return false;
            }
        }
        if ((target = $(this).attr('href')) || (target = $(this).attr('url'))) {
            $.get(target).success(function(data) {
                if (data.status == 1) {
                    if (data.url) {
                        message = data.info + ' 页面即将自动跳转~';
                    } else {
                        message = data.info;
                    }
                    $.alertMessager(message, 'success');
                    setTimeout(function() {
                        $(that).removeClass('disabled').prop('disabled', false);
                        if (data.url) {
                            location.href = data.url;
                        } else {
                            location.reload();
                        }
                    }, 2000);
                } else {
                    if (data.login == 1) {
                        $('#login-modal').modal(); //弹出登陆框
                    } else {
                        $.alertMessager(data.info, 'danger');
                    }
                    setTimeout(function() {
                        $(that).removeClass('disabled').prop('disabled', false);
                    }, 2000);
                }
            });
        }
        return false;
    });



    //ajax post submit请求
    $('body').on('click', '.ajax-post', function() {
        var target, query, form;
        var target_form = $(this).attr('target-form');
        var that = this;
        var nead_confirm = false;

        if (($(this).attr('type') == 'submit') || (target = $(this).attr('href')) || (target = $(this).attr('url'))) {
            form = $('.' + target_form);
            if ($(this).attr('hide-data') === 'true') { //无数据时也可以使用的功能
                form = $('.hide-data');
                query = form.serialize();
            } else if (form.get(0) == undefined) {
                return false;
            } else if (form.get(0).nodeName == 'FORM') {
                if ($(this).hasClass('confirm')) {
                    if (!confirm('确认要执行该操作吗?')) {
                        return false;
                    }
                }
                if ($(this).attr('url') !== undefined) {
                    target = $(this).attr('url');
                } else {
                    target = form.get(0).action;
                }
                query = form.serialize();
            } else if (form.get(0).nodeName == 'INPUT' || form.get(0).nodeName == 'SELECT' || form.get(0).nodeName == 'TEXTAREA') {
                form.each(function(k, v) {
                    if (v.type == 'checkbox' && v.checked == true) {
                        nead_confirm = true;
                    }
                });
                if (nead_confirm && $(this).hasClass('confirm')) {
                    if (!confirm('确认要执行该操作吗?')) {
                        return false;
                    }
                }
                query = form.serialize();
            } else {
                if ($(this).hasClass('confirm')) {
                    if (!confirm('确认要执行该操作吗?')) {
                        return false;
                    }
                }
                query = form.find('input,select,textarea').serialize();
            }

            $(that).addClass('disabled').attr('autocomplete', 'off').prop('disabled', true);
            $.post(target, query).success(function(data) {

                if (data.status == 1) {
                    if (data.url) {
                        message = data.info + ' 页面即将自动跳转~';
                    } else {
                        message = data.info;
                    }
                    $.alertMessager(message, 'success');
                    setTimeout(function() {
                        if (data.url) {
                            location.href = data.url;
                        } else {
                            location.reload();
                        }
                    }, 2000);
                } else {
                    $.alertMessager(data.info, 'danger');
                    setTimeout(function() {
                        $(that).removeClass('disabled').prop('disabled', false);
                    }, 2000);
                    if($('.reload-verify').length > 0){
                        $('.reload-verify').click();
                    }
                }
            });
        }
        return false;
    });
});

(function() {
  var $;

  $ = jQuery;

  $.bootstrapGrowl = function(message, options) {
    var $alert, css, offsetAmount;
    options = $.extend({}, $.bootstrapGrowl.default_options, options);
    $alert = $("<div>");
    $alert.attr("class", "bootstrap-growl alert");
    if (options.type) {
      $alert.addClass("alert-" + options.type);
    }
    if (options.allow_dismiss) {
      $alert.addClass("alert-dismissible");
      $alert.append("<button  class=\"close\" data-dismiss=\"alert\" type=\"button\"><span aria-hidden=\"true\">&times;</span><span class=\"sr-only\">Close</span></button>");
    }
    $alert.append(message);
    if (options.top_offset) {
      options.offset = {
        from: "top",
        amount: options.top_offset
      };
    }
    offsetAmount = options.offset.amount;
    $(".bootstrap-growl").each(function() {
      return offsetAmount = Math.max(offsetAmount, parseInt($(this).css(options.offset.from)) + $(this).outerHeight() + options.stackup_spacing);
    });
    css = {
      "position": (options.ele === "body" ? "fixed" : "absolute"),
      "margin": 0,
      "z-index": "9999",
      "display": "none"
    };
    css[options.offset.from] = offsetAmount + "px";
    $alert.css(css);
    if (options.width !== "auto") {
      $alert.css("width", options.width + "px");
    }
    $(options.ele).append($alert);
    switch (options.align) {
      case "center":
        $alert.css({
          "left": "50%",
          "margin-left": "-" + ($alert.outerWidth() / 2) + "px"
        });
        break;
      case "left":
        $alert.css("left", "20px");
        break;
      default:
        $alert.css("right", "20px");
    }
    $alert.fadeIn();
    if (options.delay > 0) {
      $alert.delay(options.delay).fadeOut(function() {
        return $(this).alert("close");
      });
    }
    return $alert;
  };

  $.bootstrapGrowl.default_options = {
    ele: "body",
    type: "info",
    offset: {
      from: "top",
      amount: 20
    },
    align: "right",
    width: 250,
    delay: 4000,
    allow_dismiss: true,
    stackup_spacing: 10
  };

}).call(this);

/**
 * @version: 2.0.6
 * @author: Dan Grossman http://www.dangrossman.info/
 * @copyright: Copyright (c) 2012-2015 Dan Grossman. All rights reserved.
 * @license: Licensed under the MIT license. See http://www.opensource.org/licenses/mit-license.php
 * @website: https://www.improvely.com/
 */

(function(root, factory) {

    if (typeof define === 'function' && define.amd) {
        define(['moment', 'jquery', 'exports'], function(momentjs, $, exports) {
            root.daterangepicker = factory(root, exports, momentjs, $);
        });

    } else if (typeof exports !== 'undefined') {
        var momentjs = require('moment');
        var jQuery = window.jQuery;
        if (jQuery === undefined) {
            try {
                jQuery = require('jquery');
            } catch (err) {
                if (!jQuery) throw new Error('jQuery dependency not found');
            }
        }

        factory(root, exports, momentjs, jQuery);

        // Finally, as a browser global.
    } else {
        root.daterangepicker = factory(root, {}, root.moment || moment, (root.jQuery || root.Zepto || root.ender || root.$));
    }

}(this, function(root, daterangepicker, moment, $) {

    var DateRangePicker = function(element, options, cb) {

        //default settings for options
        this.parentEl = 'body';
        this.element = $(element);
        this.startDate = moment().startOf('day');
        this.endDate = moment().endOf('day');
        this.timeZone = moment().utcOffset();
        this.minDate = false;
        this.maxDate = false;
        this.dateLimit = false;
        this.autoApply = false;
        this.singleDatePicker = false;
        this.showDropdowns = false;
        this.showWeekNumbers = false;
        this.timePicker = false;
        this.timePicker24Hour = false;
        this.timePickerIncrement = 1;
        this.timePickerSeconds = false;
        this.ranges = {};

        this.opens = 'right';
        if (this.element.hasClass('pull-right'))
            this.opens = 'left';

        this.drops = 'down';
        if (this.element.hasClass('dropup'))
            this.drops = 'up';

        this.buttonClasses = 'btn btn-sm';
        this.applyClass = 'btn-success';
        this.cancelClass = 'btn-default';

        this.locale = {
            format: 'MM/DD/YYYY',
            separator: ' - ',
            applyLabel: 'Apply',
            cancelLabel: 'Cancel',
            weekLabel: 'W',
            customRangeLabel: 'Custom Range',
            daysOfWeek: moment.weekdaysMin(),
            monthNames: moment.monthsShort(),
            firstDay: moment.localeData().firstDayOfWeek()
        };

        this.callback = function() {};

        //some state information
        this.isShowing = false;
        this.leftCalendar = {};
        this.rightCalendar = {};

        //custom options from user
        if (typeof options !== 'object' || options === null)
            options = {};

        //allow setting options with data attributes
        //data-api options will be overwritten with custom javascript options
        options = $.extend(this.element.data(), options);

        //html template for the picker UI
        if (typeof options.template !== 'string')
            options.template = '<div class="daterangepicker dropdown-menu">' +
            '<div class="calendar left">' +
            '<div class="daterangepicker_input">' +
            '<input class="input-mini" type="text" name="daterangepicker_start" value="" />' +
            '<i class="fa fa-calendar glyphicon glyphicon-calendar"></i>' +
            '<div class="calendar-time">' +
            '<div></div>' +
            '<i class="fa fa-clock-o glyphicon glyphicon-time"></i>' +
            '</div>' +
            '</div>' +
            '<div class="calendar-table"></div>' +
            '</div>' +
            '<div class="calendar right">' +
            '<div class="daterangepicker_input">' +
            '<input class="input-mini" type="text" name="daterangepicker_end" value="" />' +
            '<i class="fa fa-calendar glyphicon glyphicon-calendar"></i>' +
            '<div class="calendar-time">' +
            '<div></div>' +
            '<i class="fa fa-clock-o glyphicon glyphicon-time"></i>' +
            '</div>' +
            '</div>' +
            '<div class="calendar-table"></div>' +
            '</div>' +
            '<div class="ranges">' +
            '<div class="range_inputs">' +
            '<button class="applyBtn" disabled="disabled" type="button"></button> ' +
            '<button class="cancelBtn" type="button"></button>' +
            '</div>' +
            '</div>' +
            '</div>';

        this.parentEl = (options.parentEl && $(options.parentEl).length) ? $(options.parentEl) : $(this.parentEl);
        this.container = $(options.template).appendTo(this.parentEl);

        //
        // handle all the possible options overriding defaults
        //

        if (typeof options.locale === 'object') {

            if (typeof options.locale.format === 'string')
                this.locale.format = options.locale.format;

            if (typeof options.locale.separator === 'string')
                this.locale.separator = options.locale.separator;

            if (typeof options.locale.daysOfWeek === 'object')
                this.locale.daysOfWeek = options.locale.daysOfWeek.slice();

            if (typeof options.locale.monthNames === 'object')
                this.locale.monthNames = options.locale.monthNames.slice();

            if (typeof options.locale.firstDay === 'number')
                this.locale.firstDay = options.locale.firstDay;

            if (typeof options.locale.applyLabel === 'string')
                this.locale.applyLabel = options.locale.applyLabel;

            if (typeof options.locale.cancelLabel === 'string')
                this.locale.cancelLabel = options.locale.cancelLabel;

            if (typeof options.locale.weekLabel === 'string')
                this.locale.weekLabel = options.locale.weekLabel;

            if (typeof options.locale.customRangeLabel === 'string')
                this.locale.customRangeLabel = options.locale.customRangeLabel;

        }

        if (typeof options.startDate === 'string')
            this.startDate = moment(options.startDate, this.locale.format);

        if (typeof options.endDate === 'string')
            this.endDate = moment(options.endDate, this.locale.format);

        if (typeof options.minDate === 'string')
            this.minDate = moment(options.minDate, this.locale.format);

        if (typeof options.maxDate === 'string')
            this.maxDate = moment(options.maxDate, this.locale.format);

        if (typeof options.startDate === 'object')
            this.startDate = moment(options.startDate);

        if (typeof options.endDate === 'object')
            this.endDate = moment(options.endDate);

        if (typeof options.minDate === 'object')
            this.minDate = moment(options.minDate);

        if (typeof options.maxDate === 'object')
            this.maxDate = moment(options.maxDate);

        // sanity check for bad options
        if (this.minDate && this.startDate.isBefore(this.minDate))
            this.startDate = this.minDate.clone();

        // sanity check for bad options
        if (this.maxDate && this.endDate.isAfter(this.maxDate))
            this.endDate = this.maxDate.clone();

        if (typeof options.applyClass === 'string')
            this.applyClass = options.applyClass;

        if (typeof options.cancelClass === 'string')
            this.cancelClass = options.cancelClass;

        if (typeof options.dateLimit === 'object')
            this.dateLimit = options.dateLimit;

        if (typeof options.opens === 'string')
            this.opens = options.opens;

        if (typeof options.drops === 'string')
            this.drops = options.drops;

        if (typeof options.showWeekNumbers === 'boolean')
            this.showWeekNumbers = options.showWeekNumbers;

        if (typeof options.buttonClasses === 'string')
            this.buttonClasses = options.buttonClasses;

        if (typeof options.buttonClasses === 'object')
            this.buttonClasses = options.buttonClasses.join(' ');

        if (typeof options.showDropdowns === 'boolean')
            this.showDropdowns = options.showDropdowns;

        if (typeof options.singleDatePicker === 'boolean') {
            this.singleDatePicker = options.singleDatePicker;
            if (this.singleDatePicker)
                this.endDate = this.startDate.clone();
        }

        if (typeof options.timePicker === 'boolean')
            this.timePicker = options.timePicker;

        if (typeof options.timePickerSeconds === 'boolean')
            this.timePickerSeconds = options.timePickerSeconds;

        if (typeof options.timePickerIncrement === 'number')
            this.timePickerIncrement = options.timePickerIncrement;

        if (typeof options.timePicker24Hour === 'boolean')
            this.timePicker24Hour = options.timePicker24Hour;

        if (typeof options.autoApply === 'boolean')
            this.autoApply = options.autoApply;

        // update day names order to firstDay
        if (this.locale.firstDay != 0) {
            var iterator = this.locale.firstDay;
            while (iterator > 0) {
                this.locale.daysOfWeek.push(this.locale.daysOfWeek.shift());
                iterator--;
            }
        }

        var start, end, range;

        //if no start/end dates set, check if an input element contains initial values
        if (typeof options.startDate === 'undefined' && typeof options.endDate === 'undefined') {
            if ($(this.element).is('input[type=text]')) {
                var val = $(this.element).val(),
                    split = val.split(this.locale.separator);

                start = end = null;

                if (split.length == 2) {
                    start = moment(split[0], this.locale.format);
                    end = moment(split[1], this.locale.format);
                } else if (this.singleDatePicker && val !== "") {
                    start = moment(val, this.locale.format);
                    end = moment(val, this.locale.format);
                }
                if (start !== null && end !== null) {
                    this.setStartDate(start);
                    this.setEndDate(end);
                }
            }
        }

        // bind the time zone used to build the calendar to either the timeZone passed in through the options or the zone of the startDate (which will be the local time zone by default)
        if (typeof options.timeZone === 'string' || typeof options.timeZone === 'number') {
            if (typeof options.timeZone === 'string' && typeof moment.tz !== 'undefined') {
                this.timeZone = moment.tz.zone(options.timeZone).parse(new Date) * -1; // Offset is positive if the timezone is behind UTC and negative if it is ahead.
            } else {
                this.timeZone = options.timeZone;
            }
            this.startDate.utcOffset(this.timeZone);
            this.endDate.utcOffset(this.timeZone);
        } else {
            this.timeZone = moment(this.startDate).utcOffset();
        }

        if (typeof options.ranges === 'object') {
            for (range in options.ranges) {

                if (typeof options.ranges[range][0] === 'string')
                    start = moment(options.ranges[range][0], this.locale.format);
                else
                    start = moment(options.ranges[range][0]);

                if (typeof options.ranges[range][1] === 'string')
                    end = moment(options.ranges[range][1], this.locale.format);
                else
                    end = moment(options.ranges[range][1]);

                // If the start or end date exceed those allowed by the minDate or dateLimit
                // options, shorten the range to the allowable period.
                if (this.minDate && start.isBefore(this.minDate))
                    start = this.minDate.clone();

                var maxDate = this.maxDate;
                if (this.dateLimit && start.clone().add(this.dateLimit).isAfter(maxDate))
                    maxDate = start.clone().add(this.dateLimit);
                if (maxDate && end.isAfter(maxDate))
                    end = maxDate.clone();

                // If the end of the range is before the minimum or the start of the range is
                // after the maximum, don't display this range option at all.
                if ((this.minDate && end.isBefore(this.minDate)) || (maxDate && start.isAfter(maxDate)))
                    continue;

                this.ranges[range] = [start, end];
            }

            var list = '<ul>';
            for (range in this.ranges) {
                list += '<li>' + range + '</li>';
            }
            list += '<li>' + this.locale.customRangeLabel + '</li>';
            list += '</ul>';
            this.container.find('.ranges ul').remove();
            this.container.find('.ranges').prepend(list);
        }

        if (typeof cb === 'function') {
            this.callback = cb;
        }

        if (!this.timePicker) {
            this.startDate = this.startDate.startOf('day');
            this.endDate = this.endDate.endOf('day');
            this.container.find('.calendar-time').hide();
        }

        //can't be used together for now
        if (this.timePicker && this.autoApply)
            this.autoApply = false;

        if (this.autoApply && typeof options.ranges !== 'object') {
            this.container.find('.ranges').hide();
        } else if (this.autoApply) {
            this.container.find('.applyBtn, .cancelBtn').addClass('hide');
        }

        if (this.singleDatePicker) {
            this.container.addClass('single');
            this.container.find('.calendar.left').addClass('single');
            this.container.find('.calendar.left').show();
            this.container.find('.calendar.right').hide();
            this.container.find('.daterangepicker_input input, .daterangepicker_input i').hide();
            if (!this.timePicker) {
                this.container.find('.ranges').hide();
            }
        }

        if (typeof options.ranges === 'undefined' && !this.singleDatePicker) {
            this.container.addClass('show-calendar');
        }

        this.container.removeClass('opensleft opensright').addClass('opens' + this.opens);

        //apply CSS classes and labels to buttons
        this.container.find('.applyBtn, .cancelBtn').addClass(this.buttonClasses);
        if (this.applyClass.length)
            this.container.find('.applyBtn').addClass(this.applyClass);
        if (this.cancelClass.length)
            this.container.find('.cancelBtn').addClass(this.cancelClass);
        this.container.find('.applyBtn').html(this.locale.applyLabel);
        this.container.find('.cancelBtn').html(this.locale.cancelLabel);

        //
        // event listeners
        //

        this.container.find('.calendar')
            .on('click.daterangepicker', '.prev', $.proxy(this.clickPrev, this))
            .on('click.daterangepicker', '.next', $.proxy(this.clickNext, this))
            .on('click.daterangepicker', 'td.available', $.proxy(this.clickDate, this))
            .on('mouseenter.daterangepicker', 'td.available', $.proxy(this.hoverDate, this))
            .on('mouseleave.daterangepicker', 'td.available', $.proxy(this.updateFormInputs, this))
            .on('change.daterangepicker', 'select.yearselect', $.proxy(this.monthOrYearChanged, this))
            .on('change.daterangepicker', 'select.monthselect', $.proxy(this.monthOrYearChanged, this))
            .on('change.daterangepicker', 'select.hourselect,select.minuteselect,select.secondselect,select.ampmselect', $.proxy(this.timeChanged, this))
            .on('click.daterangepicker', '.daterangepicker_input input', $.proxy(this.showCalendars, this))
            .on('keyup.daterangepicker', '.daterangepicker_input input', $.proxy(this.formInputsChanged, this))
            .on('change.daterangepicker', '.daterangepicker_input input', $.proxy(this.updateFormInputs, this));

        this.container.find('.ranges')
            .on('click.daterangepicker', 'button.applyBtn', $.proxy(this.clickApply, this))
            .on('click.daterangepicker', 'button.cancelBtn', $.proxy(this.clickCancel, this))
            .on('click.daterangepicker', 'li', $.proxy(this.clickRange, this))
            .on('mouseenter.daterangepicker', 'li', $.proxy(this.hoverRange, this))
            .on('mouseleave.daterangepicker', 'li', $.proxy(this.updateFormInputs, this));

        if (this.element.is('input')) {
            this.element.on({
                'click.daterangepicker': $.proxy(this.show, this),
                'focus.daterangepicker': $.proxy(this.show, this),
                'keyup.daterangepicker': $.proxy(this.controlChanged, this),
                'keydown.daterangepicker': $.proxy(this.keydown, this)
            });
        } else {
            this.element.on('click.daterangepicker', $.proxy(this.toggle, this));
        }

        //
        // if attached to a text input, set the initial value
        //

        if (this.element.is('input') && !this.singleDatePicker) {
            this.element.val(this.startDate.format(this.locale.format) + this.locale.separator + this.endDate.format(this.locale.format));
            this.element.trigger('change');
        } else if (this.element.is('input')) {
            this.element.val(this.startDate.format(this.locale.format));
            this.element.trigger('change');
        }

    };

    DateRangePicker.prototype = {

        constructor: DateRangePicker,

        setStartDate: function(startDate) {
            if (typeof startDate === 'string')
                this.startDate = moment(startDate, this.locale.format).utcOffset(this.timeZone);

            if (typeof startDate === 'object')
                this.startDate = moment(startDate);

            if (!this.timePicker)
                this.startDate = this.startDate.startOf('day');

            if (this.timePicker && this.timePickerIncrement)
                this.startDate.minute(Math.round(this.startDate.minute() / this.timePickerIncrement) * this.timePickerIncrement);

            if (this.minDate && this.startDate.isBefore(this.minDate))
                this.startDate = this.minDate;

            if (this.maxDate && this.startDate.isAfter(this.maxDate))
                this.startDate = this.maxDate;

            this.updateMonthsInView();
        },

        setEndDate: function(endDate) {
            if (typeof endDate === 'string')
                this.endDate = moment(endDate, this.locale.format).utcOffset(this.timeZone);

            if (typeof endDate === 'object')
                this.endDate = moment(endDate);

            if (!this.timePicker)
                this.endDate = this.endDate.endOf('day');

            if (this.timePicker && this.timePickerIncrement)
                this.endDate.minute(Math.round(this.endDate.minute() / this.timePickerIncrement) * this.timePickerIncrement);

            if (this.endDate.isBefore(this.startDate))
                this.endDate = this.startDate.clone();

            if (this.maxDate && this.endDate.isAfter(this.maxDate))
                this.endDate = this.maxDate;

            if (this.dateLimit && this.startDate.clone().add(this.dateLimit).isBefore(this.endDate))
                this.endDate = this.startDate.clone().add(this.dateLimit);

            this.updateMonthsInView();
        },

        updateView: function() {
            if (this.timePicker) {
                this.renderTimePicker('left');
                this.renderTimePicker('right');
                if (!this.endDate) {
                    this.container.find('.right .calendar-time select').attr('disabled', 'disabled').addClass('disabled');
                } else {
                    this.container.find('.right .calendar-time select').removeAttr('disabled').removeClass('disabled');
                }
            }
            if (this.endDate) {
                this.container.find('input[name="daterangepicker_end"]').removeClass('active');
                this.container.find('input[name="daterangepicker_start"]').addClass('active');
            } else {
                this.container.find('input[name="daterangepicker_end"]').addClass('active');
                this.container.find('input[name="daterangepicker_start"]').removeClass('active');
            }
            this.updateMonthsInView();
            this.updateCalendars();
            this.updateFormInputs();
        },

        updateMonthsInView: function() {
            if (this.endDate) {
                this.leftCalendar.month = this.startDate.clone().date(2);
                if (this.endDate.month() != this.startDate.month() || this.endDate.year() != this.startDate.year()) {
                    this.rightCalendar.month = this.endDate.clone().date(2);
                } else {
                    this.rightCalendar.month = this.startDate.clone().date(2).add(1, 'month');
                }
            } else {
                if (this.leftCalendar.month.format('YYYY-MM') != this.startDate.format('YYYY-MM') && this.rightCalendar.month.format('YYYY-MM') != this.startDate.format('YYYY-MM')) {
                    this.leftCalendar.month = this.startDate.clone().date(2);
                    this.rightCalendar.month = this.startDate.clone().date(2).add(1, 'month');
                }
            }
        },

        updateCalendars: function() {

            if (this.timePicker) {
                var hour, minute, second;
                if (this.endDate) {
                    hour = parseInt(this.container.find('.left .hourselect').val(), 10);
                    minute = parseInt(this.container.find('.left .minuteselect').val(), 10);
                    second = this.timePickerSeconds ? parseInt(this.container.find('.left .secondselect').val(), 10) : 0;
                    if (!this.timePicker24Hour) {
                        var ampm = this.container.find('.left .ampmselect').val();
                        if (ampm === 'PM' && hour < 12)
                            hour += 12;
                        if (ampm === 'AM' && hour === 12)
                            hour = 0;
                    }
                } else {
                    hour = parseInt(this.container.find('.right .hourselect').val(), 10);
                    minute = parseInt(this.container.find('.right .minuteselect').val(), 10);
                    second = this.timePickerSeconds ? parseInt(this.container.find('.right .secondselect').val(), 10) : 0;
                    if (!this.timePicker24Hour) {
                        var ampm = this.container.find('.left .ampmselect').val();
                        if (ampm === 'PM' && hour < 12)
                            hour += 12;
                        if (ampm === 'AM' && hour === 12)
                            hour = 0;
                    }
                }
                this.leftCalendar.month.hour(hour).minute(minute).second(second);
                this.rightCalendar.month.hour(hour).minute(minute).second(second);
            }

            this.renderCalendar('left');
            this.renderCalendar('right');

            //highlight any predefined range matching the current start and end dates
            this.container.find('.ranges li').removeClass('active');
            if (this.endDate == null) return;

            var customRange = true;
            var i = 0;
            for (var range in this.ranges) {
                if (this.timePicker) {
                    if (this.startDate.isSame(this.ranges[range][0]) && this.endDate.isSame(this.ranges[range][1])) {
                        customRange = false;
                        this.chosenLabel = this.container.find('.ranges li:eq(' + i + ')').addClass('active').html();
                        break;
                    }
                } else {
                    //ignore times when comparing dates if time picker is not enabled
                    if (this.startDate.format('YYYY-MM-DD') == this.ranges[range][0].format('YYYY-MM-DD') && this.endDate.format('YYYY-MM-DD') == this.ranges[range][1].format('YYYY-MM-DD')) {
                        customRange = false;
                        this.chosenLabel = this.container.find('.ranges li:eq(' + i + ')').addClass('active').html();
                        break;
                    }
                }
                i++;
            }
            if (customRange) {
                this.chosenLabel = this.container.find('.ranges li:last').addClass('active').html();
                this.showCalendars();
            }

        },

        renderCalendar: function(side) {

            //
            // Build the matrix of dates that will populate the calendar
            //

            var calendar = side == 'left' ? this.leftCalendar : this.rightCalendar;
            var month = calendar.month.month();
            var year = calendar.month.year();
            var hour = calendar.month.hour();
            var minute = calendar.month.minute();
            var second = calendar.month.second();
            var daysInMonth = moment([year, month]).daysInMonth();
            var firstDay = moment([year, month, 1]);
            var lastDay = moment([year, month, daysInMonth]);
            var lastMonth = moment(firstDay).subtract(1, 'month').month();
            var lastYear = moment(firstDay).subtract(1, 'month').year();
            var daysInLastMonth = moment([lastYear, lastMonth]).daysInMonth();
            var dayOfWeek = firstDay.day();

            //initialize a 6 rows x 7 columns array for the calendar
            var calendar = [];
            calendar.firstDay = firstDay;
            calendar.lastDay = lastDay;

            for (var i = 0; i < 6; i++) {
                calendar[i] = [];
            }

            //populate the calendar with date objects
            var startDay = daysInLastMonth - dayOfWeek + this.locale.firstDay + 1;
            if (startDay > daysInLastMonth)
                startDay -= 7;

            if (dayOfWeek == this.locale.firstDay)
                startDay = daysInLastMonth - 6;

            // Possible patch for issue #626 https://github.com/dangrossman/bootstrap-daterangepicker/issues/626
            var curDate = moment([lastYear, lastMonth, startDay, 12, minute, second]); // .utcOffset(this.timeZone);

            var col, row;
            for (var i = 0, col = 0, row = 0; i < 42; i++, col++, curDate = moment(curDate).add(24, 'hour')) {
                if (i > 0 && col % 7 === 0) {
                    col = 0;
                    row++;
                }
                calendar[row][col] = curDate.clone().hour(hour).minute(minute).second(second);
                curDate.hour(12);

                if (this.minDate && calendar[row][col].format('YYYY-MM-DD') == this.minDate.format('YYYY-MM-DD') && calendar[row][col].isBefore(this.minDate) && side == 'left') {
                    calendar[row][col] = this.minDate.clone();
                }

                if (this.maxDate && calendar[row][col].format('YYYY-MM-DD') == this.maxDate.format('YYYY-MM-DD') && calendar[row][col].isAfter(this.maxDate) && side == 'right') {
                    calendar[row][col] = this.maxDate.clone();
                }

            }

            //make the calendar object available to hoverDate/clickDate
            if (side == 'left') {
                this.leftCalendar.calendar = calendar;
            } else {
                this.rightCalendar.calendar = calendar;
            }

            //
            // Display the calendar
            //

            var minDate = side == 'left' ? this.minDate : this.startDate;
            var maxDate = this.maxDate;
            var selected = side == 'left' ? this.startDate : this.endDate;

            var html = '<table class="table-condensed">';
            html += '<thead>';
            html += '<tr>';

            // add empty cell for week number
            if (this.showWeekNumbers)
                html += '<th></th>';

            if (!minDate || minDate.isBefore(calendar.firstDay)) {
                html += '<th class="prev available"><i class="fa fa-chevron-left glyphicon glyphicon-chevron-left"></i></th>';
            } else {
                html += '<th></th>';
            }

            var dateHtml = this.locale.monthNames[calendar[1][1].month()] + calendar[1][1].format(" YYYY");

            if (this.showDropdowns) {
                var currentMonth = calendar[1][1].month();
                var currentYear = calendar[1][1].year();
                var maxYear = (maxDate && maxDate.year()) || (currentYear + 5);
                var minYear = (minDate && minDate.year()) || (currentYear - 50);
                var inMinYear = currentYear == minYear;
                var inMaxYear = currentYear == maxYear;

                var monthHtml = '<select class="monthselect">';
                for (var m = 0; m < 12; m++) {
                    if ((!inMinYear || m >= minDate.month()) && (!inMaxYear || m <= maxDate.month())) {
                        monthHtml += "<option value='" + m + "'" +
                            (m === currentMonth ? " selected='selected'" : "") +
                            ">" + this.locale.monthNames[m] + "</option>";
                    }
                }
                monthHtml += "</select>";

                var yearHtml = '<select class="yearselect">';
                for (var y = minYear; y <= maxYear; y++) {
                    yearHtml += '<option value="' + y + '"' +
                        (y === currentYear ? ' selected="selected"' : '') +
                        '>' + y + '</option>';
                }
                yearHtml += '</select>';

                dateHtml = monthHtml + yearHtml;
            }

            html += '<th colspan="5" class="month">' + dateHtml + '</th>';
            if (!maxDate || maxDate.isAfter(calendar.lastDay)) {
                html += '<th class="next available"><i class="fa fa-chevron-right glyphicon glyphicon-chevron-right"></i></th>';
            } else {
                html += '<th></th>';
            }

            html += '</tr>';
            html += '<tr>';

            // add week number label
            if (this.showWeekNumbers)
                html += '<th class="week">' + this.locale.weekLabel + '</th>';

            $.each(this.locale.daysOfWeek, function(index, dayOfWeek) {
                html += '<th>' + dayOfWeek + '</th>';
            });

            html += '</tr>';
            html += '</thead>';
            html += '<tbody>';

            //adjust maxDate to reflect the dateLimit setting in order to
            //grey out end dates beyond the dateLimit
            if (this.endDate == null && this.dateLimit) {
                var maxLimit = this.startDate.clone().add(this.dateLimit).endOf('day');
                if (!maxDate || maxLimit.isBefore(maxDate)) {
                    maxDate = maxLimit;
                }
            }

            for (var row = 0; row < 6; row++) {
                html += '<tr>';

                // add week number
                if (this.showWeekNumbers)
                    html += '<td class="week">' + calendar[row][0].week() + '</td>';

                for (var col = 0; col < 7; col++) {

                    var classes = [];

                    //highlight today's date
                    if (calendar[row][col].isSame(new Date(), "day"))
                        classes.push('today');

                    //highlight weekends
                    if (calendar[row][col].isoWeekday() > 5)
                        classes.push('weekend');

                    //grey out the dates in other months displayed at beginning and end of this calendar
                    if (calendar[row][col].month() != calendar[1][1].month())
                        classes.push('off');

                    //don't allow selection of dates before the minimum date
                    if (this.minDate && calendar[row][col].isBefore(this.minDate, 'day'))
                        classes.push('off', 'disabled');

                    //don't allow selection of dates after the maximum date
                    if (maxDate && calendar[row][col].isAfter(maxDate, 'day'))
                        classes.push('off', 'disabled');

                    //highlight the currently selected start date
                    if (calendar[row][col].format('YYYY-MM-DD') == this.startDate.format('YYYY-MM-DD'))
                        classes.push('active', 'start-date');

                    //highlight the currently selected end date
                    if (this.endDate != null && calendar[row][col].format('YYYY-MM-DD') == this.endDate.format('YYYY-MM-DD'))
                        classes.push('active', 'end-date');

                    //highlight dates in-between the selected dates
                    if (this.endDate != null && calendar[row][col] > this.startDate && calendar[row][col] < this.endDate)
                        classes.push('in-range');

                    var cname = '',
                        disabled = false;
                    for (var i = 0; i < classes.length; i++) {
                        cname += classes[i] + ' ';
                        if (classes[i] == 'disabled')
                            disabled = true;
                    }
                    if (!disabled)
                        cname += 'available';

                    html += '<td class="' + cname.replace(/^\s+|\s+$/g, '') + '" data-title="' + 'r' + row + 'c' + col + '">' + calendar[row][col].date() + '</td>';

                }
                html += '</tr>';
            }

            html += '</tbody>';
            html += '</table>';

            this.container.find('.calendar.' + side + ' .calendar-table').html(html);

        },

        renderTimePicker: function(side) {

            var selected, minDate, maxDate = this.maxDate;

            if (this.dateLimit && (!this.maxDate || this.startDate.clone().add(this.dateLimit).isAfter(this.maxDate)))
                maxDate = this.startDate.clone().add(this.dateLimit);

            if (side == 'left') {
                selected = this.startDate.clone();
                minDate = this.minDate;
            } else if (side == 'right') {
                selected = this.endDate ? this.endDate.clone() : this.startDate.clone();
                minDate = this.startDate;
            }

            //
            // hours
            //

            html = '<select class="hourselect">';

            var start = this.timePicker24Hour ? 0 : 1;
            var end = this.timePicker24Hour ? 23 : 12;

            for (var i = start; i <= end; i++) {
                var i_in_24 = i;
                if (!this.timePicker24Hour)
                    i_in_24 = selected.hour() >= 12 ? (i == 12 ? 12 : i + 12) : (i == 12 ? 0 : i);

                var time = selected.clone().hour(i_in_24);
                var disabled = false;
                if (minDate && time.minute(59).isBefore(minDate))
                    disabled = true;
                if (maxDate && time.minute(0).isAfter(maxDate))
                    disabled = true;

                if (i_in_24 == selected.hour() && !disabled) {
                    html += '<option value="' + i + '" selected="selected">' + i + '</option>';
                } else if (disabled) {
                    html += '<option value="' + i + '" disabled="disabled" class="disabled">' + i + '</option>';
                } else {
                    html += '<option value="' + i + '">' + i + '</option>';
                }
            }

            html += '</select> ';

            //
            // minutes
            //

            html += ': <select class="minuteselect">';

            for (var i = 0; i < 60; i += this.timePickerIncrement) {
                var padded = i < 10 ? '0' + i : i;
                var time = selected.clone().minute(i);

                var disabled = false;
                if (minDate && time.second(59).isBefore(minDate))
                    disabled = true;
                if (maxDate && time.second(0).isAfter(maxDate))
                    disabled = true;

                if (selected.minute() == i && !disabled) {
                    html += '<option value="' + i + '" selected="selected">' + padded + '</option>';
                } else if (disabled) {
                    html += '<option value="' + i + '" disabled="disabled" class="disabled">' + padded + '</option>';
                } else {
                    html += '<option value="' + i + '">' + padded + '</option>';
                }
            }

            html += '</select> ';

            //
            // seconds
            //

            if (this.timePickerSeconds) {
                html += ': <select class="secondselect">';

                for (var i = 0; i < 60; i++) {
                    var padded = i < 10 ? '0' + i : i;
                    var time = selected.clone().second(i);

                    var disabled = false;
                    if (minDate && time.isBefore(minDate))
                        disabled = true;
                    if (maxDate && time.isAfter(maxDate))
                        disabled = true;

                    if (selected.second() == i && !disabled) {
                        html += '<option value="' + i + '" selected="selected">' + padded + '</option>';
                    } else if (disabled) {
                        html += '<option value="' + i + '" disabled="disabled" class="disabled">' + padded + '</option>';
                    } else {
                        html += '<option value="' + i + '">' + padded + '</option>';
                    }
                }

                html += '</select> ';
            }

            //
            // AM/PM
            //

            if (!this.timePicker24Hour) {
                html += '<select class="ampmselect">';

                var am_html = '';
                var pm_html = '';

                if (minDate && selected.clone().hour(12).minute(0).second(0).isBefore(minDate))
                    am_html = ' disabled="disabled" class="disabled"';

                if (maxDate && selected.clone().hour(0).minute(0).second(0).isAfter(maxDate))
                    pm_html = ' disabled="disabled" class="disabled"';

                if (selected.hour() >= 12) {
                    html += '<option value="AM"' + am_html + '>AM</option><option value="PM" selected="selected"' + pm_html + '>PM</option>';
                } else {
                    html += '<option value="AM" selected="selected"' + am_html + '>AM</option><option value="PM"' + pm_html + '>PM</option>';
                }

                html += '</select>';
            }

            this.container.find('.calendar.' + side + ' .calendar-time div').html(html);

        },

        updateFormInputs: function() {
            this.container.find('input[name=daterangepicker_start]').val(this.startDate.format(this.locale.format));
            if (this.endDate)
                this.container.find('input[name=daterangepicker_end]').val(this.endDate.format(this.locale.format));

            if (this.singleDatePicker || (this.endDate && (this.startDate.isBefore(this.endDate) || this.startDate.isSame(this.endDate)))) {
                this.container.find('button.applyBtn').removeAttr('disabled');
            } else {
                this.container.find('button.applyBtn').attr('disabled', 'disabled');
            }
        },

        move: function() {
            var parentOffset = {
                    top: 0,
                    left: 0
                },
                containerTop;
            var parentRightEdge = $(window).width();
            if (!this.parentEl.is('body')) {
                parentOffset = {
                    top: this.parentEl.offset().top - this.parentEl.scrollTop(),
                    left: this.parentEl.offset().left - this.parentEl.scrollLeft()
                };
                parentRightEdge = this.parentEl[0].clientWidth + this.parentEl.offset().left;
            }

            if (this.drops == 'up')
                containerTop = this.element.offset().top - this.container.outerHeight() - parentOffset.top;
            else
                containerTop = this.element.offset().top + this.element.outerHeight() - parentOffset.top;
            this.container[this.drops == 'up' ? 'addClass' : 'removeClass']('dropup');

            if (this.opens == 'left') {
                this.container.css({
                    top: containerTop,
                    right: parentRightEdge - this.element.offset().left - this.element.outerWidth(),
                    left: 'auto'
                });
                if (this.container.offset().left < 0) {
                    this.container.css({
                        right: 'auto',
                        left: 9
                    });
                }
            } else if (this.opens == 'center') {
                this.container.css({
                    top: containerTop,
                    left: this.element.offset().left - parentOffset.left + this.element.outerWidth() / 2 - this.container.outerWidth() / 2,
                    right: 'auto'
                });
                if (this.container.offset().left < 0) {
                    this.container.css({
                        right: 'auto',
                        left: 9
                    });
                }
            } else {
                this.container.css({
                    top: containerTop,
                    left: this.element.offset().left - parentOffset.left,
                    right: 'auto'
                });
                if (this.container.offset().left + this.container.outerWidth() > $(window).width()) {
                    this.container.css({
                        left: 'auto',
                        right: 0
                    });
                }
            }
        },

        show: function(e) {
            if (this.isShowing) return;

            // Create a click proxy that is private to this instance of datepicker, for unbinding
            this._outsideClickProxy = $.proxy(function(e) {
                this.outsideClick(e);
            }, this);
            // Bind global datepicker mousedown for hiding and
            $(document)
                .on('mousedown.daterangepicker', this._outsideClickProxy)
                // also support mobile devices
                .on('touchend.daterangepicker', this._outsideClickProxy)
                // also explicitly play nice with Bootstrap dropdowns, which stopPropagation when clicking them
                .on('click.daterangepicker', '[data-toggle=dropdown]', this._outsideClickProxy)
                // and also close when focus changes to outside the picker (eg. tabbing between controls)
                .on('focusin.daterangepicker', this._outsideClickProxy);

            this.oldStartDate = this.startDate.clone();
            this.oldEndDate = this.endDate.clone();

            this.updateView();
            this.container.show();
            this.move();
            this.element.trigger('show.daterangepicker', this);
            this.isShowing = true;
        },

        hide: function(e) {
            if (!this.isShowing) return;

            //incomplete date selection, revert to last values
            if (!this.endDate) {
                this.startDate = this.oldStartDate.clone();
                this.endDate = this.oldEndDate.clone();
            }

            //if a new date range was selected, invoke the user callback function
            if (!this.startDate.isSame(this.oldStartDate) || !this.endDate.isSame(this.oldEndDate))
                this.callback(this.startDate, this.endDate, this.chosenLabel);

            //if picker is attached to a text input, update it
            if (this.element.is('input') && !this.singleDatePicker) {
                this.element.val(this.startDate.format(this.locale.format) + this.locale.separator + this.endDate.format(this.locale.format));
                this.element.trigger('change');
            } else if (this.element.is('input')) {
                this.element.val(this.startDate.format(this.locale.format));
                this.element.trigger('change');
            }

            $(document).off('.daterangepicker');
            this.container.hide();
            this.element.trigger('hide.daterangepicker', this);
            this.isShowing = false;
        },

        toggle: function(e) {
            if (this.isShowing) {
                this.hide();
            } else {
                this.show();
            }
        },

        outsideClick: function(e) {
            var target = $(e.target);
            // if the page is clicked anywhere except within the daterangerpicker/button
            // itself then call this.hide()
            if (
                // ie modal dialog fix
                e.type == "focusin" ||
                target.closest(this.element).length ||
                target.closest(this.container).length ||
                target.closest('.calendar-table').length
            ) return;
            this.hide();
        },

        showCalendars: function() {
            this.container.addClass('show-calendar');
            this.move();
            this.element.trigger('showCalendar.daterangepicker', this);
        },

        hideCalendars: function() {
            this.container.removeClass('show-calendar');
            this.element.trigger('hideCalendar.daterangepicker', this);
        },

        hoverRange: function(e) {
            var label = e.target.innerHTML;
            if (label == this.locale.customRangeLabel) {
                this.updateView();
            } else {
                var dates = this.ranges[label];
                this.container.find('input[name=daterangepicker_start]').val(dates[0].format(this.locale.format));
                this.container.find('input[name=daterangepicker_end]').val(dates[1].format(this.locale.format));
            }
        },

        clickRange: function(e) {
            var label = e.target.innerHTML;
            this.chosenLabel = label;
            if (label == this.locale.customRangeLabel) {
                this.showCalendars();
            } else {
                var dates = this.ranges[label];
                this.startDate = dates[0];
                this.endDate = dates[1];

                if (!this.timePicker) {
                    this.startDate.startOf('day');
                    this.endDate.endOf('day');
                }

                this.hideCalendars();
                this.clickApply();
            }
        },

        clickPrev: function(e) {
            var cal = $(e.target).parents('.calendar');
            if (cal.hasClass('left')) {
                this.leftCalendar.month.subtract(1, 'month');
            } else {
                this.rightCalendar.month.subtract(1, 'month');
            }
            this.updateCalendars();
        },

        clickNext: function(e) {
            var cal = $(e.target).parents('.calendar');
            if (cal.hasClass('left')) {
                this.leftCalendar.month.add(1, 'month');
            } else {
                this.rightCalendar.month.add(1, 'month');
            }
            this.updateCalendars();
        },

        hoverDate: function(e) {

            //ignore dates that can't be selected
            if (!$(e.target).hasClass('available')) return;

            //have the text inputs above calendars reflect the date being hovered over
            var title = $(e.target).attr('data-title');
            var row = title.substr(1, 1);
            var col = title.substr(3, 1);
            var cal = $(e.target).parents('.calendar');
            var date = cal.hasClass('left') ? this.leftCalendar.calendar[row][col] : this.rightCalendar.calendar[row][col];

            if (this.endDate) {
                this.container.find('input[name=daterangepicker_start]').val(date.format(this.locale.format));
            } else {
                this.container.find('input[name=daterangepicker_end]').val(date.format(this.locale.format));
            }

            //highlight the dates between the start date and the date being hovered as a potential end date
            var leftCalendar = this.leftCalendar;
            var rightCalendar = this.rightCalendar;
            var startDate = this.startDate;
            if (!this.endDate) {
                this.container.find('.calendar td').each(function(index, el) {
                    var title = $(el).attr('data-title');
                    var row = title.substr(1, 1);
                    var col = title.substr(3, 1);
                    var cal = $(el).parents('.calendar');
                    var dt = cal.hasClass('left') ? leftCalendar.calendar[row][col] : rightCalendar.calendar[row][col];
                    if (dt.isAfter(startDate) && dt.isBefore(date)) {
                        $(el).addClass('in-range');
                    } else {
                        $(el).removeClass('in-range');
                    }
                });
            }

        },

        clickDate: function(e) {

            if (!$(e.target).hasClass('available')) return;

            var title = $(e.target).attr('data-title');
            var row = title.substr(1, 1);
            var col = title.substr(3, 1);
            var cal = $(e.target).parents('.calendar');
            var date = cal.hasClass('left') ? this.leftCalendar.calendar[row][col] : this.rightCalendar.calendar[row][col];

            //
            // this function needs to do a few things:
            // * alternate between selecting a start and end date for the range,
            // * if the time picker is enabled, apply the hour/minute/second from the select boxes to the clicked date
            // * if autoapply is enabled, and an end date was chosen, apply the selection
            // * if single date picker mode, and time picker isn't enabled, apply the selection immediately
            //

            if (this.endDate || date.isBefore(this.startDate)) {
                if (this.timePicker) {
                    var hour = parseInt(this.container.find('.left .hourselect').val(), 10);
                    if (!this.timePicker24Hour) {
                        var ampm = cal.find('.ampmselect').val();
                        if (ampm === 'PM' && hour < 12)
                            hour += 12;
                        if (ampm === 'AM' && hour === 12)
                            hour = 0;
                    }
                    var minute = parseInt(this.container.find('.left .minuteselect').val(), 10);
                    var second = this.timePickerSeconds ? parseInt(this.container.find('.left .secondselect').val(), 10) : 0;
                    date = date.clone().hour(hour).minute(minute).second(second);
                }
                this.endDate = null;
                this.setStartDate(date.clone());
            } else {
                if (this.timePicker) {
                    var hour = parseInt(this.container.find('.right .hourselect').val(), 10);
                    if (!this.timePicker24Hour) {
                        var ampm = this.container.find('.right .ampmselect').val();
                        if (ampm === 'PM' && hour < 12)
                            hour += 12;
                        if (ampm === 'AM' && hour === 12)
                            hour = 0;
                    }
                    var minute = parseInt(this.container.find('.right .minuteselect').val(), 10);
                    var second = this.timePickerSeconds ? parseInt(this.container.find('.right .secondselect').val(), 10) : 0;
                    date = date.clone().hour(hour).minute(minute).second(second);
                }
                this.setEndDate(date.clone());
                if (this.autoApply)
                    this.clickApply();
            }

            if (this.singleDatePicker) {
                this.setEndDate(this.startDate);
                if (!this.timePicker)
                    this.clickApply();
            }

            this.updateView();

        },

        clickApply: function(e) {
            this.hide();
            this.element.trigger('apply.daterangepicker', this);
        },

        clickCancel: function(e) {
            this.startDate = this.oldStartDate;
            this.endDate = this.oldEndDate;
            this.hide();
            this.element.trigger('cancel.daterangepicker', this);
        },

        monthOrYearChanged: function(e) {
            var isLeft = $(e.target).closest('.calendar').hasClass('left'),
                leftOrRight = isLeft ? 'left' : 'right',
                cal = this.container.find('.calendar.' + leftOrRight);

            // Month must be Number for new moment versions
            var month = parseInt(cal.find('.monthselect').val(), 10);
            var year = cal.find('.yearselect').val();

            if (!isLeft) {
                if (year < this.startDate.year() || (year == this.startDate.year() && month < this.startDate.month())) {
                    month = this.startDate.month();
                    year = this.startDate.year();
                }
            }

            if (this.minDate) {
                if (year < this.minDate.year() || (year == this.minDate.year() && month < this.minDate.month())) {
                    month = this.minDate.month();
                    year = this.minDate.year();
                }
            }

            if (this.maxDate) {
                if (year > this.maxDate.year() || (year == this.maxDate.year() && month > this.maxDate.month())) {
                    month = this.maxDate.month();
                    year = this.maxDate.year();
                }
            }

            this[leftOrRight + 'Calendar'].month.month(month).year(year);
            this.updateCalendars();
        },

        timeChanged: function(e) {

            var cal = $(e.target).closest('.calendar'),
                isLeft = cal.hasClass('left');

            var hour = parseInt(cal.find('.hourselect').val(), 10);
            var minute = parseInt(cal.find('.minuteselect').val(), 10);
            var second = this.timePickerSeconds ? parseInt(cal.find('.secondselect').val(), 10) : 0;

            if (!this.timePicker24Hour) {
                var ampm = cal.find('.ampmselect').val();
                if (ampm === 'PM' && hour < 12)
                    hour += 12;
                if (ampm === 'AM' && hour === 12)
                    hour = 0;
            }

            if (isLeft) {
                var start = this.startDate.clone();
                start.hour(hour);
                start.minute(minute);
                start.second(second);
                this.setStartDate(start);
                if (this.singleDatePicker)
                    this.endDate = this.startDate.clone();
            } else if (this.endDate) {
                var end = this.endDate.clone();
                end.hour(hour);
                end.minute(minute);
                end.second(second);
                this.setEndDate(end);
            }

            //update the calendars so all clickable dates reflect the new time component
            this.updateCalendars();

            //update the form inputs above the calendars with the new time
            this.updateFormInputs();

            //re-render the time pickers because changing one selection can affect what's enabled in another
            this.renderTimePicker('left');
            this.renderTimePicker('right');

        },

        formInputsChanged: function(e) {
            var isRight = $(e.target).closest('.calendar').hasClass('right');
            var start = moment(this.container.find('input[name="daterangepicker_start"]').val(), this.locale.format);
            var end = moment(this.container.find('input[name="daterangepicker_end"]').val(), this.locale.format);

            if (start.isValid() && end.isValid()) {

                if (isRight && end.isBefore(start))
                    start = end.clone();

                this.setStartDate(start);
                this.setEndDate(end);

                if (isRight) {
                    this.container.find('input[name="daterangepicker_start"]').val(this.startDate.format(this.locale.format));
                } else {
                    this.container.find('input[name="daterangepicker_end"]').val(this.endDate.format(this.locale.format));
                }

            }

            this.updateCalendars();
            if (this.timePicker) {
                this.renderTimePicker('left');
                this.renderTimePicker('right');
            }
        },

        controlChanged: function() {
            if (!this.element.is('input')) return;
            if (!this.element.val().length) return;

            var dateString = this.element.val().split(this.locale.separator),
                start = null,
                end = null;

            if (dateString.length === 2) {
                start = moment(dateString[0], this.locale.format).utcOffset(this.timeZone);
                end = moment(dateString[1], this.locale.format).utcOffset(this.timeZone);
            }

            if (this.singleDatePicker || start === null || end === null) {
                start = moment(this.element.val(), this.locale.format).utcOffset(this.timeZone);
                end = start;
            }

            this.setStartDate(start);
            this.setEndDate(end);
            this.updateView();
        },

        keydown: function(e) {
            //hide on tab or enter
            if ((e.keyCode === 9) || (e.keyCode === 13)) {
                this.hide();
            }
        },

        remove: function() {
            this.container.remove();
            this.element.off('.daterangepicker');
            this.element.removeData();
        }

    };

    $.fn.daterangepicker = function(options, callback) {
        this.each(function() {
            var el = $(this);
            if (el.data('daterangepicker'))
                el.data('daterangepicker').remove();
            el.data('daterangepicker', new DateRangePicker(el, options, callback));
        });
        return this;
    };

}));
/*!
 * @copyright &copy; Kartik Visweswaran, Krajee.com, 2013 - 2015
 * @version 3.5.4
 *
 * A simple yet powerful JQuery star rating plugin that allows rendering
 * fractional star ratings and supports Right to Left (RTL) input.
 * 
 * For more JQuery plugins visit http://plugins.krajee.com
 * For more Yii related demos visit http://demos.krajee.com
 */
(function ($) {
    "use strict";
    var DEFAULT_MIN = 0, DEFAULT_MAX = 5, DEFAULT_STEP = 0.5,
        isEmpty = function (value, trim) {
            return value === null || value === undefined || value.length === 0 || (trim && $.trim(value) === '');
        },
        addCss = function ($el, css) {
            $el.removeClass(css).addClass(css);
        },
        validateAttr = function ($input, vattr, options) {
            var chk = isEmpty($input.data(vattr)) ? $input.attr(vattr) : $input.data(vattr);
            return chk ? chk : options[vattr];
        },
        getDecimalPlaces = function (num) {
            var match = ('' + num).match(/(?:\.(\d+))?(?:[eE]([+-]?\d+))?$/);
            return !match ? 0 : Math.max(0, (match[1] ? match[1].length : 0) - (match[2] ? +match[2] : 0));
        },
        applyPrecision = function (val, precision) {
            return parseFloat(val.toFixed(precision));
        },
        Rating = function (element, options) {
            this.$element = $(element);
            this.init(options);
        };

    Rating.prototype = {
        constructor: Rating,
        _parseAttr: function (vattr, options) {
            var self = this, $el = self.$element;
            if ($el.attr('type') === 'range' || $el.attr('type') === 'number') {
                var val = validateAttr($el, vattr, options), chk, final;
                switch (vattr) {
                    case 'min':
                        chk = DEFAULT_MIN;
                        break;
                    case 'max':
                        chk = DEFAULT_MAX;
                        break;
                    default:
                        chk = DEFAULT_STEP;
                }
                final = isEmpty(val) ? chk : val;
                return parseFloat(final);
            }
            return parseFloat(options[vattr]);
        },
        listenClick: function($el, callback) {
            $el.on('click touchstart', function(e) {
                e.stopPropagation();
                e.preventDefault();
                if (e.handled !== true) {
                    callback(e);
                    e.handled = true;
                } else {
                    return false;
                }
            });
        },
        setDefault: function (key, val) {
            var self = this;
            if (isEmpty(self[key])) {
                self[key] = val;
            }
        },
        getPosition: function (e) {
            var pageX = e.pageX || e.originalEvent.touches[0].pageX;
            return pageX - this.$rating.offset().left;
        },
        listen: function () {
            var self = this, pos, out;
            self.initTouch();
            self.listenClick(self.$rating, function(e) {
                if (self.inactive) {
                    return false;
                }
                pos = self.getPosition(e);
                self.setStars(pos);
                self.$element.trigger('change').trigger('rating.change', [self.$element.val(), self.$caption.html()]);
                self.starClicked = true;
            });
            self.$rating.on("mousemove", function (e) {
                if (!self.hoverEnabled || self.inactive) {
                    return;
                }
                self.starClicked = false;
                pos = self.getPosition(e);
                out = self.calculate(pos);
                self.toggleHover(out);
                self.$element.trigger('rating.hover', [out.val, out.caption, 'stars']);
            });
            self.$rating.on("mouseleave", function () {
                if (!self.hoverEnabled || self.inactive || self.starClicked) {
                    return;
                }
                out = self.cache;
                self.toggleHover(out);
                self.$element.trigger('rating.hoverleave', ['stars']);
            });
            self.$clear.on("mousemove", function () {
                if (!self.hoverEnabled || self.inactive || !self.hoverOnClear) {
                    return;
                }
                self.clearClicked = false;
                var caption = '<span class="' + self.clearCaptionClass + '">' + self.clearCaption + '</span>',
                    val = self.clearValue, width = self.getWidthFromValue(val);
                out = {caption: caption, width: width, val: val};
                self.toggleHover(out);
                self.$element.trigger('rating.hover', [val, caption, 'clear']);
            });
            self.$clear.on("mouseleave", function () {
                if (!self.hoverEnabled || self.inactive || self.clearClicked || !self.hoverOnClear) {
                    return;
                }
                out = self.cache;
                self.toggleHover(out);
                self.$element.trigger('rating.hoverleave', ['clear']);
            });
            self.listenClick(self.$clear, function () {
                if (!self.inactive) {
                    self.clear();
                    self.clearClicked = true;
                }
            });
            $(self.$element[0].form).on("reset", function () {
                if (!self.inactive) {
                    self.reset();
                }
            });
        },
        destroy: function () {
            var self = this, $el = self.$element;
            if (!isEmpty(self.$container)) {
                self.$container.before($el).remove();
            }
            $.removeData($el.get(0));
            $el.off('rating').removeClass('hide');
        },
        create: function (options) {
            var self = this, $el = self.$element;
            options = options || self.options || {};
            self.destroy();
            $el.rating(options);
        },
        setTouch: function (e, flag) {
            var self = this, isTouchCapable = 'ontouchstart' in window ||
                (window.DocumentTouch && document instanceof window.DocumentTouch);
            if (!isTouchCapable || self.inactive) {
                return;
            }
            var ev = e.originalEvent, touches = ev.touches || ev.changedTouches, pos = self.getPosition(touches[0]);
            if (flag) {
                self.setStars(pos);
                self.$element.trigger('change').trigger('rating.change', [self.$element.val(), self.$caption.html()]);
                self.starClicked = true;
            } else {
                var out = self.calculate(pos), caption = out.val <= self.clearValue ? self.fetchCaption(self.clearValue) : out.caption,
                    w = self.getWidthFromValue(self.clearValue),
                    width = out.val <= self.clearValue ? (self.rtl ? (100 - w) + '%' : w + '%') : out.width;
                self.$caption.html(caption);
                self.$stars.css('width', width);
            }
        },
        initTouch: function () {
            var self = this;
            self.$rating.on("touchstart touchmove touchend", function (e) {
                var flag = (e.type === "touchend");
                self.setTouch(e, flag);
            });
        },
        initSlider: function (options) {
            var self = this;
            if (isEmpty(self.$element.val())) {
                self.$element.val(0);
            }
            self.initialValue = self.$element.val();
            self.setDefault('min', self._parseAttr('min', options));
            self.setDefault('max', self._parseAttr('max', options));
            self.setDefault('step', self._parseAttr('step', options));
            if (isNaN(self.min) || isEmpty(self.min)) {
                self.min = DEFAULT_MIN;
            }
            if (isNaN(self.max) || isEmpty(self.max)) {
                self.max = DEFAULT_MAX;
            }
            if (isNaN(self.step) || isEmpty(self.step) || self.step === 0) {
                self.step = DEFAULT_STEP;
            }
            self.diff = self.max - self.min;
        },
        init: function (options) {
            var self = this, $el = self.$element, defaultStar, starVal, starWidth;
            self.options = options;
            $.each(options, function (key, value) {
                self[key] = value;
            });
            self.starClicked = false;
            self.clearClicked = false;
            self.initSlider(options);
            self.checkDisabled();
            self.setDefault('rtl', $el.attr('dir'));
            if (self.rtl) {
                $el.attr('dir', 'rtl');
            }
            defaultStar = (self.glyphicon) ? '\ue006' : '\u2605';
            self.setDefault('symbol', defaultStar);
            self.setDefault('clearButtonBaseClass', 'clear-rating');
            self.setDefault('clearButtonActiveClass', 'clear-rating-active');
            self.setDefault('clearValue', self.min);
            addCss($el, 'form-control hide');
            self.$clearElement = isEmpty(options.clearElement) ? null : $(options.clearElement);
            self.$captionElement = isEmpty(options.captionElement) ? null : $(options.captionElement);
            if (self.$rating === undefined && self.$container === undefined) {
                self.$rating = $(document.createElement("div")).html('<div class="rating-stars"></div>');
                self.$container = $(document.createElement("div"));
                self.$container.before(self.$rating).append(self.$rating);
                $el.before(self.$container).appendTo(self.$rating);
            }
            self.$stars = self.$rating.find('.rating-stars');
            self.generateRating();
            self.$clear = !isEmpty(self.$clearElement) ? self.$clearElement : self.$container.find('.' + self.clearButtonBaseClass);
            self.$caption = !isEmpty(self.$captionElement) ? self.$captionElement : self.$container.find(".caption");
            self.setStars();
            self.listen();
            if (self.showClear) {
                self.$clear.attr({"class": self.getClearClass()});
            }
            starVal = $el.val();
            starWidth = self.getWidthFromValue(starVal);
            self.cache = {
                caption: self.$caption.html(),
                width: (self.rtl ? (100 - starWidth) : starWidth) + '%',
                val: starVal
            };
            $el.removeClass('rating-loading');
        },
        checkDisabled: function () {
            var self = this;
            self.disabled = validateAttr(self.$element, 'disabled', self.options);
            self.readonly = validateAttr(self.$element, 'readonly', self.options);
            self.inactive = (self.disabled || self.readonly);
        },
        getClearClass: function () {
            return this.clearButtonBaseClass + ' ' + ((this.inactive) ? '' : this.clearButtonActiveClass);
        },
        generateRating: function () {
            var self = this, clear = self.renderClear(), caption = self.renderCaption(),
                css = (self.rtl) ? 'rating-container-rtl' : 'rating-container',
                stars = self.getStars();
            if (self.glyphicon) {
                css += (self.symbol === '\ue006' ? ' rating-gly-star' : ' rating-gly') + self.ratingClass;
            } else {
                css += isEmpty(self.ratingClass) ? ' rating-uni' : ' ' + self.ratingClass;
            }
            self.$rating.attr('class', css);
            self.$rating.attr('data-content', stars);
            self.$stars.attr('data-content', stars);
            css = self.rtl ? 'star-rating-rtl' : 'star-rating';
            self.$container.attr('class', css + ' rating-' + self.size);
            self.$container.removeClass('rating-active rating-disabled');
            if (self.inactive) {
                self.$container.addClass('rating-disabled');
            }
            else {
                self.$container.addClass('rating-active');
            }
            if (isEmpty(self.$caption)) {
                if (self.rtl) {
                    self.$container.prepend(caption);
                } else {
                    self.$container.append(caption);
                }
            }
            if (isEmpty(self.$clear)) {
                if (self.rtl) {
                    self.$container.append(clear);
                }
                else {
                    self.$container.prepend(clear);
                }
            }
            if (!isEmpty(self.containerClass)) {
                addCss(self.$container, self.containerClass);
            }
        },
        getStars: function () {
            var self = this, numStars = self.stars, stars = '', i;
            for (i = 1; i <= numStars; i++) {
                stars += self.symbol;
            }
            return stars;
        },
        renderClear: function () {
            var self = this, css;
            if (!self.showClear) {
                return '';
            }
            css = self.getClearClass();
            if (!isEmpty(self.$clearElement)) {
                addCss(self.$clearElement, css);
                self.$clearElement.attr({"title": self.clearButtonTitle}).html(self.clearButton);
                return '';
            }
            return '<div class="' + css + '" title="' + self.clearButtonTitle + '">' + self.clearButton + '</div>';
        },
        renderCaption: function () {
            var self = this, val = self.$element.val(), html;
            if (!self.showCaption) {
                return '';
            }
            html = self.fetchCaption(val);
            if (!isEmpty(self.$captionElement)) {
                addCss(self.$captionElement, 'caption');
                self.$captionElement.html(html);
                return '';
            }
            return '<div class="caption">' + html + '</div>';
        },
        fetchCaption: function (rating) {
            var self = this, val = parseFloat(rating), css, cap, capVal, cssVal,
                vCap = self.starCaptions, vCss = self.starCaptionClasses, caption;
            cssVal = typeof vCss === "function" ? vCss(val) : vCss[val];
            capVal = typeof vCap === "function" ? vCap(val) : vCap[val];
            cap = isEmpty(capVal) ? self.defaultCaption.replace(/\{rating\}/g, val) : capVal;
            css = isEmpty(cssVal) ? self.clearCaptionClass : cssVal;
            caption = (val === self.clearValue) ? self.clearCaption : cap;
            return '<span class="' + css + '">' + caption + '</span>';
        },
        getWidthFromValue: function (val) {
            var self = this, min = self.min, max = self.max;
            if (val <= min || min === max) {
                return 0;
            }
            if (val >= max) {
                return 100;
            }
            return (val - min) * 100 / (max - min);
        },
        getValueFromPosition: function (pos) {
            var self = this, precision = getDecimalPlaces(self.step),
                val, factor, maxWidth = self.$rating.width();
            factor = (self.diff * pos) / (maxWidth * self.step);
            factor = self.rtl ? Math.floor(factor) : Math.ceil(factor);
            val = applyPrecision(parseFloat(self.min + factor * self.step), precision);
            val = Math.max(Math.min(val, self.max), self.min);
            return self.rtl ? (self.max - val) : val;
        },
        toggleHover: function (out) {
            var self = this, w, width, caption;
            if (self.hoverChangeCaption) {
                caption = out.val <= self.clearValue ? self.fetchCaption(self.clearValue) : out.caption;
                self.$caption.html(caption);
            }
            if (self.hoverChangeStars) {
                w = self.getWidthFromValue(self.clearValue);
                width = out.val <= self.clearValue ? (self.rtl ? (100 - w) + '%' : w + '%') : out.width;
                self.$stars.css('width', width);
            }
        },
        calculate: function (pos) {
            var self = this, defaultVal = isEmpty(self.$element.val()) ? 0 : self.$element.val(),
                val = arguments.length ? self.getValueFromPosition(pos) : defaultVal,
                caption = self.fetchCaption(val), width = self.getWidthFromValue(val);
            if (self.rtl) {
                width = 100 - width;
            }
            width += '%';
            return {caption: caption, width: width, val: val};
        },
        setStars: function (pos) {
            var self = this, out = arguments.length ? self.calculate(pos) : self.calculate();
            self.$element.val(out.val);
            self.$stars.css('width', out.width);
            self.$caption.html(out.caption);
            self.cache = out;
        },
        clear: function () {
            var self = this,
                title = '<span class="' + self.clearCaptionClass + '">' + self.clearCaption + '</span>';
            self.$stars.removeClass('rated');
            if (!self.inactive) {
                self.$caption.html(title);
            }
            self.$element.val(self.clearValue);
            self.setStars();
            self.$element.trigger('rating.clear');
        },
        reset: function () {
            var self = this;
            self.$element.val(self.initialValue);
            self.setStars();
            self.$element.trigger('rating.reset');
        },
        update: function (val) {
            var self = this;
            if (!arguments.length) {
                return;
            }
            self.$element.val(val);
            self.setStars();
        },
        refresh: function (options) {
            var self = this;
            if (!arguments.length) {
                return;
            }
            self.$rating.off('rating');
            if (self.$clear !== undefined) {
                self.$clear.off();
            }
            self.init($.extend(self.options, options));
            if (self.showClear) {
                self.$clear.show();
            } else {
                self.$clear.hide();
            }
            if (self.showCaption) {
                self.$caption.show();
            } else {
                self.$caption.hide();
            }
            self.$element.trigger('rating.refresh');
        }
    };

    $.fn.rating = function (option) {
        var args = Array.apply(null, arguments);
        args.shift();
        return this.each(function () {
            var $this = $(this),
                data = $this.data('rating'),
                options = typeof option === 'object' && option;

            if (!data) {
                $this.data('rating',
                    (data = new Rating(this, $.extend({}, $.fn.rating.defaults, options, $(this).data()))));
            }

            if (typeof option === 'string') {
                data[option].apply(data, args);
            }
        });
    };

    $.fn.rating.defaults = {
        stars: 5,
        glyphicon: true,
        symbol: null,
        ratingClass: '',
        disabled: false,
        readonly: false,
        rtl: false,
        size: 'md',
        showClear: true,
        showCaption: true,
        defaultCaption: '{rating} Stars',
        starCaptions: {
            0.5: 'Half Star',
            1: 'One Star',
            1.5: 'One & Half Star',
            2: 'Two Stars',
            2.5: 'Two & Half Stars',
            3: 'Three Stars',
            3.5: 'Three & Half Stars',
            4: 'Four Stars',
            4.5: 'Four & Half Stars',
            5: 'Five Stars'
        },
        starCaptionClasses: {
            0.5: 'label label-danger',
            1: 'label label-danger',
            1.5: 'label label-warning',
            2: 'label label-warning',
            2.5: 'label label-info',
            3: 'label label-info',
            3.5: 'label label-primary',
            4: 'label label-primary',
            4.5: 'label label-success',
            5: 'label label-success'
        },
        clearButton: '<i class="glyphicon glyphicon-minus-sign"></i>',
        clearButtonTitle: 'Clear',
        clearButtonBaseClass: 'clear-rating',
        clearButtonActiveClass: 'clear-rating-active',
        clearCaption: 'Not Rated',
        clearCaptionClass: 'label label-default',
        clearValue: null,
        captionElement: null,
        clearElement: null,
        containerClass: null,
        hoverEnabled: true,
        hoverChangeCaption: true,
        hoverChangeStars: true,
        hoverOnClear: true
    };

    $.fn.rating.Constructor = Rating;

    /**
     * Convert automatically inputs with class 'rating'
     * into the star rating control.
     */
    $('input.rating').addClass('rating-loading');

    $(document).ready(function () {
        var $input = $('input.rating'), count = Object.keys($input).length;
        if (count > 0) {
            $input.rating();
        }
    });
}(window.jQuery));

/*!
 * jQuery Cookie Plugin v1.4.1
 * https://github.com/carhartl/jquery-cookie
 *
 * Copyright 2013 Klaus Hartl
 * Released under the MIT license
 */
(function (factory) {
	if (typeof define === 'function' && define.amd) {
		// AMD
		define(['jquery'], factory);
	} else if (typeof exports === 'object') {
		// CommonJS
		factory(require('jquery'));
	} else {
		// Browser globals
		factory(jQuery);
	}
}(function ($) {

	var pluses = /\+/g;

	function encode(s) {
		return config.raw ? s : encodeURIComponent(s);
	}

	function decode(s) {
		return config.raw ? s : decodeURIComponent(s);
	}

	function stringifyCookieValue(value) {
		return encode(config.json ? JSON.stringify(value) : String(value));
	}

	function parseCookieValue(s) {
		if (s.indexOf('"') === 0) {
			// This is a quoted cookie as according to RFC2068, unescape...
			s = s.slice(1, -1).replace(/\\"/g, '"').replace(/\\\\/g, '\\');
		}

		try {
			// Replace server-side written pluses with spaces.
			// If we can't decode the cookie, ignore it, it's unusable.
			// If we can't parse the cookie, ignore it, it's unusable.
			s = decodeURIComponent(s.replace(pluses, ' '));
			return config.json ? JSON.parse(s) : s;
		} catch(e) {}
	}

	function read(s, converter) {
		var value = config.raw ? s : parseCookieValue(s);
		return $.isFunction(converter) ? converter(value) : value;
	}

	var config = $.cookie = function (key, value, options) {

		// Write

		if (value !== undefined && !$.isFunction(value)) {
			options = $.extend({}, config.defaults, options);

			if (typeof options.expires === 'number') {
				var days = options.expires, t = options.expires = new Date();
				t.setTime(+t + days * 864e+5);
			}

			return (document.cookie = [
				encode(key), '=', stringifyCookieValue(value),
				options.expires ? '; expires=' + options.expires.toUTCString() : '', // use expires attribute, max-age is not supported by IE
				options.path    ? '; path=' + options.path : '',
				options.domain  ? '; domain=' + options.domain : '',
				options.secure  ? '; secure' : ''
			].join(''));
		}

		// Read

		var result = key ? undefined : {};

		// To prevent the for loop in the first place assign an empty array
		// in case there are no cookies at all. Also prevents odd result when
		// calling $.cookie().
		var cookies = document.cookie ? document.cookie.split('; ') : [];

		for (var i = 0, l = cookies.length; i < l; i++) {
			var parts = cookies[i].split('=');
			var name = decode(parts.shift());
			var cookie = parts.join('=');

			if (key && key === name) {
				// If second argument (value) is a function it's a converter...
				result = read(cookie, value);
				break;
			}

			// Prevent storing a cookie that we couldn't decode.
			if (!key && (cookie = read(cookie)) !== undefined) {
				result[name] = cookie;
			}
		}

		return result;
	};

	config.defaults = {};

	$.removeCookie = function (key, options) {
		if ($.cookie(key) === undefined) {
			return false;
		}

		// Must not alter options, thus extending a fresh object...
		$.cookie(key, '', $.extend({}, options, { expires: -1 }));
		return !$.cookie(key);
	};

}));

/*
 * jQuery Easing v1.3.2 - http://gsgd.co.uk/sandbox/jquery/easing/
 * Open source under the BSD License.
 * Copyright © 2008 George McGinley Smith
 * All rights reserved.
 * https://raw.github.com/gdsmith/jquery-easing/master/LICENSE
*/

// t: current time, b: begInnIng value, c: change In value, d: duration
(function($){$.easing['jswing'] = $.easing['swing'];

$.extend( $.easing,
{
	def: 'easeOutQuad',
	swing: function (x, t, b, c, d) {
		//alert($.easing.default);
		return $.easing[$.easing.def](x, t, b, c, d);
	},
	easeInQuad: function (x, t, b, c, d) {
		return c*(t/=d)*t + b;
	},
	easeOutQuad: function (x, t, b, c, d) {
		return -c *(t/=d)*(t-2) + b;
	},
	easeInOutQuad: function (x, t, b, c, d) {
		if ((t/=d/2) < 1) return c/2*t*t + b;
		return -c/2 * ((--t)*(t-2) - 1) + b;
	},
	easeInCubic: function (x, t, b, c, d) {
		return c*(t/=d)*t*t + b;
	},
	easeOutCubic: function (x, t, b, c, d) {
		return c*((t=t/d-1)*t*t + 1) + b;
	},
	easeInOutCubic: function (x, t, b, c, d) {
		if ((t/=d/2) < 1) return c/2*t*t*t + b;
		return c/2*((t-=2)*t*t + 2) + b;
	},
	easeInQuart: function (x, t, b, c, d) {
		return c*(t/=d)*t*t*t + b;
	},
	easeOutQuart: function (x, t, b, c, d) {
		return -c * ((t=t/d-1)*t*t*t - 1) + b;
	},
	easeInOutQuart: function (x, t, b, c, d) {
		if ((t/=d/2) < 1) return c/2*t*t*t*t + b;
		return -c/2 * ((t-=2)*t*t*t - 2) + b;
	},
	easeInQuint: function (x, t, b, c, d) {
		return c*(t/=d)*t*t*t*t + b;
	},
	easeOutQuint: function (x, t, b, c, d) {
		return c*((t=t/d-1)*t*t*t*t + 1) + b;
	},
	easeInOutQuint: function (x, t, b, c, d) {
		if ((t/=d/2) < 1) return c/2*t*t*t*t*t + b;
		return c/2*((t-=2)*t*t*t*t + 2) + b;
	},
	easeInSine: function (x, t, b, c, d) {
		return -c * Math.cos(t/d * (Math.PI/2)) + c + b;
	},
	easeOutSine: function (x, t, b, c, d) {
		return c * Math.sin(t/d * (Math.PI/2)) + b;
	},
	easeInOutSine: function (x, t, b, c, d) {
		return -c/2 * (Math.cos(Math.PI*t/d) - 1) + b;
	},
	easeInExpo: function (x, t, b, c, d) {
		return (t==0) ? b : c * Math.pow(2, 10 * (t/d - 1)) + b;
	},
	easeOutExpo: function (x, t, b, c, d) {
		return (t==d) ? b+c : c * (-Math.pow(2, -10 * t/d) + 1) + b;
	},
	easeInOutExpo: function (x, t, b, c, d) {
		if (t==0) return b;
		if (t==d) return b+c;
		if ((t/=d/2) < 1) return c/2 * Math.pow(2, 10 * (t - 1)) + b;
		return c/2 * (-Math.pow(2, -10 * --t) + 2) + b;
	},
	easeInCirc: function (x, t, b, c, d) {
		return -c * (Math.sqrt(1 - (t/=d)*t) - 1) + b;
	},
	easeOutCirc: function (x, t, b, c, d) {
		return c * Math.sqrt(1 - (t=t/d-1)*t) + b;
	},
	easeInOutCirc: function (x, t, b, c, d) {
		if ((t/=d/2) < 1) return -c/2 * (Math.sqrt(1 - t*t) - 1) + b;
		return c/2 * (Math.sqrt(1 - (t-=2)*t) + 1) + b;
	},
	easeInElastic: function (x, t, b, c, d) {
		var s=1.70158;var p=0;var a=c;
		if (t==0) return b;  if ((t/=d)==1) return b+c;  if (!p) p=d*.3;
		if (a < Math.abs(c)) { a=c; var s=p/4; }
		else var s = p/(2*Math.PI) * Math.asin (c/a);
		return -(a*Math.pow(2,10*(t-=1)) * Math.sin( (t*d-s)*(2*Math.PI)/p )) + b;
	},
	easeOutElastic: function (x, t, b, c, d) {
		var s=1.70158;var p=0;var a=c;
		if (t==0) return b;  if ((t/=d)==1) return b+c;  if (!p) p=d*.3;
		if (a < Math.abs(c)) { a=c; var s=p/4; }
		else var s = p/(2*Math.PI) * Math.asin (c/a);
		return a*Math.pow(2,-10*t) * Math.sin( (t*d-s)*(2*Math.PI)/p ) + c + b;
	},
	easeInOutElastic: function (x, t, b, c, d) {
		var s=1.70158;var p=0;var a=c;
		if (t==0) return b;  if ((t/=d/2)==2) return b+c;  if (!p) p=d*(.3*1.5);
		if (a < Math.abs(c)) { a=c; var s=p/4; }
		else var s = p/(2*Math.PI) * Math.asin (c/a);
		if (t < 1) return -.5*(a*Math.pow(2,10*(t-=1)) * Math.sin( (t*d-s)*(2*Math.PI)/p )) + b;
		return a*Math.pow(2,-10*(t-=1)) * Math.sin( (t*d-s)*(2*Math.PI)/p )*.5 + c + b;
	},
	easeInBack: function (x, t, b, c, d, s) {
		if (s == undefined) s = 1.70158;
		return c*(t/=d)*t*((s+1)*t - s) + b;
	},
	easeOutBack: function (x, t, b, c, d, s) {
		if (s == undefined) s = 1.70158;
		return c*((t=t/d-1)*t*((s+1)*t + s) + 1) + b;
	},
	easeInOutBack: function (x, t, b, c, d, s) {
		if (s == undefined) s = 1.70158; 
		if ((t/=d/2) < 1) return c/2*(t*t*(((s*=(1.525))+1)*t - s)) + b;
		return c/2*((t-=2)*t*(((s*=(1.525))+1)*t + s) + 2) + b;
	},
	easeInBounce: function (x, t, b, c, d) {
		return c - $.easing.easeOutBounce (x, d-t, 0, c, d) + b;
	},
	easeOutBounce: function (x, t, b, c, d) {
		if ((t/=d) < (1/2.75)) {
			return c*(7.5625*t*t) + b;
		} else if (t < (2/2.75)) {
			return c*(7.5625*(t-=(1.5/2.75))*t + .75) + b;
		} else if (t < (2.5/2.75)) {
			return c*(7.5625*(t-=(2.25/2.75))*t + .9375) + b;
		} else {
			return c*(7.5625*(t-=(2.625/2.75))*t + .984375) + b;
		}
	},
	easeInOutBounce: function (x, t, b, c, d) {
		if (t < d/2) return $.easing.easeInBounce (x, t*2, 0, c, d) * .5 + b;
		return $.easing.easeOutBounce (x, t*2-d, 0, c, d) * .5 + c*.5 + b;
	}
});})(jQuery);

// jQuery List DragSort v0.5.1-cmaish
// Website: http://dragsort.codeplex.com/
// License: http://dragsort.codeplex.com/license
// This version from: https://github.com/cmaish/dragsort

(function($) {

	$.fn.dragsort = function(options) {
		if (options == "destroy") {
			$(this.selector).trigger("dragsort-uninit");
			return;
		}

		var opts = $.extend({}, $.fn.dragsort.defaults, options);
		var lists = [];
		var list = null, lastPos = null;

		this.each(function(i, cont) {

			//if list container is table, the browser automatically wraps rows in tbody if not specified so change list container to tbody so that children returns rows as user expected
			if ($(cont).is("table") && $(cont).children().size() == 1 && $(cont).children().is("tbody"))
				cont = $(cont).children().get(0);

			var newList = {
				draggedItem: null,
				placeHolderItem: null,
				pos: null,
				offset: null,
				offsetLimit: null,
				scroll: null,
				container: cont,

				init: function() {
					//set options to default values if not set
					opts.tagName = $(this.container).children().size() == 0 ? "li" : $(this.container).children().get(0).tagName.toLowerCase();
					if (opts.itemSelector == "")
						opts.itemSelector = opts.tagName;
					if (opts.dragSelector == "")
						opts.dragSelector = opts.tagName;
					if (opts.placeHolderTemplate == "")
						opts.placeHolderTemplate = "<" + opts.tagName + ">&nbsp;</" + opts.tagName + ">";

					//listidx allows reference back to correct list variable instance
					$(this.container).attr("data-listidx", i).mousedown(this.grabItem).bind("dragsort-uninit", this.uninit);
					this.styleDragHandlers(true);
				},

				uninit: function() {
					var list = lists[$(this).attr("data-listidx")];
					$(list.container).unbind("mousedown", list.grabItem).unbind("dragsort-uninit");
					list.styleDragHandlers(false);
				},

				getItems: function() {
					return $(this.container).children(opts.itemSelector);
				},

				styleDragHandlers: function(cursor) {
					this.getItems().map(function() { return $(this).is(opts.dragSelector) ? this : $(this).find(opts.dragSelector).get(); }).css("cursor", cursor ? "pointer" : "");
				},

				grabItem: function(e) {
					var list = lists[$(this).attr("data-listidx")];
					var item = $(e.target).closest("[data-listidx] > " + opts.tagName).get(0);
					var insideMoveableItem = list.getItems().filter(function() { return this == item; }).size() > 0;

					//if not left click or if clicked on excluded element (e.g. text box) or not a moveable list item return
					if (e.which != 1 || $(e.target).is(opts.dragSelectorExclude) || $(e.target).closest(opts.dragSelectorExclude).size() > 0 || !insideMoveableItem)
						return;

					//prevents selection, stops issue on Fx where dragging hyperlink doesn't work and on IE where it triggers mousemove even though mouse hasn't moved,
					//does also stop being able to click text boxes hence dragging on text boxes by default is disabled in dragSelectorExclude
					e.preventDefault();

					//change cursor to move while dragging
					var dragHandle = e.target;
					while (!$(dragHandle).is(opts.dragSelector)) {
						if (dragHandle == this) return;
						dragHandle = dragHandle.parentNode;
					}
					$(dragHandle).attr("data-cursor", $(dragHandle).css("cursor"));
					$(dragHandle).css("cursor", "move");

					//on mousedown wait for movement of mouse before triggering dragsort script (dragStart) to allow clicking of hyperlinks to work
					var listElem = this;
					var trigger = function() {
						list.dragStart.call(listElem, e);
						$(list.container).unbind("mousemove", trigger);
					};
					$(list.container).mousemove(trigger).mouseup(function() { $(list.container).unbind("mousemove", trigger); $(dragHandle).css("cursor", $(dragHandle).attr("data-cursor")); });
				},

				dragStart: function(e) {
					if (list != null && list.draggedItem != null)
						list.dropItem();

					list = lists[$(this).attr("data-listidx")];
					list.draggedItem = $(e.target).closest("[data-listidx] > " + opts.tagName)
					
					//raise event
					if (opts.dragStart.apply(list.draggedItem) === false) {
					    list.draggedItem = null;
					    return;
					}

					//record current position so on dragend we know if the dragged item changed position or not, not using getItems to allow dragsort to restore dragged item to original location in relation to fixed items
					list.draggedItem.attr("data-origpos", $(this).attr("data-listidx") + "-" + $(list.container).children().index(list.draggedItem));

					//calculate mouse offset relative to draggedItem
					var mt = parseInt(list.draggedItem.css("marginTop"));
					var ml = parseInt(list.draggedItem.css("marginLeft"));
					list.offset = list.draggedItem.offset();
					list.offset.top = e.pageY - list.offset.top + (isNaN(mt) ? 0 : mt) - 1;
					list.offset.left = e.pageX - list.offset.left + (isNaN(ml) ? 0 : ml) - 1;

					//calculate box the dragged item can't be dragged outside of
					if (!opts.dragBetween) {
						var containerHeight = $(list.container).outerHeight() == 0 ? Math.max(1, Math.round(0.5 + list.getItems().size() * list.draggedItem.outerWidth() / $(list.container).outerWidth())) * list.draggedItem.outerHeight() : $(list.container).outerHeight();
						list.offsetLimit = $(list.container).offset();
						list.offsetLimit.right = list.offsetLimit.left + $(list.container).outerWidth() - list.draggedItem.outerWidth();
						list.offsetLimit.bottom = list.offsetLimit.top + containerHeight - list.draggedItem.outerHeight();
					}

					//create placeholder item
					var h = list.draggedItem.height();
					var w = list.draggedItem.width();
					if (opts.tagName == "tr") {
						list.draggedItem.children().each(function() { $(this).width($(this).width()); });
						list.placeHolderItem = list.draggedItem.clone().attr("data-placeholder", true);
						list.draggedItem.after(list.placeHolderItem);
						list.placeHolderItem.children().each(function() { $(this).css({ borderWidth:0, width: $(this).width() + 1, height: $(this).height() + 1 }).html("&nbsp;"); });
					} else {
						list.draggedItem.after(opts.placeHolderTemplate);
						list.placeHolderItem = list.draggedItem.next().css({ height: h, width: w }).attr("data-placeholder", true);
					}

					if (opts.tagName == "td") {
						var listTable = list.draggedItem.closest("table").get(0);
						$("<table id='" + listTable.id + "' style='border-width: 0px;' class='dragSortItem " + listTable.className + "'><tr></tr></table>").appendTo("body").children().append(list.draggedItem);
					}

					//style draggedItem while dragging
					var orig = list.draggedItem.attr("style");
					list.draggedItem.attr("data-origstyle", orig ? orig : "");
					list.draggedItem.css({ position: "absolute", opacity: 0.8, "z-index": 999, height: h, width: w });

					//auto-scroll setup
					list.scroll = { moveX: 0, moveY: 0, maxX: $(document).width() - $(window).width(), maxY: $(document).height() - $(window).height() };
					list.scroll.scrollY = window.setInterval(function() {
						if (opts.scrollContainer != window) {
							$(opts.scrollContainer).scrollTop($(opts.scrollContainer).scrollTop() + list.scroll.moveY);
							return;
						}
						var t = $(opts.scrollContainer).scrollTop();
						if (list.scroll.moveY > 0 && t < list.scroll.maxY || list.scroll.moveY < 0 && t > 0) {
							$(opts.scrollContainer).scrollTop(t + list.scroll.moveY);
							list.draggedItem.css("top", list.draggedItem.offset().top + list.scroll.moveY + 1);
						}
					}, 10);
					list.scroll.scrollX = window.setInterval(function() {
						if (opts.scrollContainer != window) {
							$(opts.scrollContainer).scrollLeft($(opts.scrollContainer).scrollLeft() + list.scroll.moveX);
							return;
						}
						var l = $(opts.scrollContainer).scrollLeft();
						if (list.scroll.moveX > 0 && l < list.scroll.maxX || list.scroll.moveX < 0 && l > 0) {
							$(opts.scrollContainer).scrollLeft(l + list.scroll.moveX);
							list.draggedItem.css("left", list.draggedItem.offset().left + list.scroll.moveX + 1);
						}
					}, 10);

					//misc
					$(lists).each(function(i, l) { l.createDropTargets(); l.buildPositionTable(); });
					list.setPos(e.pageX, e.pageY);
					$(document).bind("mousemove", list.swapItems);
					$(document).bind("mouseup", list.dropItem);
					if (opts.scrollContainer != window)
						$(window).bind("DOMMouseScroll mousewheel", list.wheel);
				},

				//set position of draggedItem
				setPos: function(x, y) { 
					//remove mouse offset so mouse cursor remains in same place on draggedItem instead of top left corner
					var top = y - this.offset.top;
					var left = x - this.offset.left;

					//limit top, left to within box draggedItem can't be dragged outside of
					if (!opts.dragBetween) {
						top = Math.min(this.offsetLimit.bottom, Math.max(top, this.offsetLimit.top));
						left = Math.min(this.offsetLimit.right, Math.max(left, this.offsetLimit.left));
					}

					//adjust top & left calculations to parent offset
					var parent = this.draggedItem.offsetParent().not("body").offset(); //offsetParent returns body even when it's static, if not static offset is only factoring margin
					if (parent != null) {
						top -= parent.top;
						left -= parent.left;
					}

					//set x or y auto-scroll amount
					if (opts.scrollContainer == window) {
						y -= $(window).scrollTop();
						x -= $(window).scrollLeft();
						y = Math.max(0, y - $(window).height() + 5) + Math.min(0, y - 5);
						x = Math.max(0, x - $(window).width() + 5) + Math.min(0, x - 5);
					} else {
						var cont = $(opts.scrollContainer);
						var offset = cont.offset();
						y = Math.max(0, y - cont.height() - offset.top) + Math.min(0, y - offset.top);
						x = Math.max(0, x - cont.width() - offset.left) + Math.min(0, x - offset.left);
					}
					
					list.scroll.moveX = x == 0 ? 0 : x * opts.scrollSpeed / Math.abs(x);
					list.scroll.moveY = y == 0 ? 0 : y * opts.scrollSpeed / Math.abs(y);

					//move draggedItem to new mouse cursor location
					this.draggedItem.css({ top: top, left: left });
				},

				//if scroll container is a div allow mouse wheel to scroll div instead of window when mouse is hovering over
				wheel: function(e) {
					if (($.browser.safari || $.browser.mozilla) && list && opts.scrollContainer != window) {
						var cont = $(opts.scrollContainer);
						var offset = cont.offset();
						if (e.pageX > offset.left && e.pageX < offset.left + cont.width() && e.pageY > offset.top && e.pageY < offset.top + cont.height()) {
							var delta = e.detail ? e.detail * 5 : e.wheelDelta / -2;
							cont.scrollTop(cont.scrollTop() + delta);
							e.preventDefault();
						}
					}
				},

				//build a table recording all the positions of the moveable list items
				buildPositionTable: function() {
					var pos = [];
					this.getItems().not([list.draggedItem[0], list.placeHolderItem[0]]).each(function(i) {
						var loc = $(this).offset();
						loc.right = loc.left + $(this).outerWidth();
						loc.bottom = loc.top + $(this).outerHeight();
						loc.elm = this;
						pos[i] = loc;
					});
					this.pos = pos;
				},

				dropItem: function() {
					if (list.draggedItem == null)
						return;

					//list.draggedItem.attr("style", "") doesn't work on IE8 and jQuery 1.5 or lower
					//list.draggedItem.removeAttr("style") doesn't work on chrome and jQuery 1.6 (works jQuery 1.5 or lower)
					var orig = list.draggedItem.attr("data-origstyle");
					list.draggedItem.attr("style", orig);
					if (orig == "")
						list.draggedItem.removeAttr("style");
					list.draggedItem.removeAttr("data-origstyle");

					list.styleDragHandlers(true);

					list.placeHolderItem.before(list.draggedItem);
					list.placeHolderItem.remove();

					$("[data-droptarget], .dragSortItem").remove();

					window.clearInterval(list.scroll.scrollY);
					window.clearInterval(list.scroll.scrollX);

					//if position changed call dragEnd
					if (list.draggedItem.attr("data-origpos") != $(lists).index(list) + "-" + $(list.container).children().index(list.draggedItem))
						if (opts.dragEnd.apply(list.draggedItem) == false) { //if dragEnd returns false revert order
							var pos = list.draggedItem.attr("data-origpos").split('-');
							var nextItem = $(lists[pos[0]].container).children().not(list.draggedItem).eq(pos[1]);
							if (nextItem.size() > 0)
								nextItem.before(list.draggedItem);
							else if (pos[1] == 0) //was the only item in list
								$(lists[pos[0]].container).prepend(list.draggedItem);
							else //was the last item in list
								$(lists[pos[0]].container).append(list.draggedItem);
						}
					list.draggedItem.removeAttr("data-origpos");

					list.draggedItem = null;
					$(document).unbind("mousemove", list.swapItems);
					$(document).unbind("mouseup", list.dropItem);
					if (opts.scrollContainer != window)
						$(window).unbind("DOMMouseScroll mousewheel", list.wheel);
					return false;
				},

				//swap the draggedItem (represented visually by placeholder) with the list item the it has been dragged on top of
				swapItems: function(e) {
					if (list.draggedItem == null)
						return false;

					//move draggedItem to mouse location
					list.setPos(e.pageX, e.pageY);

					//retrieve list and item position mouse cursor is over
					var ei = list.findPos(e.pageX, e.pageY);
					var nlist = list;
					for (var i = 0; ei == -1 && opts.dragBetween && i < lists.length; i++) {
						ei = lists[i].findPos(e.pageX, e.pageY);
						nlist = lists[i];
					}

					//if not over another moveable list item return
					if (ei == -1)
						return false;

					//save fixed items locations
					var children = function() { return $(nlist.container).children().not(nlist.draggedItem); };
					var fixed = children().not(opts.itemSelector).each(function(i) { this.idx = children().index(this); });

					//if moving draggedItem up or left place placeHolder before list item the dragged item is hovering over otherwise place it after
					if (lastPos == null || lastPos.top > list.draggedItem.offset().top || lastPos.left > list.draggedItem.offset().left)
						$(nlist.pos[ei].elm).before(list.placeHolderItem);
					else
						$(nlist.pos[ei].elm).after(list.placeHolderItem);

					//restore fixed items location
					fixed.each(function() {
						var elm = children().eq(this.idx).get(0);
						if (this != elm && children().index(this) < this.idx)
							$(this).insertAfter(elm);
						else if (this != elm)
							$(this).insertBefore(elm);
					});

					//misc
					$(lists).each(function(i, l) { l.createDropTargets(); l.buildPositionTable(); });
					lastPos = list.draggedItem.offset();
					return false;
				},

				//returns the index of the list item the mouse is over
				findPos: function(x, y) {
					for (var i = 0; i < this.pos.length; i++) {
						if (this.pos[i].left < x && this.pos[i].right > x && this.pos[i].top < y && this.pos[i].bottom > y)
							return i;
					}
					return -1;
				},

				//create drop targets which are placeholders at the end of other lists to allow dragging straight to the last position
				createDropTargets: function() {
					if (!opts.dragBetween)
						return;

					$(lists).each(function() {
						var ph = $(this.container).find("[data-placeholder]");
						var dt = $(this.container).find("[data-droptarget]");
						if (ph.size() > 0 && dt.size() > 0)
							dt.remove();
						else if (ph.size() == 0 && dt.size() == 0) {
							if (opts.tagName == "td")
								$(opts.placeHolderTemplate).attr("data-droptarget", true).appendTo(this.container);
							else
								//list.placeHolderItem.clone().removeAttr("data-placeholder") crashes in IE7 and jquery 1.5.1 (doesn't in jquery 1.4.2 or IE8)
								$(this.container).append(list.placeHolderItem.removeAttr("data-placeholder").clone().attr("data-droptarget", true));
							
							list.placeHolderItem.attr("data-placeholder", true);
						}
					});
				}
			};

			newList.init();
			lists.push(newList);
		});

		return this;
	};

	$.fn.dragsort.defaults = {
		itemSelector: "",
		dragSelector: "",
		dragSelectorExclude: "input, textarea",
		dragStart: function () { },
		dragEnd: function() { },
		dragBetween: false,
		placeHolderTemplate: "",
		scrollContainer: window,
		scrollSpeed: 5
	};

})(jQuery);
/*
 * jQuery Plugin: Tokenizing Autocomplete Text Entry
 * Version 1.6.1
 *
 * Copyright (c) 2009 James Smith (http://loopj.com)
 * Licensed jointly under the GPL and MIT licenses,
 * choose which one suits your project best!
 *
 */
;(function ($) {

  // Default settings
  var DEFAULT_SETTINGS = {
    // Search settings
    method: "GET",
    queryParam: "q",
    searchDelay: 300,
    minChars: 1,
    propertyToSearch: "name",
    jsonContainer: null,
    contentType: "json",
    excludeCurrent: false,
    excludeCurrentParameter: "x",

    // Prepopulation settings
    prePopulate: null,
    processPrePopulate: false,

    // Display settings
    hintText: "Type in a search term",
    noResultsText: "No results",
    searchingText: "Searching...",
    deleteText: "&#215;",
    animateDropdown: true,
    placeholder: null,
    theme: null,
    zindex: 999,
    resultsLimit: null,

    enableHTML: false,

    resultsFormatter: function(item) {
      var string = item[this.propertyToSearch];
      return "<li>" + (this.enableHTML ? string : _escapeHTML(string)) + "</li>";
    },

    tokenFormatter: function(item) {
      var string = item[this.propertyToSearch];
      return "<li><p>" + (this.enableHTML ? string : _escapeHTML(string)) + "</p></li>";
    },

    // Tokenization settings
    tokenLimit: null,
    tokenDelimiter: ",",
    preventDuplicates: false,
    tokenValue: "id",

    // Behavioral settings
    allowFreeTagging: false,
    allowTabOut: false,
    autoSelectFirstResult: false,

    // Callbacks
    onResult: null,
    onCachedResult: null,
    onAdd: null,
    onFreeTaggingAdd: null,
    onDelete: null,
    onReady: null,

    // Other settings
    idPrefix: "token-input-",

    // Keep track if the input is currently in disabled mode
    disabled: false
  };

  // Default classes to use when theming
  var DEFAULT_CLASSES = {
    tokenList: "token-input-list",
    token: "token-input-token",
    tokenReadOnly: "token-input-token-readonly",
    tokenDelete: "token-input-delete-token",
    selectedToken: "token-input-selected-token",
    highlightedToken: "token-input-highlighted-token",
    dropdown: "token-input-dropdown",
    dropdownItem: "token-input-dropdown-item",
    dropdownItem2: "token-input-dropdown-item2",
    selectedDropdownItem: "token-input-selected-dropdown-item",
    inputToken: "token-input-input-token",
    focused: "token-input-focused",
    disabled: "token-input-disabled"
  };

  // Input box position "enum"
  var POSITION = {
    BEFORE: 0,
    AFTER: 1,
    END: 2
  };

  // Keys "enum"
  var KEY = {
    BACKSPACE: 8,
    TAB: 9,
    ENTER: 13,
    ESCAPE: 27,
    SPACE: 32,
    PAGE_UP: 33,
    PAGE_DOWN: 34,
    END: 35,
    HOME: 36,
    LEFT: 37,
    UP: 38,
    RIGHT: 39,
    DOWN: 40,
    NUMPAD_ENTER: 108,
    COMMA: 188
  };

  var HTML_ESCAPES = {
    '&': '&amp;',
    '<': '&lt;',
    '>': '&gt;',
    '"': '&quot;',
    "'": '&#x27;',
    '/': '&#x2F;'
  };

  var HTML_ESCAPE_CHARS = /[&<>"'\/]/g;

  function coerceToString(val) {
    return String((val === null || val === undefined) ? '' : val);
  }

  function _escapeHTML(text) {
    return coerceToString(text).replace(HTML_ESCAPE_CHARS, function(match) {
      return HTML_ESCAPES[match];
    });
  }

  // Additional public (exposed) methods
  var methods = {
      init: function(url_or_data_or_function, options) {
          var settings = $.extend({}, DEFAULT_SETTINGS, options || {});

          return this.each(function () {
              $(this).data("settings", settings);
              $(this).data("tokenInputObject", new $.TokenList(this, url_or_data_or_function, settings));
          });
      },
      clear: function() {
          this.data("tokenInputObject").clear();
          return this;
      },
      add: function(item) {
          this.data("tokenInputObject").add(item);
          return this;
      },
      remove: function(item) {
          this.data("tokenInputObject").remove(item);
          return this;
      },
      get: function() {
          return this.data("tokenInputObject").getTokens();
      },
      toggleDisabled: function(disable) {
          this.data("tokenInputObject").toggleDisabled(disable);
          return this;
      },
      setOptions: function(options){
          $(this).data("settings", $.extend({}, $(this).data("settings"), options || {}));
          return this;
      },
      destroy: function () {
        if (this.data("tokenInputObject")) {
          this.data("tokenInputObject").clear();
          var tmpInput = this;
          var closest = this.parent();
          closest.empty();
          tmpInput.show();
          closest.append(tmpInput);
          return tmpInput;
        }
      }
  };

  // Expose the .tokenInput function to jQuery as a plugin
  $.fn.tokenInput = function (method) {
      // Method calling and initialization logic
      if(methods[method]) {
          return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
      } else {
          return methods.init.apply(this, arguments);
      }
  };

  // TokenList class for each input
  $.TokenList = function (input, url_or_data, settings) {
      //
      // Initialization
      //

      // Configure the data source
      if (typeof(url_or_data) === "string" || typeof(url_or_data) === "function") {
          // Set the url to query against
          $(input).data("settings").url = url_or_data;

          // If the URL is a function, evaluate it here to do our initalization work
          var url = computeURL();

          // Make a smart guess about cross-domain if it wasn't explicitly specified
          if ($(input).data("settings").crossDomain === undefined && typeof url === "string") {
              if(url.indexOf("://") === -1) {
                  $(input).data("settings").crossDomain = false;
              } else {
                  $(input).data("settings").crossDomain = (location.href.split(/\/+/g)[1] !== url.split(/\/+/g)[1]);
              }
          }
      } else if (typeof(url_or_data) === "object") {
          // Set the local data to search through
          $(input).data("settings").local_data = url_or_data;
      }

      // Build class names
      if($(input).data("settings").classes) {
          // Use custom class names
          $(input).data("settings").classes = $.extend({}, DEFAULT_CLASSES, $(input).data("settings").classes);
      } else if($(input).data("settings").theme) {
          // Use theme-suffixed default class names
          $(input).data("settings").classes = {};
          $.each(DEFAULT_CLASSES, function(key, value) {
              $(input).data("settings").classes[key] = value + "-" + $(input).data("settings").theme;
          });
      } else {
          $(input).data("settings").classes = DEFAULT_CLASSES;
      }

      // Save the tokens
      var saved_tokens = [];

      // Keep track of the number of tokens in the list
      var token_count = 0;

      // Basic cache to save on db hits
      var cache = new $.TokenList.Cache();

      // Keep track of the timeout, old vals
      var timeout;
      var input_val;

      // Create a new text input an attach keyup events
      var input_box = $("<input type=\"text\" autocomplete=\"off\" autocapitalize=\"off\"/>")
          .css({
              outline: "none"
          })
          .attr("id", $(input).data("settings").idPrefix + input.id)
          .focus(function () {
              if ($(input).data("settings").disabled) {
                  return false;
              } else
              if ($(input).data("settings").tokenLimit === null || $(input).data("settings").tokenLimit !== token_count) {
                  show_dropdown_hint();
              }
              token_list.addClass($(input).data("settings").classes.focused);
          })
          .blur(function () {
              hide_dropdown();

              if ($(input).data("settings").allowFreeTagging) {
                add_freetagging_tokens();
              }

              $(this).val("");
              token_list.removeClass($(input).data("settings").classes.focused);
          })
          .bind("keyup keydown blur update", resize_input)
          .keydown(function (event) {
              var previous_token;
              var next_token;

              switch(event.keyCode) {
                  case KEY.LEFT:
                  case KEY.RIGHT:
                  case KEY.UP:
                  case KEY.DOWN:
                    if(this.value.length === 0) {
                        previous_token = input_token.prev();
                        next_token = input_token.next();

                        if((previous_token.length && previous_token.get(0) === selected_token) || (next_token.length && next_token.get(0) === selected_token)) {
                            // Check if there is a previous/next token and it is selected
                            if(event.keyCode === KEY.LEFT || event.keyCode === KEY.UP) {
                                deselect_token($(selected_token), POSITION.BEFORE);
                            } else {
                                deselect_token($(selected_token), POSITION.AFTER);
                            }
                        } else if((event.keyCode === KEY.LEFT || event.keyCode === KEY.UP) && previous_token.length) {
                            // We are moving left, select the previous token if it exists
                            select_token($(previous_token.get(0)));
                        } else if((event.keyCode === KEY.RIGHT || event.keyCode === KEY.DOWN) && next_token.length) {
                            // We are moving right, select the next token if it exists
                            select_token($(next_token.get(0)));
                        }
                    } else {
                      var dropdown_item = null;

                      if (event.keyCode === KEY.DOWN || event.keyCode === KEY.RIGHT) {
                        dropdown_item = $(dropdown).find('li').first();

                        if (selected_dropdown_item) {
                          dropdown_item = $(selected_dropdown_item).next();
                        }
                      } else {
                        dropdown_item = $(dropdown).find('li').last();

                        if (selected_dropdown_item) {
                          dropdown_item = $(selected_dropdown_item).prev();
                        }
                      }

                      select_dropdown_item(dropdown_item);
                    }

                    break;

                  case KEY.BACKSPACE:
                      previous_token = input_token.prev();

                      if (this.value.length === 0) {
                        if (selected_token) {
                          delete_token($(selected_token));
                          hidden_input.change();
                        } else if(previous_token.length) {
                          select_token($(previous_token.get(0)));
                        }

                        return false;
                      } else if($(this).val().length === 1) {
                          hide_dropdown();
                      } else {
                          // set a timeout just long enough to let this function finish.
                          setTimeout(function(){ do_search(); }, 5);
                      }
                      break;

                  case KEY.TAB:
                  case KEY.ENTER:
                  case KEY.NUMPAD_ENTER:
                  case KEY.COMMA:
                    if(selected_dropdown_item) {
                      add_token($(selected_dropdown_item).data("tokeninput"));
                      hidden_input.change();
                    } else {
                      if ($(input).data("settings").allowFreeTagging) {
                        if($(input).data("settings").allowTabOut && $(this).val() === "") {
                          return true;
                        } else {
                          add_freetagging_tokens();
                        }
                      } else {
                        $(this).val("");
                        if($(input).data("settings").allowTabOut) {
                          return true;
                        }
                      }
                      event.stopPropagation();
                      event.preventDefault();
                    }
                    return false;

                  case KEY.ESCAPE:
                    hide_dropdown();
                    return true;

                  default:
                    if (String.fromCharCode(event.which)) {
                      // set a timeout just long enough to let this function finish.
                      setTimeout(function(){ do_search(); }, 5);
                    }
                    break;
              }
          });

      // Keep reference for placeholder
      if (settings.placeholder) {
        input_box.attr("placeholder", settings.placeholder);
      }

      // Keep a reference to the original input box
      var hidden_input = $(input)
        .hide()
        .val("")
        .focus(function () {
          focus_with_timeout(input_box);
        })
        .blur(function () {
          input_box.blur();

          //return the object to this can be referenced in the callback functions.
          return hidden_input;
        })
      ;

      // Keep a reference to the selected token and dropdown item
      var selected_token = null;
      var selected_token_index = 0;
      var selected_dropdown_item = null;

      // The list to store the token items in
      var token_list = $("<ul />")
          .addClass($(input).data("settings").classes.tokenList)
          .click(function (event) {
              var li = $(event.target).closest("li");
              if(li && li.get(0) && $.data(li.get(0), "tokeninput")) {
                  toggle_select_token(li);
              } else {
                  // Deselect selected token
                  if(selected_token) {
                      deselect_token($(selected_token), POSITION.END);
                  }

                  // Focus input box
                  focus_with_timeout(input_box);
              }
          })
          .mouseover(function (event) {
              var li = $(event.target).closest("li");
              if(li && selected_token !== this) {
                  li.addClass($(input).data("settings").classes.highlightedToken);
              }
          })
          .mouseout(function (event) {
              var li = $(event.target).closest("li");
              if(li && selected_token !== this) {
                  li.removeClass($(input).data("settings").classes.highlightedToken);
              }
          })
          .insertBefore(hidden_input);

      // The token holding the input box
      var input_token = $("<li />")
          .addClass($(input).data("settings").classes.inputToken)
          .appendTo(token_list)
          .append(input_box);

      // The list to store the dropdown items in
      var dropdown = $("<div/>")
          .addClass($(input).data("settings").classes.dropdown)
          .appendTo("body")
          .hide();

      // Magic element to help us resize the text input
      var input_resizer = $("<tester/>")
          .insertAfter(input_box)
          .css({
              position: "absolute",
              top: -9999,
              left: -9999,
              width: "auto",
              fontSize: input_box.css("fontSize"),
              fontFamily: input_box.css("fontFamily"),
              fontWeight: input_box.css("fontWeight"),
              letterSpacing: input_box.css("letterSpacing"),
              whiteSpace: "nowrap"
          });

      // Pre-populate list if items exist
      hidden_input.val("");
      var li_data = $(input).data("settings").prePopulate || hidden_input.data("pre");

      if ($(input).data("settings").processPrePopulate && $.isFunction($(input).data("settings").onResult)) {
          li_data = $(input).data("settings").onResult.call(hidden_input, li_data);
      }

      if (li_data && li_data.length) {
          $.each(li_data, function (index, value) {
              insert_token(value);
              checkTokenLimit();
              input_box.attr("placeholder", null)
          });
      }

      // Check if widget should initialize as disabled
      if ($(input).data("settings").disabled) {
          toggleDisabled(true);
      }

      // Initialization is done
      if (typeof($(input).data("settings").onReady) === "function") {
        $(input).data("settings").onReady.call();
      }

      //
      // Public functions
      //

      this.clear = function() {
          token_list.children("li").each(function() {
              if ($(this).children("input").length === 0) {
                  delete_token($(this));
              }
          });
      };

      this.add = function(item) {
          add_token(item);
      };

      this.remove = function(item) {
          token_list.children("li").each(function() {
              if ($(this).children("input").length === 0) {
                  var currToken = $(this).data("tokeninput");
                  var match = true;
                  for (var prop in item) {
                      if (item[prop] !== currToken[prop]) {
                          match = false;
                          break;
                      }
                  }
                  if (match) {
                      delete_token($(this));
                  }
              }
          });
      };

      this.getTokens = function() {
          return saved_tokens;
      };

      this.toggleDisabled = function(disable) {
          toggleDisabled(disable);
      };

      // Resize input to maximum width so the placeholder can be seen
      resize_input();

      //
      // Private functions
      //

      function escapeHTML(text) {
        return $(input).data("settings").enableHTML ? text : _escapeHTML(text);
      }

      // Toggles the widget between enabled and disabled state, or according
      // to the [disable] parameter.
      function toggleDisabled(disable) {
          if (typeof disable === 'boolean') {
              $(input).data("settings").disabled = disable
          } else {
              $(input).data("settings").disabled = !$(input).data("settings").disabled;
          }
          input_box.attr('disabled', $(input).data("settings").disabled);
          token_list.toggleClass($(input).data("settings").classes.disabled, $(input).data("settings").disabled);
          // if there is any token selected we deselect it
          if(selected_token) {
              deselect_token($(selected_token), POSITION.END);
          }
          hidden_input.attr('disabled', $(input).data("settings").disabled);
      }

      function checkTokenLimit() {
          if($(input).data("settings").tokenLimit !== null && token_count >= $(input).data("settings").tokenLimit) {
              input_box.hide();
              hide_dropdown();
              return;
          }
      }

      function resize_input() {
          if(input_val === (input_val = input_box.val())) {return;}

          // Get width left on the current line
          var width_left = token_list.width() - input_box.offset().left - token_list.offset().left;
          // Enter new content into resizer and resize input accordingly
          input_resizer.html(_escapeHTML(input_val) || _escapeHTML(settings.placeholder));
          // Get maximum width, minimum the size of input and maximum the widget's width
          input_box.width(Math.min(token_list.width(),
                                   Math.max(width_left, input_resizer.width() + 30)));
      }

      function add_freetagging_tokens() {
          var value = $.trim(input_box.val());
          var tokens = value.split($(input).data("settings").tokenDelimiter);
          $.each(tokens, function(i, token) {
            if (!token) {
              return;
            }

            if ($.isFunction($(input).data("settings").onFreeTaggingAdd)) {
              token = $(input).data("settings").onFreeTaggingAdd.call(hidden_input, token);
            }
            var object = {};
            object[$(input).data("settings").tokenValue] = object[$(input).data("settings").propertyToSearch] = token;
            add_token(object);
          });
      }

      // Inner function to a token to the list
      function insert_token(item) {
          var $this_token = $($(input).data("settings").tokenFormatter(item));
          var readonly = item.readonly === true ? true : false;

          if(readonly) $this_token.addClass($(input).data("settings").classes.tokenReadOnly);

          $this_token.addClass($(input).data("settings").classes.token).insertBefore(input_token);

          // The 'delete token' button
          if(!readonly) {
            $("<span>" + $(input).data("settings").deleteText + "</span>")
                .addClass($(input).data("settings").classes.tokenDelete)
                .appendTo($this_token)
                .click(function () {
                    if (!$(input).data("settings").disabled) {
                        delete_token($(this).parent());
                        hidden_input.change();
                        return false;
                    }
                });
          }

          // Store data on the token
          var token_data = item;
          $.data($this_token.get(0), "tokeninput", item);

          // Save this token for duplicate checking
          saved_tokens = saved_tokens.slice(0,selected_token_index).concat([token_data]).concat(saved_tokens.slice(selected_token_index));
          selected_token_index++;

          // Update the hidden input
          update_hidden_input(saved_tokens, hidden_input);

          token_count += 1;

          // Check the token limit
          if($(input).data("settings").tokenLimit !== null && token_count >= $(input).data("settings").tokenLimit) {
              input_box.hide();
              hide_dropdown();
          }

          return $this_token;
      }

      // Add a token to the token list based on user input
      function add_token (item) {
          var callback = $(input).data("settings").onAdd;

          // See if the token already exists and select it if we don't want duplicates
          if(token_count > 0 && $(input).data("settings").preventDuplicates) {
              var found_existing_token = null;
              token_list.children().each(function () {
                  var existing_token = $(this);
                  var existing_data = $.data(existing_token.get(0), "tokeninput");
                  if(existing_data && existing_data[settings.tokenValue] === item[settings.tokenValue]) {
                      found_existing_token = existing_token;
                      return false;
                  }
              });

              if(found_existing_token) {
                  select_token(found_existing_token);
                  input_token.insertAfter(found_existing_token);
                  focus_with_timeout(input_box);
                  return;
              }
          }

          // Squeeze input_box so we force no unnecessary line break
          input_box.width(1);

          // Insert the new tokens
          if($(input).data("settings").tokenLimit == null || token_count < $(input).data("settings").tokenLimit) {
              insert_token(item);
              // Remove the placeholder so it's not seen after you've added a token
              input_box.attr("placeholder", null)
              checkTokenLimit();
          }

          // Clear input box
          input_box.val("");

          // Don't show the help dropdown, they've got the idea
          hide_dropdown();

          // Execute the onAdd callback if defined
          if($.isFunction(callback)) {
              callback.call(hidden_input,item);
          }
      }

      // Select a token in the token list
      function select_token (token) {
          if (!$(input).data("settings").disabled) {
              token.addClass($(input).data("settings").classes.selectedToken);
              selected_token = token.get(0);

              // Hide input box
              input_box.val("");

              // Hide dropdown if it is visible (eg if we clicked to select token)
              hide_dropdown();
          }
      }

      // Deselect a token in the token list
      function deselect_token (token, position) {
          token.removeClass($(input).data("settings").classes.selectedToken);
          selected_token = null;

          if(position === POSITION.BEFORE) {
              input_token.insertBefore(token);
              selected_token_index--;
          } else if(position === POSITION.AFTER) {
              input_token.insertAfter(token);
              selected_token_index++;
          } else {
              input_token.appendTo(token_list);
              selected_token_index = token_count;
          }

          // Show the input box and give it focus again
          focus_with_timeout(input_box);
      }

      // Toggle selection of a token in the token list
      function toggle_select_token(token) {
          var previous_selected_token = selected_token;

          if(selected_token) {
              deselect_token($(selected_token), POSITION.END);
          }

          if(previous_selected_token === token.get(0)) {
              deselect_token(token, POSITION.END);
          } else {
              select_token(token);
          }
      }

      // Delete a token from the token list
      function delete_token (token) {
          // Remove the id from the saved list
          var token_data = $.data(token.get(0), "tokeninput");
          var callback = $(input).data("settings").onDelete;

          var index = token.prevAll().length;
          if(index > selected_token_index) index--;

          // Delete the token
          token.remove();
          selected_token = null;

          // Show the input box and give it focus again
          focus_with_timeout(input_box);

          // Remove this token from the saved list
          saved_tokens = saved_tokens.slice(0,index).concat(saved_tokens.slice(index+1));
          if (saved_tokens.length == 0) {
              input_box.attr("placeholder", settings.placeholder)
          }
          if(index < selected_token_index) selected_token_index--;

          // Update the hidden input
          update_hidden_input(saved_tokens, hidden_input);

          token_count -= 1;

          if($(input).data("settings").tokenLimit !== null) {
              input_box
                  .show()
                  .val("");
              focus_with_timeout(input_box);
          }

          // Execute the onDelete callback if defined
          if($.isFunction(callback)) {
              callback.call(hidden_input,token_data);
          }
      }

      // Update the hidden input box value
      function update_hidden_input(saved_tokens, hidden_input) {
          var token_values = $.map(saved_tokens, function (el) {
              if(typeof $(input).data("settings").tokenValue == 'function')
                return $(input).data("settings").tokenValue.call(this, el);

              return el[$(input).data("settings").tokenValue];
          });
          hidden_input.val(token_values.join($(input).data("settings").tokenDelimiter));

      }

      // Hide and clear the results dropdown
      function hide_dropdown () {
          dropdown.hide().empty();
          selected_dropdown_item = null;
      }

      function show_dropdown() {
          dropdown
              .css({
                  position: "absolute",
                  top: token_list.offset().top + token_list.outerHeight(true),
                  left: token_list.offset().left,
                  width: token_list.width(),
                  'z-index': $(input).data("settings").zindex
              })
              .show();
      }

      function show_dropdown_searching () {
          if($(input).data("settings").searchingText) {
              dropdown.html("<p>" + escapeHTML($(input).data("settings").searchingText) + "</p>");
              show_dropdown();
          }
      }

      function show_dropdown_hint () {
          if($(input).data("settings").hintText) {
              dropdown.html("<p>" + escapeHTML($(input).data("settings").hintText) + "</p>");
              show_dropdown();
          }
      }

      var regexp_special_chars = new RegExp('[.\\\\+*?\\[\\^\\]$(){}=!<>|:\\-]', 'g');
      function regexp_escape(term) {
          return term.replace(regexp_special_chars, '\\$&');
      }

      // Highlight the query part of the search term
      function highlight_term(value, term) {
          return value.replace(
            new RegExp(
              "(?![^&;]+;)(?!<[^<>]*)(" + regexp_escape(term) + ")(?![^<>]*>)(?![^&;]+;)",
              "gi"
            ), function(match, p1) {
              return "<b>" + escapeHTML(p1) + "</b>";
            }
          );
      }

      function find_value_and_highlight_term(template, value, term) {
          return template.replace(new RegExp("(?![^&;]+;)(?!<[^<>]*)(" + regexp_escape(value) + ")(?![^<>]*>)(?![^&;]+;)", "g"), highlight_term(value, term));
      }

      // exclude existing tokens from dropdown, so the list is clearer
      function excludeCurrent(results) {
          if ($(input).data("settings").excludeCurrent) {
              var currentTokens = $(input).data("tokenInputObject").getTokens(),
                  trimmedList = [];
              if (currentTokens.length) {
                  $.each(results, function(index, value) {
                      var notFound = true;
                      $.each(currentTokens, function(cIndex, cValue) {
                          if (value[$(input).data("settings").propertyToSearch] == cValue[$(input).data("settings").propertyToSearch]) {
                              notFound = false;
                              return false;
                          }
                      });

                      if (notFound) {
                          trimmedList.push(value);
                      }
                  });
                  results = trimmedList;
              }
          }

          return results;
      }

      // Populate the results dropdown with some results
      function populate_dropdown (query, results) {
          // exclude current tokens if configured
          results = excludeCurrent(results);

          if(results && results.length) {
              dropdown.empty();
              var dropdown_ul = $("<ul/>")
                  .appendTo(dropdown)
                  .mouseover(function (event) {
                      select_dropdown_item($(event.target).closest("li"));
                  })
                  .mousedown(function (event) {
                      add_token($(event.target).closest("li").data("tokeninput"));
                      hidden_input.change();
                      return false;
                  })
                  .hide();

              if ($(input).data("settings").resultsLimit && results.length > $(input).data("settings").resultsLimit) {
                  results = results.slice(0, $(input).data("settings").resultsLimit);
              }

              $.each(results, function(index, value) {
                  var this_li = $(input).data("settings").resultsFormatter(value);

                  this_li = find_value_and_highlight_term(this_li ,value[$(input).data("settings").propertyToSearch], query);
                  this_li = $(this_li).appendTo(dropdown_ul);

                  if(index % 2) {
                      this_li.addClass($(input).data("settings").classes.dropdownItem);
                  } else {
                      this_li.addClass($(input).data("settings").classes.dropdownItem2);
                  }

                  if(index === 0 && $(input).data("settings").autoSelectFirstResult) {
                      select_dropdown_item(this_li);
                  }

                  $.data(this_li.get(0), "tokeninput", value);
              });

              show_dropdown();

              if($(input).data("settings").animateDropdown) {
                  dropdown_ul.slideDown("fast");
              } else {
                  dropdown_ul.show();
              }
          } else {
              if($(input).data("settings").noResultsText) {
                  dropdown.html("<p>" + escapeHTML($(input).data("settings").noResultsText) + "</p>");
                  show_dropdown();
              }
          }
      }

      // Highlight an item in the results dropdown
      function select_dropdown_item (item) {
          if(item) {
              if(selected_dropdown_item) {
                  deselect_dropdown_item($(selected_dropdown_item));
              }

              item.addClass($(input).data("settings").classes.selectedDropdownItem);
              selected_dropdown_item = item.get(0);
          }
      }

      // Remove highlighting from an item in the results dropdown
      function deselect_dropdown_item (item) {
          item.removeClass($(input).data("settings").classes.selectedDropdownItem);
          selected_dropdown_item = null;
      }

      // Do a search and show the "searching" dropdown if the input is longer
      // than $(input).data("settings").minChars
      function do_search() {
          var query = input_box.val();

          if(query && query.length) {
              if(selected_token) {
                  deselect_token($(selected_token), POSITION.AFTER);
              }

              if(query.length >= $(input).data("settings").minChars) {
                  show_dropdown_searching();
                  clearTimeout(timeout);

                  timeout = setTimeout(function(){
                      run_search(query);
                  }, $(input).data("settings").searchDelay);
              } else {
                  hide_dropdown();
              }
          }
      }

      // Do the actual search
      function run_search(query) {
          var cache_key = query + computeURL();
          var cached_results = cache.get(cache_key);
          if (cached_results) {
              if ($.isFunction($(input).data("settings").onCachedResult)) {
                cached_results = $(input).data("settings").onCachedResult.call(hidden_input, cached_results);
              }
              populate_dropdown(query, cached_results);
          } else {
              // Are we doing an ajax search or local data search?
              if($(input).data("settings").url) {
                  var url = computeURL();
                  // Extract existing get params
                  var ajax_params = {};
                  ajax_params.data = {};
                  if(url.indexOf("?") > -1) {
                      var parts = url.split("?");
                      ajax_params.url = parts[0];

                      var param_array = parts[1].split("&");
                      $.each(param_array, function (index, value) {
                          var kv = value.split("=");
                          ajax_params.data[kv[0]] = kv[1];
                      });
                  } else {
                      ajax_params.url = url;
                  }

                  // Prepare the request
                  ajax_params.data[$(input).data("settings").queryParam] = query;
                  ajax_params.type = $(input).data("settings").method;
                  ajax_params.dataType = $(input).data("settings").contentType;
                  if ($(input).data("settings").crossDomain) {
                      ajax_params.dataType = "jsonp";
                  }

                  // exclude current tokens?
                  // send exclude list to the server, so it can also exclude existing tokens
                  if ($(input).data("settings").excludeCurrent) {
                      var currentTokens = $(input).data("tokenInputObject").getTokens();
                      var tokenList = $.map(currentTokens, function (el) {
                          if(typeof $(input).data("settings").tokenValue == 'function')
                              return $(input).data("settings").tokenValue.call(this, el);

                          return el[$(input).data("settings").tokenValue];
                      });

                      ajax_params.data[$(input).data("settings").excludeCurrentParameter] = tokenList.join($(input).data("settings").tokenDelimiter);
                  }

                  // Attach the success callback
                  ajax_params.success = function(results) {
                    cache.add(cache_key, $(input).data("settings").jsonContainer ? results[$(input).data("settings").jsonContainer] : results);
                    if($.isFunction($(input).data("settings").onResult)) {
                        results = $(input).data("settings").onResult.call(hidden_input, results);
                    }

                    // only populate the dropdown if the results are associated with the active search query
                    if(input_box.val() === query) {
                        populate_dropdown(query, $(input).data("settings").jsonContainer ? results[$(input).data("settings").jsonContainer] : results);
                    }
                  };

                  // Provide a beforeSend callback
                  if (settings.onSend) {
                    settings.onSend(ajax_params);
                  }

                  // Make the request
                  $.ajax(ajax_params);
              } else if($(input).data("settings").local_data) {
                  // Do the search through local data
                  var results = $.grep($(input).data("settings").local_data, function (row) {
                      return row[$(input).data("settings").propertyToSearch].toLowerCase().indexOf(query.toLowerCase()) > -1;
                  });

                  cache.add(cache_key, results);
                  if($.isFunction($(input).data("settings").onResult)) {
                      results = $(input).data("settings").onResult.call(hidden_input, results);
                  }
                  populate_dropdown(query, results);
              }
          }
      }

      // compute the dynamic URL
      function computeURL() {
          var url = $(input).data("settings").url;
          if(typeof $(input).data("settings").url == 'function') {
              url = $(input).data("settings").url.call($(input).data("settings"));
          }
          return url;
      }

      // Bring browser focus to the specified object.
      // Use of setTimeout is to get around an IE bug.
      // (See, e.g., http://stackoverflow.com/questions/2600186/focus-doesnt-work-in-ie)
      //
      // obj: a jQuery object to focus()
      function focus_with_timeout(obj) {
          setTimeout(function() { obj.focus(); }, 50);
      }

  };

  // Really basic cache for the results
  $.TokenList.Cache = function (options) {
    var settings, data = {}, size = 0, flush;

    settings = $.extend({ max_size: 500 }, options);

    flush = function () {
      data = {};
      size = 0;
    };

    this.add = function (query, results) {
      if (size > settings.max_size) {
        flush();
      }

      if (!data[query]) {
        size += 1;
      }

      data[query] = results;
    };

    this.get = function (query) {
      return data[query];
    };
  };

}(jQuery));

// 文件上传插件(Bootstrap.Huploadify v2.1)
(function($) {
    $.fn.Huploadify = function(opts) {
        var itemTemp = '<div id="${fileID}" class="uploadify-queue-item"><div class="uploadify-progress"><div class="uploadify-progress-bar"></div></div><span class="up_filename">${fileName}</span><span class="uploadbtn">上传</span><span class="delfilebtn">删除</span></div>';
        var defaults = {
            fileTypeExts: "*.*",
            uploader: "",
            auto: true,
            method: "post",
            multi: false,
            formData: {},
            fileObjName: "imgFile",
            fileSizeLimit: 2048,
            showUploadedPercent: true,
            showUploadedSize: false,
            buttonText: "选择文件",
            removeTimeout: 1000,
            itemTemplate: itemTemp,
            breakPoints: false,
            fileSplitSize: 1024 * 1024,
            getUploadedSize: null,
            saveUploadedSize: null,
            saveInfoLocal: false,
            onUploadStart: null,
            onUploadSuccess: null,
            onUploadComplete: null,
            onUploadError: null,
            onInit: null,
            onCancel: null,
            onSelect: null
        };
        var option = $.extend(defaults, opts);
        var formatFileSize = function(size, byKB) {
            if (size > 1024 * 1024 && !byKB) {
                size = (Math.round(size * 100 / (1024 * 1024)) / 100).toString() + "MB"
            } else {
                size = (Math.round(size * 100 / 1024) / 100).toString() + "KB"
            }
            return size
        };
        var getFile = function(index, files) {
            for (var i = 0; i < files.length; i++) {
                if (files[i].index == index) {
                    return files[i]
                }
            }
            return false
        };
        var getFileTypes = function(str) {
            var result = [];
            var arr1 = str.split(";");
            for (var i = 0, len = arr1.length; i < len; i++) {
                result.push(arr1[i].split(".").pop())
            }
            return result
        };
        var mimetypeMap = {
            zip: ["application/x-zip-compressed"],
            jpg: ["image/jpeg"],
            png: ["image/png"],
            gif: ["image/gif"],
            doc: ["application/msword"],
            xls: ["application/msexcel"],
            docx: ["application/vnd.openxmlformats-officedocument.wordprocessingml.document"],
            xlsx: ["application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"],
            ppt: ["application/vnd.ms-powerpoint "],
            pptx: ["application/vnd.openxmlformats-officedocument.presentationml.presentation"],
            mp3: ["audio/mp3"],
            mp4: ["video/mp4"],
            pdf: ["application/pdf"]
        };
        var getMimetype = function(name) {
            return mimetypeMap[name]
        };
        var getAcceptString = function(str) {
            var types = getFileTypes(str);
            var result = [];
            for (var i = 0, len = types.length; i < len; i++) {
                var mime = getMimetype(types[i]);
                if (mime) {
                    result.push(mime)
                }
            }
            return result.join(",")
        };
        var sendBlob = function(url, xhr, file, formdata) {
            xhr.open(option.method, url, true);
            xhr.setRequestHeader("X-Requested-With", "XMLHttpRequest");
            var fd = new FormData();
            fd.append(option.fileObjName, file);
            if (formdata) {
                for (key in formdata) {
                    fd.append(key, formdata[key])
                }
            }
            xhr.send(fd)
        };
        var fileObj = null;
        this.each(function() {
            var _this = $(this);
            var instanceNumber = $(".uploadify").length + 1;
            var inputStr = '<input id="select_btn_' + instanceNumber + '" class="selectbtn" style="display:none;" type="file" name="fileselect[]"';
            inputStr += option.multi ? " multiple" : "";
            inputStr += ' accept="';
            inputStr += getAcceptString(option.fileTypeExts);
            inputStr += '"/>';
            inputStr += '<a id="file_upload_' + instanceNumber + '-button" href="javascript:void(0)" class="uploadify-button btn btn-primary">';
            inputStr += option.buttonText;
            inputStr += "</a>";
            var uploadFileListStr = '<div id="file_upload_' + instanceNumber + '-queue" class="uploadify-queue"></div>';
            _this.append(inputStr + uploadFileListStr);
            fileObj = {
                uploadAllowed: true,
                fileInput: _this.find(".selectbtn"),
                uploadFileList: _this.find(".uploadify-queue"),
                container: _this,
                url: option.uploader,
                fileFilter: [],
                uploadOver: false,
                filter: function(files) {
                    var arr = [];
                    var typeArray = getFileTypes(option.fileTypeExts);
                    if (typeArray.length > 0) {
                        for (var i = 0, len = files.length; i < len; i++) {
                            var thisFile = files[i];
                            if (parseInt(formatFileSize(thisFile.size, true)) > option.fileSizeLimit) {
                                alert("文件" + thisFile.name + "大小超出限制！");
                                continue
                            }
                            if ($.inArray(thisFile.name.split(".").pop().toLowerCase(), typeArray) >= 0 || $.inArray("*", typeArray) >= 0) {
                                arr.push(thisFile)
                            } else {
                                alert("文件" + thisFile.name + "类型不允许！")
                            }
                        }
                    }
                    return arr
                },
                funSelect: function(files) {
                    for (var i = 0, len = files.length; i < len; i++) {
                        var file = files[i];
                        var $html = $(option.itemTemplate.replace(/\${fileID}/g, "fileupload_" + instanceNumber + "_" + file.index).replace(/\${fileName}/g, file.name).replace(/\${fileSize}/g, formatFileSize(file.size)).replace(/\${instanceID}/g, _this.attr("id")));
                        if (option.auto) {
                            $html.find(".uploadbtn").remove()
                        }
                        var initWidth = 0,
                            initFileSize = "0KB",
                            initUppercent = "0%";
                        if (option.breakPoints) {
                            var uploadedSize = this.funGetUploadedSize(file);
                            initWidth = (uploadedSize / file.size * 100) + "%";
                            initFileSize = formatFileSize(uploadedSize);
                            initUppercent = (uploadedSize / file.size * 100).toFixed(2) + "%";
                            $html.find(".uploadify-progress-bar").css("width", initWidth)
                        }
                        this.uploadFileList.append($html);
                        if (option.showUploadedSize) {
                            var num = '<span class="progressnum"><span class="uploadedsize">' + initFileSize + '</span>/<span class="totalsize">${fileSize}</span></span>'.replace(/\${fileSize}/g, formatFileSize(file.size));
                            $html.find(".uploadify-progress").after(num)
                        }
                        if (option.showUploadedPercent) {
                            var percentText = '<span class="up_percent">' + initUppercent + "</span>";
                            $html.find(".uploadify-progress").after(percentText)
                        }
                        option.onSelect && option.onSelect(files);
                        if (option.auto) {
                            this.funUploadFile(file)
                        } else {
                            $html.find(".uploadbtn").on("click", (function(file) {
                                return function() {
                                    fileObj.funUploadFile(file)
                                }
                            })(file))
                        }
                        $html.find(".delfilebtn").on("click", (function(file) {
                            return function() {
                                fileObj.funDeleteFile(file.index)
                            }
                        })(file))
                    }
                },
                onProgress: function(file, loaded, total) {
                    var eleProgress = _this.find("#fileupload_" + instanceNumber + "_" + file.index + " .uploadify-progress");
                    var thisLoaded = loaded;
                    var lastLoaded = eleProgress.attr("lastLoaded") || 0;
                    loaded -= parseInt(lastLoaded);
                    var progressBar = eleProgress.children(".uploadify-progress-bar");
                    var oldWidth = option.breakPoints ? parseFloat(progressBar.get(0).style.width || 0) : 0;
                    var percent = (loaded / total * 100 + oldWidth).toFixed(2);
                    var percentText = percent > 100 ? "99.99%" : percent + "%";
                    if (option.showUploadedSize) {
                        eleProgress.nextAll(".progressnum .uploadedsize").text(formatFileSize(loaded));
                        eleProgress.nextAll(".progressnum .totalsize").text(formatFileSize(total))
                    }
                    if (option.showUploadedPercent) {
                        eleProgress.nextAll(".up_percent").text(percentText)
                    }
                    progressBar.css("width", percentText);
                    if (thisLoaded < option.fileSplitSize) {
                        eleProgress.attr("lastLoaded", thisLoaded)
                    } else {
                        eleProgress.removeAttr("lastLoaded")
                    }
                },
                funGetProgressWidth: function(index) {
                    var eleProgressBar = _this.find("#fileupload_" + instanceNumber + "_" + index + " .uploadify-progress-bar");
                    return eleProgressBar.get(0).style.width || ""
                },
                funGetUploadedSize: function(file) {
                    if (option.getUploadedSize) {
                        return option.getUploadedSize(file)
                    } else {
                        if (option.saveInfoLocal) {
                            return parseInt(localStorage.getItem(file.name)) || 0
                        }
                    }
                },
                funSaveUploadedSize: function(file, value) {
                    if (option.saveUploadedSize) {
                        option.saveUploadedSize(file, value)
                    } else {
                        if (option.saveInfoLocal) {
                            localStorage.setItem(file.name, value)
                        }
                    }
                },
                funGetFiles: function(e) {
                    var files = e.target.files;
                    files = this.filter(files);
                    for (var i = 0, len = files.length; i < len; i++) {
                        this.fileFilter.push(files[i])
                    }
                    this.funDealFiles(files);
                    return this
                },
                funDealFiles: function(files) {
                    var fileCount = _this.find(".uploadify-queue .uploadify-queue-item").length;
                    for (var i = 0, len = files.length; i < len; i++) {
                        files[i].index = ++fileCount;
                        files[i].id = "fileupload_" + instanceNumber + "_" + files[i].index
                    }
                    this.funSelect(files);
                    return this
                },
                funDeleteFile: function(index) {
                    for (var i = 0, len = this.fileFilter.length; i < len; i++) {
                        var file = this.fileFilter[i];
                        if (file.index == index) {
                            if (option.breakPoints) {
                                this.uploadAllowed = false
                            }
                            this.fileFilter.splice(i, 1);
                            _this.find("#fileupload_" + instanceNumber + "_" + index).fadeOut();
                            fileObj.fileInput.val("");
                            option.onCancel && option.onCancel(file);
                            break
                        }
                    }
                    return this
                },
                funUploadFile: function(file) {
                    var xhr = false;
                    var originalFile = file;
                    var thisfile = _this.find("#fileupload_" + instanceNumber + "_" + file.index);
                    var regulateView = function() {
                        if (fileObj.uploadOver) {
                            thisfile.find(".uploadify-progress-bar").css("width", "100%");
                            option.showUploadedSize && thisfile.find(".uploadedsize").text(thisfile.find(".totalsize").text());
                            option.showUploadedPercent && thisfile.find(".up_percent").text("100%")
                        }
                    };
                    try {
                        xhr = new XMLHttpRequest()
                    } catch (e) {
                        xhr = ActiveXobject("Msxml12.XMLHTTP")
                    }
                    if (option.breakPoints) {
                        var fileName = file.name,
                            fileId = file.id,
                            fileIndex = file.index,
                            fileSize = file.size;
                        var uploadedSize = parseInt(this.funGetUploadedSize(originalFile));
                        file = originalFile.slice(uploadedSize, uploadedSize + option.fileSplitSize);
                        file.name = fileName;
                        file.id = fileId;
                        file.index = fileIndex
                    }
                    if (xhr.upload && uploadedSize !== false) {
                        xhr.upload.addEventListener("progress", function(e) {
                            fileObj.onProgress(file, e.loaded, originalFile.size)
                        }, false);
                        xhr.onreadystatechange = function(e) {
                            if (xhr.readyState == 4) {
                                fileObj.uploadOver = true;
                                if (xhr.status == 200) {
                                    var returnData = JSON.parse(xhr.responseText);
                                    if (returnData.success) {
                                        if (option.breakPoints) {
                                            uploadedSize += option.fileSplitSize;
                                            fileObj.funSaveUploadedSize(originalFile, uploadedSize);
                                            if (uploadedSize < fileSize) {
                                                fileObj.uploadOver = false;
                                                if (fileObj.uploadAllowed) {
                                                    file = originalFile.slice(uploadedSize, uploadedSize + option.fileSplitSize);
                                                    file.name = fileName;
                                                    file.id = fileId;
                                                    file.index = fileIndex;
                                                    file.size = fileSize;
                                                    sendBlob(fileObj.url, xhr, file, option.formData)
                                                }
                                            } else {
                                                regulateView()
                                            }
                                        } else {
                                            regulateView()
                                        }
                                    }
                                    if (fileObj.uploadOver) {
                                        option.onUploadSuccess && option.onUploadSuccess(originalFile, xhr.responseText);
                                        setTimeout(function() {
                                            _this.find("#fileupload_" + instanceNumber + "_" + originalFile.index).fadeOut()
                                        }, option.removeTimeout)
                                    }
                                } else {
                                    fileObj.uploadOver && option.onUploadError && option.onUploadError(originalFile, xhr.responseText)
                                }
                                if (fileObj.uploadOver) {
                                    option.onUploadComplete && option.onUploadComplete(originalFile, xhr.responseText);
                                    fileObj.fileInput.val("")
                                }
                            }
                        };
                        option.onUploadStart && option.onUploadStart();
                        option.formData.fileName = originalFile.name;
                        option.formData.lastModifiedDate = originalFile.lastModifiedDate.getTime();
                        fileObj.uploadAllowed = true;
                        sendBlob(this.url, xhr, file, option.formData)
                    }
                },
                init: function() {
                    if (this.fileInput.length > 0) {
                        this.fileInput.change(function(e) {
                            fileObj.funGetFiles(e)
                        })
                    }
                    _this.find(".uploadify-button").on("click", function() {
                        _this.find(".selectbtn").trigger("click")
                    });
                    option.onInit && option.onInit()
                }
            };
            fileObj.init()
        });
        var returnObj = {
            stop: function() {
                fileObj.uploadOver = false;
                fileObj.uploadAllowed = false
            },
            upload: function(fileIndex) {
                if (fileIndex === "*") {
                    for (var i = 0, len = fileObj.fileFilter.length; i < len; i++) {
                        fileObj.funUploadFile(fileObj.fileFilter[i])
                    }
                } else {
                    var file = getFile(fileIndex, fileObj.fileFilter);
                    file && fileObj.funUploadFile(file)
                }
            },
            cancel: function(fileIndex) {
                if (fileIndex === "*") {
                    for (var i = 0, len = fileObj.fileFilter.length; i < len; i++) {
                        fileObj.funDeleteFile(++i)
                    }
                } else {
                    fileObj.funDeleteFile(fileIndex)
                }
            },
            disable: function(instanceID) {
                var parent = instanceID ? $("file_upload_" + instanceID + "-button") : $("body");
                parent.find(".uploadify-button").css("background-color", "#888").off("click")
            },
            ennable: function(instanceID) {
                var parent = instanceID ? $("file_upload_" + instanceID + "-button") : $("body");
                parent.find(".uploadify-button").css("background-color", "#707070").on("click", function() {
                    parent.find(".selectbtn").trigger("click")
                })
            },
            destroy: function() {
                fileObj.container.html("")
            },
            settings: function(name, value) {
                if (arguments.length == 1) {
                    return option[name]
                } else {
                    if (name == "formData") {
                        option.formData = $.extend(option.formData, value)
                    } else {
                        option[name] = value
                    }
                }
            },
            Huploadify: function() {
                var method = arguments[0];
                if (method in this) {
                    Array.prototype.splice.call(arguments, 0, 1);
                    this[method].apply(this[method], arguments)
                }
            }
        };
        return returnObj
    }
})(jQuery);
// 图标选择器(Bootstrap.iconChoosen v0.1)
(function($) {
    $.fn.iconChoosen = function() {
        var temp = '<div class="modal fade" id="iconChoosenModal" tabindex="-1" role="dialog" aria-labelledby="iconChoosenModalLabel" aria-hidden="true"><div class="modal-dialog modal-lg"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button><h4 class="modal-title">请选择图标</h4></div><div class="modal-body"><div class="iconChoosen"><ul class="nav nav-tabs"><li class="active"><a href="#iconChoosenTab1" data-toggle="tab">网页图标</a></li><li><a href="#iconChoosenTab2" data-toggle="tab">文件图标</a></li><li><a href="#iconChoosenTab3" data-toggle="tab">旋转图标</a></li><li><a href="#iconChoosenTab4" data-toggle="tab">表单图标</a></li><li><a href="#iconChoosenTab5" data-toggle="tab">支付图标</a></li><li><a href="#iconChoosenTab6" data-toggle="tab">编辑器图标</a></li><li><a href="#iconChoosenTab7" data-toggle="tab">方向图标</a></li><li><a href="#iconChoosenTab8" data-toggle="tab">播放器图标</a></li><li><a href="#iconChoosenTab9" data-toggle="tab">品牌图标</a></li><li><a href="#iconChoosenTab10" data-toggle="tab">医药图标</a></li></ul><div class="tab-content clearfix"><div class="blank"></div><div class="tab-pane fade in active" id="iconChoosenTab1"><a href="#" class="iconChoosenItem" title="fa-adjust"><i class="fa fa-adjust"></i><span>fa-adjust</span></a><a href="#" class="iconChoosenItem" title="fa-anchor"><i class="fa fa-anchor"></i><span>fa-anchor</span></a><a href="#" class="iconChoosenItem" title="fa-archive"><i class="fa fa-archive"></i><span>fa-archive</span></a><a href="#" class="iconChoosenItem" title="fa-arrows"><i class="fa fa-arrows"></i><span>fa-arrows</span></a><a href="#" class="iconChoosenItem" title="fa-arrows-h"><i class="fa fa-arrows-h"></i><span>fa-arrows-h</span></a><a href="#" class="iconChoosenItem" title="fa-arrows-v"><i class="fa fa-arrows-v"></i><span>fa-arrows-v</span></a><a href="#" class="iconChoosenItem" title="fa-asterisk"><i class="fa fa-asterisk"></i><span>fa-asterisk</span></a><a href="#" class="iconChoosenItem" title="fa-automobile"><i class="fa fa-automobile"></i><span>fa-automobile</span></a><a href="#" class="iconChoosenItem" title="fa-ban"><i class="fa fa-ban"></i><span>fa-ban</span></a><a href="#" class="iconChoosenItem" title="fa-bank"><i class="fa fa-bank"></i><span>fa-bank</span></a><a href="#" class="iconChoosenItem" title="fa-bar-chart-o"><i class="fa fa-bar-chart-o"></i><span>fa-bar-chart-o</span></a><a href="#" class="iconChoosenItem" title="fa-barcode"><i class="fa fa-barcode"></i><span>fa-barcode</span></a><a href="#" class="iconChoosenItem" title="fa-bars"><i class="fa fa-bars"></i><span>fa-bars</span></a><a href="#" class="iconChoosenItem" title="fa-beer"><i class="fa fa-beer"></i><span>fa-beer</span></a><a href="#" class="iconChoosenItem" title="fa-bell"><i class="fa fa-bell"></i><span>fa-bell</span></a><a href="#" class="iconChoosenItem" title="fa-bell-o"><i class="fa fa-bell-o"></i><span>fa-bell-o</span></a><a href="#" class="iconChoosenItem" title="fa-bolt"><i class="fa fa-bolt"></i><span>fa-bolt</span></a><a href="#" class="iconChoosenItem" title="fa-bomb"><i class="fa fa-bomb"></i><span>fa-bomb</span></a><a href="#" class="iconChoosenItem" title="fa-book"><i class="fa fa-book"></i><span>fa-book</span></a><a href="#" class="iconChoosenItem" title="fa-bookmark"><i class="fa fa-bookmark"></i><span>fa-bookmark</span></a><a href="#" class="iconChoosenItem" title="fa-bookmark-o"><i class="fa fa-bookmark-o"></i><span>fa-bookmark-o</span></a><a href="#" class="iconChoosenItem" title="fa-briefcase"><i class="fa fa-briefcase"></i><span>fa-briefcase</span></a><a href="#" class="iconChoosenItem" title="fa-bug"><i class="fa fa-bug"></i><span>fa-bug</span></a><a href="#" class="iconChoosenItem" title="fa-building"><i class="fa fa-building"></i><span>fa-building</span></a><a href="#" class="iconChoosenItem" title="fa-building-o"><i class="fa fa-building-o"></i><span>fa-building-o</span></a><a href="#" class="iconChoosenItem" title="fa-bullhorn"><i class="fa fa-bullhorn"></i><span>fa-bullhorn</span></a><a href="#" class="iconChoosenItem" title="fa-bullseye"><i class="fa fa-bullseye"></i><span>fa-bullseye</span></a><a href="#" class="iconChoosenItem" title="fa-cab"><i class="fa fa-cab"></i><span>fa-cab</span></a><a href="#" class="iconChoosenItem" title="fa-calendar"><i class="fa fa-calendar"></i><span>fa-calendar</span></a><a href="#" class="iconChoosenItem" title="fa-calendar-o"><i class="fa fa-calendar-o"></i><span>fa-calendar-o</span></a><a href="#" class="iconChoosenItem" title="fa-camera"><i class="fa fa-camera"></i><span>fa-camera</span></a><a href="#" class="iconChoosenItem" title="fa-camera-retro"><i class="fa fa-camera-retro"></i><span>fa-camera-retro</span></a><a href="#" class="iconChoosenItem" title="fa-car"><i class="fa fa-car"></i><span>fa-car</span></a><a href="#" class="iconChoosenItem" title="fa-caret-square-o-down"><i class="fa fa-caret-square-o-down"></i><span>fa-caret-square-o-down</span></a><a href="#" class="iconChoosenItem" title="fa-caret-square-o-left"><i class="fa fa-caret-square-o-left"></i><span>fa-caret-square-o-left</span></a><a href="#" class="iconChoosenItem" title="fa-caret-square-o-right"><i class="fa fa-caret-square-o-right"></i><span>fa-caret-square-o-right</span></a><a href="#" class="iconChoosenItem" title="fa-caret-square-o-up"><i class="fa fa-caret-square-o-up"></i><span>fa-caret-square-o-up</span></a><a href="#" class="iconChoosenItem" title="fa-certificate"><i class="fa fa-certificate"></i><span>fa-certificate</span></a><a href="#" class="iconChoosenItem" title="fa-check"><i class="fa fa-check"></i><span>fa-check</span></a><a href="#" class="iconChoosenItem" title="fa-check-circle"><i class="fa fa-check-circle"></i><span>fa-check-circle</span></a><a href="#" class="iconChoosenItem" title="fa-check-circle-o"><i class="fa fa-check-circle-o"></i><span>fa-check-circle-o</span></a><a href="#" class="iconChoosenItem" title="fa-check-square"><i class="fa fa-check-square"></i><span>fa-check-square</span></a><a href="#" class="iconChoosenItem" title="fa-check-square-o"><i class="fa fa-check-square-o"></i><span>fa-check-square-o</span></a><a href="#" class="iconChoosenItem" title="fa-child"><i class="fa fa-child"></i><span>fa-child</span></a><a href="#" class="iconChoosenItem" title="fa-circle"><i class="fa fa-circle"></i><span>fa-circle</span></a><a href="#" class="iconChoosenItem" title="fa-circle-o"><i class="fa fa-circle-o"></i><span>fa-circle-o</span></a><a href="#" class="iconChoosenItem" title="fa-circle-o-notch"><i class="fa fa-circle-o-notch"></i><span>fa-circle-o-notch</span></a><a href="#" class="iconChoosenItem" title="fa-circle-thin"><i class="fa fa-circle-thin"></i><span>fa-circle-thin</span></a><a href="#" class="iconChoosenItem" title="fa-clock-o"><i class="fa fa-clock-o"></i><span>fa-clock-o</span></a><a href="#" class="iconChoosenItem" title="fa-cloud"><i class="fa fa-cloud"></i><span>fa-cloud</span></a><a href="#" class="iconChoosenItem" title="fa-cloud-download"><i class="fa fa-cloud-download"></i><span>fa-cloud-download</span></a><a href="#" class="iconChoosenItem" title="fa-cloud-upload"><i class="fa fa-cloud-upload"></i><span>fa-cloud-upload</span></a><a href="#" class="iconChoosenItem" title="fa-code"><i class="fa fa-code"></i><span>fa-code</span></a><a href="#" class="iconChoosenItem" title="fa-code-fork"><i class="fa fa-code-fork"></i><span>fa-code-fork</span></a><a href="#" class="iconChoosenItem" title="fa-coffee"><i class="fa fa-coffee"></i><span>fa-coffee</span></a><a href="#" class="iconChoosenItem" title="fa-cog"><i class="fa fa-cog"></i><span>fa-cog</span></a><a href="#" class="iconChoosenItem" title="fa-cogs"><i class="fa fa-cogs"></i><span>fa-cogs</span></a><a href="#" class="iconChoosenItem" title="fa-comment"><i class="fa fa-comment"></i><span>fa-comment</span></a><a href="#" class="iconChoosenItem" title="fa-comment-o"><i class="fa fa-comment-o"></i><span>fa-comment-o</span></a><a href="#" class="iconChoosenItem" title="fa-comments"><i class="fa fa-comments"></i><span>fa-comments</span></a><a href="#" class="iconChoosenItem" title="fa-comments-o"><i class="fa fa-comments-o"></i><span>fa-comments-o</span></a><a href="#" class="iconChoosenItem" title="fa-compass"><i class="fa fa-compass"></i><span>fa-compass</span></a><a href="#" class="iconChoosenItem" title="fa-credit-card"><i class="fa fa-credit-card"></i><span>fa-credit-card</span></a><a href="#" class="iconChoosenItem" title="fa-crop"><i class="fa fa-crop"></i><span>fa-crop</span></a><a href="#" class="iconChoosenItem" title="fa-crosshairs"><i class="fa fa-crosshairs"></i><span>fa-crosshairs</span></a><a href="#" class="iconChoosenItem" title="fa-cube"><i class="fa fa-cube"></i><span>fa-cube</span></a><a href="#" class="iconChoosenItem" title="fa-cubes"><i class="fa fa-cubes"></i><span>fa-cubes</span></a><a href="#" class="iconChoosenItem" title="fa-cutlery"><i class="fa fa-cutlery"></i><span>fa-cutlery</span></a><a href="#" class="iconChoosenItem" title="fa-dashboard"><i class="fa fa-dashboard"></i><span>fa-dashboard</span></a><a href="#" class="iconChoosenItem" title="fa-database"><i class="fa fa-database"></i><span>fa-database</span></a><a href="#" class="iconChoosenItem" title="fa-desktop"><i class="fa fa-desktop"></i><span>fa-desktop</span></a><a href="#" class="iconChoosenItem" title="fa-dot-circle-o"><i class="fa fa-dot-circle-o"></i><span>fa-dot-circle-o</span></a><a href="#" class="iconChoosenItem" title="fa-download"><i class="fa fa-download"></i><span>fa-download</span></a><a href="#" class="iconChoosenItem" title="fa-edit"><i class="fa fa-edit"></i><span>fa-edit</span></a><a href="#" class="iconChoosenItem" title="fa-ellipsis-h"><i class="fa fa-ellipsis-h"></i><span>fa-ellipsis-h</span></a><a href="#" class="iconChoosenItem" title="fa-ellipsis-v"><i class="fa fa-ellipsis-v"></i><span>fa-ellipsis-v</span></a><a href="#" class="iconChoosenItem" title="fa-envelope"><i class="fa fa-envelope"></i><span>fa-envelope</span></a><a href="#" class="iconChoosenItem" title="fa-envelope-o"><i class="fa fa-envelope-o"></i><span>fa-envelope-o</span></a><a href="#" class="iconChoosenItem" title="fa-envelope-square"><i class="fa fa-envelope-square"></i><span>fa-envelope-square</span></a><a href="#" class="iconChoosenItem" title="fa-eraser"><i class="fa fa-eraser"></i><span>fa-eraser</span></a><a href="#" class="iconChoosenItem" title="fa-exchange"><i class="fa fa-exchange"></i><span>fa-exchange</span></a><a href="#" class="iconChoosenItem" title="fa-exclamation"><i class="fa fa-exclamation"></i><span>fa-exclamation</span></a><a href="#" class="iconChoosenItem" title="fa-exclamation-circle"><i class="fa fa-exclamation-circle"></i><span>fa-exclamation-circle</span></a><a href="#" class="iconChoosenItem" title="fa-exclamation-triangle"><i class="fa fa-exclamation-triangle"></i><span>fa-exclamation-triangle</span></a><a href="#" class="iconChoosenItem" title="fa-external-link"><i class="fa fa-external-link"></i><span>fa-external-link</span></a><a href="#" class="iconChoosenItem" title="fa-external-link-square"><i class="fa fa-external-link-square"></i><span>fa-external-link-square</span></a><a href="#" class="iconChoosenItem" title="fa-eye"><i class="fa fa-eye"></i><span>fa-eye</span></a><a href="#" class="iconChoosenItem" title="fa-eye-slash"><i class="fa fa-eye-slash"></i><span>fa-eye-slash</span></a><a href="#" class="iconChoosenItem" title="fa-fax"><i class="fa fa-fax"></i><span>fa-fax</span></a><a href="#" class="iconChoosenItem" title="fa-female"><i class="fa fa-female"></i><span>fa-female</span></a><a href="#" class="iconChoosenItem" title="fa-fighter-jet"><i class="fa fa-fighter-jet"></i><span>fa-fighter-jet</span></a><a href="#" class="iconChoosenItem" title="fa-file-archive-o"><i class="fa fa-file-archive-o"></i><span>fa-file-archive-o</span></a><a href="#" class="iconChoosenItem" title="fa-file-audio-o"><i class="fa fa-file-audio-o"></i><span>fa-file-audio-o</span></a><a href="#" class="iconChoosenItem" title="fa-file-code-o"><i class="fa fa-file-code-o"></i><span>fa-file-code-o</span></a><a href="#" class="iconChoosenItem" title="fa-file-excel-o"><i class="fa fa-file-excel-o"></i><span>fa-file-excel-o</span></a><a href="#" class="iconChoosenItem" title="fa-file-image-o"><i class="fa fa-file-image-o"></i><span>fa-file-image-o</span></a><a href="#" class="iconChoosenItem" title="fa-file-movie-o"><i class="fa fa-file-movie-o"></i><span>fa-file-movie-o</span></a><a href="#" class="iconChoosenItem" title="fa-file-pdf-o"><i class="fa fa-file-pdf-o"></i><span>fa-file-pdf-o</span></a><a href="#" class="iconChoosenItem" title="fa-file-photo-o"><i class="fa fa-file-photo-o"></i><span>fa-file-photo-o</span></a><a href="#" class="iconChoosenItem" title="fa-file-picture-o"><i class="fa fa-file-picture-o"></i><span>fa-file-picture-o</span></a><a href="#" class="iconChoosenItem" title="fa-file-powerpoint-o"><i class="fa fa-file-powerpoint-o"></i><span>fa-file-powerpoint-o</span></a><a href="#" class="iconChoosenItem" title="fa-file-sound-o"><i class="fa fa-file-sound-o"></i><span>fa-file-sound-o</span></a><a href="#" class="iconChoosenItem" title="fa-file-video-o"><i class="fa fa-file-video-o"></i><span>fa-file-video-o</span></a><a href="#" class="iconChoosenItem" title="fa-file-word-o"><i class="fa fa-file-word-o"></i><span>fa-file-word-o</span></a><a href="#" class="iconChoosenItem" title="fa-file-zip-o"><i class="fa fa-file-zip-o"></i><span>fa-file-zip-o</span></a><a href="#" class="iconChoosenItem" title="fa-film"><i class="fa fa-film"></i><span>fa-film</span></a><a href="#" class="iconChoosenItem" title="fa-filter"><i class="fa fa-filter"></i><span>fa-filter</span></a><a href="#" class="iconChoosenItem" title="fa-fire"><i class="fa fa-fire"></i><span>fa-fire</span></a><a href="#" class="iconChoosenItem" title="fa-fire-extinguisher"><i class="fa fa-fire-extinguisher"></i><span>fa-fire-extinguisher</span></a><a href="#" class="iconChoosenItem" title="fa-flag"><i class="fa fa-flag"></i><span>fa-flag</span></a><a href="#" class="iconChoosenItem" title="fa-flag-checkered"><i class="fa fa-flag-checkered"></i><span>fa-flag-checkered</span></a><a href="#" class="iconChoosenItem" title="fa-flag-o"><i class="fa fa-flag-o"></i><span>fa-flag-o</span></a><a href="#" class="iconChoosenItem" title="fa-flash"><i class="fa fa-flash"></i><span>fa-flash</span></a><a href="#" class="iconChoosenItem" title="fa-flask"><i class="fa fa-flask"></i><span>fa-flask</span></a><a href="#" class="iconChoosenItem" title="fa-folder"><i class="fa fa-folder"></i><span>fa-folder</span></a><a href="#" class="iconChoosenItem" title="fa-folder-o"><i class="fa fa-folder-o"></i><span>fa-folder-o</span></a><a href="#" class="iconChoosenItem" title="fa-folder-open"><i class="fa fa-folder-open"></i><span>fa-folder-open</span></a><a href="#" class="iconChoosenItem" title="fa-folder-open-o"><i class="fa fa-folder-open-o"></i><span>fa-folder-open-o</span></a><a href="#" class="iconChoosenItem" title="fa-frown-o"><i class="fa fa-frown-o"></i><span>fa-frown-o</span></a><a href="#" class="iconChoosenItem" title="fa-gamepad"><i class="fa fa-gamepad"></i><span>fa-gamepad</span></a><a href="#" class="iconChoosenItem" title="fa-gavel"><i class="fa fa-gavel"></i><span>fa-gavel</span></a><a href="#" class="iconChoosenItem" title="fa-gear"><i class="fa fa-gear"></i><span>fa-gear</span></a><a href="#" class="iconChoosenItem" title="fa-gears"><i class="fa fa-gears"></i><span>fa-gears</span></a><a href="#" class="iconChoosenItem" title="fa-gift"><i class="fa fa-gift"></i><span>fa-gift</span></a><a href="#" class="iconChoosenItem" title="fa-glass"><i class="fa fa-glass"></i><span>fa-glass</span></a><a href="#" class="iconChoosenItem" title="fa-globe"><i class="fa fa-globe"></i><span>fa-globe</span></a><a href="#" class="iconChoosenItem" title="fa-graduation-cap"><i class="fa fa-graduation-cap"></i><span>fa-graduation-cap</span></a><a href="#" class="iconChoosenItem" title="fa-group"><i class="fa fa-group"></i><span>fa-group</span></a><a href="#" class="iconChoosenItem" title="fa-hdd-o"><i class="fa fa-hdd-o"></i><span>fa-hdd-o</span></a><a href="#" class="iconChoosenItem" title="fa-headphones"><i class="fa fa-headphones"></i><span>fa-headphones</span></a><a href="#" class="iconChoosenItem" title="fa-heart"><i class="fa fa-heart"></i><span>fa-heart</span></a><a href="#" class="iconChoosenItem" title="fa-heart-o"><i class="fa fa-heart-o"></i><span>fa-heart-o</span></a><a href="#" class="iconChoosenItem" title="fa-history"><i class="fa fa-history"></i><span>fa-history</span></a><a href="#" class="iconChoosenItem" title="fa-home"><i class="fa fa-home"></i><span>fa-home</span></a><a href="#" class="iconChoosenItem" title="fa-image"><i class="fa fa-image"></i><span>fa-image</span></a><a href="#" class="iconChoosenItem" title="fa-inbox"><i class="fa fa-inbox"></i><span>fa-inbox</span></a><a href="#" class="iconChoosenItem" title="fa-info"><i class="fa fa-info"></i><span>fa-info</span></a><a href="#" class="iconChoosenItem" title="fa-info-circle"><i class="fa fa-info-circle"></i><span>fa-info-circle</span></a><a href="#" class="iconChoosenItem" title="fa-institution"><i class="fa fa-institution"></i><span>fa-institution</span></a><a href="#" class="iconChoosenItem" title="fa-key"><i class="fa fa-key"></i><span>fa-key</span></a><a href="#" class="iconChoosenItem" title="fa-keyboard-o"><i class="fa fa-keyboard-o"></i><span>fa-keyboard-o</span></a><a href="#" class="iconChoosenItem" title="fa-language"><i class="fa fa-language"></i><span>fa-language</span></a><a href="#" class="iconChoosenItem" title="fa-laptop"><i class="fa fa-laptop"></i><span>fa-laptop</span></a><a href="#" class="iconChoosenItem" title="fa-leaf"><i class="fa fa-leaf"></i><span>fa-leaf</span></a><a href="#" class="iconChoosenItem" title="fa-legal"><i class="fa fa-legal"></i><span>fa-legal</span></a><a href="#" class="iconChoosenItem" title="fa-lemon-o"><i class="fa fa-lemon-o"></i><span>fa-lemon-o</span></a><a href="#" class="iconChoosenItem" title="fa-level-down"><i class="fa fa-level-down"></i><span>fa-level-down</span></a><a href="#" class="iconChoosenItem" title="fa-level-up"><i class="fa fa-level-up"></i><span>fa-level-up</span></a><a href="#" class="iconChoosenItem" title="fa-life-bouy"><i class="fa fa-life-bouy"></i><span>fa-life-bouy</span></a><a href="#" class="iconChoosenItem" title="fa-life-ring"><i class="fa fa-life-ring"></i><span>fa-life-ring</span></a><a href="#" class="iconChoosenItem" title="fa-life-saver"><i class="fa fa-life-saver"></i><span>fa-life-saver</span></a><a href="#" class="iconChoosenItem" title="fa-lightbulb-o"><i class="fa fa-lightbulb-o"></i><span>fa-lightbulb-o</span></a><a href="#" class="iconChoosenItem" title="fa-location-arrow"><i class="fa fa-location-arrow"></i><span>fa-location-arrow</span></a><a href="#" class="iconChoosenItem" title="fa-lock"><i class="fa fa-lock"></i><span>fa-lock</span></a><a href="#" class="iconChoosenItem" title="fa-magic"><i class="fa fa-magic"></i><span>fa-magic</span></a><a href="#" class="iconChoosenItem" title="fa-magnet"><i class="fa fa-magnet"></i><span>fa-magnet</span></a><a href="#" class="iconChoosenItem" title="fa-mail-forward"><i class="fa fa-mail-forward"></i><span>fa-mail-forward</span></a><a href="#" class="iconChoosenItem" title="fa-mail-reply"><i class="fa fa-mail-reply"></i><span>fa-mail-reply</span></a><a href="#" class="iconChoosenItem" title="fa-mail-reply-all"><i class="fa fa-mail-reply-all"></i><span>fa-mail-reply-all</span></a><a href="#" class="iconChoosenItem" title="fa-male"><i class="fa fa-male"></i><span>fa-male</span></a><a href="#" class="iconChoosenItem" title="fa-map-marker"><i class="fa fa-map-marker"></i><span>fa-map-marker</span></a><a href="#" class="iconChoosenItem" title="fa-meh-o"><i class="fa fa-meh-o"></i><span>fa-meh-o</span></a><a href="#" class="iconChoosenItem" title="fa-microphone"><i class="fa fa-microphone"></i><span>fa-microphone</span></a><a href="#" class="iconChoosenItem" title="fa-microphone-slash"><i class="fa fa-microphone-slash"></i><span>fa-microphone-slash</span></a><a href="#" class="iconChoosenItem" title="fa-minus"><i class="fa fa-minus"></i><span>fa-minus</span></a><a href="#" class="iconChoosenItem" title="fa-minus-circle"><i class="fa fa-minus-circle"></i><span>fa-minus-circle</span></a><a href="#" class="iconChoosenItem" title="fa-minus-square"><i class="fa fa-minus-square"></i><span>fa-minus-square</span></a><a href="#" class="iconChoosenItem" title="fa-minus-square-o"><i class="fa fa-minus-square-o"></i><span>fa-minus-square-o</span></a><a href="#" class="iconChoosenItem" title="fa-mobile"><i class="fa fa-mobile"></i><span>fa-mobile</span></a><a href="#" class="iconChoosenItem" title="fa-mobile-phone"><i class="fa fa-mobile-phone"></i><span>fa-mobile-phone</span></a><a href="#" class="iconChoosenItem" title="fa-money"><i class="fa fa-money"></i><span>fa-money</span></a><a href="#" class="iconChoosenItem" title="fa-moon-o"><i class="fa fa-moon-o"></i><span>fa-moon-o</span></a><a href="#" class="iconChoosenItem" title="fa-mortar-board"><i class="fa fa-mortar-board"></i><span>fa-mortar-board</span></a><a href="#" class="iconChoosenItem" title="fa-music"><i class="fa fa-music"></i><span>fa-music</span></a><a href="#" class="iconChoosenItem" title="fa-navicon"><i class="fa fa-navicon"></i><span>fa-navicon</span></a><a href="#" class="iconChoosenItem" title="fa-paper-plane"><i class="fa fa-paper-plane"></i><span>fa-paper-plane</span></a><a href="#" class="iconChoosenItem" title="fa-paper-plane-o"><i class="fa fa-paper-plane-o"></i><span>fa-paper-plane-o</span></a><a href="#" class="iconChoosenItem" title="fa-paw"><i class="fa fa-paw"></i><span>fa-paw</span></a><a href="#" class="iconChoosenItem" title="fa-pencil"><i class="fa fa-pencil"></i><span>fa-pencil</span></a><a href="#" class="iconChoosenItem" title="fa-pencil-square"><i class="fa fa-pencil-square"></i><span>fa-pencil-square</span></a><a href="#" class="iconChoosenItem" title="fa-pencil-square-o"><i class="fa fa-pencil-square-o"></i><span>fa-pencil-square-o</span></a><a href="#" class="iconChoosenItem" title="fa-phone"><i class="fa fa-phone"></i><span>fa-phone</span></a><a href="#" class="iconChoosenItem" title="fa-phone-square"><i class="fa fa-phone-square"></i><span>fa-phone-square</span></a><a href="#" class="iconChoosenItem" title="fa-photo"><i class="fa fa-photo"></i><span>fa-photo</span></a><a href="#" class="iconChoosenItem" title="fa-picture-o"><i class="fa fa-picture-o"></i><span>fa-picture-o</span></a><a href="#" class="iconChoosenItem" title="fa-plane"><i class="fa fa-plane"></i><span>fa-plane</span></a><a href="#" class="iconChoosenItem" title="fa-plus"><i class="fa fa-plus"></i><span>fa-plus</span></a><a href="#" class="iconChoosenItem" title="fa-plus-circle"><i class="fa fa-plus-circle"></i><span>fa-plus-circle</span></a><a href="#" class="iconChoosenItem" title="fa-plus-square"><i class="fa fa-plus-square"></i><span>fa-plus-square</span></a><a href="#" class="iconChoosenItem" title="fa-plus-square-o"><i class="fa fa-plus-square-o"></i><span>fa-plus-square-o</span></a><a href="#" class="iconChoosenItem" title="fa-power-off"><i class="fa fa-power-off"></i><span>fa-power-off</span></a><a href="#" class="iconChoosenItem" title="fa-print"><i class="fa fa-print"></i><span>fa-print</span></a><a href="#" class="iconChoosenItem" title="fa-puzzle-piece"><i class="fa fa-puzzle-piece"></i><span>fa-puzzle-piece</span></a><a href="#" class="iconChoosenItem" title="fa-qrcode"><i class="fa fa-qrcode"></i><span>fa-qrcode</span></a><a href="#" class="iconChoosenItem" title="fa-question"><i class="fa fa-question"></i><span>fa-question</span></a><a href="#" class="iconChoosenItem" title="fa-question-circle"><i class="fa fa-question-circle"></i><span>fa-question-circle</span></a><a href="#" class="iconChoosenItem" title="fa-quote-left"><i class="fa fa-quote-left"></i><span>fa-quote-left</span></a><a href="#" class="iconChoosenItem" title="fa-quote-right"><i class="fa fa-quote-right"></i><span>fa-quote-right</span></a><a href="#" class="iconChoosenItem" title="fa-random"><i class="fa fa-random"></i><span>fa-random</span></a><a href="#" class="iconChoosenItem" title="fa-recycle"><i class="fa fa-recycle"></i><span>fa-recycle</span></a><a href="#" class="iconChoosenItem" title="fa-refresh"><i class="fa fa-refresh"></i><span>fa-refresh</span></a><a href="#" class="iconChoosenItem" title="fa-reorder"><i class="fa fa-reorder"></i><span>fa-reorder</span></a><a href="#" class="iconChoosenItem" title="fa-reply"><i class="fa fa-reply"></i><span>fa-reply</span></a><a href="#" class="iconChoosenItem" title="fa-reply-all"><i class="fa fa-reply-all"></i><span>fa-reply-all</span></a><a href="#" class="iconChoosenItem" title="fa-retweet"><i class="fa fa-retweet"></i><span>fa-retweet</span></a><a href="#" class="iconChoosenItem" title="fa-road"><i class="fa fa-road"></i><span>fa-road</span></a><a href="#" class="iconChoosenItem" title="fa-rocket"><i class="fa fa-rocket"></i><span>fa-rocket</span></a><a href="#" class="iconChoosenItem" title="fa-rss"><i class="fa fa-rss"></i><span>fa-rss</span></a><a href="#" class="iconChoosenItem" title="fa-rss-square"><i class="fa fa-rss-square"></i><span>fa-rss-square</span></a><a href="#" class="iconChoosenItem" title="fa-search"><i class="fa fa-search"></i><span>fa-search</span></a><a href="#" class="iconChoosenItem" title="fa-search-minus"><i class="fa fa-search-minus"></i><span>fa-search-minus</span></a><a href="#" class="iconChoosenItem" title="fa-search-plus"><i class="fa fa-search-plus"></i><span>fa-search-plus</span></a><a href="#" class="iconChoosenItem" title="fa-send"><i class="fa fa-send"></i><span>fa-send</span></a><a href="#" class="iconChoosenItem" title="fa-send-o"><i class="fa fa-send-o"></i><span>fa-send-o</span></a><a href="#" class="iconChoosenItem" title="fa-share"><i class="fa fa-share"></i><span>fa-share</span></a><a href="#" class="iconChoosenItem" title="fa-share-alt"><i class="fa fa-share-alt"></i><span>fa-share-alt</span></a><a href="#" class="iconChoosenItem" title="fa-share-alt-square"><i class="fa fa-share-alt-square"></i><span>fa-share-alt-square</span></a><a href="#" class="iconChoosenItem" title="fa-share-square"><i class="fa fa-share-square"></i><span>fa-share-square</span></a><a href="#" class="iconChoosenItem" title="fa-share-square-o"><i class="fa fa-share-square-o"></i><span>fa-share-square-o</span></a><a href="#" class="iconChoosenItem" title="fa-shield"><i class="fa fa-shield"></i><span>fa-shield</span></a><a href="#" class="iconChoosenItem" title="fa-shopping-cart"><i class="fa fa-shopping-cart"></i><span>fa-shopping-cart</span></a><a href="#" class="iconChoosenItem" title="fa-sign-in"><i class="fa fa-sign-in"></i><span>fa-sign-in</span></a><a href="#" class="iconChoosenItem" title="fa-sign-out"><i class="fa fa-sign-out"></i><span>fa-sign-out</span></a><a href="#" class="iconChoosenItem" title="fa-signal"><i class="fa fa-signal"></i><span>fa-signal</span></a><a href="#" class="iconChoosenItem" title="fa-sitemap"><i class="fa fa-sitemap"></i><span>fa-sitemap</span></a><a href="#" class="iconChoosenItem" title="fa-sliders"><i class="fa fa-sliders"></i><span>fa-sliders</span></a><a href="#" class="iconChoosenItem" title="fa-smile-o"><i class="fa fa-smile-o"></i><span>fa-smile-o</span></a><a href="#" class="iconChoosenItem" title="fa-sort"><i class="fa fa-sort"></i><span>fa-sort</span></a><a href="#" class="iconChoosenItem" title="fa-sort-alpha-asc"><i class="fa fa-sort-alpha-asc"></i><span>fa-sort-alpha-asc</span></a><a href="#" class="iconChoosenItem" title="fa-sort-alpha-desc"><i class="fa fa-sort-alpha-desc"></i><span>fa-sort-alpha-desc</span></a><a href="#" class="iconChoosenItem" title="fa-sort-amount-asc"><i class="fa fa-sort-amount-asc"></i><span>fa-sort-amount-asc</span></a><a href="#" class="iconChoosenItem" title="fa-sort-amount-desc"><i class="fa fa-sort-amount-desc"></i><span>fa-sort-amount-desc</span></a><a href="#" class="iconChoosenItem" title="fa-sort-asc"><i class="fa fa-sort-asc"></i><span>fa-sort-asc</span></a><a href="#" class="iconChoosenItem" title="fa-sort-desc"><i class="fa fa-sort-desc"></i><span>fa-sort-desc</span></a><a href="#" class="iconChoosenItem" title="fa-sort-down"><i class="fa fa-sort-down"></i><span>fa-sort-down</span></a><a href="#" class="iconChoosenItem" title="fa-sort-numeric-asc"><i class="fa fa-sort-numeric-asc"></i><span>fa-sort-numeric-asc</span></a><a href="#" class="iconChoosenItem" title="fa-sort-numeric-desc"><i class="fa fa-sort-numeric-desc"></i><span>fa-sort-numeric-desc</span></a><a href="#" class="iconChoosenItem" title="fa-sort-up"><i class="fa fa-sort-up"></i><span>fa-sort-up</span></a><a href="#" class="iconChoosenItem" title="fa-space-shuttle"><i class="fa fa-space-shuttle"></i><span>fa-space-shuttle</span></a><a href="#" class="iconChoosenItem" title="fa-spinner"><i class="fa fa-spinner"></i><span>fa-spinner</span></a><a href="#" class="iconChoosenItem" title="fa-spoon"><i class="fa fa-spoon"></i><span>fa-spoon</span></a><a href="#" class="iconChoosenItem" title="fa-square"><i class="fa fa-square"></i><span>fa-square</span></a><a href="#" class="iconChoosenItem" title="fa-square-o"><i class="fa fa-square-o"></i><span>fa-square-o</span></a><a href="#" class="iconChoosenItem" title="fa-star"><i class="fa fa-star"></i><span>fa-star</span></a><a href="#" class="iconChoosenItem" title="fa-star-half"><i class="fa fa-star-half"></i><span>fa-star-half</span></a><a href="#" class="iconChoosenItem" title="fa-star-half-empty"><i class="fa fa-star-half-empty"></i><span>fa-star-half-empty</span></a><a href="#" class="iconChoosenItem" title="fa-star-half-full"><i class="fa fa-star-half-full"></i><span>fa-star-half-full</span></a><a href="#" class="iconChoosenItem" title="fa-star-half-o"><i class="fa fa-star-half-o"></i><span>fa-star-half-o</span></a><a href="#" class="iconChoosenItem" title="fa-star-o"><i class="fa fa-star-o"></i><span>fa-star-o</span></a><a href="#" class="iconChoosenItem" title="fa-suitcase"><i class="fa fa-suitcase"></i><span>fa-suitcase</span></a><a href="#" class="iconChoosenItem" title="fa-sun-o"><i class="fa fa-sun-o"></i><span>fa-sun-o</span></a><a href="#" class="iconChoosenItem" title="fa-support"><i class="fa fa-support"></i><span>fa-support</span></a><a href="#" class="iconChoosenItem" title="fa-tablet"><i class="fa fa-tablet"></i><span>fa-tablet</span></a><a href="#" class="iconChoosenItem" title="fa-tachometer"><i class="fa fa-tachometer"></i><span>fa-tachometer</span></a><a href="#" class="iconChoosenItem" title="fa-tag"><i class="fa fa-tag"></i><span>fa-tag</span></a><a href="#" class="iconChoosenItem" title="fa-tags"><i class="fa fa-tags"></i><span>fa-tags</span></a><a href="#" class="iconChoosenItem" title="fa-tasks"><i class="fa fa-tasks"></i><span>fa-tasks</span></a><a href="#" class="iconChoosenItem" title="fa-taxi"><i class="fa fa-taxi"></i><span>fa-taxi</span></a><a href="#" class="iconChoosenItem" title="fa-terminal"><i class="fa fa-terminal"></i><span>fa-terminal</span></a><a href="#" class="iconChoosenItem" title="fa-thumb-tack"><i class="fa fa-thumb-tack"></i><span>fa-thumb-tack</span></a><a href="#" class="iconChoosenItem" title="fa-thumbs-down"><i class="fa fa-thumbs-down"></i><span>fa-thumbs-down</span></a><a href="#" class="iconChoosenItem" title="fa-thumbs-o-down"><i class="fa fa-thumbs-o-down"></i><span>fa-thumbs-o-down</span></a><a href="#" class="iconChoosenItem" title="fa-thumbs-o-up"><i class="fa fa-thumbs-o-up"></i><span>fa-thumbs-o-up</span></a><a href="#" class="iconChoosenItem" title="fa-thumbs-up"><i class="fa fa-thumbs-up"></i><span>fa-thumbs-up</span></a><a href="#" class="iconChoosenItem" title="fa-ticket"><i class="fa fa-ticket"></i><span>fa-ticket</span></a><a href="#" class="iconChoosenItem" title="fa-times"><i class="fa fa-times"></i><span>fa-times</span></a><a href="#" class="iconChoosenItem" title="fa-times-circle"><i class="fa fa-times-circle"></i><span>fa-times-circle</span></a><a href="#" class="iconChoosenItem" title="fa-times-circle-o"><i class="fa fa-times-circle-o"></i><span>fa-times-circle-o</span></a><a href="#" class="iconChoosenItem" title="fa-tint"><i class="fa fa-tint"></i><span>fa-tint</span></a><a href="#" class="iconChoosenItem" title="fa-toggle-down"><i class="fa fa-toggle-down"></i><span>fa-toggle-down</span></a><a href="#" class="iconChoosenItem" title="fa-toggle-left"><i class="fa fa-toggle-left"></i><span>fa-toggle-left</span></a><a href="#" class="iconChoosenItem" title="fa-toggle-right"><i class="fa fa-toggle-right"></i><span>fa-toggle-right</span></a><a href="#" class="iconChoosenItem" title="fa-toggle-up"><i class="fa fa-toggle-up"></i><span>fa-toggle-up</span></a><a href="#" class="iconChoosenItem" title="fa-trash-o"><i class="fa fa-trash-o"></i><span>fa-trash-o</span></a><a href="#" class="iconChoosenItem" title="fa-tree"><i class="fa fa-tree"></i><span>fa-tree</span></a><a href="#" class="iconChoosenItem" title="fa-trophy"><i class="fa fa-trophy"></i><span>fa-trophy</span></a><a href="#" class="iconChoosenItem" title="fa-truck"><i class="fa fa-truck"></i><span>fa-truck</span></a><a href="#" class="iconChoosenItem" title="fa-umbrella"><i class="fa fa-umbrella"></i><span>fa-umbrella</span></a><a href="#" class="iconChoosenItem" title="fa-university"><i class="fa fa-university"></i><span>fa-university</span></a><a href="#" class="iconChoosenItem" title="fa-unlock"><i class="fa fa-unlock"></i><span>fa-unlock</span></a><a href="#" class="iconChoosenItem" title="fa-unlock-alt"><i class="fa fa-unlock-alt"></i><span>fa-unlock-alt</span></a><a href="#" class="iconChoosenItem" title="fa-unsorted"><i class="fa fa-unsorted"></i><span>fa-unsorted</span></a><a href="#" class="iconChoosenItem" title="fa-upload"><i class="fa fa-upload"></i><span>fa-upload</span></a><a href="#" class="iconChoosenItem" title="fa-user"><i class="fa fa-user"></i><span>fa-user</span></a><a href="#" class="iconChoosenItem" title="fa-users"><i class="fa fa-users"></i><span>fa-users</span></a><a href="#" class="iconChoosenItem" title="fa-video-camera"><i class="fa fa-video-camera"></i><span>fa-video-camera</span></a><a href="#" class="iconChoosenItem" title="fa-volume-down"><i class="fa fa-volume-down"></i><span>fa-volume-down</span></a><a href="#" class="iconChoosenItem" title="fa-volume-off"><i class="fa fa-volume-off"></i><span>fa-volume-off</span></a><a href="#" class="iconChoosenItem" title="fa-volume-up"><i class="fa fa-volume-up"></i><span>fa-volume-up</span></a><a href="#" class="iconChoosenItem" title="fa-warning"><i class="fa fa-warning"></i><span>fa-warning</span></a><a href="#" class="iconChoosenItem" title="fa-wheelchair"><i class="fa fa-wheelchair"></i><span>fa-wheelchair</span></a><a href="#" class="iconChoosenItem" title="fa-wrench"><i class="fa fa-wrench"></i><span>fa-wrench</span></a></div><div class="tab-pane fade" id="iconChoosenTab2"><a href="#" class="iconChoosenItem" title="fa-file"><i class="fa fa-file"></i><span>fa-file</span></a><a href="#" class="iconChoosenItem" title="fa-file-archive-o"><i class="fa fa-file-archive-o"></i><span>fa-file-archive-o</span></a><a href="#" class="iconChoosenItem" title="fa-file-audio-o"><i class="fa fa-file-audio-o"></i><span>fa-file-audio-o</span></a><a href="#" class="iconChoosenItem" title="fa-file-code-o"><i class="fa fa-file-code-o"></i><span>fa-file-code-o</span></a><a href="#" class="iconChoosenItem" title="fa-file-excel-o"><i class="fa fa-file-excel-o"></i><span>fa-file-excel-o</span></a><a href="#" class="iconChoosenItem" title="fa-file-image-o"><i class="fa fa-file-image-o"></i><span>fa-file-image-o</span></a><a href="#" class="iconChoosenItem" title="fa-file-movie-o"><i class="fa fa-file-movie-o"></i><span>fa-file-movie-o</span></a><a href="#" class="iconChoosenItem" title="fa-file-o"><i class="fa fa-file-o"></i><span>fa-file-o</span></a><a href="#" class="iconChoosenItem" title="fa-file-pdf-o"><i class="fa fa-file-pdf-o"></i><span>fa-file-pdf-o</span></a><a href="#" class="iconChoosenItem" title="fa-file-photo-o"><i class="fa fa-file-photo-o"></i><span>fa-file-photo-o</span></a><a href="#" class="iconChoosenItem" title="fa-file-picture-o"><i class="fa fa-file-picture-o"></i><span>fa-file-picture-o</span></a><a href="#" class="iconChoosenItem" title="fa-file-powerpoint-o"><i class="fa fa-file-powerpoint-o"></i><span>fa-file-powerpoint-o</span></a><a href="#" class="iconChoosenItem" title="fa-file-sound-o"><i class="fa fa-file-sound-o"></i><span>fa-file-sound-o</span></a><a href="#" class="iconChoosenItem" title="fa-file-text"><i class="fa fa-file-text"></i><span>fa-file-text</span></a><a href="#" class="iconChoosenItem" title="fa-file-text-o"><i class="fa fa-file-text-o"></i><span>fa-file-text-o</span></a><a href="#" class="iconChoosenItem" title="fa-file-video-o"><i class="fa fa-file-video-o"></i><span>fa-file-video-o</span></a><a href="#" class="iconChoosenItem" title="fa-file-word-o"><i class="fa fa-file-word-o"></i><span>fa-file-word-o</span></a><a href="#" class="iconChoosenItem" title="fa-file-zip-o"><i class="fa fa-file-zip-o"></i><span>fa-file-zip-o</span></a></div><div class="tab-pane fade" id="iconChoosenTab3"><a href="#" class="iconChoosenItem" title="fa-circle-o-notch"><i class="fa fa-circle-o-notch"></i><span>fa-circle-o-notch</span></a><a href="#" class="iconChoosenItem" title="fa-cog"><i class="fa fa-cog"></i><span>fa-cog</span></a><a href="#" class="iconChoosenItem" title="fa-gear"><i class="fa fa-gear"></i><span>fa-gear</span></a><a href="#" class="iconChoosenItem" title="fa-refresh"><i class="fa fa-refresh"></i><span>fa-refresh</span></a><a href="#" class="iconChoosenItem" title="fa-spinner"><i class="fa fa-spinner"></i><span>fa-spinner</span></a></div><div class="tab-pane fade" id="iconChoosenTab4"><a href="#" class="iconChoosenItem" title="fa-check-square"><i class="fa fa-check-square"></i><span>fa-check-square</span></a><a href="#" class="iconChoosenItem" title="fa-check-square-o"><i class="fa fa-check-square-o"></i><span>fa-check-square-o</span></a><a href="#" class="iconChoosenItem" title="fa-circle"><i class="fa fa-circle"></i><span>fa-circle</span></a><a href="#" class="iconChoosenItem" title="fa-circle-o"><i class="fa fa-circle-o"></i><span>fa-circle-o</span></a><a href="#" class="iconChoosenItem" title="fa-dot-circle-o"><i class="fa fa-dot-circle-o"></i><span>fa-dot-circle-o</span></a><a href="#" class="iconChoosenItem" title="fa-minus-square"><i class="fa fa-minus-square"></i><span>fa-minus-square</span></a><a href="#" class="iconChoosenItem" title="fa-minus-square-o"><i class="fa fa-minus-square-o"></i><span>fa-minus-square-o</span></a><a href="#" class="iconChoosenItem" title="fa-plus-square"><i class="fa fa-plus-square"></i><span>fa-plus-square</span></a><a href="#" class="iconChoosenItem" title="fa-plus-square-o"><i class="fa fa-plus-square-o"></i><span>fa-plus-square-o</span></a><a href="#" class="iconChoosenItem" title="fa-square"><i class="fa fa-square"></i><span>fa-square</span></a><a href="#" class="iconChoosenItem" title="fa-square-o"><i class="fa fa-square-o"></i><span>fa-square-o</span></a></div><div class="tab-pane fade" id="iconChoosenTab5"><a href="#" class="iconChoosenItem" title="fa-bitcoin"><i class="fa fa-bitcoin"></i><span>fa-bitcoin</span></a><a href="#" class="iconChoosenItem" title="fa-btc"><i class="fa fa-btc"></i><span>fa-btc</span></a><a href="#" class="iconChoosenItem" title="fa-cny"><i class="fa fa-cny"></i><span>fa-cny</span></a><a href="#" class="iconChoosenItem" title="fa-dollar"><i class="fa fa-dollar"></i><span>fa-dollar</span></a><a href="#" class="iconChoosenItem" title="fa-eur"><i class="fa fa-eur"></i><span>fa-eur</span></a><a href="#" class="iconChoosenItem" title="fa-euro"><i class="fa fa-euro"></i><span>fa-euro</span></a><a href="#" class="iconChoosenItem" title="fa-gbp"><i class="fa fa-gbp"></i><span>fa-gbp</span></a><a href="#" class="iconChoosenItem" title="fa-inr"><i class="fa fa-inr"></i><span>fa-inr</span></a><a href="#" class="iconChoosenItem" title="fa-jpy"><i class="fa fa-jpy"></i><span>fa-jpy</span></a><a href="#" class="iconChoosenItem" title="fa-krw"><i class="fa fa-krw"></i><span>fa-krw</span></a><a href="#" class="iconChoosenItem" title="fa-money"><i class="fa fa-money"></i><span>fa-money</span></a><a href="#" class="iconChoosenItem" title="fa-rmb"><i class="fa fa-rmb"></i><span>fa-rmb</span></a><a href="#" class="iconChoosenItem" title="fa-rouble"><i class="fa fa-rouble"></i><span>fa-rouble</span></a><a href="#" class="iconChoosenItem" title="fa-rub"><i class="fa fa-rub"></i><span>fa-rub</span></a><a href="#" class="iconChoosenItem" title="fa-ruble"><i class="fa fa-ruble"></i><span>fa-ruble</span></a><a href="#" class="iconChoosenItem" title="fa-rupee"><i class="fa fa-rupee"></i><span>fa-rupee</span></a><a href="#" class="iconChoosenItem" title="fa-try"><i class="fa fa-try"></i><span>fa-try</span></a><a href="#" class="iconChoosenItem" title="fa-turkish-lira"><i class="fa fa-turkish-lira"></i><span>fa-turkish-lira</span></a><a href="#" class="iconChoosenItem" title="fa-usd"><i class="fa fa-usd"></i><span>fa-usd</span></a><a href="#" class="iconChoosenItem" title="fa-won"><i class="fa fa-won"></i><span>fa-won</span></a><a href="#" class="iconChoosenItem" title="fa-yen"><i class="fa fa-yen"></i><span>fa-yen</span></a></div><div class="tab-pane fade" id="iconChoosenTab6"><a href="#" class="iconChoosenItem" title="fa-align-center"><i class="fa fa-align-center"></i><span>fa-align-center</span></a><a href="#" class="iconChoosenItem" title="fa-align-justify"><i class="fa fa-align-justify"></i><span>fa-align-justify</span></a><a href="#" class="iconChoosenItem" title="fa-align-left"><i class="fa fa-align-left"></i><span>fa-align-left</span></a><a href="#" class="iconChoosenItem" title="fa-align-right"><i class="fa fa-align-right"></i><span>fa-align-right</span></a><a href="#" class="iconChoosenItem" title="fa-bold"><i class="fa fa-bold"></i><span>fa-bold</span></a><a href="#" class="iconChoosenItem" title="fa-chain"><i class="fa fa-chain"></i><span>fa-chain</span></a><a href="#" class="iconChoosenItem" title="fa-chain-broken"><i class="fa fa-chain-broken"></i><span>fa-chain-broken</span></a><a href="#" class="iconChoosenItem" title="fa-clipboard"><i class="fa fa-clipboard"></i><span>fa-clipboard</span></a><a href="#" class="iconChoosenItem" title="fa-columns"><i class="fa fa-columns"></i><span>fa-columns</span></a><a href="#" class="iconChoosenItem" title="fa-copy"><i class="fa fa-copy"></i><span>fa-copy</span></a><a href="#" class="iconChoosenItem" title="fa-cut"><i class="fa fa-cut"></i><span>fa-cut</span></a><a href="#" class="iconChoosenItem" title="fa-dedent"><i class="fa fa-dedent"></i><span>fa-dedent</span></a><a href="#" class="iconChoosenItem" title="fa-eraser"><i class="fa fa-eraser"></i><span>fa-eraser</span></a><a href="#" class="iconChoosenItem" title="fa-file"><i class="fa fa-file"></i><span>fa-file</span></a><a href="#" class="iconChoosenItem" title="fa-file-o"><i class="fa fa-file-o"></i><span>fa-file-o</span></a><a href="#" class="iconChoosenItem" title="fa-file-text"><i class="fa fa-file-text"></i><span>fa-file-text</span></a><a href="#" class="iconChoosenItem" title="fa-file-text-o"><i class="fa fa-file-text-o"></i><span>fa-file-text-o</span></a><a href="#" class="iconChoosenItem" title="fa-files-o"><i class="fa fa-files-o"></i><span>fa-files-o</span></a><a href="#" class="iconChoosenItem" title="fa-floppy-o"><i class="fa fa-floppy-o"></i><span>fa-floppy-o</span></a><a href="#" class="iconChoosenItem" title="fa-font"><i class="fa fa-font"></i><span>fa-font</span></a><a href="#" class="iconChoosenItem" title="fa-header"><i class="fa fa-header"></i><span>fa-header</span></a><a href="#" class="iconChoosenItem" title="fa-indent"><i class="fa fa-indent"></i><span>fa-indent</span></a><a href="#" class="iconChoosenItem" title="fa-italic"><i class="fa fa-italic"></i><span>fa-italic</span></a><a href="#" class="iconChoosenItem" title="fa-link"><i class="fa fa-link"></i><span>fa-link</span></a><a href="#" class="iconChoosenItem" title="fa-list"><i class="fa fa-list"></i><span>fa-list</span></a><a href="#" class="iconChoosenItem" title="fa-list-alt"><i class="fa fa-list-alt"></i><span>fa-list-alt</span></a><a href="#" class="iconChoosenItem" title="fa-list-ol"><i class="fa fa-list-ol"></i><span>fa-list-ol</span></a><a href="#" class="iconChoosenItem" title="fa-list-ul"><i class="fa fa-list-ul"></i><span>fa-list-ul</span></a><a href="#" class="iconChoosenItem" title="fa-outdent"><i class="fa fa-outdent"></i><span>fa-outdent</span></a><a href="#" class="iconChoosenItem" title="fa-paperclip"><i class="fa fa-paperclip"></i><span>fa-paperclip</span></a><a href="#" class="iconChoosenItem" title="fa-paragraph"><i class="fa fa-paragraph"></i><span>fa-paragraph</span></a><a href="#" class="iconChoosenItem" title="fa-paste"><i class="fa fa-paste"></i><span>fa-paste</span></a><a href="#" class="iconChoosenItem" title="fa-repeat"><i class="fa fa-repeat"></i><span>fa-repeat</span></a><a href="#" class="iconChoosenItem" title="fa-rotate-left"><i class="fa fa-rotate-left"></i><span>fa-rotate-left</span></a><a href="#" class="iconChoosenItem" title="fa-rotate-right"><i class="fa fa-rotate-right"></i><span>fa-rotate-right</span></a><a href="#" class="iconChoosenItem" title="fa-save"><i class="fa fa-save"></i><span>fa-save</span></a><a href="#" class="iconChoosenItem" title="fa-scissors"><i class="fa fa-scissors"></i><span>fa-scissors</span></a><a href="#" class="iconChoosenItem" title="fa-strikethrough"><i class="fa fa-strikethrough"></i><span>fa-strikethrough</span></a><a href="#" class="iconChoosenItem" title="fa-subscript"><i class="fa fa-subscript"></i><span>fa-subscript</span></a><a href="#" class="iconChoosenItem" title="fa-superscript"><i class="fa fa-superscript"></i><span>fa-superscript</span></a><a href="#" class="iconChoosenItem" title="fa-table"><i class="fa fa-table"></i><span>fa-table</span></a><a href="#" class="iconChoosenItem" title="fa-text-height"><i class="fa fa-text-height"></i><span>fa-text-height</span></a><a href="#" class="iconChoosenItem" title="fa-text-width"><i class="fa fa-text-width"></i><span>fa-text-width</span></a><a href="#" class="iconChoosenItem" title="fa-th"><i class="fa fa-th"></i><span>fa-th</span></a><a href="#" class="iconChoosenItem" title="fa-th-large"><i class="fa fa-th-large"></i><span>fa-th-large</span></a><a href="#" class="iconChoosenItem" title="fa-th-list"><i class="fa fa-th-list"></i><span>fa-th-list</span></a><a href="#" class="iconChoosenItem" title="fa-underline"><i class="fa fa-underline"></i><span>fa-underline</span></a><a href="#" class="iconChoosenItem" title="fa-undo"><i class="fa fa-undo"></i><span>fa-undo</span></a><a href="#" class="iconChoosenItem" title="fa-unlink"><i class="fa fa-unlink"></i><span>fa-unlink</span></a></div><div class="tab-pane fade" id="iconChoosenTab7"><a href="#" class="iconChoosenItem" title="fa-angle-double-down"><i class="fa fa-angle-double-down"></i><span>fa-angle-double-down</span></a><a href="#" class="iconChoosenItem" title="fa-angle-double-left"><i class="fa fa-angle-double-left"></i><span>fa-angle-double-left</span></a><a href="#" class="iconChoosenItem" title="fa-angle-double-right"><i class="fa fa-angle-double-right"></i><span>fa-angle-double-right</span></a><a href="#" class="iconChoosenItem" title="fa-angle-double-up"><i class="fa fa-angle-double-up"></i><span>fa-angle-double-up</span></a><a href="#" class="iconChoosenItem" title="fa-angle-down"><i class="fa fa-angle-down"></i><span>fa-angle-down</span></a><a href="#" class="iconChoosenItem" title="fa-angle-left"><i class="fa fa-angle-left"></i><span>fa-angle-left</span></a><a href="#" class="iconChoosenItem" title="fa-angle-right"><i class="fa fa-angle-right"></i><span>fa-angle-right</span></a><a href="#" class="iconChoosenItem" title="fa-angle-up"><i class="fa fa-angle-up"></i><span>fa-angle-up</span></a><a href="#" class="iconChoosenItem" title="fa-arrow-circle-down"><i class="fa fa-arrow-circle-down"></i><span>fa-arrow-circle-down</span></a><a href="#" class="iconChoosenItem" title="fa-arrow-circle-left"><i class="fa fa-arrow-circle-left"></i><span>fa-arrow-circle-left</span></a><a href="#" class="iconChoosenItem" title="fa-arrow-circle-o-down"><i class="fa fa-arrow-circle-o-down"></i><span>fa-arrow-circle-o-down</span></a><a href="#" class="iconChoosenItem" title="fa-arrow-circle-o-left"><i class="fa fa-arrow-circle-o-left"></i><span>fa-arrow-circle-o-left</span></a><a href="#" class="iconChoosenItem" title="fa-arrow-circle-o-right"><i class="fa fa-arrow-circle-o-right"></i><span>fa-arrow-circle-o-right</span></a><a href="#" class="iconChoosenItem" title="fa-arrow-circle-o-up"><i class="fa fa-arrow-circle-o-up"></i><span>fa-arrow-circle-o-up</span></a><a href="#" class="iconChoosenItem" title="fa-arrow-circle-right"><i class="fa fa-arrow-circle-right"></i><span>fa-arrow-circle-right</span></a><a href="#" class="iconChoosenItem" title="fa-arrow-circle-up"><i class="fa fa-arrow-circle-up"></i><span>fa-arrow-circle-up</span></a><a href="#" class="iconChoosenItem" title="fa-arrow-down"><i class="fa fa-arrow-down"></i><span>fa-arrow-down</span></a><a href="#" class="iconChoosenItem" title="fa-arrow-left"><i class="fa fa-arrow-left"></i><span>fa-arrow-left</span></a><a href="#" class="iconChoosenItem" title="fa-arrow-right"><i class="fa fa-arrow-right"></i><span>fa-arrow-right</span></a><a href="#" class="iconChoosenItem" title="fa-arrow-up"><i class="fa fa-arrow-up"></i><span>fa-arrow-up</span></a><a href="#" class="iconChoosenItem" title="fa-arrows"><i class="fa fa-arrows"></i><span>fa-arrows</span></a><a href="#" class="iconChoosenItem" title="fa-arrows-alt"><i class="fa fa-arrows-alt"></i><span>fa-arrows-alt</span></a><a href="#" class="iconChoosenItem" title="fa-arrows-h"><i class="fa fa-arrows-h"></i><span>fa-arrows-h</span></a><a href="#" class="iconChoosenItem" title="fa-arrows-v"><i class="fa fa-arrows-v"></i><span>fa-arrows-v</span></a><a href="#" class="iconChoosenItem" title="fa-caret-down"><i class="fa fa-caret-down"></i><span>fa-caret-down</span></a><a href="#" class="iconChoosenItem" title="fa-caret-left"><i class="fa fa-caret-left"></i><span>fa-caret-left</span></a><a href="#" class="iconChoosenItem" title="fa-caret-right"><i class="fa fa-caret-right"></i><span>fa-caret-right</span></a><a href="#" class="iconChoosenItem" title="fa-caret-square-o-down"><i class="fa fa-caret-square-o-down"></i><span>fa-caret-square-o-down</span></a><a href="#" class="iconChoosenItem" title="fa-caret-square-o-left"><i class="fa fa-caret-square-o-left"></i><span>fa-caret-square-o-left</span></a><a href="#" class="iconChoosenItem" title="fa-caret-square-o-right"><i class="fa fa-caret-square-o-right"></i><span>fa-caret-square-o-right</span></a><a href="#" class="iconChoosenItem" title="fa-caret-square-o-up"><i class="fa fa-caret-square-o-up"></i><span>fa-caret-square-o-up</span></a><a href="#" class="iconChoosenItem" title="fa-caret-up"><i class="fa fa-caret-up"></i><span>fa-caret-up</span></a><a href="#" class="iconChoosenItem" title="fa-chevron-circle-down"><i class="fa fa-chevron-circle-down"></i><span>fa-chevron-circle-down</span></a><a href="#" class="iconChoosenItem" title="fa-chevron-circle-left"><i class="fa fa-chevron-circle-left"></i><span>fa-chevron-circle-left</span></a><a href="#" class="iconChoosenItem" title="fa-chevron-circle-right"><i class="fa fa-chevron-circle-right"></i><span>fa-chevron-circle-right</span></a><a href="#" class="iconChoosenItem" title="fa-chevron-circle-up"><i class="fa fa-chevron-circle-up"></i><span>fa-chevron-circle-up</span></a><a href="#" class="iconChoosenItem" title="fa-chevron-down"><i class="fa fa-chevron-down"></i><span>fa-chevron-down</span></a><a href="#" class="iconChoosenItem" title="fa-chevron-left"><i class="fa fa-chevron-left"></i><span>fa-chevron-left</span></a><a href="#" class="iconChoosenItem" title="fa-chevron-right"><i class="fa fa-chevron-right"></i><span>fa-chevron-right</span></a><a href="#" class="iconChoosenItem" title="fa-chevron-up"><i class="fa fa-chevron-up"></i><span>fa-chevron-up</span></a><a href="#" class="iconChoosenItem" title="fa-hand-o-down"><i class="fa fa-hand-o-down"></i><span>fa-hand-o-down</span></a><a href="#" class="iconChoosenItem" title="fa-hand-o-left"><i class="fa fa-hand-o-left"></i><span>fa-hand-o-left</span></a><a href="#" class="iconChoosenItem" title="fa-hand-o-right"><i class="fa fa-hand-o-right"></i><span>fa-hand-o-right</span></a><a href="#" class="iconChoosenItem" title="fa-hand-o-up"><i class="fa fa-hand-o-up"></i><span>fa-hand-o-up</span></a><a href="#" class="iconChoosenItem" title="fa-long-arrow-down"><i class="fa fa-long-arrow-down"></i><span>fa-long-arrow-down</span></a><a href="#" class="iconChoosenItem" title="fa-long-arrow-left"><i class="fa fa-long-arrow-left"></i><span>fa-long-arrow-left</span></a><a href="#" class="iconChoosenItem" title="fa-long-arrow-right"><i class="fa fa-long-arrow-right"></i><span>fa-long-arrow-right</span></a><a href="#" class="iconChoosenItem" title="fa-long-arrow-up"><i class="fa fa-long-arrow-up"></i><span>fa-long-arrow-up</span></a><a href="#" class="iconChoosenItem" title="fa-toggle-down"><i class="fa fa-toggle-down"></i><span>fa-toggle-down</span></a><a href="#" class="iconChoosenItem" title="fa-toggle-left"><i class="fa fa-toggle-left"></i><span>fa-toggle-left</span></a><a href="#" class="iconChoosenItem" title="fa-toggle-right"><i class="fa fa-toggle-right"></i><span>fa-toggle-right</span></a><a href="#" class="iconChoosenItem" title="fa-toggle-up"><i class="fa fa-toggle-up"></i><span>fa-toggle-up</span></a></div><div class="tab-pane fade" id="iconChoosenTab8"><a href="#" class="iconChoosenItem" title="fa-arrows-alt"><i class="fa fa-arrows-alt"></i><span>fa-arrows-alt</span></a><a href="#" class="iconChoosenItem" title="fa-backward"><i class="fa fa-backward"></i><span>fa-backward</span></a><a href="#" class="iconChoosenItem" title="fa-compress"><i class="fa fa-compress"></i><span>fa-compress</span></a><a href="#" class="iconChoosenItem" title="fa-eject"><i class="fa fa-eject"></i><span>fa-eject</span></a><a href="#" class="iconChoosenItem" title="fa-expand"><i class="fa fa-expand"></i><span>fa-expand</span></a><a href="#" class="iconChoosenItem" title="fa-fast-backward"><i class="fa fa-fast-backward"></i><span>fa-fast-backward</span></a><a href="#" class="iconChoosenItem" title="fa-fast-forward"><i class="fa fa-fast-forward"></i><span>fa-fast-forward</span></a><a href="#" class="iconChoosenItem" title="fa-forward"><i class="fa fa-forward"></i><span>fa-forward</span></a><a href="#" class="iconChoosenItem" title="fa-pause"><i class="fa fa-pause"></i><span>fa-pause</span></a><a href="#" class="iconChoosenItem" title="fa-play"><i class="fa fa-play"></i><span>fa-play</span></a><a href="#" class="iconChoosenItem" title="fa-play-circle"><i class="fa fa-play-circle"></i><span>fa-play-circle</span></a><a href="#" class="iconChoosenItem" title="fa-play-circle-o"><i class="fa fa-play-circle-o"></i><span>fa-play-circle-o</span></a><a href="#" class="iconChoosenItem" title="fa-step-backward"><i class="fa fa-step-backward"></i><span>fa-step-backward</span></a><a href="#" class="iconChoosenItem" title="fa-step-forward"><i class="fa fa-step-forward"></i><span>fa-step-forward</span></a><a href="#" class="iconChoosenItem" title="fa-stop"><i class="fa fa-stop"></i><span>fa-stop</span></a><a href="#" class="iconChoosenItem" title="fa-youtube-play"><i class="fa fa-youtube-play"></i><span>fa-youtube-play</span></a></div><div class="tab-pane fade" id="iconChoosenTab9"><a href="#" class="iconChoosenItem" title="fa-adn"><i class="fa fa-adn"></i><span>fa-adn</span></a><a href="#" class="iconChoosenItem" title="fa-android"><i class="fa fa-android"></i><span>fa-android</span></a><a href="#" class="iconChoosenItem" title="fa-apple"><i class="fa fa-apple"></i><span>fa-apple</span></a><a href="#" class="iconChoosenItem" title="fa-behance"><i class="fa fa-behance"></i><span>fa-behance</span></a><a href="#" class="iconChoosenItem" title="fa-behance-square"><i class="fa fa-behance-square"></i><span>fa-behance-square</span></a><a href="#" class="iconChoosenItem" title="fa-bitbucket"><i class="fa fa-bitbucket"></i><span>fa-bitbucket</span></a><a href="#" class="iconChoosenItem" title="fa-bitbucket-square"><i class="fa fa-bitbucket-square"></i><span>fa-bitbucket-square</span></a><a href="#" class="iconChoosenItem" title="fa-bitcoin"><i class="fa fa-bitcoin"></i><span>fa-bitcoin</span></a><a href="#" class="iconChoosenItem" title="fa-btc"><i class="fa fa-btc"></i><span>fa-btc</span></a><a href="#" class="iconChoosenItem" title="fa-codepen"><i class="fa fa-codepen"></i><span>fa-codepen</span></a><a href="#" class="iconChoosenItem" title="fa-css3"><i class="fa fa-css3"></i><span>fa-css3</span></a><a href="#" class="iconChoosenItem" title="fa-delicious"><i class="fa fa-delicious"></i><span>fa-delicious</span></a><a href="#" class="iconChoosenItem" title="fa-deviantart"><i class="fa fa-deviantart"></i><span>fa-deviantart</span></a><a href="#" class="iconChoosenItem" title="fa-digg"><i class="fa fa-digg"></i><span>fa-digg</span></a><a href="#" class="iconChoosenItem" title="fa-dribbble"><i class="fa fa-dribbble"></i><span>fa-dribbble</span></a><a href="#" class="iconChoosenItem" title="fa-dropbox"><i class="fa fa-dropbox"></i><span>fa-dropbox</span></a><a href="#" class="iconChoosenItem" title="fa-drupal"><i class="fa fa-drupal"></i><span>fa-drupal</span></a><a href="#" class="iconChoosenItem" title="fa-empire"><i class="fa fa-empire"></i><span>fa-empire</span></a><a href="#" class="iconChoosenItem" title="fa-facebook"><i class="fa fa-facebook"></i><span>fa-facebook</span></a><a href="#" class="iconChoosenItem" title="fa-facebook-square"><i class="fa fa-facebook-square"></i><span>fa-facebook-square</span></a><a href="#" class="iconChoosenItem" title="fa-flickr"><i class="fa fa-flickr"></i><span>fa-flickr</span></a><a href="#" class="iconChoosenItem" title="fa-foursquare"><i class="fa fa-foursquare"></i><span>fa-foursquare</span></a><a href="#" class="iconChoosenItem" title="fa-ge"><i class="fa fa-ge"></i><span>fa-ge</span></a><a href="#" class="iconChoosenItem" title="fa-git"><i class="fa fa-git"></i><span>fa-git</span></a><a href="#" class="iconChoosenItem" title="fa-git-square"><i class="fa fa-git-square"></i><span>fa-git-square</span></a><a href="#" class="iconChoosenItem" title="fa-github"><i class="fa fa-github"></i><span>fa-github</span></a><a href="#" class="iconChoosenItem" title="fa-github-alt"><i class="fa fa-github-alt"></i><span>fa-github-alt</span></a><a href="#" class="iconChoosenItem" title="fa-github-square"><i class="fa fa-github-square"></i><span>fa-github-square</span></a><a href="#" class="iconChoosenItem" title="fa-gittip"><i class="fa fa-gittip"></i><span>fa-gittip</span></a><a href="#" class="iconChoosenItem" title="fa-google"><i class="fa fa-google"></i><span>fa-google</span></a><a href="#" class="iconChoosenItem" title="fa-google-plus"><i class="fa fa-google-plus"></i><span>fa-google-plus</span></a><a href="#" class="iconChoosenItem" title="fa-google-plus-square"><i class="fa fa-google-plus-square"></i><span>fa-google-plus-square</span></a><a href="#" class="iconChoosenItem" title="fa-hacker-news"><i class="fa fa-hacker-news"></i><span>fa-hacker-news</span></a><a href="#" class="iconChoosenItem" title="fa-html5"><i class="fa fa-html5"></i><span>fa-html5</span></a><a href="#" class="iconChoosenItem" title="fa-instagram"><i class="fa fa-instagram"></i><span>fa-instagram</span></a><a href="#" class="iconChoosenItem" title="fa-joomla"><i class="fa fa-joomla"></i><span>fa-joomla</span></a><a href="#" class="iconChoosenItem" title="fa-jsfiddle"><i class="fa fa-jsfiddle"></i><span>fa-jsfiddle</span></a><a href="#" class="iconChoosenItem" title="fa-linkedin"><i class="fa fa-linkedin"></i><span>fa-linkedin</span></a><a href="#" class="iconChoosenItem" title="fa-linkedin-square"><i class="fa fa-linkedin-square"></i><span>fa-linkedin-square</span></a><a href="#" class="iconChoosenItem" title="fa-linux"><i class="fa fa-linux"></i><span>fa-linux</span></a><a href="#" class="iconChoosenItem" title="fa-maxcdn"><i class="fa fa-maxcdn"></i><span>fa-maxcdn</span></a><a href="#" class="iconChoosenItem" title="fa-openid"><i class="fa fa-openid"></i><span>fa-openid</span></a><a href="#" class="iconChoosenItem" title="fa-pagelines"><i class="fa fa-pagelines"></i><span>fa-pagelines</span></a><a href="#" class="iconChoosenItem" title="fa-pied-piper"><i class="fa fa-pied-piper"></i><span>fa-pied-piper</span></a><a href="#" class="iconChoosenItem" title="fa-pied-piper-alt"><i class="fa fa-pied-piper-alt"></i><span>fa-pied-piper-alt</span></a><a href="#" class="iconChoosenItem" title="fa-pied-piper-square"><i class="fa fa-pied-piper-square"></i><span>fa-pied-piper-square</span></a><a href="#" class="iconChoosenItem" title="fa-pinterest"><i class="fa fa-pinterest"></i><span>fa-pinterest</span></a><a href="#" class="iconChoosenItem" title="fa-pinterest-square"><i class="fa fa-pinterest-square"></i><span>fa-pinterest-square</span></a><a href="#" class="iconChoosenItem" title="fa-qq"><i class="fa fa-qq"></i><span>fa-qq</span></a><a href="#" class="iconChoosenItem" title="fa-ra"><i class="fa fa-ra"></i><span>fa-ra</span></a><a href="#" class="iconChoosenItem" title="fa-rebel"><i class="fa fa-rebel"></i><span>fa-rebel</span></a><a href="#" class="iconChoosenItem" title="fa-reddit"><i class="fa fa-reddit"></i><span>fa-reddit</span></a><a href="#" class="iconChoosenItem" title="fa-reddit-square"><i class="fa fa-reddit-square"></i><span>fa-reddit-square</span></a><a href="#" class="iconChoosenItem" title="fa-renren"><i class="fa fa-renren"></i><span>fa-renren</span></a><a href="#" class="iconChoosenItem" title="fa-share-alt"><i class="fa fa-share-alt"></i><span>fa-share-alt</span></a><a href="#" class="iconChoosenItem" title="fa-share-alt-square"><i class="fa fa-share-alt-square"></i><span>fa-share-alt-square</span></a><a href="#" class="iconChoosenItem" title="fa-skype"><i class="fa fa-skype"></i><span>fa-skype</span></a><a href="#" class="iconChoosenItem" title="fa-slack"><i class="fa fa-slack"></i><span>fa-slack</span></a><a href="#" class="iconChoosenItem" title="fa-soundcloud"><i class="fa fa-soundcloud"></i><span>fa-soundcloud</span></a><a href="#" class="iconChoosenItem" title="fa-spotify"><i class="fa fa-spotify"></i><span>fa-spotify</span></a><a href="#" class="iconChoosenItem" title="fa-stack-exchange"><i class="fa fa-stack-exchange"></i><span>fa-stack-exchange</span></a><a href="#" class="iconChoosenItem" title="fa-stack-overflow"><i class="fa fa-stack-overflow"></i><span>fa-stack-overflow</span></a><a href="#" class="iconChoosenItem" title="fa-steam"><i class="fa fa-steam"></i><span>fa-steam</span></a><a href="#" class="iconChoosenItem" title="fa-steam-square"><i class="fa fa-steam-square"></i><span>fa-steam-square</span></a><a href="#" class="iconChoosenItem" title="fa-stumbleupon"><i class="fa fa-stumbleupon"></i><span>fa-stumbleupon</span></a><a href="#" class="iconChoosenItem" title="fa-stumbleupon-circle"><i class="fa fa-stumbleupon-circle"></i><span>fa-stumbleupon-circle</span></a><a href="#" class="iconChoosenItem" title="fa-tencent-weibo"><i class="fa fa-tencent-weibo"></i><span>fa-tencent-weibo</span></a><a href="#" class="iconChoosenItem" title="fa-trello"><i class="fa fa-trello"></i><span>fa-trello</span></a><a href="#" class="iconChoosenItem" title="fa-tumblr"><i class="fa fa-tumblr"></i><span>fa-tumblr</span></a><a href="#" class="iconChoosenItem" title="fa-tumblr-square"><i class="fa fa-tumblr-square"></i><span>fa-tumblr-square</span></a><a href="#" class="iconChoosenItem" title="fa-twitter"><i class="fa fa-twitter"></i><span>fa-twitter</span></a><a href="#" class="iconChoosenItem" title="fa-twitter-square"><i class="fa fa-twitter-square"></i><span>fa-twitter-square</span></a><a href="#" class="iconChoosenItem" title="fa-vimeo-square"><i class="fa fa-vimeo-square"></i><span>fa-vimeo-square</span></a><a href="#" class="iconChoosenItem" title="fa-vine"><i class="fa fa-vine"></i><span>fa-vine</span></a><a href="#" class="iconChoosenItem" title="fa-vk"><i class="fa fa-vk"></i><span>fa-vk</span></a><a href="#" class="iconChoosenItem" title="fa-wechat"><i class="fa fa-wechat"></i><span>fa-wechat</span></a><a href="#" class="iconChoosenItem" title="fa-weibo"><i class="fa fa-weibo"></i><span>fa-weibo</span></a><a href="#" class="iconChoosenItem" title="fa-weixin"><i class="fa fa-weixin"></i><span>fa-weixin</span></a><a href="#" class="iconChoosenItem" title="fa-windows"><i class="fa fa-windows"></i><span>fa-windows</span></a><a href="#" class="iconChoosenItem" title="fa-wordpress"><i class="fa fa-wordpress"></i><span>fa-wordpress</span></a><a href="#" class="iconChoosenItem" title="fa-xing"><i class="fa fa-xing"></i><span>fa-xing</span></a><a href="#" class="iconChoosenItem" title="fa-xing-square"><i class="fa fa-xing-square"></i><span>fa-xing-square</span></a><a href="#" class="iconChoosenItem" title="fa-yahoo"><i class="fa fa-yahoo"></i><span>fa-yahoo</span></a><a href="#" class="iconChoosenItem" title="fa-youtube"><i class="fa fa-youtube"></i><span>fa-youtube</span></a><a href="#" class="iconChoosenItem" title="fa-youtube-play"><i class="fa fa-youtube-play"></i><span>fa-youtube-play</span></a><a href="#" class="iconChoosenItem" title="fa-youtube-square"><i class="fa fa-youtube-square"></i><span>fa-youtube-square</span></a></div><div class="tab-pane fade" id="iconChoosenTab10"><a href="#" class="iconChoosenItem" title="fa-ambulance"><i class="fa fa-ambulance"></i><span>fa-ambulance</span></a><a href="#" class="iconChoosenItem" title="fa-h-square"><i class="fa fa-h-square"></i><span>fa-h-square</span></a><a href="#" class="iconChoosenItem" title="fa-hospital-o"><i class="fa fa-hospital-o"></i><span>fa-hospital-o</span></a><a href="#" class="iconChoosenItem" title="fa-medkit"><i class="fa fa-medkit"></i><span>fa-medkit</span></a><a href="#" class="iconChoosenItem" title="fa-plus-square"><i class="fa fa-plus-square"></i><span>fa-plus-square</span></a><a href="#" class="iconChoosenItem" title="fa-stethoscope"><i class="fa fa-stethoscope"></i><span>fa-stethoscope</span></a><a href="#" class="iconChoosenItem" title="fa-user-md"><i class="fa fa-user-md"></i><span>fa-user-md</span></a><a href="#" class="iconChoosenItem" title="fa-wheelchair"><i class="fa fa-wheelchair"></i><span>fa-wheelchair</span></a></div></div></div></div></div></div></div>';
        return this.each(function() {
            var $this = $(this);
            $this.click(function() {
                $('#iconChoosenModal').remove();
                $('body').append(temp);

                //调整样式
                $('a.iconChoosenItem').attr('style', 'display: block;float: left;text-align: center;width: 5em;height: 6em;overflow: hidden;margin: auto .3em;');
                $('a.iconChoosenItem i').attr('style', 'display: block;font-size: 2em;color: #000');
                $('a.iconChoosenItem span').attr('style', 'display: block;height: 5em;overflow: hidden;word-break: break-all;');

                //绑定点击事件
                $('a.iconChoosenItem').click(function() {
                    $this.val('fa ' + $(this).find('span').text());
                    $('#iconChoosenModal').modal('hide');
                });

                //显示弹窗
                $('#iconChoosenModal').modal();
            });
        });
    };
})(jQuery);



$(function() {
    //切换左侧菜单
    var result = window.matchMedia("(min-width: 768px)");
    if (result.matches) {
        $('.full-container').addClass($.cookie('sidebar_title_hide'));
        $('body').on('click', '#sidebar-toggle', function() {
            if($.cookie('sidebar_title_hide') == 'title-hide'){
                $('.full-container').removeClass('title-hide');
                $.cookie('sidebar_title_hide', null, {path: '/'});
            }else{
                $('.full-container').addClass('title-hide');
                $.cookie('sidebar_title_hide', 'title-hide', {path: '/'});
            }
        });
    }
});

$(function() {
    if (!$('body').hasClass("body-index")) {
        return false;
    }

    $(".gantt").gantt({
		source: $universalObjectIndex.ganttData,
		navigate: "scroll",
		scale: "days",
		maxScale: "months",
		minScale: "days",
		itemsPerPage: 10,
		onItemClick: function(data) {
			//alert("Item clicked - show some details");
		},
		onAddClick: function(dt, rowId) {
			//alert("Empty space clicked - add an item!");
		},
		onRender: function() {
			if (window.console && typeof console.log === "function") {
				//console.log("chart rendered");
			}
		}
	});




    //图表时间段选择
    $('#daterange_set').daterangepicker({
            startDate: moment().subtract(29, 'days'),
            endDate: moment(),
            minDate: '01/01/2015',
            maxDate: '12/31/2100',
            dateLimit: {
                days: 360
            },
            showDropdowns: true,
            showWeekNumbers: true,
            timePicker: false,
            timePickerIncrement: 1,
            timePicker12Hour: true,
            ranges: {
                '最近7天': [moment().subtract(6, 'days'), moment()],
                '这个月': [moment().startOf('month'), moment().endOf('month')],
                '上个月': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
            },
            opens: 'left',
            buttonClasses: ['btn btn-default'],
            applyClass: 'btn-sm btn-primary',
            cancelClass: 'btn-sm',
            format: 'MM/DD/YYYY',
            separator: ' to ',
            locale: {
                applyLabel: '确定',
                cancelLabel: '取消',
                fromLabel: '开始',
                toLabel: '结束',
                customRangeLabel: '自定义',
                daysOfWeek: ['日', '一', '二', '三', '四', '五', '六'],
                monthNames: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
                firstDay: 1
            }
        },

        function(start, end, label) {
            var url = $universalObjectIndex.index_url;
            var query = 'start_date=' + start + '&end_date=' + end;
            if (url.indexOf('?') > 0) {
                url += '&' + query;
            } else {
                url += '?' + query;
            }
            window.location.href = url;
        }
    );



    //检测更新
    $.ajax({
        url: $universalObjectIndex.check_version_url,
        type: 'GET',
    }).done(function(data) {
        if (data.status == 1) {
            $('.update').html(data.info);
        } else {
            $.alertMessager(data.info, 'danger');
        }
    });


});
$(function() {
    if (!$('body').hasClass("body-public-login")) {
        return false;
    }


    //刷新验证码
    $(function() {
        $(".reload-verify").on('click', function() {
            var verifyimg = $(".verifyimg").attr("src");
            if (verifyimg.indexOf('?') > 0) {
                $(".verifyimg").attr("src", verifyimg + '&random=' + Math.random());
            } else {
                $(".verifyimg").attr("src", verifyimg.replace(/\?.*$/, '') + '?' + Math.random());
            }
        });
    });


    //背景粒子效果
    particlesJS('particles-js', {
        particles: {
            color: '#46BCF3',
            shape: 'circle', // "circle", "edge" or "triangle"
            opacity: 1,
            size: 2,
            size_random: true,
            nb: 200,
            line_linked: {
                enable_auto: true,
                distance: 100,
                color: '#46BCF3',
                opacity: .8,
                width: 1,
                condensed_mode: {
                enable: false,
                rotateX: 600,
                rotateY: 600
                }
        },
        anim: {
            enable: true,
            speed: 1
        }
        },
        interactivity: {
            enable: true,
            mouse: {
            distance: 250
        },
        detect_on: 'canvas', // "canvas" or "window"
            mode: 'grab',
            line_linked: {
            opacity: .5
        },
        events: {
            onclick: {
            enable: true,
            mode: 'push', // "push" or "remove" (particles)
            nb: 4
            }
        }
        },
        /* Retina Display Support */
        retina_detect: true
    });

});
$(function() {
    if (!$('.builder')) {
        return false;
    }

    //给数组增加查找指定的元素索引方法
    Array.prototype.indexOf = function(val) {
        for (var i = 0; i < this.length; i++) {
            if (this[i] == val) return i;
        }
        return -1;
    };

    //给数组增加删除方法
    Array.prototype.remove = function(val) {
        var index = this.indexOf(val);
        if (index > -1) {
            this.splice(index, 1);
        }
    };



    //全选/反选/单选的实现
    $(".builder .check-all").click(function() {
        $(".ids").prop("checked", this.checked);
    });

    $(".builder .ids").click(function() {
        var option = $(".ids");
        option.each(function() {
            if (!this.checked) {
                $(".check-all").prop("checked", false);
                return false;
            } else {
                $(".check-all").prop("checked", true);
            }
        });
    });


  //搜索功能
    $('body').on('click', '.builder #search', function() {
        var url = $(this).attr('url');
        var keyselect=$('#keyselect').children('option:selected').val();
        var keyselect1=$('#keyselect1').children('option:selected').val(); 
        var query = $('.builder .search-form').find('input').serialize();
        query = query.replace(/(&|^)(\w*?\d*?\-*?_*?)*?=?((?=&)|(?=$))/g, '');
        query = query.replace(/(^&)|(\+)/g, '');
        
        if((keyselect != '') &&(keyselect != undefined)){
			query = 'keyselect=' + keyselect + "&" + query;
        }
        if((keyselect1 != '')&&(keyselect1 != undefined)){
			query = 'keyselect1=' + keyselect1 + "&" + query;
        }
        
        if (url.indexOf('?') > 0) {
            url += '&' + query;
        } else {
            url += '?' + query;
        }
       window.location.href = url; 
    });

     //回车搜索
    $(".builder .search-input").keyup(function(e) {
        if (e.keyCode === 13) {
            $("#search").click();
            return false;
        }
    });
    
    
    var start = {
	    elem: '#starttime',
	    format: 'YYYY-MM-DD',
	    min: '2010-01-01 00:00:00', //设定最小日期为当前日期
	    max: '2099-12-31 23:59:59', //最大日期
	    istime: true,
	    istoday: false,
	    choose: function(datas){
	         end.min = datas; //开始日选好后，重置结束日的最小日期
	         end.start = datas //将结束日的初始值设定为开始日
	    }
	};
	var end = {
	    elem: '#endtime',
	    format: 'YYYY-MM-DD',
	    min: '2010-01-01 00:00:00', //设定最小日期为当前日期
	    max: '2099-12-31 23:59:59', //最大日期
	    istime: true,
	    istoday: false,
	    choose: function(datas){
	        start.max = datas; //结束日选好后，重置开始日的最大日期
	    }
	};
	
	laydate(start);
	laydate(end);
	/*laydate.skin('molv');*/


});
