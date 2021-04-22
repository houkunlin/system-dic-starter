package test.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.system.dic.starter.DicUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import test.application.bean.Bean1;
import test.application.bean.Bean2;
import test.application.bean.PeopleType;

/**
 * @author HouKunLin
 */
@Component
public class CommandRunnerTests implements CommandLineRunner {
    private final ObjectMapper objectMapper;

    public CommandRunnerTests(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(final String... args) throws Exception {
        System.out.println(objectMapper.writeValueAsString(new Bean2()));
        final Bean1 bean1 = new Bean1();
        System.out.println(objectMapper.writeValueAsString(bean1));
        System.out.println(objectMapper.writeValueAsString(new Bean2()));
        System.out.println(DicUtil.getDicType(PeopleType.class.getSimpleName()));
        System.out.println(objectMapper.writeValueAsString(DicUtil.getDicType(PeopleType.class.getSimpleName())));
    }
}
