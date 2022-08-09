package rpc.transport.util;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rpc.transport.enumeration.RpcError;
import rpc.transport.exception.RpcException;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class NacosUtil {
    private static final Logger logger = LoggerFactory.getLogger(NacosUtil.class);
    private static final NamingService namingservice;
    private static final Set<String> serviceNames=new HashSet<>();
    private static InetSocketAddress address;
    private static String SERVER_ADDR="127.0.0.1:8848";
    static{
        namingservice=getNacosNamingService();
    }
    public static NamingService getNacosNamingService(){
        try{
            return NamingFactory.createNamingService(SERVER_ADDR);
        }catch(NacosException e){
            logger.info("连接到nacos出错",e);
            throw new RpcException(RpcError.FAILED_TO_CONNECT_TO_SERVICE_REGISTRY);
        }
    }
    public static void registerService(String serviceName,InetSocketAddress address) throws NacosException {
        namingservice.registerInstance(serviceName, address.getHostName(), address.getPort());
        NacosUtil.address=address;
        serviceNames.add(serviceName);
    }
    public static List<Instance> getAllInstance(String servicename) throws NacosException {
        return namingservice.getAllInstances(servicename);
    }
    public static void clearRegistry(){
        if(serviceNames.isEmpty()&&address!=null){
            String host= address.getHostName();
            int port= address.getPort();
            Iterator<String> iterator= serviceNames.iterator();
            while(iterator.hasNext()){
                String serviceName= iterator.next();
                try{
                    namingservice.deregisterInstance(serviceName,host,port);
                }catch(NacosException e){
                    logger.info("注销服务 {} 失败", serviceName, e);
                }
            }
        }

    }
}

