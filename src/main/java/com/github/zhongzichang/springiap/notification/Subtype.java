package com.github.zhongzichang.springiap.notification;

public enum Subtype {
  INITIAL_BUY,
  RESUBSCRIBE,
  DOWNGRADE,
  UPGRADE,
  AUTO_RENEW_ENABLED,
  AUTO_RENEW_DISABLED,
  VOLUNTARY,
  BILLING_RETRY,
  PRICE_INCREASE,
  GRACE_PERIOD,
  BILLING_RECOVERY,
  PENDING,
  ACCEPTED
}
