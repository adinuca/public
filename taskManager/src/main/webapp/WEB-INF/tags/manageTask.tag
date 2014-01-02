<%@ tag language="java" pageEncoding="ISO-8859-1" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<form name="input" action="addTask.htm" method="post">
    <table>
        <tr>
            <td><input type="hidden" name="parentTaskId"
                       value="${task.parentTaskId}">
            </td>
        </tr>
        <tr>
            <td><input type="hidden" name="generatedId"
                       value=${task.generatedId}></td>
        </tr>
        <!-- <tr>
			<td><input type="hidden" name="dueDate" value=${task.dueDate}/></td>
		</tr>
		<tr>
			<td><input type="hidden" name="user" value=${task.userEmail}></input></td>
		</tr>
 -->
        <tr>
            <td><spring:message code="label.name"/></td>
            <td><input type="text" name="name" value="${task.name}"></td>
        </tr>
        <tr>
            <td><spring:message code="label.description"/></td>
            <td><textarea name="description">${task.description}</textarea></td>
        </tr>
        <tr>
            <td>Category</td>
            <td><select name="category">
                <c:if test="${not empty task.category}">
                    <option selected=${task.category}>${task.category}</option>
                </c:if>
                <option value="Work"><spring:message code="label.work"/></option>
                <option value="Study"><spring:message code="label.study"/></option>
                <option value="PersonalImprovement"><spring:message code="label.personalImprovement"/></option>
                <option value="Home"><spring:message code="label.home"/></option>
            </select></td>
        </tr>
        <tr>
            <td>Status</td>
            <td><select name="status">
                <c:if test="${not empty task.status}">
                    <option selected=${task.status}>${task.status}</option>
                </c:if>
                <option value="todo"><spring:message code="label.todo"/></option>
                <option value="defined"><spring:message code="label.defined"/></option>
                <option value="inProgress"><spring:message code="label.inprogress"/></option>
                <option value="completed"><spring:message code="label.completed"/></option>
            </select></td>
        </tr>
        <tr>
            <td><spring:message code="label.timeSpent"/></td>
            <td><input type="text" name="timeSpent" value=${task.timeSpent }></td>
        </tr>

        <tr>
            <td>URL</td>
            <td><input type="text" name="url" value=${task.url}></td>
        </tr>
        <tr>
            <td><input type="submit" name="addTask" value=
            <spring:message code="label.submit"/>/></td>
        </tr>
    </table>

</form>
