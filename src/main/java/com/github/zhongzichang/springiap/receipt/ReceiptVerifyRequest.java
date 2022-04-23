package com.github.zhongzichang.springiap.receipt;

@lombok.Data
public class ReceiptVerifyRequest {
  // (Required) The Base64-encoded receipt data.
  private byte[] receiptData;
  // Your app’s shared secret, which is a hexadecimal string.
  private String password;
  // Set this value to true for the response to include only the latest renewal transaction for any subscriptions.
  // Use this field only for app receipts that contain auto-renewable subscriptions.
  private Boolean excludeOldTransactions;
}
