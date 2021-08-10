package org.ilmostro.start.service.grpc;

import io.grpc.BindableService;
import io.grpc.ServerBuilder;
import io.grpc.ServerServiceDefinition;
import org.ilmostro.start.grpc.Helloworld;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class GrpcApplication implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ServerBuilder.forPort(8081)
                .addService((BindableService) () -> ServerServiceDefinition.builder(Helloworld.getDescriptor().getName()).build()).build().start();
    }
}
