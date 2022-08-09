package rpc.hook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rpc.transport.util.NacosUtil;
import rpc.factory.ThreadPoolFactory;

public class ShutdownHook {
    private static final Logger logger = LoggerFactory.getLogger(ShutdownHook.class);
    private static final ShutdownHook shutdownhook=new ShutdownHook();
    public static ShutdownHook getShutdownHook(){
        return shutdownhook;
    }




    public void addClearAllHook(){
        logger.info("关闭后将注销所有服务");
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            NacosUtil.clearRegistry();
            ThreadPoolFactory.shutDownAll();
                })

        );
    }


}
