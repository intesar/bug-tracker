// this function is executed on load 	
$(document).ready(function() {
    // checkout datatable plugin to understand below code
    oTable = $('#projectTable').dataTable( {               
        "sDom": '<"top"i>rt<"bottom"flp<"clear">',
        "aoData": [null,null,{
            "sClass":"action"
        }]
    } );
    
    /* Global variable for the DataTables object */
    var oTable;	
    /* Global var for counter */
    var giCount = 2;    
    var projectCache = {};
    var viewed = null;
    var _project = null;
    
    function displayProjects(projects) {
        var dArray = new Array();
        // Delete all the rows from table
        oTable.fnClearTable();
        for ( var i = 0 ; i < projects.length; i++ ) {  
            // create object
            var data = [ ""+projects[i].name, ""+projects[i].status, 
            "<img alt='delete project' src='../images/delete.png' id='deleteProject"+projects[i].id +"' class='deleteProject' /><a href='javascript:void(0);' id='deleteProject"+projects[i].id +"' class='deleteProject' >Delete</a>&nbsp;&nbsp;<img id='projectUsers"+projects[i].id +"' class='projectUsers' src='../images/users.png' /><a href='javascript:void(0);' class='projectUsers' id='projectUsers"+projects[i].id +"'>Members</a>"];
            projectCache[projects[i].id] = projects[i];
            dArray[i] = data;                                     
        }                   
        // adds object array to table and displays
        oTable.fnAddData( dArray );
        // required by daatable plugin
        giCount++;
        $('#projectTable').tableHover()
    }
    
    var projectList = function (projects) {
        displayProjects( projects );
    }
    
   
    // executed onload
    AjaxProjectService.getProjectList(projectList);
    
    // executed on "create new project" link is clicked
    $('.createANewProject').click(function() {                
        $('#newProjectContainer').modal();        
        $('#clear').trigger('click');        
    });
   
    // executed on "delete" link is clicked
    $('.deleteProject').livequery('click', function () {                                
        viewed = $(this).attr('id').toString().substring(13);
        var proj = projectCache[viewed];
        if ( confirm("Are you sure you want to delete " + proj.name + "?") ) {
            AjaxProjectService.deleteProject ( viewed, function ( data ) {
                displayProjects (data);
                $.jGrowl( proj.name +" deleted sucessfully!");
                $.jGrowl( "Refreshing project list");
            });
        }
    });
    
      
    // executed on "click" link is clicked
    $('#clear').click(function() {
        $('#name').val("");        
        $('#description').val("");
        $('#name').attr('disabled', false);
        viewed = null;
    });
    // executed on on Save & Close button is clicked, creates and saves a project
    var _p = null;
    $('#saveProject').click(function() {
        var project1 = null;
        if ( viewed == null ) {
            project1 = {
                id:null,
                name:null,
                description:null,
                status:null
            };
            project1.name = $.trim ( $('#name').val() );
            var x = project1.name.substr(0, 1).toUpperCase();
            project1.name = x + project1.name.substr(1);
            $('#name').val(project1.name);
        } else {
            project1 = projectCache[viewed];
        }
        project1.description = $.trim ( $('#description').val() );
        project1.status = $('#status').val();
        if ( project1.name.length < 1 ) {
            alert ( " Project should have a name!");
            return;
        }
        AjaxProjectService.saveProject ( project1, saveProjectCallback);
        _p = project1;
    });
    
    var saveProjectCallback = function ( projects ) {
        $.jGrowl( _p.name +" saved sucessfully!");
        $.jGrowl( "Refreshing project list");
        $('#clear').trigger("click");
        displayProjects(projects);
    }
    // open user in a modal for editing
    $('#projectTable td').livequery( function () { 
        $(this).not('.action').click(function () { 
            viewed = $(this).parent().children()[2].firstChild.id.toString().substring(13);            
            $('#newProjectContainer').modal();
            var project = projectCache[viewed]; 
            $('#name').attr('disabled', true);
            $('#name').val(project.name);
            $('#status').val(project.status);
            $('#description').val(project.description);            
        });
    });
    
     
    // ------------ project users --------------//
    
    // executed on "delete" link is clicked
    $('.projectUsers').livequery('click', function () {                                
        viewed = $(this).attr('id').toString().substring(12);      
        _project = projectCache[viewed];    
        AjaxProjectService.getProjectUsers(viewed, projectUsersList);        
        $('#projectUsersDiv').slideToggle('fast');
        $('#projectTableContainer').slideToggle('fast');
        $('#projectUsersTable').attr("style", "width:800px");
    });
    
    $('.backToProjectList').livequery ('click', function() {
        $('#projectUsersDiv').slideToggle('fast');
        $('#projectTableContainer').slideToggle('fast');
    });
    /* Global variable for the DataTables object */
    var puTable;	
    /* Global var for counter */
    var puCount = 2;  
    puTable = $('#projectUsersTable').dataTable( {               
        "sDom": '<"top"i>rt<"bottom"flp<"clear">'
    });
    
    var projectUsersArray = {};
    var viewedUser = null;
    
    function displayProjectUsers(projectUsers) {
        var dArray = new Array();
        // Delete all the rows from table
        puTable.fnClearTable();
        for ( var i = 0 ; i < projectUsers.users.length; i++ ) {  
            // create object
            var manager = '<b>Member</b>';
            var managerAction = "<a id='projectUserAsManager"+projectUsers.users[i].id + "' class='addUserAsManager' href='javascript:void(0);'>(Make-Manager)</a>";
            if ( projectUsers.users[i].manager == true ) {  
                manager = '<b>Manager</b>';
                managerAction = "<a id='projectUserAsManager" + projectUsers.users[i].id + "' class='removeUserAsManager'  href='javascript:void(0);'>(Remove-As-Manager)</a>";
            }            
            var data = [ projectUsers.projectName, "" + projectUsers.users[i].firstname + " " + projectUsers.users[i].lastname, 
            manager + "  " +  managerAction,
            "<img src='../images/delete.png' id='deleteProjectUser"+projectUsers.users[i].id +"' class='deleteProjectUser'  /><a class='deleteProjectUser' id='deleteProjectUser"+projectUsers.users[i].id +"'>Remove User</a>"];
            dArray[i] = data;         
            projectUsersArray[projectUsers.users[i].id] = projectUsers.users[i];
        }                   
        //projectUsersCache[projectUsers.users[i].id] = projectUsers;
        // adds object array to table and displays
        puTable.fnAddData( dArray );
        // required by daatable plugin
        puCount++;
    }
    
    var projectUsersList = function ( projectUsers ) {
        displayProjectUsers ( projectUsers );
    }
    
    $('.addUserAsManager').livequery('click', function () { 
        viewedUser = $(this).attr('id').toString().substring(20);
        var username = projectUsersArray[viewedUser];
        AjaxProjectService.saveUserToProject (viewed, username.username, true, function(data) {
            $.jGrowl( username.firstname + " " + username.lastname + " promoted as manager sucessfully!");
            $.jGrowl( "Refreshing users list");
            projectUsersList(data);
        });
    });
    
    $('.removeUserAsManager').livequery('click', function () { 
        var viewedUser1 = $(this).attr('id').toString().substring(20);        
        var username1 = projectUsersArray[viewedUser1];
        AjaxProjectService.saveUserToProject (viewed, username1.username, false, function(data) {
            $.jGrowl( username1.firstname + " " + username1.lastname + " demoted as member sucessfully!");
            $.jGrowl( "Refreshing users list");
            projectUsersList(data);
        });
    });
    
    $('.deleteProjectUser').livequery('click', function () { 
        var viewedUser1 = $(this).attr('id').toString().substring(17);
        var username1 = projectUsersArray[viewedUser1];
        AjaxProjectService.deleteUserFromProject(viewed, username1.id, function(data) {
            $.jGrowl( username1.firstname + " " + username1.lastname + " deleted sucessfully!");
            $.jGrowl( "Refreshing users list");
            projectUsersList(data);
        });
    });
    
    $('displayAddUserToProject').livequery('click', function () { 
        $('#projectUsersSaveDiv').modal();
    });
    
    // add projectUsers clicked
    $('.saveProjectUsersLink').click (function() {
        if ( _project != null ) {
            $('#projectName').val(_project.name);
            $('#projectUsersSaveDiv').modal();
        }else {
            alert ("Select project from above list");
        }        
    });
    
    $('#addUserToProjectBtn').livequery('click', function () {         
        var username= $('#projectUsername').val();
        var manager = $('#manager').attr('checked');
        AjaxProjectService.saveUserToProject (viewed, username, manager, function(data) {
            $.jGrowl( username + " added to project sucessfully!");
            $.jGrowl( "Refreshing users list");
            projectUsersList(data);
        });
    });
    
});