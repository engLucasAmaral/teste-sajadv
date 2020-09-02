package br.com.api.softplan.sajadv.service;

import br.com.api.softplan.sajadv.dao.DAO;
import br.com.api.softplan.sajadv.dao.TypeQuery;
import br.com.api.softplan.sajadv.entity.Pessoa;
import br.com.api.softplan.sajadv.exception.APIException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author lucas
 */
@Stateless
@LocalBean
public class Service {

    private final Logger log = LogManager.getLogger();

    @EJB
    private DAO dao;

    public List<Pessoa> buscarTodos(String nome, String cpf, String email, String nascimento) throws APIException {
        Date dataNascimento = null;
        if (nascimento != null) {
            try {
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                dataNascimento = df.parse(nascimento);
            } catch (ParseException e) {
                throw new APIException(0, "Data de nascimento invalida!");
            }
        }
        log.info("Buscando Pessoa por filtro. Nome: {}, CPF: {}, Email: {}, Data: {}", nome, cpf, email, nascimento);
        return dao.buscarTodos(nome, cpf, email, dataNascimento);
    }

    public Pessoa buscarPorId(long id) {
        log.info("Buscando Pessoa por ID: {}", id);
        return (Pessoa) dao.buscarEntidade(Pessoa.class, id);
    }

    public Pessoa buscarPorCPF(String cpf) {
        log.info("Buscando Pessoa por CPF: {}", cpf);
        return (Pessoa) dao.buscarEntidade("Pessoa.buscarPorCPF", TypeQuery.NAMED_QUERY, cpf);
    }

    public Pessoa exclusaoLogica(long id) throws APIException {
        log.info("Exclusão lógica ID: {}", id);
        Pessoa pessoa = (Pessoa) dao.buscarEntidade(Pessoa.class, id);
        if (Objects.isNull(pessoa) || !pessoa.isAtivo()) {
            throw new APIException(0, "Pessoa não encontrada!");
        }

        pessoa.setAtivo(Boolean.FALSE);
        return (Pessoa) dao.update(pessoa);
    }

    public Pessoa inserirPessoa(Pessoa pessoa) throws APIException {
        log.info("Cadastrando pessoa : {}", pessoa);
        return (Pessoa) dao.insert(pessoa);
    }

    public Pessoa atualizar(Pessoa pessoa) throws APIException {
        log.info("Atualizando pessoa : {}", pessoa);
        return (Pessoa) dao.update(pessoa);
    }

    public void validaPessoa(Pessoa pessoa) {

    }

}
