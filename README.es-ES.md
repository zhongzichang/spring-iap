

# spring-iap

[![Java CI with Gradle](https://github.com/zhongzichang/spring-iap/actions/workflows/gradle.yml/badge.svg?branch=main&event=push)](https://github.com/zhongzichang/spring-iap/actions/workflows/gradle.yml)

Validación de los recibos de transacciones dentro de la aplicación y gestión de las notificaciones del servidor de la App Store.

## Instalación

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

## Uso

Envíe un recibo a la App Store para su verificación.

```java
ReceiptVerifier verifier = new ReceiptVerifierImpl();
ReceiptVerifyResult result = verifier.verify(receiptData, sharedSecret, false);
```

Monitoree los eventos de compras dentro de la aplicación en tiempo real mediante notificaciones del servidor de la App Store.

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
