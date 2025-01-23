package com.busschedule.util.permission

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings

private const val REQ_CODE_OVERLAY_PERMISSION: Int = 0

object OverlayPermission {
    fun onObtainingPermissionOverlayWindow(context: Activity) {
        val intent = Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            Uri.parse("package:${context.packageName}")
        )
        context.startActivityForResult(intent, REQ_CODE_OVERLAY_PERMISSION)
    }


    fun alertPermissionCheck(context: Context?): Boolean {
        return !Settings.canDrawOverlays(context)
    }
}