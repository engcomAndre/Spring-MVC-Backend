package com.andre.cursomc.services;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.mockito.Mockito.ignoreStubs;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andre.cursomc.domain.PagamentoComBoleto;
import com.andre.cursomc.domain.Pedido;
import com.andre.cursomc.domain.enums.EstadoPagamento;
import com.andre.cursomc.repositories.PagamentoRepository;
import com.andre.cursomc.repositories.PedidoRepository;
import com.andre.cursomc.services.exception.ObjectNotFoundException;

@Service
public class PedidoService {
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	public Pedido find(Integer id) {
		Pedido obj = pedidoRepository.findOne(id);
		if(obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado ! Id : " + id
					+ ", Tipo : " + Pedido.class.getName()); 
		}
		return obj;
	}
	
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.getPagamento().setEstadoPagamento(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		
		if(obj.getPagamento() instanceof PagamentoComBoleto){
			PagamentoComBoleto pagto = (PagamentoComBoleto)obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto,obj.getInstante());			
		}
		obj = pedidoRepository.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		
		return obj;
	}
	
}





