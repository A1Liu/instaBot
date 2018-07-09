# InstaBot

This is an Instagram bot built using the Selenium API. It includes basic functionality for a personal account, and right now, it doesn't try to expand user sphere of influence whatsoever. It simply likes posts in the feed and checks on some information. Later versions might have some of that more intense stuff, but for now this is all it does.

## Setup
Using the FunctionalBot requires only a username and password. If you're using mac, you can quickly enable this bot by first enabling 'develop menu' in preferences, then selecting 'allow remote automation'.  

For non-Mac users, and for blind-haters of Safari, use the instructions at this [link](http://toolsqa.com/selenium-tutorial/) to set up Selenium correctly.

## Packages

#### Bot Package
The bot package contains most of the logic that the bot employs to perform actions.
- Methods that perform simple tasks like logging in
- Methods that perform a series of simple tasks, like liking lots of posts
- Objects and methods to help set up the bot

#### Instagram Package
The Instagram package contains object wrappers to make parsing through Instagram a little easier.
- Wrapper objects that automatically load pertinent data on instantiation
- Constants that describe locations of objects on the site

#### Main Package
The default package for implementing extensions of the FunctionalBot, and also where the running of the final bot should occur.

## Sources
[**Selenium**](https://www.seleniumhq.org/) - This took a long time to get used to using, but once I figured out some of the basics it got a lot easier -- it's a very powerful API once you understand what methods to copy-paste from StackOverflow.  
[**Tools QA Selenium tutorial**](http://toolsqa.com/selenium-tutorial/) - Used this to get a basic grasp of some of the features of Selenium.  
[**XPath Tutorial**](https://www.w3schools.com/xml/xpath_intro.asp) - Used this to start to learn XPath.  
[**XPath Generator**](https://chrome.google.com/webstore/detail/xpath-generator/dphfifdfpfabhbkghlmnkkdghbmocfeb?hl=en-US) - This made XPath stuff a lot easier to learn. Another alternative is [this](https://chrome.google.com/webstore/detail/chropath/ljngjbnaijcbncmcnjfhigebomdlkcjo/related?hl=en) (both are Chrome extensions).  
[**JBrowserDriver**](https://github.com/MachinePublishers/jBrowserDriver) - I couldn't get this to work, so it's not included in the project, but it's a cool potential direction for the future.  
[**HTMLUnit**](http://htmlunit.sourceforge.net/) - I'm going to try to use this to remove the 'visible browser' part of the bot.  
[**Instagram**](www.instagram.com) - I couldn't have done this project without the HTML, servers, and existence of Instagram.  

