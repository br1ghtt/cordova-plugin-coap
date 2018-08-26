var exec = require('cordova/exec');

function CoapClient() {

}
CoapClient.prototype.get = function(name, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Coap", "get", [name]);
};
CoapClient.prototype.post = function(name, options, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Coap", "post", [name, options]);
};
CoapClient.prototype.test = function (successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Coap", "test");
};

module.exports = new CoapClient();
