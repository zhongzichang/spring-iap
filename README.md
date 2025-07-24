# spring-iap
SDK for validating in-app transaction receipts and handling notifications from app store server.

## Installation

Maven

```xml
<dependency>
  <groupId>io.github.zhongzichang</groupId>
  <artifactId>spring-iap</artifactId>
  <version>1.0.2</version>
</dependency>
```

Gradle

```groovy
implementation('io.github.zhongzichang:spring-iap:1.0.2')
```

## Usage

Send a receipt to the App Store for verification.

```java
ReceiptVerifier verifier = new ReceiptVerifierImpl();
ReceiptVerifyResult result = verifier.verify(receiptData, sharedSecret, false);
```

Monitor in-app purchase events in real time with server notifications from the App Store.

```java
@RestController
@RequestMapping("/app-store-notify")
public class NotificationController {

  private NotificationDecoder notificationDecoder = new NotificationDecoderImpl();

  @PostMapping("/v2")
  public void handle(@RequestBody ResponseBodyV2 responseBodyV2) throws CertificateException, JsonProcessingException {

    ResponseBodyV2DecodedPayload decodedPayload =
        notificationDecoder.decodePayload(responseBodyV2.getSignedPayload());

    String signedRenewalInfo = decodedPayload.getData().getSignedRenewalInfo();
    String signedTransactionInfo = decodedPayload.getData().getSignedTransactionInfo();

    JWSRenewalInfoDecodedPayload jwsRenewalInfoDecodedPayload = notificationDecoder.decodeRenewalInfo(signedRenewalInfo);
    JWSTransactionDecodedPayload jwsTransactionDecodedPayload = notificationDecoder.decodeTransaction(signedTransactionInfo);
    
  }
}
```
