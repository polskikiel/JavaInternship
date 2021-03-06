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
    <h2>Profile</h2>

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
    <br/>
    <h3>Language statistics</h3>

    <h4>Projects written in</h4>


    <c:forEach items="${usedLanguagesMap}" var="entry">
        <p style="font-size: 14px;">${entry.key} - ${entry.value}</p>
    </c:forEach>
    <br/>

    <h4>All bytes together</h4>

    <c:forEach items="${mergedLanguageMaps}" var="entry">
        <p style="font-size: 14px;">${entry.key} - ${entry. value}</p>
    </c:forEach>

    <br/>
    <br/>
</section>

<section class="publicRepos mrg-2 col-5">
    <h2>Public repositories</h2>
    <br/>

    <c:forEach items="${user.repos}" var="repo">
        <c:if test="${repo.priv == false}">
            <p style="font-weight: 600; line-height: 0%;">
                <a href="${repo.html_url}">${repo.name}</a>
            <p>

            <p style="font-size: 10px; font-weight: 600;">
            <c:forEach items="${repo.languagesMap}" var="entry">
                ${entry.key} - ${entry.value}&nbsp;&nbsp;&nbsp;&nbsp;
            </c:forEach>
            </p>

            <p style="font-size: 14px">
                    ${repo.description}
            </p>

            <p style="text-align: right; font-size: 10px;">
                created at <br/>
                    ${fn:split(repo.created_at, 'T')[0]}
                    ${fn:substring(fn:split(repo.created_at, 'T')[1], 1 , fn:length(fn:split(repo.created_at, 'T')[1]) - 1)}
            </p>

            <br/>
        </c:if>
    </c:forEach>
</section>
<div style="clear: both"></div>
</body>
</html>