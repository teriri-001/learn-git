# springboot整合mongodb

## 环境搭建

1. 添加依赖

   1. mongodb-driver：是Java链接MongoDB的驱动包
   2. spring-Data-MongoDB：用于操作MongoDB的持久层框架，封装了mongodb-driver

2. 配置MongoDB数据源

   ```yaml
   mongodb:
   	url:mongodb://主机地址:端口号/数据库名
   ```

## 项目开发

1. 编写实体类

   1. 为每一个集合编写一个实体类
   2. 注解“
      1. @document（collection=“集合名称”）：标明文档所在集合，当实体类类名与集合名相同则可以不使用该注解
      2. @ID：表明该属性映射的是主键“\_id”
      3. @field(“字段名”)：当实体类的属性名与集合中字段名不一致时，可以使用该注解标明映射
      4. @Indexed：表示属性是添加的单字段索引
      5. @compoundIndex(def= “{}”)：表示添加的复合索引
   3. 关于索引，建议使用命令行的方式添加而非注解

2. 编写dao层接口

   1. 需要继承MongoRepository

      ```java
      public interface UserRepository extends MongoRepository<User,String>{
      	public Page<User> findByUserNameLike(String userName, Pageable pageable);
      }
      ```

3. 编写service层业务逻辑

   1. @autowired自动注入dao接口
   2. 编写业务逻辑

4. 编写controller层接口

   1. @autowired自动注入service服务
   2. 获取所需要的返回值

5. spring-Data-MongoDB(mongoTemplate方式)

   1. @autowired自动注入MongoTemplate

   2. MongoTemplate的方法参数

      1. query：查询条件，使用提供的query类，查询使用提供的criteria类

         ```java
         // 查询条件为查询_id等于参数id的文档
         Query query = new Query(Criteria.where("_id").is(id))
         ```

      2. Update：更新条件，使用提供的Update类的方法

      3. Object.class：需要操作的实体类
      
   3. MongoTemplate语法

      1. 插入操作

         ```java
         // insert插入单个/多个数据,user实体类
         mongoTemplate.insert(List,"user")
         // save保存单个数据，如果该主键已存在，则变为修改该数据
         mongoTemplate.save(user)
         ```

      2. 删除操作

         ```java
         // 直接删除指定对象或列表
         mongoTemplate.remove(object/list<object>)
         // 根据查询条件的数据删除
         mongoTemplate.remove(query,user.class)
         ```

      3. 修改操作

         ```Java
         // 更新返回的第一条数据
         mongoTemplate.updatefirst(query,update,user.class)
         // 更新返回的所有数据
         mongoTemplate.updateMulti(query,update,user.class)
         ```

      4. 查询操作

         ```
         // 根据条件查询
         mongoTemplate.find(query,user.class)
         // 查询满足条件的第一条
         mongoTemplate.findOne(query.user.class)
         // 查询所有
         mongoTemplate.findAll(user.class)
         ```

         

6. Criteria常用方法

   1. andOperator：新建一个criteria对象放到当前criteria对象的criteriaChain属性中，解决了查询同一个字段多个约束的问题

      ```java
      //一个元素要大于28并且它的数组长度大于2位
      Criteria criteria = new Criteria().andOperator(  
              Criteria.where("qty").gt(28),  
              Criteria.where("qty").size(2)  
      );
      // 对应sql逻辑 qty>28 and qty
      ```