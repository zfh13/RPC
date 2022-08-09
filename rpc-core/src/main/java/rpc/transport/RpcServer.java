package rpc.transport;

import rpc.serializer.CommonSerializer;

public interface RpcServer {
    int DEFAULT_SERIALIZER = CommonSerializer.KRYO_SERIALIZER;

    void start();



    <T> void publishService(T service, String serviceName);

}