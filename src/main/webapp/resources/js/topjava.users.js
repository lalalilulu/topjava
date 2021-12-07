const userAjaxUrl = "admin/users/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: userAjaxUrl
};

// $(document).ready(function () {
$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "name"
                },
                {
                    "data": "email"
                },
                {
                    "data": "roles"
                },
                {
                    "data": "enabled"
                },
                {
                    "data": "registered"
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
                    "asc"
                ]
            ]
        })
    );
});

$(function () {
    $(".checkbox").change(function () {
        changeEnabled($(this).closest('tr'), $(this).prop('checked'));
    });
});

function changeEnabled(row, enabled) {
    $.ajax({
        url: ctx.ajaxUrl + row.attr("id"),
        type: "POST",
        data: "enabled=" + enabled
    }).done(function () {
        row.toggleClass('disabled');
        successNoty(row.children()[0].textContent + " is " + (enabled ? "enabled" : "disabled"));
    });
}