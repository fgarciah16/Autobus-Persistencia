/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jesus
 */
@Entity
@Table(name = "RESERVA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reserva.findAll", query = "SELECT r FROM Reserva r")
    , @NamedQuery(name = "Reserva.findByIdre", query = "SELECT r FROM Reserva r WHERE r.idre = :idre")
    , @NamedQuery(name = "Reserva.findByNum", query = "SELECT r FROM Reserva r WHERE r.num = :num")})
public class Reserva implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDRE")
    private Integer idre;
    @Basic(optional = false)
    @Column(name = "NUM")
    private int num;
    @JoinColumn(name = "IDCORRIDA", referencedColumnName = "IDCORRIDA")
    @ManyToOne
    private Corridas idcorrida;

    public Reserva() {
    }

    public Reserva(Integer idre) {
        this.idre = idre;
    }

    public Reserva(Integer idre, int num) {
        this.idre = idre;
        this.num = num;
    }

    public Integer getIdre() {
        return idre;
    }

    public void setIdre(Integer idre) {
        this.idre = idre;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Corridas getIdcorrida() {
        return idcorrida;
    }

    public void setIdcorrida(Corridas idcorrida) {
        this.idcorrida = idcorrida;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idre != null ? idre.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reserva)) {
            return false;
        }
        Reserva other = (Reserva) object;
        if ((this.idre == null && other.idre != null) || (this.idre != null && !this.idre.equals(other.idre))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Reserva[ idre=" + idre + " ]";
    }
    
}
