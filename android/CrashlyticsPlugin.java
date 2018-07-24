package com.tealeaf.plugin.plugins;

import java.util.Iterator;
import org.json.JSONObject;
import org.json.JSONException;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.Log;

import com.tealeaf.plugin.IPlugin;
import com.tealeaf.logger;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;


public class CrashlyticsPlugin implements IPlugin {

  Activity _activity;

  public void onCreateApplication(Context applicationContext) {
    logger.log("{crashlytics} registeriing crashlytics");
      Fabric.Builder fb = new Fabric.Builder(applicationContext)
            .kits(new Crashlytics());

    if (isDebuggable()) {
      fb.debuggable(true);
    }
    Fabric fabric = fb.build();
    Fabric.with(fabric);
  }

  private int getLogLevel(String level) {
    if ("debug".equals(level)) {
      return Log.DEBUG;
    } else if ("warn".equals(level)) {
      return Log.WARN;
    } else if ("error".equals(level)) {
      return Log.ERROR;
    } else {
      return Log.INFO;
    }
  }

  private boolean isDebuggable() {
    return 0 != (_activity.getApplicationInfo().flags &= ApplicationInfo.FLAG_DEBUGGABLE);
  }

  public void setUserData(String jsonParams) {
    try {
      logger.log("{crashlytics} inside setUserdata");

      JSONObject obj = new JSONObject(jsonParams);
      Iterator<?> keys = obj.keys();
      String currKey, currVal;

      if (obj.has("uid")) {
        Crashlytics.setUserIdentifier(obj.getString("uid"));
      }

      while(keys.hasNext()) {
        currKey = (String) keys.next();
        currVal = obj.getString(currKey);

        if ("uid".equals(currKey)) {
          Crashlytics.setUserIdentifier(currVal);
        } else {
          Crashlytics.setString(currKey, currVal);
        }
      }
    } catch (JSONException e) {
      logger.log("{crashlytics} setUserdata - failure: " + e.getMessage());
    }
  }

  public void log(String jsonParams) {
    try {
      logger.log("{crashlytics inside logger");

      JSONObject obj = new JSONObject(jsonParams);
      Crashlytics.log(getLogLevel(obj.getString("priority")), obj.getString("tag"), obj.getString("msg"));
    } catch (JSONException e) {
      logger.log("{crashlytics logger - failure: " + e.getMessage());
    }
  }

  public void onCreate(Activity activity, Bundle savedInstanceState) {

    _activity = activity;


  }

  public void onResume() {

  }

  public void onRenderResume() {

  }

  public void onStart() {

  }

  public void onFirstRun() {

  }

  public void onPause() {
  }

  public void onRenderPause() {

  }

  public void onStop() {

  }

  public void onDestroy() {

  }

  public void setInstallReferrer(String referrer) {

  }

  public void onActivityResult(Integer request, Integer result, Intent data) {

  }

  public boolean consumeOnBackPressed() {
    return true;
  }

  public void onBackPressed() {

  }

  public void onNewIntent(Intent intent) {

  }

}
