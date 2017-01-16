/* Copyright (C) 2017 Felipe de Lima Peressim
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.persistencia;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

public class ManipXMLX{

	private static String NOMEDOARQUIVO = "DATA_VAR_X";
	//final static String LOCALHOST = "C:/Users/Felipe/Dropbox/workspace/ProjetoFinal/xml/";
	 
	public static void setNomeArquivo(String fileName) {
		NOMEDOARQUIVO = fileName;
	}
	
	public static boolean gravarXML(List<Double> X){		//
		// Cria o elemento que ser� o root
				Element config = new Element("Data");

				//define config como root
				Document documento = new Document(config);

				for (int x = 0; x < X.size(); x++){
					Element VAR_X = new Element("VAR_X");
					
					VAR_X.setAttribute("N", String.valueOf(x));
					
					Element Num = new Element("x");
					Num.setText(String.valueOf(X.get(x)));
													
					VAR_X.addContent(Num);
					config.addContent(VAR_X);		
				}
		//classe respons�vel para imprimir / gerar o xml
		XMLOutputter xout = new XMLOutputter();	
		try {
			//criando o arquivo de saida
			BufferedWriter arquivo = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(NOMEDOARQUIVO + ".xml"),"UTF-8"));
			//imprimindo o xml no arquivo
			xout.output(documento, arquivo);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static List<Double> lerXMLCOM(){
		List<Double> x = new ArrayList<Double>();
		Document doc = null;
		SAXBuilder builder = new SAXBuilder();	
		try { 
			doc = builder.build(NOMEDOARQUIVO);
		} catch (Exception e) {
			e.printStackTrace();
		}            
		Element config = doc.getRootElement();
		List<Double> lista = config.getChildren("VAR_X");
		
		for (Iterator iter = lista.iterator(); iter.hasNext();) {
			Element element = (Element) iter.next();
			double num = Double.parseDouble(element.getChildText("x"));
			x.add(num);
		}
		
		return x;
	}	
}
