package com.victornsto.desafiofadesp.service;

import com.victornsto.desafiofadesp.exceptions.ResourceNotFoundException;
import com.victornsto.desafiofadesp.model.MetodoPagamento;
import com.victornsto.desafiofadesp.repository.MetodoPagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MetodoPagamentoService {
    @Autowired
    MetodoPagamentoRepository metodoPagamentoRepository;

    public MetodoPagamento buscarPorId(Long idMetodoPagamento) throws Exception {
        Optional<MetodoPagamento> metodoPagamento = metodoPagamentoRepository.findById(idMetodoPagamento);
        if (!metodoPagamento.isPresent()) {
            throw new ResourceNotFoundException("Método de pagamento não encontrado!");
        }
        return metodoPagamento.get();
    }
}
