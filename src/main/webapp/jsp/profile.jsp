<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="pageTitle" value="Мой профиль - Vintage Dishes" scope="request"/>
<jsp:include page="header.jsp"/>

<div class="container">
    <div class="profile-container">
        <h1>Личный профиль</h1>

        <c:if test="${not empty param.success}">
            <div class="success-message">Вы обновили профиль</div>
        </c:if>

        <c:if test="${not empty param.error}">
            <div class="error-message">Этот email уже существует</div>
        </c:if>

        <div class="profile-info">
            <div class="info-item">
                <strong>Имя:</strong> ${user.name}
            </div>
            <div class="info-item">
                <strong>Email:</strong> ${user.email}
            </div>
            <div class="info-item">
                <strong>Роль:</strong> ${user.role}
            </div>
        </div>

        <div class="edit-form">
            <h3>Редактировать профиль</h3>
            <form action="${pageContext.request.contextPath}/profile" method="post">
                <div class="form-group">
                    <label for="name">Имя:</label>
                    <input type="text" id="name" name="name" value="${user.name}" required class="form-input">
                </div>

                <div class="form-group">
                    <label for="email">Email:</label>
                    <input type="email" id="email" name="email" value="${user.email}" required class="form-input">
                </div>

                <button type="submit" class="submit-btn">Сохранить изменения</button>
            </form>
        </div>

        <div class="danger-zone">
            <h3>Выйти из аккаунта навсегда</h3>
            <form action="${pageContext.request.contextPath}/profile/delete" method="post"
                  onsubmit="return confirm('Вы точно этого хотите?...')">
                <button type="submit" class="btn-danger">Удалить аккаунт</button>
            </form>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp"/>
