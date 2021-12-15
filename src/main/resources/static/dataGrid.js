$(function() {
    $("#dataGrid").dxDataGrid({
        dataSource: new DevExpress.data.CustomStore({
            key: "id",
            load: function() {
                return $.getJSON("/getTasks")
                    //.done(function (data) { debugger; })
                    .fail(function() { throw "Data loading error" });
            }
        }),
        allowColumnReordering: true,
        allowColumnResizing: true,
        columnAutoWidth: true,
        showBorders: true,
        columnChooser: {
            enabled: true,
        },
        columns: [{
            dataField: 'title'
        }, {
            dataField: 'description'
        }, {
            dataField: 'startDate',
            dataType: 'date'
        },{
            dataField: 'endDate',
            dataType: 'date'
        },{
            dataField: 'priority'
        },{
            dataField: 'allDay',
            dataType: 'boolean'
        }]
    });
});