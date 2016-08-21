'use strict';

angular.module('myApp')
.service('ratioProfileService', ratioProfileService);

function ratioProfileService($q, $log, commonService) {

	var service = {};

	service.getRatioProfiles = getRatioProfiles;
	service.getRatioProfile = getRatioProfile;
	service.createRatioProfile = createRatioProfile;
	service.updateRatioProfile = updateRatioProfile;
	service.deleteRatioProfile = deleteRatioProfile;
	service.createRatioProfileValue = createRatioProfileValue;
	service.updateRatioProfileValue = updateRatioProfileValue;
	service.deleteRatioProfileValue = deleteRatioProfileValue;

	var getRatioProfilesRoute = "/ratioProfiles/{load}";
	var getRatioProfileRoute = "/ratioProfile/{id}";
	var createRatioProfileRoute = "/createRatioProfile";
	var updateRatioProfileRoute = "/updateRatioProfile";
	var deleteRatioProfileRoute = "/deleteRatioProfile/{id}";
	var createRatioProfileValueRoute = "/createRatioProfileValue";
	var updateRatioProfileValueRoute = "/updateRatioProfileValue";
	var deleteRatioProfileValueRoute = "/deleteRatioProfileValue/{id}";

	function getRatioProfiles(smallLoad) {
		return commonService.get(getRatioProfilesRoute.replace("{load}", smallLoad ? "s":"l"));
	}

	function getRatioProfile(id) {
		return commonService.get(getRatioProfileRoute.replace("{id}", id));
	}

	function createRatioProfile(model) {
		return commonService.post(createRatioProfileRoute, model);
	}

	function updateRatioProfile(model) {
		return commonService.post(updateRatioProfileRoute, model);
	}

	function deleteRatioProfile(id) {
		return commonService.get(deleteRatioProfileRoute.replace("{id}", id));
	}

	function createRatioProfileValue(model) {
		return commonService.post(createRatioProfileValueRoute, model);
	}

	function updateRatioProfileValue(model) {
		return commonService.post(updateRatioProfileValueRoute, model);
	}

	function deleteRatioProfileValue(id) {
		return commonService.get(deleteRatioProfileValueRoute.replace("{id}", id));
	}
  
  	return service;
}