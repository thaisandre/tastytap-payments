# tastytap-payment
Repositório da aplicação de pagamentos - Software Architecture da FIAP.

## fluxo:
```mermaid
sequenceDiagram
    external-client->>tastytap-payments: POST /create
    activate tastytap-payments
    tastytap-payments->>payment-provider: POST /create
    payment-provider-->>tastytap-payments: CREATED
    tastytap-payments-->>external-client: CREATED
    deactivate tastytap-payments
    payment-provider->>tastytap-payments: POST /webhook STATUS
    activate tastytap-payments
    tastytap-payments->>tastytap-payments: update payment status
    tastytap-payments->>external-client: POST /webhook STATUS
    deactivate tastytap-payments
    external-client->>external-client: update service status
    external-client->>tastytap-payments: GET /payments
    activate tastytap-payments
    tastytap-payments-->>external-client: DETAILS
    deactivate tastytap-payments
```

----
### grupo:
- [Gabriel Ronei de Oliveira Paulo](https://github.com/gabrielronei) - RM355521
- [Thais Thomazini André](https://github.com/thaisandre) - RM355319