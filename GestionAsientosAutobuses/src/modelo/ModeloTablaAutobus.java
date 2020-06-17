/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author jesus
 */
public class ModeloTablaAutobus extends AbstractTableModel {
    private List <Autobus> datos;
    private String encabezado[];

    public ModeloTablaAutobus(List<Autobus> corridas, String[] nomColumnas) {
        this.datos = corridas;
        this.encabezado = nomColumnas;
    }
    
    @Override
    public String getColumnName(int i){
        return encabezado[i];
    }
    
    @Override
    public Object getValueAt(int r, int c) {
        switch (c){
            case 0: return datos.get(r).getIdautobus();
            case 1: return datos.get(r).getTipo();
            default: return datos.get(r).getAsientos();
        }
    }
    @Override
    public int getRowCount() {
        return datos.size();
    }

    @Override
    public int getColumnCount() {
        return encabezado.length;
    }
}
