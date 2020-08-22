package hello;

public class Application {
    public static void main(String[] args) {
        System.out.println("application");
        // 创建消息打印机对象
        MessagePrinter printer = new MessagePrinter();
        // 消息服务对象
        MessageService service = new MessageService();

        // 设置打印机对象的Service属性
        printer.setMessageService(service);

        // 打印
        printer.printMessage();
    }
}
