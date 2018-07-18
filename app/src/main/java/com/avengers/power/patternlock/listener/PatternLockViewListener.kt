package com.avengers.power.patternlock.listener

/**
 * Created by wulianghuan on 19/03/17.
 */


import com.avengers.power.patternlock.PatternLockView

/**
 * The callback interface for detecting patterns entered by the user
 */
interface PatternLockViewListener {

    /**
     * Fired when the pattern drawing has just started
     */
    fun onStarted()

    /**
     * Fired when the pattern is still being drawn and progressed to
     * one more [com.andrognito.patternlockview.PatternLockView.Dot]
     */
    fun onProgress(progressPattern: List<PatternLockView.Dot>)

    /**
     * Fired when the user has completed drawing the pattern and has moved their finger away
     * from the view
     */
    fun onComplete(pattern: List<PatternLockView.Dot>)

    /**
     * Fired when the patten has been cleared from the view
     */
    fun onCleared()
}