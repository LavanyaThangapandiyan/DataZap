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
var compiled_template = tmpl('<li style="padding:5px 10px;"> <h3 title = "<#=$("<div/>").text(employeeName).html()#>"><#=$("<div/>").text(employeeName).html()#></h3> <p class="cs-bsmar" style="padding-top: 0px;"><span class="cs-graylightc"></p></li>');
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
			var employeeDefinitionList_container_headerButtonsPlugin = new Slick.Plugins.HeaderButtons();
			employeeDefinitionList_grid.registerPlugin(employeeDefinitionList_container_headerButtonsPlugin); $.each(employeeDefinitionList_column, function(index, value) {
				if (typeof value.formatter != "undefined") {
					value.formatter = eval(value.formatter);
				}
				if (typeof value.editor != "undefined") {
					value.editor = eval(value.editor);
				}
			});
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
