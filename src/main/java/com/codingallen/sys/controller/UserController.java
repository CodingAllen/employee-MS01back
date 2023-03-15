package com.codingallen.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codingallen.common.vo.Result;
import com.codingallen.sys.entity.User;
import com.codingallen.sys.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.codingallen.common.vo.Result;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author CodingAllen
 * @since 2023-03-14
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping("/all")
    public Result<List<User>> getAllUser() {
        List<User> list = userService.list();
        return Result.success(list, "検索に成功しました。");
    }

    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody User user) {
        Map<String, Object> data = userService.login(user);
        if (data != null) {
            return Result.success(data);
        } else {
            return Result.fail(20002, "ユーザ名或いはパスワードは間違えました！");
        }
    }

    //ユーザ情報のゲット
    @GetMapping("/info")
    public Result<Map<String, Object>> getUserInfo(@RequestParam("token") String token) {
        //tokenを通じて、ユーザ情報をゲット
        Map<String, Object> data = userService.getUserInfo(token);
        if (data != null) {
            return Result.success(data);
        } else {
            return Result.fail(20003, "ログイン情報が無効です。もう一度ログインしてください。");
        }
    }

    @PostMapping("/logout")
    public Result<?> logout(@RequestHeader("X-Token") String token) {
        userService.logout(token);
        return Result.success();
    }

    //検索
    //required = false 引数を必須ではなくする
    @GetMapping("/list")
    public Result<Map<String,Object>> getUserList(@RequestParam(value = "username",required = false) String username,
                                                  @RequestParam(value = "phone",required = false) String phone,
                                                  @RequestParam(value = "pageNo") Long pageNo,
                                                  @RequestParam(value = "pageSize") Long pageSize){

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasLength(username),User::getUsername,username);
        wrapper.eq(StringUtils.hasLength(phone),User::getPhone,phone);
        //データの順番
        wrapper.orderByDesc(User::getId);

        Page<User> page = new Page<>(pageNo,pageSize);
        userService.page(page,wrapper);

        Map<String,Object> data = new HashMap<>();
        data.put("total",page.getTotal());
        data.put("rows",page.getRecords());

        return Result.success(data);

    }

    @PostMapping
    public Result<?> addUser(@RequestBody User user){

        userService.save(user);
        return Result.success("ユーザを追加しました。");
    }

    @PutMapping
    public Result<?> updateUser(@RequestBody User user){

        userService.updateById(user);
        return Result.success("ユーザを編集しました。");
    }

    @GetMapping("/{id}")
    public Result<User> getUserById(@PathVariable("id") Integer id){
        User user = userService.getById(id);
        return Result.success(user);
    }

    @DeleteMapping("/{id}")
    public Result<User> deleteUserById(@PathVariable("id") Integer id){
        userService.removeById(id);
        return Result.success("ユーザを削除しました。");
    }
}
