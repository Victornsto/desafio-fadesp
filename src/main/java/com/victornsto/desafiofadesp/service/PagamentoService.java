package com.victornsto.desafiofadesp.service;

import com.victornsto.desafiofadesp.exceptions.ResourceBadRequestException;
import com.victornsto.desafiofadesp.exceptions.ResourceNotFoundException;
import com.victornsto.desafiofadesp.model.MetodoPagamento;
import com.victornsto.desafiofadesp.model.Pagamento;
import com.victornsto.desafiofadesp.model.dto.PagamentoDto;
import com.victornsto.desafiofadesp.model.enums.StatusPagamento;
import com.victornsto.desafiofadesp.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class PagamentoService {
    @Autowired
    PagamentoRepository pagamentoRepository;
    @Autowired
    MetodoPagamentoService metodoPagamentoService;

    public void novoPagamento(PagamentoDto pagamentoDto) throws Exception {

        if (!pagamentoDto.validarCpfCnpf()) throw new ResourceBadRequestException("CPF ou CNPJ inválido");

        if (!pagamentoDto.validarCartao()) throw new ResourceBadRequestException("Cartão não é valido");

        MetodoPagamento metodoPagamento = metodoPagamentoService.buscarPorId(pagamentoDto.getIdMetodoPagamento());

        Pagamento pagamento = new Pagamento();

        pagamento.setCodDebito(pagamentoDto.getCodDebito());
        pagamento.setValorPagamento(pagamentoDto.getValorPagamento());
        pagamento.setStatus(StatusPagamento.PENDENTE);
        pagamento.setCpfCnpj(pagamentoDto.getCpfCnpj());
        pagamento.setNumCartao(pagamentoDto.getNumCartao());
        pagamento.setMetodoPagamento(metodoPagamento);

        pagamentoRepository.save(pagamento);
    }

    public void atualizarStatusPagamento(Long idPagamento, StatusPagamento statusPagamento) {

        Optional<Pagamento> pagamento = pagamentoRepository.findById(idPagamento);
        if (pagamento.isEmpty()) throw new ResourceNotFoundException("Pagamento não encontrado!");

        switch (pagamento.get().getStatus()) {
            case SUCESSO ->
                    throw new ResourceBadRequestException("Este pagamento não pode atualizar o status pois já foi efetuado com sucesso!");
            case PENDENTE -> {
                if (statusPagamento != StatusPagamento.PENDENTE) {
                    pagamento.get().setStatus(statusPagamento);
                    pagamentoRepository.save(pagamento.get());
                }
            }
            case FALHA -> {
                if (statusPagamento == StatusPagamento.PENDENTE)
                    pagamento.get().setStatus(statusPagamento);
            }
            default -> throw new ResourceBadRequestException("Opção não se aplica ao pagamento selecionado");

        }
    }
}
