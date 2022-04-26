package com.github.zhongzichang.springiap.receipt;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.github.zhongzichang.springiap.ReceiptVerifier;
import org.apache.logging.log4j.Logger;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

import static org.apache.logging.log4j.LogManager.getLogger;

public class ReceiptVerifierImpl implements ReceiptVerifier {

  private static final String URL_SANDBOX = "https://sandbox.itunes.apple.com/verifyReceipt";
  private static final String URL_VERIFY = "https://buy.itunes.apple.com/verifyReceipt";
  private static final Logger LOGGER = getLogger(ReceiptVerifierImpl.class);

  private static final ObjectMapper objectMapper = new ObjectMapper();

  static {
    objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.KEBAB_CASE);
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }

  @Override
  public ReceiptVerifyResult verify(String receiptData, String password, boolean sandbox) throws IOException {
    byte[] bytes = Base64.getUrlDecoder().decode(receiptData);
    return verify(bytes, password, sandbox);
  }

  @Override
  public ReceiptVerifyResult verify(byte[] receiptData, String password, boolean sandbox) throws IOException {
    ReceiptVerifyRequest verifyRequest = new ReceiptVerifyRequest();
    verifyRequest.setExcludeOldTransactions(true);
    verifyRequest.setReceiptData(receiptData);
    if (password != null && !password.isEmpty()) verifyRequest.setPassword(password);
    String url = sandbox ? URL_SANDBOX : URL_VERIFY;
    String responseBody = httpPost(url, objectMapper.writeValueAsString(verifyRequest));
    return objectMapper.readValue(responseBody, ReceiptVerifyResult.class);
  }

  private String httpPost(String url, String requestBody) throws IOException {

    LOGGER.info("url: {}, requestBody: {}", url, requestBody);
    URL u = new URL(url);
    HttpURLConnection con = (HttpURLConnection) u.openConnection();

    con.setRequestMethod("POST");
    con.setRequestProperty("Content-Type", "application/json");
    con.setRequestProperty("Accept", "application/json");

    HttpURLConnection.setFollowRedirects(true);
    con.setInstanceFollowRedirects(false);
    con.setDoOutput(true);

    OutputStream out = con.getOutputStream();
    out.write(requestBody.getBytes());

    InputStream inputStream = con.getInputStream();
    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

    StringBuilder response = new StringBuilder();
    String responseSingle = null;

    while ((responseSingle = reader.readLine()) != null) {
      response.append(responseSingle);
    }

    String responseBody = response.toString();
    LOGGER.info("responseBody: {}", responseBody);

    return responseBody;
  }
}
