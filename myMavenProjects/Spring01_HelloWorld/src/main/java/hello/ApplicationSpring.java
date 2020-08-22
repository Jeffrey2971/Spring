package hello;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
public class ApplicationSpring {
    public static void main(String[] args) {
        System.out.println("applicationSpring");
//        // 创建消息打印机对象
//        MessagePrinter printer = new MessagePrinter();
//        // 消息服务对象
//        MessageService service = new MessageService();
//
//        // 设置打印机对象的Service属性
//        printer.setMessageService(service);
//
//        // 打印
//        printer.printMessage();

        // 初始化spring
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationSpring.class);
    }
}
