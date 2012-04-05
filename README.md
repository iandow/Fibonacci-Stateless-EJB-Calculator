Stateless EJB-based Fibonacci Calculator
==================================

This simple J2EE application was written for load testing Enterprise JavaBeans (EJB 3.0) technology. Load is created by invoking stateless session beans which concurrently calculate Fibonacci sequences on an application server. Tests are started from a web browser after submitting load parameters from a JSP page.  Results are returned by a Servlet which reports a record of the completed Fibonacci calculations and graphs their processing time using Google Charts.

An IntelliJ project is provided, which includes an application server configuration for running with JBoss 6.1.0.


Prerequisites
-------------

To be able to run this project, the following software must be installed on your computer:

   * Java Development Kit (JDK), version 1.6 or later.
   * JBoss Application Server version 6.1.0
   * A Web browser
   * (optional) IntelliJ IDEA, version 10.5 or later


Usage
-----

Running in IntelliJ:

   1.  Open the project in IntelliJ
   2.  Update the JBoss Run configs
   3.  Update paths in Project Settings for Project, Modules, Libraries, and Artifacts 
   4.  Deploy and/or run the project
   5.  Open, http://localhost:8080/FibonacciStatelessEJB/index.jsp


Running directly in JBoss 6.1.0 (without IntelliJ):

   1.  Deploy the war package with, cp -R out/artifacts/FibonacciStatelessEJB.war /opt/jboss-6.1.0.Final/server/default/deploy/
   2.  Start JBoss with, /opt/jboss-6.1.0.Final/run.sh 
   3.  Open, http://localhost:8080/FibonacciStatelessEJB/index.jsp


Author
------

Created by Ian Downard, March 2012.