@FirstApplicationFeature
Feature: Create a new Payment
  Create a payment and expect ok.

Scenario: a payment
    Given A payment request with transactionId = 10, qrCodeUrl = "lavinia@gmail.com" and externalReference = 10, amount = "10"
    When create the object
    Then should create the user correctly
