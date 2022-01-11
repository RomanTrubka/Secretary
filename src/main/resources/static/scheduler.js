$(function() {
    $("#scheduler").dxScheduler({
        dataSource: tasksDataSource,
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
            form.getEditor('repeat').option('disabled', true);
            var priorityEditor = form.getEditor('priority');
            if (priorityEditor && !priorityEditor.option('value')) {
                priorityEditor.option('value', 'LOW');
            };
        },
        appointmentTooltipTemplate: function (model) {
            return $("<div class='dx-tooltip-appointment-item'>" + model.appointmentData.description + "</div>");
        }
    });
});