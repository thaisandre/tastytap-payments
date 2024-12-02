package br.com.fiap.tastytap_payments.presentation.payment;

import br.com.fiap.tastytap_payments.application.usecase.create.CreatePaymentResponse;
import br.com.fiap.tastytap_payments.application.usecase.find.FindPaymentResponse;
import br.com.fiap.tastytap_payments.presentation.payment.dtos.CreatePaymentRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Payment Controller", description = "Pagamento")
public interface PaymentControllerDocs {

    @Operation(
            summary = "Busca pagamento por transactionId",
            description = "Busca pagamento por transactionId")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Erro interno do sistema", content = {@Content(schema = @Schema())})
    })
    ResponseEntity<FindPaymentResponse> find(@PathVariable Long transactionId);;

    @Operation(
            summary = "Cria um novo pagamento",
            description = "Faz o cadastro de um novo pagamento e retorna o pagamento criado em caso de sucesso",
            security = {@SecurityRequirement(name = "bearerAuth")})
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = CreatePaymentResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou incorretos", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", description = "Erro interno do sistema", content = {@Content(schema = @Schema())})
    })
    ResponseEntity<CreatePaymentResponse> create(@Valid @RequestBody CreatePaymentRequest command);
}
