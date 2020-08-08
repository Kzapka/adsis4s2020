package aula20200807.funcionários;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class AppFuncionário {
	
	public static void main(String[] args) throws Exception {
		Connection conexão = null;
		try {
			
			conexão = abrirConexão();
			criarTabelaDeFuncionarios(conexão);
			inserirFuncionarios(conexão);
			alterarNome(conexão, 1l, "teste");

			long quantidadeDeFuncionariosAntesDeDeletar = contarFuncionarios(conexão);

			System.out.println("Antes: " + quantidadeDeFuncionariosAntesDeDeletar);

			listarFuncionarios(conexão);

			deletarFuncionarios(conexão);
			long quantidadeDeFuncionariosDepoisDeDeletar = contarFuncionarios(conexão);
			System.out.println("Após deletar: \n" + quantidadeDeFuncionariosDepoisDeDeletar);

			alterarPrimeiro(conexão,"Teclado");
			listarOs30Primeiros(conexão);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conexão.close();
		}
		System.out.println("/nConectado");
	}

	private static void listarFuncionarios(Connection conexão) throws Exception {
		Statement recuperarFuncionarios = null;
		try {
			recuperarFuncionarios = conexão.createStatement();
			ResultSet resultado = recuperarFuncionarios.executeQuery("select id, nome from FUNCIONARIO");
			System.out.println("Funcionários: ");
			while (resultado.next()) {
				System.out.println("- " + resultado.getString("nome") + ", id=" + resultado.getLong("id"));
			}
			System.out.println("Finalizado\n");
		} finally {
			recuperarFuncionarios.close();
		}
	}

	private static void listarOs30Primeiros(Connection conexão) throws Exception {
		Statement mostrar30Funcionarios = null;

		try {
			mostrar30Funcionarios = conexão.createStatement();
			ResultSet resultado = mostrar30Funcionarios.executeQuery("select id, nome FROM FUNCIONARIO limit 0,30");
			System.out.println("LISTANDO OS 30 FUNCIONÁRIOS\n");

			while (resultado.next()) {
				System.out.println("- " + resultado.getString("nome") + ", id=" + resultado.getLong("id"));
			}
			System.out.println("Listado\n");
		} finally {
			mostrar30Funcionarios.close();
		}

	}

	private static long contarFuncionarios(Connection conexão) throws Exception {
		long quantidadeDeFuncionarios = 0;
		Statement contarFuncionarios = null;
		try {
			contarFuncionarios = conexão.createStatement();
			ResultSet resultado = contarFuncionarios.executeQuery("select count(*) as quantidade from FUNCIONARIO");
			if (resultado.next()) {
				quantidadeDeFuncionarios = resultado.getLong("quantidade");
			}
		} finally {
			contarFuncionarios.close();
		}
		return quantidadeDeFuncionarios;
	}

	private static void alterarNome(Connection conexão, Long id, String novoNome) throws Exception {
		PreparedStatement alterarFuncionario = null;
		try {
			alterarFuncionario = conexão.prepareStatement("update FUNCIONARIO set nome = ? where id = ?");
			alterarFuncionario.setLong(2, id);
			alterarFuncionario.setString(1, novoNome);
			alterarFuncionario.execute();
		} finally {
			alterarFuncionario.close();
		}
	}

	private static void alterarPrimeiro(Connection conexão, String novoNome) throws Exception {
		PreparedStatement alterarFuncionarios = null;
		try {
			alterarFuncionarios = conexão.prepareStatement("update FUNCIONARIO set nome = ? where id = (select MIN(id) from FUNCIONARIO)");
			alterarFuncionarios.setString(1, novoNome);
			alterarFuncionarios.execute();
		} finally {
			alterarFuncionarios.close();
		}
	}
	
	private static void deletarFuncionarios(Connection conexão) throws Exception {
		PreparedStatement deletarFuncionarios = null;
		try {
			deletarFuncionarios = conexão.prepareStatement("delete from FUNCIONARIO where id = ?");
			for (int i = 1; i < 11; i++) {
				deletarFuncionarios.setInt(1, i);
				deletarFuncionarios.executeUpdate();
			}
		} finally {
			deletarFuncionarios.close();
		}
	}

	private static void inserirFuncionarios(Connection conexão) throws Exception {
		PreparedStatement inserirFuncionarios = null;
		try {
			inserirFuncionarios = conexão.prepareStatement("insert into FUNCIONARIO (id, nome) values (?,?)");
			for (int contador = 1; contador <= 2000; contador++) {
				inserirFuncionarios.setLong(1, contador);
				inserirFuncionarios.setString(2, "Funcionário(a)" + contador);
				inserirFuncionarios.execute();
			}
		} finally {
			inserirFuncionarios.close();
		}
	}

	private static void criarTabelaDeFuncionarios(Connection conexão) throws Exception {
		Statement criarTabela = null;
		try {
			criarTabela = conexão.createStatement();
			criarTabela.execute("create table if not exists FUNCIONARIO (" + "id long not null primary key,"
					+ "nome varchar(255) not null unique" + ")");
		} finally {
			criarTabela.close();
		}
	}

	private static Connection abrirConexão() throws Exception {
		Connection c = DriverManager.getConnection("jdbc:h2:~/loja", "sa", "");
		return c;
	}

}
