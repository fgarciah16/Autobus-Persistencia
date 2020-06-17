/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jesus
 */
@Entity
@Table(name = "CORRIDAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Corridas.findAll", query = "SELECT c FROM Corridas c")
    , @NamedQuery(name = "Corridas.findByIdcorrida", query = "SELECT c FROM Corridas c WHERE c.idcorrida = :idcorrida")
    , @NamedQuery(name = "Corridas.findByFechaHora", query = "SELECT c FROM Corridas c WHERE c.fechaHora = :fechaHora")
    , @NamedQuery(name = "Corridas.findByCosto", query = "SELECT c FROM Corridas c WHERE c.costo = :costo")})
public class Corridas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDCORRIDA")
    private Integer idcorrida;
    @Column(name = "FECHA_HORA")
    @Temporal(TemporalType.DATE)
    private Date fechaHora;
    @Column(name = "COSTO")
    private Integer costo;
    @JoinColumn(name = "AUTOBUS", referencedColumnName = "IDAUTOBUS")
    @ManyToOne
    private Autobus autobus;
    @JoinColumn(name = "DESTINO", referencedColumnName = "IDPOB")
    @ManyToOne
    private Poblacion destino;
    @JoinColumn(name = "ORIGEN", referencedColumnName = "IDPOB")
    @ManyToOne
    private Poblacion origen;
    @OneToMany(mappedBy = "idcorrida")
    private List<Reserva> reservaList;

    public Corridas() {
    }

    public Corridas(Integer idcorrida) {
        this.idcorrida = idcorrida;
    }

    public Integer getIdcorrida() {
        return idcorrida;
    }

    public void setIdcorrida(Integer idcorrida) {
        this.idcorrida = idcorrida;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Integer getCosto() {
        return costo;
    }

    public void setCosto(Integer costo) {
        this.costo = costo;
    }

    public Autobus getAutobus() {
        return autobus;
    }

    public void setAutobus(Autobus autobus) {
        this.autobus = autobus;
    }

    public Poblacion getDestino() {
        return destino;
    }

    public void setDestino(Poblacion destino) {
        this.destino = destino;
    }

    public Poblacion getOrigen() {
        return origen;
    }

    public void setOrigen(Poblacion origen) {
        this.origen = origen;
    }

    @XmlTransient
    public List<Reserva> getReservaList() {
        return reservaList;
    }

    public void setReservaList(List<Reserva> reservaList) {
        this.reservaList = reservaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcorrida != null ? idcorrida.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Corridas)) {
            return false;
        }
        Corridas other = (Corridas) object;
        if ((this.idcorrida == null && other.idcorrida != null) || (this.idcorrida != null && !this.idcorrida.equals(other.idcorrida))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Corridas[ idcorrida=" + idcorrida + " ]";
    }
    
}
