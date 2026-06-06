package com.banquito.core.admin.infrastructure.grpc;

import com.banquito.core.admin.infrastructure.grpc.generated.AdminCatalogServiceGrpc;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AdminGrpcServer {
    private static final Logger log = LoggerFactory.getLogger(AdminGrpcServer.class);
    private final AdminCatalogGrpcService adminCatalogGrpcService;
    private final int port;
    private Server server;

    public AdminGrpcServer(AdminCatalogGrpcService adminCatalogGrpcService,
                           @Value("${banquito.grpc.server.port:9093}") int port) {
        this.adminCatalogGrpcService = adminCatalogGrpcService;
        this.port = port;
    }

    @PostConstruct
    public void start() throws Exception {
        this.server = ServerBuilder.forPort(port).addService(adminCatalogGrpcService).build().start();
        log.info("Admin gRPC server started on port {}", port);
    }

    @PreDestroy
    public void stop() {
        if (server != null) {
            server.shutdown();
            log.info("Admin gRPC server stopped");
        }
    }
}
