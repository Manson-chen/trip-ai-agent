# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

**trip-ai-agent** is a Spring Boot application that integrates with Alibaba Cloud DashScope (Lingji AI) to provide AI capabilities. The project serves as a foundation for building AI-powered agent services with multi-modal conversation support.

**Key Stack:**
- Spring Boot 3.5.12 with Java 21
- DashScope SDK 2.19.1 (Alibaba Cloud AI)
- Knife4j + SpringDoc OpenAPI (Swagger documentation)
- Hutool 5.8.37 (utility library)
- Lombok (boilerplate reduction)

**Server Configuration:**
- Port: 8123
- Context path: /api
- API docs available at: `http://localhost:8123/api/swagger-ui.html`

## Common Development Commands

### Build
```bash
mvn clean package
```

### Run Application
```bash
mvn spring-boot:run
```

### Run Tests
```bash
mvn test
```

### Run a Single Test
```bash
mvn test -Dtest=TripAiAgentApplicationTests
```

### Verify/Compile Only (No Test)
```bash
mvn clean compile
```

### Check Dependencies
```bash
mvn dependency:tree
```

## Project Architecture

### High-Level Structure

```
com.jd.tripaiagent/
├── TripAiAgentApplication              # Spring Boot entry point
├── controller/
│   └── HealthController                # Basic health check endpoint
└── demo/invoke/
    ├── HttpAiInvoke                    # HTTP-based API integration example
    ├── SdkAiInvoke                     # SDK-based AI invocation example
    └── TestApiKey                      # API credentials (demo utilities)
```

### Key Components

**Controllers:**
- `HealthController` - Simple health check at `/health` endpoint for deployment verification

**Demo Modules:**
- `HttpAiInvoke` - Demonstrates direct HTTP requests to DashScope API using Hutool
- `SdkAiInvoke` - Demonstrates using DashScope SDK for multi-modal conversation (text + images)
- These are standalone examples and can be run independently or migrated into controller/service layers

### Typical Development Workflow

1. **API Endpoints**: Add new controllers in `com.jd.tripaiagent.controller` package
2. **AI Integration**: Use DashScope SDK (imported in pom.xml) or HttpRequest patterns from demo classes
3. **Configuration**: Update `application.yml` for server port, context paths, logging levels
4. **API Docs**: New controller endpoints are automatically documented via Knife4j/Swagger at `/swagger-ui.html`

### Important Notes

- **API Keys**: The demo classes reference `TestApiKey.API_KEY` - never commit real keys to the repository
- **Multi-modal Support**: Use `MultiModalConversation` from DashScope SDK for handling image + text inputs
- **Context Path**: All endpoints are served under `/api` prefix (configured in application.yml)
- **Knife4j Configuration**: Currently scans `com.jd.tripaiagent.controller` package for API endpoints - update `packages-to-scan` in application.yml when adding new controller packages
