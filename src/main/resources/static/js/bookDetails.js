function validateQuantity() {
            var maxQuantityInput = document.getElementById('maxQuantity');
            var amount = parseInt(maxQuantityInput.value);

            var quantityInput = document.getElementById('quantity');
            var quantity = parseInt(quantityInput.value);

            if (quantity > amount) {
                alert('Quantity cannot be greater than ' + amount);
                return false;
            }

            return true;
        }