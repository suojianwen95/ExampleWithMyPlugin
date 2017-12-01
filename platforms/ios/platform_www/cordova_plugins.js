cordova.define('cordova/plugin_list', function(require, exports, module) {
module.exports = [
  {
    "id": "SimpleTest.MyTest",
    "file": "plugins/SimpleTest/www/MyTest.js",
    "pluginId": "SimpleTest",
    "clobbers": [
      "cordova.plugins.MyTest"
    ]
  }
];
module.exports.metadata = 
// TOP OF METADATA
{
  "cordova-plugin-whitelist": "1.3.3",
  "SimpleTest": "1.0.0"
};
// BOTTOM OF METADATA
});