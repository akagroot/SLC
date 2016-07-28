'use strict';

angular.module('myApp.securityService')
.service('securityService', securityService);

function securityService($q, $log, apiDataService) {

	var service = {};

  service.getInventoryItems = getInventoryItems;

  var greeting = 'greeting/create';

  function getInventoryItems(){
    var deferred = $q.defer();
    var d=new Date();
    var timestamp = '?' + d.getTime();
    var uri = cancelInventoryItemRoute;

    apiDataService.all(getRegisteredUASRoute).customGET()

    .then(function(response) {
        deferred.resolve(response);
    }, function(error) {
        deferred.reject(error);
    });

    return deferred.promise;
  }
  
  return service;
}