package com.github.zhongzichang.springiap.receipt;

public class Status {
  // The request to the App Store was not made using the HTTP POST request method.
  public static final int NOT_HTTP_POST = 21000;
  // This status code is no longer sent by the App Store.
  public static final int NO_LONGER_SENT = 21001;
  // The data in the receipt-data property was malformed or the service experienced a temporary issue. Try again.
  public static final int RECEIPT_MALFORMED_OR_TEMPORARY_ISSUE = 21002;
  // The receipt could not be authenticated.
  public static final int RECEIPT_NOT_AUTHENTICATED = 21003;
  public static final int SHARED_SECRET_NOT_MATCH = 21004;
  public static final int TEMPORARILY_UNABLE_PROVIDE = 21005;
  // This receipt is valid but the subscription has expired.
  // When this status code is returned to your server, the receipt data is also decoded and returned as part of the response.
  // Only returned for iOS 6-style transaction receipts for auto-renewable subscriptions.
  public static final int SUBSCRIPTION_EXPIRED = 21006;
  // This receipt is from the test environment, but it was sent to the production environment for
  // verification.
  public static final int SANDBOX_TO_PRODUCTION = 21007;
  public static final int PRODUCTION_TO_SANDBOX = 21008;
  public static final int INTERNAL_DATA_ACCESS_ERROR = 21009;
  // The user account cannot be found or has been deleted.
  public static final int USER_ACCOUNT_NOT_FOUND = 21010;
}
