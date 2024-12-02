package br.com.fiap.tastytap_payments.application.usecase.create;

public record QRCodeView(Long transactionId, String qrCodeUrl) {}
