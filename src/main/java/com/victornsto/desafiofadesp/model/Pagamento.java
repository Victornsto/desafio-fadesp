package com.victornsto.desafiofadesp.model;

import com.victornsto.desafiofadesp.model.enums.StatusPagamento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pagamento")
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "cod_debito")
    private Integer codDebito;
    @Column(name = "cpf_cnpj")
    private String cpfCnpj;
    @ManyToOne
    @JoinColumn(name = "id_metodo_pagamento", referencedColumnName = "id")
    private MetodoPagamento metodoPagamento;
    @Column(name = "num_cartao")
    private String numCartao;
    @Column(name = "valor_pagamento")
    private BigDecimal valorPagamento;
    @Column(name ="status_pagamento")
    private StatusPagamento status;
}
