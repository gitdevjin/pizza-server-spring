package project.pizza.web.admin.login;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.pizza.domain.member.Member;
import project.pizza.service.admin.LoginService;
import project.pizza.web.SessionConst;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String adminLoginForm(@ModelAttribute("loginForm") AdminLoginForm form) {
        return "/admin/loginForm";
    }

    @PostMapping("/login")
    public String adminLogin(@Valid @ModelAttribute("loginForm") AdminLoginForm form,
                             BindingResult bindingResult, HttpServletRequest request) {

        if (bindingResult.hasErrors()) return "admin/loginForm";

        Member loginMember = loginService.login(form.getEmailId(), form.getPassword());

        if (loginMember == null) {
            log.info("[LoginController] -- User not Found");
            bindingResult.reject("loginFail", "Incorrect email or password");
            return "admin/loginForm";
        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.Admin_Member, loginMember);

        return "redirect:/admin";
    }
}
