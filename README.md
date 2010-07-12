# Akka Samples

This project contains various samples for working with the [akka](http://akkasource.org) framework.  If you just want a staring point for playing with Akka in an SBT project then look at my [akka-template-rest](http://github.com/efleming969/akka-template-rest) project

Akka is an integration of many techologies, so some examples are not specific to akka.

## Getting started
1. cd akka-samples
2. ./sbt (unix-based) or sbt (windows)
3. update (this could take awhile)
4. jetty-run
5. http://localhost:8080/index.html

## Changelog

2010-07-11

* Updated project files for Scala-2.8.0.RC3 and Akka 0.10
* Fixed CRLF in src/main/webapp directory.

2010-04-07

* BasicJsonRestService

A sample of using Akka''s built-in serialization, to implement a simple json service for users

* NonActorService

Just uses Akka''s serialization without the need for an actor. Many use-case probably don''t need Actor so proving that Akka''s Servlet allow for standard Jersey services to be created

2010-04-10

* BasicJsonRestService

Cleaned up the service and added a basic single-page javascript application to interact with the service
