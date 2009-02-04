$(document).ready(function() {

    function errh(data) {
        //jQuery.facebox("<h3>"+msg+"</h3>");
        //$.modal('<h3>' + data + '</h3>' );
        alert ( data );
    }

    dwr.engine.setErrorHandler(errh);

    function login(pageData) {
        // display login page in a light box
        var data = 'Your session has expired, please login again!';
        //jQuery.facebox('<h3>' + data + '</h3>' );
        $.modal('<h3>' + data + '</h3>' );
        //alert ("Your session has expired, please login again by refresh!");
    }
    dwr.engine.setTextHtmlHandler(login);
});