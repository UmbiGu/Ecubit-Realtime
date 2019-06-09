package it.almaviva.nerds.services.controller;

import de.gultsch.ejabberd.api.EjabberdApi;
import de.gultsch.ejabberd.api.hla.Ejabberd;
import it.almaviva.nerds.services.model.RichiestaIntervento;
import it.almaviva.nerds.services.model.RichiestaInterventoUser;
import it.almaviva.nerds.services.repository.InterventoRepository;
import it.almaviva.nerds.services.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ImportResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@ImportResource("/xmppOutbound.xml")
public class InterventoRestServiceController {

    @Autowired
    private InterventoRepository interventoRepository;

    @Value("${nerds.host}")
    private String host;

    @Value("${nerds.ejabberd.api}")
    private String ejabberApi;

    @Value("${nerds.ejabberd.multicast}")
    private String multicast;

    @CrossOrigin(maxAge = 3600)
    @GetMapping(path = "/intervento/all")
    public @ResponseBody
    Iterable<RichiestaIntervento> getInterventi() {
        return interventoRepository.findAll();
    }

    @CrossOrigin(maxAge = 3600)
    @GetMapping(path = "/intervento/search/{name}")
    public @ResponseBody
    Iterable<RichiestaIntervento> searchInterventi(String name) {
        ArrayList<RichiestaIntervento> result = new ArrayList<>();
        for (RichiestaIntervento intervento : interventoRepository.findAll()){
            if (intervento.getDescrizione().contains(name))
                result.add(intervento);
        }
        return result;
    }

    @CrossOrigin(maxAge = 3600)
    @PostMapping(path = "/intervento/add") // Map ONLY GET Requests
    public @ResponseBody
    Long addNewIntervento(@RequestBody RichiestaIntervento intervento) throws InterventoException {

        try {

            Ejabberd client = new Ejabberd(new EjabberdApi(ejabberApi));
            Collection<String> result = client.getSharedRosterGroupMembers(host, intervento.getGruppo());
            intervento.setInizio(new Date());

            ArrayList<String> addresses = new ArrayList<>();
            for (String user : result) {
                RichiestaInterventoUser interventoUser = new RichiestaInterventoUser();
                interventoUser.setUserId(user);
                interventoUser.setIntervento(intervento);
                intervento.addRichiesta(interventoUser);
                addresses.add(user);
            }
            interventoRepository.save(intervento);
            String stanza = Util.Message.multicastStanza(
                    "REQUEST",
                    intervento.getId().toString() + "|" + intervento.getDescrizione(), addresses);
            boolean response = client.sendStanza("admin@nerds.almaviva.it", multicast, stanza);
            return intervento.getId();
        } catch (Exception e) {
            throw new InterventoException(e.getMessage());
        }
    }

    @CrossOrigin
    @GetMapping(path = "/intervento/{id}")
    public ResponseEntity<RichiestaIntervento> getIntervento(@PathVariable Long id) throws InterventoException {

        Optional<RichiestaIntervento> intervento = interventoRepository.findById(id);
        if (!intervento.isPresent()) {
            throw new InterventoException("Intervento does not exist");
        }
        return new ResponseEntity<>(intervento.get(), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(path = "/intervento/user/{name}")
    public Iterable<RichiestaIntervento> getInterventoUser(@PathVariable String name) throws InterventoException {

        ArrayList<RichiestaIntervento> interventiUser = new ArrayList();
        Iterable<RichiestaIntervento> interventi = interventoRepository.findAll();
        for (RichiestaIntervento intervento: interventi) {
            for (RichiestaInterventoUser userReq: intervento.getRichieste()) {
                if (userReq.getUserId().startsWith(name)){
                    interventiUser.add(intervento);
                }
            }
        }
        return interventiUser;
    }

    @CrossOrigin
    @PostMapping(path = "/interventoAction/{id}")
    public ResponseEntity<RichiestaIntervento> setIntervento(@PathVariable Long id, @RequestBody RichiestaInterventoUser interventoReq) throws InterventoException {

        try {

            Optional<RichiestaIntervento> interventoRepo = interventoRepository.findById(id);
            if (!interventoRepo.isPresent()) {
                throw new InterventoException("Intervento does not exist");
            }

            RichiestaIntervento intervento = interventoRepo.get();
            Set<RichiestaInterventoUser> richieste = intervento.getRichieste();

            if (interventoReq.getAssegnato() == true) {
                for (RichiestaInterventoUser rUser : richieste) {
                    if (rUser.getAssegnato() == true) {
                        return new ResponseEntity<>(intervento, HttpStatus.NOT_FOUND);
                    }
                }
            }

            ArrayList<String> addresses = new ArrayList<>();
            for (RichiestaInterventoUser rUser : richieste) {
                if (rUser.getUserId().equals(interventoReq.getUserId())) {
                    if (interventoReq.getAssegnato()) {
                        rUser.setAssegnato(true);
                        rUser.setDataAssegnazione(new Date());
                        intervento.setFine(new Date());
                    }
                    rUser.setMotivazione(interventoReq.getMotivazione());
                    rUser.setDataLettura(new Date());
                }
                addresses.add(rUser.getUserId());
            }

            interventoRepository.save(intervento);
            String stanza = Util.Message.multicastStanza(
                    "INTERVENTO_ACTION",
                    intervento.getId().toString(), addresses);
            Ejabberd client = new Ejabberd(new EjabberdApi(ejabberApi));
            boolean response = client.sendStanza("admin@nerds.almaviva.it", multicast, stanza);
            return new ResponseEntity<>(intervento, HttpStatus.OK);
        } catch (Exception e) {
            throw new InterventoException(e.getMessage());
        }
    }

    @CrossOrigin
    @DeleteMapping(path = "/intervento/{id}")
    public ResponseEntity<RichiestaIntervento> deleteIntervento(@PathVariable Long id) throws InterventoException {
        Optional<RichiestaIntervento> intervento = interventoRepository.findById(id);

        if (!intervento.isPresent()) {
            throw new InterventoException("Intervento does not exist");
        }
        interventoRepository.delete(intervento.get());
        return new ResponseEntity<>(intervento.get(), HttpStatus.OK);
    }

    @ExceptionHandler(InterventoException.class)
    public ResponseEntity<ErrorResponse> InterventoExceptionHandler(Exception ex) {
        ErrorResponse error = new ErrorResponse();
        error.setErrorCode(HttpStatus.PRECONDITION_FAILED.value());
        error.setMessage(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.OK);
    }

}
