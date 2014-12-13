redsniff
========
[![Build Status](https://travis-ci.org/jhc-systems/redsniff.svg?branch=master)](https://travis-ci.org/jhc-systems/redsniff)

A fluent, feedback-rich API for WebDriver or other similar libraries

redsniff is a thin higher-level API on top of [Selenium Webdriver](http://code.google.com/p/selenium/wiki/GettingStarted) that integrates with [hamcrest](http://code.google.com/p/hamcrest/wiki/Tutorial) to enable writing expressive, effective automated tests for interacting with, and making assertions against websites. 

(It is originally based on the experimental [Selenium Lift-Style API](http://code.google.com/p/selenium/wiki/LiftStyleApi) but ended up diverging substantially.)

###Fluent Interface
The style used is as a [Fluent interface](http://en.wikipedia.org/wiki/Fluent_interface), or [Internal DSL](http://martinfowler.com/books/dsl.html).

This means it uses certain tricks to make the API look very readable, `albeit.with(aLotOf(brackets()))`, almost like an English sentence if you took the punctuation out. 

It tends to encourage a *declarative* rather than *procedural* way of thinking about things - *what*, not *how*.

###Quick example

we create a RedsniffWebDriverTester as below  

    RedsniffWebDriverTester browser = new RedsniffWebDriverTester( new ChromeDriver() );
    browser.goTo("http://my-url.com");


Then you can write assertions like this:

    browser.assertPresenceOf( dropDown().that( hasName("a_drop-down") ) );


which means, **browser**, *please confirm the presence of a drop down item that has `name` `"a_drop-down"`*

i.e. *on the browser, check that a dropdown whose 'name' attribute is "a_drop-down" is present somewhere on the page*


###More Examples

Here are some more - the syntax is designed so that what it does is obvious without explanation or comments :

    browser.goTo("http://www.wikipedia.org");
    browser.type("clowns", into( textBox().that( hasName("search") ) ));
    browser.clickOn( button("OK") );
    browser.assertThatThe( third(dropDown().that( hasName("a_drop-down") ), isEnabled() );
    
You can also just use css selectors:

    browser.assertThatThe( first( $(".itemselector") ) , isEnabled() );
    broswer.clickOn( $("#orderSubmit") );

###Feedback - a first class citizen
redsniff puts a very high value on feedback - diagnostic messages that appear when assertions and expectations fail.
Some messages you could get from above examples are:

    Expected: a(n) 3rd drop-down that has name "a_drop-down" to match is <enabled>
    but: only found <2> such elements


    Expected: 3rd drop-down that has name "a_drop-down" to match  is <enabled>
    but:
    element was <disabled>


    Expected: a unique textbox that has name "search" to type "clowns" into, 
    but:
    Did not find any textbox that: {has name "search"}
    <input> (name:Search)  - not matched because name  was:
      '[S]earch'
    instead of:
      '[s]earch'

By contrast, using WebDriver directly would have resulted in just `ElementNotFoundException` in many cases, and it may take you some time to realise you had, for instance, misspelled an element name.


###Why 'redsniff'?
Because, when your test fails and goes red, this can help you sniff out the reason.  Also, "_hamcrest_" is an anagram of "_matchers_", which is the core concept in that library, and so this is an anagram of "finders", which is the main concept in this library. 

Well, nearly, there's an extra 'f' but nothing is ever perfect - which is why we need flexible test frameworks.

##Goal
redsniff aims to make the writing and understanding of the intent of tests easier, whilst diagnosing clearly why they fail, which is often a frustrating and time-consuming process when we first write the test.

It also
* allows for less brittle tests by allowing you to specify as much or as little as you need
* has high [compositionality](http://vimeo.com/user22258446/review/79095045/9590c62ac2) - expressions can be plugged together in different ways, like Lego 
* deals with synchronization issues particularly for AJAX-enabled apps, where elements can appear and disappear
* particular support for testing tables of data, including csv downloads
* easy support for creating custom abstractions specific to your web app and domain

Whatever you can express should do what you'd expect it to do by examining the expression.

###Further Examples





####Support for tables

You can [make assertions about data in an html table] (https://github.com/jhc-systems/redsniff/wiki/Support-for-tables), being as specific as you would like to be

####Assertions about Downloads

You can [make assertions about downloadable files](https://github.com/jhc-systems/redsniff/wiki/Support-for-downloads), such as a CSV download, using the table assertions


## Similar projects
- [Selenide](http://selenide.org/)
- [FluentLenium](https://github.com/FluentLenium/FluentLenium)

## Author
- [Nic Infante](https://github.com/nico78)


## Contributors
- Daniel Ecer
- [Graeme Oswald](https://github.com/GraemeOswald)


## Acknowledgments
- [Selenium Lift-Style API](http://code.google.com/p/selenium/wiki/LiftStyleApi) a similar project  by [Robert Chatley](https://github.com/rchatley) which inspired this one
- [Steve Freeman](https://github.com/sf105) ideas about table matching from workshop sessions as well as being one of the authors of [Hamcrest](https://github.com/hamcrest/JavaHamcrest)

- [Ebselen](https://github.com/Ardesco/Ebselen)  - File download code modified from code in this project
- [Dean Chapman](https://github.com/p14n) Ideas, feedback, encouragement



