<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>EJB Load Testing Utility</title>
        <style type="text/css">
            body {
                font-family: verdana, tahoma, sans-serif;
                text-align: center;
            }
            h1 {
                margin-top: 50px;
                color: #cc6666;
            }
            .message_text {
                color: #996666;
            }
        </style>
    </head>
    <body>
        <h1>EJB Load Testing Utility</h1>
        <form method=post action="FiboServlet">
            <p class="message_text">This calculator computes Fibonacci numbers concurrently with an iterative
                <br>algorithm running in a stateless EJB bean.</p>
            <p>Fibonacci value to compute: <input type=text name=value value="30" size=5></p>
            <p>Number of times to compute it (concurrent EJBs): <input type=text name=iterations value="10" size=5> </p>
            <p>Longest time to wait for everything to complete (ms): <input type=text name=timeout value="1500" size=5> </p>
                <input type=submit value="Submit"></p>
        </form>
        <BR><BR>
        <!--<a href="JackServlet">Click here to start a 20 second EJB Timer</a>-->

    </body>
</html>