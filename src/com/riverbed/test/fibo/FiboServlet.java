package com.riverbed.test.fibo;

import java.io.*;
import java.util.*;
import javax.ejb.EJB;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * <p>
 * This is a simple servlet that invokes server-side processing of Fibonacci
 * sequences via stateless EJB entities, and plots the corresponding
 * processing times via Javascript calls to Google Charts
 * </p>
 *
 * @author <a href="mailto:idownard@opnet.com">Ian Downard</a>
 */
public class FiboServlet extends HttpServlet {
    @EJB(name="com.riverbed.test.fibo.FiboRemote")
    private FiboRemote fibo;
    @EJB
    private ConcurrencyManager concurrencyManager;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        try {
            Integer fibonacci_argument = Integer.parseInt(request.getParameter("value"));
            Integer fibonacci_iterations = Integer.parseInt(request.getParameter("iterations"));
            Integer timeout = Integer.parseInt(request.getParameter("timeout"));

            //int[] numbers = {43,42,49};
            ArrayList<Task> tasks = new ArrayList<Task>();
            long now = System.currentTimeMillis();

            for (int i = 0; i < fibonacci_iterations; i++)
                tasks.add(new FibonacciTask(fibonacci_argument, timeout));

            Collection<Task> completed_tasks = (concurrencyManager.executeAndWait(tasks, timeout));
            long endTime = System.currentTimeMillis() - now;
            //fibo.bean_entry_point(fibonacci_argument);




            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<html>");
            out.println("<head> ");
            out.println(
                    "<script type=\"text/javascript\" src=\"https://www.google.com/jsapi\"></script>" +
                            "<script type=\"text/javascript\">\n" +
                            "      google.load(\"visualization\", \"1\", {packages:[\"corechart\"]});\n" +
                            "      google.setOnLoadCallback(drawChart);\n" +
                            "      function drawChart() {\n" +
                            "        var data = new google.visualization.DataTable();\n" +
                            "        data.addColumn('number', 'Iteration');\n" +
                            "        data.addColumn('number', 'Time (ms)');\n" +
                            "        data.addRows([\n");
            int completed=0;
            long result=0;
            for (Task t : completed_tasks)
            {
                result = ((FibonacciTask) t).getResult();
                if (result!=0) {
                    completed++;
                    out.println("[" + completed + ", " + ((FibonacciTask) t).getElapsedTime() + "],");
                }
            }

            String charttype = "LineChart";
            if (completed < 2) charttype = "ScatterChart";
            out.println("]);\n\n" +
                    "        var options = {\n" +
                    //"        width: 1000, height: 240," +
                    "        title: 'Fibonacci(" + fibonacci_argument + ") Processing Time', legend: {position: 'none'}, vAxis: {title: 'Time (ms)'}, hAxis: {title: 'Iteration'}};\n" +
                    "        \n" +
                    "        var chart = new google.visualization."+charttype+"(document.getElementById('chart_div'));\n" +
                    "        chart.draw(data, options);\n" +
                    "      }\n" +
                    "    </script>");
            out.println("<title>EJB Load Testing Utility</title> " +
                    "<style type=\"text/css\"> " +
                    "body { " +
                    "font-family: verdana, tahoma, sans-serif; " +
                    "text-align: center; " +
                    "} " +
                    "h1 { " +
                    "margin-top: 50px; " +
                    "color: #cc6666; " +
                    "} " +
                    ".message_text { " +
                    "color: #996666; " +
                    "} " +
                    "</style> " +
                    "</head>");

            out.println("<body>" +
                    "<h1><A href=\"index.jsp\">EJB Load Testing Utility</a></h1> " +
                    "<form method=post action=\"FiboServlet\">" +
                    "<p class=\"message_text\">This calculator computes Fibonacci numbers concurrently with an iterative" +
                    "<br>algorithm running in a stateless EJB bean.</p>" +
                    "<p>Fibonacci value to compute: <input type=text name=value value=\"" + fibonacci_argument +"\" size=5>\n" +
                    "<p>Number of times to compute it (concurrent EJBs): <input type=text name=iterations value=\"" + fibonacci_iterations +"\" size=5> </p>\n" +
                    "<p>Longest time to wait for everything to complete (ms): <input type=text name=timeout value=\"" + timeout +"\" size=5> </p>" +
                    "<input type=submit value=\"Submit\"></p>" +
                    "</form>" +
                    "<HR><H3>Result:</H3><P>");

            //FiboBean fibobean = new FiboBean();
            //long here = fibobean.bean_entry_point(Integer.getInteger(request.getParameter("value")));
            //long here = fibobean.bean_entry_point(44);
            //out.println("<h1>FiboBean returned after " + here + "ms</h1>");

            if (completed_tasks.size()!=0) out.println("<p>Fibonacci(" + fibonacci_argument + ") = " + result + "</p>");
//          out.println("<p>Fibonacci(" + fibonacci_argument + ") = " + fibo.getResult() + "</p>");
//          out.println("<p>Fibonacci(" + fibonacci_argument + ") computed in " + fibo.getElapsedTime()/1000F + "seconds</p>");
            out.println("<p>" + completed + " iterations computed within " + endTime + "ms:");
            out.println("<p>" + (fibonacci_iterations-completed) + " iterations aborted");
            out.println("<p><div id=\"chart_div\"></div></p>");
            out.println("</body>");
            out.println("</html>");
        } catch (NumberFormatException e) {
            response.sendRedirect(response.encodeRedirectURL("index.jsp"));
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception e) {
            System.err.println(e);
            //try again
            //response.sendRedirect(response.encodeRedirectURL("index.jsp"));
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<html><body>An exception occurred.  Trying again in 5s...<pre>" + e +"</pre>");
            out.flush();
            try {
                Thread.sleep(5000);
                processRequest(request, response);
            } catch (Exception e1) {
                e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                out.println("<B>EJB concurrency error.  Try running a less complex test.</B>");
                //response.sendRedirect(response.encodeRedirectURL("index.jsp"));
            }


        }
    }
}