<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html><head>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8">
        <title>Grow-Fast</title>
        <!-- table style -->
        <style type="text/css">
            body
            {
                font: .8em "Lucida Grande", Tahoma, Arial, Helvetica, sans-serif;
            }
            
            ol
            {
                margin:0;
                padding: 0 1.5em;
            }
            
            table
            {
                color:#FFF;
                background:#C00;
                border-collapse:collapse;
                width:400px;        
                border:2px solid #900;
            }
            
            thead
            {
                
            }
            
            thead th
            {
                padding:1em 1em .5em;
                border-bottom:solid 1px #FFF;
                font-size:90%;
                text-align:left;
            }
            
            
            
            thead tr
            {
                
            }
            
            td
            {
                padding:.2em 1em;
            }
            
            
            
            tbody tr.odd td
            {
                background:transparent url(tr_bg.png) repeat top left;
            }
            
            tfoot
            {
                
            }
            
            tfoot td
            {
                
                padding-bottom:1.5em;
            }
            
            tfoot tr
            {
                
            }
            
            
            * html tr.odd td
            {
                background:#C00;
                filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(src='tr_bg.png', sizingMethod='scale');
            }
            
            
            #middle
            {
                background-color:#900;
            }
            
            
            
        </style>
        
        
        
        <title>FaceGuard</title>
        <style>
            a:link    {color:black; text-decoration:none; font-size:8.5pt}
            a:hover   {color:black; text-decoration:none;
                font-size:8.5pt}
            a:active  {color:black; text-decoration:none; font-size:8.5pt}
            a:visited {color:black; text-decoration:none; font-size:8.5pt}
        </style>
        <style>
            a:link    {color:black; text-decoration:none; font-size:8.5pt}
            a:hover   {color:black; text-decoration:none;
                font-size:8.5pt}
            a:active  {color:black; text-decoration:none; font-size:8.5pt}
            a:visited {color:black; text-decoration:none; font-size:8.5pt}
            .style16 {color: #FFFFFF}
        </style>
        
        
        <script type="text/javascript" src="../dwr/interface/AjaxUsersService.js"></script>
        <script type="text/javascript" src="../dwr/engine.js"></script>        
        <script type="text/javascript" src="../dwr/util.js"></script>
        
        <script type="text/javascript"> 
            
            function recoverPassword() {
                dwr.util.useLoadingMessage("Please Wait, Loading...");
                var email = null;
                email = dwr.util.getValue("email");
                if ( email != null && email.length > 0 ) {
                    AjaxUsersService.requestPasswordKeySendToEmail(email);
                }
            }
                function resetPassword() {
                    dwr.util.useLoadingMessage("Please Wait, Loading...");
                    var e = dwr.util.getValue("email1");
                    var a = dwr.util.getValue("activationCode");
                    var p = dwr.util.getValue("password");
                    var cp = dwr.util.getValue("confirmPassword");
                    if ( p != null && p.length > 0 && cp != null && p == cp && a != null & a.length > 0 ) {
                        AjaxUsersService.resetPasswordWithKey(e,a,p, function(data) {
                            if ( data == "successful") {
                                location.href="../login.jsp";
                            } else {
                                alert ( data ) ;
                            }
                        });
                    }
                
                
                    }
        </script>
    </head><body>
        <br><br><br>
        <table style="border: 5px solid rgb(153, 0, 0); background: rgb(204, 0, 0) none repeat scroll 0% 0%; color: rgb(255, 255, 255); -moz-background-clip: -moz-initial; -moz-background-origin: -moz-initial; -moz-background-inline-policy: -moz-initial; border-collapse: collapse; width: 370px;" align="center" bgcolor="#cc0000" border="0" width="50%">
            <thead>
                <tr>
                    <th></th>
                    <th>Help me with Password Reset</th>
                </tr>
            </thead>
            <tbody><tr>
                    <td>Username/Email:</td>
                    <td><input name="email" value="" type="text"></td>                
                </tr>
                <tr>
                    <td></td>
                    <td><input value="Send Activation Code to My Email" onclick="recoverPassword();" type="submit"></td>
                </tr>
                
        </tbody></table>
        <br>
        <table style="border: 5px solid rgb(153, 0, 0); background: rgb(204, 0, 0) none repeat scroll 0% 0%; color: rgb(255, 255, 255); -moz-background-clip: -moz-initial; -moz-background-origin: -moz-initial; -moz-background-inline-policy: -moz-initial; border-collapse: collapse; width: 370px;" align="center" bgcolor="#cc0000" border="0" width="50%">
            <thead>
                <tr>
                    <th></th>
                    <th>Have Activation Code</th>
                </tr>
            </thead>
            <tbody><tr>
                    <td>Username/Email:*</td>
                    <td><input name="email1" value="" type="text"></td>                
                </tr>
                <tr>
                    <td>Activation Code:*</td>
                    <td><input name="activationCode" value="" type="text"></td>                
                </tr>
                <tr>
                    <td>Password:*</td>
                    <td><input name="password" value="" type="password"></td>                
                </tr>
                <tr>
                    <td>Confirm Password:*</td>
                    <td><input name="confirmPassword" value="" type="password"></td>                
                </tr>
                <tr>
                    <td></td>
                    <td><input value="Reset My Password" onclick="resetPassword();" type="submit"></td>               
                </tr>
                <br><br>
                <tr>
                    <td>
                        <a href="../view/login.jsp">Go to Login Page</a>
                    </td>
                </tr>
        </tbody></table>
    </body></html>