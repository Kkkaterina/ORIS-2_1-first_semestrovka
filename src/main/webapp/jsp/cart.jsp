<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="pageTitle" value="Корзина - Vintage Dishes" scope="request"/>
<jsp:include page="header.jsp"/>

<div class="container">
    <h1>Корзина</h1>

    <c:choose>
        <c:when test="${empty cartItems}">
            <div class="empty-cart">
                <p>Ваша корзина пуста</p>
                <a href="${pageContext.request.contextPath}/" class="submit-btn">Назад</a>
            </div>
        </c:when>
        <c:otherwise>
            <div class="cart-items">
                <c:forEach var="entry" items="${cartItems}">
                    <c:set var="product" value="${productService.getProductById(entry.key).orElse(null)}"/>
                    <c:if test="${not empty product}">
                        <div class="cart-item">
                            <div class="item-image">
                                <c:choose>
                                    <c:when test="${not empty product.imageUrl}">
                                        <img src="${pageContext.request.contextPath}${product.imageUrl}" alt="${product.name}">
                                    </c:when>
                                    <c:otherwise>
                                        <div class="no-image"></div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="item-info">
                                <h3>${product.name}</h3>
                                <p class="item-description">${product.description}</p>
                                <div class="item-details">
                                    <span>Цена: <fmt:formatNumber value="${product.price}" type="currency"/></span>
                                    <span>Количество: ${entry.value}</span>
                                    <span>Сумма: <fmt:formatNumber value="${product.price * entry.value}" type="currency"/></span>
                                </div>
                            </div>
                            <div class="item-actions">
                                <form action="${pageContext.request.contextPath}/cart" method="post">
                                    <input type="hidden" name="action" value="remove">
                                    <input type="hidden" name="productId" value="${product.id}">
                                    <button type="submit" class="btn-remove">Удалить</button>
                                </form>
                            </div>
                        </div>
                    </c:if>
                </c:forEach>
            </div>

            <div class="cart-summary">
                <c:set var="total" value="0"/>
                <c:forEach var="entry" items="${cartItems}">
                    <c:set var="product" value="${productService.getProductById(entry.key).orElse(null)}"/>
                    <c:if test="${not empty product}">
                        <c:set var="total" value="${total + (product.price * entry.value)}"/>
                    </c:if>
                </c:forEach>

                <div class="total-price">
                    <h3>Итого: <fmt:formatNumber value="${total}" type="currency"/></h3>
                </div>

                <div class="cart-actions">
                    <a href="${pageContext.request.contextPath}/" class="btn-continue">← Продолжить покупки</a>
                    <a href="${pageContext.request.contextPath}/checkout" class="btn-checkout">Оформить заказ →</a>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<jsp:include page="footer.jsp"/>