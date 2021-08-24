import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

/**
 * Projeto1 de POO
 * Grupo de alunos:  João Jorge Evangelista Fernandes, João Pedro Sousa Rodrigues
 *
 */

public class JogoDaForca {
	private int N; // quantidade de palavras do arquivo (lido do arquivo)
	private String[] palavras; // um array com as N palavras (lidas do arquivo)
	private String[] dicas; // um array com as N dicas (lidas do arquivo)
	private String palavra;
	private String[] palavraEscondida;
	private int indice = -1; // indice da palavra sorteadado jogo
	private int acertos; // total de acertos do jogo
	private int erros; // total de erros do jogo
	private String[] penalidades = {"perna","perna","braço","braço","tronco","cabeça"};

	public JogoDaForca(String nomearquivo) throws Exception{
		try {
			Scanner arquivo = new Scanner(new File(nomearquivo));
			this.N = Integer.parseInt(arquivo.nextLine()); // Atribuindo this.N

			String[] palavra = new String[this.N];
			String[] dica = new String[this.N];
			for (int i = 0; i < N; i++){
				String[] linha = arquivo.nextLine().split(";");
				palavra[i] = linha[0];
				dica[i] = linha[1];
			}

			this.palavras = palavra; // Atribuindo this.palavras
			this.dicas = dica; // Atribuindo this.dicas

			arquivo.close();

		} catch (FileNotFoundException e) {
			throw new Exception("Arquivo inexistente");
		}
	}

	public void iniciar() {
		Random sorteio = new Random();
		this.indice = sorteio.nextInt(this.N); // Atribuindo this.indice
		this.palavra = this.palavras[this.indice]; // Atribuindo this.palavra
		this.palavraEscondida = new String[this.palavra.length()];
		for (int i = 0; i < this.palavraEscondida.length; i++)
			this.palavraEscondida[i] = "*";
	}

	public boolean adivinhou(String letra) {
		for (String letraEscondida : this.palavraEscondida)
			if (letraEscondida.equals(letra.toUpperCase()))
				return true; // Utilizado para não incrementar o número de acertos caso o usúario repita a msm letra

		String[] letras = this.palavra.split("");
		int acertos = 0;
		for ( int i = 0; i < letras.length; i++)
			if (letras[i].equals(letra.toUpperCase())){
				acertos++;
				this.palavraEscondida[i] = letras[i];
			}

		if (acertos > 0){
			this.acertos+= acertos;
			return true;
		}
		else{
			this.erros++;
			return false;
		}
	}

	public boolean terminou() {
		if (this.acertos == this.palavra.length() || this.erros == 6)
			return true;
		return false;
	}

	public String getPalavra() {
		String palavraMostrada = "";
		for (String letra : this.palavraEscondida)
			palavraMostrada += letra;
		return palavraMostrada;
	}

	public String getDica() {
		String dica = "";
		for (int i = 0; i < this.palavras.length; i++){
			if (this.palavras[i].equals(this.palavra))
				dica = this.dicas[i];
		}
		return dica;
	}

	public String getPenalidade() { return this.penalidades[this.erros-1]; }

	public int getAcertos() { return this.acertos; }

	public int getErros() { return this.erros; }

	public String getResultado() {
		if (this.erros == 6)
			return "Você foi enforcado";
		return "Ganhou o jogo";
	}

}