package rpc.transport;

import rpc.serializer.CommonSerializer;
import rpc.transport.entity.RpcRequest;


public interface RpcClient {
    Object sendRequest(RpcRequest rpcRequest);
    int DEFAULT_SERIALIZER = CommonSerializer.KRYO_SERIALIZER;

}
