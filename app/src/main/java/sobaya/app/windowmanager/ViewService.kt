package sobaya.app.windowmanager

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager

class ViewService: Service() {

    private var windowManager: WindowManager? = null
    private var view: View? = null

    override fun onBind(intent: Intent?) = null

    override fun onCreate() {

        super.onCreate()
        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        view = LayoutInflater.from(this)
            .inflate(R.layout.overlay, null, false).apply {
                setOnClickListener {
                    stopSelf()
                }
            }

        val layoutFlag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            else
                WindowManager.LayoutParams.TYPE_PHONE

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            0,
            0,
            layoutFlag,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
            PixelFormat.TRANSLUCENT
        )

        windowManager?.addView(view, params)
    }

    override fun onDestroy() {

        windowManager?.removeView(view)
        super.onDestroy()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int) = START_NOT_STICKY
}