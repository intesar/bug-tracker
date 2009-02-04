<%-- 
    Document   : users
    Created on : Nov 27, 2008, 4:50:06 PM
    Author     : intesar
--%>

<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>        
        <title>Grow-Fast - Users</title>
        <jsp:include page="include.jsp" />
        <script type="text/javascript" src="../dwr/interface/AjaxUsersService.js"></script>
        <script type="text/javascript" src="../js/local/users.js"></script>
        
    </head>
    <body id="dt_example" class="example_alt_pagination" style="">
        <jsp:include page="header_1.jsp" />
        <jsp:include page="banner.jsp" /> 
        <br>   
        <div id="leftDiv">
            <div id="userTableContainer"  align="center"> 
                <div id="container">                    
                    <div align="right">
                        <img alt="Add" src="../images/add.gif" class="createANewUser" title="Add new user to enterprise"></img><a id="createANewUser" class="createANewUser" href="javascript:void(0)"  title="Add new user to enterprise">New user</a>                    
                    </div>
                    <table id="usersTable" cellpadding="0" cellspacing="0" border="0" class="display" align="center">                        
                        <thead class="">
                            <tr class="">
                                <th>ID</th>
                                <th>Name</th>                                                            
                                <th>Active</th>
                                <th>Administrator</th>
                                <th>Edit</th>                                
                            </tr>
                        </thead>                        
                        <tbody>                       
                            <!-- required by plugin-->
                            <tr><td></td><td></td><td></td><td></td><td></td></tr>
                        </tbody>                        
                    </table>    
                </div>
            </div>
            
            <div id="newEditContainer" style="display:none" align="center">
                <div id="container" align="center" >
                    <!--<a id="backToUserList"><font size="1"><< </font>Back to user list</a><br><br>-->
                    <table>                
                        <tr>
                            <td><b>Firstname<font color="RED">*</font></b><br>
                            <input id="firstname" name="firstname" value="" maxlength="40" type="text" title="firstname"></td>                                
                            <td><b>Lastname<font color="RED">*</font></b><br>
                            <input id="lastname" name="lastname" value="" maxlength="40" type="text" title="lastname"></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td><br></td>
                        </tr>
                        <tr>
                            <td><b>Email<font color="RED">*</font></b><small>(email is username)</small><br>
                            <input id="email" name="email" value="" maxlength="40" type="text" title="use this email as username to login"></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td><br></td>
                        </tr>
                        <tr>
                            <td>  <b>Enabled</b>
                                <br><input type="checkbox" id="enabled" value="ON" title="if select user can login or consider this as active switch"/>
                            <small>(User cannot login if disabled)</small></td>
                            <td>  <b>Admin</b>
                                <br><input type="checkbox" id="administrator" value="ON" title="allows user to administrate this account"/>
                            <small>(manages all users and settings)</small></td>                            
                        </tr>           
                        <tr>
                            <td></td>
                            <td><br></td>
                        </tr>
                        <tr>
                            <td><input type="button" value="Reset" id="clear" title="clears all fields"/>
                            <input type="button" value="Save" class='' id="saveUser" title="saves user to database"/></td>                            
                            <td></td>
                        </tr>                         
                    </table>    
                </div>
            </div>
        </div>
        <!--<div id="rightDiv"></div>-->
    </body>
</html>

