import java.io.*;

public class Computador {

	char 	ativo;
	String	codComp;
	String 	marca;
	String 	modelo;
	String  processador;
	int 	quantMemoria;
	int 	tamanhoTela;
	int 	quantEstoque;
	float 	preco;
	int 	quantTotalVendida;
	int 	quantUltimaVenda;
	String 	dtUltimaVenda;

	public long pesquisarComputador(String codCompPesq) {	
		// metodo para localizar um registro no arquivo em disco
		long posicaoCursorArquivo = 0;
		try { 
			RandomAccessFile arqComputador = new RandomAccessFile("COMPUTADOR.DAT", "rw");
			while (true) {
				posicaoCursorArquivo  = arqComputador.getFilePointer();	// posicao do inicio do registro no arquivo
				ativo			  	  = arqComputador.readChar();
				codComp  			  = arqComputador.readUTF();
				marca   			  = arqComputador.readUTF();
				modelo      		  = arqComputador.readUTF();
				processador 		  = arqComputador.readUTF();
				quantMemoria    	  = arqComputador.readInt();
				tamanhoTela      	  = arqComputador.readInt();
				quantEstoque     	  = arqComputador.readInt();
				preco 		     	  = arqComputador.readFloat();
				quantTotalVendida 	  = arqComputador.readInt();
				quantUltimaVenda 	  = arqComputador.readInt();
				dtUltimaVenda 	      = arqComputador.readUTF();


				if ( codCompPesq.equals(codComp) && ativo == 'S') {
					arqComputador.close();
					return posicaoCursorArquivo;
				}
			}
		}catch (EOFException e) {
			return -1; // registro nao foi encontrado
		}catch (IOException e) { 
			System.out.println("Erro na abertura do arquivo  -  programa sera finalizado");
			System.exit(0);
			return -1;
		}
	}

	public void salvarComputador() {	
		// metodo para incluir um novo registro no final do arquivo em disco
		try {
			RandomAccessFile arqComputador = new RandomAccessFile("COMPUTADOR.DAT", "rw");	
			arqComputador.seek(arqComputador.length());  // posiciona o ponteiro no final do arquivo (EOF)
			arqComputador.writeChar(ativo);
			arqComputador.writeUTF(codComp);
			arqComputador.writeUTF(marca);
			arqComputador.writeUTF(modelo);
			arqComputador.writeUTF(processador);
			arqComputador.writeInt(quantMemoria);
			arqComputador.writeInt(tamanhoTela);	
			arqComputador.writeInt(quantEstoque);	
			arqComputador.writeFloat(preco);	
			arqComputador.writeInt(quantTotalVendida);	
			arqComputador.writeInt(quantUltimaVenda);	
			arqComputador.writeUTF(dtUltimaVenda);	
			arqComputador.close();
		}catch (IOException e) { 
			System.out.println("Erro na abertura do arquivo  -  programa sera finalizado");
			System.exit(0);
		}
	}

	public void desativarComputador(long posicao)	{    
		// metodo para alterar o valor do campo ATIVO para N, tornando assim o registro excluido
		try {
			RandomAccessFile arqComputador = new RandomAccessFile("COMPUTADOR.DAT", "rw");			
			arqComputador.seek(posicao);
			arqComputador.writeChar('N');   // desativar o registro antigo
			arqComputador.close();
		}catch (IOException e) { 
			System.out.println("Erro na abertura do arquivo  -  programa sera finalizado");
			System.exit(0);
		}
	}

