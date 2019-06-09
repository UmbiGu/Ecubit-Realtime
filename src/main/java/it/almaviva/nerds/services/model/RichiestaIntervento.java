package it.almaviva.nerds.services.model;


import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Table(name = "intervento")
@Entity
public class RichiestaIntervento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo;

    private String descrizione;

    private String  gruppo;

    @JsonFormat(pattern="dd-MM-YYYY hh:mm")
    private Date inizio;

    @JsonFormat(pattern="dd-MM-YYYY hh:mm")
    private Date fine;

    @OneToMany(mappedBy = "intervento", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("userId ASC")
    private Set<RichiestaInterventoUser> richieste = new HashSet<RichiestaInterventoUser>(0) {
    };

    public RichiestaIntervento() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getGruppo() {
        return gruppo;
    }

    public void setGruppo(String gruppo) {
        this.gruppo = gruppo;
    }

    public Date getInizio() {
        return inizio;
    }

    public void setInizio(Date inizio) {
        this.inizio = inizio;
    }

    public Date getFine() {
        return fine;
    }

    public void setFine(Date fine) {
        this.fine = fine;
    }

    public Boolean getIsAssigned() {

        for (RichiestaInterventoUser intUser : richieste)
            if (intUser.getAssegnato() == true)
                return true;
        return false;
    }

    public String getAssigned() {
        for (RichiestaInterventoUser intUser : richieste)
            if (intUser.getAssegnato() == true)
                return intUser.getUserId();
        return null;
    }

    public void addRichiesta(RichiestaInterventoUser userReq) {
        richieste.add(userReq);
    }

    public Set<RichiestaInterventoUser> getRichieste() {
        return richieste;
    }

    public void setRichieste(Set<RichiestaInterventoUser> richieste) {
        this.richieste = richieste;
    }

//    public ArrayList<String> getUtenti() {
//        return utenti;
//    }
//
//    public void setUtenti(ArrayList<String> utenti) {
//        this.utenti = utenti;
//    }
}
