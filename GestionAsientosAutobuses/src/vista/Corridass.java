/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import acceso_datos.CorridasJpaController;
import acceso_datos.ReservaJpaController;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import modelo.ModeloTablaCorridas;
import modelo.Reserva;
import modelo.Corridas;

/**
 *
 * @author jesus
 */
public class Corridass extends javax.swing.JPanel {
 private List<modelo.Corridas> corridas;
    private String[] encabezados={"Num.Corrida","Origen","Destino","Fecha","Hora","Clase Autobus","Precio","Lugares"};
    private Principal inicio;
    private vista.Reserva reserva;
    private ModeloTablaCorridas modeloTC;
    private modelo.Corridas corrida;
    private int numCorSel;
    private EntityManagerFactory emf;
    private CorridasJpaController controladorCorrida;
    private ReservaJpaController controladorReserva;
    
    
    public Corridass() {
         emf=Persistence.createEntityManagerFactory("GestionAsientosAutobusesPU");
        controladorCorrida = new CorridasJpaController(emf);
        controladorReserva = new  ReservaJpaController(emf);
        initComponents();
         corridas = controladorCorrida.findCorridasEntities();
        modeloTC = new ModeloTablaCorridas(corridas,encabezados);
        TablaCorridas.setModel(modeloTC);
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        TablaCorridas = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        TablaCorridas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(TablaCorridas);

        jButton1.setText("Aceptar seleccion");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Todas las corridas");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(136, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(97, 97, 97))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(246, 246, 246))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(309, 309, 309))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1)
                .addGap(40, 40, 40)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(39, 39, 39))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
         int numCorrida=(Integer)modeloTC.getValueAt(TablaCorridas.getSelectedRow(),0);
        for(int c=0;c<corridas.size();c++){
          if((Integer)corridas.get(c).getIdcorrida()==numCorrida){
            numCorSel=c;
            reserva.getSelAsi().setNumAsientos(corridas.get(c).getAutobus().getAsientos());
            reserva.getSelAsi().setAsignar(getAsientosOcupados(corridas.get(c)));
            break;
          }
          corrida=corridas.get(c);
        }
        
    }//GEN-LAST:event_jButton1ActionPerformed
  
    
     public void conectaInicio(Principal inicio){
        this.inicio=inicio;
    }
    public void conectaReserva(vista.Reserva reserva){
        this.reserva = reserva;
    }
    public void seleccionarCorrida(String cO,String cD,Date fes){
        List<modelo.Corridas> corriSal= new ArrayList<modelo.Corridas>();
        if(inicio.getTodaslasCorridas().isSelected())
            corriSal=corridas;
        else 
            for(int nc=0;nc<corridas.size();nc++){
                if(corridas.get(nc).getOrigen().getNombre().equalsIgnoreCase(cO) &&
                        corridas.get(nc).getDestino().getNombre().equalsIgnoreCase(cD) &&
                        compararDMA(corridas.get(nc).getFechaHora(),fes)){
                            corriSal.add(corridas.get(nc));
                        }
                }
               modeloTC= new ModeloTablaCorridas(corriSal,encabezados);
               TablaCorridas.setModel(modeloTC);
            }
        
    public ArrayList<Integer> getAsientosOcupados(modelo.Corridas c){
        List<Reserva> res=controladorReserva.findReservaEntities();
        ArrayList<Integer> asientosO=new ArrayList<Integer>();
             for(Reserva r1 : res){
                if(c.getIdcorrida()==r1.getIdcorrida().getIdcorrida()){
                        asientosO.add(r1.getNum());
                }
             }
             return asientosO;
    }
    
     private boolean compararDMA(Date fecha, Date fes) {
          DateFormat formatoFecha= new SimpleDateFormat("dd/MM/yyyy");
         System.out.println(fecha);
         System.out.println(fes);
         System.out.println(formatoFecha.format(fecha).equals(formatoFecha.format(fes)));
        return formatoFecha.format(fecha).equals(formatoFecha.format(fes));
    }
     
    public modelo.Corridas getCorrida(){
        corrida=corridas.get(numCorSel);
        System.out.println(numCorSel);
        System.out.println(corrida.getDestino());
        return corrida;
    }
    public int getNumeroCorridaSeleccionada(){
        return numCorSel;
    }
    public List<modelo.Corridas> getCorridas(){
        return corridas;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TablaCorridas;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
