var exec = require('cordova/exec');

exports.getstatus = function(arg0, success, error) {
    exec(success, error, "geolocation", "getstatus", [arg0]);
};

exports.getname = function(arg0, success, error) {
  if (arg0 && typeof(arg0) === 'string' && arg0.length > 0) {
    success(arg0);
  }else {
    error('Empty message!');
  }
};

exports.getgeolocation = function(arg0, success, error) {
  exec(success, error, "geolocation", "getgeolocation", [arg0]);
}
