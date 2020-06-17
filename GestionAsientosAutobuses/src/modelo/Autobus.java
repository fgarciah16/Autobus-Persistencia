/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jesus
 */
@Entity
@Table(name = "AUTOBUS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Autobus.findAll", query = "SELECT a FROM Autobus a")
    , @NamedQuery(name = "Autobus.findByIdautobus", query = "SELECT a FROM Autobus a WHERE a.idautobus = :idautobus")
    , @NamedQuery(name = "Autobus.findByTipo", query = "SELECT a FROM Autobus a WHERE a.tipo = :tipo")
    , @NamedQuery(name = "Autobus.findByAsientos", query = "SELECT a FROM Autobus a WHERE a.asientos = :asientos")})
public class Autobus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDAUTOBUS")
    private Integer idautobus;
    @Basic(optional = false)
    @Column(name = "TIPO")
    private String tipo;
    @Column(name = "ASIENTOS")
    private Integer asientos;
    @OneToMany(mappedBy = "autobus")
    private List<Corridas> corridasList;

    public Autobus() {
    }

    public Autobus(Integer idautobus) {
        this.idautobus = idautobus;
    }

    public Autobus(Integer idautobus, String tipo) {
        this.idautobus = idautobus;
        this.tipo = tipo;
    }

    public Integer getIdautobus() {
        return idautobus;
    }

    public void setIdautobus(Integer idautobus) {
        this.idautobus = idautobus;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getAsientos() {
        return asientos;
    }

    public void setAsientos(Integer asientos) {
        this.asientos = asientos;
    }

    @XmlTransient
    public List<Corridas> getCorridasList() {
        return corridasList;
    }

    public void setCorridasList(List<Corridas> corridasList) {
        this.corridasList = corridasList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idautobus != null ? idautobus.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Autobus)) {
            return false;
        }
        Autobus other = (Autobus) object;
        if ((this.idautobus == null && other.idautobus != null) || (this.idautobus != null && !this.idautobus.equals(other.idautobus))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Autobus[ idautobus=" + idautobus + " ]";
    }
    
}
