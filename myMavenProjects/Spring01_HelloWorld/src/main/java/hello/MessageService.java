package hello;

import org.springframework.stereotype.Component;

/**
 * 打印服务
 */

@Component
public class MessageService {

    public MessageService() {
        System.out.println("MessageService... ...");
    }

    /**
     * 执行打印功能
     * @return String
     */
    public String getMessage(){
        return "HelloWorld";
    }
}
