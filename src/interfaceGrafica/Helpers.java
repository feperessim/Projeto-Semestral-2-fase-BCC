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

package interfaceGrafica;

import java.util.ArrayList;
import java.util.List;

/* Classe com métodos de auxilio */
public class Helpers {

	private List<Double> x;
	private List<Double> y;
	
	/* Divide a string por vírgula, faz o parse de valores numéricos 
	 * dos pares ordenados e os add para uma lista x, y*/
	public void parseAndSet(String s) {
		String[] n = s.split(",");
		List<Double> x = new ArrayList<>();
		List<Double> y = new ArrayList<>();
		
		for (int i = 0; i < n.length; i++) {
			double p = Double.parseDouble(n[i]);
			if (i % 2 == 0) {
				x.add(p);
			}
			else {
				y.add(p);
			}
		}
		setX(x);
		setY(y);
		
	}
	
	public void parseAndSetY(String s) {
		String k = s.substring(1);
		String[] n = k.split("\\s+");
		
		List<Double> y = new ArrayList<Double>();
				
		for (int i = 0; i < n.length; i++) {
			double p = Double.parseDouble(n[i]);
			y.add(p);
		}
		setY(y);
			
	}
	
	public void parseAndSetX(String s) {
		String[] n = s.split(" ");
		List<Double> x = new ArrayList<>();
		
		for (int i = 0; i < n.length; i++) {
			double p = Double.parseDouble(n[i]);
			x.add(p);
		}
		setX(x);
	}
	
	public void setX(List<Double> x) {
		this.x = x;
	}
	
	public void setY(List<Double> y) {
		this.y = y;
	}
	
	public List<Double> getX() {
		return x;
	}
	
	public List<Double> getY() {
		return y;
	}
	
	/*
	public static void main(String[] args) {
		String text = "12.5, 5.8, 8.6";
		String[] n = text.split(",");
		
		for (int i = 0; i < n.length; i++) {
			try {
				double p = Double.parseDouble(n[i]);
				System.out.println(p);
			} catch (Exception e) {
				System.out.println("shit");
			}
		}
	}*/	
}
