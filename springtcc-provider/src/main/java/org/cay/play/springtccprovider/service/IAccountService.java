package org.cay.play.springtccprovider.service;

public interface IAccountService {

	public void increaseAmount(String accountId, double amount);

	public void decreaseAmount(String accountId, double amount);

}
