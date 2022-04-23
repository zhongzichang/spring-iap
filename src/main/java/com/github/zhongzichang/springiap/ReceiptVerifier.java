package com.github.zhongzichang.springiap;

import com.github.zhongzichang.springiap.receipt.ReceiptVerifyResult;

public interface ReceiptVerifier {
  ReceiptVerifyResult verify(String receiptData, String password, boolean sandbox);
  ReceiptVerifyResult verify(byte[] receiptData, String password, boolean sandbox);
}
