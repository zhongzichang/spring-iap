package com.github.zhongzichang.springiap.receipt;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.github.zhongzichang.springiap.ReceiptVerifier;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

import static org.slf4j.LoggerFactory.getLogger;

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

  private final WebClient.RequestBodySpec bodySpec4Sandbox;
  private final WebClient.RequestBodySpec bodySpec;

  public ReceiptVerifierImpl() {
    HttpClient httpClient =
        HttpClient.create()
            .wiretap(true)
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
            .responseTimeout(Duration.ofMillis(5000))
            .doOnConnected(
                conn ->
                    conn.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                        .addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS)));

    WebClient webClient =
        WebClient.builder()
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .codecs(
                clientDefaultCodecsConfigurer -> {
                  clientDefaultCodecsConfigurer
                      .defaultCodecs()
                      .jackson2JsonEncoder(
                          new Jackson2JsonEncoder(objectMapper, MediaType.APPLICATION_JSON));
                  clientDefaultCodecsConfigurer
                      .defaultCodecs()
                      .jackson2JsonDecoder(
                          new Jackson2JsonDecoder(objectMapper, MediaType.APPLICATION_JSON));
                })
            .filter(logRequest())
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .build();

    this.bodySpec4Sandbox = webClient.post().uri(URL_SANDBOX);
    this.bodySpec = webClient.post().uri(URL_VERIFY);
  }

  private static ExchangeFilterFunction logRequest() {
    return ExchangeFilterFunction.ofRequestProcessor(
        clientRequest -> {
          LOGGER.debug("Request: {} {}", clientRequest.method(), clientRequest.url());
          clientRequest
              .headers()
              .forEach(
                  (name, values) -> values.forEach(value -> LOGGER.debug("{}={}", name, value)));
          return Mono.just(clientRequest);
        });
  }

  @Override
  public ReceiptVerifyResult verify(String receiptData, String password, boolean sandbox) {
    byte[] bytes = Base64.getUrlDecoder().decode(receiptData);
    return verify(bytes, password, sandbox);
  }

  @Override
  public ReceiptVerifyResult verify(byte[] receiptData, String password, boolean sandbox) {
    WebClient.RequestBodySpec funBodySpec = sandbox ? bodySpec4Sandbox : bodySpec;
    ReceiptVerifyRequest verifyRequest = new ReceiptVerifyRequest();
    verifyRequest.setExcludeOldTransactions(true);
    verifyRequest.setReceiptData(receiptData);
    if (password != null && !password.isEmpty()) verifyRequest.setPassword(password);
    return funBodySpec
        .body(Mono.just(verifyRequest), ReceiptVerifyRequest.class)
        .retrieve()
        .bodyToMono(ReceiptVerifyResult.class)
        .block();
  }
}
