package com.victornsto.desafiofadesp.util;

public class ValidacaoUtil {
    public static String validarCPF(String cpfCnpj) {
        String novoCpfCnpj = cpfCnpj.replace("-","").replace(".", "").replace("/","");
        return novoCpfCnpj.length() == 11 || novoCpfCnpj.length() == 14 ? novoCpfCnpj : null;
    }

    public static String validarCartao(String numCartao) {
        String novoNumCartao = numCartao.replace(" ","");
        return novoNumCartao.length() == 16 ? novoNumCartao : null;
    }
}

