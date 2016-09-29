'use strict';
/**
 * Search filter
 * http://stackoverflow.com/a/17315483
 */
angular.module('myApp').filter('selectedAthlete', function($rootScope) {

  return function (userList, selectedFilter) {
  	if(selectedFilter == "ALL") {
  		return userList;
  	}

  	var returnList = new Array();

  	$.each(userList, function(i, e) {
  		if(selectedFilter == "UNASSIGNED" && (e.coachId == null || e.coachId == undefined)) {
  			returnList.push(e);
  		} else if(selectedFilter == "MY" && e.coachId == $rootScope.userProfile.id) {
  			returnList.push(e);
  		}
  	});

    return returnList;
  }
});
