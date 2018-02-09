package com.kinglloy.android.github.vo;

/**
 * Status of a resource that is provided to the UI.
 * <p>
 * These are usually created by the Repository classes where they return
 * {@code LiveData<Resource<T>>} to pass back the latest data to the UI with its fetch status.
 *
 * @author jinyalin
 * @since 2018/2/9.
 */

public enum Status {
    SUCCESS,
    ERROR,
    LOADING
}
