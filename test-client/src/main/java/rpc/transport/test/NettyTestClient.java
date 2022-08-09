package rpc.transport.test;

import rpc.serializer.CommonSerializer;
import rpc.transport.api.ByeService;
import rpc.transport.netty.client.NettyClient;
import rpc.serializer.ProtobufSerializer;
import rpc.transport.api.HelloObject;
import rpc.transport.api.HelloService;
import rpc.transport.RpcClient;
import rpc.transport.RpcClientProxy;

public class NettyTestClient {
    public static void main(String[] args) {
        RpcClient client = new NettyClient(CommonSerializer.PROTOBUF_SERIALIZER);
        RpcClientProxy rpcClientProxy = new RpcClientProxy(client);
        HelloService helloService = rpcClientProxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(12, "This is a message");
        String res = helloService.hello(object);
        System.out.println(res);

        ByeService byeService = rpcClientProxy.getProxy(ByeService.class);
        System.out.println(byeService.bye("Netty"));
    }
}
