<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="template" tagdir="/WEB-INF/tags"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="resources/css/index.css" rel="stylesheet" type="text/css" />

</head>
<body>
	
	<ul>
		<li>
			<form class="taskForm" name=input method=POST>
				<p class="taskName"><spring:message code="label.tasks"/></p>
				<input type=hidden name="generatedId" value=""/> 
				<input
					class="taskFormButtons" type=button name=submit value="Add task"
					onclick="window.open('tasks.htm','popUpWindow','height=500,width=300,left=100,top=100,resizable=yes,scrollbars=yes,status=yes');" />
			</form>
			<ul>
				<c:forEach var="task" items="${tasks}">
					<template:taskNode task="${task}" />
				</c:forEach>
			</ul>
		</li>
	</ul>
</body>
</html>