package org.cay.play.springredis.controller;


import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.cay.play.result.Result;
import org.cay.play.springredis.bean.Mytest;
import org.cay.play.springredis.service.IMyTest;
import org.cay.play.systemlog.SystemControllerLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {
    @Autowired
    IMyTest iMyTest;

    //    @Transactional

    /**
     * @Api：修饰整个类，描述Controller的作用
     * @ApiOperation：描述一个类的一个方法，或者说一个接口
     * @ApiParam：单个参数描述
     * @ApiModel：用对象来接收参数
     * @ApiProperty：用对象接收参数时，描述对象的一个字段
     * @ApiResponse：HTTP响应其中1个描述
     * @ApiResponses：HTTP响应整体描述
     * @ApiIgnore：使用该注解忽略这个API
     * @ApiError ：发生错误返回的信息
     * @ApiImplicitParam：一个请求参数
     * @ApiImplicitParams：多个请求参数
     */
    @ApiOperation(value = "用户取得", notes = "根据ID取得用户数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "查询id", example = "2")
    })
    @SystemControllerLog(description = "finduser called!")
    @GetMapping("/user/{id}")
    public Result finduser(@PathVariable Long id) {
        Mytest mytest = iMyTest.getUserById(id);
        if (mytest == null) {
            return Result.failure(999, "id:" + id + " not found!");
        } else {
            return Result.ok().setData(mytest).put("aaa", "bbb");
        }
    }

    @SystemControllerLog(description = "updateuser called!")
    @PostMapping("/updateuser")
    public Result updateuser(@RequestBody Mytest mytest) {
        boolean rtn = iMyTest.updateById(mytest);
        if (rtn) {
            return Result.ok();
        } else {
            return Result.failure(999, "更新失败!");
        }
    }
}
