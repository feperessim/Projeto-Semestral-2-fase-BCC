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

package projetoFinal;

import java.util.List;
import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.regression.SimpleRegression;

public class FuncoesEstatisticas {

	private List<Double> x;
	private List<Double> y;
	private DescriptiveStatistics descreptiveStatForX;
	private DescriptiveStatistics descreptiveStatForY; 
	private SimpleRegression regression;
	private final WeightedObservedPoints obs;
	private double cofAQuadraticReg;
	private double cofBQuadraticReg;
	private double cofCQuadraticReg;
     
	
	/* Construtor 1 da Classe Funções Estatísticas. 
	 * Inicializa como null as listas caso nenhum 
	 * argumento seja passado*/
	public FuncoesEstatisticas() {
		descreptiveStatForX = new DescriptiveStatistics();
		regression = new SimpleRegression();
		descreptiveStatForY = new DescriptiveStatistics();
		obs = new WeightedObservedPoints();
		cofAQuadraticReg = 0.0;
		cofBQuadraticReg = 0.0;
		cofCQuadraticReg = 0.0;
		x = null;
		y = null;
	}
	
	/* Construtor 2 da  Classe Funções Estatísticas.
	 *  Inicializa descreptiveStat com os valores 
	 * de uma lista passada como argumento*/
	public FuncoesEstatisticas(List<Double> x) {
		descreptiveStatForX = new DescriptiveStatistics();	
		regression = new SimpleRegression();
		descreptiveStatForY = new DescriptiveStatistics();
		obs = new WeightedObservedPoints();
		cofAQuadraticReg = 0.0;
		cofBQuadraticReg = 0.0;
		cofCQuadraticReg = 0.0;
		setDescreptiveStat(x);
	}

	/* Construtor que recebe valores de x e y, inicializa a lista de objetos
	 * para regressão, e funções estatísticas para ambas as listas que contém
	 * pares ordenados */
	public FuncoesEstatisticas(List<Double> x, List<Double> y) {
		descreptiveStatForX = new DescriptiveStatistics();	
		descreptiveStatForY = new DescriptiveStatistics();	
		regression = new SimpleRegression();	
		obs = new WeightedObservedPoints();
		setDescreptiveStat(x);
		cofAQuadraticReg = 0.0;
		cofBQuadraticReg = 0.0;
		cofCQuadraticReg = 0.0;
		setQuadraticRegression(x, y); 
		setDescreptiveStatForY(y);
		if (x.size() == y.size()) {
			setRegression(x, y);
		}
	} 

	/* Calcula a média de uma lista com dados numéricos e retorna seu resultado*/
	public double getMean(String var) {
		if (var.equalsIgnoreCase("x")) {
			return descreptiveStatForX.getMean();
		} 
		else {
			return descreptiveStatForY.getMean();
		}
	}
	
	/* Calcula o desvio padrão e retorna seu resultado - sqrt((x - media)^2 / (n-1))*/
	public double getStdDeviaton (String var) {
		if (var.equalsIgnoreCase("x")) {
			return descreptiveStatForX.getStandardDeviation();
		}
		else {
			return descreptiveStatForY.getStandardDeviation();
		}
	}
	
	/* Calcula o desvio padrão e retorna seu resultado - sqrt((x - media)^2 / n))*/
	public double getPopulationStdDeviaton (String var) {
		if (var.equalsIgnoreCase("x")) {
			return Math.sqrt(descreptiveStatForX.getPopulationVariance());
		}
		else {
			return Math.sqrt(descreptiveStatForY.getPopulationVariance());
		}
	}
	
	/* Calcula a variança e retorna seu valor - E (x - media)^2 / (n-1)*/
	public double getVariance(String var) {
		if (var.equalsIgnoreCase("x")) {
			return descreptiveStatForX.getVariance();
		}
		else {
			return descreptiveStatForY.getVariance();
		}
	}
	
	/* Calcula variança populacional - E (x - media)^2 / n*/
	public double getPopulationVariance(String var) {
		if (var.equalsIgnoreCase("x")) {
			return descreptiveStatForX.getPopulationVariance();
		}
		else {
			return descreptiveStatForY.getPopulationVariance();
		}
	}
	
