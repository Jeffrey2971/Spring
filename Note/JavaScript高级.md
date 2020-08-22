# 今日内容
- JavaScript
    - ECMAScript
    - BOM
    - DOM
        - 事件

## DOM简单学习：为了满足案例要求
- 功能：控制HTML文档的内容
- 代码：获取页面的标签(元素)对象 Element
    - document.getElementById("id值")：通过元素的id获取元素对象
- 操作element对象：
    - 设置属性值
        - 需明确获取的对象是哪一个
        - 查看API文档，找其中有哪些属性可以设置
    - 修改标签体内容
        - 属性：innerHTML
        

## 事件简单学习
- 功能：某些组件被执行了某些操作后，触发某些代码的执行。
    - 造句：xxx被xxx，我就xxx
         - 我方水晶被摧毁后，我就责备队友
         - 敌方水晶被摧毁后，我就夸奖自己
    - 绑定事件
        - 直接在HTML标签上，指定事件的属性(操作)，属性值就是JS代码
            - 事件：onclick 单击事件
        
        - 通过JS获取元素对象，指定事件属性，设置一个函数

# BOM
- 概念：Browser Object Model 浏览器对象模型
    - 将浏览器的各个组成部分封装成为对象
- 组成
    - window：窗口对象
    - navigator：浏览器对象
    - screen：显示器屏幕对象
    - history：历史记录对象
    - location：地址栏对象
    
- window窗口对象    
    - 创建
        - 方法
            - 与弹出窗口有关：
                - alert() 显示带有一段消息和一个确认按钮的警告框
                - confirm() 显示带有一段信息以及确认按钮和取消按钮的对话框
                    - 如果用户点击确定按钮，则方法返回true，如点击取消按钮，则返回false
                - prompt() 显示可提示用户输入的对话框
                    - 返回值：获取用户输入的值
            - 与打开和关闭有关的方法
                - open()：打开一个新的浏览器窗口
                    - 返回一个新的window对象
                - window.close()：关闭浏览器窗口
                    - 谁调用我，我关谁
            - 定时器有关的方法
                - setTimeout()：在指定的毫秒数之后调用函数或计算表达式，只执行一次
                    - 参数
                        - JS代码或方法对象
                        - 毫秒值
                    - 返回值：唯一标示，用于取消定时器
                - clearTimeout()：取消由setTimeout()方法设置的timeout

                - setInterval()：按照指定的周期(以毫秒计)来调用函数或表达式
                - clearInterval()：取消由setInterval()设置的timeout
        - 属性
            - 获取其他BOM对象
                - history
                - location
                - navigator
                - screen
            - 获取DOM对象
                - document
        - 特点
            - window对象不需要创建，可以直接使用 window使用。 window.方法名()
            - window引用可以省略。 方法名();

- location地址栏对象
    - 创建(获取)
        - window.location
        - location
    - 方法
        - reload() 重新加载当前文档。刷新
    - 属性
        - href() 设置或返回完整的url
        
- history历史记录页面
    - 创建(获取)
        - window.history
        - history
    - 方法
        - back() 加载history列表中的前一个url
        - forward() 列表中的下一个url
        - go() 加载history列表中的某个具体页面
    - 属性
        - length 返回当前窗口历史列表中的url数量

## DOM
- 概念：Document Object Model 文档对象模型
    - 将标记语言文档的各个组成部分，封装为对象，可以使用这些对象，对标记语言文档CRUD(增删改查)的动态操作
- W3C DOM 被标准分为3个不同的部分
    - 核心 DOM：针对任何结构化文档的标准模型
        - Document：文档对象
        - Element：元素对象
        - Attribute：属性对象
        - Text：文本对象
        - Comment：注释对象
        - Node：节点对象，其他五个的父对象
    - XML DOM：针对XML文档的标准模型
    - HTML DOM：针对HTML文档的标准模型
    
- 核心DOM模型：
    - Document：文档对象
        - 创建(获取)：在HTML DOM模型中，可以使用window对象来获取
             - window.document
             - Document
        - 方法
            - 获取Element对象
                - getElementById() 根据ID属性值获取元素对象，ID属性值一般唯一
                - getElementsByTagName() 根据元素的名称获取元素对象们，返回值是一个数组
                - getElementsByClassName() 根据class的属性值获取元素对象们，返回值是一个数组
                - getElementsByName() 根据name属性值获取元素对象们，返回值是一个数组
            - 创建其他DOM对象
                - createAttribute(name)
                - createComment()
                - createElement()
                - createTextNode()
        - 属性
    - Element：元素对象
        - 获取/创建：通过Document来获取和创建
        - 方法
            - removeAttribute()：删除属性
            - setAttribute()：设置属性
    - Node：节点对象，其他五个的父对象
        - 特点：所有DOM对象都可以被认为是一个节点
        - 方法
            - crud dom 树
                - appendChild() 向节点的子节点列表的末尾添加新的子节点
                - removeChild() 删除(并返回)当前节点的指定子节点
                - replaceChild() 用新节点替换一个子节点
        - 属性
            - parentNode() 返回节点的父节点
        
    
- HTML DOM：针对HTML文档的标准模型
    - 标签体的设置和获取：innerHTML
    
    - 使用HTML元素对象的属性
    
        - 控制元素样式
            - 使用元素的style属性设置
            
        - 提前定义好类选择器的样式，通过元素的className属性来设置其class属性值
    
## 事件监听机制
- 概念：某些组件被执行了某些操作后，出发某些代码的执行。
    - 事件：某些操作，如：单击，双击，键盘按下了，鼠标移动了等
    - 事件源：组件，如：按钮，文本输入框等
    - 监听器：代码
    - 注册监听：将事件，事件源，监听器结合在一起，当事件源上发生了某个事件，则触发执行某个监听器代码。
    
- 常见的事件
    - 点击事件
        - onclick：单击事件
        - ondblclick：双击事件
    - 焦点事件
        - onblur：失去焦点
            - 一般用于表单验证
        - onfocus：元素获得焦点
    - 加载事件：
        - onload：一张页面或一幅图像完成加载
    - 鼠标事件
        - onmousedown：鼠标被按下
            - 定义方法时，定义一个形参，接收event对象
            - event对象的button属性可以获取鼠标哪个键被点击
                - 0：左键
                - 1：滚轮
                - 2：右键
        - onmouseup：鼠标按键被松开
        - onmousemove：鼠标被移动
        - onmouseout：鼠标从某元素移开
        - onmouseover：鼠标移动到某元素之上
    - 键盘事件
        - onkeydown：键盘某个被按下
            -
        - onkeyup：某个键盘按键被松开
        - onkeypress：某个键盘按键被按下并松开

    - 选中和改变
        - onchange：区域的内容被更改
        - onselect：文本被选中

    - 表单事件
        - onsubmit：确认按钮被点击
            - 可以阻止表单的提交
                - 需return，如果返回false则阻止提交
        - onreset：重置按钮被点击