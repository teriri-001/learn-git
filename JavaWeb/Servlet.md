# Servlet

### Servlet基础

##### 1、什么是servlet

* Servlet是JavaEE规范之一，规范就是接口
* Servlet是JavaEE三大组件之一。三大组件分别是：Servlet程序、Filter过滤器、Listener监听器。
* Servlet是运行在服务器上的一个Java小程序，**用于接收客户端发送过来的请求，并响应数据给客户端**。

---

##### 2、第一个servlet程序

```Java
/*
* 新建一个JavaEE动态Web工程
* 编写一个类去实现 Servlet 接口（少用）
* 重写 service 方法，处理请求，并相应数据
* 到 Web.xml 中去配置 Servlet 程序的访问地址
**/
public class myServlet implements Servlet{
    @override
    public void service(ServletRequest request,ServletResponse response){
        System.out.println("hello servlet!")
    }
}
```

配置web.xml

```xml
<!-- 配置Servlet程序-->
<servlet>
    <!-- 该标签用于给servlet程序取一个别名，一般与类名相同 -->
    <servlet-name>myServlet</servlet-name>
    
    <!-- 配置Servlet程序的全类名 -->
    <servlet-class>com.sinon.Servlet.myServlet</servlet-class>
</servlet>

<!-- 配置Servlet程序访问地址-->
<servlet-mapping>
    <servlet-name>myServlet</servlet-name>
    
    <!--说明：
		‘/’ ：在解析时表示的是："http://ip:port/工程路径(工程路径创建项目时已配置)"
		‘hello’ ： 在之后添加hello
	-->
    <url-pattern>/hello</url-pattern>
</servlet-mapping>
```

---

##### 3、url如何定位到servlet程序

浏览器访问网址：`http://localhost:8080/servletDemo/hello`为例

* localhost ：通过ip地址定位到具体的服务器
* 8080：通过端口号定位到具体的端口（tomcat默认**监听**8080端口）
* servletDemo：工程名定位到该端口具体的工程
* hello：资源名定位到`web.xml`配置文件中对应的`servlet-mapping`标签，从而对应到具体的资源进行访问。

---

##### 4、Servlet的生命周期

* 执行`Servlet构造器方法` —> 执行`init初始化方法` —> 执行`service方法` —> 执行`destroy销毁方法`
* 第一、二步在第一次访问创建Servlet程序时调用

---

##### 5、请求的分发处理

```java
public class myServlet implements Servlet{
    @override
    public void service(ServletRequest request,ServletResponse response){
        // 向下类型转换，ServletRequest是接口无法直接使用，取其子类
        HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
        // 获取请求方式
        String method = httpServletRequest.getMethod();
        
        // 根据请求方式处理不同的业务
        if ("GET".equals(method)){
            // 将get请求封装成一个方法调用
            HandleGet();
        } else {
            HandlePost();
        }
    public void HandleGet(){
        // 处理Get业务
    }
    public void HandlePost(){
        // 处理Post业务
    }
    }
}
```

---

##### 6、通过继承HttpServlet子类实现Servlet程序

```Java
/*
* 编写一个类去继承 httpServlet 类
* 根据业务需求重写 doGet 或 doPost 等方法
* 到web.xml中配置 servlet 程序的访问地址
* */
public class MyServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
```

---

##### 7、使用IDEA工具自动生成Servlet程序

* 包名右键  —> creat new servlet —> 添加处理逻辑 —->配置访问地址

* **在IDEA中使用Servlet**

  ```Java
  // 使用注解 @Webservlet
  // 继承HttpServlet
  @Webservlet
  public myServlet extends HttpServlet{
      protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
          // 在页面中打印输出
          resp.getWriter().println("hello!");
      }
  }
  
  ---------------------------------------------------
      
  // 在启动类添加注解 @ServletComnentScan(xxxx) 进行组件扫描
  ```

  

---

##### 8、servletConfig类

servlet程序的配置信息类

* 可以获取servlet程序配置的别名
* 获取初始化参数 `init-param`，在`web.xml`配置中的`servlet`标签下配置，可配多组键值对
* 获取`servletContext`对象

---

##### 9、servletContext上下文对象

