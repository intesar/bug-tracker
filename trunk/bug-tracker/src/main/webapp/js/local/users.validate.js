$(document).ready(function() {
    // validate signup form on keyup and submit

    var validator = jquery("#signupform").validate({
        rules: {
            firstname:  {
                required: true,
                minlength: 2
            },
            lastname:  {
                required: true,
                minlength: 2
            },
            username: {
                required: true,
                minlength: 2
            },
            email: {
                required: true,
                email: true
            }
        },
        messages: {
            firstname: { 
                required: "Enter your firstname",
                minlength: jQuery.format("Enter at least {0} characters")            
            },
            lastname: {
                required: "Enter your lastname",
                minlength: jQuery.format("Enter at least {0} characters")   
            },
            username: {
                required: "Enter a username",
                minlength: jQuery.format("Enter at least {0} characters"),
                remote: jQuery.format("{0} is already in use")
            },
            email: {
                required: "Please enter a valid email address",
                minlength: "Please enter a valid email address",
                remote: jQuery.format("{0} is already in use")
            }
        },
                
        // specifying a submitHandler prevents the default submit, good for the demo
        //submitHandler: function() {
        //    alert("submitted!");
        //},
        // set this class to error-labels to indicate valid fields
        success: function(label) {
            // set &nbsp; as text for IE			
            label.html("&nbsp;").addClass("checked");
        }
    });
        
    // propose username by combining first- and lastname
    $("#username").focus(function() {	    
        var firstname = $("#firstname").val();
        var lastname = $("#lastname").val();
        if(firstname && lastname && !this.value) {
            this.value = firstname + "." + lastname;
        }
    });
    
    // propose username by combining first- and lastname
    $("#email").focus(function() {	    
        var firstname = $("#firstname").val();
        var lastname = $("#lastname").val();
        if(firstname && lastname && !this.value) {
            this.value = firstname + "." + lastname + "@";
        }
    });

});
    