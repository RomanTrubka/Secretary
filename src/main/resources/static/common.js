function isNotEmpty(value) {
    return value !== undefined && value !== null && value !== '';
}
const tasksDataSource = new DevExpress.data.CustomStore({
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
            beforeSend: function(xhr) {
                xhr.setRequestHeader(header, token);
            },
            success(result) {
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
            dataType: 'json',
            beforeSend: function(xhr) {
                xhr.setRequestHeader(header, token);
            }
        });
    },
    update: function(key, values) {
        return $.ajax({
            url: "/api/tasks/" + encodeURIComponent(key),
            method: "PUT",
            data: JSON.stringify(values),
            contentType:"application/json; charset=utf-8",
            beforeSend: function(xhr) {
                xhr.setRequestHeader(header, token);
            }
        });
    },
    remove: function(key) {
        return $.ajax({
            url: "/api/tasks/" + encodeURIComponent(key),
            method: "DELETE",
            beforeSend: function(xhr) {
                xhr.setRequestHeader(header, token);
            }
        });
    }
});
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
const token = $('#_csrf').attr('content');
const header = $('#_csrf_header').attr('content');