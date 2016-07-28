// http://stackoverflow.com/a/17690791
angular.module("myApp")
.directive("actionDirective", ['$parse', function($parse) {
      return function(scope, element, attr) {
        //grabbing the function from the attributes and parsing it (to get parameters I believe, this taken from the code above.
        var fn = $parse(attr['actionDirective']);

        //making the handler so it can be bound to different events without repeating again taken from source above
        var handler = function(event) {
            scope.$apply(function() {
             fn(scope, {$event:event});
            }
          )};

         //If clicked calling the handler
         element.bind('click', handler);
         //Checking first that it's the enter key "13" then calling the handler
         element.bind('keyup', function(event) { if(event.keyCode==13) handler(event)});
      }
}])