# 今日内容

- XML
    - 概念
        
    - 语法
        
    - 解析

## XML
- 概念：Extensible Markup Language 可扩展标记语言
    - 可扩展：标签都是自定义的 <user>   <student> ...

- 功能
    - 存储数据
        - 配置文件
        - 在网络中传输
        

- XML与HTML的区别
    - XML的标签都是自定义的，HTML是预定义的
    - XML的语法非常严格，HTML的语法是松散的
    - XML是用于存储数据的，HTML是展示数据的

- w3c：万威望联盟
    
- 语法
    - 基本语法
        - XML文档的后缀名 .xml
        - XML第一行必须定义为文档申明
        - XML文档中有且仅有一个根标签   
        - 属性值必须使用引号(单双都可以)引起来
        - 标签必须正确关闭(可使用自闭和)
        - XML标签名称区分大小写
    - 快速入门
        - 文档申明
            - 格式：<?xml 属性列表 ?>
            - 属性列表：
                - version：版本号，必须的属性
                - encoding：编码方式，告知解析引擎当前文档使用的编码方式，默认ISO-8859-1
                - standalone：是否独立
                    - yes：不依赖其他文件
                    - no：依赖其他文件
        - 指定(结合CSS)
            - <?xml-stylesheet type="text/css" href="../CSS/a.css" ?>
        - 标签
            - 标签名称自定义
                - 规则
                    - 名称可以包含字母，数字以及其他的字符
                    - 名称不能以数字或者其他标点符号开始
                    - 名称不能以字母xml(或者XML，Xml等)开始
                    - 名称不能包含空格
                    - 注释和HTML一样
        - 属性
            - id属性值唯一
        - 文本内容
            - CDATA区：在该区域中的数据会被原样展示
            - 格式：<![CDATA[数据]]>
    - 组成部分
        
    - 约束：规定XML文档的书写规则
        - 作为框架的使用者(程序员)
            - 能够在XML中引入约束文档
            - 简单的读懂约束文档
        - 分类
            - DTD：简单的约束技术
            - Schema：一种比较复杂的约束基数
            
        - DTD
            - 引入DTD文档到XML文档中
                - 内部DTD：将约束规则定义在XML文档中
                - 外部DTD：将约束的规则定义在DTD文件中
                    - 本地：<!DOCTYPE 根标签名 SYSTEM "DTD文件的位置">
                    - 网络：<!DOCTYPE 根标签名 PUBLIC "dtd文件的名字" "DTD文件的位置url">
                    
        - Schema
            - 填写xml文档的根元素
            - 引入xsi前缀.  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            - 引入xsd文件命名空间.  xsi:schemaLocation="http://www.itcast.cn/xml  student.xsd"
            - 为每一个xsd约束声明一个前缀,作为标识  xmlns="http://www.itcast.cn/xml" 
                 <students   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                     xmlns="http://www.itcast.cn/xml" 
                     xsi:schemaLocation="http://www.itcast.cn/xml  student.xsd"
                 >
        - 解析：操作XML文档，将文档那个中的数据读取到内存中
            - 操作XML文档那个
                - 解析(读取)：将文档那个中的数据读取到内存中
                - 写入：将内存中的数据保存到XML文档中。持久化存储
            
            - 解析XML的方式
                - DOM：将标记语言文档一次性加载进内存，在内存中形成一颗DOM树
                    - 优点：操作方便，可以对文档进行CRUD的所有操作
                    - 缺点：占内存
                - SAX：逐行读取，基于事件驱动
                    - 优点：不占内存
                    - 缺点：只能读取，不能增删改
            
            - XML常见的解析器
                - JAXP：sum公司提供的解析器，支持DOM和sax两种思想
                - DOM4J：一款非常优秀的解析器
                - Jsoup：jsoup 是一款Java 的HTML解析器，可直接解析某个URL地址、HTML文本内容。它提供了一套非常省力的API，可通过DOM，CSS以及类似于jQuery的操作方法来取出和操作数据。
                - PULL：Android操作系统内置的解析器，sax方式

            - Jsoup
                - 快速入门
                    - 步骤
                        - 导入jar包
                        - 获取Document对象
                        - 获取对应的标签Element对象
                        - 获取数据
                        
                - 对象的使用
                    - Jsoup：工具类，可以解析HTML或XML文档，返回Document
                        - parse：解析HTML或XML文档，返回Document
                            - parse(File in,String charsetName)：解析HTML或XML文件
                            - parse(String html)：解析XML或HTML字符串
                            - parse(URL url, int timeoutMillis)：通过网络路径获取指定的HTML或XML的文档对象
                    - Document：文档对象，代表内存中DOM树
                        - 获取Element对象
                            - getElementById(String id)：根据ID属性值获取唯一的Element对象
                            - getElementByTag(String tagName)：根据标签名称获取元素对象的集合
                            - getElementByAttribute(String key)：根据属性名称获取元素对象集合
                            - getElementByAttributeValue(String key, String value)：根据对应的属性名和属性值获取元素对象集合
                    - Element：元素Element对象的集合，可当做ArrayList<Element>来使用
                    - Element：元素对象
                        - 获取子元素对象
                              - getElementById(String id)：根据ID属性值获取唯一的Element对象
                              - getElementByTag(String tagName)：根据标签名称获取元素对象的集合
                              - getElementByAttribute(String key)：根据属性名称获取元素对象集合
                              - getElementByAttributeValue(String key, String value)：根据对应的属性名和属性值获取元素对象集合 
                        - 获取属性值
                            - String attr(String key)：根据属性名称获取属性值
                        - 获取文本内容
                            - String text()：获取文本内容，获取纯文本内容
                            - String innerHTML()：获取标签体的所有内容(包括子标签的标签和文本字符串内容)
                    - Node：节点对象
                        - 是Document和Element的父类
                      
                - 快捷查询方式
                    - selector：选择器
                        - 使用的方法：Element select(String cssQuery)
                            - 语法：参考selector类中定义的语法
                    - xpath
                        - XPath即为XML路径语言（XML Path Language），它是一种用来确定XML文档中某部分位置的语言。
                            - 使用jsoup的xpath需要额外导入jar包
                            - 查询w3cshool参考手册，使用xpath的语法完成查询
                    