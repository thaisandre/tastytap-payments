package br.com.fiap.tastytap_payments.infraestructure.persistence;

import br.com.fiap.tastytap_payments.domain.Status;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository extends MongoRepository<PaymentEntity, UUID> {

    Optional<PaymentEntity> findByTransactionId(Long transactionId);

    @Query("{'transactionId' : ?0}")
    @Update("{'$set': {'status': ?1}}")
    Integer update(Long transactionId, Status status);
}
