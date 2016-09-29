'use strict';

angular.module('myApp')
.service('storageService', storageService);

function storageService($q, $log) {

	var service = {};

  service.set = set;
  service.get = get;

  function set(key, value) {
    localStorage.setItem(key, JSON.stringify(value));
  }

  function get(key) {
    try {
      return JSON.parse(localStorage.getItem(key));
    } catch(f) {
      $log.error("Error loading data: ", key);
    }
    return null;
  }
  
  return service;
}