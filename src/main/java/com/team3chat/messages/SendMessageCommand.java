package com.team3chat.messages;

/**
 * Created by Java_9 on 07.09.2017.
 */
public class SendMessageCommand implements Command {
    private final String message;
    private String login;

    public SendMessageCommand(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String getRepresentation() {
        return "hello";
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
