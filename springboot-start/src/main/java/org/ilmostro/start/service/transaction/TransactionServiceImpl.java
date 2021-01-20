package org.ilmostro.start.service.transaction;

import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

@Service
public class TransactionServiceImpl {

    private final PlatformTransactionManager platformTransactionManager;
    private final TransactionDefinition transactionDefinition;

    private final FirstService firstService;
    private final SecondService secondService;

    public TransactionServiceImpl(PlatformTransactionManager platformTransactionManager,
                                  TransactionDefinition transactionDefinition,
                                  FirstService firstService,
                                  SecondService secondService) {
        this.platformTransactionManager = platformTransactionManager;
        this.transactionDefinition = transactionDefinition;
        this.firstService = firstService;
        this.secondService = secondService;
    }

    public void executor(){
        TransactionStatus transaction = platformTransactionManager.getTransaction(transactionDefinition);
        try{
            firstService.say();
            secondService.speak();
            platformTransactionManager.commit(transaction);
        }catch (Exception ex){
            platformTransactionManager.rollback(transaction);
        }
    }
}
