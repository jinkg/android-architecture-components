package com.kinglloy.example.android.kotlin

import org.mockito.ArgumentCaptor

/**
 * Returns ArgumentCaptor.capture() as nullable type to avoid java.lang.IllegalStateException
 * when null is returned.
 * @author jinyalin
 * @since 2018/1/23.
 */

fun <T> capture(argumentCaptor: ArgumentCaptor<T>): T = argumentCaptor.capture()