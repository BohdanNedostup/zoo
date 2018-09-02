<%@include file="/WEB-INF/taglib.jsp"%>

<div class="row justify-content-around">
    <div>
        <form:form action="/login" method="post">
            <legend>Welcome back! Please sign in</legend>
            <div class="form-group">
                <input type="email" class="form-control" placeholder="Email" name="emailParam" required>
            </div>
            <div class="form-group">
                <input type="password" class="form-control" placeholder="Password" name="passwordParam" required>
            </div>
            <div class="form-group form-check">
                <label class="form-check-label">
                    <input class="form-check-input" type="checkbox" name="rememberMe"> Remember me
                </label>
            </div>
            <button type="submit" class="btn btn-success">Login</button>
        </form:form>
    </div>
</div>
<div class="row justify-content-around">
    <div>
        <span>Don`t have account? <a href="/register">Register</a></span>
    </div>
</div>