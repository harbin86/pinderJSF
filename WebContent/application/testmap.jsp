<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://code.google.com/p/gmaps4jsf/" prefix="m"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style>
  html, body, #form {
    height: 100%;
    margin: 0px;
    padding: 0px
  }
</style>

<script type="text/javascript"
  src="https://maps.googleapis.com/maps/api/js?sensor=true">
</script> 
<title>Insert title here</title>
</head>
<body>
<jsp:useBean id="date" class="java.util.Date" /> 
<p>The date/time is <%= date %>
<f:view>
    <h:form id="form">
            <m:map width="100%" height="100%" latitude="37.13" longitude="22.43" enableScrollWheelZoom="true">
                <m:marker>
                    <m:htmlInformationWindow htmlText="This is Sparta, Greece"/>
                </m:marker>
            </m:map>
    </h:form>
</f:view>
</body>
</html>