* `servletContext`是一个接口，表示`servlet`[上下文](https://www.zhihu.com/question/26387327)
* 一个web工程，只有一个ServletContext对象实例
* `servletContext`是一个[域对象](https://www.jianshu.com/p/6c02951267d8)
* 在工程部署启动时创建，在工程停止时销毁，作用域是整个web工程

---

##### 10、servletContext对象的四个作用

* 获取`web.xml`中配置的上下文参数`context-param`

* 获取当前的工程路径

  

* 获取工程部署后在服务器硬盘上的绝对路径（资源所在路径）

  ```java
  public class MyServlet extends HttpServlet{
      @Override
      protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
          // 获取上下文对象
          ServletContext context = getServletContext();
          // 获取绝对路径，“/”对应了JavaEE工程中的web目录
          String realPath = context.getRealPath("/");
      }
  }
  ```

* 像map一样存储数据，且存在时各个servlet都能读取到值

  ```Java
  public class MyServlet extends HttpServlet{
      // 获取上下文对象
      ServletContext context = getServletContext();
      // 存数据
      context.setAttribute(key,value);
      // 取数据
      Object key = context.getAttribute("key");
  }
  ```

---

##### 11、Web中  “/”  斜线的不同意义

* “/” 斜杠如果被浏览器解析，得到的地址是：`http://ip:8080`

* “/” 斜杠如果别服务器解析，得到的地址是：`http://ip:port:工程路径`

  ```java
  // example
  servletContext.getRealPath("/")
  ```

---

##### 12、JavaEE三层架构



---

### Http协议

##### 1、什么是HTTP协议

* 客户端和服务器之间通信时，需要遵守的规则，叫做Http协议

---

##### 2、GET请求

* 请求行（第一行）

  * 请求的方式		 —> GET
  * 请求的资源路径  + [ + ‘?’ + 请求参数]
  * 请求协议的版本号    —> HTTP/1.1

* 请求头（键值对组成）

  |         key         |                             说明                             |
  | :-----------------: | :----------------------------------------------------------: |
  |     ==Accept==      |             向服务器声明客户端可以接收的数据类型             |
  | ==Accept-Language== |                 声明客户端可以接收的语言类型                 |
  |   ==User-Argent==   |                         浏览器的信息                         |
  | ==Accept-Encoding== |               声明客户端可以接收的数据压缩格式               |
  |      ==Host==       |                表示所请求的服务器的IP和端口号                |
  |   ==Connection==    | 告诉服务器如何处理请求连接：1. keep-alive(回传数据后，不要马上断开连接) ；2 .直接关闭 |

---

##### 3、Post请求

* 请求头（大部分与GET相同）
  * Referer ：请求发起时，浏览器地址栏中的地址（数据来源）
  * content-type：发送的数据的类型
  * content-length：发送数据的长度
* 请求行
* 空行
* 请求体 
  * 发送给服务器的数据

---

##### 4、响应的HTTP协议格式

* 响应行

  * 响应的协议和版本号
  * 响应的状态码
  * 响应的描述符

* 响应头（键值对组成）

  |     key      |          说明          |
  | :----------: | :--------------------: |
  |    server    |      服务器的信息      |
  | content-type |    响应体的数据类型    |
  |    allow     | 服务器支持哪些请求方法 |

* 空行

* 响应体

  * 回传给客户端的数据

---

##### 5、MIME类型说明

`mime`是HTTP中的数据类型，格式是`大类型/小类型`如：`text/html`，`image/jpeg`等



---

### Http常用封装类

##### 1、HttpServletRequest类

* 每次只要有请求进入Tomcat服务器，Tomcat服务器就会把请求过来的Http协议解析，结果放入Request对象中。然后传递到service方法（doGET 和 doPost）中。

* 通过改类对象，可以获取所有请求信息

---

##### 2、doPost请求中文乱码问题

* 当使用post请求传值为中文时，会出现乱码问题，spring boot不会出现该问题

---

##### 3、请求转发

**说明**

* 指服务器接收到请求之后，从一个服务器资源跳转到另一个资源的操作

**特点**

* 转发属于一次请求，地址栏没有变化，且共享Request域中的数据
* 可以转发到`WEB-INF`中的资源，一般该目录无法直接访问
* 不可以转发访问该工程外的资源

**示例**

* 表单提交

---

##### 4、请求重定向

**含义**

* 服务器原地址搬迁之后，浏览器访问原地址时，由原地址响应程序返回信息提示，浏览器接收信息解析后重新访问新地址得到响应。

**原地址响应信息**

* 响应行状态码：302
* 响应头`location`：新地址

**特点**

* 浏览器地址栏会发生变化
* 重定向是两次请求
* 不共享Request域中的数据
* 不能访问 `WEB-INF`中的资源，可以访问工程外的资源（如百度）

**示例一**

```Java
// 原地址servlet响应
@Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("原地址servlet响应，跳转新地址：");
    
    	// 方式一，不推荐
        // 设置响应信息,表示重定向，并设置地址
        resp.setStatus(302);
        resp.setHeader("Location","http://localhost:8080/newHello");
    
    	// 方式二
    	resp.sendRedirect("http://localhost:8080/newHello")
    }

// 新地址
@Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("跳转到新地址：");
        resp.getWriter().println("hello");
}
```



---

##### 5、HttpServletResponse类

**说明**

* 封装了每次请求后，服务器所响应的信息
* 可以该类设置返回信息

**两个输出流**

* 字节流 `getOutputStream()`	：常用于下载（传递二进制数据）
* 字符流 `getWirter()` 	：常用于回传字符串

**往客户端回传数据**

```Java
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	// 修改编码方式，防止中文乱码问题
    
    	// 设置服务器字符集为 "utf-8"
        resp.setCharacterEncoding("UTF-8");
        // 通过响应头设置浏览器字符集也为 "utf-8"
    	resp.setHeader("Content-Type","text/html;charset=UTF-8");
    	// 也可以直接使用 resp.setContentType("text/html;charset=UTF-8")
    
    	// 发送数据
        resp.getWriter().write("hello");
        resp.getWriter().println("帅滴");
    }
```





---

### Cookie和Session

##### 1、Cookie

**概念**

* `cookie`是 `servlet`发送到 web浏览器的少量信息，这些信息由浏览器保存
* 客户端有了`cookie`之后，每次访问服务器是发送该信息给服务器
* cookie的值可以唯一的标识客户端

**Cookie使用**

```java 
// 创建Cookie，不支持中文
Cookie myCookie = new Cookie("key","value");
// 通知客户端保存cookie
Resp.addCookie(myCookie);
    
    
// 获取Cookie,存放在请求头
Cookie[] cookies = req.getCookies();

// 修改Cookie的值
// 通过创建并添加同名的Cookie修改
resp.addCookie(new Cookie("key","newValue"));

```

**Cookie的生命控制**

* 设置Cookie的生存时间`setMaxAge()`

  ```java
  Cookie myCookie = new Cookie("key","value");
  /*
  * 正数：在指定的秒数后过期
  * 负数：关闭浏览器后删除，默认值
  * 零：  马上删除
  **/
  myCookie.setMaxAge(-1);
  ```

**Cookie的有效路径path**

* 可以有效的过滤哪些`cookie`可以发送给服务器

* 举例

  ```
  cookieA 	path=/工程路径
  cookieB		path=/工程路径/ABC
  
  现请求地址如下：
  	http://ip:port/工程路径/a.html
  
  则Cookie发送：cookieA 
  
  ```

---

**免用户名登录**

将用户名作为`cookie`保存到浏览器中，下次访问时，服务器获取该值并自动填写



---

##### 2、Session会话

**概念**

* `session`是一个接口(`httpSession`)，每一个客户端都有自己的一个session会话，经常用来保存用户登录后的信息
* **在浏览器关闭后失效，request和response在请求结束后失效**



**使用session**

```Java
// 获取并创建Session
HttpSession session = request.getSession();

// 判断session是否为新建
boolean result = session.isNew();
```



**RequestId**

* 每一个会话都有一个身份证号，即会话ID，这个ID值是唯一的。

  ```
  // 获取会话的唯一标识
  String id = request.getSession().getId();
  ```

  









