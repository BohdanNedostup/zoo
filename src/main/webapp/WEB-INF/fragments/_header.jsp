<%@include file="/WEB-INF/taglib.jsp"%>
<header>
    <nav id="header-navbar" class="navbar navbar-expand-sm fixed-top justify-content-around">
        <a id="logo" class="navbar-brand" href="/"><img src="/resources/img/icons/ferarum_logo.png" alt="logo"></a>
        <div class="btn-group">
            <sec:authorize access="isAuthenticated()">
                <ul class="nav">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#">
                            <sec:authorize access="hasRole('ADMIN')">
                                <img src="/resources/img/icons/admin-with-cogwheels.png" alt="admin-icon">
                            </sec:authorize>
                            <sec:authorize access="!hasRole('ADMIN')">
                                <img src="/resources/img/icons/man-user.png" alt="user_icon">
                            </sec:authorize>
                        </a>
                        <div class="dropdown-menu">
                            <a class="dropdown-item" href="/user/profile">Profile</a>
                            <sec:authorize access="hasRole('ADMIN')">
                                <a class="dropdown-item" href="/admin/manage">Admin page</a>
                            </sec:authorize>
                            <sec:authorize access="hasRole('DOCTOR')">
                                <a class="dropdown-item" href="/admin/animals">Animals</a>
                            </sec:authorize>
                            <form:form action="/log_out" method="get">
                                <button type="submit" class="dropdown-item">Logout</button>
                            </form:form>
                        </div>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/user/orders">
                            <img id="shopping-cart" src="/resources/img/icons/shopping-cart.png" alt="cart_icon">
                        </a>
                    </li>
                </ul>
            </sec:authorize>
            <sec:authorize access="!isAuthenticated()">
                <ul class="nav">
                    <li class="nav-item">
                        <a class="nav-link" href="/login">
                            <img src="/resources/img/icons/login.png" alt="login_icon">
                        </a>
                    </li>
                </ul>
            </sec:authorize>
            <button id="menu-btn" type="button" class="btn btn-link">
                <img id="menu-icon" src="/resources/img/icons/three-parallel-lines.png"
                     alt="menu_icon">
            </button>
        </div>
    </nav>
</header>