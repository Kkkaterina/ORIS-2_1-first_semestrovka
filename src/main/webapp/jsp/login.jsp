<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="pageTitle" value="Вход - Vintage Dishes" scope="request"/>
<jsp:include page="header.jsp"/>

<div class="container">
    <div class="auth-form">
        <h2>Вход в аккаунт</h2>

        <c:if test="${not empty error}">
            <div class="error-message">
                <c:out value="${error}"/>
            </div>
        </c:if>

        <form action="${pageContext.request.contextPath}/login" method="post">
            <c:if test="${not empty param.redirect}">
                <input type="hidden" name="redirect" value="<c:out value='${param.redirect}'/>">
            </c:if>

            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email"
                       value="<c:out value='${param.email}'/>"
                       required class="form-input">
            </div>

            <div class="form-group">
                <label for="password">Пароль:</label>
                <input type="password" id="password" name="password"
                       required class="form-input">
            </div>

            <button type="submit" class="submit-btn">Войти</button>
        </form>

        <div class="auth-links">
            <p>Нет аккаунта? <a href="${pageContext.request.contextPath}/register">Зарегистрируйтесь</a></p>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp"/>