$(function() {
	var jsObj = $(" #employeeDefinitionForm #pageValueObject").val();
    fillPage('employeeDefinitionForm');
    $("employeeDefinitionForm #pageValueObject").val(jsObj);
	callTemplateGridOnDemand();
	//setWindowNameTitle("Employee", "Sprint");
	$("#bodyTopButtons").append("<a class = 'cs-tooltip-bottom' href='#' id='NewTopButton'><i class='icon-cs-new cs-ts-16'></i></a>");
	$("#bodyTopButtons").append("<a class = 'cs-tooltip-bottom' href='#' id='SaveTopButton'><i class='icon-cs-save cs-ts-16'></i></a>");

	$("#bodyTopButtons #NewTopButton").attr('title', $("#i18n-do-sprint-new").val());
	$("#bodyTopButtons #SaveTopButton").attr('title', $("#i18n-do-sprint-save").val());

	$("#NewTopButton").on("click", function() {
		location.href = PFM_CONTEXT_PATH + "/core/employeedefinition/launch";
	});
	
	$("#SaveTopButton").on("click", function() {
		saveMethod();
	});
});
$(document).off("click", ".employeedelete").on("click", ".employeedelete", function() {
	deleteEmployee(this.id);
});
function saveMethod() {
	$.ajax({
		type: "post",
		url: PFM_CONTEXT_PATH + "/core/employeedefinition/save",
		data: serializeForm("employeeDefinitionForm"),
		success: function(data) {
			var valObj = data;
			var jsObj = JSON.parse(valObj);
			jsObj.type = "status";
			$(" #employeeDefinitionForm #pageValueObject").val(JSON.stringify(jsObj.valueObject));
			fillPage('employeeDefinitionForm');
			invokeTransactionMessage(jsObj);
			
			var result = eval('(' + data +')');
        	employeeDefinitionList_data = result.content; 
        	i=0;
        	keysToLowerCase(employeeDefinitionList_data); 
        	employeeDefinitionList_dataview.beginUpdate();
        	employeeDefinitionList_dataview.setItems(employeeDefinitionList_data);
        	employeeDefinitionList_dataview.endUpdate();
        	employeeDefinitionList_dataview.refresh();        	
        	employeeDefinitionList_grid.invalidateAllRows();
        	employeeDefinitionList_grid.resizeCanvas();
			setSessionValuesInExistingIds();
        },
		error: function(xhr, status, error) {
			document.getElementById('modalLayer').style.visibility = 'hidden';
			if (xhr.responseText == "Session-out") {
				logOut();
			} else {
				var jsObj = JSON.parse(xhr.responseText);
				invokeTransactionMessage(jsObj);
			}
		}
	});
}
