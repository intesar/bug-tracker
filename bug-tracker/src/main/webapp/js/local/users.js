
// this function is executed on load 	
$(document).ready(function() {    
    oTable = $('#usersTable').dataTable( {
        //"sPaginationType": "full_numbers"  
        "sDom": '<"top"i>rt<"bottom"flp<"clear">',
        "aoData": [{
            "bVisible":false
        },null,null,null,{
            "sClass":"action"
        }]
    });
    
    /* Global variable for the DataTables object */
    var oTable;	
    /* Global var for counter */
    var giCount = 2;    
    var usersCache = {};
    var viewed = null;
    
    var usersList = function ( users ) {        
        displayList( users);
    }
    function displayList(users) {
        var dArray = new Array();
        // Delete all the rows except for the "pattern" row
        oTable.fnClearTable();
        for ( var i = 0 ; i < users.length; i++ ) {  
            var active = "N";
            if ( users[i].enabled ) {
                active = "Y";
            }
            var administrator = "N";
            if ( users[i].administrator ) {
                administrator = "Y";
            }
            var data = [ users[i].id, users[i].firstname + " " + users[i].lastname, active, administrator,
            "<img alt='Delete User' src='../images/delete.png' id='deleteUser" + users[i].id + "' class='deleteUser' />"];
            usersCache[users[i].id] = users[i];
            dArray[i] = data;                                     
        }                                                         
        oTable.fnAddData( dArray );
        giCount++;
        $('#usersTable').tableHover()
    }
    // executed onload
    AjaxUsersService.getUserList(usersList);

    // load div and check enabled to true
    $('.createANewUser').click(function() {    
        $('#clear').trigger('click');
        viewed = null;
        $('#newEditContainer').modal({
            persist: true
        });
        $('#enabled').attr('checked', 'checked');
        $('#email').attr('disabled', false);
    });

    
    // executed on "delete" link is clicked
    $('.deleteUser').livequery('click', function () {                                
        viewed = $(this).attr('id').toString().substring(10); 
        var user = usersCache[viewed]; 
        if ( confirm("Are you sure you want to delete " + user.firstname + " " + user.lastname + "?") ) {
            AjaxUsersService.deleteUser ( viewed, function ( users ) {
                displayList( users);
                $.jGrowl( user.firstname + " " + user.lastname +" deleted sucessfully!");
            });
        }
    });
    
    // executed on "click" link is clicked
    $('#clear').click(function() {
        $('#firstname').val("");        
        $('#lastname').val("");        
        $('#email').val("");        
        $('#enabled').removeAttr('checked');        
        $('#administrator').removeAttr('checked');  
        $('#email').attr('disabled', false);        
        viewed = null;
    });
    
    // open user in a modal for editing
    $('#usersTable td').livequery( function () { 
        $(this).not('.action').click(function () { 
            viewed = $(this).parent().children()[3].firstChild.id.toString().substring(10);
            $('#newEditContainer').modal();
            var user = usersCache[viewed]; 
            $('#email').val(user.username);
            $('#email').attr('disabled', true);
            $('#firstname').val(user.firstname);
            $('#lastname').val(user.lastname);
            if ( user.enabled == true ) {  
                $('#enabled').attr('checked', 'checked');
            }
            else { 
                $('#enabled').removeAttr('checked', '');
            }
            if ( user.administrator == true) {  
                $('#administrator').attr('checked', 'checked');
            }
            else {  
                $('#administrator').removeAttr('checked');
            }
        });
    });
    
    // executed on "create new project" button is clicked
    var _u = null;
    
    $('#saveUser').click(function() {
        var user1 = null;
        if ( viewed == null ) {
            user1 = {
                id:null,
                firstname:null,
                lastname:null,
                password:null,
                username:null,
                email:null,
                enabled:'true', 
                administrator:null
            };
            user1.username = $.trim ( $('#email').val() );
            user1.email = user1.username;                
        } else {
            user1 = usersCache[viewed];   
        }
        user1.firstname = $.trim ( $('#firstname').val() );
        var x = user1.firstname.substr(0, 1).toUpperCase();
        user1.firstname = x + user1.firstname.substr(1);
        $('#firstname').val(user1.firstname);
        user1.lastname = $.trim ( $('#lastname').val() );
        var y = user1.lastname.substr(0, 1).toUpperCase();
        user1.lastname = y + user1.lastname.substr(1);
        $('#lastname').val(user1.lastname);
        user1.enabled = $('#enabled').attr('checked');
        user1.administrator = $('#administrator').attr('checked');
        if ( user1.username.length < 5 || user1.firstname.length < 2 || user1.lastname.length < 2 ) {
            alert ( ' All fields are required! ');
            return;
        }
        AjaxUsersService.saveUser ( user1, saveUserCallback);               
        _u = user1;
    });   
    
    var saveUserCallback = function( users ) {
        $.jGrowl( _u.firstname + " " + _u.lastname +" saved sucessfully!");    
        _u = null;
        $('#clear').trigger('click');
        displayList( users);        
    }
    
} );