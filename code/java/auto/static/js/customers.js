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
    $('#CustomerTableContainer').jtable({
        title: 'Customers',
        fields: {
            customer_Id: {
                title: 'Customer ID',
                key: true
            },
            name: {
                title: 'Name'
            },
            address: {
                title: 'Address'
            },
            Phones: {
              title: '',
              width : '5%',
              sorting: false,
              edit: false,
              create: false,
              display: function (customerData) {
                var $img = $('<img src="/resources/phone_metro.png" title="Edit phone numbers" />')
                $img.click(function () {
                  $('#CustomerTableContainer').jtable('openChildTable',
                    $img.closest('tr'),
                    { //Start of Table
                      title: customerData.record.name + ' - Phone Numbers',
                      fields: {
                        phone_Number: {
                          title: 'Phone Number',
                          key: true,
                          create: true,
                          edit: false
                        },
                        customer_Id: {
                          create: false,
                          edit: false,
                          type: 'hidden'
                        }
                      },
                      actions: {
                        createAction: function(postData, jtParams) {
                          console.log("creating user:");
                          postData = QueryStringToJSON(postData);
                          postData.customer_Id = customerData.record.customer_Id
                          return $.Deferred(function ($dfd) {
                            $.ajax({
                              url: '/phoneNumbers',
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
                        }, //End of Create
                        listAction: function(postData, jtParams) {
                          return $.Deferred(function ($dfd) {
                            $.ajax({
                              url: '/phoneNumbers?customerId=' + customerData.record.customer_Id,
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
                          console.log("deleting customer:");
                          postData.customer_Id = customerData.record.customer_Id
                          return $.Deferred(function ($dfd) {
                            $.ajax({
                              url: '/deletePhoneNumber',
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
                      } //End of Actions
                    }, function (data) { //opened handler
                        data.childTable.jtable('load');
                      });// End of Table
                })
                return $img;
              }
          } //End of Phones
        }, // End of Fields
        actions: {
            createAction: function(postData, jtParams) {
                console.log("creating user:");
                postData = QueryStringToJSON(postData);
                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: '/customers',
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
                console.log('Getting Customers')
                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: '/customers',
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
                console.log("updating customer:");
                postData = QueryStringToJSON(postData);
                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: '/updateCustomer',
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
            }/*,
            deleteAction: function (postData, jtParams) {
                console.log("deleting customer:");
                console.log(postData);
                return $.Deferred(function ($dfd) {
                    $.ajax({
                        url: '/deleteCustomer',
                        type: 'POST',
                        contentType: "application/json; charset=utf-8",
                        data: postData.customer_Id,
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
            }*/
        }
    });
    $('#CustomerTableContainer').jtable('load');
});
