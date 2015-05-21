<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Scientists Page</title>
</head>
<body>
<%@page import="sg.com.pinder.shiro.actions.Actions"%>

<form action="/simpleshirosecuredapplication/masterservlet" method="get">
<%@include file="/simpleshirosecuredapplication/common/commonformstuff.jsp" %>
<h2>Scientists Page</h2>
This page is meant for scientists only. If you are not one, please go away. Available functions:
    <table class="sample">
        <thead>
        <tr>
            <th>Function Name</th>
            <th>Do It</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>Research: </td>
            <td><button type="submit" name="action" value="<%=Actions.RESEARCH_NEW_STUFF.getName()%>">Do It</button></td>
        </tr>
        <tr>
            <td>Write article: </td>
            <td><button type="submit" name="action" value="<%=Actions.WRITE_ARTICLE.getName()%>">Do It</button></td>
        </tr>
        <tr>
            <td>Prepare talk: </td>
            <td><button type="submit" name="action" value="<%=Actions.PREPARE_TALK.getName()%>">Do It</button></td>
        </tr>
        </tbody>
    </table>
</form>
</body>
</html>