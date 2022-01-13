package poly.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import poly.com.entity.Apartment;
import poly.com.entity.TokenUser;
import poly.com.repository.ApartmentRepository;
import poly.com.repository.TokenRespository;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Date;

@Service
@Transactional
public class ResetPasswordServices {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private ApartmentRepository apartmentRepository;

    @Autowired
    private TokenRespository tokenRespository;

    @Value("${app.domain}")
    private String domain;


    /* -----------------------Scheduling delete token ----------------------------- */
    // scheduleding(lap lich) het tgian se tu dong xoa  cho token
    @Scheduled(cron = "${purge.cron.expression}")
    public void TOKENEXPIRED() {
        Date now = Date.from(Instant.now());
        tokenRespository.deleteAllByExpiryDate(now);
    }

    public ModelAndView SendTokenToEmail(ModelAndView modelAndView, String id, String email) {
        try {
            Apartment apartment = apartmentRepository.findById(id).orElse(null);
            if (apartment != null) {
                String EmailOwn = apartment.getOwnApartment().getEmail();
                if (EmailOwn.matches(email)) {
                    TokenUser existTokenUser = tokenRespository.findByApartment(apartment);
                    if (existTokenUser != null){
                        tokenRespository.deleteById(existTokenUser.getId());
                    }
                    TokenUser tokenUser = new TokenUser(apartment);
                    tokenRespository.save(tokenUser);
                    SimpleMailMessage mailMessage = new SimpleMailMessage();
                    mailMessage.setTo(email);
                    mailMessage.setSubject("Xác nhận đặt lại mật khẩu");
                    mailMessage.setFrom("ndt.programmer@gmail.com");
                    //mailMessage.setFrom("huynhtri2552@gmail.com");
                    mailMessage.setText("Xin chào Bạn " + "\n"
                            + "chúng tôi đã nhận được yêu cầu  đặt lại mật khẩu của bạn " + "\n"
                            + "vui lòng click vào link bên dưới để đặt lại mật khẩu " + "\n"
                            + domain + "/resetpassword/confirm-reset?token="
                            + tokenUser.getToken());
                    emailSenderService.sendEmail(mailMessage);
                    modelAndView.setStatus(HttpStatus.OK);
                    modelAndView.addObject("messageSuccess",
                            "Hệ thống đã gửi cho bạn một e-mail có kèm theo link" + "\n" + "để đặt lại mật khẩu, kiểm tra email của bạn");

                } else {
                    modelAndView.setStatus(HttpStatus.NOT_FOUND);
                    modelAndView.addObject("messageError", "Email này không tồn tại, vui lòng kiểm tra lại ");

                }
            } else {
                modelAndView.setStatus(HttpStatus.NOT_FOUND);
                modelAndView.addObject("messageError", "Id căn hộ không tồn tại, vui lòng kiểm tra lại");
            }
        } catch (Exception e) {
            modelAndView.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            modelAndView.addObject("messageError", "Lỗi server! vui lòng thử lại sau");
        }

        modelAndView.setViewName("form-check-email");
        return modelAndView;
    }

    public ModelAndView validateresettoken(ModelAndView modelAndView, String token) {
        TokenUser tokenUser = tokenRespository.findByToken(token).orElse(null);

        if (tokenUser != null) {
            Apartment apartment = apartmentRepository.findApartmentById(tokenUser.getApartment().getId()).orElse(null);
            apartmentRepository.save(apartment);
            modelAndView.addObject("apartment", apartment);
            modelAndView.addObject("id", apartment.getId());
            modelAndView.addObject("token", token);
            modelAndView.setStatus(HttpStatus.OK);
            modelAndView.setViewName("form-reset-password");
        } else {
            modelAndView.setStatus(HttpStatus.NOT_FOUND);
            modelAndView.addObject("message", "Liên kết không hợp lệ hoặc bị hỏng!");
            modelAndView.setViewName("404");
        }
        return modelAndView;
    }

    /* ------------------------------------resetpassword ---------------------------*/
    public ModelAndView resetpassword(ModelAndView modelAndView, Apartment apartment, String token) {
        TokenUser tokenUser = tokenRespository.findByToken(token).orElse(null);

        if (tokenUser == null) {
            modelAndView.setStatus(HttpStatus.NOT_FOUND);
            modelAndView.addObject("message", "The link is invalid or broken!");
            modelAndView.setViewName("404");
        } else {
            Apartment tokenApartment = tokenUser.getApartment();
            tokenApartment.setPassword(passwordEncoder.encode(apartment.getPassword()));
            apartmentRepository.save(tokenApartment);
            modelAndView.setStatus(HttpStatus.OK);
            modelAndView.addObject("messageSuccess", "Đặt lại mật khẩu thành công");
            tokenRespository.deleteById(tokenUser.getId());
            modelAndView.setViewName("login");
        }
        return modelAndView;
    }
}