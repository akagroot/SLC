'use strict';

angular.module('myApp')
.service('standardsService', standardsService);

function standardsService($q, $log, commonService) {

	var service = {};

  service.getStandard = getStandard;
  service.updateStandard = updateStandard;

  var getStandardRoute = "/getStandard/{userId}";
  var updateStandardRoute = "/updateStandard";

  function getStandard(userId) {
    return commonService.get(getStandardRoute.replace("{userId}", userId));
  }

  function updateStandard(model) {
    return commonService.post(updateStandardRoute, model);
  }
  
  return service;
}