'use strict';

angular.module('myApp')
.service('commonService', commonService);

function commonService($q, $log, apiDataService) {

	var service = {};

  service.get = get;
  service.post = post;

  function post(url, data) {
    $log.debug("commonService.post(): " + url, data);
    var deferredOrder = $q.defer(); 

    apiDataService.all(url).customPOST(data)
    .then(function(response) {   
      deferredOrder.resolve(response);
    }, function(error) {
      $log.error(url, error);
      deferredOrder.reject(error);
    });
    return deferredOrder.promise;
  }

  function get(url) {
    $log.debug("commonService.get(): ", url);
    var deferred = $q.defer();

    apiDataService.all(url).customGET()
    .then(function(response) {
      deferred.resolve(response);
    }, function(error) {
      $log.error(url, error);
      deferred.reject(error);
    });

    return deferred.promise;
  }
  
  return service;
}