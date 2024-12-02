package br.com.fiap.tastytap_payments.application.commons;

public abstract class UnitUseCase<IN> {

    public abstract void execute(IN in);
}