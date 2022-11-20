package com.zero.schools.utils

import androidx.fragment.app.FragmentManager
import androidx.test.espresso.IdlingResource

class FragmentIdlingResource(
    private val manager: FragmentManager, private val tag: String
) : IdlingResource {

    override fun getName(): String {
        return FragmentIdlingResource::class.java.name + ":" + tag
    }

    private var callback: IdlingResource.ResourceCallback? = null

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        this.callback = callback
    }

    override fun isIdleNow(): Boolean {
        val isIdle = manager.findFragmentByTag(tag) == null
        if (isIdle) callback?.onTransitionToIdle()
        return (isIdle)
    }
}