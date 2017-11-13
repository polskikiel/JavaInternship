<%@page language="Java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="Refresh" content="5;url=/">
    <title>GitInfo</title>
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css"/> " type="text/css">
</head>
<body>
${errorMsg}
<br/>
<br/>
<a href="/">Click here to try login again</a>
</body>
</html>