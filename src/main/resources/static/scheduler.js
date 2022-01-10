$(function() {
    const priorities = [{
        id: "LOW",
        text: 'Low',
        color: '#56ca85'
    }, {
        id: "MIDDLE",
        text: 'Middle',
        color: '#ff9747'
    }, {
        id: "HIGH",
        text: 'High',
        color: '#cc5c53'
    }];
    $("#scheduler").dxScheduler({
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
        startDayHour: 8,
        textExpr: "title",
        descriptionExpr: "description",
        allDayExpr: "allDay",
        currentDate: new Date(),
        firstDayOfWeek: 1,
        views: ['day', 'week', 'workWeek', 'month', 'agenda'],
        resources: [{
            fieldExpr: 'priority',
            dataSource: priorities,
            label: 'Priority',
        }],
        onAppointmentFormOpening: function(e) {
            const form = e.form;
            form.getEditor("repeat").option('disabled', true);
        },
        appointmentTooltipTemplate: function (model) {
            return $("<div class='dx-tooltip-appointment-item'>" + model.appointmentData.description + "</div>");
        }
    });
});