	// ***********************   INCLUSAO   *****************************
	public void incluir() {
		String 	codCompChave;
		char 	confirmacao;
		long 	posicaoRegistro;

		do {
			do {
				Main.leia.nextLine();
				System.out.println("\n ***************  INCLUSAO DE COMPUTADORES  ***************** ");
				System.out.print("Digite a código do computador (FIM para encerrar): ");
				codCompChave = Main.leia.nextLine();
				if (codCompChave.equalsIgnoreCase("FIM")) {
					break;
				}
				posicaoRegistro = pesquisarComputador(codCompChave);

				if (posicaoRegistro >= 0) {
					System.out.println("Computador já cadastrado, digite outro valor.\n");
				}
			}while (posicaoRegistro >= 0);

			if (codCompChave.equalsIgnoreCase("FIM")) {
				break;
			}

			ativo = 'S';
			codComp = codCompChave;
			quantMemoria = 0;
			tamanhoTela = 0;
			quantEstoque = 0;
			quantTotalVendida = 0;
			quantUltimaVenda = 0;
			dtUltimaVenda = "";
			do {
				System.out.println("Nome da marca........................: ");
				marca = Main.leia.nextLine();
				if(!consistirMarca(marca)) {
					System.out.println("Marca não encontrada, digite uma marca cadastrada no sistema!");
				}
			}while(! consistirMarca(marca));

			do {
				System.out.println("Digite o modelo do computador.......: ");
				modelo = Main.leia.nextLine();
				if (! validarModelo(modelo)) {
					System.out.println("Campo obrigatório, preencha o modelo do computador!");
				}
			}while(! validarModelo(modelo));

			do {
				System.out.println("Processador..................: ");
				processador = Main.leia.nextLine();
				if(! consistirProcessador(processador)) {
					System.out.println("Processador não encontrado, digite um processador cadastrado no sistema1");
				}
			}while(! consistirProcessador(processador));

			do {
				System.out.println("Quantidade de memoria RAM em GB...................: ");
				quantMemoria = Main.leia.nextInt();
				if (! validarQuantMemoria(quantMemoria)) {
					System.out.println("Quantidade inválida, digite um valor entre 1 e 32 GB");
				}
			}while(! validarQuantMemoria(quantMemoria));

			do {
				System.out.println("Tamanho em polegadas da tela...................: ");
				tamanhoTela = Main.leia.nextInt();
				if (! consistirTamanhoTela(tamanhoTela)) {
					System.out.println("Tamanho de tela não encontrado, digite um valor válido!");
				}
			}while(! consistirTamanhoTela(tamanhoTela));

			do {
				System.out.print("Quantidade no estoque...................: ");
				quantEstoque = Main.leia.nextInt();
				if (! validarQuantEstoque(quantEstoque)) {
					System.out.println("Quantidade invalida, digite um valor acima de 0!");
				}
			}while(! validarQuantEstoque(quantEstoque));

			do {
				System.out.print("Preco de venda...................: ");
				preco = Main.leia.nextFloat();
				if (!  validarPreco(preco)) {
					System.out.println("Preco invalido, digite um valor >= R$ 1.000,00 ou <= R$ 20.000,00");
				}
			}while(!  validarPreco(preco));


			do {
				System.out.print("\nConfirma a gravação dos dados (S/N) ? ");
				confirmacao = Main.leia.next().charAt(0);
				if (confirmacao == 'S') {
					salvarComputador();
				}
			}while (confirmacao != 'S' && confirmacao != 'N');

		}while ( ! codCompChave.equalsIgnoreCase("FIM"));	    
	}


	//************************  ALTERACAO  *****************************

