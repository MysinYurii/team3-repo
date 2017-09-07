package com.team3chat.server;

import com.team3chat.messages.SendMessageCommand;
import com.team3chat.messages.ShowHistoryCommand;

class BusinessLogic {
    private HistoryDealer historyDealer;

    BusinessLogic(HistoryDealer historyDealer) {
        this.historyDealer = historyDealer;
    }

    public void applyCommand(SendMessageCommand command) {
        if (command != null) {
            historyDealer.saveHistory(command.getMessage());
        }
    }

    public void applyCommand(ShowHistoryCommand command) {
        if (command != null) {
            historyDealer.readHistory();
        }
    }
}