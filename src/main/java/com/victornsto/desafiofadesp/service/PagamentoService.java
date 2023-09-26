package com.victornsto.desafiofadesp.service;

import com.victornsto.desafiofadesp.exceptions.ResourceBadRequestException;
import com.victornsto.desafiofadesp.exceptions.ResourceNotFoundException;
import com.victornsto.desafiofadesp.model.MetodoPagamento;
import com.victornsto.desafiofadesp.model.Pagamento;
import com.victornsto.desafiofadesp.model.dto.PagamentoDto;
import com.victornsto.desafiofadesp.model.enums.StatusPagamento;
import com.victornsto.desafiofadesp.repository.PagamentoRepository;
import com.victornsto.desafiofadesp.util.ValidacaoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
public class PagamentoService {
    @Autowired
    PagamentoRepository pagamentoRepository;
    @Autowired
    MetodoPagamentoService metodoPagamentoService;

    public void novoPagamento(PagamentoDto pagamentoDto) throws Exception {

        pagamentoDto.setCpfCnpj(ValidacaoUtil.validarCPF(pagamentoDto.getCpfCnpj()));


        if (pagamentoDto.getCpfCnpj() == null) throw new ResourceBadRequestException("CPF ou CNPJ inválido");

        MetodoPagamento metodoPagamento = metodoPagamentoService.buscarPorId(pagamentoDto.getIdMetodoPagamento());

        if (metodoPagamento.getDescricao().equals("cartao_debito") || metodoPagamento.getDescricao().equals("cartao_credito")) {
            pagamentoDto.setNumCartao(ValidacaoUtil.validarCartao(pagamentoDto.getNumCartao()));
            if (pagamentoDto.getNumCartao() == null) throw new ResourceBadRequestException("Cartão não é valido");
        } else {
            if (!pagamentoDto.getNumCartao().isEmpty()) throw new ResourceBadRequestException("Número do cartão só é necessário se o método de pagamento for débito ou crédito");
        }

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
                } else {
                    throw new ResourceBadRequestException("Pagamento com status pendende");
                }
            }
            case FALHA -> {
                if (statusPagamento == StatusPagamento.PENDENTE) {
                    pagamento.get().setStatus(statusPagamento);
                    pagamentoRepository.save(pagamento.get());
                } else {
                    throw new ResourceBadRequestException("Pagamentos com processamento falho tem que entrar na fila de processamento como pendente!");
                }
            }
            default -> throw new ResourceBadRequestException("Opção não se aplica ao pagamento selecionado");

        }
    }

    public List<PagamentoDto> listarPagamentos(Integer codDebito, String cpfCnpj, StatusPagamento statusPagamento) {
        if (cpfCnpj != null  && ValidacaoUtil.validarCPF(cpfCnpj) == null) throw new ResourceBadRequestException("CPF ou CNPJ inválido!");
        PagamentoDto pagamentoDto = new PagamentoDto();
        return pagamentoDto.convertListToDto(pagamentoRepository.findPagamentoByCodDebitoOrCpfCnpjOrStatus(codDebito, cpfCnpj, statusPagamento));
    }

    public void deletarPagamento(Long idPagamento) {
        Optional<Pagamento> pagamento = pagamentoRepository.findById(idPagamento);
        if (!pagamento.isPresent()) throw new ResourceNotFoundException("Pagamento não encontrado");

        if (pagamento.get().getStatus() != StatusPagamento.PENDENTE) throw new ResourceBadRequestException("Pagamento só pode ser excluido se estiver com status pendente!");

        pagamentoRepository.delete(pagamento.get());
    }
}
