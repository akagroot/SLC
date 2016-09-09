'use strict';

angular.module('myApp')
.service('userService', userService);

function userService($q, $log, commonService) {

	var service = {};

  service.getAllUsers = getAllUsers;
  service.getUserProfile = getUserProfile;
  service.getUserData = getUserData;
  service.addUser = addUser;
  service.setRole = setRole;
  service.updateUser = updateUser;
  service.deleteUser = deleteUser;
  service.setGoalVisibility = setGoalVisibility;
  service.getPerfectAccount = getPerfectAccount;
  service.setPerfectAccount = setPerfectAccount;

  var getUserProfileRoute = "/userProfile";
  var setPerfectAccountRoute = "/updatePerfectAccount";
  var getPerfectAccountRoute = "/perfectAccount";
  var getUserDataRoute = "/userData";
  var getUserDataByIdRoute = "/userData/{userId}";
  var getAllUsersRoute = "/allUsers";
  var addUserRoute = "/addUser";
  var setUserRoleRoute = "/updateRole";
  var updateUserRoute = "/updateUser";
  var deleteUserRoute = "/deleteUser/{userId}";

  service.addGoal = addGoal;
  service.deleteGoal = deleteGoal;
  service.getGroupedGoals = getGroupedGoals;

  var addGoalRoute = "/addGoal";
  var deleteGoalRoute = "/deleteGoal/{goalId}";
  var visibilityGoalRoute = "/updateGoalVisibility";
  var getGroupedGoalsRoute = "/groupedGoals/{userId}";

  function setPerfectAccount(id) {
    return commonService.post(setPerfectAccountRoute, id);
  }

  function getPerfectAccount() {
    return commonService.get(getPerfectAccountRoute);
  }

  function addGoal(model) {
    return commonService.post(addGoalRoute, model);
  }

  function setGoalVisibility(goalId, visibility) {
    var model = new Object();
    model.exerciseGoalId = goalId;
    model.visibility = visibility;
    return commonService.post(visibilityGoalRoute, model);
  }

  function deleteGoal(goalId) {
    return commonService.get(deleteGoalRoute.replace("{goalId}", goalId));
  }

  function getGroupedGoals(userId) {
    return commonService.get(getGroupedGoalsRoute.replace("{userId}", userId)); 
  }

  function getAllUsers(){
    return commonService.get(getAllUsersRoute);
  }

  function getUserProfile() {
    return commonService.get(getUserProfileRoute);
  }

  function getUserData(userId) {
    if(userId == undefined || userId == null || userId.length == 0) 
      return commonService.get(getUserDataRoute);
    else
      return commonService.get(getUserDataByIdRoute.replace("{userId}", userId));
  }

  function setRole(userId, role) {
    var data = {};
    data.userId = userId;
    data.role = role;
    return commonService.post(setUserRoleRoute, data);
  }

  function addUser(data) {
    return commonService.post(addUserRoute, data);
  }

  function updateUser(model) {
    return commonService.post(updateUserRoute, model);
  }

  function deleteUser(userId) {
    return commonService.get(deleteUserRoute.replace("{userId}", userId));
  }
  
  return service;
}