package poly.com.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import poly.com.helper.MessageResponse;
import poly.com.request.ChangePasswordRequest;
import poly.com.service.ChangePasswordService;

@RestController
@RequestMapping("/api/account")
public class ChangePasswordAPI {
    @Autowired
    private ChangePasswordService changePasswordService;

    @PostMapping("/changepassword")
    public ResponseEntity<MessageResponse> ChangePassword(@Valid  @RequestBody ChangePasswordRequest changePasswordRequest) {
        return changePasswordService.ChanePassword(changePasswordRequest);
    }
}
