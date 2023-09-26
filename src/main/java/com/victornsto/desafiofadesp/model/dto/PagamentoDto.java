package com.victornsto.desafiofadesp.model.dto;

import com.victornsto.desafiofadesp.model.MetodoPagamento;
import com.victornsto.desafiofadesp.model.Pagamento;
import com.victornsto.desafiofadesp.model.enums.StatusPagamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagamentoDto implements Serializable {
    private Long id;
    private Integer codDebito;
    private String cpfCnpj;
    private Long idMetodoPagamento;
    private String numCartao;
    private BigDecimal valorPagamento;
    private StatusPagamento status;

    public List<PagamentoDto> convertListToDto(List<Pagamento> pagamentos) {
        List<PagamentoDto> pagamentoDTOs = new ArrayList<>();

        for (Pagamento pagamento : pagamentos) {
            PagamentoDto pagamentoDTO = new PagamentoDto();
            pagamentoDTO.setId(pagamento.getId());
            pagamentoDTO.setCodDebito(pagamento.getCodDebito());
            pagamentoDTO.setCpfCnpj(pagamento.getCpfCnpj());
            pagamentoDTO.setIdMetodoPagamento(pagamento.getMetodoPagamento().getId());
            pagamentoDTO.setNumCartao(pagamento.getNumCartao());
            pagamentoDTO.setValorPagamento(pagamento.getValorPagamento());
            pagamentoDTO.setStatus(pagamento.getStatus());

            pagamentoDTOs.add(pagamentoDTO);
        }

        return pagamentoDTOs;
    }
}
