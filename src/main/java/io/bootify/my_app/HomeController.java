package io.bootify.my_app;

import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import io.bootify.my_app.model.Appuser;
import io.bootify.my_app.model.Reservation;
import io.bootify.my_app.service.AppuserService;
import io.bootify.my_app.service.ReservationService;

@Controller
public class HomeController {

    final AppuserService userService;
    final ReservationService reservationService;

    public HomeController(AppuserService userService, ReservationService reservationService) {
        this.userService = userService;
        this.reservationService = reservationService;
    }

    @GetMapping("/")
    // @ResponseBody
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/reservations")
    public String reservations(Model model, HttpSession session) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.getUsername();
        Appuser appuser = userService.getUserByUsername(name);

        if (appuser != null) {
            session.setAttribute("appuser", appuser);
            Reservation reservation = new Reservation();
            model.addAttribute("reservation", reservation);

            return "reservations";
        }
        return "index";
    }

    @PostMapping("/reservations-submit")
    public String reservationsSubmit(@ModelAttribute Reservation reservation,
            @SessionAttribute("appuser") Appuser appuser) {

        // Save to DB after updating
        assert appuser != null;
        reservation.setAppuser(appuser);
        try {
            reservationService.create(reservation);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "redirect:/reservations";
        }
        userService.update(appuser.getId(), appuser);
        return "redirect:/reservations";
    }

}
