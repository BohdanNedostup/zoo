<%@ taglib prefix="tilesx" uri="http://tiles.apache.org/tags-tiles-extras" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/taglib.jsp" %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">

    <link href="https://use.fontawesome.com/releases/v5.0.13/css/all.css"
          integrity="sha384-DNOHZ68U8hZfKXOrtjWvjxusGo9WQnrNx2sqG0tfsghAvtVlRW3tvkXWZh58N9jp" crossorigin="anonymous"
          rel="stylesheet">
    <tilesx:useAttribute name="stylesheets" id="list"/>
    <c:forEach items="${list}" var="item">
        <link rel="stylesheet" href="<c:out value="${item}"/>">
    </c:forEach>

    <tilesx:useAttribute name="adminPageStylesheets" id="cssList"/>
    <c:forEach items="${cssList}" var="item">
        <link rel="stylesheet" href="<c:out value="${item}"/>"/>
    </c:forEach>
</head>
<body>
<div class="container-fluid">
    <tiles:insertAttribute name="header"/>
    <tiles:insertAttribute name="navigation"/>
    <div class="container-fluid" id="body-wrapper">
        <tiles:insertAttribute name="admin-navigation"/>
        <tiles:insertAttribute name="body"/>
    </div>
</div>

<tilesx:useAttribute name="scripts" id="list"/>
<c:forEach items="${list}" var="item">
    <script src="<c:out value="${item}"/>"></script>
</c:forEach>

<tilesx:useAttribute name="adminPageScripts" id="scriptList"/>
<c:forEach items="${scriptList}" var="item">
    <script src="<c:out value="${item}"/>"></script>
</c:forEach>

</body>
</html>
