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
        if (command != null) {
            if (command instanceof SendMessageCommand) {
                SendMessageCommand sendMessageCommand = (SendMessageCommand) command;
                formattedMessage = historyDealer.saveHistory(sendMessageCommand.getMessage());
            } else if (command instanceof ShowHistoryCommand) {
                historyDealer.readHistory();
            }
        }
    }

    public String getFormattedMessage() {
        return formattedMessage;
    }
}