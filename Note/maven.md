# maven项目标准

- maven Java工程目录
    - src/main/java：核心代码部分
    - src/main/resources：配置文件部分
    - src/test/java：测试代码部分
    - src/test/resources：测试配置文件
- maven Web工程目录
    - src/main/webapp：页面资源(js/css/图片等)


- 命令
    - mvn clear：清清除编译后的目录，默认是target目录
    - mvn compile：编译,将Java 源程序编译成 class 字节码文件。
    - mvn test：测试，并生成测试报告
    - mvn package：打包
    - mvn deploy

- 默认生命周期
    - compile，test，package，install，deploy
        - 每一个构建项目的命令都对应了maven底层的一个插件。
- pom.xml文件下的三类信息
    - 项目自身信息
    - 项目运行所依赖的jar包信息
    - 运行环境信息，tomcat，jdk等
        <dependency>
            <groupId>javax.servlet.jsp</groupId> 公司组织的名称
            <artifactId>jsp-api</artifactId> 项目名
            <version>2.0</version> 版本号
            <scope>provided</scope>
        </dependency>

- idea集成maven的配置
    - 