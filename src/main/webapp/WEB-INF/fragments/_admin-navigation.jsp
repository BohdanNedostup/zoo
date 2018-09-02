<%@include file="/WEB-INF/taglib.jsp" %>
<sec:authorize access="hasRole('ADMIN')">
    <ul class="nav nav-tabs nav-justified">
        <li class="nav-item " id="admin-menu1">
            <a href="/admin/manage/users" class="nav-link admin-nav-link">Users</a>
        </li>
        <li class="nav-item" id="admin-menu2">
            <a href="/admin/manage/news" class="nav-link admin-nav-link">News</a>
        </li>
        <li class="nav-item" id="admin-menu3">
            <a href="/admin/animals" class="nav-link admin-nav-link">Animals</a>
        </li>
        <li class="nav-item" id="admin-menu4">
            <a href="/admin/manage/products" class="nav-link admin-nav-link">Products</a>
        </li>
        <li class="nav-item" id="admin-menu6">
            <a href="/admin/manage/orders" class="nav-link admin-nav-link">Orders</a>
        </li>
    </ul>

    <script>
        document.addEventListener("DOMContentLoaded", function () {
            let links = document.getElementsByClassName("admin-nav-link");
            for (let i = 0; i < links.length; i++) {
                if (window.location.href.indexOf(links[i].innerHTML.toLowerCase()) > 0) {
                    links[i].setAttribute("class", "nav-link admin-nav-link active");
                }
            }
        })
    </script>
</sec:authorize>