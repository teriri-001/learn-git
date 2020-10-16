# Tomcat

### JavaWeb概念

**什么是JavaWeb**

* javaWeb即通过Java语言开发、可以通过浏览器访问的程序

* 基于请求和响应开发

---

**请求**

* 客户端给服务器发送数据，即请求：`request`



---

**响应**

* 服务器给客户端回传数据，即响应：`response`

* **请求**与**响应**是成对出现的



---

### Web资源分类

**静态资源**

* `html`  `css`  `图片`等不会改变的资源

---

**动态资源**

* `js`  `jsp`等资源



---

### 常用的web容器

**Tomcat**

* 提供对`jsp` 和 `servlet`的支持。是一种轻量级的`javaWeb`容器，免费开源

---

**其他**



---

### Tomcat介绍

##### **版本介绍**

当前常用版本：7.x/8.x

---

##### **安装服务器**

* 下载tomcat安装包
* 直接安装

---

##### **目录介绍**

* bin：可执行程序
* conf：配置文件
* lib：jar包
* logs：运行输出日志信息
* temp：运行时的临时数据，结束即消失
* webapps：存放部署的web工程，一个目录对应一个工程
* work：工作时目录，存放tomcat运行时，jsp翻译为servlet的源码，和session钝化（序列化）的目录

---

##### **启动tomcat服务器**

1. **方式一**

   * 运行`bin`目录下的`startUp.bat`文件即可

   * 浏览器输入`localhost:8080`测试

2. **命令行方式**

   * 命令行跳转到bin目录下，执行：catalina run

---

##### **停止tomcat服务器**

* 运行bin目录下的：`shutdown.bat`

---

##### 修改端口号

* 默认端口号：8080
* 修改conf目录下：->server.xml : ->port
* 重启生效

---

##### Root工程

* localhost:8080访问到的页面，默认访问名称为：index的资源

---

### 部署Web工程到Tomcat中

##### 方式一

1. 在webapps目录下新建项目目录
2. 将项目放入该目录即可
3. **他人通过ip地址访问到该项目**

---

##### 方式二

1. 进入目录：config -> catalina -> localhost 

2. 该目录下新建xml文件，一个文件代表一个项目

3. 编辑xml文件：

   ```xml
   <!--context: 表示工程上下文
   	path: 表示工程访问路径 -> localhost:8080/web03
   	docBase: 工程文件的存放地址
   -->
   <context path="/web03" docBase="C:\Users\yangyim\Desktop"/>
   ```



---

### IDEA整合Tomcat

##### 基本配置

* 路径：File | Settings | Build, Execution, Deployment | Application Servers
* 点击 `+`  选择 `tomcat server`
* 填写tomcat安装目录
* 新建项目`java enterprise`时，可查看配置成功

---

##### 新建动态的Web工程

![](C:\Users\yangyim\Pictures\snipaste\Snipaste_2020-10-16_16-43-48.jpg)



---

##### 目录介绍

```yaml
src: #存放Java源代码
 web: # 存放web工程的资源文件
  WEB-INF: #受服务器保护的目录，浏览器无法直接访问
   lib: #存放第三方jar包
   web.xml: # 整个web工程的配置部署描述文件，可以在这里配置web工程的组件，如：servlet程序，filter过滤器，listener监听器等
```

