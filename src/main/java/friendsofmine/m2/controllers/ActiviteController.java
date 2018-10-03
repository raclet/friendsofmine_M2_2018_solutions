package friendsofmine.m2.controllers;

import friendsofmine.m2.domain.Activite;
import friendsofmine.m2.services.ActiviteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class ActiviteController {

    @Autowired
    private ActiviteService activiteService ;

    @RequestMapping("/activitesWithResponsable")
    public ArrayList<Activite> findAllActvitesWithResponsable() {
        return activiteService.findAllActivites();
    }

    public void setActiviteService(ActiviteService activiteService) {
        this.activiteService = activiteService;
    }

}
