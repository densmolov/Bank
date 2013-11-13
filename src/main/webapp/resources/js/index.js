var errors = [
    "Enter your username !",	//1
    "Enter your password !",	//2
];


$(function () {
    //Login error
    var req = new XMLHttpRequest();
    req.open('GET', document.location, false);
    req.send(null);
    str = req.getResponseHeader("message");
    if(str!=undefined && str.trim()!="") {
    toastr.error(str);
    //alert(str);
    }
});

$(document).on("click", ".btn-primary", function(e) {
    e.preventDefault();
    var username_length=$("#username").val().length;
    var password_length=$("#password").val().length;
    if(username_length==0) {
        toastr.error(errors[1]);
    } else if(password_length==0) {
        toastr.error(errors[2]);
    } else {
        $("#loginform").submit();
    }
});
