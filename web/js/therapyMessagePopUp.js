function closePopup() {
    document.getElementById("successPopup").style.display = "none";
}

let pendingForm = null;

document.addEventListener("DOMContentLoaded", function () {
    document.querySelectorAll(".choose-therapist-btn").forEach(button => {
        button.addEventListener("click", function (e) {
            e.preventDefault(); // Prevent immediate form submission
            pendingForm = this.closest("form"); // Save the form reference
            document.getElementById("confirmPopup").style.display = "flex";
        });
    });

    document.getElementById("confirmBtn").addEventListener("click", function () {
        if (pendingForm) {
            pendingForm.submit(); // Submit the saved form
            closeConfirmPopup();
        }
    });
});

function closeConfirmPopup() {
    document.getElementById("confirmPopup").style.display = "none";
    pendingForm = null;
}
