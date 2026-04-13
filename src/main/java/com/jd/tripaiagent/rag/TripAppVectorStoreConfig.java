package com.jd.tripaiagent.rag;

import jakarta.annotation.Resource;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 旅行向导向量数据库配置（初始化基于内存的向量数据库 Bean））
 */
@Configuration
public class TripAppVectorStoreConfig {

    @Resource
    private TripAppDocumentLoader tripAppDocumentLoader;

    @Bean
    VectorStore TripAppVectorStore(EmbeddingModel dashscopeEmbeddingModel) {
        SimpleVectorStore simpleVectorStore = SimpleVectorStore
                .builder(dashscopeEmbeddingModel)
                .build();
        // 加载文档
        List<Document> documents = tripAppDocumentLoader.loadMarkDowns();
        simpleVectorStore.add(documents);
        return simpleVectorStore;
    }
}
