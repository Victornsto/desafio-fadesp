package com.victornsto.desafiofadesp.controller;

import com.victornsto.desafiofadesp.model.Pagamento;
import com.victornsto.desafiofadesp.model.dto.PagamentoDto;
import com.victornsto.desafiofadesp.model.enums.StatusPagamento;
import com.victornsto.desafiofadesp.service.PagamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/desafio-fadesp/pagamentos")
public class PagamentoController {
    @Autowired
    PagamentoService pagamentoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void novoPagamento(@RequestBody @Valid PagamentoDto pagamentoDto) throws Exception {
         pagamentoService.novoPagamento(pagamentoDto);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity atualizaStatusPagamento(@PathVariable(value = "id") Long idPagamento, @RequestBody PagamentoDto pagamentoDto) {
        pagamentoService.atualizarStatusPagamento(idPagamento, pagamentoDto.getStatus());
        return ResponseEntity.ok("Status atualizado com sucesso");
    }

}
