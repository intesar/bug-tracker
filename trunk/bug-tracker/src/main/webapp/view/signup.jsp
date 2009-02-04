
<html>
    <head>
        <script type="text/javascript" src="../js/lib/jquery-1.2.6.min.js" ></script>
        <script type='text/javascript' src='../dwr/interface/AjaxUsersService.js'></script>
        <script type='text/javascript' src='../dwr/engine.js'></script>
        <script type='text/javascript' src='../js/local/signup.js'></script>
    </head>
    <body>


        <div style="position:absolute; left:45%; top:10%; bottom:5%; width:70%; height:90%;">
            <h3>Sign Up for free new Account</h3>
            <table>
                <tbody>
                    <tr>
                        <td>First Name<font color="RED">*</font><br>
                        <input class="" id="firstname" name="firstname" value="" maxlength="40" type="text" title="firstname"></td>
                        <td>Last Name<font color="RED">*</font><br>
                        <input class="" id="lastname" name="lastname" value="" maxlength="100" type="text" title="lastname"></td>
                    </tr>
                    <tr>
                        <td colspan="2">Email<font color="RED">*</font><br>
                        <input class="" id="username" name="username" value="" maxlength="50" type="text" title="email is use for login"></td>
                    </tr>
                    <tr>
                        <td>Password<font color="RED">*</font><br>
                        <input id="password" name="password" maxlength="50" value="" type="password"></td>
                        <td>Confirm Password<font color="RED">*</font><br>
                        <input class="" id="password_confirm" name="password_confirm" maxlength="50" value="" type="password"></td>
                    </tr>
                    <tr>
                        <td>Company Name<br>
                        <input id="organization" name="organization" value="" maxlength="150" type="text" title="enterprise accounts have many more features compare to personal accounts"></td>
                        <td>Personal <br>
                        <input type="checkbox" id="personal" title="check this if you are signing by yourself"></td>
                    </tr>
                    <tr><td></td><td></td></tr>
                    <tr><td></td><td></td></tr>
                    <br><br>
                    <tr>
                        <td></td>
                        <td> <input id="resetForm" name="resetForm" value="Reset" type="button" title="clears all fields">
                        
                            <input id="signup" name="signup" value="Signup" type="button" title="saves information and creates you virtual account">
                        </td>
                    </tr>
                </tbody>
            </table>
            
        </div>
    </body>
</html>