package com.jarcheng.mbg.model;

import lombok.Data;

@Data
public class WXAuth {
    String encryptedData;
    String iv;
    String sessionId;
}
