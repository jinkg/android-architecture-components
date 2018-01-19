package com.kinglloy.example.android.persistence.model;

import java.util.Date;

/**
 * @author jinyalin
 * @since 2018/1/19.
 */

public interface Comment {
    int getId();

    int getProductId();

    String getText();

    Date getPostedAt();
}
