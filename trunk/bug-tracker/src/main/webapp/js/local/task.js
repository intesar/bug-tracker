// this function is executed on load 	
$(document).ready(function() {
    $('#abbrevationsId').click(function() { $('#abbrevations').modal();});
    // checkout datatable plugin to understand below code
    oTable = $('#taskTable').dataTable( {             
        "sDom": '<"top"i>rt<"bottom"flp<"clear">',
        "aoData": [null,null,null,null,null,{
            "sWidth":"250px"
        },null]
    } );
    
    /* Global variable for the DataTables object */
    var oTable;	
    /* Global var for counter */
    var giCount = 2;    
    var tasksCache = {};
    var viewed = null;
    var projectDDCache = {};
    var usersDDCache = {};
   
    var taskList = function (tasks) {        
        var dArray = new Array();
        // Delete all the rows from table
        oTable.fnClearTable();
        for ( var i = 0 ; i < tasks.length; i++) {             
            // create object                        
            var project = tasks[i].projectName; if ( project == null) project = "<img src='../images/todo.png' title='Todo' />";            
            var deadline = tasks[i].deadlineFormat; if (deadline == null ) {
                deadline = "<img src='../images/empty-calendar.png' title='None' />"
            } else {
                deadline = deadline.substr(0, 6);
            }
            var title = tasks[i].title ;
            title += ' (' + tasks[i].estimatedHours + ')';
            var assignedUser = tasks[i].assignedToName; 
            if ( assignedUser == 'me') {
                assignedUser = "<img src='../images/user.png' title='Me' />";
            }
            else if ( assignedUser == '') {
                assignedUser = "<img src='../images/add-user.png' title='None' />";
            }
            var status = tasks[i].status;
            if ( status == 'In Progress') {
                status = "<img src='../images/in_progress.png' title='In Progress' />";
            }
            else if ( status == 'Completed') {
                status = "<img src='../images/completed.png' title='Completed' />";
            }
            var priority = tasks[i].priority;        
            if ( priority == "High") { 
                priority = "<img src='../images/high_priority.png' title='High' />";
            }
            var data = [ tasks[i].id , status, project, assignedUser, priority, title, deadline];                                  
            tasksCache[tasks[i].id] = tasks[i];            
            dArray[i] = data;                
        }                   
        // adds object array to table and displays
        oTable.fnAddData( dArray );
        // required by daatable plugin
        giCount++;
        
    }
    // executed onload returns current task
    AjaxTaskService.getCurrentTask("Current Task", taskList);
    
    // priority change 
    function changeTaskPriorityToHigh ( taskId) {
        var task = tasksCache[taskId];
        if ( task.priority != 'High') {
            AjaxTaskService.changeTaskPriority(taskId, 'High', function() {                
                $.jGrowl( "Task # " + taskId + " is marked High Priority!");
            });
        } else {
            $.jGrowl( "Task # " + taskId + " is already has a High Priority!");
        }
    }
    // status change 
    function changeTaskStatus ( taskId, status) {
        var task = tasksCache[taskId];
        if ( task.status != status) {
            AjaxTaskService.changeTaskStatus(taskId, status, function() {                
                $.jGrowl( "Task # " + taskId + " is marked " + status + " !");
            });
        } else {
            $.jGrowl( "Task # " + taskId + " is already " + status + " !");
        }
    }
    
    function deleteTask ( taskId ) {
        var task = tasksCache[taskId];
        if (task.isOwner == true ) {
            AjaxTaskService.deleteTask ( taskId, function(data) {            
                $.jGrowl( "Task # " + taskId + " deleted successfully!");
                taskList(data);
            });
        }else {
            $.jGrowl( 'Only Admin, Project Manager or Owner can delete task');
            alert ('Only Admin, Project Manager or Owner can delete task');
        }
    }
    function assignMe ( taskId ) {                
        AjaxTaskService.assignTaskUser ( taskId, null, function() {            
            $.jGrowl( "Task # " + taskId + " assigned successfully!");
        });
        
    }
    
    // executed on "create new project" link is clicked
    $('.workspaceDiv').livequery ("click", function() {
        $('#taskTableContainer').slideDown('fast');
        $('#detailReports').slideUp('fast');
        $('#newTaskContainer').slideUp('fast');
        $('#reportsDivId').attr('href','javascript:void(0)');
        $('#taskEditDivId').attr('href','javascript:void(0)');
        $('#workspaceDivId').removeAttr('href');
    });
    $('.ReportsDiv').livequery ("click", function() {
        $('#taskTableContainer').slideUp('fast');
        $('#detailReports').slideDown('fast');
        $('#newTaskContainer').slideUp('fast');
        $('#workspaceDivId').attr('href','javascript:void(0)');
        $('#taskEditDivId').attr('href','javascript:void(0)');
        $('#reportsDivId').removeAttr('href');
    });
    
    $('.taskEditDiv').click(function() {    
        $('#taskTableContainer').slideUp('fast');
        $('#detailReports').slideUp('fast');
        $('#newTaskContainer').slideDown('fast');
        $('#reportsDivId').attr('href','javascript:void(0);');
        $('#workspaceDivId').attr('href','javascript:void(0);');
        $('#taskEditDivId').removeAttr('href');
    });
    
    $('#taskTable tbody tr').livequery(function() {
        $(this).parent().parent().tableHover();
    });
    
    $("#deadline").date_input();

    // Show menu when a list item is clicked
    $("#taskTable td").livequery(function() {
        $(this).contextMenu({
            menu: 'myMenu'
        }, function(action, el, pos) {
            var taskId = null;
            if ( action == 'edit') { 
                taskId = $(el).parent().children()[0].firstChild.data; showTaskForEdit(taskId);
            }
            if ( action == 'new') { 
                $('.taskEditDiv').trigger('click');
            }
            else if ( action == 'high') { 
                taskId = $(el).parent().children()[0].firstChild.data; changeTaskPriorityToHigh(taskId);
            }
            else if ( action == 'inprogress') { 
                taskId = $(el).parent().children()[0].firstChild.data; changeTaskStatus(taskId, 'In Progress');
            }
            else if ( action == 'completed') { 
                taskId = $(el).parent().children()[0].firstChild.data; changeTaskStatus(taskId, 'Completed');
            }
            else if ( action == 'assignme') { 
                taskId = $(el).parent().children()[0].firstChild.data; assignMe(taskId);
            }
            else if ( action == 'delete') { 
                taskId = $(el).parent().children()[0].firstChild.data; deleteTask(taskId);
            }
        });
    });
    
    // open user in a modal for editinga
    $('#taskTable td').livequery( function () { 
        $(this).click(function () {             
            var taskId = $(this).parent().children()[0].firstChild.data;            
            showTaskForEdit( taskId );
        });
    });
    
    function showTaskForEdit( taskId) {        
        $('#clear').trigger('click');        
        var task = tasksCache[ taskId ];
        $('#title').val(task.title);        
        $('#estimatedHours').val(task.estimatedHours);
        $('#totalHoursSpend').val(task.spendHours);
        $('#notificationEmails').val(task.notificationEmails);
        $('#description').val(task.description);
        $('#projectDD').val(task.projectName);
        $('#assignToDD').val(task.assignedToId);
        $('#priority').val(task.priority);
        $('#status').val(task.status);
        $('#deadline').val(task.deadlineFormat);
        if ( task.isOwner == false ) {
            $('#title').attr('disabled', true);
            $('#estimatedHours').attr('disabled', true);                
            $('#notificationEmails').attr('disabled', true);
            $('#description').attr('disabled', true);
            $('#projectDD').attr('disabled', true);
            $('#assignToDD').attr('disabled', true);                                
        //$('#deadline').attr('disabled', true);
        }            
        $('#taskTableContainer').slideUp('fast');
        $('#detailReports').slideUp('fast');
        $('#newTaskContainer').slideDown('fast');
        viewed = taskId;
    }

    
    // executed on "clear" button is clicked
    $('#clear').click(function() {
        viewed = null;
        $('#title').val("");        
        $('#estimatedHours').val("");
        $('#hoursSpend').val("");
        $('#totalHoursSpend').val("");
        $('#notificationEmails').val("");
        $('#description').val("");
        $('#title').attr('disabled', false);
        $('#projectDD').val("Todo");
        $('#assignToDD').val("None");
        $('#priority').val("Medium");
        $('#status').val("New");
        $('#deadline').val("");
        $('#estimatedHours').attr('disabled', false);                
        $('#notificationEmails').attr('disabled', false);
        $('#description').attr('disabled', false);
        $('#projectDD').attr('disabled', false);
        $('#assignToDD').attr('disabled', false);                                
    //$('#deadline').attr('disabled', false);
    });
    // executed on "create new task" button is clicked
    $('#saveTask').click(function() {        
        var task1 = null;
        if ( viewed != null ) {
            task1 = tasksCache[viewed];
        }
        if ( task1 == null) {
            task1 = {
                id:null,
                title:null,
                deadline:null,
                deadlineFormat:null,
                priority:null,
                projectName:null,
                assignedToUsername:null,
                estimatedHours:null,
                notificationEmails:null,
                description:null
            };
        }
        task1.title = $.trim( $('#title').val() );
        if ( task1.title.length < 1 ) {
            alert ( " Title cannot be empty! ");
        }
        //task1.deadline = Date.parse($('#deadline').val());
        task1.priority = $('#priority').val();
        var proj = $('#projectDD').val();
        if ( "Todo" != proj ) {
            task1.projectId = proj;
            task1.projectName = projectDDCache[task1.projectId].projectName;
        }  else {
            task1.projectId = null;
            task1.projectName = null;
        }  
        task1.status = $('#status').val();            
        task1.spendHours = $('#hoursSpend').val();        
        task1.estimatedHours = $('#estimatedHours').val();
        task1.deadlineFormat = $('#deadline').val();
        task1.notificationEmails = $.trim ( $('#notificationEmails').val() );
        task1.description = $.trim ( $('#description').val() );
        if ( $('#copyTask').is(":checked") ) {
            var u1 = $('#assignToDD').val();
            var userIds = u1.toString().split(",");
            for ( var i = 0; i < userIds.length; i++ ) {                                
                if ( userIds[i] > 0 ) {
                    task1.assignedToId = userIds[i];                
                    task1.assignedToUsername = usersDDCache[task1.assignedTo];
                    AjaxTaskService.saveTask(task1, function() {
                        $('#clear').trigger("click");
                        $.jGrowl( "Task saved successfully!");
                        viewed = null;
                    });                                             
                }                                                   
            }            
        } else {
            var u = $('#assignToDD').val();
            if ( u != "0") {
                task1.assignedToId = u;
                task1.assignedToUsername = usersDDCache[task1.assignedTo];
            }else {
                task1.assignedToId = null;
                task1.assignedToUsername = null;
            }  
            AjaxTaskService.saveTask(task1, function() {
                $('#clear').trigger("click");
                $.jGrowl( "Task saved successfully!");
            });
            
        }
    //$('#clear').trigger("click");
    });
         
    
    $('#refreshTask').click(function() {
        var selectedStatus = $('#projectStatusDropdown').val();       
        if ( selectedStatus == 'Find by Task ID') {
            var taskId = prompt("Enter Task ID : ", "");
            AjaxTaskService.getCurrentTask(taskId, taskList);
        } else {
            AjaxTaskService.getCurrentTask(selectedStatus, taskList);
        }
    });
    setInterval(function() {
        var selectedStatus = $('#projectStatusDropdown').val();       
        if ( selectedStatus == 'Find by Task ID') {
        } else {
            AjaxTaskService.getCurrentTask(selectedStatus, taskList);
        }
    },300000);
    
    $('#projectStatusDropdown').change(function() {
        var selectedStatus = $(this).val();       
        if ( selectedStatus == 'Find by Task ID') {
            var taskId = prompt("Enter Task ID : ", "");
            AjaxTaskService.getCurrentTask(taskId, taskList);
        } else {
            AjaxTaskService.getCurrentTask(selectedStatus, taskList);
        }
    });
    $('#advanceSearch').click(function() {
        $('#advanceSearchDiv').slideToggle('fast');
    });
    
    function displayProjectDDList(projectDDList) {        
        var options = "<option value='Todo'>Todo</option>";
        for ( var i = 0; i < projectDDList.length; i++ ) {
            options += "<option value='"+ projectDDList[i].projectId+"'>"+projectDDList[i].projectName + "</option>";
            projectDDCache[projectDDList[i].projectId] = projectDDList[i];
        }
        $('#projectDD').html(options);
    }
    $('#projectDD').livequery ('change', function() {
        var projectId =$(this).val();
        var _users = projectDDCache[projectId];
        var users = _users.users;
        var options = "<option value='0'></option>";
        for ( var i = 0; i < users.length; i++ ) {
            options += "<option value='" + users[i].id + "'>" + users[i].firstname + " " + users[i].lastname + "</option>";
        }
        $('#assignToDD').html (options );
    });
    
    projectDDList = function ( projectDDListData ) {
        displayProjectDDList ( projectDDListData );
    }
        
    AjaxProjectService.getProjectsForDropdown(projectDDList);
    
   
    function displayUsersDDList(usersDDList) {
        var options = "<option value='0'></option>";
        for ( var i = 0; i < usersDDList.length; i++ ) {
            options += "<option value='"+ usersDDList[i].id+"'>"+usersDDList[i].firstname + " " + usersDDList[i].lastname + "</option>";
            usersDDCache[usersDDList.id] = usersDDList[i];
        }
        $('#assignToDD').html(options);        
    }
    usersDDList = function ( usersDDListData ) {
        displayUsersDDList ( usersDDListData );
    }
    AjaxUsersService.getActiveUserList(usersDDList);
    
    $('#copyTask').click ( function() {
        if ($(this).is(":checked")) {
            $('#assignToDD').attr('multiple', 'multiple');
            $('#assignToDD').attr('size', '3');
        } else {
            $('#assignToDD').removeAttr('multiple', 'multiple');
            $('#assignToDD').removeAttr('size', '3');
        }
    })
   
    // --------------- reporting -----------------------------------//
    
    var displayReportId = 1; // 1 is user, 2 is project

    function displayUserReportFunction (data, divId) {
        $('#' + divId).empty();
        for ( var i = 0; i < data.length; i++ ) {
            var title = data[i].name + " " + data[i].reportDate;
            var tasksAssigned = data[i].totalAssigned;
            var tasksCompleted = data[i].totalCompleted;
            var hoursAssigned = data[i].estimatedHours;
            var hoursDone = data[i].hoursSpend;
            var myCreatedTask = data[i].totalCreated;
            var api = new jGCharts.Api();             
            jQuery('<img>') 
            .attr('src', api.make({
                title       : title, 
                data : [[tasksAssigned, tasksCompleted, hoursAssigned, hoursDone, myCreatedTask]],
                axis_labels : [''],
                legend : ['Tasks Assigned '+tasksAssigned, 'Tasks Completed '+tasksCompleted,'Hours Assigned '+hoursAssigned,'Hours Done '+hoursDone,'My Created Tasks '+myCreatedTask]
            })).appendTo("#" + divId);	
        }
    }

    function displayProjectReportFunction (data, divId) {
        $('#' + divId).empty();
        for ( var i = 0; i < data.length; i++ ) {
            var title = data[i].projectName + " " + data[i].reportDate;
            var tasksAssigned = data[i].taskCreated;
            var tasksCompleted = data[i].taskFinished;
            var hoursAssigned = data[i].estimatedTime;
            var hoursDone = data[i].timeSpend;
            var api = new jGCharts.Api();
            jQuery('<img>')
            .attr('src', api.make({
                title       : title,
                data : [[tasksAssigned, tasksCompleted, hoursAssigned, hoursDone]],
                axis_labels : [''],
                legend : ['Tasks Assigned '+tasksAssigned, 'Tasks Completed '+tasksCompleted,'Hours Assigned '+hoursAssigned,'Hours Done '+hoursDone]
            })).appendTo("#" + divId);
        }
    }
    
    displayUserReport = function( data) {
        displayUserReportFunction(data, "reportDiv");
    }
    displayProjectReport = function( data) {
        displayProjectReportFunction(data, "reportDiv");
    }
    var loadUserReport = function() {       
        AjaxReportService.getUserReports(-1, 1, displayUserReport);  // get random user report
    }    
    var loadProjectReport = function() {       
        AjaxReportService.getProjectReports(-1, 1, displayProjectReport);  // get radmon project report belonging to user
    }
    var loadUserOrProjectReport = function() {
        if ( displayReportId == 1 ) { //
            loadUserReport();
            displayReportId = 2;
        } else {
            loadProjectReport();
            displayReportId = 1;
        }
    }
    setInterval(function() {
        loadUserOrProjectReport();
    },30000);
    
    // set timer for 30secs  
    function loadNews() {
        AjaxTaskService.getRecentCompletedTask ( 5, displayNews);
    }
    var displayNews = function(tasks) {
        var content = "";
        for ( var i = 0; i < tasks.length; i++ )  {
            tasksCache[tasks[i].id] = tasks[i];
            content += "<p>"
            + "<a id='news" + tasks[i].id + "'  class='newsClass' >" + tasks[i].title + " </a> <br> "
            + tasks[i].assignedToName + ", "
            + tasks[i].priority + ", Completed, # " + tasks[i].id
            + "</p>";
        }
        $('#rightBottomDiv').html( content );
        
    }
    setInterval(function() {
        loadNews();
    },30000);
    
    $('.newsClass').livequery('click', function() {
        var taskId = $(this).attr('id').substr(4);
        showTaskForEdit(taskId);
    })
    


    // ------------------------------------ logged in name -----------------------
    AjaxUsersService.getUserDetails(function (name){    
        $('#welcomeDiv').html("<p>Welcome " + name.firstname + " " +name.lastname + "</p>");
    });

});
    
