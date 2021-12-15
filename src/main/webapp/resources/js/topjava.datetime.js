const startDate = $('#startDate');
const endDate = $('#endDate');
const startTime = $('#startTime');
const endTime = $('#endTime');

//https://stackoverflow.com/a/43936940
startDate.datetimepicker({
    format: "Y-m-d",
    timepicker: false,
    onShow: function (ct) {
        this.setOptions({
            maxDate: endDate.val() ? endDate.val() : false
        })
    }
});

endDate.datetimepicker({
    format: "Y-m-d",
    timepicker: false,
    onShow: function (ct) {
        this.setOptions({
            minDate: startDate.val() ? startDate.val() : false
        })
    }
});

startTime.datetimepicker({
    format: 'H:i',
    datepicker: false,
    onShow: function (ct) {
        this.setOptions({
            maxTime: endTime.val() ? endTime.val() : false
        })
    }
});

endTime.datetimepicker({
    format: 'H:i',
    datepicker: false,
    onShow: function (ct) {
        this.setOptions({
            minTime: startTime.val() ? startTime.val() : false
        })
    }
});


$('#dateTime').datetimepicker({
    format: "Y-m-d H:i",
    gotoCurrent: true,
});

