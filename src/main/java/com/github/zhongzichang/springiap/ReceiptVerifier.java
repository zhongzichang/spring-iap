package com.github.zhongzichang.springiap;

import com.github.zhongzichang.springiap.receipt.ReceiptVerifyResult;

/**
 * App Store In-App Purchase Receipt Verifier.
 *
 * @see <a href="https://developer.apple.com/documentation/appstorereceipts/verifyreceipt">verifyReceipt on develiper.apple.com</a>
 */
public interface ReceiptVerifier {

  /**
   * Send a receipt to the App Store for verification.
   * @see <a href="https://developer.apple.com/documentation/appstorereceipts/requestbody">request body on developer.apple.com</a>
   * @see <a href="https://developer.apple.com/documentation/appstorereceipts/responsebody">response body on developer.apple.com</a>
   * @param receiptData (Required) The Base64-encoded receipt data.
   * @param password Your app’s shared secret, which is a hexadecimal string.
   * @param sandbox true if in sandbox else false.
   * @return Result mapped to Decoded responseBody.
   */
  ReceiptVerifyResult verify(String receiptData, String password, boolean sandbox);

  /**
   * Send a receipt to the App Store for verification.
   * @see <a href="https://developer.apple.com/documentation/appstorereceipts/requestbody">request body on developer.apple.com</a>
   * @see <a href="https://developer.apple.com/documentation/appstorereceipts/responsebody">response body on developer.apple.com</a>
   * @param receiptData (Required) The Base64-encoded receipt data.
   * @param password Your app’s shared secret, which is a hexadecimal string.
   * @param sandbox true if in sandbox else false.
   * @return Result mapped to Decoded responseBody.
   */
  ReceiptVerifyResult verify(byte[] receiptData, String password, boolean sandbox);
}
