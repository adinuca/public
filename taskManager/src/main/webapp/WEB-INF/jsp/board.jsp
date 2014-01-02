<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>

<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta charset="utf-8"/>
    <title>Task manager</title>

    <link rel="stylesheet" href="resources/css/jquery-ui.css"/>
    <link href="resources/css/bc-stylesheet.css" rel="stylesheet"
          type="text/css"/>

    <script src="http://code.jquery.com/jquery-1.8.3.js"></script>
    <script src="resources/js/jquery.treeview.js" type="text/javascript"></script>
    <script src="http://code.jquery.com/ui/1.9.2/jquery-ui.js"></script>
    <script>
        $(function showTabs() {
            $("#tabs").tabs();
        });
        $(document).ready(showTabs);
        $(document).ready(function () {

            $("#tasks").treeview({
                toggle: function () {
                    console.log("%s was toggled.", $(this).find(">span").text());
                }
            });

        });
    </script>
    <link href="CSS/tasks.css" rel="stylesheet" type="text/css"/>
</head>

<body>
<div id="container">
    <tags:header/>
    <tags:language/>
    <div id="content">
        <div id="tabs">
            <ul>
                <li><a href="tasksDisplay.htm"><spring:message code="label.all"/></a></li>
                <li><a href="tasksDisplay.htm?category=home"><spring:message code="label.home"/></a></li>
                <li><a href="tasksDisplay.htm?category=personal"><spring:message code="label.personal"/></a></li>
                <li><a href="tasksDisplay.htm?category=work"><spring:message code="label.work"/></a></li>
                <li><a href="tasksDisplay.htm?category=goals"><spring:message code="label.goals"/></a></li>

                <li>
                    <form name=input method=POST action="logout.htm">
                        <input type="submit" value="<spring:message code="label.logout"/>"/>
                    </form>
                </li>
                <!-- <li><a href="upload.htm">Upload tasks xml</a></li>
-->
            </ul>

        </div>
    </div>
    <div id="footer"></div>
</div>
</body>
</html>