## 权限分类

- 分为十个字符串，四部分
- 格式： - --- --- ---
- 第一部分
    - -表示为一个文件
    - d表示为一个文件夹
    - l表示链接
- 第二部分
    - 当前用户具有该文件的权限
    - r：read读
    - w：write写
    - x：execute执行
- 第三部分
    - 当前组内其他用户具有该文件的权限
        - r：read读
        - w：write写
        - x：execute执行
- 第四部分
    - 其他组的用户具有该文件的权限
        - r：read读
        - w：write写
        - x：execute执行
        
- 权限分别对应的数字
    - r：4
    - w：2
    - x：1
    
## 常用网络操作
- hostname：查看主机名或临时更改主机名(重启恢复)，如需永久更改需修改配置文件/etc/sysconfig/network文件
    - hostname jeffrey
- service network restart：重启网络服务
- ifconfig：查看或修改ip地址(重启后无效)，如需永久修改需修改配置文件/etc/sysconfig/network-scripts/ifcfg-eth0文件
    - ifconfig eth0 xxx.xxx.xxx.xxx 修改ip地址
    - Once po