package com.github.zhongzichang.springiap.notification;

@lombok.Data
public class ResponseBodyV2DecodedPayload {
  private NotificationType notificationType;
  private Subtype subtype;
  private String notificationUUID;
  private String version; // 2.0
  private Data data;
}

