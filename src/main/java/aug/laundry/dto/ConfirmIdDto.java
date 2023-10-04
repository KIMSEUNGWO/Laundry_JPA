package aug.laundry.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
@Data
@Component
public class ConfirmIdDto {

    private String memberName;

    private String memberPhone;

    private String memberAccount;

    public ConfirmIdDto(String memberName, String memberPhone, String memberAccount) {
        this.memberName = memberName;
        this.memberPhone = memberPhone;
        this.memberAccount = memberAccount;
    }

    public ConfirmIdDto() {
    }
}
