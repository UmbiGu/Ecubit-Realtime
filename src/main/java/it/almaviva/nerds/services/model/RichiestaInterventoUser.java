package it.almaviva.nerds.services.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

@Table(name="intervento_user")
@Entity
public class RichiestaInterventoUser {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "intervento_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private RichiestaIntervento intervento;

    private String userId;

    @JsonFormat(pattern="dd-MM-YYYY hh:mm")
    private Date dataLettura;

    private Boolean assegnato = false;

    @JsonFormat(pattern="dd-MM-YYYY hh:mm")
    private Date dataAssegnazione;

    private String motivazione;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RichiestaIntervento getIntervento() {
        return intervento;
    }

    public void setIntervento(RichiestaIntervento intervento) {
        this.intervento = intervento;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getDataLettura() {
        return dataLettura;
    }

    public void setDataLettura(Date dataLettura) {
        this.dataLettura = dataLettura;
    }

    public Boolean getAssegnato() {
        return assegnato;
    }

    public void setAssegnato(Boolean assegnato) {
        this.assegnato = assegnato;
    }

    public Date getDataAssegnazione() {
        return dataAssegnazione;
    }

    public void setDataAssegnazione(Date dataAssegnazione) {
        this.dataAssegnazione = dataAssegnazione;
    }

    public String getMotivazione() {
        return motivazione;
    }

    public void setMotivazione(String motivazione) {
        this.motivazione = motivazione;
    }
}
