package com.sinon.controller;

import com.sinon.mapper.AuthorMapper;
import com.sinon.pojo.author;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class authorController {
    @Autowired
    private AuthorMapper authorMapper;
    @ApiOperation("查询全部")
    @GetMapping("/query")
    public List<author> queryAllAuthor(){
        List<author> authorList = authorMapper.queryAllAuthor();
        return authorList;
    }
    @ApiOperation("根据id查询")
    @GetMapping("/queryid/{id}")
    public author queryAuthorById(@PathVariable("id")int id){
        author author = authorMapper.queryAuthorById(id);
        return author;
    }
    @ApiOperation("添加")
    @GetMapping("/add")
    public String addAuthor(author author){
        authorMapper.addAuthor(author);
        return "finish add";
    }
    @ApiOperation("更新")
    @PostMapping("/update")
    public String updateAuthor(author author){
        authorMapper.updateAuthor(author);
        return "update finished";
    }
    @ApiOperation("删除")
    @GetMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable("id")int id){
        authorMapper.deleteAuthor(id);
        return "delete finished";
    }
}
