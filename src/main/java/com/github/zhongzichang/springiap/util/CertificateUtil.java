package com.github.zhongzichang.springiap.util;

import java.io.ByteArrayInputStream;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;

public class CertificateUtil {

  public static PublicKey x5c0ToKey(String x5c0) throws CertificateException {
    byte[] x5c0Bytes = Base64.getDecoder().decode(x5c0);
      CertificateFactory factory = CertificateFactory.getInstance("X.509");
      X509Certificate certificate =
          (X509Certificate) factory.generateCertificate(new ByteArrayInputStream(x5c0Bytes));
      return certificate.getPublicKey();
  }
}
