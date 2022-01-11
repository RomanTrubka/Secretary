$(function() {
    $("#dataGrid").dxDataGrid({
        dataSource: tasksDataSource,
        editing: {
            mode: 'popup',
            allowUpdating: true,
            allowAdding: true,
            allowDeleting: true,
        },
        onInitNewRow: function (e) {
            e.data.allDay = false;
            e.data.priority = "LOW";
        },
        onRowUpdating: function(e) {
            for (var property in e.oldData) {
                if (!e.newData.hasOwnProperty(property)) {
                    e.newData[property] = e.oldData[property];
                }
            }
        },
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
            dataType: 'datetime'
        },{
            dataField: 'endDate',
            dataType: 'datetime'
        },{
            dataField: 'priority',
            lookup: {
                dataSource: priorities,
                valueExpr: 'id',
                displayExpr: 'text',
            }
        },{
            dataField: 'allDay',
            dataType: 'boolean'
        }],
        remoteOperations: true
    });
});