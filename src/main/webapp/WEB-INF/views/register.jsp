<%@include file="/WEB-INF/taglib.jsp"%>

<div class="row justify-content-around">
    <div>
        <form:form action="/register" method="post" modelAttribute="registerUser">

            <div class="form-group">
                <form:input path="firstName" cssClass="form-control" placeholder="First name"/>
            </div>
            <div class="form-group">
                <form:input path="lastName" cssClass="form-control" placeholder="Last name"/>
            </div>
            <div class="form-group">
                <form:input path="email" type="email" cssClass="form-control" placeholder="Email"/>
            </div>
            <div class="form-group">
                <c:forEach items="${userGenders}" var="gender">
                    <form:radiobutton path="userGender" value="${gender}"/>${gender.genderName}
                </c:forEach>
            </div>
            <div class="form-group">
                <form:input path="birthDate" type="text" cssClass="form-control" placeholder="Date" onfocus="(this.type='date')" onblur="(this.type='text')"/>
            </div>
            <div class="form-group">
                <form:select path="country" cssClass="form-control">
                    <form:options items="${countries}" itemLabel="country" />
                </form:select>
            </div>
            <div class="form-group">
                <form:input path="Telephone" cssClass="form-control" placeholder="telephone"/>
            </div>
            <div class="form-group">
                <form:input path="password" type="password" cssClass="form-control" placeholder="Password"/>
            </div>
            <div class="form-group">
                <form:input path="passwordConfirm" type="password" cssClass="form-control" placeholder="Password confirm"/>
            </div>
            <div class="btn-group">
                <button type="submit" class="btn btn-success">Register</button>
                <button type="reset" class="btn btn-danger">Clear form</button>
            </div>
            <div class="form-group">
                <form:errors path="*"/>
            </div>
        </form:form>
    </div>
</div>