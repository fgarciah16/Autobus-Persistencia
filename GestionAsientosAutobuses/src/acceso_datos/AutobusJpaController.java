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
import modelo.Corridas;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Autobus;

/**
 *
 * @author jesus
 */
public class AutobusJpaController implements Serializable {

    public AutobusJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Autobus autobus) {
        if (autobus.getCorridasList() == null) {
            autobus.setCorridasList(new ArrayList<Corridas>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Corridas> attachedCorridasList = new ArrayList<Corridas>();
            for (Corridas corridasListCorridasToAttach : autobus.getCorridasList()) {
                corridasListCorridasToAttach = em.getReference(corridasListCorridasToAttach.getClass(), corridasListCorridasToAttach.getIdcorrida());
                attachedCorridasList.add(corridasListCorridasToAttach);
            }
            autobus.setCorridasList(attachedCorridasList);
            em.persist(autobus);
            for (Corridas corridasListCorridas : autobus.getCorridasList()) {
                Autobus oldAutobusOfCorridasListCorridas = corridasListCorridas.getAutobus();
                corridasListCorridas.setAutobus(autobus);
                corridasListCorridas = em.merge(corridasListCorridas);
                if (oldAutobusOfCorridasListCorridas != null) {
                    oldAutobusOfCorridasListCorridas.getCorridasList().remove(corridasListCorridas);
                    oldAutobusOfCorridasListCorridas = em.merge(oldAutobusOfCorridasListCorridas);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Autobus autobus) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Autobus persistentAutobus = em.find(Autobus.class, autobus.getIdautobus());
            List<Corridas> corridasListOld = persistentAutobus.getCorridasList();
            List<Corridas> corridasListNew = autobus.getCorridasList();
            List<Corridas> attachedCorridasListNew = new ArrayList<Corridas>();
            for (Corridas corridasListNewCorridasToAttach : corridasListNew) {
                corridasListNewCorridasToAttach = em.getReference(corridasListNewCorridasToAttach.getClass(), corridasListNewCorridasToAttach.getIdcorrida());
                attachedCorridasListNew.add(corridasListNewCorridasToAttach);
            }
            corridasListNew = attachedCorridasListNew;
            autobus.setCorridasList(corridasListNew);
            autobus = em.merge(autobus);
            for (Corridas corridasListOldCorridas : corridasListOld) {
                if (!corridasListNew.contains(corridasListOldCorridas)) {
                    corridasListOldCorridas.setAutobus(null);
                    corridasListOldCorridas = em.merge(corridasListOldCorridas);
                }
            }
            for (Corridas corridasListNewCorridas : corridasListNew) {
                if (!corridasListOld.contains(corridasListNewCorridas)) {
                    Autobus oldAutobusOfCorridasListNewCorridas = corridasListNewCorridas.getAutobus();
                    corridasListNewCorridas.setAutobus(autobus);
                    corridasListNewCorridas = em.merge(corridasListNewCorridas);
                    if (oldAutobusOfCorridasListNewCorridas != null && !oldAutobusOfCorridasListNewCorridas.equals(autobus)) {
                        oldAutobusOfCorridasListNewCorridas.getCorridasList().remove(corridasListNewCorridas);
                        oldAutobusOfCorridasListNewCorridas = em.merge(oldAutobusOfCorridasListNewCorridas);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = autobus.getIdautobus();
                if (findAutobus(id) == null) {
                    throw new NonexistentEntityException("The autobus with id " + id + " no longer exists.");
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
            Autobus autobus;
            try {
                autobus = em.getReference(Autobus.class, id);
                autobus.getIdautobus();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The autobus with id " + id + " no longer exists.", enfe);
            }
            List<Corridas> corridasList = autobus.getCorridasList();
            for (Corridas corridasListCorridas : corridasList) {
                corridasListCorridas.setAutobus(null);
                corridasListCorridas = em.merge(corridasListCorridas);
            }
            em.remove(autobus);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Autobus> findAutobusEntities() {
        return findAutobusEntities(true, -1, -1);
    }

    public List<Autobus> findAutobusEntities(int maxResults, int firstResult) {
        return findAutobusEntities(false, maxResults, firstResult);
    }

    private List<Autobus> findAutobusEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Autobus.class));
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

    public Autobus findAutobus(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Autobus.class, id);
        } finally {
            em.close();
        }
    }

    public int getAutobusCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Autobus> rt = cq.from(Autobus.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
