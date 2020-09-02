/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.api.softplan.sajadv.regras;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author lucas
 */
@RequestScoped
public class CPFUtil {

    private final List<String> invalidos = Arrays.asList("00000000000", "11111111111", "22222222222", "33333333333",
            "44444444444", "55555555555", "66666666666", "77777777777", "88888888888", "99999999999");

    public CPFUtil() {
    }

    public boolean isValid(String cpf) {
        if (Objects.isNull(cpf) || "".equals(cpf)) {
            return false;
        }

        String ncpf = cpf.trim();
        if (ncpf.length() != 11 || Long.valueOf(ncpf).equals(0L) || invalidos.contains(ncpf)) {
            return false;
        }

        int d1 = 0, d2 = 0, digito1, digito2, resto;
        int digitoCPF;
        String nDigResult;

        for (int nCount = 1; nCount < ncpf.length() - 1; nCount++) {
            digitoCPF = Integer.valueOf(ncpf.substring(nCount - 1, nCount));
            //multiplique a ultima casa por 2 a seguinte por 3 a seguinte por 4 e assim por diante.
            d1 = d1 + (11 - nCount) * digitoCPF;
            //para o segundo digito repita o procedimento incluindo o primeiro digito calculado no passo anterior.
            d2 = d2 + (12 - nCount) * digitoCPF;
        }

        //Primeiro resto da divisão por 11.
        resto = (d1 % 11);
        digito1 = (resto < 2 ? 0 : (11 - resto));
        d2 += 2 * digito1;

        //Segundo resto da divisão por 11.
        resto = (d2 % 11);
        digito2 = (resto < 2 ? 0 : (11 - resto));

        //Digito verificador do CPF que está sendo validado.
        String nDigVerific = ncpf.substring(ncpf.length() - 2, ncpf.length());
        //Concatenando o primeiro resto com o segundo.
        nDigResult = String.valueOf(digito1) + String.valueOf(digito2);
        //comparar o digito verificador do cpf com o primeiro resto + o segundo resto.
        return nDigVerific.equals(nDigResult);
    }
}
