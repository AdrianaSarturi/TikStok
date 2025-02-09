package com.utfpr.tikstok.api_saldos.services;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.utfpr.tikstok.api_estoques.dtos.EstoqueDTO;
import com.utfpr.tikstok.api_estoques.dtos.EstoqueUpdateDTO;
import com.utfpr.tikstok.api_estoques.models.Estoque;
import com.utfpr.tikstok.api_estoques.repository.EstoqueRepository;
import com.utfpr.tikstok.api_saldos.models.Saldos;
import com.utfpr.tikstok.api_saldos.models.SaldosKey;
import com.utfpr.tikstok.api_saldos.repository.SaldoAnteriorRepository;
import com.utfpr.tikstok.api_saldos.repository.SaldosRepository;

@Service
public class SaldosService {
	private SaldosRepository saldosRepository;
	private SaldoAnteriorRepository saldoAnteriorRepository;
	private EstoqueRepository estoqueRepository;
	
	public SaldosService(SaldosRepository repository) {
		this.saldosRepository = repository;
	}
	
	public Saldos lancarSaldo(EstoqueDTO registro) {
		Double q_entradas;
		Double v_entradas;
		Double q_saidas;
		Double v_saidas;
		Double saldoAnterior;
		Double saldoAtual;
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

		Saldos saldoSalvo = this.saldosRepository.save(saldo);
		
		return saldoSalvo;
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
        	Estoque estoque = estoqueRepository.findById(estoqueDTO.id());
        	
            estoque.setDtMovimento(estoqueUpdateDTO.dtMovimento() != null ? estoqueUpdateDTO.dtMovimento(): estoque.getDtMovimento());
            estoque.setQuantidade(estoqueUpdateDTO.quantidade() != null ? estoqueUpdateDTO.quantidade() : estoque.getQuantidade());
            estoque.setValorUnitario(estoqueUpdateDTO.valorUnitario() != null ? estoqueUpdateDTO.valorUnitario() : estoque.getValorUnitario());

            return estoqueRepository.save(estoque);
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
	
	private String reprocessaSaldo(EstoqueDTO registro) {
		minar
		return "Processado";
	}
}