	/* Calcula e retorna o coeficiente de curtose*/
	public double getKurtosis(String var) {
		if (var.equalsIgnoreCase("x")) {
			return descreptiveStatForX.getKurtosis();
		}
		else {
			return descreptiveStatForY.getKurtosis();
		}
	}
	
	/* Calcula a mediana e retorna o seu valor */
	public double getMedian(String var) {
		if (var.equalsIgnoreCase("x")) {
			return descreptiveStatForX.getPercentile(50);
		}
		else {
			return descreptiveStatForY.getPercentile(50);
		}
	}
	
	/* Computa Regressão linear e retorna o coeficiente linear */
	public double getIntercept() {
		return regression.getIntercept();
	}
	
	/* Computa Regressão e retorna o coeficiente linear angular da reta */
	public double getSlop() {
		return regression.getSlope();
	}
	
	/* Computa regressão e retorna coeficiente linear com erro padrão */
	public double getSlopeStdErr() {
		return regression.getSlopeStdErr();
	}

	/* Seta pares ordenados individualmente para a lista de regressão*/
	public void setRegression(double x, double y) {
		this.regression.addData(x,y);
	}
	
	/* Seta os pares ordenados das listas x e y para a lista de regressão */
	public void setRegression(List<Double> x, List<Double> y) {
		regression.clear();
		if (x.size() == y.size()) {
			for (int i = 0; i < y.size(); i++) {
				regression.addData(x.get(i), y.get(i));
			}
		}
	}

	/* Seta valores individualmente para a lista de estatística descritiva referente a váriavel y*/
	public void setDescreptiveStatForY(double y) {
		descreptiveStatForY.addValue(y);
	}

	/* Seta valores de uma lista com dados para a lista de estatística descritiva referente a váriavel y*/
	public void setDescreptiveStatForY(List<Double> y) {
		for (int i = 0; i < y.size(); i++) {
			descreptiveStatForY.addValue(y.get(i));
		}
	}
	
	/* Seta valores individualmente para a lista de estatística descritiva referente a váriavel x*/
	public void setDescreptiveStat(double x) {
		descreptiveStatForX.addValue(x);
	}

	/* Seta valores de uma lista com dados para a lista de estatística descritiva referente a váriavel x*/
	public void setDescreptiveStat(List<Double> x) {
		for (int i = 0; i < x.size(); i++) {
			descreptiveStatForX.addValue(x.get(i));
		}
	}
	
	/* Prediz um valor y para um valor x na regressão linear */
	public double predict(double x) {
		return regression.predict(x);
	}
	
	/* Computa e retorna o coeficiente de correlação */
	public double getR() {
		return regression.getR();
	}
	
	/* Computa e retorna de determinação */
	public double getRSquared() {
		return regression.getRSquare();
	}
	
	/* Retorna o valor máximo de um conjunto de dados x ou y */
	public double getMax(String var) {
		if (var.equalsIgnoreCase("x")) {
			return descreptiveStatForX.getMax();
		}
		else {
			return descreptiveStatForY.getMax();
		}
	}
	
	/* Retorna o valor minímo do conjunto de dados x ou y */
	public double getMin(String var) {
		if (var.equalsIgnoreCase("x")) {
			return descreptiveStatForX.getMin();
		}
		else {
			return descreptiveStatForY.getMin();
		}
	}
	
	/* Computa e retorna o valor da quantidade de elementos do conjunto de dados */
	public double getNforRegression() {
		if (regression != null) { 
			return regression.getN();
		}
		else {
			return 0.0;
		}
	}
	/* Computa e retorna o valor da quantidade de elementos do conjunto de dados para variavel x */
	public double getNforDescreptiveStatforX() {
		if (descreptiveStatForX != null) {
			return descreptiveStatForX.getN();
		}
		else { 
			return 0.0;
		}
	}
	
	/* Computa e retorna o valor da quantidade de elementos do conjunto de dados para variavel y */
	public double getNforDescreptiveStatforY() {
		if (descreptiveStatForY != null) {
			return descreptiveStatForY.getN();
		}
		else { 
			return 0.0;
		}
	}
	
