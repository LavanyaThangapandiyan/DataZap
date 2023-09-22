var employeeDefinitionList_grid;
var employeeDefinitionList_column = [{
	id: "user", name: "Employee Definition", width: "248", formatter: renderCell,
	header: {
		buttons: [
			{
				image: "/appconnect/images/csbase/search-icon-sm2.png", tooltip: "Search", handler: function(e) {
					toggleFilterRow(employeeDefinitionList_grid)
				}
			}
		]
	}
}];
var employeeDefinitionList_option = { "asyncEditorLoading": true, "autoEdit": false, "rowHeight": "60", "enableCellNavigation": true, "enableColumnReorder": false, "explicitInitialization": true, "forceFitColumns": true, "multiSelect": false, "showHeaderRow": "true" };
var employeeDefinitionList_data = [];
var employeeDefinitionList_dataview = new Slick.Data.DataView();
var employeeDefinitionList_container = "employeeDefinitionList_container";
var employeeDefinitionList_columnFilter = "";
var employeeDefinitionListcommandQueue = [];
employeeDefinitionList_dataview.setFilterArgs({ searchString: "" });
var compiled_template = tmpl('<li style="padding:5px 10px;"> <h3 title = "<#=$("<div/>").text(employeeName).html()#>"><#=$("<div/>").text(employeeName).html()#></h3> <p class="cs-bsmar" style="padding-top: 0px;"><span class="cs-graylightc"><a href="#" class="employeedelete" id="<#=employeeId#>" title = "' + $("#i18n-do-pp-button-delete").val() + '"><i class="icon-cs-delete cs-ts-13  cs-lmar-lg cs-blackc"></i></p></li>');
function renderCell(row, cell, value, columnDef, dataContext) {
	return compiled_template(dataContext);
}

$(window).resize(function() {
	setTimeout(function() {
		resizeTemplateGrid();
		employeeDefinitionList_grid.resizeCanvas();
	});
});
function callTemplateGridOnDemand() {
	$.ajax({
		type: "POST",
		url: PFM_CONTEXT_PATH + "/core/employeedefinition/fetchall",
		datatype: "text",
		data: { pageValueObject: $('#pageValueObject').val() },
		success: function(Response) {
			
			var result = eval('(' + Response + ')');
			employeeDefinitionList_data = result.content;
			i = 0;
			keysToLowerCase(employeeDefinitionList_data);
			employeeDefinitionList_grid = new Slick.Grid("#employeeDefinitionList_container", employeeDefinitionList_dataview, employeeDefinitionList_column, employeeDefinitionList_option);
			var employeeDefinitionList_pager = new Slick.Controls.Pager(employeeDefinitionList_dataview, employeeDefinitionList_grid, $("#employeeDefinitionList_pager"));


			employeeDefinitionList_grid.onClick.subscribe(function(e, args) {
				fetchEmployeeDefinitionDetail(e, args, this);
			});

			buildTemplateGrid(employeeDefinitionList_data, employeeDefinitionList_option, employeeDefinitionList_dataview, employeeDefinitionList_column, employeeDefinitionList_grid, employeeDefinitionList_container, employeeDefinitionList_columnFilter, searchByEmployeeName);
			selectEmployeeNameInOutlook({ grid: employeeDefinitionList_grid, dataView: employeeDefinitionList_dataview })
			setTimeout(function() {
				; resizeTemplateGrid();
				employeeDefinitionList_grid.resizeCanvas();
			}, 1200);
		}
	});
} if (!String.prototype.startsWith) {
	Object.defineProperty(String.prototype, 'startsWith', {
		enumerable: false,
		configurable: false,
		writable: false,
		value: function(searchString, position) {
			position = position || 0;
			return this.indexOf(searchString, position) === position;
		}
	});

}
function deleteEmployee(employeeId) {
	$.ajax({
		type: "POST",
		data: {
			pageValueObject: $('#pageValueObject').val(),
			employeeId: employeeId
		},
		dataType: "text",
		url: PFM_CONTEXT_PATH + "/core/employeedefinition/delete",
		success: function(data) 
		{	
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
				
			/*var result = eval('(' + data + ')');
			employeeDefinitionList_data="";
			employeeDefinitionList_data = result.content;
			employeeDefinitionList_dataview = new Slick.Data.DataView();
			i = 0;
			keysToLowerCase(employeeDefinitionList_data);
			employeeDefinitionList_grid = new Slick.Grid("#employeeDefinitionList_container", employeeDefinitionList_dataview, employeeDefinitionList_column, employeeDefinitionList_option);
			var employeeDefinitionList_pager = new Slick.Controls.Pager(employeeDefinitionList_dataview, employeeDefinitionList_grid, $("#employeeDefinitionList_pager"));


			employeeDefinitionList_grid.onClick.subscribe(function(e, args) {
				fetchEmployeeDefinitionDetail(e, args, this);
			});

			buildTemplateGrid(employeeDefinitionList_data, employeeDefinitionList_option, employeeDefinitionList_dataview, employeeDefinitionList_column, employeeDefinitionList_grid, employeeDefinitionList_container, employeeDefinitionList_columnFilter, searchByEmployeeName);
			selectEmployeeNameInOutlook({ grid: employeeDefinitionList_grid, dataView: employeeDefinitionList_dataview })
			setTimeout(function() {
				; resizeTemplateGrid();
				employeeDefinitionList_grid.resizeCanvas();
			}, 1200);*/
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
