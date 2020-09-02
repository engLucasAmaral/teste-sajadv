/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.api.softplan.sajadv.regras;

import br.com.api.softplan.sajadv.entity.Pessoa;
import br.com.api.softplan.sajadv.exception.APIException;
import java.util.Objects;
import java.util.regex.Pattern;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RequestScoped
public class RNValidaEmail {

    private Logger log = LogManager.getLogger();
   
    private String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private Pattern pattern = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);

    public void validar(@Observes Pessoa pessoa) throws APIException {
        log.info("Verificando email");

        if (Objects.nonNull(pessoa)) {

            if (Objects.isNull(pessoa.getEmail()) || "".equals(pessoa.getEmail()) || !pattern.matcher(pessoa.getEmail()).matches()) {
                throw new APIException(20, "Email da pessoa invalido!");
            }
        }
    }
}