	public void alterar() {
		String 	codCompChave;
		char 	confirmacao;
		long 	posicaoRegistro = 0;
		byte 	opcao;

		do {
			do {
				Main.leia.nextLine();
				System.out.println("\n ***************  ALTERAÇÃO DE COMPUTADORES  ***************** ");
				System.out.print("Digite o Codigo do Computador que deseja alterar( FIM para encerrar ): ");
				codCompChave = Main.leia.nextLine();
				if (codCompChave.equalsIgnoreCase("FIM")) {
					break;
				}

				posicaoRegistro = pesquisarComputador(codCompChave);
				if (posicaoRegistro == -1) {
					System.out.println("Computador não cadastrado no arquivo, digite outro valor\n");
				}
			}while (posicaoRegistro == -1);

			if (codCompChave.equalsIgnoreCase("FIM")) {
				break;
			}

			ativo = 'S';

			do {
				System.out.println("[ 1 ] Marca............................: " + marca);
				System.out.println("[ 2 ] Modelo...........................: " + modelo);
				System.out.println("[ 3 ] Processador......................: " + processador);
				System.out.println("[ 4 ] Quantidade de Memória............: " + quantMemoria);
				System.out.println("[ 5 ] Tamanho da Tela..................: " + tamanhoTela);
				System.out.println("[ 6 ] Quantidade em Estoque............: " + quantEstoque);
				System.out.println("[ 7 ] Preço............................: " + preco);
				System.out.println("[ 8 ] Quantidade da Ultima Venda.......: " + quantUltimaVenda);
				System.out.println("[ 9 ] Data da Ultima Venda.............: " + dtUltimaVenda);

				do{
					System.out.println("Digite o numero do campo que deseja alterar (0 para finalizar as alterações): ");
					opcao = Main.leia.nextByte();
				}while (opcao < 0 || opcao > 9);

				switch (opcao) {
				case 1:
					do {
						Main.leia.nextLine();
						System.out.println("Nome da marca........................: ");
						marca = Main.leia.nextLine();
						if(!consistirMarca(marca)) {
							System.out.println("Marca não encontrada, digite uma marca cadastrada no sistema!");
						}
					}while(!consistirMarca(marca));
					break;
				case 2: 
					do {
						Main.leia.nextLine();
						System.out.println("Digite o modelo do computador.......: ");
						modelo = Main.leia.nextLine();
						validarModelo(modelo);
						if (! validarModelo(modelo)) {
							System.out.print("Campo obrigatório, preencha o modelo do computador!");
						}
					}while(! validarModelo(modelo));
					break;
				case 3:
					do {
						System.out.println("Processador..................: ");
						processador = Main.leia.nextLine();
						if(! consistirProcessador(processador)) {
							System.out.println("Processador não encontrado, digite um processador cadastrado no sistema!");
						}
					}while(! consistirProcessador(processador));
					break;
				case 4: 
					do {
						System.out.println("Quantidade de memoria RAM em GB...................: ");
						quantMemoria = Main.leia.nextInt();
						if (! validarQuantMemoria(quantMemoria)) {
							System.out.println("Quantidade inválida, digite um valor entre 1 e 32 GB");
						}
					}while(! validarQuantMemoria(quantMemoria));
					break;
				case 5:
					do {
						System.out.println("Tamanho em polegadas da tela...................: ");
						tamanhoTela = Main.leia.nextInt();
						if (! consistirTamanhoTela(tamanhoTela)) {
							System.out.println("Tamanho de tela não encontrado, digite um valor válido!");
						}
					}while(! consistirTamanhoTela(tamanhoTela));
					break;
				case 6:
					do {
						System.out.print("Quantidade no estoque...................: ");
						quantEstoque = Main.leia.nextInt();
						if (! validarQuantEstoque(quantEstoque)) {
							System.out.println("Quantidade invalida, digite um valor acima de 0!");
						}
					}while(! validarQuantEstoque(quantEstoque));
					break;
				case 7:
					do {
						System.out.print("Preco de venda...................: ");
						preco = Main.leia.nextFloat();
						if (!  validarPreco(preco)) {
							System.out.println("Preco invalido, digite um valor >= R$ 1.000,00 ou <= R$ 20.000,00");
						}
					}while(!  validarPreco(preco));

					break;
				case 8:
					do {
						System.out.println  ("Digite a QUANTIDADE DA ULTIMA VENDA............:");
						quantUltimaVenda = Main.leia.nextInt();
					} while (! validarQuantUltimaVenda());

					quantTotalVendida += quantUltimaVenda;
					quantEstoque -= quantUltimaVenda;

					do {
						System.out.println  ("Digite a DATA DA ULTIMA VENDA............:");
						dtUltimaVenda = Main.leia.next();
						System.out.print(dataEhValida(dtUltimaVenda));
					} while (! dataEhValida(dtUltimaVenda));

					break;
				}

				System.out.println();

				System.out.println(Cabecalho());;
				imprimirComputador();
				System.out.println();

			}while (opcao != 0);  		

			do {
				System.out.print("\nConfirma a alteracao dos dados (S/N) ? ");
				confirmacao = Main.leia.next().charAt(0);
				if (confirmacao == 'S') {
					desativarComputador(posicaoRegistro);
					salvarComputador();
					System.out.println("Dados gravados com sucesso !\n");
				}
			}while (confirmacao != 'S' && confirmacao != 'N');

		}while ( ! codComp.equalsIgnoreCase("FIM"));
	}



