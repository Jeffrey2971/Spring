package jdbc;

import java.sql.*;

/**
 * 程序的耦合
 *      耦合：可以理解为程序间的依赖关系
 *          包括：
 *
 *          fd
 */


public class Demo1 {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        // 注册驱动
//        DriverManager.registerDriver(new com.mysql.jdbc.Driver());
//        Class.forName("com.mysql.jdbc.Driver");
        // 获取链接
         Connection connection = DriverManager.getConnection("jdbc:mysql:///eesy?characterEncoding=utf-8", "root", "664490254");
        // 获取操作数据的预处对象
        PreparedStatement pstm = connection.prepareStatement("select * from account");
        // 执行sql得到结果集
        ResultSet rs = pstm.executeQuery();
        // 遍历结果集
        while (rs.next()){
            System.out.println(rs.getString("name"));
        }
        // 释放资源
        rs.close();
        pstm.close();
        connection.close();
    }
}
