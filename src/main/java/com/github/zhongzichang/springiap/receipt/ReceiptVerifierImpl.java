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

public class ReceiptVerifierImpl implements ReceiptVerifier {

  private static final String URL_SANDBOX = "https://sandbox.itunes.apple.com/verifyReceipt";
  private static final String URL_VERIFY = "https://buy.itunes.apple.com/verifyReceipt";

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

    URL u;
    HttpURLConnection con = null;
    OutputStream output = null;
    InputStream input = null;
    BufferedReader reader = null;
    String responseBody;

    try {

      u = new URL(url);
      con = (HttpURLConnection) u.openConnection();

      con.setRequestMethod("POST");
      con.setRequestProperty("Content-Type", "application/json");
      con.setRequestProperty("Accept", "application/json");

      HttpURLConnection.setFollowRedirects(true);
      con.setInstanceFollowRedirects(false);
      con.setDoOutput(true);

      output = con.getOutputStream();
      output.write(requestBody.getBytes());

      input = con.getInputStream();
      reader = new BufferedReader(new InputStreamReader(input));

      StringBuilder response = new StringBuilder();
      String responseSingle;

      while ((responseSingle = reader.readLine()) != null) {
        response.append(responseSingle);
      }

      responseBody = response.toString();

    } finally{

      if(reader != null){
        reader.close();
      }

      if(input != null){
        input.close();
      }

      if(output != null){
        output.close();
      }

      if(con != null){
        con.disconnect();
      }

    }

    return responseBody;
  }
}
