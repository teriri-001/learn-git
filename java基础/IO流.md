# IO流

## File类的使用

**基本概念**

* 声明在`java.io`包

* file类的一个对象，代表了一个文件或文件目录
* 不涉及到文件内容的操作，该操作属于IO流内容



---

**常用构造器**

创建文件对象，能够对文件进行操作，但是只存在于内存中，持久化到磁盘

* **file ( pathname )**

  ```Java
  // 相对路径，相对于本项目所在文件夹
  File file = new File("hello.txt");
  
  // 绝对路径
  File file = new File("C:\\Users\\yangyim\\Desktop\\io.txt");
  ```

* **file ( parentPath,childPath )**

  ```java 
  // 把路径分为了两部分
  File file2 = new File("C:\\Users\\yangyim\\Desktop","io.txt")
  ```

* **file ( file,childpath )**

  ```java
  // 入参为文件类型，和文件下的新建内容
  // 新建一个文件对象
  File file = new File("C:\\Users\\yangyim\\Desktop")
  
  // 将其作为入参,在其目录下新建文件
  File file2 = new File(file,"io.txt")
  ```

---

**File类常用方法**

```Java
// 定义一个文件对象
File file = new File("C:\\Users\\yangyim\\Desktop\\test\\io.txt");

// 获取文件绝对路径
file.getAbsolutePath();

// 获取文件名称
file.getName()

// 获取文件长度
file.length()

// 最近修改时间，返回时间戳
file.lastModified();

// 获取文件目录
String[] lists = file.list();
File[] files = file.listFiles();

//移动文件并重命名,将1移动到2指定的路径，并改为相应的名称，2指定不能同名
file1.renameTo(file2);
```

---

**File类常用判断方法**

```Java
//       是否是一个文件夹 
        boolean directory = file.isDirectory();
        
//        是否是一个文件
        boolean file1 = file.isFile();
        
//        是否已经存在
        boolean exists = file.exists();
        
//        是否可读/可写
        boolean canRead = file.canRead();
        boolean write = file.canWrite();
```

---

**File类创建文件**

* 在磁盘中创建文件

```java
// 创建文件，若文件存在则不创建
File file = new File("C:\\Users\\yangyim\\Desktop\\test\\girl.txt");
if (!file.exists()){
    file.createNewFile();
    System.out.println("创建文件成功："+file.getName());
}
        
// 创建文件目录，若存在则不创建，若指定的上级目录不存在则不创建
boolean result = file.mkdir();

// 创建文件目录，若上级目录不存在，则一并创建
boolean result = file.mkdirs();
```

* 删除磁盘中的文件：`file.delete()`



---

## IO流

### IO流概述

**简述**

* IO流即输入输出流，用于处理设备之间的数据传输。如文件读写、网络通信等
* Java程序中，对于数据的输入输出操作以“流(stream)”的方式进行

---

### 流的分类

**按操作文件类型分为：字节流、字符流**

* 对于文本文件(.txt,.java,.c,.cpp)，使用字符流处理
* 对于非文本文件(.jpg,.mp3,.mp4,.avi,.doc,.ppt,...)，使用字节流处理

---

**按数据的流向分为：输入流、输出流**

* 输入流：读取外部数据（磁盘、光盘等）到程序（内存）中
* 输出流：将程序（内存）数据输出到磁盘、光盘等存储设备中

---

**按流所属角色分为：节点流、处理流**

---

**IO流体系**

Java的lO流共涉及40多个类，实际上非常规则，都是从如下4个抽象基类派生的。由这四个类派生出来的子类名称都是以其父类名作为子类名后缀，如访问文本文件的`FileInputStream`

| 抽象基类 |    字节流    | 字符流 |
| :------: | :----------: | :----: |
|  输入流  | InputStream  | Reader |
|  输出流  | OutputStream | Writer |



### 节点流

直接从数据源读写数据

**字符流**

* **Reader（输入流）**

  读取的文件一定要存在，否则就会报FileNotFoundException。

  ```java
  /*
  * 输入过程：
  * 1. 创建file文件对象，指定读取的文件
  * 2. 创建输入流对象
  * 3. 具体的读入过程，通常使用try finally包裹
  * 4. 关闭流资源,程序中打开的文件IO资源不属于内存里的资源，垃圾回收机制无法回收该资源，所以应该显式关闭文件IO资源。
  **/
  File file = new File("C:\\Users\\yangyim\\Desktop\\test\\io.txt");
  FileReader fileReader = new FileReader(file);
  
  /*read()
  * 读取一个字节，返回该字节的ASCII码，文件为空则返回-1
  * */
  int read = fileReader.read();
  char result = (char)read;
  
  /*
  * read(char[] chars)
  * 将文件中的字符读入字符数组，返回实际读取的字节数
  * */
  char[] chars = new char[50];
  int read = fileReader.read(chars);
  
  // 读取指定长度len字符到字符数组中
  int read（char[] cbuf,int off,int len）
  ```

* **Writer（输出流）**

  File对应的硬盘中的文件如果不存在，在输出的过程中，会自动创建此文件。

  ```java
  File file = new File("C:\\Users\\yangyim\\Desktop\\test\\io.txt");
  // 创建输出流对象，如果第二个参数为true，则在文件末尾追加字符；如果为false则删除文件原内容，重新写入字符。
  FileWriter writer = new FileWriter(file,false);
  
  // 写入一个字符，参数为ASCII码
  void write(int b);
  
  // 写入字符数组
  char[] chars = {'f','i','l','e'};
  writer.write(chars);
  
  // 刷新此输出流并强制写出所有缓冲的输出字节，调用此方法将这些字节立即写入它们预期的目标。
  public void flush()throws IOException
  ```

