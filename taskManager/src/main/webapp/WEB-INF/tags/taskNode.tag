<%@ tag language="java"%>
<%@tag description="display the whole nodeTree" pageEncoding="UTF-8"%>
<%@attribute name="task" type="ro.reanad.taskmanager.model.Task"
	required="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="template" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<li>
	<form class="taskForm" name=input method=POST action=tasks.htm
		target="POPUPW"
		onsubmit="POPUPW = window.open('about:blank','POPUPW',
   'width=600,height=400');">
		<p class="taskName">${task.name}</p>
		<input type=hidden name="generatedId" value="${task.generatedId}">
		<input class="taskFormButtons" type=submit name=add
			value=<spring:message code="label.addsubtask"/> >
        <input class="taskFormButtons " type=submit
			name=edit value=<spring:message code="label.edit"/>>
        <input class="taskFormButtons "
			type=submit name=remove value=<spring:message code="label.remove"/> >
	</form>
    <c:if test="${fn:length(task.task)>0}">
		<ul>
			<c:forEach var="subtask" items="${task.task}">
				<template:taskNode task="${subtask}">
				</template:taskNode>
			</c:forEach>
		</ul>
	</c:if>
</li>