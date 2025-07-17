package com.github.zhongzichang.springiap.notification;

import com.github.zhongzichang.springiap.Environment;

@lombok.Data
public class Data {
  private String appAppleId; // number
  private String bundleId;
  private String bundleVersion;
  private Environment environment;
  // JWSRenewalInfo
  private String signedRenewalInfo;

  private String signedTransactionInfo;
  private String status;
}
