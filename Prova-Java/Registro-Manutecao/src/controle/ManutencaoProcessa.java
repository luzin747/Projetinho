package controle;

import java.util.ArrayList;

import modelo.Manutencao;
import uteis.Cripto;

public class ManutencaoProcessa {

	public static ArrayList<Manutencao> usuarios = new ArrayList<>();
	private static ManutencaoDAO ud = new ManutencaoDAO();

	public static void carregar() {
		usuarios = ud.ler();
		if(usuarios.size() == 0) {
			usuarios.add(new Manutencao("admin@admin.com",Cripto.encripta("admin")));
		}
	}

	public static void salvar() {
		ud.escrever(usuarios);
	}
	
	public static int checarEmail(String email) {
		int retorno = -1;
		for (int i = 0; i < usuarios.size(); i++) {
			if (usuarios.get(i).getEmail().equals(email)) {
				return i;
			}
		}
		return retorno;
	}

	public static boolean checarSenha(int indice, String senha) {
		if (usuarios.get(indice).getSenha().equals(senha)) {
			return true;
		}
		return false;
	}
}
