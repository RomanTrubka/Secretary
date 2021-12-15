$(function() {
    $("#scheduler").dxScheduler({
        // dataSource: new DevExpress.data.CustomStore({
        //     key: "id",
        //     load: function() {
        //         return $.getJSON("/getTasks")
        //             //.done(function (data) { debugger; })
        //             .fail(function() { throw "Data loading error" });
        //     }
        // }),
        textExpr: "title",
        allDayExpr: "allDay",
        recurrenceRuleExpr: "recurrence",
        currentDate: new Date()
    });
});