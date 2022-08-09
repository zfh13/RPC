package rpc.transport.test;

import rpc.annotation.Service;
import rpc.transport.api.ByeService;
@Service
public class ByeServiceImpl implements ByeService {
    public String bye(String name) {
        return "bye, " + name;
    }
}
