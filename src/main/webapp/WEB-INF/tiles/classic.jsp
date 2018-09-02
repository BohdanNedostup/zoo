<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/WEB-INF/taglib.jsp"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles-extras" prefix="tilesx" %>

<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <link  href="https://use.fontawesome.com/releases/v5.0.13/css/all.css" integrity="sha384-DNOHZ68U8hZfKXOrtjWvjxusGo9WQnrNx2sqG0tfsghAvtVlRW3tvkXWZh58N9jp" crossorigin="anonymous" rel="stylesheet">

    <tilesx:useAttribute id="list" name="stylesheets"/>
    <c:forEach var="item" items="${list}">
        <link rel="stylesheet" href="<c:out value="${item}"/>">
    </c:forEach>

    <tilesx:useAttribute name="mainPageStylesheets" id="cssList"/>
    <c:forEach items="${cssList}" var="item">
        <link rel="stylesheet" href="<c:out value="${item}"/>"/>
    </c:forEach>
</head>
<body>
    <div id="hellopreloader_preload" style="
        display: block;
        position: fixed;
        z-index: 99999;
        bottom: 0;
        right: 0;
        width: 100%;
        height: 100%;
        background: #35cffc url(http://hello-site.ru//main/images/preloads/tail-spin.svg) center center no-repeat;
        background-size: 200px;
    "></div>

    <script type="text/javascript">
        let hellopreloader = document.getElementById("hellopreloader_preload");

        function fadeOutnojquery(el) {
            el.style.opacity = 1;
            let interhellopreloader = setInterval(function() {
                el.style.opacity = el.style.opacity - 0.05;
                if (el.style.opacity <= 0.05) {
                    clearInterval(interhellopreloader);
                    hellopreloader.style.display = "none";
                }
            }, 16);
        }

        setTimeout(function() {
            fadeOutnojquery(hellopreloader)
        }, 1000);

    </script>
    <div class="container-fluid">
        <tiles:insertAttribute name="header"/>
        <tiles:insertAttribute name="navigation"/>
        <div class="container-fluid" id="body-wrapper">
            <tiles:insertAttribute name="body"/>
        </div>
        <tiles:insertAttribute name="footer"/>
    </div>

    <tilesx:useAttribute id="list" name="scripts"/>
    <c:forEach var="item" items="${list}">
        <script src="<c:out value="${item}"/>"></script>
    </c:forEach>

    <tilesx:useAttribute name="mainPageScripts" id="scriptList"/>
    <c:forEach items="${scriptList}" var="item">
        <script src="<c:out value="${item}"/>"></script>
    </c:forEach>
</body>
</html>
