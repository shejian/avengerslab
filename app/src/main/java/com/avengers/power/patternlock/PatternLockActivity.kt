package com.avengers.power.patternlock

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.text.TextUtils
import android.util.Log
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.avengers.power.R
import com.avengers.power.databinding.ActivityPatternLockBinding
import com.avengers.power.patternlock.listener.PatternLockViewListener
import com.avengers.power.patternlock.utils.PatternLockUtils
import com.avengers.power.patternlock.utils.ResourceUtils
import com.avengers.zombiebase.BaseActivity
import com.avengers.zombiebase.ZombieBaseUtils
import com.spinytech.macore.router.RouterRequest

@Route(path = "/patternlock/patternLockActivity")
class PatternLockActivity : BaseActivity() {
    override fun reloadData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private lateinit var activityDataBinding: ActivityPatternLockBinding
    var patternPassword:String = ""
    var confirmPassword:String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var routerRequest = RouterRequest.obtain(this)
                .provider("WakandaProvider")
                .action("WakandaAction")
                .data("password", "")
                .data("confirmPassword", "")
        var readsa = ZombieBaseUtils.onLocalRoute(this, routerRequest)
        activityDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_pattern_lock)
        activityDataBinding.patterLockView!!.dotCount = 3
        activityDataBinding.patterLockView!!.dotNormalSize = ResourceUtils.getDimensionInPx(this, R.dimen.pattern_lock_dot_size).toInt()
        activityDataBinding.patterLockView!!.dotSelectedSize = ResourceUtils.getDimensionInPx(this, R.dimen.pattern_lock_dot_selected_size).toInt()
        activityDataBinding.patterLockView!!.pathWidth = ResourceUtils.getDimensionInPx(this, R.dimen.pattern_lock_path_width).toInt()
        activityDataBinding.patterLockView!!.isAspectRatioEnabled = true
        activityDataBinding.patterLockView!!.aspectRatio = PatternLockView.AspectRatio.ASPECT_RATIO_HEIGHT_BIAS
        activityDataBinding.patterLockView!!.setViewMode(PatternLockView.PatternViewMode.CORRECT)
        activityDataBinding.patterLockView!!.wrongStateColor = resources.getColor(R.color.red)
        activityDataBinding.patterLockView!!.dotAnimationDuration = 150
        activityDataBinding.patterLockView!!.pathEndAnimationDuration = 100
        activityDataBinding.patterLockView!!.correctStateColor = ResourceUtils.getColor(this, R.color.white)
        activityDataBinding.patterLockView!!.isInStealthMode = false
        activityDataBinding.patterLockView!!.isTactileFeedbackEnabled = true
        activityDataBinding.patterLockView!!.isInputEnabled = true
        activityDataBinding.patterLockView!!.addPatternLockListener(mPatternLockViewListener)
    }

    private val mPatternLockViewListener = object : PatternLockViewListener {
        override fun onStarted() {
            Log.d(javaClass.name, "Pattern drawing started")
            activityDataBinding.textTip!!.setText("")
            activityDataBinding.textPattenType!!.setText("完成后移开手指")
        }

        override fun onProgress(progressPattern: List<PatternLockView.Dot>) {
            Log.d(javaClass.name, "Pattern progress: " + PatternLockUtils.patternToString(activityDataBinding.patterLockView, progressPattern))
        }

        override fun onComplete(pattern: List<PatternLockView.Dot>) {
            Log.d(javaClass.name, "Pattern complete: " + PatternLockUtils.patternToString(activityDataBinding.patterLockView, pattern))
            var gesturePatternStr:String = PatternLockUtils.patternToString(activityDataBinding.patterLockView, pattern)
            if (gesturePatternStr.length < 4) {
                activityDataBinding.textPattenType!!.setText("至少连接4个点")
                activityDataBinding.patterLockView.clearPattern()
            } else {
                if (TextUtils.isEmpty(patternPassword) && TextUtils.isEmpty(confirmPassword)) {
                    // 首次绘制
                    patternPassword = gesturePatternStr
                    activityDataBinding.patterLockView.clearPattern()
                    activityDataBinding.textPattenType!!.setText("验证图案")
                } else {
                    if (TextUtils.isEmpty(confirmPassword)) {
                        // 二次确认
                        confirmPassword = gesturePatternStr
                        if (!confirmPassword.equals(patternPassword)) {
                            // 不一致
                            activityDataBinding.textTip!!.setText("密码不匹配，请重新设置")
                            activityDataBinding.textPattenType!!.setText("绘制图案")
                            activityDataBinding.patterLockView.clearPattern()
                            patternPassword = ""
                            confirmPassword = ""
                        } else {
                            activityDataBinding.textTip!!.visibility = View.GONE
                            activityDataBinding.textPattenType!!.setText("")
                            activityDataBinding.textPattenType!!.setText("设置成功")
                            finish()
                        }
                    } else {
                        // 校验手势密码, 解锁
                        if (gesturePatternStr == patternPassword) {
                            activityDataBinding.patterLockView!!.setViewMode(PatternLockView.PatternViewMode.CORRECT)
                            finish()
                        } else {
                            activityDataBinding.patterLockView!!.setViewMode(PatternLockView.PatternViewMode.WRONG)
                            activityDataBinding.patterLockView.clearPattern()
                            activityDataBinding.textTip!!.setText("密码输入有误")
                        }
                    }
                }
            }
        }

        override fun onCleared() {
            Log.d(javaClass.name, "Pattern has been cleared")
        }
    }


}
