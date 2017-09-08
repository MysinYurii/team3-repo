package com.team3chat.server;

import com.team3chat.exceptions.SavingHistoryException;

class BusinessLogic {
    private HistoryDealer historyDealer;
    private String formattedMessage;

    BusinessLogic(HistoryDealer historyDealer) {
        this.historyDealer = historyDealer;
    }

    public void receiveCommand(Object command) throws SavingHistoryException {
        historyDealer.readHistory();
    }

    public String getFormattedMessage() {
        return formattedMessage;
    }
}