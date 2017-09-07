package com.team3chat.server;

import com.team3chat.exceptions.SavingHistoryException;
import com.team3chat.messages.Command;
import com.team3chat.messages.SendMessageCommand;
import com.team3chat.messages.ShowHistoryCommand;

class BusinessLogic {
    private HistoryDealer historyDealer;
    private String formattedMessage;

    BusinessLogic(HistoryDealer historyDealer) {
        this.historyDealer = historyDealer;
    }

    public void receiveCommand(Object command) throws SavingHistoryException {
        if (command instanceof SendMessageCommand) {
            applyCommand((SendMessageCommand) command);
        }
    }

    public String getFormattedMessage() {
        return formattedMessage;
    }

    void applyCommand(Command command) {

    }

    private void applyCommand(SendMessageCommand command) throws SavingHistoryException {
        if (command != null) {
            formattedMessage = historyDealer.saveHistory(command.getMessage());
        }
    }

    private void applyCommand(ShowHistoryCommand command) throws SavingHistoryException {
        if (command != null) {
            historyDealer.readHistory();
        }
    }
}