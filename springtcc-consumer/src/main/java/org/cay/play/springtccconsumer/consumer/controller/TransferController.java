package org.cay.play.springtccconsumer.consumer.controller;

import org.bytesoft.compensable.Compensable;
import org.cay.play.springtccconsumer.consumer.dao.TransferDao;
import org.cay.play.springtccconsumer.consumer.service.ITransferService;
import org.cay.play.springtccconsumer.feign.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@Compensable(
		interfaceClass = ITransferService.class,
		cancellableKey = "transferServiceCancel",
		confirmableKey = "transferServiceConfirm"
)
@RestController
public class TransferController implements ITransferService {
	@Autowired
	private TransferDao transferDao;

	@Autowired
	private IAccountService acctService;

	@ResponseBody
	@RequestMapping(value = "/transfer", method = RequestMethod.POST)
	@Transactional
	public void transfer(@RequestParam String sourceAcctId, @RequestParam String targetAcctId, @RequestParam double amount) {
		this.acctService.decreaseAmount(sourceAcctId, amount);
		this.increaseAmount(targetAcctId, amount);
	}

	private void increaseAmount(String acctId, double amount) {
		this.transferDao.increaseAmount(acctId, amount);
		throw new IllegalStateException("error");
//		System.out.printf("exec increase: acct= %s, amount= %7.2f%n", acctId, amount);
	}

}
