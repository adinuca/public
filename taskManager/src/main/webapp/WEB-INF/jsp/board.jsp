<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="ro.reanad.taskmanager.model.Task,java.util.List"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta charset="utf-8" />
<title>Task manager</title>

<link rel="stylesheet" href="resources/css/jquery-ui.css" />
<link href="resources/css/bc-stylesheet.css" rel="stylesheet"
	type="text/css" />

<script src="http://code.jquery.com/jquery-1.8.3.js"></script>
<script src="resources/js/jquery.treeview.js" type="text/javascript"></script>
<script src="http://code.jquery.com/ui/1.9.2/jquery-ui.js"></script>
<script>
	$(function showTabs() {
		$("#tabs").tabs();
	});
	$(document).ready(showTabs);
	$(document).ready(function() {

		$("#tasks").treeview({
			toggle : function() {
				console.log("%s was toggled.", $(this).find(">span").text());
			}
		});

	});
</script>
<link href="CSS/tasks.css" rel="stylesheet" type="text/css" />
</head>

</head>
<body>

	<div id="container">
		<div id="header">
			<ul>
			</ul>
			<div>
				<h1>
					<a href="#">Task Manager</a>
				</h1>
				<h3>Welcome ${user}!</h3>
			</div>
		</div>
		<div id="content">
			<div id="tabs">
				<ul>
					<li><a href="tasksDisplay.htm">All</a></li>
					<li><a href="tasksDisplay.htm?category=home">Home</a></li>
					<li><a href="tasksDisplay.htm?category=personal">Personal</a></li>
					<li><a href="tasksDisplay.htm?category=work">Work</a></li>
					<li><a href="tasksDisplay.htm?category=goals">Goals</a></li>

					<li><form name=input method=POST action="logout.htm">
							<input type="submit" value="Logout" />
					</form></li>
					<!-- <li><a href="upload.htm">Upload tasks xml</a></li>
 -->
				</ul>
			</div>
		</div>
		<div id="footer"></div>
	</div>
</body>
</html>