---

**字节流**

文件字节流操作与字符流操作类似，只是实例化对象操作和数据类型不同，字节流操作的是byte类型

```Java
File fileInput = new File("C:\\Users\\yangyim\\Desktop\\test\\io.png");
FileInputStream inputStream = new FileInputStream(fileInput);
try {
    byte[] bytes = new byte[1024];
    int read = inputStream.read(bytes);
    System.out.println(read);
} catch (IOException e){
    e.printStackTrace();
}finally {
    inputStream.close();
}
```



---

### 缓冲流

**概述**

* 缓冲流是对文件流处理的一种流，它本身并不具备 IO 功能，只是在别的流上加上缓冲提高读写效率
* 缓冲流先将数据缓存起来（缓冲区默认大小为8kb），缓冲区满后一起写入或读取出来，也可以使用`flush()`方法手动清空缓冲区
* 不带缓冲的操作，每读一个字节就要写入一个字节，由于涉及磁盘的IO操作相比内存的操作要慢很多，所以不带缓冲的流效率很低
* **使用BufferedOutputStream写完数据后，要调用flush()方法或close()方法，强行将缓冲区中的数据写出。**

---

**flush() 和 close()**

* flush 是从缓冲区把文件写出
* close 是将文件从缓冲区内写出并且关闭相应的流

---

**输入流**

```java
File fileInput = new File("C:\\Users\\yangyim\\Desktop\\test\\io.txt");
FileReader fileReader = new FileReader(fileInput);
BufferedReader bufferedReader = new BufferedReader(fileReader);
try {
    String data;
    // readLine()方法读取一行
    while ((data = bufferedReader.readLine()) != null){
        System.out.println(data);
    }
} catch (IOException e){
    e.printStackTrace();
} finally {
    bufferedReader.close();
}
```

---

**输出流**

```Java
File fileInput = new File("C:\\Users\\yangyim\\Desktop\\test\\io.txt");
BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileInput,true));
try {
    // 写入缓冲区（内存）
    bufferedWriter.write("hello\n");
    bufferedWriter.write("bweoenejwoen\n");
    // 写入文件（磁盘）
    bufferedWriter.flush();
} catch (IOException e){
    e.printStackTrace();
} finally {
    bufferedWriter.close();
}
```

---

### 数据流

用于读取或写出基本数据类型的变量或字符串，需要注意读取的类型、顺序需要和写入时保持一致

**DataInputStream 和 DataOutputStream**

```Java
File fileInput = new File("C:\\Users\\yangyim\\Desktop\\test\\io.txt");
// 写入文件
DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(fileInput));
        try {
            // 写入字符串
            dataOutputStream.writeUTF("data");
            dataOutputStream.writeUTF("hello");
            // 写入整数
            dataOutputStream.writeInt(18);
            // 写入布尔值
            dataOutputStream.writeBoolean(true);
            dataOutputStream.flush();
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            dataOutputStream.close();
        }

// 读取数据
DataInputStream dataInputStream = new DataInputStream(new FileInputStream(fileInput));
        try {
            // 按照写入时的顺序和类型读取
            String s = dataInputStream.readUTF();
            String s1 = dataInputStream.readUTF();
            int i = dataInputStream.readInt();
            boolean b = dataInputStream.readBoolean();
            System.out.println(s+s1+i+b);
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            dataInputStream.close();
        }
```



---

### 对象流

**对象**

* **ObjectOutputStream** ：内存中的对象--->存储到文件、通过网络传输出去：序列化过程
* **ObjectInputStream** ：存储在文件中的对象、通过网络接收过来 --->内存中的对象：反序列化过程

---

**对象的序列化**

* 对象序列化机制允许把内存中的Java对象转换成平台无关的二进制流，从而允许把这种二进制流持久地保存在磁盘上，或通过网络将这种二进制流传输到另一个网络节点。
* 序列化是RMI（远程方法调用）过程的参数和返回值都必须实现的机制，RMI是JavaEE的基础。因此序列化机制是JavaEE平台的基础。
* 简单来说，Java的序列化机制是通过在运行时判断类的serialversionUID来验证版本一致性的。在进行反序列化时，JVM会把传来的字节流中的serialversionUID与本地相应实体类的serialversionUID进行比较，如果相同就认为是一致的，可以进行反序列化，否则就会出现序列化版本不一致的异常

---

**实现序列化的对象所属类需要满足：**

1. 需要实现接口：Serializable（标识接口）
2. 当前类提供一个全局常量：serialVersionUID（序列版本号）
3. 除了当前类需要实现Serializable接口之外，还必须保证其内部所有属性也必须是可序列化的。（默认情况下，基本数据类型可序列化）
4. 不能序列化static和[transient](https://blog.csdn.net/qq_39505717/article/details/82119579)修饰的成员变量

---

**序列化**

```Java
File fileInput = new File("C:\\Users\\yangyim\\Desktop\\test\\io.txt");
ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileInput));
try {
    objectOutputStream.writeObject(new Person("megumi",18));
    objectOutputStream.writeObject(new Person("violet",18));
    objectOutputStream.flush();
} catch (IOException e){
    e.printStackTrace();
} finally {
    objectOutputStream.close();
}
```

**反序列化**

```Java
File fileInput = new File("C:\\Users\\yangyim\\Desktop\\test\\io.txt");
ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(fileInput));
try {
    Object object = objectInputStream.readObject();
    Person person2 = (Person)objectInputStream.readObject();
    Person person = (Person)object;
    System.out.println(person.toString());
    System.out.println(person2.toString());
} catch (ClassNotFoundException e) {
    e.printStackTrace();
} finally {
    objectInputStream.close();
}
```

