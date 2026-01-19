<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="pageTitle" value="Регистрация - Vintage Dishes" scope="request"/>
<jsp:include page="header.jsp"/>

<div class="container">
    <div class="auth-form">
        <h2>Регистрация</h2>

        <c:if test="${not empty error}">
            <div class="error-message">
                <c:out value="${error}"/>
            </div>
        </c:if>

        <form action="${pageContext.request.contextPath}/register" method="post" id="registerForm">
            <div class="form-group">
                <label for="name">Имя:</label>
                <input type="text" id="name" name="name"
                       value="<c:out value='${param.name}'/>"
                       required class="form-input">
            </div>

            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email"
                       value="<c:out value='${param.email}'/>"
                       required class="form-input">
            </div>

            <div class="form-group">
                <label for="password">Пароль:</label>
                <input type="password" id="password" name="password"
                       required class="form-input" minlength="6">
                <small>Минимум 6 символов</small>
            </div>

            <div class="form-group">
                <label for="confirmPassword">Подтвердите пароль:</label>
                <input type="password" id="confirmPassword" name="confirmPassword"
                       required class="form-input">
            </div>

            <button type="submit" class="submit-btn">Зарегистрироваться</button>
        </form>

        <div class="auth-links">
            <p>Уже есть аккаунт? <a href="${pageContext.request.contextPath}/login">Войдите</a></p>
        </div>
    </div>
</div>

<%--<script>--%>
<%--    document.getElementById('registerForm').addEventListener('submit', function(e) {--%>
<%--        const password = document.getElementById('password').value;--%>
<%--        const confirmPassword = document.getElementById('confirmPassword').value;--%>

<%--        if (password !== confirmPassword) {--%>
<%--            e.preventDefault();--%>
<%--            alert('Пароли не совпадают!');--%>
<%--            return false;--%>
<%--        }--%>

<%--        if (password.length < 6) {--%>
<%--            e.preventDefault();--%>
<%--            alert('Пароль должен быть не менее 6 символов!');--%>
<%--            return false;--%>
<%--        }--%>
<%--    });--%>
<%--</script>--%>

<jsp:include page="footer.jsp"/>