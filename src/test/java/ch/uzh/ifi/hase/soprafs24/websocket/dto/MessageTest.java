package ch.uzh.ifi.hase.soprafs24.websocket.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class MessageTest {

    @Test
    void messageConstructorAndGetMessageShouldWorkProperly() {
        String initialMessage = "Hello, this is a test message";
        Message messageObj = new Message(initialMessage);
        assertEquals(initialMessage, messageObj.getMessage(), "The getMessage should return the message set in constructor.");
    }

    @Test
    void setMessageShouldUpdateMessageProperly() {
        String initialMessage = "Initial Message";
        String newMessage = "Updated Message";
        Message messageObj = new Message(initialMessage);
        messageObj.setMessage(newMessage);
        assertEquals(newMessage, messageObj.getMessage(), "The getMessage should return the updated message after calling setMessage.");
    }
}
