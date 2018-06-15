package com.andre.cursomc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.andre.cursomc.domain.Categoria;
import com.andre.cursomc.domain.Cidade;
import com.andre.cursomc.domain.Cliente;
import com.andre.cursomc.domain.Endereco;
import com.andre.cursomc.domain.Estado;
import com.andre.cursomc.domain.Pagamento;
import com.andre.cursomc.domain.PagamentoComBoleto;
import com.andre.cursomc.domain.PagamentoComCartao;
import com.andre.cursomc.domain.Pedido;
import com.andre.cursomc.domain.Produto;
import com.andre.cursomc.domain.enums.EstadoPagamento;
import com.andre.cursomc.domain.enums.TipoCliente;
import com.andre.cursomc.repositories.CategoriaRepository;
import com.andre.cursomc.repositories.CidadeRepository;
import com.andre.cursomc.repositories.ClienteRepository;
import com.andre.cursomc.repositories.EnderecoRepository;
import com.andre.cursomc.repositories.EstadoRepository;
import com.andre.cursomc.repositories.PagamentoRepository;
import com.andre.cursomc.repositories.PedidoRepository;
import com.andre.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {
	@Autowired
	CategoriaRepository categoriaRepository;
	@Autowired
	ProdutoRepository produtoRepository;
	@Autowired
	CidadeRepository cidadeRepository;
	@Autowired
	EstadoRepository estadoRepository;
	@Autowired
	ClienteRepository clienteRepository;
	@Autowired
	EnderecoRepository enderecoRepository;
	@Autowired
	PedidoRepository pedidoRepository;
	@Autowired
	PagamentoRepository pagamentoRepository;
	

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null,"Computador",2000.00);
		Produto p2 = new Produto(null,"Impressora",800.00);
		Produto p3 = new Produto(null,"Mouse",80.00);
				
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p2.getCategorias().addAll(Arrays.asList(cat1));
		
		categoriaRepository.save(Arrays.asList(cat1,cat2));
		produtoRepository.save(Arrays.asList(p1,p2,p3));
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null,"Uberlandia",est1);
		Cidade c2 = new Cidade(null,"São Paulo",est2);
		Cidade c3 = new Cidade(null,"Campinas",est2);

		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2,c3));
		
		estadoRepository.save(Arrays.asList(est1,est2));
		cidadeRepository.save(Arrays.asList(c1,c2,c3));		
		
		Cliente cli1  = new Cliente(null,"Maria da Silva","maria@gmail.com","12345678911",TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("1234568900","142536789"));
		
		Endereco e1 =new Endereco(null, "Rua Flores...", "300", "Apto303", "Jardim", "61604555",cli1, c1);
		Endereco e2 =new Endereco(null, "Rua Aveida...", "31", "Sala 500", "Centro", "61600555",cli1, c2);		
		
		cli1.getEnderecos().addAll(Arrays.asList(e1,e2));
		
		clienteRepository.save(Arrays.asList(cli1));
		enderecoRepository.save(Arrays.asList(e1,e2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:30"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);


		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);		
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2,sdf.parse("20/10/2017 00:00"),null);
		ped2.setPagamento(pagto2);	
		
		cli1.getPedidos().addAll(Arrays.asList(ped1,ped2));	
		
		pedidoRepository.save(Arrays.asList(ped1,ped2));
		pagamentoRepository.save(Arrays.asList(pagto1,pagto2));
		
		
	}
}
