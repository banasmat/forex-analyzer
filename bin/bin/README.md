# Spring MVC Skeleton
Spring Web MVC ready to go. A simple skeleton structure that could be used to begin prototyping an application.  

### Created using...
>
  - [Spring Tools Suite (STS)](https://spring.io/tools)
  - [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
  - [Tomcat 8](https://tomcat.apache.org/download-80.cgi) 
  - Spring 4.1.7
   - spring-core
   - spring-web
   - spring-webmvc
  - Maven 4.0
  - WebApp 3.1
  - J2EE
   - jstl-api 1.2
   - servlet-api 3.1.0
   - jsp-api 2.2
   - taglibs-standard-impl 1.2.5
  - JQuery 1.11.3

### Instructions
>
1. Clone project
  * `$ git clone git@github.com:BorysHN/SpringMVCSkeleton.git`
  * or download zip
2. Add project to STS
    - File->Import->Maven->"Existing Maven Projects" 
3. Download and add Tomcat 8 to STS servers
    - [crunchify tutorial](http://crunchify.com/step-by-step-guide-to-setup-and-install-apache-tomcat-server-in-eclipse-development-environment-ide/)
4. Change context root to "/" (without quotes)
    - [mkyong tutorial](http://www.mkyong.com/eclipse/eclipse-how-to-change-web-project-context-root/)
5. Run project on Tomcat server
     1. Right click project->"Run As"->"Run on Server"
     2. Select Tomcat v8.0 from list
         - you may select "Always use this server..."
     3. Select Next
     4. Move the specified project to the "Configured" side
     5. Select Finish

### Two Cents 
> Despite having access to various spring web mvc resources, I still had some
issues putting together all the components for a running application on tomcat.
Between various configuration conflicts, outdated information, and a few
environment hiccups using the latest technologies, it was a nice learning
experience. Big thanks to a few blogs out there that really helped. I saw a lot 
of comments and threads around similar issues that I dealt with. Hope this helps
someone else get started!

### Resources Used
>
+ spring docs
+ mkyong
+ crunchify
+ beginning spring
+ stackoverflow <3
