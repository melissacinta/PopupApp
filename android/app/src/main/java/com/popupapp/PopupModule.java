package com.popupapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

public class PopupModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;

    public PopupModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "PopupModule";
    }

    @ReactMethod
    public void showPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getCurrentActivity());
        builder.setMessage("Your Popup Content")
                .setCancelable(false)
                .setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        alert.show();
    }


    @ReactMethod
    public void requestOverlayPermission(Promise promise) {
        /**
         *  Before android 6.0 Marshmallow you dont need to ask for canDrawOverlays permission,
         *  but in newer android versions this is mandatory
         */
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(this.reactContext)) {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                            Uri.parse("package:" + this.reactContext.getPackageName()));
                    this.reactContext.startActivityForResult(intent, 0, null);
                }
            } else {
                promise.resolve(true);
            }
        } catch (Error e) {
            promise.reject(e);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @ReactMethod
    public void isRequestOverlayPermissionGranted(Callback booleanCallback) {
        boolean equal=!Settings.canDrawOverlays(this.reactContext);
        booleanCallback.invoke(equal);
    }
}
