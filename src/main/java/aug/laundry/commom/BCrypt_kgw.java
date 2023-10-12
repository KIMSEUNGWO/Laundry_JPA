package aug.laundry.commom;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class BCrypt_kgw {

    @Autowired
    private final BCryptPasswordEncoder bc;

    public BCrypt_kgw(BCryptPasswordEncoder bc) {
        this.bc = bc;
    }

    public String encodeBCrypt(String password){
        return bc.encode(password);

    }

    public boolean matchBCrypt(String password, String encodedPassword){
        return bc.matches(password, encodedPassword);
    }
}
