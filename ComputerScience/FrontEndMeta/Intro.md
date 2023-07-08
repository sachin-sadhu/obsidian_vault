# Web Server

Computer that provides a service to a client, some example of functionality that a web server provides includes :
* Website
* Data Storage
* Security
* Managing emails

Also able to handle web requests, must be able to handle thousands of requests from clients per second

# Website/Webpage/WebApp

Webpage is a document that displays images, texts, videos etc.
Website is a collection of webpages linked together.
Website and Web Application are used interchangably, key differences are that a web application is usually more interactive than a website 


## Request Response Cycle

Webbrowser makes a resposne to a web server for information, server responds back with viable info which browser renders and then displays

# Webhosting

Place your website files on another companies server, essentially part of their server to run your website.

## Shared Hosting

Pay for webhosting server that is also hosting other websites, availability of resources depends on other websites you are sharing service with.

## Virtual Private Servers

Also share with other websites, however available resources are fixed, hence will not be slowed down.

# Internet Protocols

IP Addresses give your computer an address on the network, allows for information to be transmitted across a network.

Information is sent across networks as IP Packets, which contain a header and the actual data
Packets will contain both source and receipient address. 

When sending packets, packets can arrive out of order, packets can be corrupted, data can be lost 

## Transmission Control Protocol (TCP)

Makes sure none of these 3 issues occur, at cost of longer transmission time

## UDP

Solves corrupt packet problem, however packets can still arrive out of order or not at all 

# Hyper Text Transfer Protocol (HTTP)
    
Protocol used to transmit information between web clients and web servers. 

Four different HTTP methods are:
* GET (Retrieve information)
* POST (Used to store information at the server)
* PUT (Used to update information at the server)
* DELETE (Used to delete information stored at the server)

## HTTP Request Headers

Contains info such as:
* Host (Host of server and where resource is being requested from)
* User-Agent (Informs server of application making request such as OS, version, vendor)
* Accept (What info the client will accept as response)
* Content-type (Type of content being transmistted)

    
