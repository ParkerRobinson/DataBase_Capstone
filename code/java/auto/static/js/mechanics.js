function QueryStringToJSON(str) {
  var pairs = str.split('&');
  var result = {}
  pairs.forEach(function(pair) {
    pair = pair.split('=');
    result[pair[0]] = decodeURIComponent(pair[1] || '');
  });
  return JSON.parse(JSON.stringify(result));
}

certOptions = null
$(document).ready(function () {
    $('#MechanicTableContainer').jtable({
        title: 'Mechanics',
        fields: {
            mechanic_Id: {
                title: 'Mechanic ID',
                key: true
            },
            name: {
                title: 'Name'
            },
            years_experience: {
                title: 'Years Experience'
            },
            hourly_rate: {
                title: 'Hourly Rate (in Dollars)',
                create: false,
                edit: false
            },
            certCount: {
              Title: 'Cert Count',
              type: 'hidden'
            },
            Certs: {
              title: 'Certifications',
              width : '5%',
              sorting: false,
              edit: false,
              create: false,
              display: function (mechanicData) {
                var $img = $('<img src="/resources/list_metro.png" title="Edit Mechanic Certifications"/>')
                $img.click(function () {
                  $('#MechanicTableContainer').jtable('openChildTable',
                    $img.closest('tr'),
                    { //Start of Table
                      title: mechanicData.record.name + ' - Certifications',
                      fields: {
                        mechanic_Id: {
                          title: 'Mechanic Id',
                          key: true,
                          create: false,
                          edit: false,
                          type: 'hidden'
                        },
                        name: {
                          title: 'Certification Name',
                          key: true,
                          create: true,
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
                      }
                    },
                      actions: {
                        createAction: function(postData, jtParams) {
                          console.log("creating MechCert:");
                          postData = QueryStringToJSON(postData);
                          postData.mechanic_Id = mechanicData.record.mechanic_Id
                          return $.Deferred(function ($dfd) {
                            $.ajax({
                              url: '/mechcerts',
                              type: 'POST',
                              contentType: "application/json; charset=utf-8",
                              data: JSON.stringify(postData),
                              success: function (data) {
                                mechanicData.record.certCount += 1;
                                mechanicData.record.hourly_rate += 1;
                                $.ajax({
                                  url: '/updateMechanicHourlyRate',
                                  type: 'POST',
                                  contentType: "application/json; charset=utf-8",
                                  data: JSON.stringify(mechanicData.record),
                                  success: function (data) {
                                      var index = mechanicData.record.mechanic_Id - 1
                                      var rows = $('tr.jtable-data-row')
                                      var row = rows[index]
                                      var cell = row.cells[3]
                                      cell.innerHTML = mechanicData.record.hourly_rate
                                  }
                                });
                                $dfd.resolve({ "Result": "OK", "Record": data });
                              },
                              error: function (xhr, options, error) {
                                console.log("error");
                                console.log(xhr.responseText);
                                $dfd.reject();
                              }
                            });
                          });
                        }, //End of Create
                        listAction: function(postData, jtParams) {
                          console.log('Getting MechCerts')
                          return $.Deferred(function ($dfd) {
                            $.ajax({
                              url: '/mechcerts?mechanicId=' + mechanicData.record.mechanic_Id,
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
                        }, // End of List
                        deleteAction: function (postData, jtParams) {
                          console.log("deleting MechCert:");
                          postData.mechanic_Id = mechanicData.record.mechanic_Id
                          return $.Deferred(function ($dfd) {
                            $.ajax({
                              url: '/deleteMechCert',
                              type: 'POST',
                              contentType: "application/json; charset=utf-8",
                              data: JSON.stringify(postData),
                              success: function (data) {
                                mechanicData.record.certCount -= 1;
                                mechanicData.record.hourly_rate -= 1;
                                $.ajax({
                                  url: '/updateMechanicHourlyRate',
                                  type: 'POST',
                                  contentType: "application/json; charset=utf-8",
                                  data: JSON.stringify(mechanicData.record),
                                  success: function (data) {
                                      var index = mechanicData.record.mechanic_Id - 1
                                      var rows = $('tr.jtable-data-row')
                                      var row = rows[index]
                                      var cell = row.cells[3]
                                      cell.innerHTML = mechanicData.record.hourly_rate
                                  }
                                });
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
                      } //End of Actions
                    }, function (data) { //opened handler
                        data.childTable.jtable('load');
                      });// End of Table
                })
                return $img;
              }
          } //End of Phones
        }, // End of Mechanic Fields
        actions: {
            createAction: function(postData, jtParams) {
                console.log("creating mechanic:");
                postData = QueryStringToJSON(postData);
                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: '/mechanics',
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
                console.log('Getting Mechanics')
                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: '/mechanics',
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
                console.log("updating mechanic:");
                postData = QueryStringToJSON(postData);
                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: '/updateMechanic',
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
                console.log("deleting mechanic:");
                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: '/deleteMechanic',
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
        },
        recordUpdated: function(event, data){
          console.log('Record Updated')
          $('#MechanicTableContainer').jtable('load');
        }
    });
    $('#MechanicTableContainer').jtable('load');
});
