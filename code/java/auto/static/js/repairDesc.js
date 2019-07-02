function QueryStringToJSON(str) {
  var pairs = str.split('&');
  var result = {}
  pairs.forEach(function(pair) {
    pair = pair.split('=');
    result[pair[0]] = decodeURIComponent(pair[1] || '');
  });
  return JSON.parse(JSON.stringify(result));
}

/**
 * Format dates into strings of the form yyyy-mm-dd
 */
function formatIndividualDate(date) {
    var year = date.getFullYear()
    var monthInt = date.getMonth() + 1
    var month = (monthInt / 10 >= 1) ? ('' + monthInt) : ('0' + monthInt)
    var dateInt = date.getDate() + 1
    var date = (dateInt / 10 >= 1) ? ('' + dateInt) : ('0' + dateInt)
    return year + '-' + month + '-' + date
}

/**
 * Get data for the vin dropdown
 */
function loadDropdown() {
    var vinOptions = []
    $.ajax({
        url: '/vins',
        type: 'GET',
        async: false,
        success: function (data) {
            vinOptions = data;
        }
    });
    var vinSelect = $('#vinSelect')
    vinOptions.forEach(function(vin){
        vinSelect.append(
            $('<option></option>').val(vin).html(vin)
        )
    })
}

/**
 * Get a mechanic for a repair. The mechanic must have the required certification.
 * The mechanic is then selected by the one with the lowest work load and then the
 * highest pay.
 */
function getMechanic(record) {
    var mechanics = []
    var certName = record.name ? record.name : ""
    $.ajax({
        url: '/mechanicsByCertification?cert=' + certName,
        type: 'GET',
        async: false,
        success: function (data) {
            mechanics = data
        }
    });
    var mechanic = null
    var minHours = null
    mechanics.forEach( function(mech) {
        var hours = null
        $.ajax({
            url: '/mechanicHours?mechanicId=' + mech.mechanic_Id,
            type: 'GET',
            async: false,
            success: function (data) {
                hours = data
                if (mechanic === null || hours < minHours) {
                    minHours = hours
                    mechanic = mech
                } else if (hours === minHours) {
                    if (mech.hourly_rate > mechanic.hourly_rate) {
                        minHours = hours
                        mechanic = mech
                    }
                }
            }
        });
    })
    return mechanic
}

var selectedRepairs = []
var certOptions = null

$(document).ready(function () {
    loadDropdown()
    $('#DescTableContainer').jtable({
        title: 'Repair Descriptions',
        selecting: true,
        multiselect: true,
        selectingCheckboxes: true,
        fields: {
            description: {
                title: 'Description',
                key: true, 
                create: true
            },
            hours_needed: {
                title: 'Hours Needed'
            },
            name: {
                title: 'Certification Needed',
                options: function() {
                    if (certOptions) {
                        return certOptions
                    }
                    var options = []
                    $.ajax({
                        url: '/certNames',
                        type: 'GET',
                        async: false,
                        success: function (data) {
                            options = data;
                        }
                    });
                    return certOptions = options
                }

            },
            parts_cost: {
                title: 'Cost of Parts (in Dollars)',
                display: function(data) {
                    return data.record.parts_cost.toFixed(2)
                }
            }
        },
        actions: {
            createAction: function(postData, jtParams) {
                console.log("creating desc:");
                postData = QueryStringToJSON(postData);
                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: '/repairDescriptions',
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
            },
            listAction: function (postData, jtParams) {
                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: '/repairDescriptions',
                        type: 'GET',
                        success: function (data) {
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
                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: '/updateRepairDescription',
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
            },
            deleteAction: function (postData, jtParams) {
                console.log("deleting Repair description:");
                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: '/deleteRepairDescription',
                        type: 'POST',
                        contentType: "application/json; charset=utf-8",
                        data: postData.description,
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
        },
        selectionChanged: function () {
            //Get all selected rows
            var $selectedRows = $('#DescTableContainer').jtable('selectedRows');

            $('#SelectedRowList').empty();
            $('#EstimatedCost').empty();
            selectedRepairs = []
            var overallCost = 0
            if ($selectedRows.length > 0) {
                //Show selected rows
                $selectedRows.each(function () {
                    // Form record
                    var record = $(this).data('record');
                    var mechanic = getMechanic(record)
                    var cert = record.name ? record.name : 'None'
                    var cost = record.parts_cost.toFixed(2)
                    var laborCost = record.hours_needed * mechanic.hourly_rate * 1.5
                    var totalCost = record.parts_cost + laborCost
                    overallCost += totalCost
                    // Display record information
                    $('#SelectedRowList').append(
                        '<b>Description</b>: ' + record.description +
                        '<br /><b>Hours Needed</b>: ' + record.hours_needed +
                        '<br /><b>Certification Needed</b>: ' + cert +
                        '<br /><b>Mechanic</b>: ' + mechanic.mechanic_Id + ' - ' + mechanic.name +
                        '<br /><b>Cost of Parts</b>: $' + cost + 
                        '<br /><b>Cost of Labor</b>: $' + laborCost.toFixed(2) +
                        '<br /><b>Total Cost</b>: $' + totalCost.toFixed(2) +'<br /><br />'
                        );
                    record.mechanic = mechanic
                    selectedRepairs.push(record)
                });
                $('#EstimatedCost').append('$' + overallCost.toFixed(2))
            } else {
                //No rows selected
                $('#SelectedRowList').append('No Repairs Selected!');
                $('#EstimatedCost').append('No Estimate Yet!');
            }
        }
    });
    $('#DescTableContainer').jtable('load');

    /**
     * Get selected descriptions and create RepairRecords and insert them into the database
     */
    $('#EstimateButton').button().click(function () {
        selectedRepairs.forEach(function (repair){
            var record = {}
            record.repairDescription = {}
            record.mechanic = {}
            record.car = {}
            var todayDate = new Date()
            record.date = formatIndividualDate(todayDate)
            record.mechanic.mechanic_Id = repair.mechanic.mechanic_Id
            record.name = repair.name ? repair.name : ""
            record.repairDescription.description = repair.description
            var vinSelect = $('#vinSelect')
            var selection = vinSelect.children("option:selected").val()
            record.car.vin = selection
            $.ajax({
                url: '/repairRecords',
                type: 'POST',
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify(record),
                success: function (data) {
                    //$dfd.resolve({ "Result": "OK", "Record": data });
                    console.log('Success!')
                },
                error: function (xhr, options, error) {
                    console.log("error");
                    console.log(xhr.responseText);
                    //$dfd.reject();
                    alert('Repair Record Creation Failed!')
                }
            });
        })

    })
});
