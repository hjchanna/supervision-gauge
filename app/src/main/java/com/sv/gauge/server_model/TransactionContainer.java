/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.gauge.server_model;

import java.util.List;

/**
 *
 * @author mohan
 */
public class TransactionContainer {

    private Transaction transactionObject;
    private List<TransactionDetail> transactionDetailObjects;

    public TransactionContainer() {
    }

    public Transaction getTransactionObject() {
        return transactionObject;
    }

    public void setTransactionObject(Transaction transactionObject) {
        this.transactionObject = transactionObject;
    }

    public List<TransactionDetail> getTransactionDetailObjects() {
        return transactionDetailObjects;
    }

    public void setTransactionDetailObjects(List<TransactionDetail> transactionDetailObjects) {
        this.transactionDetailObjects = transactionDetailObjects;
    }

}
