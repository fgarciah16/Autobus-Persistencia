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
@Table(name = "MUNICIPIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Municipio.findAll", query = "SELECT m FROM Municipio m")
    , @NamedQuery(name = "Municipio.findByIdmpo", query = "SELECT m FROM Municipio m WHERE m.idmpo = :idmpo")
    , @NamedQuery(name = "Municipio.findByNombre", query = "SELECT m FROM Municipio m WHERE m.nombre = :nombre")
    , @NamedQuery(name = "Municipio.findByHabitantes", query = "SELECT m FROM Municipio m WHERE m.habitantes = :habitantes")})
public class Municipio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDMPO")
    private Integer idmpo;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "HABITANTES")
    private Integer habitantes;
    @OneToMany(mappedBy = "idmpo")
    private List<Poblacion> poblacionList;

    public Municipio() {
    }

    public Municipio(Integer idmpo) {
        this.idmpo = idmpo;
    }

    public Municipio(Integer idmpo, String nombre) {
        this.idmpo = idmpo;
        this.nombre = nombre;
    }

    public Integer getIdmpo() {
        return idmpo;
    }

    public void setIdmpo(Integer idmpo) {
        this.idmpo = idmpo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getHabitantes() {
        return habitantes;
    }

    public void setHabitantes(Integer habitantes) {
        this.habitantes = habitantes;
    }

    @XmlTransient
    public List<Poblacion> getPoblacionList() {
        return poblacionList;
    }

    public void setPoblacionList(List<Poblacion> poblacionList) {
        this.poblacionList = poblacionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idmpo != null ? idmpo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Municipio)) {
            return false;
        }
        Municipio other = (Municipio) object;
        if ((this.idmpo == null && other.idmpo != null) || (this.idmpo != null && !this.idmpo.equals(other.idmpo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Municipio[ idmpo=" + idmpo + " ]";
    }
    
}
