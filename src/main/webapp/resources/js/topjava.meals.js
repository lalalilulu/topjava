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
        populateTable(data);
    });
}

function clearFilter() {
    //https://stackoverflow.com/questions/16452699/how-to-reset-a-form-using-jquery-with-reset-method
    filterForm.trigger('reset');
    updateTable();
}