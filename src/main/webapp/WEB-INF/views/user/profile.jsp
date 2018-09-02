<%@include file="/WEB-INF/taglib.jsp"%>
<div class="row justify-content-center">
    <div>
        <div class="form-group">
            <span>${user.status}</span>
        </div>
        <div class="form-group">
            <img src="${image}" alt="user photo" width=256 height="256">
        </div>
        <form action="/user/profile/photo?${_csrf.parameterName}=${_csrf.token}" method="post" enctype="multipart/form-data">
            <div class="form-group">
                <input type="file" class="form-control-file" name="profileImage"><br>
            </div>
            <div class="d-flex justify-content-around">
                <button type="submit" class="btn btn-success">Upload</button>
            </div>
        </form>
    </div>
    <div>
        <form:form action="/user/profile" method="post" modelAttribute="user">
            <div class="form-group">
                <label for="firstName">First name:</label>
                <form:input path="firstName" cssClass="form-control" id="firstName" required="required"/>
            </div>
            <div class="form-group">
                <label for="lastName">Last name:</label>
                <form:input path="lastName" cssClass="form-control" id="lastName" required="required"/>
            </div>
            <div class="form-group">
                <c:forEach items="${userGenders}" var="gender">
                    <form:radiobutton path="userGender" value="${gender}"/>${gender.genderName}
                </c:forEach>
            </div>
            <div class="form-group">
                <label for="birthDate">Birth date:</label>
                <form:input id="birthDate" path="birthDate" type="text" cssClass="form-control" placeholder="Date" onfocus="(this.type='date')" onblur="(this.type='text')"/>
            </div>
            <div class="form-group">
                <label for="telephone">Telephone:</label>
                <form:input path="telephone" cssClass="form-control" id="telephone" required="required"/>
            </div>
            <div class="form-group">
                <label for="country">Country:</label>
                <form:select path="country" cssClass="form-control" id="country" required="required">
                    <form:options items="${countries}" itemLabel="country"/>
                </form:select>
            </div>
            <div class="form-group">
                <label for="password">Password:</label>
                <form:input path="password" type="password" cssClass="form-control" id="password" required="required"/>
            </div>
            <div class="form-group">
                <label for="passwordConfirm">Password confirm:</label>
                <form:input path="passwordConfirm" type="password" value="${user.password}" cssClass="form-control" id="passwordConfirm" required="required"/>
            </div>
            <div class="form-group d-flex justify-content-around">
                <button type="submit" class="btn btn-success">Update</button>
            </div>
            <div class="form-group">
                <form:errors path="*"/>
            </div>
        </form:form>
    </div>
</div>
<div class="row justify-content-around">
    <form action="/user/delete?${_csrf.parameterName}=${_csrf.token}" method="post">
        <button type="submit" class="btn btn-danger">Delete account</button>
    </form>
</div>
