package hello;

import org.springframework.stereotype.Component;

/**
 * 打印机
 */
@Component
public class MessagePrinter {

    public MessagePrinter() {
        super();
        System.out.println("MessagePrinter...");
    }

    /**
     * 建立和MessageService的关联关系
     */
    private MessageService service;

    /**
     * 设置Service的值
     * @param messageService
     */
    public void setMessageService(MessageService messageService) {
        this.service = messageService;
    }

    public void printMessage(){
        System.out.println(this.service.getMessage());
    }
}
