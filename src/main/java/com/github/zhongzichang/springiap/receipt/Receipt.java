package com.github.zhongzichang.springiap.receipt;

@lombok.Data
public class Receipt {

  private Long adam_id;

  private Long app_item_id;

  private String application_version;

  private String bundle_id;

  private String download_id;

  private String expiration_date;
  private String expiration_date_ms;
  private String expiration_date_pst;

  private InApp[] in_app;

  private String original_application_version;

  private String original_purchase_date;
  private String original_purchase_date_ms;
  private String original_purchase_date_pst;

  private String preorder_date;

  private String preorder_date_ms;
  private String preorder_date_pst;

  private String receipt_creation_date;
  private String receipt_creation_date_ms;
  private String receipt_creation_date_pst;

  // Possible values: Production, ProductionVPP, ProductionSandbox, ProductionVPPSandbox
  private String receipt_type;

  private String request_date;
  private String request_date_ms;
  private String request_date_pst;

  private Integer version_external_identifier;
}
