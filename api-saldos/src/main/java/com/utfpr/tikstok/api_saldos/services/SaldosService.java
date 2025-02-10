package com.utfpr.tikstok.api_saldos.services;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.utfpr.tikstok.api_saldos.dtos.EstoqueDTO;
import com.utfpr.tikstok.api_saldos.models.Estoque;
import com.utfpr.tikstok.api_saldos.repository.EstoqueRepository;
import com.utfpr.tikstok.api_saldos.models.Saldos;
import com.utfpr.tikstok.api_saldos.models.SaldosKey;
import com.utfpr.tikstok.api_saldos.repository.ReprocessaSaldoRepository;
import com.utfpr.tikstok.api_saldos.repository.SaldoAnteriorRepository;
import com.utfpr.tikstok.api_saldos.repository.SaldosRepository;

@Service
public class SaldosService {
	private SaldosRepository saldosRepository;
	private SaldoAnteriorRepository saldoAnteriorRepository;
	private ReprocessaSaldoRepository reprocessaSaldoRepository;
	private EstoqueRepository estoqueRepository;
	
	public SaldosService(SaldosRepository repository) {
		this.saldosRepository = repository;
	}
	
	public Saldos lancarSaldo(EstoqueDTO registro) {
		double q_entradas;
		double v_entradas;
		double q_saidas;
		double v_saidas;
		double saldoAnterior;
		double saldoAtual;
		SaldosKey saldoId = new SaldosKey();
		saldoId.setIdProduto(registro.idProduto());
		saldoId.setData(this.dataSemHora(registro.dtMovimento()));
		
		Saldos saldo = saldosRepository.findById(saldoId).orElse(null);
		if (saldo != null) {
			saldoAnterior = saldo.getQ_anterior();
			q_entradas = saldo.getQ_entradas();
			v_entradas = saldo.getV_entradas();
			q_saidas = saldo.getQ_saidas();
			v_saidas = saldo.getV_saidas();
		} else {
			Saldos saldoBase = saldoAnteriorRepository.getSaldoAnterior(registro.idProduto(), this.dataSemHora(registro.dtMovimento()));
			if (saldoBase != null) {
				saldoAnterior = saldoBase.getQ_atual();
			} else {
				saldoAnterior = 0.00;
			}
			saldo = new Saldos();
			saldo.setSaldosid(saldoId);
			q_entradas = 0.00;
			v_entradas = 0.00;
			q_saidas = 0.00;
			v_saidas = 0.00;
		}

		if (registro.tipo() == "E") {
			q_entradas += registro.quantidade();
			v_entradas += (registro.valorUnitario() * registro.quantidade());
		} else {
			q_saidas += registro.quantidade();
			v_saidas += (registro.valorUnitario() * registro.quantidade());
		}
		
		saldoAtual = saldoAnterior + (q_entradas - q_saidas);
		saldo.setQ_anterior(saldoAnterior);
		saldo.setQ_entradas(q_entradas);
		saldo.setV_entradas(v_entradas);
		saldo.setQ_saidas(q_saidas);
		saldo.setV_saidas(v_saidas);
		saldo.setQ_atual(saldoAtual);
		
		Saldos saldoIncluido = this.saldosRepository.save(saldo); 
		
		this.reprocessaSaldo(registro);
		
		return saldoIncluido;
	}

	public Saldos getSaldoById(SaldosKey saldoId) {
		return saldosRepository.findById(saldoId).orElse(null);
	}

