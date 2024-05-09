package ch.uzh.ifi.hase.soprafs24.controller;

import ch.uzh.ifi.hase.soprafs24.websocket.dto.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.*;
import ch.uzh.ifi.hase.soprafs24.service.GameService;
import ch.uzh.ifi.hase.soprafs24.service.WebsocketService;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


@ExtendWith(SpringExtension.class)
public class WebsocketControllerTest {

        private MockMvc mockMvc;

        @Mock
        private WebsocketService websocketService;


        @InjectMocks
        private WebsocketController websocketController;

        @BeforeEach
        public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(websocketController).build();
    }


    @Test
        public void testSendMessage() throws Exception {

            mockMvc.perform(get("/test"))
                    .andExpect(status().isOk());


            verify(websocketService).sendMessage(eq("/topic/greeting"), any(Message.class));
        }

        @Test
        public void testStartTimer() throws Exception {

            mockMvc.perform(get("/timer"))
                    .andExpect(status().isOk());
            Thread.sleep(11000);


            verify(websocketService, times(12)).sendMessage(eq("/topic/timer"), any(Message.class));
        }
}
