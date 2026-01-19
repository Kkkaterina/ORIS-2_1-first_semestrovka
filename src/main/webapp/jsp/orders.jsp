<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="pageTitle" value="Мои заказы - Vintage Dishes" scope="request"/>
<jsp:include page="header.jsp"/>

<div class="container">
    <h1>Мои заказы</h1>

    <c:choose>
        <c:when test="${empty orders}">
            <div class="empty-orders">
                <p>У вас пока нет заказов</p>
                <a href="${pageContext.request.contextPath}/" class="btn">Начать покупки</a>
            </div>
        </c:when>
        <c:otherwise>
            <div class="orders-list">
                <c:forEach var="order" items="${orders}">
                    <div class="order-card">
                        <div class="order-header">
                            <h3>Заказ #${order.id}</h3>
                            <span class="order-status">${order.status}</span>
                            <span class="order-date">
                                <fmt:formatDate value="${order.createdAt}" pattern="dd.MM.yyyy HH:mm"/>
                            </span>
                        </div>
                        <div class="order-items">
                            <c:forEach var="item" items="${order.items}">
                                <div class="order-item">
                                    <span>${item.product.name} x ${item.quantity}</span>
                                    <span><fmt:formatNumber value="${item.totalPrice}" type="currency"/></span>
                                </div>
                            </c:forEach>
                        </div>
                        <div class="order-total">
                            Итого: <fmt:formatNumber value="${order.totalPrice}" type="currency"/>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<jsp:include page="footer.jsp"/>