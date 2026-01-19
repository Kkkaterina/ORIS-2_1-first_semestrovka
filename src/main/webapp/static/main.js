document.addEventListener('DOMContentLoaded', function() {
    initFormValidation();

    initCartFunctionality();

    initSearchFunctionality();
});

function initFormValidation() {
    const forms = document.querySelectorAll('form');
    forms.forEach(form => {
        form.addEventListener('submit', function(e) {
            const requiredFields = form.querySelectorAll('[required]');
            let isValid = true;

            requiredFields.forEach(field => {
                if (!field.value.trim()) {
                    isValid = false;
                    field.style.borderColor = '#e74c3c';
                } else {
                    field.style.borderColor = '';
                }
            });

            if (!isValid) {
                e.preventDefault();
                alert('Заполните все поля');
            }
        });
    });
}
// обновление колличества
function initCartFunctionality() {
    const quantityInputs = document.querySelectorAll('.quantity-input');
    quantityInputs.forEach(input => {
        input.addEventListener('change', function() {
            if (this.value < 1) this.value = 1;
            if (this.value > 10) this.value = 10;
        });
    });
}

function initSearchFunctionality() {
    const searchInput = document.querySelector('input[name="search"]');
    if (searchInput) {
        searchInput.addEventListener('input', debounce(function(e) {

            console.log('Search:', e.target.value);
        }, 300));
    }
}



function addToCart(productId, quantity) {

    fetch('/cart/add', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: `productId=${productId}&quantity=${quantity}`
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                showNotification('Товар добавлен в корзину!', 'success');
                updateCartCounter(data.cartCount);
            } else {
                showNotification('Ошибка при добавлении в корзину', 'error');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            showNotification('Ошибка сети', 'error');
        });
}

function showNotification(message, type) {
    const notification = document.createElement('div');
    notification.className = `notification ${type}`;
    notification.textContent = message;
    notification.style.cssText = `
        position: fixed;
        top: 20px;
        right: 20px;
        padding: 1rem 2rem;
        border-radius: 4px;
        color: white;
        z-index: 1000;
        background: ${type === 'success' ? '#27ae60' : '#e74c3c'};
    `;

    document.body.appendChild(notification);

    setTimeout(() => {
        notification.remove();
    }, 3000);

    document.getElementById('registerForm').addEventListener('submit', function(e) {
        const password = document.getElementById('password').value;
        const confirmPassword = document.getElementById('confirmPassword').value;

        if (password !== confirmPassword) {
            e.preventDefault();
            alert('Пароли не совпадают!');
            return false;
        }

        if (password.length < 6) {
            e.preventDefault();
            alert('Пароль должен быть не менее 6 символов!');
            return false;
        }
    });
}