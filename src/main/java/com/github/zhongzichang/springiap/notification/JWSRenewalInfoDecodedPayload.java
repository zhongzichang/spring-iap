package com.github.zhongzichang.springiap.notification;

@lombok.Data
public class JWSRenewalInfoDecodedPayload {
  private String autoRenewProductId;
  // 0 => Automatic renewal is off. 1 => Automatic renewal is on.
  private Integer autoRenewStatus;
  // 1 => The customer canceled their subscription.
  // 2 => A billing error occurred; for example, the customer’s payment information was no longer valid.
  // 3 => The customer didn’t consent to a recent price increase.
  // 4 => The product wasn’t available for purchase at the time of renewal.
  private Integer expirationIntent;
  private Long gracePeriodExpiresDate; // timestamp
  private Boolean isInBillingRetryPeriod;
  private String offerIdentifier;
  // 1 An introductory offer.
  // 2 A promotional offer.
  // 3 An offer with a subscription offer code.
  private Integer offerType;
  // The original transaction identifier of a purchase.
  private String originalTransactionId;
  // 1 The customer hasn’t responded to the subscription price increase.
  // 2 The customer consented to the subscription price increase.
  private Integer priceIncreaseStatus;
  private String productId;
  private Long signedDate;// timestamp
}
