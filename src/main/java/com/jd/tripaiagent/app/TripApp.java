package com.jd.tripaiagent.app;

import com.jd.tripaiagent.advisor.MyLoggerAdvisor;
import com.jd.tripaiagent.advisor.ReReadingAdvisor;
import com.jd.tripaiagent.chatmemory.FileBasedChatMemory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;
import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY;

@Component
@Slf4j
public class TripApp {
    private final ChatClient chatClient;

    private static final String SYSTEM_PROMPT = "你是一个旅游专家";

    public TripApp(ChatModel dashscopeChatModel) {
        // 初始化基于内存的对话记忆
//        ChatMemory chatMemory = new InMemoryChatMemory();
        // 初始化基于文件的对话记忆
        String fileDir = System.getProperty("user.dir") + "/chat-memory";
        ChatMemory chatMemory = new FileBasedChatMemory(fileDir);
        chatClient = ChatClient.builder(dashscopeChatModel)
                .defaultSystem(SYSTEM_PROMPT)
                .defaultAdvisors(
                        new MessageChatMemoryAdvisor(chatMemory),
                        // 自定义日志 Advisor，可按需开启
                        new MyLoggerAdvisor(),
                        new ReReadingAdvisor()
                )
                .build();
    }

    public String doChat(String message, String chatId) {

        ChatResponse response = chatClient
                .prompt()
                .user(message)
                .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 3))
                .call()
                .chatResponse();
        String content = response.getResult().getOutput().getText();
        log.info("response: {}", content);
        return content;

    }

    record TripReport(String title, List<String> suggestions){
    }

    public TripReport doChatWithReport(String message, String chatId) {
        TripReport tripReport = chatClient
                .prompt()
                .system(SYSTEM_PROMPT + "每次对话都要生成旅行分析，标题为{用户名}的旅行报告，内容为建议列表")
                .user(message)
                .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10))
                .call()
                .entity(TripReport.class);
        log.info("tripReport: {}", tripReport);
        return tripReport;
    }
}
