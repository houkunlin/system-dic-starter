package com.system.dic.starter.dic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.system.dic.starter.DicUtil;
import com.system.dic.starter.notice.RefreshDicEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import test.application.bean.Bean1;
import test.application.bean.Bean2;
import test.application.bean.PeopleType;

/**
 * @author HouKunLin
 */
@SpringBootTest
class DicApplicationTests {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ApplicationEventPublisher publisher;

    @Test
    void testBean1() throws JsonProcessingException {
        System.out.println(objectMapper.writeValueAsString(new Bean2(PeopleType.ADMIN, PeopleType.ADMIN)));
        final Bean1 bean1 = new Bean1("0", "1", "0", "1", "中风", "0", "0", PeopleType.ADMIN);
        System.out.println(objectMapper.writeValueAsString(bean1));
        System.out.println(objectMapper.writeValueAsString(new Bean2(PeopleType.ADMIN, PeopleType.ADMIN)));
        System.out.println(DicUtil.getDicType(PeopleType.class.getSimpleName()));
        System.out.println(objectMapper.writeValueAsString(DicUtil.getDicType(PeopleType.class.getSimpleName())));
    }

    @Test
    void testRefresh() throws InterruptedException {
        publisher.publishEvent(new RefreshDicEvent("test", true));
        Thread.sleep(20 * 1000);
    }
}
