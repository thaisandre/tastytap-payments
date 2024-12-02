package br.com.fiap.tastytap_payments.infraestructure.client;

public record QRCodeResponse(Long transactionId, String qrCodeUrl) {}
