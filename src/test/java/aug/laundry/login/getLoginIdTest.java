package aug.laundry.login;

import aug.laundry.dao.login.LoginRepository;
import aug.laundry.domain.Member;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

//@SpringBootTest
public class getLoginIdTest {

    @Autowired
    private LoginRepository loginRepository;
    @Autowired
    private static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void findByMember() {
        String sessionId = "AA80C0CD77667D013FABD786D903B0,49";
    }



}
