package org.cay.play.springtccconsumer.consumer.service.impl;

import org.cay.play.springtccconsumer.consumer.service.ITransferService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("transferServiceConfirm")
public class TransferServiceConfirm implements ITransferService {
    @Transactional
    public void transfer(String sourceAcctId, String targetAcctId, double amount) {
        System.out.println("confirm !");
//        throw new IllegalStateException("error");
    }
}
