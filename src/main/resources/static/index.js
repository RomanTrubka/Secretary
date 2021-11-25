$(function() {
    $("#scheduler").dxScheduler({
        dataSource: "http://localhost:8080/getTasks",
        textExpr: "title",
        allDayExpr: "dayLong",
        recurrenceRuleExpr: "recurrence",
        currentDate: new Date()
    });
});