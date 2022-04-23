package com.github.zhongzichang.springiap.notification;

import com.github.zhongzichang.springiap.InAppOwnershipType;

@lombok.Data
public class JWSTransactionDecodedPayload {

  // uuid
  private String appAccountToken;
  private String bundleId;
  private Long expiresDate;
  private InAppOwnershipType inAppOwnershipType;

  private Boolean isUpgraded;
  private String offerIdentifier;

  private Integer offerType;
  private Long originalPurchaseDate; // timestamp

  private String originalTransactionId;
  private String productId;
  private Long purchaseDate;
  private Integer quantity;

  private Long revocationDate;
  private Long signedDate;// timestamp
  private String subscriptionGroupIdentifier;
  private String transactionId;
  // Auto-Renewable Subscription
  // Non-Consumable
  // Consumable
  // Non-Renewing Subscription
  private String type;

  private String webOrderLineItemId;
}
