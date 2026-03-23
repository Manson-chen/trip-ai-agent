//package com.jd.tripaiagent.demo.invoke;
//
//import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
//import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
//import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
//import com.alibaba.cloud.ai.graph.agent.ReactAgent;
//import com.alibaba.cloud.ai.graph.checkpoint.savers.MemorySaver;
//import com.alibaba.cloud.ai.graph.exception.GraphRunnerException;
//import org.springframework.ai.chat.messages.AssistantMessage;
//import org.springframework.ai.chat.model.ChatModel;
//import org.springframework.ai.chat.model.ToolContext;
//import org.springframework.ai.tool.ToolCallback;
//import org.springframework.ai.tool.function.FunctionToolCallback;
//
//import java.util.function.BiFunction;
//// 定义天气查询工具
//class WeatherTool implements BiFunction<String, ToolContext, String> {
//    @Override
//    public String apply(String city, ToolContext toolContext) {
//        return "It's always sunny in " + city + "!";
//    }
//}
///**
// * 构建一个基础的 Agent
// */
//public class AgentInvoke {
//
//    public static void main(String[] args) throws GraphRunnerException {
//        DashScopeApi dashScopeApi = DashScopeApi.builder()
//                .apiKey(TestApiKey.API_KEY)
//                .build();
//
//        ChatModel chatModel = DashScopeChatModel.builder()
//                .dashScopeApi(dashScopeApi)
//                .defaultOptions(DashScopeChatOptions.builder()
//                        // Note: model must be set when use options build.
//                        .withModel(DashScopeChatModel.DEFAULT_MODEL_NAME)
//                        .withTemperature(0.5)
//                        .withMaxToken(1000)
//                        .build())
//                .build();
//
//        ToolCallback weatherTool = FunctionToolCallback.builder("get_weather", new WeatherTool())
//                .description("Get weather for a given city")
//                .inputType(String.class)
//                .build();
//
//        // 创建 agent
//        ReactAgent agent = ReactAgent.builder()
//                .name("weather_agent")
//                .model(chatModel)
//                .tools(weatherTool)
//                .systemPrompt("You are a helpful assistant")
//                .saver(new MemorySaver())
//                .build();
//
//        // 运行 agent
//        AssistantMessage response = agent.call("what is the weather in San Francisco");
//        System.out.println(response.getText());
//    }
//}
