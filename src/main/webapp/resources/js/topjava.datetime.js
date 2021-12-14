$('#startDate, #endDate').datetimepicker({
    format: "Y-m-d",
    timepicker: false,
});


$('#startTime, #endTime').datetimepicker({
    format: 'H:i',
    datepicker: false,
});


$('#dateTime').datetimepicker({
    format: "Y-m-d H:i",
    gotoCurrent: true,
});

