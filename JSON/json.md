# JSON

## JSON简介

1. json是一种轻量级的数据交换格式。
2. 它完全独立于语言的文本格式，是理想的数据交换语言
3. json是js对象的字符串表示法，本质上是一个==字符串==
4. 组成结构
   1. ==对象==：键值对的无序集合
   2. ==数组==：值的有序列表
5. 语法格式：
   1. 花括号=={}==保存对象
   2. 方括号==[]==保存数组
   3. 数据之间由逗号分隔，最后一个数据后不加逗号
   4. 键和值都由双引号==“”==包裹，使用冒号==：==分隔

## jackson

1. 概述
   1. 是Java用来处理JSON格式数据的类库
   2. 使后端能够返回一个JSON格式的数据（原始返回的是一个视图解析器）
   3. 能够将java对象序列化为JSON字符串，也能够将JSON字符串反序列化为java对象 
2. 基础准备
   1. 导入jar包`jackson-databind`
3. 使用方式（添加注解）
   1. @ResponsBody：将服务器返回的对象转换为JSON对象响应回去
   2. 通过objectmapper方法操作

## fastjson

1. API序列化：将对象、map或集合转换为JSON字符串

   ```java
   string str = JSON.toJSONstring(object)
   ```

2. API反序列化：将字符串转为对象、集合等

   ```java
   // 字符串转对象
   Object obj = JSON.parseObject(str，Object.class)
   // 字符串转集合
   List<Object> objList = JSON.parseArray(str,Object.class)
   ```

3. 定制序列化

   1. 日期格式化

      * 方式一：在需要格式化的日期属性上添加注解@jsonfild(format=日期格式)

      * 方式二：

        ```java
        //定义日期格式
        json.deffault_date_format = "日期格式";
        //引用改格式
        string str = json.tojsonstring(object,serializerfeature.writedateusedateformat)
        ```

      * 方式三：配置serializeconfig

      * @jsonfild注意

        1. FastJson 在进行操作时，是根据 getter 和 setter 的方法进行的，并不是依据 Field 进行。
        2. 若属性是私有的，必须有 set 方法。否则无法反序列化。

4. 创建JSON对象

   1. 创建方法：使用fastjson提供的JSONObject和JSONArray对象创建

   2. JSONObject特点

      1. 可以把JSONObject 当成一个 Map<String,Object> 来看
      2.  JSONObject 提供了更为丰富便捷的方法，方便我们对于对象属性的操作
      3. 同样我们可以把 JSONArray 当做一个 List<Object>，可以把 JSONArray 看成 JSONObject 对象的一个集合。

   3. 使用示例

      ```java
      // 创建,是一个map
      JSONObject jsonobject = new JSONObject()
      // 添加值
      jsonobject.put(key,value);
      // 根据key获取对应的字符串
      jsonobject.getstring(key);
      // 根据key获取对应的int值
      jsonObject.getintvalue(key)
      // 添加入jsonarray
JSONArray jsonarray = new JSONArray(jsonobject);
      ```
   
      
   
   