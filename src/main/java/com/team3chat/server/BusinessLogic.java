package com.team3chat.server;

import com.team3chat.messages.SendMessageCommand;
import com.team3chat.messages.ShowHistoryCommand;

class BusinessLogic {
    private HistoryDealer historyDealer;
    private String formattedMessage;

    BusinessLogic(HistoryDealer historyDealer) {
        this.historyDealer = historyDealer;
    }

    public void receiveCommand(Object command) {
        if (command instanceof SendMessageCommand) {
            applyCommand((SendMessageCommand) command);
        }
    }

    public String getFormattedMessage() {
        return formattedMessage;
    }

    private void applyCommand(SendMessageCommand command) {
        if (command != null) {
            formattedMessage = historyDealer.saveHistory(command.getMessage());
        }
    }

    private void applyCommand(ShowHistoryCommand command) {
        if (command != null) {
            historyDealer.readHistory();
        }
    }
}