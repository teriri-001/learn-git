# git命令
## 配置命令
**查看配置**

- 查看配置：`git config`
- 查看系统配置：`git config --system --list`
- 查看人为配置：`git config --global --list`

---
**配置邮箱和姓名**

- 配置邮箱：`git config --global user.name "megumi"`
- 配置密码：`git config --global user.password "yangyi.wor@fox.mail"`

---
- git的配置文件都是保存在本地的



## git使用
**创建本地仓库**

- 创建新的本地仓库（方式一）：`git init`
- 克隆远程仓库到本地（方式二）:  `git clone [url]`

---

**git文件操作**

* 查看文件状态：

  ```bash
  # 命令
  	- git status [filesname]
  ```

* 文件添加到暂存区:

  ```bash
  # 添加全部
  	- `git add .` 或 `git add --all`
  	
  # 添加指定文件：
  	- git add files
  ```

* 文件提交到本地仓库：

  ```bash
  # 命令
  	- git commit -m "描述信息"
  
  # 查看提交信息
  	- git log
  ```

* 推送到远程仓库：

  ```bash
  # 使用http方式
  # 添加远程仓库地址，并设定一个别称
  	- git remote add megumi url
  	
  # 推送到仓库
  	- git push megumi master(分支名称)
  ```

* 拉取代码：

  ```bash
  # 命令
  	- git pull megumi master
  ```




---

**其他文件操作**

* **回滚**：在各个commit提交信息之间进行切换

  ```bash
  # 查看commit信息
  	- git log
  	
  # 回滚
  	- git reset --hard commit提交编码
  	
  ```

   - git reflog
       	- 回滚到已经回滚过的版本时需要使用`git reflog`命令查询版本号
  	- **忽略文件( .gitignoer )**
  ```
  
  

---


​	可以将不用提交的文件，在该文件中标注出来

​```bash
# 文件中使用 '#' 做注释
*.txt 		 # 忽略所有以'.txt'结尾的文件
!lib.txt	 # 'lib.txt'文件除外
/temp		 # 忽略根目录下的文件，temp除外
build/		 # 忽略build目录下的所有文件
  ```



---

## git分支

**分支中常用命令**

```bash
# 列出所有本地分支
	- git branch
	
# 列出所有远程分支
	- git branch -r
	
# 新建分支develop
	- git branch develop
	
# 切换分支到develop
	- git checkout develop

# 合并指定分支到当前分支
	- git merge 分支名称
	
# 删除本地分支
	- git branch -d 分支名称
```



---

## idea

**新建项目关联git**

* 将从远程仓库克隆的git项目中的文件复制到新建的项目中，因为git项目已经关联到了远程仓库



**git管理**

* 快捷操作：拉取代码、提交代码到本地仓库（代码更新指：远程仓库版本新增的内容添加![](C:\Users\yangyim\Pictures\snipaste\Snipaste_2020-09-29_14-15-02.jpg)

* 代码提交：提交新建、或修改的文件到本地仓库

  <img src="C:\Users\yangyim\Pictures\snipaste\Snipaste_2020-09-29_14-16-25.jpg" style="zoom: 50%;" />

  

* 代码推送到远程仓库托管

  <img src="C:\Users\yangyim\Pictures\snipaste\Snipaste_2020-09-29_14-24-22.jpg" style="zoom:50%;" />

* 查看提交信息![](C:\Users\yangyim\Pictures\snipaste\Snipaste_2020-09-29_14-18-52.jpg)



**使用命令行方式操作**

​	在idea中也可以使用命令行方式操作idea，有时候更加便捷![](C:\Users\yangyim\Pictures\snipaste\Snipaste_2020-09-29_14-26-13.jpg)