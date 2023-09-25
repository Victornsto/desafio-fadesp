package com.victornsto.desafiofadesp.util;

import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;

public class ValidacaoUtil {
    public static boolean validarCPF(String cpf) {
        try {
            new CPFValidator().assertValid(cpf);
            return true;
        } catch (InvalidStateException e) {
            return false;
        }
    }

    public static boolean validarCNPJ(String cnpj) {
        try {
            new CNPJValidator().assertValid(cnpj);
            return true;
        } catch (InvalidStateException e) {
            return false;
        }
    }
}

