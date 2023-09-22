var fetchFlag;
/* This function is used to search for an particular employee name in outlook*/
function searchByEmployeeName(item, args) {
	if (args.searchString.toUpperCase() != "" && item["employeeName"].toUpperCase().indexOf(args.searchString.toUpperCase()) == -1) {
		return false;
	}
	return true;
}
/* This function is used to fetch the particular  details from database*/
function fetchEmployeeDefinitionDetail(e, args, obj) {
	var item = obj.getDataItem(args.row);
	var propertyValueJson;
	var stringifiedPropertyValueJson;
	propertyValueJson = {
		"employeeId": item.employeeId,
	};
	stringifiedPropertyValueJson = encodeURIComponent(JSON.stringify(propertyValueJson));
	$.ajax({
		type: "POST",
		dataType: "text",
		data: { stringifiedPropertyValueJson: stringifiedPropertyValueJson },
		url: PFM_CONTEXT_PATH + "/core/employeedefinition/fetch",
		success: function(data) {
			$(":input").each(function(vals) {
				$(vals).removeClass("error");
			});
			var jsObj = JSON.parse(data);
			document.forms[0].reset();
			$('textarea').val('');
			$("#employeeDefinitionForm #pageValueObject").val(JSON.stringify(jsObj.valueObject));
			fillPage('employeeDefinitionForm');
			$('#employeeName').prop('readonly', true);

		},
		error: function(xhr, status, error) {
			console.log(xhr);
			console.log(status);
			console.log(error);
		}
	});
}

function selectEmployeeNameInOutlook(currentGrid) {
	let employeeName = JSON.parse($("#pageValueObject").val()).employeeName;
	if (employeeName != undefined && employeeName != "") {
		let data = currentGrid.grid.getData().getItems();
		let fetchData;
		for (let i = 0; i < data.length; i++) {
			if (data[i].employeeName == employeeName) {
				fetchData = data[i];
				break;
			}
		}
		if (fetchData != undefined) {
			let rowNum = currentGrid.grid.getData().getRowById(fetchData.id);
			currentGrid.grid.scrollRowToTop(rowNum);
			currentGrid.grid.setActiveCell(rowNum, 0);
			$(".slick-row .active").trigger("click");
		}
	}
}
