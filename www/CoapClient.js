var exec = require('cordova/exec');

function CoapClient() {

}
CoapClient.prototype.get = function (options, successCallback, errorCallback) {
    console.log(JSON.stringify(options));
    exec(successCallback, errorCallback, "Coap", "get", [options]);
};
CoapClient.prototype.discover = function (options, successCallback, errorCallback) {
    console.log(JSON.stringify(options));
    exec(successCallback, errorCallback, "Coap", "discover", [options]);
};
CoapClient.prototype.ping = function (options, successCallback, errorCallback) {
    console.log(JSON.stringify(options));
    exec(successCallback, errorCallback, "Coap", "ping", [options]);
};

module.exports = new CoapClient();
