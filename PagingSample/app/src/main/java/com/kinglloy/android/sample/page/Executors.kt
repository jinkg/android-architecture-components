package com.kinglloy.android.sample.page

import java.util.concurrent.Executors

/**
 * @author jinyalin
 * @since 2018/1/26.
 */
private val IO_EXECUTOR = Executors.newSingleThreadExecutor()

/**
 * Utility method to run blocks on a dedicated background thread, used for io/database work.
 */
fun ioThread(f: () -> Unit) {
    IO_EXECUTOR.execute(f)
}