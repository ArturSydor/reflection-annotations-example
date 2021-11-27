package com.learning.basic.orm;

import com.learning.basic.orm.entity.TransactionHistory;

import java.math.BigDecimal;

public class Application {
    public static void main(String[] args) throws IllegalAccessException {
        var bob = new TransactionHistory(12324, "Bob", "Credit", BigDecimal.valueOf(1342));
        var rob = new TransactionHistory(12325, "Rob", "Credit", BigDecimal.valueOf(3362));
        var mike = new TransactionHistory(12326, "Mike", "Loan", BigDecimal.valueOf(359));

        Hibernate<TransactionHistory> hibernate = Hibernate.getDbManager();

        SchemaCreationUtility.initSchema(Application.class.getPackageName(), hibernate);

        hibernate.save(bob);
    }
}