	// ************************ EXCLUSAO *****************************
	public void excluir() {
		String 	codCompChave;
		char 	confirmacao;
		long 	posicaoRegistro = 0;

		do {
			do {
				Main.leia.nextLine();
				System.out.println(" ***************  EXCLUSAO DE COMPUTADORES  ***************** ");
				System.out.print("Digite o código do computador que deseja excluir ( FIM para encerrar ): ");
				codCompChave = Main.leia.nextLine();
				if (codCompChave.equalsIgnoreCase("FIM")) {
					break;
				}

				posicaoRegistro = pesquisarComputador(codCompChave);
				if (posicaoRegistro == -1) {
					System.out.println("Código do computador nao cadastrado no arquivo, digite outro valor\n");
				}
			} while (posicaoRegistro == -1);

			if (codCompChave.equalsIgnoreCase("FIM")) {
				System.out.println("\n ************  PROGRAMA ENCERRADO  ************** \n");
				break;
			}

			System.out.println("Ativo........................: " + ativo);
			System.out.println("Código do computador.........: " + codComp);
			System.out.println("Marca do computador..........: " + marca);
			System.out.println("Modelo do computador.........: " + modelo);
			System.out.println("Processador do computador....: " + processador);
			System.out.println("Quantidade de memória........: " + quantMemoria);
			System.out.println("Tamanho da tela..............: " + tamanhoTela);
			System.out.println("Quantidade no estoque........: " + quantEstoque);
			System.out.println("Preço do computador..........: " + preco);
			System.out.println("Quant total vendida..........: " + quantTotalVendida);
			System.out.println("Quant última venda...........: " + quantUltimaVenda);
			System.out.println("Data última venda............: " + dtUltimaVenda);
			System.out.println();

			do {
				System.out.print("\nConfirma a exclusao deste computador (S/N) ? ");
				confirmacao = Main.leia.next().charAt(0);
				if (confirmacao == 'S') {
					desativarComputador(posicaoRegistro);
				}
			} while (confirmacao != 'S' && confirmacao != 'N');

		} while (!codComp.equalsIgnoreCase("FIM"));
	}

