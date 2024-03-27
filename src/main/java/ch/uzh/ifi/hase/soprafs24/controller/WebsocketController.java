package ch.uzh.ifi.hase.soprafs24.controller;


import ch.uzh.ifi.hase.soprafs24.service.WebsocketService;
import ch.uzh.ifi.hase.soprafs24.websocket.dto.Message;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Timer;
import java.util.TimerTask;

/**
 A Class used for testing websocket messages
 */
@RestController
public class WebsocketController {
    private final WebsocketService websocketService;
    private Timer timer;
    private boolean timeRunning = false;
    private int remainingTimeInSeconds;

    WebsocketController(WebsocketService websocketService) {
        this.websocketService = websocketService;
    }

    @GetMapping("/test")
    @ResponseStatus(HttpStatus.OK)
    public void sendMessage() {
        websocketService.sendMessage("/topic/greeting", new Message("WEBSOCKET IS WORKING!!!!!!!"));
    }

    @GetMapping("/timer")
    @ResponseStatus(HttpStatus.OK)
    public void startTimer() {
        this.timer = new Timer();
        if (!timeRunning) {
            timeRunning = true;
            remainingTimeInSeconds = 10;
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    websocketService.sendMessage("/topic/timer", new Message(String.valueOf(remainingTimeInSeconds)));
                    if (remainingTimeInSeconds <= 0) {
                        timeRunning = false;
                        websocketService.sendMessage("/topic/timer", new Message("TIME IS UP"));
                        timer.cancel();
                    }
                    remainingTimeInSeconds--;
                }
            }, 0, 1000);
        }
    }
}
