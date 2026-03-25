package com.jd.tripaiagent.advisor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.advisor.api.*;
import org.springframework.ai.chat.model.MessageAggregator;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义 Re2 Advisor
 * 可提高大型语言模型的推理能力
 */
@Slf4j
public class ReReadingAdvisor implements CallAroundAdvisor, StreamAroundAdvisor {
    private AdvisedRequest before(AdvisedRequest request) {
        Map<String, Object> advisedRequestParams = new HashMap<>(request.userParams());
        advisedRequestParams.put("re2_input_query", request.userText());
        AdvisedRequest reReadingRequest = AdvisedRequest.from(request)
                .userText("""
                        {re2_input_query}
                        Read the question again: {re2_input_query}
                        """)
                .userParams(advisedRequestParams)
                .build();
        return reReadingRequest;
    }

    @Override
    public AdvisedResponse aroundCall(AdvisedRequest advisedRequest, CallAroundAdvisorChain chain) {
        advisedRequest = this.before(advisedRequest);
        return chain.nextAroundCall(advisedRequest);
    }

    @Override
    public Flux<AdvisedResponse> aroundStream(AdvisedRequest advisedRequest, StreamAroundAdvisorChain chain) {
        advisedRequest = this.before(advisedRequest);
        return chain.nextAroundStream(advisedRequest);
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