	/* Computa e retorna o somátório dos valores do conjunto de dados informado */
	public double getSum(String var) {
		if (var.equalsIgnoreCase("x")) {
			return descreptiveStatForX.getSum();
		}
		else {
			return descreptiveStatForY.getSum();
		}
	}
	
	/* Computa e retorna a soma do quadrado dos valores do conjunto de dados informado */
	public double getSumsq(String var) {
		if (var.equalsIgnoreCase("x")) {
			return descreptiveStatForX.getSumsq();
		}
		else {
			return descreptiveStatForY.getSumsq();
		}
	}
	
	/* Computa e retorna o soma dos produtos cruzados */
	public double getSumOfCrossProducts() {
		return regression.getSumOfCrossProducts();
	}
	
	
	/* Computa a regressão quadrática */
	public void setQuadraticRegression(List<Double> x, List<Double> y) {
		obs.clear();
		setCoefCQuadraticReg(0);
		setCoefBQuadraticReg(0);
		setCoefAQuadraticReg(0);
		
        for (int i = 0; i < x.size() && x.size() == y.size(); i++) {
           		obs.add(x.get(i), y.get(i));
        }
        if (x.size() > 0 && x.size() == y.size()) {
        	final PolynomialCurveFitter fitter = PolynomialCurveFitter.create(2);
        	final double[] coeff = fitter.fit(obs.toList());
        
        	setCoefCQuadraticReg(coeff[0]);
        	setCoefBQuadraticReg(coeff[1]);
        	setCoefAQuadraticReg(coeff[2]);
        }
	}
	
	/* Seta o coeficiente A da regressão Quadrática */
	private void setCoefAQuadraticReg(double a) {
		cofAQuadraticReg = a;
	}
	
	/* Retorna o coeficiente A da regressão Quadrática */
	public double getAQuadraticReg() {
		return cofAQuadraticReg;
	}
	
	/* Seta o coeficiente B da regressão Quadrática */
	private void setCoefBQuadraticReg(double b) {
		cofBQuadraticReg = b;
	}
	
	/* Retorna o coeficiente B da regressão Quadrática */
	public double getBQuadraticReg() {
		return cofBQuadraticReg;
	}
	
	/* Seta o coeficiente C da regressão Quadrática */
	private void setCoefCQuadraticReg(double c) {
		cofCQuadraticReg = c;
	}
	
	/* Retorna o coeficiente C da regressão Quadrática */
	public double getCQuadraticReg() {
		return cofCQuadraticReg;
	}
	
	/* Retorna lista de dados referentes a variável X */
	public List<Double> getX() {
		return x;
	}
	
	/* Seta um valor a lista X */
	public void setX(double x) {
		this.x.add(x);
	}
	
	/* Retorna lista de dados referentes a variável Y */
	public List<Double> getY() {
		return y;
	}

	/* Seta um valor a lista y */
	public void setY(double y) {
		this.y.add(y);
	}
	
	/* Limpa a lista atual e atribui uma nova lista a x */
	public void setX(List<Double> x) {
		this.x = x;
		descreptiveStatForX.clear();
		setDescreptiveStat(x);
	}
	
	/* Limpa a lista atual e atribui uma nova lista a y */
	public void setY(List<Double> y) {
		this.y = y;
		descreptiveStatForY.clear();
		setDescreptiveStatForY(y);
	}
	
	/* Retorna objeto referente a estatística descritiva*/
	public DescriptiveStatistics getDescreptiveStat() {
		return descreptiveStatForX;
	}

	/* Retorna objeto referente a regressão simples */
	public SimpleRegression getRegression() {
		return regression;
	}
	
	/* Limpa a memória */
	public void clear() {
		descreptiveStatForX.clear();
		descreptiveStatForY.clear();
		regression.clear();
		obs.clear();
		setCoefAQuadraticReg(0);
		setCoefBQuadraticReg(0);
		setCoefCQuadraticReg(0);
		
	}
	
}
