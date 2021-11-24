package com.fzcode.servicenote.controller;

import com.fzcode.internalcommon.crud.Create;
import com.fzcode.internalcommon.crud.Delete;
import com.fzcode.internalcommon.crud.FullUpdate;
import com.fzcode.internalcommon.crud.IncrementalUpdate;
import com.fzcode.internalcommon.dto.http.SuccessResponse;
import com.fzcode.internalcommon.dto.servicenote.request.category.CategoryRequest;
import com.fzcode.internalcommon.exception.CustomizeException;
import com.fzcode.servicenote.entity.Categories;
import com.fzcode.servicenote.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.List;

@Api(tags = "分类模块")
@RestController
@RequestMapping(value = "/category")
public class CategoryController {
    CategoryService categoryService;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @ApiOperation(value = "测试接口")
    @GetMapping(value = "/test")
    public String test (){
        String ipHostAddress = "";
        try {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress ip = (InetAddress) addresses.nextElement();
                    if (ip instanceof Inet4Address
                            && !ip.isLoopbackAddress() //loopback地址即本机地址，IPv4的loopback范围是127.0.0.0 ~ 127.255.255.255
                            && !ip.getHostAddress().contains(":")) {
                        System.out.println("本机的IP = " + ip.getHostAddress());
                        ipHostAddress = ip.getHostAddress();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "service-note:"+ipHostAddress;

    }
    @ApiOperation(value = "获取分类列表")
    @GetMapping(value = "/list")
    public List<Categories> getList() {
        return categoryService.findAll();
    }

    @ApiOperation(value = "获取分类")
    @GetMapping(value = "/id/{id}")
    public SuccessResponse getById(@PathVariable(name = "id") Integer id) throws CustomizeException {
        Categories categories = categoryService.findById(id);
        return new SuccessResponse("查询成功", categories);
    }

    @ApiOperation(value = "添加分类")
    @PostMapping(value = "/create")
    public Integer create(@RequestBody @Validated(value = Create.class) CategoryRequest categoryRequest, @RequestHeader("aid") Integer aid, @RequestHeader("authority") String authority) throws CustomizeException {
        System.out.println("create");
        System.out.println(aid);
        System.out.println(authority);
        return categoryService.create(categoryRequest, aid,authority);
    }

    @ApiOperation(value = "修改分类-全量")
    @PutMapping(value = "/update")
    public SuccessResponse update(@RequestBody @Validated(value = FullUpdate.class) CategoryRequest categoryRequest, @RequestHeader("aid") Integer aid) throws CustomizeException {
        Integer cid = categoryService.update(categoryRequest,aid);
        return new SuccessResponse("更新成功", cid);
    }

    @ApiOperation(value = "修改分类-增量")
    @PatchMapping(value = "/patch")
    public SuccessResponse patch(@RequestBody @Validated(value = IncrementalUpdate.class) CategoryRequest categoryRequest, @RequestHeader("aid") Integer aid) throws CustomizeException {
        Integer cid = categoryService.patch(categoryRequest,aid);
        return new SuccessResponse("更新成功", cid);
    }

    @ApiOperation(value = "删除分类")
    @DeleteMapping(value = "/delete")
    public SuccessResponse del(@RequestBody @Validated(value = Delete.class) CategoryRequest categoryRequest, @RequestHeader("aid") Integer aid) throws CustomizeException {
        Integer cid = categoryService.delete(categoryRequest,aid);
        return new SuccessResponse("删除", cid);
    }
}
