package rpc.registry;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.pojo.Instance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rpc.loadbalancer.LoadBalancer;
import rpc.loadbalancer.RandomLoadBalancer;
import rpc.transport.enumeration.RpcError;
import rpc.transport.exception.RpcException;
import rpc.transport.util.NacosUtil;

import java.net.InetSocketAddress;
import java.util.List;

public class NacosServiceDiscovery implements  ServiceDiscovery{
    private static final Logger logger = LoggerFactory.getLogger(NacosServiceDiscovery.class);
    private final LoadBalancer loadbalancer;
    public NacosServiceDiscovery(LoadBalancer loadBalancer){
        if(loadBalancer==null){
            this.loadbalancer=new RandomLoadBalancer();
        }else{
            this.loadbalancer=loadBalancer;
        }

    }

    @Override
    public InetSocketAddress lookupService(String serviceName) {
        try {
            List<Instance> instances = NacosUtil.getAllInstance(serviceName);
            if(instances.size() == 0) {
                logger.error("找不到对应的服务: " + serviceName);
                throw new RpcException(RpcError.SERVICE_NOT_FOUND);
            }
            Instance instance = loadbalancer.select(instances);
            return new InetSocketAddress(instance.getIp(), instance.getPort());
        } catch (NacosException e) {
            logger.error("获取服务时有错误发生:", e);
        }
        return null;
    }
}
