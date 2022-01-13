package poly.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import poly.com.entity.Apartment;
import poly.com.service.ResetPasswordServices;

@Controller
@RequestMapping("/resetpassword")
public class ResetPasswordController {

    @Autowired
    ResetPasswordServices resetPasswordServices;

    @GetMapping("/page-forgot-password")
    public ModelAndView displayResetPassword(ModelAndView modelAndView, Apartment apartment) {
        modelAndView.addObject("apartment", apartment);
        modelAndView.setViewName("form-check-email");
        return modelAndView;
    }

    @RequestMapping(value = "/forgot-password", method = RequestMethod.POST)
    public ModelAndView sendtokentoemail(ModelAndView modelAndView,
                                         @RequestParam("id") String id
                                        ,@RequestParam("email") String email) {
        return resetPasswordServices.SendTokenToEmail(modelAndView, id, email);
    }

    @GetMapping("/confirm-reset")
    public ModelAndView validateresettoken(ModelAndView modelAndView, @RequestParam("token") String token) {
        return resetPasswordServices.validateresettoken(modelAndView, token);
    }

    @PostMapping(value = "/reset-password")
    public ModelAndView resetpassword(ModelAndView modelAndView, Apartment apartment, @RequestParam("token") String token) {
        return resetPasswordServices.resetpassword(modelAndView, apartment, token);
    }
}
