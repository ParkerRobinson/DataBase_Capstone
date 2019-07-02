function QueryStringToJSON(str) {
  var pairs = str.split('&');
  var result = {}
  pairs.forEach(function(pair) {
    pair = pair.split('=');
    result[pair[0]] = decodeURIComponent(pair[1] || '');
  });
  return JSON.parse(JSON.stringify(result));
}

$(document).ready(function () {
    $('#AutoTableContainer').jtable({
        title: 'Cars',
        fields: {
            vin: {
                title: 'VIN',
                key: true, 
                create: true
            },
            year: {
                title: 'Year'
            },
            make: {
                title: 'Make'
            },
            model: {
                title: 'Model'
            },
            customer_Id: {
                title: 'Customer ID'
            },
            name: {
                title: 'Customer Name',
                create: false,
                edit: false
            }
        },
        actions: {
            createAction: function(postData, jtParams) {
                console.log("creating car:");
                postData = QueryStringToJSON(postData);
                console.log(postData);
                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: '/cars',
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
                        url: '/cars',
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
                console.log("updating car:");
                postData = QueryStringToJSON(postData);
                console.log(postData);
                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: '/updateCar',
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
                console.log("deleting car:");
                console.log(postData);
                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: '/deleteCar',
                        type: 'POST',
                        contentType: "application/json; charset=utf-8",
                        data: postData.vin,
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
    $('#AutoTableContainer').jtable('load');
});
