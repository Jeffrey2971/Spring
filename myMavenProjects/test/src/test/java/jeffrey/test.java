package jeffrey;


import jeffrey.config.SpringConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import jeffrey.service.IAccountService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfiguration.class)
public class test {

    @Autowired
    private IAccountService as;

    @Test
    public void test_findAccountById(){

        System.out.println(as.findAccountById(7));
    }
}
