package com.github.zhongzichang.springiap.notification;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.github.zhongzichang.springiap.NotificationDecoder;

import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.interfaces.ECPublicKey;
import java.util.Base64;

import static com.github.zhongzichang.springiap.util.CertificateUtil.x5c0ToKey;

public class NotificationDecoderImpl implements NotificationDecoder {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  static {
    objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.LOWER_CAMEL_CASE);
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }

  private void verify(DecodedJWT jwt) throws JsonProcessingException, CertificateException {
    String header = new String(Base64.getDecoder().decode(jwt.getHeader()));
    JWSDecodedHeader decodedHeader = objectMapper.readValue(header, JWSDecodedHeader.class);
    PublicKey publicKey = x5c0ToKey(decodedHeader.getX5c()[0]);
    Algorithm.ECDSA256((ECPublicKey) publicKey, null).verify(jwt);
  }

  @Override
  public ResponseBodyV2DecodedPayload decodePayload(String signedPayload)
      throws JsonProcessingException, CertificateException {

    DecodedJWT jwt = JWT.decode(signedPayload);
    verify(jwt);
    String payload = new String(Base64.getDecoder().decode(jwt.getPayload()));
    return objectMapper.readValue(payload, ResponseBodyV2DecodedPayload.class);
  }

  @Override
  public JWSRenewalInfoDecodedPayload decodeRenewalInfo(String signedRenewalInfo)
      throws JsonProcessingException, CertificateException {

    DecodedJWT jwt = JWT.decode(signedRenewalInfo);
    verify(jwt);
    String payload = new String(Base64.getDecoder().decode(jwt.getPayload()));
    return objectMapper.readValue(payload, JWSRenewalInfoDecodedPayload.class);
  }

  @Override
  public JWSTransactionDecodedPayload decodeTransaction(String signedTransaction)
      throws JsonProcessingException, CertificateException {

    DecodedJWT jwt = JWT.decode(signedTransaction);
    verify(jwt);
    String payload = new String(Base64.getDecoder().decode(jwt.getPayload()));
    return objectMapper.readValue(payload, JWSTransactionDecodedPayload.class);
  }
}
