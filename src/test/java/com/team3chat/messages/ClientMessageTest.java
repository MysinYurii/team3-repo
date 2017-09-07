package com.team3chat.messages;

import com.team3chat.SysoutCaptureAndAssertionAbility;
import com.team3chat.client.ChatClientRealisation;
import com.team3chat.client.parser.CommandParser;
import com.team3chat.client.ui.CommandPrinter;
import com.team3chat.client.ui.ConsolePrinterReader;
import com.team3chat.client.ui.UserInputHandler;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;

import java.io.ByteArrayInputStream;
import java.io.PrintStream;

import static org.mockito.Mockito.*;

import static org.mockito.Mockito.times;
import static org.mockito.MockitoAnnotations.initMocks;

public class ClientMessageTest implements SysoutCaptureAndAssertionAbility {
}