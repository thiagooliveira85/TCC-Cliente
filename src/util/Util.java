package util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Util{

	public static final String EMAIL_REGULAR_EXPRESSION = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9]{1}[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$";
	public static final String LOGIN_REGULAR_EXPRESSION = "[a-zA-Z0-9._-]{3,12}$";

	public static final String comAcento = "Á«·ÈÌÛ˙˝¡…Õ”⁄›‡ËÏÚ˘¿»Ã“Ÿ„ıÒ‰ÎÔˆ¸ˇƒÀœ÷‹√’—‚ÍÓÙ˚¬ Œ‘€";  
	public static final String semAcento = "cCaeiouyAEIOUYaeiouAEIOUaonaeiouyAEIOUAONaeiouAEIOU";
	
	public boolean validarTamanhaMaximoCampo(String campo, int tam) {
		if(campo.length() <= tam)
			return true;
		return false;
	}

    
	public static boolean isEmpty(String str, boolean trim) {
		if ( str == null || str.equals("") || ( trim && str.trim( ).equals( "" )) ) { 
			return true;
		}
		return false;
	}
	
	public static String resgataParamString(HttpServletRequest request, String param){
		String campo = request.getParameter(param);
		if (request != null && campo != null)
			return campo;
		return "";		
	}	
	
	public static String[] getProperties(Class<?> classe, String nomeArq, String param) {
		String[] dados = new String[]{};

		try {
			Properties props = new Properties();
			InputStream inputStream = classe.getResourceAsStream(nomeArq);		
			props.load(inputStream);

			if (props != null) {
				String inf = props.getProperty(param);
				if (inf != null) {
					dados = inf.split(",");	
				}
			}
		} catch (IOException e) {
			System.out.println("Erro ao abrir properties = " + nomeArq);
		}

		return dados;
	}

	public static String getFirstProperties(Class<?> classe, String nomeArq, String param) {
		String dado = "";

		try {
			Properties props = new Properties();
			InputStream inputStream = classe.getResourceAsStream(nomeArq);		
			props.load(inputStream);

			if (props != null) {
				dado = props.getProperty(param);
			}
		} catch (IOException e) {
			System.out.println("Erro ao abrir properties = " + nomeArq);
		}

		return dado;
	}

	public static String getWebsiteUri(HttpServletRequest request){

		if(request != null){
			String uri = request.getRequestURI();
			String servletPath = request.getServletPath();
			int index = uri.lastIndexOf(servletPath);
			if(index != -1)
				uri = uri.substring(0, index);
			if(uri.endsWith("/"))
				uri = uri.substring(0, uri.length() - 1);
			return uri;
		}
		return null;
	}

	public static String criaSQL(String alias, String coluna, String dado)
	{
		int contador = 0;
		boolean primeiraVez = true, temDado = false;
		String sql = " and (" + alias + "." + coluna + " in (";

		try
		{
			if (dado!=null && !dado.equals(""))
			{
				//Divido a string de 500 em 500
				StringTokenizer strLinha = new StringTokenizer(dado,",");

				while (strLinha.hasMoreTokens())
				{
					if (temDado){
						sql += "," ;
					}else{ 
						if(!primeiraVez)
							sql += ") or " + alias + "." + coluna + " in (";
					}

					sql += strLinha.nextToken();
					temDado = true;
					primeiraVez = false;
					contador++;
					if (contador % 500 == 0)
						temDado = false;
				}

				sql += "))" ;

				if (contador == 0)
					sql = "";
			}else sql = "";
		}catch (Exception e) {
			sql = "";
		}

		return sql; 
	}

	public static String retiraAcentos(String txt){
		if ( txt != null && txt.length( ) > 0 ) {
			for(int i = 0; i < semAcento.length( ); i++) {
				txt = txt.replaceAll( String.valueOf( comAcento.charAt( i ) ), String.valueOf( semAcento.charAt( i ) ) );
			}
		}
		return txt;
	}
	
	public static void exportarCSV(HttpServletResponse response, List<String[]> lstResultado, SimpleDateFormat sdf, String... config){

		String newRow = "\r\n";
		StringBuilder currentRow = null;
		int count = -1;

		try{

			//guardando o nome do arquivo
			String nomeArquivo = (sdf != null) ? config[0] + "_" + sdf.format(new Date()) + ".CSV" : config[0] + ".CSV";

			response.setContentType("text/plain");
			response.addHeader("Content-disposition", "attachment;filename=" + nomeArquivo);

			//carrega o streaming de saida que recebe os dados
			ServletOutputStream os = response.getOutputStream();
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(os, "ISO-8859-1"));			

			//gerando o arquivo csv
			for (String[] line : lstResultado){

				count++;
				currentRow = new StringBuilder("");
				for (String column : line){

					//substitui valores nulos por vazio
					if (config.length > 3 && config[3].equals("S")){
						if (column == null || column.equals("null")){
							column = "";
						}
					}

					//verificando se coluna dever· ser apresentada como numero no CSV
					if (config.length > 2 && config[2].equals("S")){
						try{
							Long.parseLong(column);
							currentRow.append(((column != null) ? "=\"" + column.trim() + "\"" + ";" : " ;"));
						}catch(Exception e){
							currentRow.append(column + ";");
						}
					}else{
						currentRow.append(column + ";");
					}
				}

				currentRow.append(newRow);
				out.write(currentRow.toString());
			}

			//verificando se deve ser exibido o total de registros
			if (config.length > 1){
				if (config[1].equals("S")){
					out.write(newRow + newRow + "Total de Registros: " + count);
				}
			}

			//finaliza a exportaÁ„o do arquivo
			out.flush();
			out.close(); 

		}catch(Exception e){
			System.out.println("Erro: " + e.getMessage());
		}
	}

	public static void exportarTXT(HttpServletResponse response, String lstResultado, SimpleDateFormat sdf, String... config){

		try{

			//guardando o nome do arquivo
			String nomeArquivo = (sdf != null) ? config[0] + "_" + sdf.format(new Date()) + ".TXT" : config[0] + ".TXT";

			response.setContentType("text/plain");
			response.addHeader("Content-disposition", "attachment;filename=" + nomeArquivo);

			//carrega o streaming de saida que recebe os dados
			ServletOutputStream os = response.getOutputStream();
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(os, "ISO-8859-1"));			

			//gerando o arquivo txt 
			out.write(lstResultado);

			//finaliza a exportaÁ„o do arquivo
			out.flush();
			out.close(); 

		}catch(Exception e){
			System.out.println("Erro: " + e.getMessage());
		}
	}

	private static String limpaValor(String val) {
		String valor = val;
		String[] caracteres = {" ","/", "-" };
		for (int i = 0; i < caracteres.length; i++) {
			valor = valor.replaceAll(caracteres[i], "");
		}
		return valor;
	}

	public static String formataString(String mask, String valor){
		/*  
		 * Formata valor com a mascara passada    
		 */ 
		String mascara = mask;
		for(int i = 0; i < valor.length(); i++){   
			mascara = mascara.replaceFirst("#", valor.substring(i, i + 1));   
		}   
		return mascara.replaceAll("#", "");   
	}

	
	public static String formataCEP(String val) throws Exception {
		String valor = val;
		if( valor == null ) {
			throw new NullPointerException();
		}
		valor = valor.trim();
		valor = limpaValor(valor);
		try {
			Long.parseLong(valor);
		} catch (NumberFormatException nfe) {
			throw new Exception(nfe);
		}
		if( valor.length() < 8) {
			throw new Exception("Valor inv·lido! CEP deve ter 8 n˙meros.");
		}
		return formataString("#####-###",valor);
	}

	public static String formataCPF(String val) throws Exception {
		String valor = val;
		if( valor == null ) {
			throw new NullPointerException();
		}
		valor = valor.trim();
		valor = limpaValor(valor);
		if( valor.length() < 11) {
			throw new Exception("Valor inv·lido! CPF deve ter 11 n˙meros.");
		}
		return formataString("###.###.###-##",valor);
	}

	public static String formataCNPJ(String val) throws Exception {
		String valor = val;
		if( valor == null ) {
			throw new NullPointerException();
		} 
		valor = valor.trim();
		valor = limpaValor(valor);
		try {
			Long.parseLong(valor);
		} catch (NumberFormatException nfe) {
			throw new Exception(nfe);
		}
		if( valor.length() < 11) {
			throw new Exception("Valor inv·lido! CNPJ deve ter 14 n˙meros.");
		}
		return formataString("##.###.###/####-##",valor);
	}


	public static boolean validaFormatoEmail(String email, String regex) {
		return Util.isEmail(email);
	}

	public static boolean validaTexto(String texto, String regex) {

		if ( texto == null ) { 
			return false;
		}

		Pattern pattern = Pattern.compile(regex);
		Matcher m = pattern.matcher(texto);
		if ( m.matches() ) {
			return true;
		}
		return false;
	}
	
	public static String getSubString(String str, int size, boolean start){
		if (str != null && !str.equals("") &&  size <= str.length()){
			if (start)
				str = str.substring(0, size);
			else
				str = str.substring(size);
		}
		return str;
	}
		
	public static String criptografa(String texto) throws Exception {
		if (texto == null) {
			return null;
		}

		BASE64Encoder enc64 = new BASE64Encoder();
		String chave = "criptografiaAES.";
		byte[] chaveEmBytes = chave.getBytes();
		SecretKeySpec skeySpec = new SecretKeySpec(chaveEmBytes, "AES");

		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] dados = cipher.doFinal(texto.getBytes());

		String resposta = enc64.encode(dados);

		return resposta;
	}
	
	public static String descriptografa(String texto) throws Exception  
	{
		if(texto == null) {
			throw new Exception();
		} 

		byte[] original = null;

		try {

			BASE64Decoder dec64  = new BASE64Decoder();
			String chave    = "criptografiaAES.";
			SecretKeySpec skeySpec  = new SecretKeySpec(chave.getBytes(), "AES");

			Cipher cipher    = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			original     = cipher.doFinal(dec64.decodeBuffer(texto));

		} catch (Exception e) {
			throw e;
		} 

		return new String(original);
	}
	
	public static String onlyNumbers( String str ) {
		if ( str == null ) {
			return null;
		}
		return str.replaceAll("[^0-9]", "");
	}

	public static boolean isCPFOk(String cpfParam) {
		String cpf = cpfParam;
		if ( cpf == null || cpf.trim().equals("") ) {
			return false;
		}

		cpf = onlyNumbers(cpf);
		int d1, d2;
		int digito1, digito2, resto;
		int digitoCPF;
		String nDigResult;

		d1 = d2 = 0;
		digito1 = digito2 = resto = 0;

		for (int nCount = 1; nCount < cpf.length() - 1; nCount++) {
			digitoCPF = Integer.valueOf(cpf.substring(nCount - 1, nCount)).intValue();

			//multiplique a ultima casa por 2 a seguinte por 3 a seguinte por 4 e assim por diante.  
			d1 = d1 + (11 - nCount) * digitoCPF;

			// para o segundo digito repita o procedimento incluindo o primeiro digito calculado no passo anterior.
			d2 = d2 + (12 - nCount) * digitoCPF;
		}

		//Primeiro resto da divis„o por 11.
		resto = (d1 % 11);

		//Se o resultado for 0 ou 1 o digito È 0 caso contr·rio o digito È 11 menos o resultado anterior.  
		if (resto < 2) {
			digito1 = 0;
		} else {
			digito1 = 11 - resto;
		}

		d2 += 2 * digito1;

		//Segundo resto da divis„o por 11.
		resto = (d2 % 11);

		//Se o resultado for 0 ou 1 o digito È 0 caso contr·rio o digito È 11 menos o resultado anterior.  
		if (resto < 2) {
			digito2 = 0;
		} else {
			digito2 = 11 - resto;
		}

		//Digito verificador do CPF que est· sendo validado.
		String nDigVerific = cpf.substring(cpf.length() - 2, cpf.length());

		//Concatenando o primeiro resto com o segundo.
		nDigResult = String.valueOf(digito1) + String.valueOf(digito2);

		//comparar o digito verificador do cpf com o primeiro resto + o segundo resto.
		return nDigVerific.equals(nDigResult);
	}
	
	public static boolean isEmpty( String[] array ) {
		if ( array == null || array.length == 0 || array[0].toString().equals("") )  {
			return true;
		}
		return false;
	}
	
	public static boolean isEmpty( List<String> lst ) {
		if ( lst == null || lst.isEmpty() )  {
			return true;
		}
		return false;
	}

	public static boolean isEmpty( String str ) {
		return isEmpty(str, true);
	}

	public static boolean equals( String a, String b ) {
		if ( a == b ) {
			return true;
		} else if ( a != null && b != null && a.toUpperCase() == b.toUpperCase() ) {
			return true;
		}
		return false;
	}

	public static String onlyAlphaNumeric(String str) {
		if (str == null) {
			return null;
		}
		return str.replaceAll("[^a-zA-Z0-9]", "");
	}

	public static boolean isEmail(String email) {

		if (email == null) {
			return false;
		}

		Pattern pattern = Pattern.compile( EMAIL_REGULAR_EXPRESSION );
		Matcher m = pattern.matcher(email);
		if ( m.matches() ) {
			return true;
		}
		return false;
	}

	public static boolean isWindows() {
		return System.getProperty("os.name").toUpperCase().contains("WINDOWS");
	}

	public static String completaStringEsquerda(String texto, int tamanho, String caracter){
		String str = caracter;
		String txt = texto;
		if ( isEmpty(str) ) {
			str = " ";
		}
		if ( isEmpty(texto) ) {
			txt = caracter;
		}
		if ( txt.length() >= tamanho ) {
			return texto;
		}
		return completaStringEsquerda(caracter + txt, tamanho, caracter);
	}

	public static boolean isCollectionEmpty(Collection<?> col) {
		return isEmpty(col);
	}

	private static boolean isEmpty(Collection<?> col) {
		if ( col == null || col.size() == 0) {
			return true;
		}
		return false;
	}
	
	public static List<String> split(String str, String delimitador) throws Exception {		
		String aux[] =	null;		
		try{
			aux = str.split(delimitador);
			return Arrays.asList(aux);
		}catch (Exception e) {
			throw e;
		}
	}
	
}