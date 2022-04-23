package com.github.zhongzichang.springiap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.zhongzichang.springiap.notification.JWSRenewalInfoDecodedPayload;
import com.github.zhongzichang.springiap.notification.JWSTransactionDecodedPayload;
import com.github.zhongzichang.springiap.notification.ResponseBodyV2DecodedPayload;

import java.security.cert.CertificateException;

public interface NotificationDecoder {

  ResponseBodyV2DecodedPayload decodePayload(String signedPayload) throws JsonProcessingException, CertificateException;
  JWSRenewalInfoDecodedPayload decodeRenewalInfo(String signedRenewalInfo) throws JsonProcessingException, CertificateException;
  JWSTransactionDecodedPayload decodeTransaction(String signedTransaction) throws JsonProcessingException, CertificateException;

}
