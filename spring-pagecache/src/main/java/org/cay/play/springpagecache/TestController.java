package org.cay.play.springpagecache;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@Controller
@Slf4j
public class TestController {
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    @RequestMapping(value = {"/", "test"})
    public String test(Model model, HttpServletResponse response) {
        log.info("test run!");
        setHead(response);

        String str = stringRedisTemplate.opsForValue().get("test");

        model.addAttribute("msg", str);

        return "TestPage";
    }


    @RequestMapping(value = {"test2"})
    @ResponseBody
    public String test2(Model model, HttpServletResponse response, HttpServletRequest request) {
        log.info("test2 run!");

        //取缓存
        String html = stringRedisTemplate.opsForValue().get("testpage");
        if (!StringUtils.isEmpty(html)) {
            return html;
        }

        log.info("test2 create html again!");
        String str = stringRedisTemplate.opsForValue().get("test");
        model.addAttribute("msg", str);
        setHead(response);
        WebContext ctx = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
        //手动渲染
        html = thymeleafViewResolver.getTemplateEngine().process("TestPage", ctx);//两个参数，一个模板名称，一个是IContext的实现类
        if (!StringUtils.isEmpty(html)) {
            stringRedisTemplate.opsForValue().set("testpage", html, 30, TimeUnit.SECONDS);
        }
        return html;
    }

    @RequestMapping("/set/{str}")
    @ResponseBody
    public String setRedisValue(@PathVariable String str) {
        stringRedisTemplate.opsForValue().set("test", str, 10, TimeUnit.SECONDS);
        return "ok";
    }


    void setHead(HttpServletResponse response) {
        response.setContentType("text/html");
        //servlet页面默认是不缓存的
        //本页面允许在浏览器端或缓存服务器中缓存，时限为20秒。
        //20秒之内重新进入该页面的话不会进入该servlet的
        java.util.Date date = new java.util.Date();
        response.setDateHeader("Last-Modified", date.getTime()); //Last-Modified:页面的最后生成时间
        response.setDateHeader("Expires", date.getTime() + 20000); //Expires:过时期限值
        response.setHeader("Cache-Control", "public"); //Cache-Control来控制页面的缓存与否,public:浏览器和缓存服务器都可以缓存页面信息；
        response.setHeader("Pragma", "Pragma"); //Pragma:设置页面是否缓存，为Pragma则缓存，no-cache则不缓存

        //不允许浏览器端或缓存服务器缓存当前页面信息。
        /*response.setHeader( "Pragma", "no-cache" );
        response.setDateHeader("Expires", 0);
        response.addHeader( "Cache-Control", "no-cache" );//浏览器和缓存服务器都不应该缓存页面信息
        response.addHeader( "Cache-Control", "no-store" );//请求和响应的信息都不应该被存储在对方的磁盘系统中；
        response.addHeader( "Cache-Control", "must-revalidate" );*///于客户机的每次请求，代理服务器必须想服务器验证缓存是否过时；

    }
}
