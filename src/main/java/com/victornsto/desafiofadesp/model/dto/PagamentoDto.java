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
    private int codDebito;
    private String cpfCnpj;
    private Long idMetodoPagamento;
    private String numCartao;
    private BigDecimal valorPagamento;
    private StatusPagamento status;

    public Pagamento convertToModel() {
        return convertToModel(new Pagamento());
    }

    public boolean validarCpfCnpf() {
        this.setCpfCnpj(this.getCpfCnpj().replace("-","").replace(".", "").replace("/",""));
        return this.cpfCnpj.length() == 11 || this.cpfCnpj.length() == 14;
    }

    public boolean validarCartao() {
        this.setNumCartao(this.getNumCartao().replace(" ",""));
        return this.numCartao.length() == 16;
    }

    public Pagamento convertToModel(Pagamento pagamento){
        pagamento.setId(this.getId());
        pagamento.setCodDebito(this.getCodDebito());
        pagamento.setCpfCnpj(this.getCpfCnpj());
       // pagamento.setm(this.getMetodoPagamento());
        pagamento.setNumCartao(this.getNumCartao());
        pagamento.setValorPagamento(this.getValorPagamento());
        pagamento.setStatus(this.getStatus());
        return pagamento;
    }

    public Pagamento convertToModel(MetodoPagamento metodoPagamento) {
        Pagamento pagamento = new Pagamento();
        pagamento.setMetodoPagamento(metodoPagamento);
        return pagamento;
    }

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
