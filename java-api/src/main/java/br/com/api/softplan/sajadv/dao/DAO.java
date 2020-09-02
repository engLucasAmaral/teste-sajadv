package br.com.api.softplan.sajadv.dao;

import br.com.api.softplan.sajadv.entity.Pessoa;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author lucas
 */
@Stateless
@LocalBean
public class DAO extends AbstractDao {

    private Logger log = LogManager.getLogger();

    @Override
    @PersistenceContext(unitName = "databaseAPIPU")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

      public List<Pessoa> buscarTodos(String nome, String cpf, String email, Date nascimento) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery q = cb.createQuery(Pessoa.class);
        Root<Pessoa> root = q.from(Pessoa.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(root.get("ativo").as(Boolean.class), true));
        if (nome != null && !"".equals(nome)) {
            predicates.add(cb.equal(root.get("nome").as(String.class), nome));
        }
        if (cpf != null && !"".equals(cpf)) {
            predicates.add(cb.equal(root.get("cpf").as(String.class), cpf));
        }
        if (email != null && !"".equals(email)) {
            predicates.add(cb.equal(root.get("email").as(String.class), email));
        }
        if (nascimento != null && !"".equals(nascimento)) {
            predicates.add(cb.equal(root.get("nascimento").as(Date.class), nascimento));
        }
        if (predicates.size() > 0) {
            q.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        }

        TypedQuery<Pessoa> qc = entityManager.createQuery(q);
        return qc.getResultList();

    }
}
