$(function() {
    const priorities = [{
        id: "LOW", name: 'Low',
    }, {
        id: "MIDDLE", name: 'Middle',
    }, {
        id: "HIGH", name: 'High',
    }];
    function isNotEmpty(value) {
        return value !== undefined && value !== null && value !== '';
    }
    $("#dataGrid").dxDataGrid({
        dataSource: new DevExpress.data.CustomStore({
            key: "id",
            load: function(loadOptions) {
                const deferred = $.Deferred();
                const args = {};
                [
                    'skip',
                    'take',
                    'requireTotalCount',
                    'requireGroupCount',
                    'sort',
                    'filter',
                    'totalSummary',
                    'group',
                    'groupSummary',
                ].forEach((i) => {
                    if (i in loadOptions && isNotEmpty(loadOptions[i])) {
                        args[i] = JSON.stringify(loadOptions[i]);
                    }
                });
                $.ajax({
                    url: '/api/tasks',
                    dataType: 'json',
                    data: args,
                    success(result) {
                        //TODO: summary, grouping, filtering, sorting: https://js.devexpress.com/Demos/WidgetsGallery/Demo/DataGrid/CustomDataSource/jQuery/Light/
                        deferred.resolve(result, {
                            totalCount: result.length,
                            summary: result.summary,
                            groupCount: result.groupCount,
                        });
                    },
                    error() {
                        deferred.reject('Data Loading Error');
                    },
                    timeout: 5000,
                });

                return deferred.promise();
            },
            byKey: function(key) {
                return $.getJSON("/api/tasks/" + encodeURIComponent(key));
            },
            insert: function(values) {
                return $.ajax({
                    type: "POST",
                    url: "/api/tasks",
                    data: JSON.stringify(values),
                    contentType:"application/json; charset=utf-8",
                    dataType: 'json'
                });
            },
            update: function(key, values) {
                return $.ajax({
                    url: "/api/tasks/" + encodeURIComponent(key),
                    method: "PUT",
                    data: JSON.stringify(values),
                    contentType:"application/json; charset=utf-8"
                });
            },
            remove: function(key) {
                return $.ajax({
                    url: "/api/tasks/" + encodeURIComponent(key),
                    method: "DELETE",
                });
            }
        }),
        editing: {
            mode: 'popup',
            allowUpdating: true,
            allowAdding: true,
            allowDeleting: true,
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
                displayExpr: 'name',
            }
        },{
            dataField: 'allDay',
            dataType: 'boolean'
        }],
        remoteOperations: true
    });
});