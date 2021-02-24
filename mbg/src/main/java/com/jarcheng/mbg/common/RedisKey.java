package com.jarcheng.mbg.common;

public interface RedisKey {
    String WINDOW_QUEUE_KEY = "window:";
    String WINDOW_PRODUCT_KEY = "window:product:";
    String PRODUCT_LOCK_KEY = "lock:product:";
    String ORDER_QUEUE_KEY = "order:queue";
    String TASK_GEO_KEY = "task:geo:";
    String GROUP_MESSAGE = "group:";
    String REDIS_KEY_PREFIX_AUTH_CODE = "portal:authCode:";
    int AUTH_CODE_EXPIRE_SECONDS = 60 * 5;
    String TOKEN_KEY = "flash-user:";
    String CAROUSEL_KEY = "carousel";
    String WX_SESSION_ID="wx:session:";
}
