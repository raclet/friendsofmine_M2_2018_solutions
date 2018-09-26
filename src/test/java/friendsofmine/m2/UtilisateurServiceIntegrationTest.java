package friendsofmine.m2;

import friendsofmine.m2.domain.Utilisateur;
import friendsofmine.m2.services.UtilisateurService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UtilisateurServiceIntegrationTest {

    @Autowired
    private UtilisateurService utilisateurService;

    private Utilisateur util, util1;

    @Before
    public void setup() {
        util = new Utilisateur("nom", "prenom", "toto@toto.fr", "F");
        util1 = new Utilisateur("nom1", "prenom1", "tutu@tutu.fr", "M");
        utilisateurService.saveUtilisateur(util1);
    }

    @Test
    public void testSavedUtilisateurHasId(){
        // given: un Utilisateur non persisté util
        // then: util n'a pas d'id
        assertNull(util.getId());
        // when: util est persistée
        utilisateurService.saveUtilisateur(util);
        // then: util a id
        assertNotNull(util.getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveUtilisateurNull(){
        // when: null est persisté via un UtilisateurService
        // then: une exception IllegalArgumentException est levée
        utilisateurService.saveUtilisateur(null);
    }

    @Test
    public void testFetchedUtilisateurIsNotNull() {
        // given: un Utilisateur persisté util1
        // when: on appelle findUtilisateurById avec l'id de cet Utilisateur
        Utilisateur fetched = utilisateurService.findUtilisateurById(util1.getId());
        // then: le résultat n'est pas null
        assertNotNull(fetched);
    }

    @Test
    public void testFetchedUtilisateurHasGoodId() {
        // given: un Utilisateur persisté util1
        // when: on appelle findUtilisateurById avec l'id de cet Utilisateur
        Utilisateur fetched = utilisateurService.findUtilisateurById(util1.getId());
        // then: l'Utilisateur obtenu en retour a le bon id
        assertEquals(util1.getId(), fetched.getId());
    }

    @Test
    public void testFetchedUtilisateurIsUnchanged() {
        // given: un Utilisateur persisté util1
        // when: on appelle findUtilisateurById avec l'id de cet Utilisateur
        Utilisateur fetched = utilisateurService.findUtilisateurById(util1.getId());
        // then: les attributs de l'Utilisateur obtenu en retour a les bonnes valeurs
        assertEquals(util1.getNom(), fetched.getNom());
        assertEquals(util1.getPrenom(), fetched.getPrenom());
        assertEquals(util1.getEmail(), fetched.getEmail());
        assertEquals(util1.getSexe(), fetched.getSexe());
    }

    @Test
    public void testUpdatedUtilisateurIsUpdated() {
        // given: un Utilisateur persisté util1
        Utilisateur fetched = utilisateurService.findUtilisateurById(util1.getId());
        // when: l'email est modifié au niveau "objet"
        fetched.setEmail("tyty@tyty.fr");
        // when: l'objet util1 est mis à jour en base
        utilisateurService.saveUtilisateur(fetched);
        // when: l'objet act1 est relu en base
        Utilisateur fetchedUpdated = utilisateurService.findUtilisateurById(fetched.getId());
        // then: l'email a bien été mis à jour
        assertEquals(fetched.getEmail(), fetchedUpdated.getEmail());
    }

    @Test
    public void testSavedUtilisateurIsSaved() {
        long before = utilisateurService.countUtilisateur();
        // given: un nouvel Utilisateur
        // when: cet Utilisateur est persisté
        utilisateurService.saveUtilisateur(new Utilisateur("john", "john", "john@john.fr", "M"));
        // le nombre d'Utilisateur persisté a augmenté de 1
        assertEquals(before + 1, utilisateurService.countUtilisateur());
    }

    @Test
    public void testUpdateDoesNotCreateANewEntry() {
        long count = utilisateurService.countUtilisateur();
        // given: un Utilisateur persisté util1
        Utilisateur fetched = utilisateurService.findUtilisateurById(util1.getId());
        // when: l'email est modifié au niveau "objet"
        fetched.setEmail("titi@titi.fr");
        // when: l'objet est mis à jour en base
        utilisateurService.saveUtilisateur(fetched);
        // then: une nouvelle entrée n'a pas été créée en base
        assertEquals(count, utilisateurService.countUtilisateur());
    }

    @Test
    public void testFindUtilisateurWithUnexistingId() {
        // when:  findUtilisateurById est appelé avec un id ne correspondant à aucun objet en base
        // then: null est retourné
        assertNull(utilisateurService.findUtilisateurById(1000L));
    }

}