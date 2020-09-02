/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.api.softplan.sajadv.regras;

import br.com.api.softplan.sajadv.entity.Pessoa;
import br.com.api.softplan.sajadv.exception.APIException;
import java.util.Objects;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RequestScoped
public class RNValidaCPF {

    private final Logger log = LogManager.getLogger();
    @Inject
    private CPFUtil cpfUtils;

    public void validar(@Observes Pessoa pessoa) throws APIException {
        log.debug("Verificando CPF");

        if (Objects.nonNull(pessoa)) {
            if (Objects.isNull(pessoa.getCpf()) || "".equals(pessoa.getCpf()) || !cpfUtils.isValid(pessoa.getCpf())) {
                throw new APIException(40, "CPF da pessoa invalido ou não informado!");
            }
        }
    }
}
