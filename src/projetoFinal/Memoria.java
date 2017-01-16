/* Copyright (C) 2017 Felipe de Lima Peressim
 
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

import java.util.ArrayList;
import java.util.List;

import com.persistencia.ManipXMLX;

public class Memoria {

	private List<Double> x;
	private List<Double> y;
	
	/* Construtor 1 - Cria instancias para as variáveis x e y */
	public Memoria() {
		x = new ArrayList<Double>();
		y = new ArrayList<Double>();
	}
	
	/* Inicializa a lista x e instancia a lista y */
	public Memoria(List<Double> x) {
		this.x = x;
		y = new ArrayList<Double>();
	}
	
	/* Inicializa a lista x e a lista y */
	public Memoria(List <Double>x, List <Double>y) {
		this.x = x;
		this.y = y;
	} 

	/* Retorna a lista x */
	public List<Double> getX() {
		return x;
	}

	/* Seta em x uma lista passada */
	public void setX(List<Double> x) {
		for (int i = 0; i < x.size(); i++) {
			this.x.add(x.get(i));
		}
	}

	/* Retorna a lista y */
	public List<Double> getY() {
		return y;
	}

	/* Seta em  uma lista passada */
	public void setY(List<Double> y) {
		for (int i = 0; i < y.size(); i++) {
			this.y.add(y.get(i));
		}
	}
	
	/* Seta valores individuais para a lista x */
	public void setX(double x) {
		this.x.add(x);
	}
	
	/* Seta valores individuais para a lista y */
	public void setY(double y) {
		this.y.add(y);
	}
	
	/* Faz a persistência dos valores de x ou y */
	public void persistencia(String var) {
		if (var.equalsIgnoreCase("x")) {
			ManipXMLX.gravarXML(x);
		}
		else if (var.equalsIgnoreCase("y")) {
			ManipXMLX.gravarXML(y);
		}
	}
	
	/* Lê valores persistidos de x ou y*/
	public void readData() {
		setX(ManipXMLX.lerXMLCOM());
	}
	
	public void delData(String var, int val) {
		if (var.equalsIgnoreCase("x") && !x.isEmpty()) {
			if (val == 0) {
				x.remove(x.size()-1);
			}
			else {
				if (val-1 < x.size()) {
					x.remove(val-1);	
				}	
			}
		}
		else if (var.equalsIgnoreCase("y") && !y.isEmpty()) {
			if (val  == 0) {
				y.remove(y.size()-1);
			}
			else {
				if (val-1 < y.size()) {
					y.remove(val-1);	
				}	
			}
		}
	}
	
	/* Limpa a memória */
	public void clear() {
		x.clear();
		y.clear();
	}
	
}
