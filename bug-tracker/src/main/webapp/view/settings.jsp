
<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>Grow-Fast - Settings</title>
        <jsp:include page="include.jsp" />
        <script type="text/javascript" src="../dwr/interface/AjaxUsersService.js"></script>
        <script type="text/javascript">
            $(document).ready(function() {
                reply = function( data) {
                    alert (data);
                }
                
                $('#changePassword').click(function (){                    
                    var oldPassword = $('#currentPassword').val();
                    var newPassword = $('#newPassword').val();
                    var confirmPassword = $('#confirmPassword').val();                    
                    if ( newPassword == confirmPassword) {
                        AjaxUsersService.changeMyPassword (oldPassword, newPassword, reply);
                    } else {
                        alert ( "new and confirm password should be same!");
                    }
        
                });
                
            });
        </script>
    </head>
    <body  id="dt_example" class="example_alt_pagination">
        <jsp:include page="header_3.jsp" />
        
        
        <div style="position:absolute; left:5%; top:30%; bottom:5%; width:70%; height:90%; ">
            
            <div id="newUserContainer"  align="center">
                <a><h3> Change Password</h3></a>
                <div id="container">                    
                    <table>                
                        <tr>
                            <td>Current password<font color="RED">*</font><br>
                            <input id="currentPassword" maxlength="40" type="password"></td>
                            <td>New password<font color="RED">*</font><br>
                            <input id="newPassword"  maxlength="40" type="password"></td>
                            <td>Confirm new password<font color="RED">*</font><br>
                            <input id="confirmPassword" maxlength="40" type="password"></td>                            
                            <td><br><input type="button" value="Change Password" id="changePassword"/> </td>                            
                        </tr>                         
                    </table>    
                </div>
            </div>
        </div>
    </body>
</html>