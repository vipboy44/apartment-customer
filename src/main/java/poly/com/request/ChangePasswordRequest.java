package poly.com.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequest {

    @NotNull
    private String id;

    @NotNull
    @Size(min = 6 ,max = 12, message = "Password from 6 to 12 characters!")
    private String oldPassword;

    @NotNull
    @Size(min = 6 ,max = 12, message = "Password from 6 to 12 characters!")
    private String newPassword;
}
