package rpc.transport.test;

import rpc.annotation.ServiceScan;
import rpc.serializer.CommonSerializer;
import rpc.transport.RpcServer;
import rpc.transport.netty.server.NettyServer;
import rpc.serializer.ProtobufSerializer;
import rpc.transport.api.HelloService;

@ServiceScan
public class NettyTestServer {
    public static void main(String[] args) {
        RpcServer server = new NettyServer("127.0.0.1", 9999, CommonSerializer.PROTOBUF_SERIALIZER);
        server.start();
    }
}
