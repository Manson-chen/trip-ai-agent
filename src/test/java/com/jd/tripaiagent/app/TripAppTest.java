package com.jd.tripaiagent.app;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TripAppTest {

    @Resource
    private TripApp tripApp;

    @Test
    void doChat() {
        String chatId = UUID.randomUUID().toString();
        // 第一轮
        String message = "你好，我要去旅游";
        String answer = tripApp.doChat(message, chatId);
        Assertions.assertNotNull(answer);
        // 第二轮
        message = "我要去上海旅游";
        answer = tripApp.doChat(message, chatId);
        Assertions.assertNotNull(answer);
        // 第三轮
        message = "我去哪旅游来着？刚跟你说过，帮我回忆一下";
        answer = tripApp.doChat(message, chatId);
        Assertions.assertNotNull(answer);
    }

    @Test
    void doChatWithReport() {
        String chatId = UUID.randomUUID().toString();
        // 第一轮
        String message = "你好，我是旅行小白，我想去上海旅行，但我不知道该怎么做";
        TripApp.TripReport tripReport = tripApp.doChatWithReport(message, chatId);
        Assertions.assertNotNull(tripReport);
    }

    @Test
    void doChatWithRag() {
        String chatId = UUID.randomUUID().toString();
        String message = "我第一次去国外旅游，怎么办？";
        String answer = tripApp.doChatWithRag(message, chatId);
        Assertions.assertNotNull(answer);
    }
}