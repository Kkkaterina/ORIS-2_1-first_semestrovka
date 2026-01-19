<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="pageTitle" value="Каталог товаров - Vintage Dishes" scope="request"/>
<jsp:include page="header.jsp"/>

<div class="container">
    <div class="page-title">
        <h1>Каталог винтажной посуды</h1>
        <p>Все товары: ${products.size()} позиций</p>
    </div>

    <div class="products-grid">
        <c:forEach var="product" items="${products}">
            <div class="product-card">

                <div class="product-image">
                    <c:choose>
                        <c:when test="${not empty product.imageUrl}">
                            <img src="${pageContext.request.contextPath}${product.imageUrl}"
                                 alt="${product.name}">
                        </c:when>
                        <c:otherwise>
                            <div class="no-image">
                                <span>📷</span>
                                <p>Изображение</p>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>

                <div class="product-info">
                    <h3 class="product-name">${product.name}</h3>
                    <p class="product-description">${product.description}</p>

                    <div class="product-details">
                        <div class="detail-item">
                            <strong>Эпоха:</strong> ${product.era}
                        </div>
                        <div class="detail-item">
                            <strong>Материал:</strong> ${product.material}
                        </div>
                        <div class="detail-item">
                            <strong>Категория:</strong> ${product.category}
                        </div>
                    </div>

                    <div class="product-price">
                        <fmt:formatNumber value="${product.price}" type="currency" currencyCode="RUB"/>
                    </div>
                </div>

                <div class="product-actions">
                    <c:choose>
                        <c:when test="${product.available}">
                            <form action="${pageContext.request.contextPath}/cart?action=add" method="post">
                                <input type="hidden" name="productId" value="${product.id}">
                                <input type="number" name="quantity" value="1" min="1" max="10" class="quantity-input">
                                <button type="submit" class="add-to-cart-btn">В корзину</button>
                            </form>
                        </c:when>
                        <c:otherwise>
                            <button disabled class="out-of-stock-btn">Нет в наличии</button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
<jsp:include page="footer.jsp"/>