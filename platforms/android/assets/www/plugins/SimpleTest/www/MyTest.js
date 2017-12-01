cordova.define("SimpleTest.MyTest", function(require, exports, module) {
var exec = require('cordova/exec');
var myTestFunc = function(){};  

myTestFunc.prototype.coolMethod = function (arg0, success, error) {
    exec(success, error, 'MyTest', 'coolMethod', [arg0]);
};

myTestFunc.prototype.thelist = function ( success, error) {
    exec(success, error, 'MyTest', 'thelist', []);
};

myTestFunc.prototype.connect = function (address, success, error) {
    exec(success, error, 'MyTest', 'connect', [address]);
};

myTestFunc.prototype.printData = function (data, success, error) {
    exec(success, error, 'MyTest', 'printData', [data]);
};

myTestFunc.prototype.opencash = function ( data,success, error) {
    exec(success, error, 'MyTest', 'opencash', [data]);
};

myTestFunc.prototype.cutpaper = function ( data,success, error) {
    exec(success, error, 'MyTest', 'cutpaper', [data]);
};

myTestFunc.prototype.example = function ( success, error) {
    exec(success, error, 'MyTest', 'example', []);
};


var MYTESTFUNC = new myTestFunc();
module.exports = MYTESTFUNC; 

});

