package com.github.zhongzichang.springiap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.zhongzichang.springiap.notification.Data;
import com.github.zhongzichang.springiap.notification.JWSRenewalInfoDecodedPayload;
import com.github.zhongzichang.springiap.notification.JWSTransactionDecodedPayload;
import com.github.zhongzichang.springiap.notification.ResponseBodyV2DecodedPayload;

import java.security.cert.CertificateException;

/**
 * Apple App Store Server Notifications Decoder.
 *
 * @see <a
 *     href="https://developer.apple.com/documentation/appstoreservernotifications/receiving_app_store_server_notifications">Receiving
 *     App Store Server Notifications</a>
 */
public interface NotificationDecoder {

  /**
   * Decode singed payload.
   * @see <a href="https://developer.apple.com/documentation/appstoreservernotifications/signedpayload">signedPayload on developer.apple.com</a>
   * @param signedPayload A cryptographically signed payload, in JSON Web Signature (JWS) format,
   *     containing the response body for a version 2 notification.
   * @return The response body the App Store sends in a version 2 server notification.
   * @throws JsonProcessingException invalid content
   * @throws CertificateException invalid certificate
   */
  ResponseBodyV2DecodedPayload decodePayload(String signedPayload) throws JsonProcessingException, CertificateException;

  /**
   * Decode renewal info.
   * @see <a href="https://developer.apple.com/documentation/appstoreservernotifications/jwsrenewalinfo">renewal info on developer.apple.com</a>
   * @param signedRenewalInfo {@link Data#getSignedRenewalInfo()} Subscription renewal information
   *     signed by the App Store, in JSON Web Signature (JWS) * format.
   * @return Subscription renewal information
   * @throws JsonProcessingException invalid content
   * @throws CertificateException invalid certificate
   */
  JWSRenewalInfoDecodedPayload decodeRenewalInfo(String signedRenewalInfo) throws JsonProcessingException, CertificateException;

  /**
   * Decode signed transaction info.
   * @see <a href="https://developer.apple.com/documentation/appstoreservernotifications/jwstransaction">transaction info on developer.apple.com</a>
   * @param signedTransaction {@link Data#getSignedTransactionInfo()} Transaction information,
   *     signed by the App Store, in JSON Web Signature (JWS) format.
   * @return Transaction information
   * @throws JsonProcessingException invalid content
   * @throws CertificateException invalid certificate
   */
  JWSTransactionDecodedPayload decodeTransaction(String signedTransaction) throws JsonProcessingException, CertificateException;

}