	// ************************ CONSULTA *****************************
	public void consultar() {
		RandomAccessFile arqComputador;
		byte 	opcao;
		String	codCompChave;
		float	precoMax;
		float 	precoMin;
		long 	posicaoRegistro;
		String 	anoUltimaVenda;
		float 	totalUnidadeVendas = 0;
		float 	totalFaturado = 0;

		do {
			do {
				System.out.println(" ***************  CONSULTA DE COMPUTADORES  ***************** ");
				System.out.println(" [1] LISTAR TODOS OS COMPUTADORES ");
				System.out.println(" [2] LISTAR APENAS UM COMPUTADOR ");
				System.out.println(" [3] LISTAR COMPUTADORES VENDIDOS ");
				System.out.println(" [4] LISTAR COMPUTADORES CUJA ÚLTIMA VENDA OCORREU EM DETERMINADO ANO");
				System.out.println(" [5] LISTAR COMPUTADORES POR FAIXA DE PREÇO");
				System.out.println(" [6] LISTAR COMPUTADORES CUJO PREÇO DO ESTOQUE ULTRAPASSE R$50.000,00");
				System.out.println(" [0] SAIR");
				System.out.print("\nDigite a opcao desejada: ");
				opcao = Main.leia.nextByte();
				if (opcao < 0 || opcao > 6) {
					System.out.println("Opção inválida, digite novamente.\n");
				}
			} while (opcao < 0 || opcao > 6);

			System.out.print("\n");

			switch (opcao) {
			case 0:
				System.out.println("\n ************  PROGRAMA ENCERRADO  ************** \n");
				break;

			case 1: // listar todos os computadores

				try {
					arqComputador = new RandomAccessFile("./COMPUTADOR.DAT", "r");

					String cabecalho = Cabecalho();

					String titulo = "************* LISTAGEM DE TODOS OS COMPUTADORES ***************";

					System.out.println(titulo+"\n");

					System.out.println(cabecalho);

					while (true) {
						arqComputador.getFilePointer();
						ativo = 				arqComputador.readChar();
						codComp =				arqComputador.readUTF();
						marca = 				arqComputador.readUTF();
						modelo = 				arqComputador.readUTF();
						processador =	 		arqComputador.readUTF();
						quantMemoria = 			arqComputador.readInt();
						tamanhoTela = 			arqComputador.readInt();
						quantEstoque = 			arqComputador.readInt();
						preco = 				arqComputador.readFloat();
						quantTotalVendida = 	arqComputador.readInt();
						quantUltimaVenda = 		arqComputador.readInt();
						dtUltimaVenda = 		arqComputador.readUTF();

						if (ativo == 'S') {
							imprimirComputador();
							totalUnidadeVendas += quantTotalVendida;
							totalFaturado += (preco * quantTotalVendida);
						}

					}
					//			arqComputador.close(); 
				} catch (EOFException e) {
					if(totalUnidadeVendas > 0) {
						imprimirTotais(totalUnidadeVendas,totalFaturado);
					} else {
						System.out.println("SEM REGISTROS.");
					}
					System.out.println("\n FIM DE RELATORIO - ENTER para continuar...\n");
					Main.leia.nextLine();
					codCompChave = Main.leia.nextLine();
				} catch (IOException e) {
					System.out.println("Erro na abertura do arquivo - programa sera finalizado");
					System.exit(0);
				}
				break;

			case 2: // listar apenas um computador desejado
				Main.leia.nextLine(); // limpa buffer de memoria
				System.out.print("Digite o código do computador: ");
				codCompChave = Main.leia.nextLine();

				posicaoRegistro = pesquisarComputador(codCompChave);
				if (posicaoRegistro == -1) {
					System.out.println("Código do computador não cadastrada no arquivo \n");
				} else {
					System.out.println(Cabecalho());
					imprimirComputador();
					System.out.println("\n FIM DE RELATORIO - ENTER para continuar...\n");
					Main.leia.nextLine();
				}

				break;

			case 3: // listar somente computadores já vendidos
				try {
					arqComputador = new RandomAccessFile("COMPUTADOR.DAT", "rw");
					String titulo = "************* LISTAGEM DE TODOS OS COMPUTADORES ***************";

					System.out.println(titulo+"\n");
					System.out.println(Cabecalho());
					while (true) {
						ativo = 				arqComputador.readChar();
						codComp = 				arqComputador.readUTF();
						marca = 				arqComputador.readUTF();
						modelo = 				arqComputador.readUTF();
						processador = 			arqComputador.readUTF();
						quantMemoria = 			arqComputador.readInt();
						tamanhoTela = 			arqComputador.readInt();
						quantEstoque = 			arqComputador.readInt();
						preco = 				arqComputador.readFloat();
						quantTotalVendida = 	arqComputador.readInt();
						quantUltimaVenda = 		arqComputador.readInt();
						dtUltimaVenda = 		arqComputador.readUTF();

						if (ativo == 'S' && quantTotalVendida > 0) {
							imprimirComputador();
							totalUnidadeVendas += quantTotalVendida;
							totalFaturado += (preco * quantTotalVendida);
						}
					}
					// arqComputador.close();
				} catch (EOFException e) {
					if(totalUnidadeVendas > 0) {
						imprimirTotais(totalUnidadeVendas,totalFaturado);
					} else {
						System.out.println("SEM REGISTROS.");
					}
					System.out.println("\n FIM DE RELATORIO - ENTER para continuar...\n");
					Main.leia.nextLine();
					codCompChave = Main.leia.nextLine();
				} catch (IOException e) {
					System.out.println("Erro na abertura do arquivo - programa sera finalizado");
					System.exit(0);
				}
				break;

			case 4: // listar computadores cuja última venda ocorreu em determinado ano (solicitar
				// ano)
				System.out.println("Digite o ano da última venda:  ");
				anoUltimaVenda = Main.leia.next();

				try {
					arqComputador = new RandomAccessFile("COMPUTADOR.DAT", "rw");
					String titulo = "************* LISTAGEM DE TODOS OS COMPUTADORES ***************";

					System.out.println(titulo+"\n");
					System.out.println(Cabecalho());
					while (true) {
						ativo = 				arqComputador.readChar();
						codComp = 				arqComputador.readUTF();
						marca = 				arqComputador.readUTF();
						modelo = 				arqComputador.readUTF();
						processador = 			arqComputador.readUTF();
						quantMemoria = 			arqComputador.readInt();
						tamanhoTela = 			arqComputador.readInt();
						quantEstoque = 			arqComputador.readInt();
						preco = 				arqComputador.readFloat();
						quantTotalVendida = 	arqComputador.readInt();
						quantUltimaVenda = 		arqComputador.readInt();
						dtUltimaVenda = 		arqComputador.readUTF();

						if(!dtUltimaVenda.isEmpty()) {
							String ano = dtUltimaVenda.substring(6);
							if (ativo == 'S' && ano.equals(anoUltimaVenda)) {
								imprimirComputador();
								totalUnidadeVendas += quantTotalVendida;
								totalFaturado += (preco * quantTotalVendida);
							}
						}
					}
					// arqComputador.close();
				} catch (EOFException e) {
					if(totalUnidadeVendas > 0) {
						imprimirTotais(totalUnidadeVendas,totalFaturado);
					} else {
						System.out.println("SEM REGISTROS.");
					}
					System.out.println("\n FIM DE RELATORIO - ENTER para continuar...\n");
					Main.leia.nextLine();
					codCompChave = Main.leia.nextLine();
				} catch (IOException e) {
					System.out.println("Erro na abertura do arquivo - programa sera finalizado");
					System.exit(0);
				}
				break;

			case 5: // listar computadores por faixa de preco
				System.out.println("Digite o preço máximo: ");
				precoMax = Main.leia.nextFloat();
				System.out.println("Digite o preço mínimo: ");
				precoMin = Main.leia.nextFloat();

				try {
					arqComputador = new RandomAccessFile("COMPUTADOR.DAT", "rw");
					String titulo = "************* LISTAGEM DE TODOS OS COMPUTADORES ***************";

					System.out.println(titulo+"\n");
					System.out.println(Cabecalho());
					while (true) {
						ativo = 				arqComputador.readChar();
						codComp = 				arqComputador.readUTF();
						marca = 				arqComputador.readUTF();
						modelo = 				arqComputador.readUTF();
						processador = 			arqComputador.readUTF();
						quantMemoria = 			arqComputador.readInt();
						tamanhoTela = 			arqComputador.readInt();
						quantEstoque = 			arqComputador.readInt();
						preco = 				arqComputador.readFloat();
						quantTotalVendida = 	arqComputador.readInt();
						quantUltimaVenda = 		arqComputador.readInt();
						dtUltimaVenda = 		arqComputador.readUTF();

						if (ativo == 'S' && preco >= precoMin && preco <= precoMax) {
							imprimirComputador();
							totalUnidadeVendas += quantTotalVendida;
							totalFaturado += (preco * quantTotalVendida);
						}
					}
					// arqComputador.close();
				} catch (EOFException e) {
					if(totalUnidadeVendas > 0) {
						imprimirTotais(totalUnidadeVendas,totalFaturado);
					} else {
						System.out.println("SEM REGISTROS.");
					}
					System.out.println("\n FIM DE RELATORIO - ENTER para continuar...\n");
					Main.leia.nextLine();
					codCompChave = Main.leia.nextLine();
				} catch (IOException e) {
					System.out.println("Erro na abertura do arquivo - programa sera finalizado");
					System.exit(0);
				}
				break;

			case 6: // listar computadores cujo preco do estoque ultrapasse o valor de
				// R$50.000,00(estoque multiplicado pelo preco)
				try {
					arqComputador = new RandomAccessFile("COMPUTADOR.DAT", "rw");
					String titulo = "************* LISTAGEM DE TODOS OS COMPUTADORES ***************";

					System.out.println(titulo+"\n");
					System.out.println(Cabecalho());
					while (true) {
						ativo = 				arqComputador.readChar();
						codComp = 				arqComputador.readUTF();
						marca = 				arqComputador.readUTF();
						modelo = 				arqComputador.readUTF();
						processador = 			arqComputador.readUTF();
						quantMemoria = 			arqComputador.readInt();
						tamanhoTela = 			arqComputador.readInt();
						quantEstoque = 			arqComputador.readInt();
						preco = 				arqComputador.readFloat();
						quantTotalVendida = 	arqComputador.readInt();
						quantUltimaVenda = 		arqComputador.readInt();
						dtUltimaVenda = 		arqComputador.readUTF();

						if (ativo == 'S' && (quantEstoque * preco) > 50000) {
							imprimirComputador();
							totalUnidadeVendas += quantTotalVendida;
							totalFaturado += (preco * quantTotalVendida);
						}
					}
					// arqComputador.close();
				} catch (EOFException e) {
					if(totalUnidadeVendas > 0) {
						imprimirTotais(totalUnidadeVendas,totalFaturado);
					} else {
						System.out.println("SEM REGISTROS.");
					}
					System.out.println("\n FIM DE RELATORIO - ENTER para continuar...\n");
					Main.leia.nextLine();
					codCompChave = Main.leia.nextLine();
				} catch (IOException e) {
					System.out.println("Erro na abertura do arquivo - programa sera finalizado");
					System.exit(0);
				}
				break;

			}

		} while (opcao != 0);
	}

