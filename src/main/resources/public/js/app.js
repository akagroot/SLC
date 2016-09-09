'use strict';

// Declare app level module which depends on views, and components
angular.module('myApp', [
  'ui.router', 
  'ui.bootstrap', 
  'restangular', 
  'angularSpinner'
])
.constant('API_END_POINT','/api/v1')
.factory('responseInterceptor', function($q, $log, $injector) {  
	var responseInterceptor = {
	  	responseError: function(response) {
	  		switch(response.status) {
	  			case 401:
	  			case 403:
	  			case 409:
	  				window.location = '/logout';
	  				break;
	  		}

	  		return $q.reject(response);
	  	}
	};
	return responseInterceptor;
})
.factory('apiDataService', function(Restangular, API_END_POINT) {
	var endpoint = API_END_POINT; 
	return Restangular.withConfig(function(RestangularConfigurer) {
		RestangularConfigurer.setBaseUrl(endpoint);

		/* Add CSRF header to all restangular requests */
		var token = $("meta[name='_csrf']").attr('content');
		var header = $("meta[name='_csrf_header']").attr('content');
		var csrfHeader = {};
		csrfHeader[header] = token; 
		RestangularConfigurer.setDefaultHeaders(csrfHeader);

		// This also requires us to use .data when getting data.
		RestangularConfigurer.setFullResponse(true); // Also get headers.
	});
}) 
.config(function($stateProvider, $urlRouterProvider) {
	$urlRouterProvider.otherwise("/redirectToDashboard");

	$stateProvider
		.state('redirectToDashboard', {
			url: '/redirectToDashboard',
			templateUrl: 'view/redirectToDashboard/redirectToDashboard.html',
			controller: 'RedirectToDashboardCtrl', 
			controllerAs: 'RedirectToDashboard', 
			title: 'PerformanceCareRx - Loading'
		})
		.state('exercises', {
			url: '/exercises', 
			templateUrl: 'view/exercises/exercises.html', 
			controller: 'ExercisesCtrl', 
			controllerAs: 'Exercises', 
			title: 'PerformanceCareRx - Exercises'
		})
		.state('ratioProfiles', {
			url: '/ratioProfiles', 
			templateUrl: 'view/ratioProfiles/ratioProfiles.html', 
			controller: 'RatioProfilesCtrl', 
			controllerAs: 'RatioProfiles', 
			title: 'PerformanceCareRx - Ratio Profiles'
		})
		.state('users', {
			url: '/users',
			templateUrl: 'view/users/users.html',
			controller: 'UsersCtrl',
			controllerAs: 'Users',
			title: 'PerformanceCareRx - Users'
		})
		.state('addUser', {
			url: '/addUser', 
			templateUrl: 'view/addUser/addUser.html', 
			controller: 'AddUserCtrl', 
			controllerAs: 'AddUser', 
			title: 'PerformanceCareRx - Add User'
		})
		.state('userProfile', {
			url: '/userProfile/:userId',
			templateUrl: 'view/userProfile/userProfile.html',
			controller: 'UserProfileCtrl',
			controllerAs: 'UserProfile',
			title: 'PerformanceCareRx - UserProfile'
		})
		// .state('userGoals', {
		// 	url: '/userGoals/:userId',
		// 	templateUrl: 'view/userGoals/userGoals.html',
		// 	controller: 'UserGoalsCtrl',
		// 	controllerAs: 'UserGoals',
		// 	title: 'PerformanceCareRx - UserGoals'
		// })
		.state('standards', {
			url: '/standards/:userId',
			templateUrl: 'view/standards/standards.html',
			controller: 'StandardsCtrl',
			controllerAs: 'Standards',
			title: 'PerformanceCareRx - Standards'
		});
})
.run(function($rootScope, $q, API_END_POINT, userService, $state, exerciseService) {
	console.log("app.js run: ", API_END_POINT);

	$rootScope.userProfile = null;
	userService.getUserProfile()
		.then(function(response) {
			console.log("Response: ", response);
			$rootScope.userProfile = response.data;
		}, function(error) {
			console.log("Error: ", error);
			$rootScope.userProfile = null;
		}).finally(function() {
			console.log("rootScope.userProfile? ", $rootScope.userProfile);
			console.log("state.current", $state.current);
			if(!$rootScope.userProfile) {
				console.log("Redirect to login...");
				window.location = '/login'
			} else if($state.current.name == "redirectToDashboard") {
				if($rootScope.userProfile.role == "ADMIN") {
					$state.go("users");
				} else {
					$state.go("userProfile");
				}
			}
		});
});