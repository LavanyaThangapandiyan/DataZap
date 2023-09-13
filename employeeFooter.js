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
                         		},
		error: function() {
		}
	});
}
