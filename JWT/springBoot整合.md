### springboot整合

##### 1、基础配置

**引入依赖**

```xml
<dependency>
    <groupId>com.auth0</groupId>
    <artifactId>java-jwt</artifactId>
    <version>3.11.0</version>
</dependency>

<!-- mybatis 通用mapper mysql等依赖-->
```

**实体类**

```java
@table("user")
public class User{
    private String id;
    private String userName;
    private String password;
}
```

**mapper接口**

```java
@Mapper
public interface UserMapper extends Mapper<User>{}
```

**service层**

```java
@service
public class UserService{
    @Autowired
    private UserMapper usermapper;
    public User login(User user){
        User user = usermapper.selectOne(user);
        if(user != null){
            //登录成功
            return user;
        }
        throw new RuntimeException("登录失败")
    }
}
```

**controller层**

```java
@RestController
public class UserController{
    @Autowired
    private UserService uservice;
    // 登录接口，验证信息，生成token返回。
    @GetMapping("/login")
    public Map<String,Object> login(User user){
        // 创建map封装响应返回信息
        Map<String,Object> map = new HashMap<>();
        try{
            User user = uservice.login(user);
            // 新建map存储payload中的内容
            Map<String,Object> payload = new HashMap<>();
            payload.put("id",user.getId());
            payload.put("name",user.getName());
            // 使用自己封装的工具类生成token
            String token = JWTUtils.getToken(payload);
            // 封装返回信息
            map.put("code",200);
            map.put("msg","登录认证成功");
            map.put("token",token);
        } catch( Exception e){
            // 封装失败信息
            map.put("code",500);
            map.put("msg",e.getMessage());
        }
        return map;
    }
------------------------------------------------------------------    
    // 测试接口，验证信息
    @GetMapping("/test")
    public Map<String,Object> test(String token){
    	Map<String,Object> map = new HashMap<>();
        try {
            // 使用自定义的工具类验证token
            DecodedJWT testToken = JWTUtils.verify(token);
          	// 封装返回信息
            map.put("code",200);
            map.put("msg","请求成功");
            return map;
        }catch (SignatureVerificationException e){
            // 进行细粒度的异常捕获处理
            System.out.println("签名异常");
            // 封装错误信息
            map.put("msg","签名异常")
        }catch (TokenExpiredException e) {
            System.out.println("token异常");
            map.put("msg","token异常");
        }catch (AlgorithmMismatchException e){
            System.out.println("算法异常");
            map.put("msg","算法异常");
        }catch (Exception e){
            System.out.println("总异常");
            map.put("msg","总异常");
        }
    	map.put("code",500);
    	return map;
    }
}
```

**代码优化**

```markdown
# 问题
 - 每个接口请求时，都要传递一个token值
 
# 解决方案
 - javaWeb项目将验证放入拦截器，分布式项目将验证放入网关；
```



---

##### 2、拦截器进行验证

**拦截器**

```Java
public class JWTInterceptor implements HandlerInterceptor {
    @Override
    // 在controller之前调用
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        
        try {
            // 使用自定义的工具类验证token
            DecodedJWT testToken = JWTUtils.verify(token);
          	return true; //验证成功，方法放行
        }catch (SignatureVerificationException e){
            System.out.println("签名异常");
            // 封装错误信息
            map.put("msg","签名异常")
        }catch (TokenExpiredException e) {
            System.out.println("token异常");
            map.put("msg","token异常");
        }catch (AlgorithmMismatchException e){
            System.out.println("算法异常");
            map.put("msg","算法异常");
        }catch (Exception e){
            System.out.println("总异常");
            map.put("msg","总异常");
        }
    	map.put("code",500);
        // 没有@ResponseBody，需要手动将map转为json,调用JackSon方法
        String json = new ObjectMapper().writeValueAsString(map);
        // 设置浏览器响应
        response.setContentType("application/json,charset=UTF-8");
        response.getWriter().write(json);
    	return false;
    }
    }
}
```

**配置拦截器**

```java
@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {

    // 添加拦截器

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JWTInterceptor())
                .addPathPatterns("/**") // 拦截所有请求
                .excludePathPatterns("/user/**"); //排除登录、注册相关的请求
     }
}
```

