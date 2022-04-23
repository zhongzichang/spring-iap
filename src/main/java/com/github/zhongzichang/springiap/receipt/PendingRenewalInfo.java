package com.github.zhongzichang.springiap.receipt;

@lombok.Data
public class PendingRenewalInfo {

  private String auto_renew_product_id;

  private String auto_renew_status;

  private String expiration_intent;

  private String grace_period_expires_date;

  private String grace_period_expires_date_ms;

  private String is_in_billing_retry_period;

  private String offer_code_ref_name;

  private String original_transaction_id;

  private String price_consent_status;

  private String product_id;

  private String promotional_offer_id;
}
