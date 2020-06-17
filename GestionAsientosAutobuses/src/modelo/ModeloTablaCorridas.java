/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import acceso_datos.ReservaJpaController;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author jesus
 */
public class ModeloTablaCorridas extends AbstractTableModel{
    private List <Corridas> datos;
private String encabezado[];
private ReservaJpaController reserva;
private EntityManagerFactory emf;
public ModeloTablaCorridas(List<Corridas> corridas,String nomColumnas[]){
	datos=corridas;
	encabezado= nomColumnas;
        emf=Persistence.createEntityManagerFactory("GestionAsientosAutobusesPU");
        reserva= new  ReservaJpaController(emf);
}

 @Override
	public String getColumnName(int i){
		return encabezado[i];
	}
 @Override 
	public Object getValueAt(int r, int c){
		switch(c){
			case 0: return datos.get(r).getIdcorrida();
			case 1: return datos.get(r).getOrigen().getNombre();
			case 2: return datos.get(r).getDestino().getNombre();
			case 3: DateFormat formatoFecha= new SimpleDateFormat("dd/MM/yyyy");
				return formatoFecha.format(datos.get(r).getFechaHora());
			case 4: DateFormat formatoHora= new SimpleDateFormat("HH:mm");
				return formatoHora.format(datos.get(r).getFechaHora());
			case 5: return datos.get(r).getAutobus().getTipo();
			case 6: return datos.get(r).getCosto();
			default: 
                            List<Reserva> res=reserva.findReservaEntities();
                            ArrayList<Integer> asientosO=new ArrayList<Integer>();
                            for(Reserva r1 : res){
                                if(datos.get(r).getIdcorrida()==r1.getIdcorrida().getIdcorrida()){
                                    asientosO.add(r1.getNum());
                                }
                            }
                            return datos.get(r).getAutobus().getAsientos()-asientosO.size();
 
		}
	}

 @Override 
	public int getRowCount(){
		return datos.size();
	}
 @Override 
	public int getColumnCount(){
		return encabezado.length;
	}
}
