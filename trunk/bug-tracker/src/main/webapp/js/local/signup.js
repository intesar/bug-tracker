
$(document).ready(function () {

    $('#personal').click ( function() {
        if ($(this).is(":checked")) {
            $('#organization').attr('disabled', 'disabled');
        } else {
            $('#organization').removeAttr('disabled')
        }
    });

    $('#resetForm').click (function() {
        $('#firstname').val("");
        $('#lastname').val("");
        $('#username').val("");
        $('#password').val("");
        $('#password_confirm').val("");
        $('#organization').val("");
    });

    $('#signup').click(function () {
        var usersDto = {
            id:null,
            firstname:null,
            lastname:null,
            username:null,
            password:null,
            email:null, 
            organization:null,
            city:null,
            country:null
        };
            
        usersDto.firstname= $.trim ( $('#firstname').val() );
        var x = usersDto.firstname.substr(0, 1).toUpperCase();
        usersDto.firstname = x + usersDto.firstname.substr(1);
        $('#firstname').val(usersDto.firstname);
        usersDto.lastname = $.trim ( $('#lastname').val() );
        var y = usersDto.lastname.substr(0, 1).toUpperCase();
        usersDto.lastname = y + usersDto.lastname.substr(1);
        $('#lastname').val(usersDto.lastname);
        usersDto.username = $.trim ( $('#username').val() );
        usersDto.email = usersDto.username;
        usersDto.password = $('#password').val();
        usersDto.organization = $.trim ( $('#organization').val() );
        if ( $('#personal').is(':checked') || $('#organization').val().length < 1 ){
            usersDto.organization = $('#username').val();
        } else {
            usersDto.organization = $('#organization').val();     
        }
        var cf = $('#password_confirm').val();
        if ( usersDto.firstname.length < 2 || usersDto.lastname.length < 2 || usersDto.username.length < 5 ) {
            alert ( "All field required!");
            return;
        }
        if ( cf != null && cf.length > 4 && cf == usersDto.password) {                       
            AjaxUsersService.signUp(usersDto, function(result) {
                alert ( result );
                location.href="../view/login.jsp";
            });   
        } else { 
            alert ("password & confirm password should be same & should have atleast 5 chars!");
        }
    });

});
