$(document).ready(function() {
    displayUsersForReport = function (usersDDList) {
        var options = "";
        for ( var i = 0; i < usersDDList.length; i++ ) {
            var active = "Disabled";
            if ( usersDDList[i].enabled == true ) { active ="";}                
            options += "<option value='"+ usersDDList[i].id+"'>"+usersDDList[i].firstname + " " 
                + usersDDList[i].lastname + " " + active + "</option>";            
        }
        $('#usersDDReports').html(options);
    }
    
    displayProjectDDList = function (projectDDList) {
        var options = "";
        for ( var i = 0; i < projectDDList.length; i++ ) {
            options += "<option value='"+ projectDDList[i].projectId+"'>"+projectDDList[i].projectName + "</option>";
        }
        $('#projectDDReports').html(options);
    }
    
    AjaxUsersService.getUsersListforDropdown(displayUsersForReport);
    AjaxProjectService.getProjectsForDropdown(displayProjectDDList);
    
   $('#refreshUserReport').click(function() {
       var userId = $('#usersDDReports').val();
        AjaxReportService.getUserReports(userId, 3, displayReport1);
   })
   
   $('#refreshProjectReport').click(function() {
       var projectId = $('#projectDDReports').val();
       AjaxReportService.getProjectReports(projectId, 3, displayReport2 );
   })
        
    $('#usersDDReports').livequery('change', function() {        
        var userId = $(this).val();
        AjaxReportService.getUserReports(userId, 3, displayReport1);
    })
    
    $('#projectDDReports').livequery('change', function() {
        AjaxReportService.getProjectReports($(this).val(), 3, displayReport2 );
    })
    
    displayReport1 = function( data) {
        $.jGrowl( "User report loaded successfully!");
        displayReportFunction1(data, "reportsArea");
    }
    
    displayReport2 = function( data) {
        $.jGrowl( "Project report loaded successfully!");
        displayReportFunction2(data, "reportsArea");
    }
    
    function displayReportFunction1 (data, divId) {
        $('#' + divId).empty();
        for ( var i = 0; i < data.length; i++ ) {
            var title = data[i].reportDate;
            var tasksAssigned = data[i].totalAssigned;
            var tasksCompleted = data[i].totalCompleted;
            var hoursAssigned = data[i].estimatedHours;
            var hoursDone = data[i].hoursSpend;
            var myCreatedTask = data[i].totalCreated;
            var api = new jGCharts.Api();             
            jQuery('<img>') 
            .attr('src', api.make({
                title       : title, 
                grid        : true,
                data : [[tasksAssigned, tasksCompleted, hoursAssigned, hoursDone, myCreatedTask]],
                axis_labels : [''],
                legend : ['Tasks Assigned ' + tasksAssigned, 'Tasks Completed '+tasksCompleted,
                    'Hours Assigned '+hoursAssigned,'Hours Done '+hoursDone,'My Created Tasks ' +tasksCompleted]
            })).appendTo("#" + divId);	
        }
    }
    
    function displayReportFunction2 (data, divId) {
        $('#' + divId).empty();
        for ( var i = 0; i < data.length; i++ ) {            
            var title = data[i].title;
            if ( title == null || title.length < 1) { title = data[i].reportDate;}
            var tasksAssigned = data[i].taskCreated;
            var tasksCompleted = data[i].taskFinished;
            var hoursAssigned = data[i].estimatedTime;
            var hoursDone = data[i].timeSpend;            
            var api = new jGCharts.Api();             
            jQuery('<img>') 
            .attr('src', api.make({
                title       : title, 
                grid        : true,
                data : [[tasksAssigned, tasksCompleted, hoursAssigned, hoursDone]],
                axis_labels : [''],
                legend : ['Tasks Created ' +tasksAssigned, 'Tasks Completed '+tasksCompleted,
                    'Hours Assigned '+hoursAssigned,'Hours Done '+hoursDone]
            })).appendTo("#" + divId);	
        }
    }
});