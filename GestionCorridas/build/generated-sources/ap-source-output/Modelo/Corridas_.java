package Modelo;

import Modelo.Autobus;
import Modelo.Poblacion;
import Modelo.Reserva;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-06-20T21:15:35")
@StaticMetamodel(Corridas.class)
public class Corridas_ { 

    public static volatile SingularAttribute<Corridas, Integer> costo;
    public static volatile ListAttribute<Corridas, Reserva> reservaList;
    public static volatile SingularAttribute<Corridas, Date> fechaHora;
    public static volatile SingularAttribute<Corridas, Integer> idcorrida;
    public static volatile SingularAttribute<Corridas, Autobus> autobus;
    public static volatile SingularAttribute<Corridas, Poblacion> destino;
    public static volatile SingularAttribute<Corridas, Poblacion> origen;

}