package com.sinon.mapper;

import com.sinon.pojo.author;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

//标识这是一个mybatis的mapper类
@Mapper
/*可以使用通用的spring容器管理的单例bean组件：@component，但是建议使用@repository标明为dao层对象，可以操作
数据库相关的操作*/
@Repository
public interface AuthorMapper {
//    查询所有的author
    List<author> queryAllAuthor();
//    通过id查询author
    author queryAuthorById(int id);
//    增加一个author
    int addAuthor(author author);
//    修改
    int updateAuthor(author author);
//    删除
    int deleteAuthor(int id);
}
