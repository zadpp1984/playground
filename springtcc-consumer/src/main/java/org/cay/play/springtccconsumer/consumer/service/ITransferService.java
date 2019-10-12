package org.cay.play.springtccconsumer.consumer.service;

public interface ITransferService {

	public void transfer(String sourceAcctId, String targetAcctId, double amount);

}
