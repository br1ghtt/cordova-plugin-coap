var exec = require('cordova/exec');

function CoapClient() {

}
CoapClient.prototype.get = function(url, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Coap", "get", [url]);
};
CoapClient.prototype.post = function(url, options, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Coap", "post", [url, options]);
};
CoapClient.prototype.ping = function (url, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Coap", "ping", [url]);
};

module.exports = new CoapClient();
