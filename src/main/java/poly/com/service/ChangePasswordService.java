package poly.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import poly.com.entity.Apartment;
import poly.com.helper.MessageResponse;
import poly.com.repository.ApartmentRepository;
import poly.com.request.ChangePasswordRequest;

@Service
public class ChangePasswordService {

    @Autowired
    private ApartmentRepository apartmentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<MessageResponse> ChanePassword(ChangePasswordRequest changePasswordRequest ) {
        try {
            Apartment apartment = apartmentRepository.findById(changePasswordRequest.getId()).orElse(null);
            if (apartment == null)
                return new ResponseEntity<>(new MessageResponse("Căn hộ không tồn tại"), HttpStatus.NOT_FOUND);
                String dbPassword = apartment.getPassword();
                String oldPassword = changePasswordRequest.getOldPassword();
                if (passwordEncoder.matches(oldPassword, dbPassword)) {
                    apartment.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
                    apartmentRepository.save(apartment);
                    return ResponseEntity.ok(new MessageResponse("Đã đổi mật khẩu"));// chỗ này nó lại trả vể trong hàm error
                } else {
                    return new ResponseEntity<>(new MessageResponse("Mật khẩu cũ không đúng"), HttpStatus.BAD_REQUEST);
                }
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponse("Lỗi server"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }



}
