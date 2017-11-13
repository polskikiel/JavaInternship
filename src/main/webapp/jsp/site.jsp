<%@page language="Java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>GitInfo</title>
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css"/> " type="text/css">
</head>
<body>

<section class="userInfo mrg-3 col-2">

    <a href="${user.html_url}">
        <img src="${user.avatar_url}">
    </a>
    <br/>

    <p>
        Name: ${user.name}
    </p>
    <p>
        Email: ${user.email}
    </p>
</section>

<section class="publicRepos mrg-1 col-3">
    <c:forEach items="${user.repos}" var="repo">
        <c:if test="${repo.priv == false}">
            ${repo.language}<br/>
            ${repo.description}<br/>
            XDDD
        </c:if>
    </c:forEach>
</section>
<div style="clear: both"></div>
</body>
</html>