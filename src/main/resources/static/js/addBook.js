function validateDate() {
            var dateInput = document.getElementById("year");
            var dateValue = new Date(dateInput.value);
            var today = new Date();

            // Убедиться, что выбранная дата не в будущем
            if (dateValue > today) {
                alert("The date cannot be in the future.");
                return false;
            }

            return true;
        }

        function validateForm() {
            return validateDate();
        }