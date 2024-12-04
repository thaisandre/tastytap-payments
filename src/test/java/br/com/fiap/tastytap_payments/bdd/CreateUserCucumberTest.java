package br.com.fiap.tastytap_payments.bdd;

import br.com.fiap.tastytap_payments.domain.Payment;
import io.cucumber.java.en.*;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;

import java.math.BigDecimal;

public class CreateUserCucumberTest {


    private Long transactionId;
    private String qrCodeUrl;
    private long externalReference;
    private BigDecimal amount;
    private Payment payment;

    @Given("A payment request with transactionId = {long}, qrCodeUrl = {string} and externalReference = {long}, amount = {string}")
    public void aPaymentRequestWithTransactionIdQrCodeUrlAndExternalReferenceAmount(long transactionId, String qrCodeUrl, long externalReference, String amount) {
        this.transactionId = transactionId;
        this.qrCodeUrl = qrCodeUrl;
        this.externalReference = externalReference;
        this.amount = new BigDecimal(amount);
    }

    @When("create the object")
    public void createTheObject() {
        this.payment = new Payment(transactionId, qrCodeUrl, externalReference, amount);
    }

    @Then("should create the user correctly")
    public void shouldCreateTheUserCorrectly() {
        Assertions.assertNotNull(this.payment);
    }
}

@RunWith(Cucumber.class)
@CucumberOptions(plugin = "pretty", features = "src/test/java/resources/features")
class CreatePaymentRunnerTest {
}
