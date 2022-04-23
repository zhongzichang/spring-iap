package com.github.zhongzichang.springiap.notification;

@lombok.Data
public class JWSDecodedHeader {
  private String alg;
  private String kid;
  private String[] x5c;
}
