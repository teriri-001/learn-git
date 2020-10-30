### jwt基础

##### 1、什么是JWT

```
  jwt即 `json web token(令牌)`，即通过`JSON`形式作为 `Web`应用中的令牌，用于在各方之间安全的将信息作为 `JSON`对象安全的进行传输。
```



---

##### 2、JWT能做什么

**授权**

```
  一旦用户登录，则后续的请求中都包含 `token`，从而允许用户访问后端接口。当前的单点登录广泛的使用 `JWT`。 优点是：开销小、可以跨越
```

**信息交换**

```
  因为可以对 `JWT` 进行签名（如，使用公/私钥），能够通过验签查看信息是否被篡改，所以比较安全。
```



---

##### 3、传统的Session认证

**认证方式**

```
  因为 `http` 是一种无状态的协议，这意味着用户提供了“用户名”和“密码”来进行用户认证，而在下一次的请求时，用户仍然还需要再进行一次用户认证才行。
  用户向服务器发送请求时，在服务器 `session` 中保存用户的登录信息，同时将该信息即 `sessionId` 通过响应传递回浏览器，并告诉其保存为 `cookie` ，在下次请求时自动携带该信息，服务器通过 `sessionId` 查询 `session` 值，如果有值则表示以及登录过，从而放行。
```

**暴露问题**

```
  每个用户经过认证之后，服务器都会做一次记录。通常而言，session都是保存在内存中，随着用户的增多，服务器的开销会非常大
  用户认证之后，服务器做认证记录。这也意味着用户下次请求必须要请求到这台服务器
```



---

##### 4、基于JWT认证

**认证流程**

```bash
- 前端通过web表单将用户信息发送到后端接口，一般使用 "http post"请求，（建议使用通过ssL加密的传输（https协议),避免信息被嗅探）
- 后端核对信息之后，将用户的 "id"等信息作为 "JWT Payload(负载)" ，将其与头部分别进行 "base64"编码拼接之后签名，形成 "JWT"(token) ,类似于"111.zzz.xxx"的字符串(head.payload.singurater)
- 后端将 "token"返回给前端，由前端将其保存在"localStorage"或"sessionStorage"上，如果要退出登录，则将其删除即可
- 前端在每次请求时，将"token"放入 HTTP Header中的 "authorrization"位。即 "HEADER"
- 后端对 "token"信息进行检查，如检查正确性、是否失效等
```



**优势**

```bash
- 简洁：可以通过"POST参数" "URL"或者在"http header"发送
- 自包含：负载中包含了所有用户需要的信息，避免多次查询数据库
- 不需要再服务端保存session信息，适用于分布式微服务
```



---

##### 5、JWT的结构

```markdown
# 1.令牌（token）组成
 - 1. 标头(header)
 - 2. 负载(payload)
 - 3. 签名(signature)
 - 形式如下： xxx.yyy.zzz
 
```

```markdown
# 2. Header
 - 标头通常由两部分组成：令牌的类型(JWT)和使用的签名算法。它会使用"base64"编码形成 JWT结构的第一部分
 - 注意：base64是编码，并不是加密过程。
 
# 明文示例
{
	"aig": "HS256",
	"type": "JWT"
}
```

```markdown
# 3. payload
 - 有效负载，其中包含声明，如用户信息实体
 - 同样使用 base64 编码组成,可以解码获取对应的信息
 - 建议不放密码等敏感信息，避免被拦截后解码泄露
 
# 明文示例
{
	"name": "admin",
	"password": "123qwe" // 建议不放密码等敏感信息
}
```

```markdown
# 4. signature
 - 前面两部分使用 base64 编码，前端解码就可获取信息。
 - signature 需要使用编码后的 header 和 payload 以及我们提供的一个密钥，然后使用签名算法进行签名获取，“密钥很重要，不能泄露”
 
# 签名示例
 HMACSHA256 ( base64Encode(header))+"."+base64Encode(payload) , secret );

# 签名的目的
 对头部和负载的内容进行签名，防止内容被篡改
```



---

##### 6、使用JWT

**引入依赖**

```xml
<dependency>
    <groupId>com.auth0</groupId>
    <artifactId>java-jwt</artifactId>
    <version>3.11.0</version>
</dependency>
```

**生成Token**

```Java
Map<String,Object> map = new HashMap<>();
Calendar calendar = Calendar.getInstance();
// 设定24小时候过期
calendar.add(Calendar.HOUR,24);
String token = JWT.create()
        //.withHeader(map) //header,一般不创建而使用默认值
        .withClaim("userName", "megumi") //payload,可以设置多个键值对
        .withClaim("sex", 1)
        .withExpiresAt(calendar.getTime()) // 指定令牌的过期时间，传入date类型，一般使用日历类
         .sign(Algorithm.HMAC256("imugem"));//签名，Algorithm提供了多种算法，需添加密钥
System.out.println(token);
```

**令牌验证**

```Java
// 创建验证对象
JWTVerifier imugem = JWT.require(Algorithm.HMAC256("imugem")).build();

// 验证token
DecodedJWT verify = imugem.verify("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzZXgiOjEsInVzZXJOYW1lIjoibWVndW1pIiwiZXhwIjoxNjAzNzgzNzk1fQ.wFoiTQnNOKW12tlLH4im989b-bu1KcRulfRi2j_QahA");

// 通过生成的对象获取信息
String userName = verify.getClaim("userName").asString();

// 获取所有的信息
Map<String, Claim> claims = verify.getClaims();
Claim name = claims.get("userName");
String names = name.asString();

// 过期时间
Date expiresAt = verify.getExpiresAt();
```

