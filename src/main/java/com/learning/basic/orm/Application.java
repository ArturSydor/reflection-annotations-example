package com.learning.basic.orm;

import com.learning.basic.orm.entity.TransactionHistory;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Slf4j
public class Application {
    public static void main(String[] args) throws IllegalAccessException {
        var bob = new TransactionHistory(12324, "Bob", "Credit", BigDecimal.valueOf(1342));
        var rob = new TransactionHistory(12325, "Rob", "Credit", BigDecimal.valueOf(3362));
        var mike = new TransactionHistory(12326, "Mike", "Loan", BigDecimal.valueOf(359));

        Hibernate<TransactionHistory, Integer> hibernate = Hibernate.getDbManager();

        SchemaCreationUtility.initSchema(Application.class.getPackageName(), hibernate);

        hibernate.save(bob);
        hibernate.save(rob);
        hibernate.save(mike);

        int id = 3;
        log.info("findById {}", id);
        var result = hibernate.findById(id, TransactionHistory.class);
        if(result.isPresent()) {
            log.info("Result: {}", result.get());
        } else {
            log.error("Transaction with ID={} not found", id);
        }
    }
}