	private void imprimirTotais(float totalUnidadeVendas, float totalFaturado) {
		System.out.println(formatarString("TOTAIS", 132) + formatarString(String.valueOf(totalUnidadeVendas), 80) + formatarString(String.valueOf(totalFaturado), 13));
	}

	public String Cabecalho() {
		String espacos = "  ";
		StringBuilder sb = new StringBuilder();
		sb.append(
				formatarString("ATIVO", 6)).append(espacos).append(
						formatarString("COD COMP", 11)).append(espacos).append(
								formatarString("MARCA", 20)).append(espacos).append(
										formatarString("MODELO", 20)).append(espacos).append(
												formatarString("PROCESSADOR", 20)).append(espacos).append(
														formatarString("QUANT MEMÓRIA", 13)).append(espacos).append(
																formatarString("TAMANHO TELA", 13)).append(espacos).append(
																		formatarString("QUANT ESTOQUE", 13)).append(espacos).append(
																				formatarString("PRECO", 15)).append(espacos).append(
																						formatarString("QUANTI TOTAL VENDA", 20)).append(espacos).append(
																								formatarString("QUANT ÚLTIMA VENDA", 20)).append(espacos).append(
																										formatarString("DATA ÚLTIMA VENDA", 17)).append(espacos).append(
																												formatarString("VLR VEND", 15));

		return sb.toString();	
	}