    public Saldos alterarSaldo(Long idProduto, Date data, EstoqueDTO estoqueDTO) {
		SaldosKey saldoId = new SaldosKey();
		saldoId.setIdProduto(idProduto);
		saldoId.setData(this.dataSemHora(data));

        Saldos saldo = saldosRepository.findById(saldoId).orElse(null);
        if(saldo != null){
        	Estoque estoque = estoqueRepository.findById(estoqueDTO.id()).orElse(null);
        	// Desfazendo...
    		double q_alterada;
    		double v_alterada;
    		double saldoAlterada;
    		q_alterada = estoque.getQuantidade();
    		v_alterada = (estoque.getValorUnitario() * q_alterada);
        	SaldosKey saldoIdAntes = new SaldosKey(estoque.getIdProduto(), this.dataSemHora(estoque.getDtMovimento()));
        	Saldos saldoAntes = saldosRepository.findById(saldoIdAntes).orElse(null);
        	if (estoque.getTipo() == "E") {
        		saldoAntes.setQ_entradas(saldoAntes.getQ_entradas() - q_alterada);
        		saldoAntes.setV_entradas(saldoAntes.getV_entradas() - v_alterada);
        	} else {
        		saldoAntes.setQ_saidas(saldoAntes.getQ_saidas() - q_alterada);
        		saldoAntes.setV_saidas(saldoAntes.getV_saidas() - v_alterada);
        	}
    		saldoAlterada = saldoAntes.getQ_anterior() + (saldoAntes.getQ_entradas() - saldoAntes.getQ_saidas());
    		saldoAntes.setQ_atual(saldoAlterada);
    		this.saldosRepository.save(saldoAntes);
    		// Fazendo de novo (alterando)
    		q_alterada = estoqueDTO.quantidade();
    		v_alterada = (estoqueDTO.valorUnitario() * q_alterada);
        	if (estoqueDTO.tipo() == "E") {
        		saldo.setQ_entradas(saldo.getQ_entradas() + q_alterada);
        		saldo.setV_entradas(saldo.getV_entradas() + v_alterada);
        	} else {
        		saldo.setQ_saidas(saldo.getQ_saidas() + q_alterada);
        		saldo.setV_saidas(saldo.getV_saidas() + v_alterada);
        	}
    		saldoAlterada = saldo.getQ_anterior() + (saldo.getQ_entradas() - saldo.getQ_saidas());
    		saldo.setQ_atual(saldoAlterada);
    		
    		Saldos saldoAlterado = this.saldosRepository.save(saldo);
    		
    		// Criando um novo EstoqueDTO com a data do lançamento antes da alteração
    		EstoqueDTO reprocesso = new EstoqueDTO(estoqueDTO.id(), estoque.getDtMovimento(), estoqueDTO.tipo(), estoqueDTO.idProduto(), estoqueDTO.quantidade(), estoqueDTO.valorUnitario());
    		
    		this.reprocessaSaldo(reprocesso);

    		return saldoAlterado;
        }

        return null;
    }

	private Date dataSemHora(Date dataComHora) {
        // Converter Date para LocalDateTime
        LocalDateTime localDateTime = dataComHora.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        // Zerar horas, minutos e segundos
        LocalDateTime dataZerada = localDateTime.toLocalDate().atStartOfDay();

        // Converter de volta para Date (se necessário)
        Date novaData = Date.from(dataZerada.atZone(ZoneId.systemDefault()).toInstant());
        
        return novaData;
	}
	
	private void reprocessaSaldo(EstoqueDTO registro) {
		List<Saldos> lista = reprocessaSaldoRepository.findAllBySaldosKey(registro.idProduto(), this.dataSemHora(registro.dtMovimento()));
		String tipo;
		double quantidade;
		tipo = registro.tipo();
		quantidade = registro.quantidade();
		if (lista != null) {
			for (Saldos regSaldo : lista) {
				if (tipo == "E") {
					regSaldo.setQ_anterior(regSaldo.getQ_anterior() + quantidade);
					regSaldo.setQ_atual(regSaldo.getQ_atual() + quantidade);
				} else {
					regSaldo.setQ_anterior(regSaldo.getQ_anterior() + quantidade);
					regSaldo.setQ_atual(regSaldo.getQ_atual() + quantidade);
				}
				this.saldosRepository.save(regSaldo);
			}
		}
	}
}
