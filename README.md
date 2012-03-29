Fibonacci Stateless EJB Calculator
==================================

This J2EE application was written for load testing Enterprise JavaBeans (EJB 3.0) technology in a JRE release 1.6 or later. Load is created by invoking stateless session beans which concurrently calculate Fibonacci sequences on an application server. Tests are started from a web browser after submitting load parameters from a JSP page.  Results are returned by a Servlet which reports a record of the completed Fibonacci calculations and graphs their processing time using Google Charts.

An IntelliJ project is provided, which includes an application server configuration for JBoss 6.1.0. Once JBoss starts, the application can be accessed through the following URL after the war file has been deployed (which happens automatically in IntelliJ), http://localhost:8080/JBossSampleApp_war_exploded/FooServlet

The application should also be deployable to other application servers by deploying the out/artifacts/FibonacciStatelessEJB.war directory.


Prerequisites
-------------

To be able to run this project, the following software must be installed on your computer:

   * IntelliJ IDEA, version 10.5 or later
   * Java Development Kit (JDK), version 1.6 or later.
   * JBoss Application Server version 6.1.0
   * A Web browser


Usage
-----

General guidance: open the project in IntelliJ, update the JBoss Run configs, then deploy and/or run the project.


Author
------

Created by Ian Downard, March 2012.