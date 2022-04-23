package com.github.zhongzichang.springiap.receipt;

import com.github.zhongzichang.springiap.InAppOwnershipType;

@lombok.Data
public class LatestReceiptInfo {

  private String app_account_token;
  private String cancellation_date;
  private String cancellation_date_ms;
  private String cancellation_date_pst;

  private String cancellation_reason;
  private String expires_date;
  private String expires_date_ms;
  private String expires_date_pst;
  private InAppOwnershipType in_app_ownership_type;
  private String is_in_intro_offer_period;
  private String is_trial_period;
  private String is_upgraded;
  private String offer_code_ref_name;

  private String original_purchase_date;
  private String original_purchase_date_ms;
  private String original_purchase_date_pst;

  private String original_transaction_id;

  private String product_id;

  private String promotional_offer_id;

  private String purchase_date;

  private String purchase_date_ms;
  private String purchase_date_pst;
  private String quantity;
  private String subscription_group_identifier;
  private String web_order_line_item_id;
  private String transaction_id;

}
