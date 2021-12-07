const mealAjaxUrl = "ui/meals/";

const ctx = {
    ajaxUrl: mealAjaxUrl
};

$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ]
        })
    );
});

const filterForm = $('#filterForm');

function filter() {
    $.ajax({
        url: ctx.ajaxUrl + "filter",
        type: "GET",
        data: filterForm.serialize()
    }).done(function (data) {
        ctx.datatableApi.clear().rows.add(data).draw();
        successNoty("Filtered");
    });
}

function clearFilter() {
    filterForm.find(":input").val("");
    updateTable();
    successNoty("Filter form is cleared");
}