	public void imprimirComputador() {
		System.out.println(
				formatarString(Character.toString(ativo), 6) + "  " + 
						formatarString(codComp, 11) + "  " + 
						formatarString(marca, 20) + "  " + 
						formatarString(modelo, 20) + "  " + 
						formatarString(processador, 20) + "  " + 
						formatarString(String.valueOf(quantMemoria), 13) + "  " + 
						formatarString(String.valueOf(tamanhoTela), 13) + "  " + 
						formatarString(String.valueOf(quantEstoque), 13) + "  " + 
						formatarString(String.valueOf(preco), 15) + "  " + 
						formatarString(String.valueOf(quantTotalVendida), 20) + "  " + 
						formatarString(String.valueOf(quantUltimaVenda), 20) + "  " + 
						formatarString(dtUltimaVenda, 17) + "  " + 
						formatarString(String.valueOf(preco * quantTotalVendida), 15));
	}

	public static String formatarString (String texto, int tamanho) {	
		// retorna uma string com o numero de caracteres passado como parametro em TAMANHO
		if (texto.length() > tamanho) {
			texto = texto.substring(0,tamanho);
		}else{
			while (texto.length() < tamanho) {
				texto = texto + " ";
			}
		}
		return texto;
	}

	public boolean consistirMarca (String marca) {  //Metodo para fazer a consistencia da marca
		for (byte x = 0; x <= 6; x++) {
			if (marca.equalsIgnoreCase(Main.vetMarcas[x])) {
				return true;
			}
		}
		return false;
	}

