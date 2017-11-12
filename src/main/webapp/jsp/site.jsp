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

<section class="userInfo mrg-5 col-4">

    <img src="${user.avatar_url}">
    <br/>

    <p>
        Name: ${user.name}
    </p>
    <p>
        Email: ${user.email}
    </p>
</section>

<section class="publicRepos mrg-1 col-3">
alala
</section>

</body>
</html>