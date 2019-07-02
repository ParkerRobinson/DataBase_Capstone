function QueryStringToJSON(str) {
  var pairs = str.split('&');
  var result = {}
  pairs.forEach(function(pair) {
    pair = pair.split('=');
    result[pair[0]] = decodeURIComponent(pair[1] || '');
  });
  return JSON.parse(JSON.stringify(result));
}

function formatIndividualDate(date) {
    var year = date.getFullYear()
    var monthInt = date.getMonth() + 1
    var month = (monthInt / 10 >= 1) ? ('' + monthInt) : ('0' + monthInt)
    var dateInt = date.getDate() + 1
    var date = (dateInt / 10 >= 1) ? ('' + dateInt) : ('0' + dateInt)
    return year + '-' + month + '-' + date
}

function formatDates(listData) {
    var data = listData
    for (var i = 0; i < data.length; i++) {
        var tempDate = new Date(data[i].date)
        data[i].date = formatIndividualDate(tempDate)
    }
    return data
}

var vinOptions = null
var descOptions = null
var mechOptions = null

$(document).ready(function () {
    $('#RecordTableContainer').jtable({
        title: 'Repair Records',
        fields: {
            record_Id: {
                title: 'Record Id',
                key: true
            },
            date: {
                title: 'Date (yyyy-mm-dd)'
            },
            description: {
                title: 'Repair Description',
                options: function() {
                    if (descOptions) {
                        return descOptions
                    }
                    var options = []
                    $.ajax({
                        url: '/descNames',
                        type: 'GET',
                        async: false,
                        success: function (data) {
                            options = data;
                        }
                    });
                    return descOptions = options
                }
            },
            mechanicId: {
                title: 'Mechanic Id',
                list: false,
                options: function() {
                    if (mechOptions) {
                        return mechOptions
                    }
                    var options = []
                    $.ajax({
                        url: '/mechanics',
                        type: 'GET',
                        async: false,
                        success: function (data) {
                            data.forEach( function(mech) {
                                var temp = {}
                                temp.Value = mech.mechanic_Id
                                temp.DisplayText = mech.mechanic_Id + ' - ' + mech.name
                                options.push(temp)
                            })
                        }
                    });
                    return mechOptions = options
                }
            },
            name: {
                title: 'Mechanic Name',
                create: false,
                edit: false,
                display: function(data) {
                    return data.record.mechanic.name
                }
            },
            vin: {
                title: 'VIN',
                options: function() {
                    if (vinOptions) {
                        return vinOptions
                    }
                    var options = []
                    $.ajax({
                        url: '/vins',
                        type: 'GET',
                        async: false,
                        success: function (data) {
                            options = data;
                        }
                    });
                    return vinOptions = options
                }
            },
            totalCost: {
                title: 'Cost of Repair(Dollars)',
                edit: false,
                create: false,
                display: function(data) {
                    return data.record.totalCost.toFixed(2)
                }
            }
        },
        actions: {
            createAction: function(postData, jtParams) {
                console.log("creating desc:");
                postData = QueryStringToJSON(postData);
                postData.repairDescription = {description: postData.description}
                postData.mechanic = {mechanic_Id: postData.mechanicId}
                postData.car = {vin: postData.vin}
                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: '/repairRecords',
                        type: 'POST',
                        contentType: "application/json; charset=utf-8",
                        data: JSON.stringify(postData),
                        success: function (data) {
                            var tempDate = new Date(data.date)
                            data.date = formatIndividualDate(tempDate)
                            $dfd.resolve({ "Result": "OK", "Record": data });
                        },
                        error: function (xhr, options, error) {
                            console.log("error");
                            console.log(xhr.responseText);
                            $dfd.reject();
                        }
                    });
                });
            },
            listAction: function (postData, jtParams) {
                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: '/repairRecords',
                        type: 'GET',
                        success: function (data) {
                            console.log('Listing Records')
                            data = formatDates(data)
                            console.log(data)
                            $dfd.resolve({ "Result": "OK", "Records": data, "TotalRecordCount": data.length });
                        },
                        error: function (xhr, options, error) {
                            console.log("error");
                            console.log(xhr.responseText);
                            console.log(error)
                            $dfd.reject();
                        }
                    });
                });
            },
            updateAction: function(postData, jtParams) {
                console.log("updating desc:");
                postData = QueryStringToJSON(postData);
                postData.repairDescription = {description: postData.description}
                postData.mechanic = {mechanic_Id: postData.mechanicId}
                postData.car = {vin: postData.vin}
                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: '/updateRepairRecord',
                        type: 'POST',
                        contentType: "application/json; charset=utf-8",
                        data: JSON.stringify(postData),
                        success: function (data) {
                            var tempDate = new Date(data.date)
                            data.date = formatIndividualDate(tempDate)
                            $dfd.resolve({ "Result": "OK", "Record": data });
                        },
                        error: function (xhr, options, error) {
                            console.log("error");
                            console.log(xhr.responseText);
                            $dfd.reject();
                        }
                    });
                });
            },
            deleteAction: function (postData, jtParams) {
                console.log("deleting car:");
                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: '/deleteRepairRecord',
                        type: 'POST',
                        contentType: "application/json; charset=utf-8",
                        data: JSON.stringify(postData),
                        success: function (data) {
                            $dfd.resolve({ "Result": "OK", "Record": data });
                        },
                        error: function (xhr, options, error) {
                            console.log("error");
                            console.log(xhr.responseText);
                            $dfd.reject();
                        }
                    });
                });
            }
        }
    });
    $('#RecordTableContainer').jtable('load');
});
