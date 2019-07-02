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
    $('#CertificationTableContainer').jtable({
        title: 'Certifications',
        fields: {
            name: {
                title: 'Certification Name',
                key: true, 
                create: true
            }
        },
        actions: {
            createAction: function(postData, jtParams) {
                console.log("creating car:");
                postData = QueryStringToJSON(postData);
                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: '/certifications',
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
                        url: '/certifications',
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
            deleteAction: function (postData, jtParams) {
                console.log("deleting car:");
                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: '/deleteCertification',
                        type: 'POST',
                        contentType: "application/json; charset=utf-8",
                        data: postData.name,
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
    $('#CertificationTableContainer').jtable('load');
});
