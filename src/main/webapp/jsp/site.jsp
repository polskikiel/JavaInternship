<%@page language="Java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>GitInfo</title>
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css"/> " type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet">
</head>
<body>

<section class="userInfo mrg-2 col-3">

    <a href="${user.html_url}">
        <img src="${user.avatar_url}">
    </a>
    <br/>

    <p>
        ${user.name}
    </p>
    <p>
        ${user.email}
    </p>
    <p>
        ${user.bio}
    </p>
</section>

<section class="publicRepos mrg-2 col-5">
    <h2>Public repositories</h2>
    <c:forEach items="${user.repos}" var="repo">
        <c:if test="${repo.priv == false}">
            <p style="font-weight: 600">
                    ${repo.name}
            <p>

            <p style="font-size: 10px">
                written in ${repo.language}
            </p>

            <p style="text-align: right; font-size: 10px;">
                    ${fn:split(repo.created_at, 'T')[0]}
                    ${fn:substring(fn:split(repo.created_at, 'T')[1], 1 , fn:length(fn:split(repo.created_at, 'T')[1]) - 1)}
            </p>

            <p style="font-size: 14px">
                    ${repo.description}
            </p>

            <br/>
        </c:if>
    </c:forEach>
</section>
<div style="clear: both"></div>
</body>
</html>