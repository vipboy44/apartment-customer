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
                    mailMessage.setSubject("X??c nh???n ?????t l???i m???t kh???u");
                    mailMessage.setFrom("ndt.programmer@gmail.com");
                    //mailMessage.setFrom("huynhtri2552@gmail.com");
                    mailMessage.setText("Xin ch??o B???n " + "\n"
                            + "ch??ng t??i ???? nh???n ???????c y??u c???u  ?????t l???i m???t kh???u c???a b???n " + "\n"
                            + "vui l??ng click v??o link b??n d?????i ????? ?????t l???i m???t kh???u " + "\n"
                            + domain + "/resetpassword/confirm-reset?token="
                            + tokenUser.getToken());
                    emailSenderService.sendEmail(mailMessage);
                    modelAndView.setStatus(HttpStatus.OK);
                    modelAndView.addObject("messageSuccess",
                            "H??? th???ng ???? g???i cho b???n m???t e-mail c?? k??m theo link" + "\n" + "????? ?????t l???i m???t kh???u, ki???m tra email c???a b???n");

                } else {
                    modelAndView.setStatus(HttpStatus.NOT_FOUND);
                    modelAndView.addObject("messageError", "Email n??y kh??ng t???n t???i, vui l??ng ki???m tra l???i ");

                }
            } else {
                modelAndView.setStatus(HttpStatus.NOT_FOUND);
                modelAndView.addObject("messageError", "Id c??n h??? kh??ng t???n t???i, vui l??ng ki???m tra l???i");
            }
        } catch (Exception e) {
            modelAndView.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            modelAndView.addObject("messageError", "L???i server! vui l??ng th??? l???i sau");
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
            modelAndView.addObject("message", "Li??n k???t kh??ng h???p l??? ho???c b??? h???ng!");
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
            modelAndView.addObject("messageSuccess", "?????t l???i m???t kh???u th??nh c??ng");
            tokenRespository.deleteById(tokenUser.getId());
            modelAndView.setViewName("login");
        }
        return modelAndView;
    }
}