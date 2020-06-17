/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acceso_datos;

import acceso_datos.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Municipio;
import modelo.Corridas;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Poblacion;

/**
 *
 * @author jesus
 */
public class PoblacionJpaController implements Serializable {

    public PoblacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Poblacion poblacion) {
        if (poblacion.getCorridasList() == null) {
            poblacion.setCorridasList(new ArrayList<Corridas>());
        }
        if (poblacion.getCorridasList1() == null) {
            poblacion.setCorridasList1(new ArrayList<Corridas>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Municipio idmpo = poblacion.getIdmpo();
            if (idmpo != null) {
                idmpo = em.getReference(idmpo.getClass(), idmpo.getIdmpo());
                poblacion.setIdmpo(idmpo);
            }
            List<Corridas> attachedCorridasList = new ArrayList<Corridas>();
            for (Corridas corridasListCorridasToAttach : poblacion.getCorridasList()) {
                corridasListCorridasToAttach = em.getReference(corridasListCorridasToAttach.getClass(), corridasListCorridasToAttach.getIdcorrida());
                attachedCorridasList.add(corridasListCorridasToAttach);
            }
            poblacion.setCorridasList(attachedCorridasList);
            List<Corridas> attachedCorridasList1 = new ArrayList<Corridas>();
            for (Corridas corridasList1CorridasToAttach : poblacion.getCorridasList1()) {
                corridasList1CorridasToAttach = em.getReference(corridasList1CorridasToAttach.getClass(), corridasList1CorridasToAttach.getIdcorrida());
                attachedCorridasList1.add(corridasList1CorridasToAttach);
            }
            poblacion.setCorridasList1(attachedCorridasList1);
            em.persist(poblacion);
            if (idmpo != null) {
                idmpo.getPoblacionList().add(poblacion);
                idmpo = em.merge(idmpo);
            }
            for (Corridas corridasListCorridas : poblacion.getCorridasList()) {
                Poblacion oldDestinoOfCorridasListCorridas = corridasListCorridas.getDestino();
                corridasListCorridas.setDestino(poblacion);
                corridasListCorridas = em.merge(corridasListCorridas);
                if (oldDestinoOfCorridasListCorridas != null) {
                    oldDestinoOfCorridasListCorridas.getCorridasList().remove(corridasListCorridas);
                    oldDestinoOfCorridasListCorridas = em.merge(oldDestinoOfCorridasListCorridas);
                }
            }
            for (Corridas corridasList1Corridas : poblacion.getCorridasList1()) {
                Poblacion oldOrigenOfCorridasList1Corridas = corridasList1Corridas.getOrigen();
                corridasList1Corridas.setOrigen(poblacion);
                corridasList1Corridas = em.merge(corridasList1Corridas);
                if (oldOrigenOfCorridasList1Corridas != null) {
                    oldOrigenOfCorridasList1Corridas.getCorridasList1().remove(corridasList1Corridas);
                    oldOrigenOfCorridasList1Corridas = em.merge(oldOrigenOfCorridasList1Corridas);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Poblacion poblacion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Poblacion persistentPoblacion = em.find(Poblacion.class, poblacion.getIdpob());
            Municipio idmpoOld = persistentPoblacion.getIdmpo();
            Municipio idmpoNew = poblacion.getIdmpo();
            List<Corridas> corridasListOld = persistentPoblacion.getCorridasList();
            List<Corridas> corridasListNew = poblacion.getCorridasList();
            List<Corridas> corridasList1Old = persistentPoblacion.getCorridasList1();
            List<Corridas> corridasList1New = poblacion.getCorridasList1();
            if (idmpoNew != null) {
                idmpoNew = em.getReference(idmpoNew.getClass(), idmpoNew.getIdmpo());
                poblacion.setIdmpo(idmpoNew);
            }
            List<Corridas> attachedCorridasListNew = new ArrayList<Corridas>();
            for (Corridas corridasListNewCorridasToAttach : corridasListNew) {
                corridasListNewCorridasToAttach = em.getReference(corridasListNewCorridasToAttach.getClass(), corridasListNewCorridasToAttach.getIdcorrida());
                attachedCorridasListNew.add(corridasListNewCorridasToAttach);
            }
            corridasListNew = attachedCorridasListNew;
            poblacion.setCorridasList(corridasListNew);
            List<Corridas> attachedCorridasList1New = new ArrayList<Corridas>();
            for (Corridas corridasList1NewCorridasToAttach : corridasList1New) {
                corridasList1NewCorridasToAttach = em.getReference(corridasList1NewCorridasToAttach.getClass(), corridasList1NewCorridasToAttach.getIdcorrida());
                attachedCorridasList1New.add(corridasList1NewCorridasToAttach);
            }
            corridasList1New = attachedCorridasList1New;
            poblacion.setCorridasList1(corridasList1New);
            poblacion = em.merge(poblacion);
            if (idmpoOld != null && !idmpoOld.equals(idmpoNew)) {
                idmpoOld.getPoblacionList().remove(poblacion);
                idmpoOld = em.merge(idmpoOld);
            }
            if (idmpoNew != null && !idmpoNew.equals(idmpoOld)) {
                idmpoNew.getPoblacionList().add(poblacion);
                idmpoNew = em.merge(idmpoNew);
            }
            for (Corridas corridasListOldCorridas : corridasListOld) {
                if (!corridasListNew.contains(corridasListOldCorridas)) {
                    corridasListOldCorridas.setDestino(null);
                    corridasListOldCorridas = em.merge(corridasListOldCorridas);
                }
            }
            for (Corridas corridasListNewCorridas : corridasListNew) {
                if (!corridasListOld.contains(corridasListNewCorridas)) {
                    Poblacion oldDestinoOfCorridasListNewCorridas = corridasListNewCorridas.getDestino();
                    corridasListNewCorridas.setDestino(poblacion);
                    corridasListNewCorridas = em.merge(corridasListNewCorridas);
                    if (oldDestinoOfCorridasListNewCorridas != null && !oldDestinoOfCorridasListNewCorridas.equals(poblacion)) {
                        oldDestinoOfCorridasListNewCorridas.getCorridasList().remove(corridasListNewCorridas);
                        oldDestinoOfCorridasListNewCorridas = em.merge(oldDestinoOfCorridasListNewCorridas);
                    }
                }
            }
            for (Corridas corridasList1OldCorridas : corridasList1Old) {
                if (!corridasList1New.contains(corridasList1OldCorridas)) {
                    corridasList1OldCorridas.setOrigen(null);
                    corridasList1OldCorridas = em.merge(corridasList1OldCorridas);
                }
            }
            for (Corridas corridasList1NewCorridas : corridasList1New) {
                if (!corridasList1Old.contains(corridasList1NewCorridas)) {
                    Poblacion oldOrigenOfCorridasList1NewCorridas = corridasList1NewCorridas.getOrigen();
                    corridasList1NewCorridas.setOrigen(poblacion);
                    corridasList1NewCorridas = em.merge(corridasList1NewCorridas);
                    if (oldOrigenOfCorridasList1NewCorridas != null && !oldOrigenOfCorridasList1NewCorridas.equals(poblacion)) {
                        oldOrigenOfCorridasList1NewCorridas.getCorridasList1().remove(corridasList1NewCorridas);
                        oldOrigenOfCorridasList1NewCorridas = em.merge(oldOrigenOfCorridasList1NewCorridas);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = poblacion.getIdpob();
                if (findPoblacion(id) == null) {
                    throw new NonexistentEntityException("The poblacion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Poblacion poblacion;
            try {
                poblacion = em.getReference(Poblacion.class, id);
                poblacion.getIdpob();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The poblacion with id " + id + " no longer exists.", enfe);
            }
            Municipio idmpo = poblacion.getIdmpo();
            if (idmpo != null) {
                idmpo.getPoblacionList().remove(poblacion);
                idmpo = em.merge(idmpo);
            }
            List<Corridas> corridasList = poblacion.getCorridasList();
            for (Corridas corridasListCorridas : corridasList) {
                corridasListCorridas.setDestino(null);
                corridasListCorridas = em.merge(corridasListCorridas);
            }
            List<Corridas> corridasList1 = poblacion.getCorridasList1();
            for (Corridas corridasList1Corridas : corridasList1) {
                corridasList1Corridas.setOrigen(null);
                corridasList1Corridas = em.merge(corridasList1Corridas);
            }
            em.remove(poblacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Poblacion> findPoblacionEntities() {
        return findPoblacionEntities(true, -1, -1);
    }

    public List<Poblacion> findPoblacionEntities(int maxResults, int firstResult) {
        return findPoblacionEntities(false, maxResults, firstResult);
    }

    private List<Poblacion> findPoblacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Poblacion.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Poblacion findPoblacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Poblacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getPoblacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Poblacion> rt = cq.from(Poblacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
