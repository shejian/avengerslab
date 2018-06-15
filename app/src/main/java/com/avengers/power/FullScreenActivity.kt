package com.avengers.power

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.widget.Toast
import com.avengers.power.databinding.ActivityFullscreenBinding
import com.avengers.zombiebase.*
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.Permission
import com.yanzhenjie.permission.PermissionActivity
import com.yanzhenjie.permission.runtime.PermissionRequest


class FullScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var fullscreenBinding = DataBindingUtil.setContentView<ActivityFullscreenBinding>(this, R.layout.activity_fullscreen)
        initPermission()
    }


    //启动权限，设备读写权限，运行时权限定位，相机，
    fun initPermission() {
        requestPermission(Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE, Permission.CAMERA)

    }

    /**
     * Request permissions.
     */
    private fun requestPermission(vararg permissions: String) {
        var sdsd = RuntimeRationale()

        AndPermission.with(this)
                .runtime()
                .permission(*permissions)
                .rationale(sdsd)
                .onGranted {
                    toast(R.string.successfully)
                    toMainActivity()
                    finish()
                }
                .onDenied { permissions ->
                    toast(R.string.failure)
                    if (AndPermission.hasAlwaysDeniedPermission(this@FullScreenActivity, permissions)) {
                        showSettingDialog(this@FullScreenActivity, permissions)
                    } else {
                        initPermission()
                    }
                }
                .start()
    }

    /**
     * Display setting dialog.
     */
    fun showSettingDialog(context: Context, permissions: List<String>) {
        val permissionNames = Permission.transformText(context, permissions)
        val message = context.getString(R.string.message_permission_always_failed, TextUtils.join("\n", permissionNames))

        AlertDialog.Builder(context)
                .setCancelable(false)
                .setTitle(R.string.title_dialog)
                .setMessage(message)
                .setPositiveButton(R.string.setting) { dialog, which -> setPermission() }
                .setNegativeButton(R.string.cancel) { dialog, which -> }
                .show()
    }

    /**
     * Set permissions.
     */
    private fun setPermission() {
        AndPermission.with(this)
                .runtime()
                .setting()
                .onComeback { Toast.makeText(this@FullScreenActivity, R.string.message_setting_comeback, Toast.LENGTH_SHORT).show() }
                .start()
    }


    private fun requestPermissionForAlertWindow() {
        AndPermission.with(this)
                .overlay()
                .rationale(OverlayRationale())
                .onGranted { showAlertWindow() }
                .onDenied { toast(R.string.message_overlay_failed) }
                .start()
    }

    private fun showAlertWindow() {
        showLauncherView()
        val backHome = Intent(Intent.ACTION_MAIN)
        backHome.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        backHome.addCategory(Intent.CATEGORY_HOME)
        startActivity(backHome)
    }


    fun showLauncherView() {
        val alertWindow = AlertWindow(this)
        val view = LauncherView(this)
        view.setCancelClickListener { alertWindow.dismiss() }
        alertWindow.setContentView(view)
        alertWindow.show()
    }

    fun toMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    protected fun toast(@StringRes message: Int) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    protected fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}