	public boolean consistirProcessador (String processador) { //Metodo para fazer a consistencia do processador
		for (byte x = 0; x <= 6; x++) {
			if (processador.equalsIgnoreCase(Main.vetProcessadores[x])) {
				return true;
			}
		}
		return false;
	}

	public boolean consistirTamanhoTela (int tamanhoTela) {  //Metodo para fazer a consistencia do tam da tela
		for (byte x = 0; x <= 5; x++) {			
			if (tamanhoTela == Main.vetTamanhoTelas[x]) {
				return true;
			}
		}
		return false;
	}

	public boolean validarModelo (String modelo) {
		if (modelo.equals("")) {
			return false;
		}
		return true;
	}

	public boolean validarQuantMemoria (int quantMemoria) {
		if (quantMemoria < 1 || quantMemoria > 32) {
			return false;
		}
		return true;
	}

	public boolean validarQuantEstoque (int quantEstoque) {
		if (quantEstoque < 0) {
			return false;
		}
		return true;
	}

	public boolean validarPreco (float preco) {
		if(preco < 1000 || preco > 20000) {
			return false;
		}
		return true;
	}

	public boolean validarQuantUltimaVenda () {

		if (quantUltimaVenda > quantEstoque || quantUltimaVenda <= 0) {
			System.out.println("Quantidade invalida, digite um valor menor ou igual a quantidade em estoque!");
			return false;
		}

		return true;
	}


	public boolean dataEhValida (String data) {
		int dia, mes, ano;

		if (data.length() != 10) {
			System.out.println("Data invalida, digite 10 caracteres");
			return false;
		}
		if (data.charAt(2) != '/' || data.charAt(5) != '/') {
			System.out.println("Data invalida, digite / nas posicoes 3 e 6");
			return false;
		}
		try {
			dia = Integer.parseInt( data.substring(0,2) );
			mes = Integer.parseInt( data.substring(3,5) );
			ano = Integer.parseInt( data.substring(6) );
		}catch(NumberFormatException e) {
			System.out.println("Data invalida, digite numeros para dia, mes e ano");
			return false;
		}
		if (ano < 1 || ano > 2025) {
			System.out.println("Data invalida, digite ano até 2025");
			return false;
		}
		if (mes < 1 || mes > 12) {
			System.out.println("Data invalida, digite mes entre 1 e 12");
			return false;
		}
		if (dia < 1 || dia > 31) {
			System.out.println("Data invalida, digite dia entre 1 e 31");
			return false;
		}
		if ( (mes == 4 || mes == 6 || mes == 9 || mes == 11) && dia > 30) {
			System.out.println("Data invalida, mas meses 4, 6, 9 ou 11, digite dia entre 1 e 30");
			return false;
		}
		if (mes == 2) {
			if (ano % 4 == 0 && ano % 100 != 0 || ano % 400 == 0) { // ano bissexto
				if (dia > 29) {
					System.out.println("Data invalida, para fevereiro bissexto, digite dia ate 29");
					return false;
				}
			} else {
				if (dia > 28) {
					System.out.println("Data invalida, para fevereiro NAO bissexto, digite dia ate 28");
					return false;
				}
			}
		}

		return true;
	}

}
