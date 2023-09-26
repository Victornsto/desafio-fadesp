package com.victornsto.desafiofadesp.controller;

import com.victornsto.desafiofadesp.model.dto.PagamentoDto;
import com.victornsto.desafiofadesp.model.enums.StatusPagamento;
import com.victornsto.desafiofadesp.service.PagamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@Tag(name = "Pagamentos", description = "Endpoints voltados ao gerenciamento de pagamentos")
@RequestMapping("v1/desafio-fadesp/pagamentos")
public class PagamentoController {
    @Autowired
    PagamentoService pagamentoService;

    @PostMapping
    @Operation(summary = "Cadastra um novo pagamento com status pendente", description = "Cadastra um novo pagamento com status pendente", tags = "Pagamentos")
    @ResponseStatus(HttpStatus.CREATED)
    public void novoPagamento(@RequestBody @Valid PagamentoDto pagamentoDto) throws Exception {
         pagamentoService.novoPagamento(pagamentoDto);
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualiza o status do pagamento", description = "Atualiza o status do pagamento", tags = "Pagamentos")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity atualizaStatusPagamento(@PathVariable(value = "id") Long idPagamento, @RequestBody StatusPagamento status) {
        pagamentoService.atualizarStatusPagamento(idPagamento, status);
        return ResponseEntity.ok("Status atualizado com sucesso");
    }

    @GetMapping
    @Operation(summary = "Busca os pagamentos com filtro", description = "Busca os pagamentos com filtro", tags = "Pagamentos")
    @ResponseStatus(HttpStatus.OK)
    public List<PagamentoDto> listarPagamentos(@RequestParam(required = false) Integer codDebito, @RequestParam(required = false) String cpfCnpj, @RequestParam(required = false) StatusPagamento statusPagamento) {
        return pagamentoService.listarPagamentos(codDebito, cpfCnpj, statusPagamento);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Exclui um pagamento que esteja com status pendente", description = "Exclui um pagamento que esteja com status pendente", tags = "Pagamentos")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarPagamento(@PathVariable(value = "id") Long idPagamento) {
        pagamentoService.deletarPagamento(idPagamento);
    }

}
