<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="pageTitle" value="Главная - Vintage Dishes" scope="request"/>
<jsp:include page="header.jsp"/>

<div class="container">
    <section class="hero">
        <h2>Найдите свою уникальную винтажную посуду</h2>
        <p>Советский фарфор, дореволюционное серебро, хрусталь и многое другое</p>

        <form action="${pageContext.request.contextPath}/" method="get" class="search-form">
            <input type="text" name="search" placeholder="Найти посуду..."
                   value="<c:out value='${param.search}'/>">
            <button type="submit">Найти</button>
        </form>
    </section>

    <section class="categories">
        <h3>Категории</h3>
        <div class="category-filters">
            <a href="${pageContext.request.contextPath}/"
               class="category-btn <c:if test='${empty param.category}'>active</c:if>">
                Все товары
            </a>
            <c:forEach var="category" items="${categories}">
                <a href="${pageContext.request.contextPath}/?category=${category}"
                   class="category-btn <c:if test='${param.category == category}'>active</c:if>">
                        ${category}
                </a>
            </c:forEach>
        </div>
    </section>

    <section class="products-grid">
        <h3>
            <c:choose>
                <c:when test="${not empty param.search}">
                    Результаты поиска: "<c:out value='${param.search}'/>"
                </c:when>
                <c:when test="${not empty param.category}">
                    Категория: <c:out value='${param.category}'/>
                </c:when>
                <c:otherwise>
                    Все товары
                </c:otherwise>
            </c:choose>
            <span class="products-count">(${products.size()} товаров)</span>
        </h3>

        <c:choose>
            <c:when test="${empty products}">
                <div class="no-products">
                    <p>Товары не найдены. Попробуйте изменить критерии поиска.</p>
                </div>
            </c:when>
            <c:otherwise>
                <div class="products">
                    <c:forEach var="product" items="${products}">
                        <div class="product-card">
                            <div class="product-image">
                                <c:choose>
                                    <c:when test="${not empty product.imageUrl}">
                                        <img src="${pageContext.request.contextPath}${product.imageUrl}"
                                             alt="${product.name}">
                                    </c:when>
                                    <c:otherwise>
                                        <div class="no-image"></div>
                                    </c:otherwise>
                                </c:choose>
                            </div>

                            <div class="product-info">
                                <h4 class="product-name">
                                    <c:out value="${product.name}"/>
                                </h4>
                                <p class="product-description">
                                    <c:out value="${product.description}"/>
                                </p>
                                <div class="product-meta">
                                    <span class="era">Эпоха: <c:out value="${product.era}"/></span>
                                    <span class="material">Материал: <c:out value="${product.material}"/></span>
                                </div>
                                <div class="product-price">
                                    <fmt:formatNumber value="${product.price}" type="currency" currencyCode="RUB"/>
                                </div>
                            </div>

                            <div class="product-actions">
                                <c:choose>
                                    <c:when test="${product.available}">
                                        <form action="${pageContext.request.contextPath}/cart?action=add" method="post" class="add-to-cart-form">
                                            <input type="hidden" name="productId" value="${product.id}">
                                            <input type="number" name="quantity" value="1" min="1" max="10" class="quantity-input">
                                            <button type="submit" class="add-to-cart-btn">В корзину</button>
                                        </form>
                                    </c:when>
                                </c:choose>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
    </section>
</div>

<jsp:include page="footer.jsp"/>