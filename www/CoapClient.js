var exec = require('cordova/exec');

function CoapClient() {

}
CoapClient.prototype.get = function (options, successCallback, errorCallback) {
    console.log(JSON.stringify(options));
    exec(successCallback, errorCallback, "Coap", "get", [options]);
};
CoapClient.prototype.post = function (options, successCallback, errorCallback) {
    console.log(JSON.stringify(options));
    exec(successCallback, errorCallback, "Coap", "post", [options]);
};
CoapClient.prototype.put = function (options, successCallback, errorCallback) {
    console.log(JSON.stringify(options));
    exec(successCallback, errorCallback, "Coap", "put", [options]);
};
CoapClient.prototype.delete = function (options, successCallback, errorCallback) {
    console.log(JSON.stringify(options));
    exec(successCallback, errorCallback, "Coap", "delete", [options]);
};
CoapClient.prototype.discover = function (options, successCallback, errorCallback) {
    console.log(JSON.stringify(options));
    exec(successCallback, errorCallback, "Coap", "discover", [options]);
};
CoapClient.prototype.ping = function (options, successCallback, errorCallback) {
    console.log(JSON.stringify(options));
    exec(successCallback, errorCallback, "Coap", "ping", [options]);
};
CoapClient.prototype.mediaTypes = function (successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Coap", "mediatypes", []);
};

module.exports = new CoapClient();
