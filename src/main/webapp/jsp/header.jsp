<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><c:out value="${not empty pageTitle ? pageTitle : 'Vintage Dishes Shop'}"/></title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/style.css">

</head>
<body>
<header class="header">
    <div class="container">
        <div class="logo">
            <h1>Vintage And Love</h1>
        </div>
        <nav class="nav">
            <ul>
                <li><a href="${pageContext.request.contextPath}/">Главная</a></li>
                <li><a href="${pageContext.request.contextPath}/products">Каталог</a></li>

                <c:choose>
                    <c:when test="${not empty sessionScope.user}">
                        <li><a href="${pageContext.request.contextPath}/profile">Профиль</a></li>
                        <li><a href="${pageContext.request.contextPath}/orders">Мои заказы</a></li>
                        <li><a href="${pageContext.request.contextPath}/cart">Корзина</a></li>

                        <li>
                            <form action="${pageContext.request.contextPath}/logout" method="post" class="logout-form">
                                <button type="submit">Выйти (${sessionScope.user.name})</button>
                            </form>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="${pageContext.request.contextPath}/login">Войти</a></li>
                        <li><a href="${pageContext.request.contextPath}/register">Регистрация</a></li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </nav>
    </div>
</header>