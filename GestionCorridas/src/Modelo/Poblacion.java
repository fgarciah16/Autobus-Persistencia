/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jesus
 */
@Entity
@Table(name = "POBLACION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Poblacion.findAll", query = "SELECT p FROM Poblacion p")
    , @NamedQuery(name = "Poblacion.findByIdpob", query = "SELECT p FROM Poblacion p WHERE p.idpob = :idpob")
    , @NamedQuery(name = "Poblacion.findByNombre", query = "SELECT p FROM Poblacion p WHERE p.nombre = :nombre")})
public class Poblacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDPOB")
    private Integer idpob;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @JoinColumn(name = "IDMPO", referencedColumnName = "IDMPO")
    @ManyToOne
    private Municipio idmpo;
    @OneToMany(mappedBy = "destino")
    private List<Corridas> corridasList;
    @OneToMany(mappedBy = "origen")
    private List<Corridas> corridasList1;

    public Poblacion() {
    }

    public Poblacion(Integer idpob) {
        this.idpob = idpob;
    }

    public Poblacion(Integer idpob, String nombre) {
        this.idpob = idpob;
        this.nombre = nombre;
    }

    public Integer getIdpob() {
        return idpob;
    }

    public void setIdpob(Integer idpob) {
        this.idpob = idpob;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Municipio getIdmpo() {
        return idmpo;
    }

    public void setIdmpo(Municipio idmpo) {
        this.idmpo = idmpo;
    }

    @XmlTransient
    public List<Corridas> getCorridasList() {
        return corridasList;
    }

    public void setCorridasList(List<Corridas> corridasList) {
        this.corridasList = corridasList;
    }

    @XmlTransient
    public List<Corridas> getCorridasList1() {
        return corridasList1;
    }

    public void setCorridasList1(List<Corridas> corridasList1) {
        this.corridasList1 = corridasList1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpob != null ? idpob.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Poblacion)) {
            return false;
        }
        Poblacion other = (Poblacion) object;
        if ((this.idpob == null && other.idpob != null) || (this.idpob != null && !this.idpob.equals(other.idpob))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Poblacion[ idpob=" + idpob + " ]";
    }
    
}
