package com.github.zhongzichang.springiap.receipt;

import com.github.zhongzichang.springiap.Environment;

@lombok.Data
public class ReceiptVerifyResult {

  private Environment environment;

  private Boolean isRetryable;
  // The latest Base64 encoded app receipt.
  // Only returned for receipts that contain auto-renewable subscriptions.
  private byte[] latest_receipt;

  private LatestReceiptInfo[] latest_receipt_info;

  private PendingRenewalInfo[] pending_renewal_info;

  private Receipt receipt;

  private int status;
}
