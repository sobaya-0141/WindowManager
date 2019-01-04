package sobaya.app.windowmanager

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AppCompatActivity

class MainActivity: AppCompatActivity() {

    val REQUEST_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (checkPermission())
            startOverlay()
        else
            requestPermission()
    }

    private fun checkPermission() = if (Build.VERSION.SDK_INT < 23) true else Settings.canDrawOverlays(this)

    private fun requestPermission() {
        startActivityForResult(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:${packageName}")), REQUEST_CODE)
    }

    private fun startOverlay() {

        if (Build.VERSION.SDK_INT >= 26)
            startForegroundService(Intent(this, ViewService::class.java))
        else
            startService(Intent(this, ViewService::class.java))
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == REQUEST_CODE) {
            if (checkPermission())
                startOverlay()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}