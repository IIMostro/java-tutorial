package org.ilmostro.mysql.listener;

import lombok.extern.slf4j.Slf4j;
import org.ilmostro.mysql.service.impl.SpringMessagePublisherServiceImpl;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * @author li.bowei
 */
@Service
@Slf4j
public class SimpleTransactionalEventListener {

	@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
	public void onApplicationEventBeforeCommit(@NonNull SpringMessagePublisherServiceImpl.MessageApplicationEvent event){
		log.info("event source:{}, body:{}, {}", event.getSource(), event.getBody(), TransactionPhase.BEFORE_COMMIT);
	}

	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void onApplicationEventAfterCommit(@NonNull SpringMessagePublisherServiceImpl.MessageApplicationEvent event){
		log.info("event source:{}, body:{}, {}", event.getSource(), event.getBody(), TransactionPhase.AFTER_COMMIT);
	}

	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
	public void onApplicationEventAfterCompletion(@NonNull SpringMessagePublisherServiceImpl.MessageApplicationEvent event){
		log.info("event source:{}, body:{}, {}", event.getSource(), event.getBody(), TransactionPhase.AFTER_COMPLETION);
	}

	@TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
	public void onApplicationEventAfterRollback(@NonNull SpringMessagePublisherServiceImpl.MessageApplicationEvent event){
		log.info("event source:{}, body:{}, {}", event.getSource(), event.getBody(), TransactionPhase.AFTER_ROLLBACK);
	}
}
