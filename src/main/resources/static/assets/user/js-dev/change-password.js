document.querySelector('#test').addEventListener('click', () => {
    var changepassword = getValueFormPassword();
    if (validate(changepassword)) {
        $.ajax({
            type: 'POST',
            url: URL + `api/account/changepassword`,
            contentType: 'application/json',
            dataType: 'json',
            cache: false,
            data: JSON.stringify(changepassword),
            success: function(response) {
                    $("#message").html("Đã đổi mật khẩu ").addClass("alert-success");

            },
            error: function (error) {
                 if (error.status === 404) {
                    $("#message").html("Tài khoản không tồn tại!").addClass("alert-danger");
                } else if (error.status === 400) {
                    $("#message").html("Mật khẩu cũ không đúng!").addClass("alert-danger")
                } else if (error.status === 500) {
                    $("#message").html("Lỗi server vui lòng thử lại sau!").addClass("alert-danger")
                }

            }
        });
    }
});


/* ----------------------Toggle Password ------------------- */
function TogglePassword() {
    var password = document.getElementById('oldPassword')
    var newPassword = document.getElementById('newPassword')
    var confirmPassword = document.getElementById('confirmPassword')
    if (confirmPassword.type, newPassword.type, password.type === "password") {
        password.type = "text";
        newPassword.type = "text"
        confirmPassword.type = "text"
    } else {
        password.type = "password";
        newPassword.type = "password"
        confirmPassword.type = "password"
    }
}

/* ---------------------  get valve form ---------------------------------  */
let getValueFormPassword = () => {
    return {
        'id': document.getElementById('id_Apartment').textContent,
        'oldPassword': document.querySelector('#oldPassword').value.trim(),
        'newPassword': document.querySelector('#newPassword').value.trim(),
    }
}


/* ------------------- clean form ------------------------- */
let cleanFormChangePassword = () => {
    document.getElementById('oldPassword').value = "";
    document.getElementById('newPassword').value = "";
    document.getElementById('confirmPassword').value = ""
}

/* ---------------- clean form when modal close ---------- */
$("#form-change-password").on("hidden.bs.modal", function () {
    cleanFormChangePassword()
});

/*  ---------------------- clean form when click btn ------------------------- */
document.querySelector('#clean-form-change-password').addEventListener('click', cleanFormChangePassword);

let validate = (data) => {
    let odlPassword = document.querySelector('#oldPassword').value.trim()
    let confirmPassword = document.querySelector('#confirmPassword').value.trim()
    let password = document.querySelector('#newPassword').value.trim()
    if (data.oldPassword === '') {
        document.querySelector('#oldPassword').focus()
        $("#message").html("Vui lòng nhập mật khẩu cũ!").addClass("alert-danger")
        return false
    }
    if (data.oldPassword.length < 6 || data.oldPassword.length > 12) {
        $("#message").html("Mật khẩu từ 6 đến 12 ký tự!").addClass("alert-warning");
        document.querySelector('#oldPassword').focus()
        return false
    }
    if (data.newPassword === '') {
        document.querySelector('#newPassword').focus()
        $("#message").html("Vui lòng nhập mật khẩu mới!").addClass("alert-danger");
        return false
    }
    if (password === odlPassword) {
        document.querySelector('#newPassword').focus()
        $("#message").html("bạn đã nhập mật khẩu cũ hay thử một mẩu khác!").addClass("alert-warning");
        return false
    }
    let special = data.newPassword.match((/[!#$%^&*_]+/g));
    if (special != null) {
        $("#message").html("Mật khẩu không được chứa ký tực đặc biệt!").addClass("alert-danger");
        document.querySelector('#newPassword').focus();
        return false
    }
    if (data.newPassword.length < 6 || data.newPassword.length > 12) {
        $("#message").html("Mật khẩu từ 6 đến 12 ký tự!").addClass("alert-warning");
        document.querySelector('#newPassword').focus();
        return false
    }
    if (confirmPassword === '') {
        document.querySelector('#confirmPassword').focus()
        $("#message").html("Vui lòng nhập xậc nhận mật khẩu!").addClass("alert-danger");
        return false
    }

    if (password !== confirmPassword) {
        document.querySelector('#newPassword').focus()
        $("#message").html("Xác nhận mật khẩu không đúng!").addClass("alert-danger");
        return false
    }

    return true
}
