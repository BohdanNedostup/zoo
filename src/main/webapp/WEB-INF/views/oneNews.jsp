<%@include file="/WEB-INF/taglib.jsp" %>

<div class="container newsArea">
    <p>${news.createdAt}</p>
    <h2>${news.title}</h2>
    <c:if test="${news.imageUrl != null || news.imageUrl != ''}">
        <div class="newsImage">
            <img src="${news.imageUrl}" alt="news photo" height="300" width="300">
        </div>
    </c:if>
    <p>${news.text}</p>
    <div id="mc-container"></div>
</div>
<script type="text/javascript">
    cackle_widget = window.cackle_widget || [];
    cackle_widget.push({widget: 'Comment', id: 60604});
    (function() {
        var mc = document.createElement('script');
        mc.type = 'text/javascript';
        mc.async = true;
        mc.src = ('https:' == document.location.protocol ? 'https' : 'http') + '://cackle.me/widget.js';
        var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(mc, s.nextSibling);
    })();
</script>