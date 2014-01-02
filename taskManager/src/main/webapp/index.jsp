<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@taglib prefix="template" tagdir="/WEB-INF/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>

<title>Task Manager</title>
<meta name="author" content="Jenna Smith" />
<meta name="copyright" content="Copyright 2006 growldesign" />
<meta name="keywords" content="" />
<meta name="description" content="" />
<meta http-equiv="imagetoolbar" content="no" />
<link href="resources/css/bc-stylesheet.css" rel="stylesheet"
	type="text/css" />

    <script type="text/javascript">
        (function() {
            if (typeof window.janrain !== 'object') window.janrain = {};
            if (typeof window.janrain.settings !== 'object') window.janrain.settings = {};

            janrain.settings.tokenUrl = 'http://localhost:8080/taskManager/callback_url';

            function isReady() { janrain.ready = true; };
            if (document.addEventListener) {
                document.addEventListener("DOMContentLoaded", isReady, false);
            } else {
                window.attachEvent('onload', isReady);
            }

            var e = document.createElement('script');
            e.type = 'text/javascript';
            e.id = 'janrainAuthWidget';

            if (document.location.protocol === 'https:') {
                e.src = 'https://rpxnow.com/js/lib/reanad/engage.js';
            } else {
                e.src = 'http://widget-cdn.rpxnow.com/js/lib/reanad/engage.js';
            }

            var s = document.getElementsByTagName('script')[0];
            s.parentNode.insertBefore(e, s);
        })();
    </script>
</head>

<body>
	<div id="container">
		<template:header/>
        <template:language/>
		<div id="content">
			<h2 id="idea">Improvements and fixes</h2>
			<h3>Fixes</h3>
			<p>Unit tests for controllers.</p>
			<p>Validation for form fields - don't try to find defects, there
				are plenty, and I'm aware of them.</p>
			<p>UI changes. Make mind map for tasks, fix buttons and other
				minor fixes.</p>
			<p>Add logger settings.</p>
			<p>Handle errors thrown.Add error messages where necessary.</p>
			<p>Add warning message when a task is deleted.</p>
			<p>Fix the error that is displayed in the tomcat at startup.</p>
			<p>Some small little problems arround get and post( add spring
				web flow maybe).</p>
			<p>This is all that comes into my mind at the moment, but I will
				update as soon as I remember something.</p>


			<h3>Improvements</h3>
			<p>Make the board configurable, people should be able to add
				their own categories.</p>
			<p>Add alert when removing a task.</p>
			<p>Add spring security.</p>
			<p>Make tasks more visible according to their status and due
				dates.</p>
			<p>Internationalization.</p>
			<p>Use velocity</p>
			
			<table>
				<tr>
                    <div id="janrainEngageEmbed"></div>
				</tr>
			</table>
		</div>
	</div>
	<div id="footer">
		<div id="banner">something bla bla bla</div>
		<!-- Please select "design by growldesign" if you leave the design as is or "inspired by growldesign" if you make any changes. -->
		<!-- It is not an imperitive that you leave a link to my site but I would appreciate it -->
		<p>
			Copyright &copy; 2006 Company. All Rights Reserved. <a
				href="http://validator.w3.org/check/referer">XHTML 1.1</a> | <a
				href="http://jigsaw.w3.org/css-validator/check/referer?warning=no&amp;profile=css2">CSS</a>
			| design/inspired by <a href="http://www.growldesign.co.uk">growldesign</a>
		</p>
	</div>
</body>
</html>
