package com.pinkdumbell.cocobob.common;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SlackService {
    @Value(value = "${slack.token}")
    private String token;
    @Value(value = "${slack.channel.monitor}")
    private String channel;

    public void postSlackMessage(String message) {
        try {
            MethodsClient methodsClient = Slack.getInstance().methods(token);
            ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                    .channel(channel)
                    .text(message)
                    .build();
            methodsClient.chatPostMessage(request);
        } catch (SlackApiException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
