function validateForm() {
    var forms = document.querySelectorAll('.needs-validation');
    Array.prototype.slice.call(forms)
        .forEach(function (form) {
            form.addEventListener('submit', function (event) {
                if (!form.checkValidity()) {
                    event.preventDefault();
                    event.stopPropagation();
                }
                form.classList.add('was-validated');
            }, false);
        });
}

function clearModal() {
    $('.modal').on('hidden.bs.modal', function () {
        $(this).find('form')[0].reset();
        $(this).find('form.needs-validation').removeClass('was-validated');
        $(this).find('form.needs-validation input, form.needs-validation select').removeClass('is-invalid');
    });
}

function customizeDatePicker() {
    $('.date').datepicker({
        format: 'yyyy-mm-dd',
        autoclose: true,
    });

    $('.close-button').unbind();

    $('.close-button').click(function () {
        if ($('.datepicker').is(":visible")) {
            $('.date').datepicker('hide');
        } else {
            $('.date').datepicker('show');
        }
    });
}