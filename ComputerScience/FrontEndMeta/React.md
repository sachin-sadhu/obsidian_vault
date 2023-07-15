
# React

## Single Page Applications (SPA)

Solves problem of instead of reloading entire webpage when every page needs to be updated, 
update section of webpage that requires new content instead.

## Lazy Loading
    
Loads minimum amount of html/css/js in order to display webpage, loads in other information only when required

## Bundling

Loads all html/css/js at beginning, could take longer to load webpage initally

## Components

Sections of a UI that can be created once and re-used multiple times. Allows for testing seperate components
When your component is first initialized, the render method is called, generating a lightweight representation of your view. From that representation, a string of markup is produced and injected into the document. When your data changes, the render method is called again. In order to perform updates as efficiently as possible, we diff the return value from the previous call to render with the new one and generate a minimal set of changes to be applied to the DOM.
The data returned from render is neither a string nor a DOM node — it’s a lightweight description of what the DOM should look like.

## Virtual DOM

React builds out its own virtual DOM in memory, which is a representation of browser DOM. React checks to see whether element in virtual DOM matches element in browser DOM.
If change is required, browser DOM is updated, not updated if no change is required. 
When you make a change to element, current virtual DOM is changed, new virtual DOM is then compared to previous version of virtual DOM.
Elements that have a change are then changed in browser DOM.
