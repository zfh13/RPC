package rpc.transport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rpc.transport.entity.RpcRequest;
import rpc.transport.entity.RpcResponse;
import rpc.transport.netty.client.NettyClient;
import rpc.transport.util.RpcMessageChecker;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class RpcClientProxy implements InvocationHandler {

    private static final Logger logger = LoggerFactory.getLogger(RpcClientProxy.class);

    private String host;
    private int port;
    private final RpcClient client;
    public RpcClientProxy(String host, int port, RpcClient client) {
        this.host = host;
        this.port = port;
        this.client = client;
    }

    public RpcClientProxy(RpcClient client) {
        this.client=client;
    }

 
        public <T> T getProxy(Class<T> clazz) {
            return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, this);
        }
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) {
            logger.info("调用方法: {}#{}", method.getDeclaringClass().getName(), method.getName());
            RpcRequest rpcRequest = new RpcRequest(UUID.randomUUID().toString(), method.getDeclaringClass().getName(),
                    method.getName(), args, method.getParameterTypes(), false);
            RpcResponse rpcResponse = null;
            if (client instanceof NettyClient) {
                try {
                    CompletableFuture<RpcResponse> completableFuture = (CompletableFuture<RpcResponse>) client.sendRequest(rpcRequest);
                    rpcResponse = completableFuture.get();
                } catch (Exception e) {
                    logger.error("方法调用请求发送失败", e);
                    return null;
                }
            }

            RpcMessageChecker.check(rpcRequest, rpcResponse);
            return rpcResponse.getData();
        }
    }