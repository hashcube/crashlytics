var Crashlytics = Class(function () {
  "use strict";

  this.setUserId = function (uid) {
    this.setUserData({
      uid: uid
    });
  };

  this.setUserData = function (data) {
    if (!data) {
      data = {};
    }

    NATIVE.plugins.sendEvent("CrashlyticsPlugin", "setUserData", JSON.stringify(data));
  };

  this.log = function (msg, tag, priority) {
    if (!msg) {
      return;
    }

    if (!priority) {
      priority = "info";
    }

    if (!tag) {
      tag = "App";
    }

    NATIVE.plugins.sendEvent("CrashlyticsPlugin", "log", JSON.stringify({
      priority: priority,
      tag: tag,
      msg: msg
    }));
  };
});

exports = new Crashlytics();
