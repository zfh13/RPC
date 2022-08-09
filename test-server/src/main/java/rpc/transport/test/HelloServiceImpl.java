package rpc.transport.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rpc.annotation.Service;
import rpc.transport.api.HelloObject;
import rpc.transport.api.HelloService;
@Service
public class HelloServiceImpl implements HelloService {
    private static final Logger logger = LoggerFactory.getLogger(HelloServiceImpl.class);
    public String hello(HelloObject object) {
        logger.info("接收到：{}", object.getMessage());
         return "这是Impl1方法";
    }
}
