package io.github.wrobezin.framework.example.validator.controller;

import com.alibaba.fastjson.JSON;
import io.github.wrobezin.framework.business.entity.Student;
import io.github.wrobezin.framework.business.entity.TestEntity;
import io.github.wrobezin.framework.example.validator.WebApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author yuan
 * date: 2019/12/17
 */
@SpringBootTest(classes = WebApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ValidatorUseControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    void hello() throws Exception {
        mvc.perform(get("/validation/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("hello"))
                .andDo(print());
    }

    @Test
    void user1() throws Exception {
        mvc.perform(
                post("/validation/user")
                        .param("username", "田所浩二")
                        .param("password", "123456")
                        .param("age", "24")
        ).andExpect(status().isOk())
                .andExpect(content().json("{\"successful\":true,\"message\":\"操作成功\",\"data\":{\"username\":\"田所浩二\",\"password\":\"123456\",\"age\":24}}"))
                .andDo(print());
    }

    @Test
    void user2() throws Exception {
        mvc.perform(post("/validation/user")
                .param("username", "田所浩二田所浩二田所浩二田所浩二田所浩二")
                .param("password", "123456")
                .param("age", "24")
        ).andExpect(status().isOk())
                .andExpect(content().json("{\"successful\":false,\"message\":\"用户名不能为空且不能超过16个字符\",\"data\":null}"))
                .andDo(print());
    }

    @Test
    void user3() throws Exception {
        mvc.perform(
                post("/validation/user")
                        .param("username", "田所浩二")
                        .param("password", "12345")
                        .param("age", "24")
        ).andExpect(status().isOk())
                .andExpect(content().json("{\"successful\":false,\"message\":\"密码长度应为6至16位\",\"data\":null}"))
                .andDo(print());
    }

    @Test
    void user4() throws Exception {
        mvc.perform(
                post("/validation/user")
                        .param("username", "田所浩二")
                        .param("password", "野兽先辈1234")
                        .param("age", "24")
        ).andExpect(status().isOk())
                .andExpect(content().json("{\"successful\":false,\"message\":\"密码只能包含字母、数字及常规符号\",\"data\":null}"))
                .andDo(print());
    }

    @Test
    void user5() throws Exception {
        mvc.perform(
                post("/validation/user")
                        .param("username", "田所浩二")
                        .param("password", "123456")
                        .param("age", "240")
        ).andExpect(status().isOk())
                .andExpect(content().json("{\"successful\":false,\"message\":\"年龄取值错误\",\"data\":null}"))
                .andDo(print());
    }

    @Test
    void student1() throws Exception {
        Student student = new Student(
                "田所浩二",
                24,
                new TestEntity("a", "b"),
                new String[]{"红茶", "软手机"}
        );
        mvc.perform(
                post("/validation/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(student))
        ).andExpect(status().isOk())
                .andExpect(content().json("{\"successful\":true,\"message\":\"操作成功\",\"data\":{\"name\":\"田所浩二\",\"age\":24,\"test\":{\"a\":\"a\",\"b\":\"b\"},\"fancies\":[\"红茶\",\"软手机\"]}}"))
                .andDo(print());
    }

    @Test
    void student2() throws Exception {
        Student student = new Student(
                "野兽先辈",
                0x24,
                new TestEntity("a", "b"),
                new String[]{"红茶", "软手机"}
        );
        mvc.perform(
                post("/validation/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(student))
        ).andExpect(status().isOk())
                .andExpect(content().json("{\"successful\":false,\"message\":\"你不是田所家的人\",\"data\":null}"))
                .andDo(print());
    }

    @Test
    void student3() throws Exception {
        Student student = new Student(
                "田所树人",
                240,
                new TestEntity("a", "b"),
                new String[]{"红茶", "软手机"}
        );
        mvc.perform(
                post("/validation/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(student))
        ).andExpect(status().isOk())
                .andExpect(content().json("{\"successful\":false,\"message\":\"你不是24岁\",\"data\":null}"))
                .andDo(print());
    }

    @Test
    void student4() throws Exception {
        Student student = new Student(
                "田所浩二",
                24,
                new TestEntity("aa", "bbbbb"),
                new String[]{"红茶", "软手机"}
        );
        mvc.perform(
                post("/validation/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(student))
        ).andExpect(status().isOk())
                .andExpect(content().json("{\"successful\":false,\"message\":\"a不是a\",\"data\":null}"))
                .andDo(print());
    }

    @Test
    void student5() throws Exception {
        Student student = new Student(
                "田所浩二",
                24,
                new TestEntity("a", "bbbbb"),
                new String[]{"红茶", "软手机"}
        );
        mvc.perform(
                post("/validation/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(student))
        ).andExpect(status().isOk())
                .andExpect(content().json("{\"successful\":false,\"message\":\"b不是b\",\"data\":null}"))
                .andDo(print());
    }

    @Test
    void student6() throws Exception {
        Student student = new Student(
                "田所浩二",
                24,
                new TestEntity("a", "b"),
                new String[]{"红茶", "软手机", "雷霆普惠"}
        );
        mvc.perform(
                post("/validation/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(student))
        ).andExpect(status().isOk())
                .andExpect(content().json("{\"successful\":false,\"message\":\"你的爱好太多了\",\"data\":null}"))
                .andDo(print());
    }

    @Test
    void student7() throws Exception {
        Student student = new Student(
                "田所浩二",
                24,
                null,
                new String[]{"红茶", "软手机", "雷霆普惠"}
        );
        mvc.perform(
                post("/validation/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(student))
        ).andExpect(status().isOk())
                .andExpect(content().json("{\"successful\":false,\"message\":\"test不能为空\",\"data\":null}"))
                .andDo(print());
    }

    @Test
    void bigDecimal1() throws Exception {
        mvc.perform(post("/validation/big-decimal")
                .param("bigDecimal", "50")
        ).andExpect(status().isOk())
                .andExpect(content().json("{\"successful\":true,\"message\":\"操作成功\",\"data\":50}"))
                .andDo(print());
    }

    @Test
    void bigDecimal2() throws Exception {
        mvc.perform(post("/validation/big-decimal")
                .param("bigDecimal", "30")
        ).andExpect(status().isOk())
                .andExpect(content().json("{\"successful\":false,\"message\":\"范围不对\",\"data\":null}"))
                .andDo(print());
    }

    @Test
    void bigDecimal3() throws Exception {
        mvc.perform(post("/validation/big-decimal")
                .param("bigDecimal", "766666666666666666666666666666666666666666666")
        ).andExpect(status().isOk())
                .andExpect(content().json("{\"successful\":false,\"message\":\"范围不对\",\"data\":null}"))
                .andDo(print());
    }
}
