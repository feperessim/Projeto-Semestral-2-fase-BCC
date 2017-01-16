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

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JRadioButton;

import java.awt.BorderLayout;

import javax.swing.JTextField;

import java.awt.Dimension;

import javax.swing.JFormattedTextField;
import javax.swing.JTextPane;
import javax.swing.JList;

import java.awt.Color;
import java.awt.Point;
import java.awt.Insets;

import javax.swing.SwingConstants;

import java.awt.Font;
import java.awt.Button;

import javax.swing.JLabel;

import com.jgoodies.forms.factories.DefaultComponentFactory;
import com.persistencia.ManipXMLX;
import com.persistencia.ManipXMLY;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import java.awt.Cursor;
import java.awt.ComponentOrientation;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JButton;

import java.awt.SystemColor;

import javax.swing.UIManager;

import java.awt.Canvas;

import javax.swing.JScrollPane;
import javax.swing.JPopupMenu;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Window.Type;
import java.awt.Toolkit;
import java.awt.Label;

import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import javax.swing.JComboBox;
import javax.swing.Box;
import javax.swing.JSlider;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import projetoFinal.FuncoesEstatisticas;
import projetoFinal.Memoria;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.DefaultComboBoxModel;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.io.File;

import javax.swing.JFileChooser;

public class CalculadoraGui {

	private JFrame frmCalculadora;
	private JTextField textField;
	private JButton btnM;
	private boolean mPlus = true;
	private JButton btnNewButton_1;
	private JLabel lblNewLabel;
	private JButton btnN;
	private JButton btnx;
	private JButton btnNewButton_2;
	private JButton btny;
	private JButton btny_1;
	private JButton btnNewButton_3;
	private JButton btnNewButton_4;
	private JButton btnNewButton_5;
	private JButton btnYn;
	private JButton btnNewButton_6;
	private JButton btnXn;
	private JButton btnYn_1;
	private JButton btnYn_2;
	private JButton btnKy;
	private JComboBox comboBox;
	private Memoria memoria = new Memoria();
	private FuncoesEstatisticas stat = new FuncoesEstatisticas(); 
	private boolean statisticsMode = true;
	private boolean linearRegression = false;
	private boolean quadraticRegression = false;
	private JButton btnA;
	private JButton btnB;
	private JButton btnC;
	private JButton btnR;
	private JButton btnR_1;
	private JButton btnAbrir;
	private boolean xSaveOpenState = true;
	private boolean ySaveOpenState = false;
	private JButton btnM_1;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CalculadoraGui window = new CalculadoraGui();
					window.frmCalculadora.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CalculadoraGui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCalculadora = new JFrame();
		frmCalculadora.getContentPane().setIgnoreRepaint(true);
		frmCalculadora.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\felipe\\Dropbox\\workspace\\ProjetoFinal\\Icones\\Sigma-128.png"));
		frmCalculadora.setType(Type.POPUP);
		frmCalculadora.setTitle("Calculadora");
		frmCalculadora.setBounds(100, 100, 550, 403);
		frmCalculadora.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCalculadora.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setIgnoreRepaint(true);
		textField.addKeyListener(new KeyAdapter() {
			@Override
			/* Sempre que um valor for inserido e a tecla m+ foi pressionada
			 *  o número anterior é apagado do visor */
			public void keyPressed(KeyEvent arg0) {
				if (mPlus == true) {
					textField.setText("");
					mPlus = false;
				}
			}
		});
		textField.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		textField.setText("0");
		textField.setName("");
		textField.setFont(new Font("Tahoma", Font.PLAIN, 22));
		textField.setBorder(null);
		textField.setBackground(UIManager.getColor("TableHeader.background"));
		textField.setBounds(23, 26, 487, 45);
		frmCalculadora.getContentPane().add(textField);
		textField.setColumns(10);
		
		/* Retorna a média dos valores de x*/
		final JButton btnNewButton = new JButton("\u00B5");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textField.setText("µ = " + String.valueOf(stat.getMean("x")));
				textField.grabFocus();
			}
		});
		btnNewButton.setBounds(123, 292, 89, 23);
		frmCalculadora.getContentPane().add(btnNewButton);
		
		/* Botão M+ - quando o usuário pressiona essa tecla um número é salvo na memória */
		btnM = new JButton("M+");
		btnM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String s = textField.getText();
					/* Se tem ',' provavelmente temos pares ordenados
					 * chamamos um método auxiliador para fazer o parse
					 * e setar os valores dos pares ordenados as suas respectivas
					 * listas */ 
					if (s.contains(",") && (s.toCharArray())[0] != ',') {
						Helpers helper = new Helpers();
						helper.parseAndSet(s);
						memoria.setX(helper.getX());
						memoria.setY(helper.getY());
						stat.setX(memoria.getX());
						stat.setY(memoria.getY());
						
					}
					else {
						if ((s.toCharArray())[0] == ',') {
							Helpers helper = new Helpers();
							helper.parseAndSetY(s);
							memoria.setY(helper.getY());
							stat.setY(memoria.getY());
						}
						else {
							if (s.contains(" ")) {
								Helpers helper = new Helpers();
								helper.parseAndSetX(s);
								memoria.setX(helper.getX());
								stat.setX(memoria.getX());
							}
							else {
								double var = Double.parseDouble(s);
								memoria.setX(var);
								stat.setX(memoria.getX());
							}
						}
					}
					mPlus = true;
					textField.grabFocus();
				}catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Entrada inválida!");
					textField.grabFocus();				
				}
			}
		});
		btnM.setBounds(421, 292, 89, 23);
		frmCalculadora.getContentPane().add(btnM);

		/* Remove dados da calculadora */
		btnM_1 = new JButton("M-");
		btnM_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String s = textField.getText();
					if (s.isEmpty() || !(s.trim().length() > 0) || s == null) {
						if (xSaveOpenState) {
							memoria.delData("x", -1);
							stat.setX(memoria.getX());
						}
						else if (ySaveOpenState) {
								memoria.delData("y", -1);
								stat.setY(memoria.getY());
						}		
					}
					else {
						int var = Integer.parseInt(s);
						if (xSaveOpenState) {
							memoria.delData("x", var);
							stat.setX(memoria.getX());
						}
						else if (ySaveOpenState) {
								memoria.delData("y", var);
								stat.setY(memoria.getY());
						}		
					}
				}catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Entrada inválida!");
					textField.grabFocus();				
				}
				textField.grabFocus();				
				
			}
		});
		
		/* Retorna a média dos valores de y */
		btnNewButton_1 = new JButton("\u0233");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textField.setText("ȳ = " + String.valueOf(stat.getMean("y")));
				textField.grabFocus();
			}
		});
		btnNewButton_1.setBounds(222, 292, 89, 23);
		frmCalculadora.getContentPane().add(btnNewButton_1);
		
		/* Exibe a quantidade de dados inseridos na váriavel x */
		btnN = new JButton("n-x");
		btnN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textField.setText("n = " + (int)stat.getNforDescreptiveStatforX());
				textField.grabFocus();
			}
		});
		btnN.setBounds(123, 190, 89, 23);
		frmCalculadora.getContentPane().add(btnN);
		
		/* Exibe a quantidade de dados inseridos na váriavel x */
		JButton btnNy = new JButton("n-y");
		btnNy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textField.setText("n = " + (int)stat.getNforDescreptiveStatforY());
				textField.grabFocus();
			}
		});
		btnNy.setBounds(222, 190, 89, 23);
		frmCalculadora.getContentPane().add(btnNy);
		
		/* Mostra o somatório de x */
		btnx = new JButton("∑x");
		btnx.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textField.setText("∑x = " + String.valueOf(stat.getSum("x")));
				textField.grabFocus();
			}
		});
		btnx.setBounds(122, 258, 89, 23);
		frmCalculadora.getContentPane().add(btnx);
		
		/* Mostra o somatório de x^2 */
		btnNewButton_2 = new JButton("∑x²");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textField.setText("∑x² = " + String.valueOf(stat.getSumsq("x")));
				textField.grabFocus();
			}
		});
		btnNewButton_2.setBounds(222, 258, 89, 23);
		frmCalculadora.getContentPane().add(btnNewButton_2);
		
		/* Mostra o somatório de y */
		btny = new JButton("∑y");
		btny.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textField.setText("∑y = " + String.valueOf(stat.getSum("y")));
				textField.grabFocus();
			}
		});
		btny.setBounds(122, 224, 89, 23);
		frmCalculadora.getContentPane().add(btny);
		
		/* Mostra o somatório de y^2 */
		btny_1 = new JButton("∑y²");
		btny_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textField.setText("∑y² = " + String.valueOf(stat.getSumsq("y")));
				textField.grabFocus();
			}
		});
		btny_1.setBounds(222, 224, 89, 23);
		frmCalculadora.getContentPane().add(btny_1);
		
		/* Mostra o Desvio Padrão Populacional em relação a variável x*/
		btnNewButton_3 = new JButton("xσn");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textField.setText("xσn = " + String.valueOf(stat.getPopulationStdDeviaton("x")));
				textField.grabFocus();
			}
		});
		btnNewButton_3.setBounds(321, 258, 89, 23);
		frmCalculadora.getContentPane().add(btnNewButton_3);
		
		/* Mostra o Desvio Padrão Amostral em relação a variável x */
		btnNewButton_4 = new JButton("xσn-1");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textField.setText(String.valueOf("xσn-1 = " + stat.getStdDeviaton("x")));
				textField.grabFocus();	
			}
		});
		btnNewButton_4.setBounds(421, 258, 89, 23);
		frmCalculadora.getContentPane().add(btnNewButton_4);
		
		/* Mostra o Desvio Padrão Amostral em relação a variável x */
		btnYn = new JButton("yσn-1");
		btnYn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textField.setText(String.valueOf("yσn-1 = " + stat.getStdDeviaton("y")));
				textField.grabFocus();
			}
		});
		btnYn.setBounds(321, 190, 89, 23);
		frmCalculadora.getContentPane().add(btnYn);
		
		/* Mostra o Desvio Padrão Amostral em relação a variável y */
		btnNewButton_5 = new JButton("yσn");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textField.setText("yσn = " + String.valueOf(stat.getPopulationStdDeviaton("y")));
				textField.grabFocus();
			}
		});
		btnNewButton_5.setBounds(421, 190, 89, 23);
		frmCalculadora.getContentPane().add(btnNewButton_5);
		
		/* Mostra a Variância Populacional em relação a variável x */
		btnNewButton_6 = new JButton("xσ²n");
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textField.setText("xσ²n = " + String.valueOf(stat.getPopulationVariance("x")));
				textField.grabFocus();
			}
		});
		btnNewButton_6.setBounds(321, 224, 89, 23);
		frmCalculadora.getContentPane().add(btnNewButton_6);
		
		
		/* Mostra a Variância Amostral em relação a variável x */
		btnXn = new JButton("xσ²n-1");
		btnXn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textField.setText("xσ²n-1 = " + String.valueOf(stat.getVariance("x")));
				textField.grabFocus();
			}
		});
		btnXn.setBounds(421, 224, 89, 23);
		frmCalculadora.getContentPane().add(btnXn);
		
		/* Mostra a Variância Populacional em relação a variável y */
		btnYn_1 = new JButton("yσ²n");
		btnYn_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textField.setText("yσ²n = " + String.valueOf(stat.getPopulationVariance("y")));
				textField.grabFocus();
			}
		});
		btnYn_1.setBounds(321, 156, 89, 23);
		frmCalculadora.getContentPane().add(btnYn_1);
		
		/* Mostra a Variância Amostral em relação a variável y */
		btnYn_2 = new JButton("yσ2n-1");
		btnYn_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textField.setText("yσ²n-1 = " + String.valueOf(stat.getVariance("y")));
				textField.grabFocus();
		
			}
		});
		btnYn_2.setBounds(421, 156, 89, 23);
		frmCalculadora.getContentPane().add(btnYn_2);
		
		/* Exibe o coeficiente de curtose em relação a váriavel x */
		JButton btnK = new JButton("K-x");
		btnK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textField.setText("K = " + String.valueOf(stat.getKurtosis("x")));
				textField.grabFocus();
			}
		});
		btnK.setBounds(24, 292, 89, 23);
		frmCalculadora.getContentPane().add(btnK);
		
		/* Exibe o coeficiente de curtose em relação a váriavel y */
		btnKy = new JButton("K-y");
		btnKy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textField.setText("K = " + String.valueOf(stat.getKurtosis("y")));
				textField.grabFocus();
			}
		});
		btnKy.setBounds(23, 258, 89, 23);
		frmCalculadora.getContentPane().add(btnKy);
		
		/* Lida com o evento de seleção das opções disponíveis da calculadora */
		comboBox = new JComboBox();
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				String x = String.valueOf(comboBox.getSelectedItem());
				
				if (x.equalsIgnoreCase("stat")) {
					statisticsMode = true;
					linearRegression = false;
					quadraticRegression = false;
				}
				else if (x.equalsIgnoreCase("Linear Reg")) {
					statisticsMode = false;
					linearRegression = true;
					quadraticRegression = false;
					stat.setRegression(memoria.getX(), memoria.getY());
				}
				else if (x.equalsIgnoreCase("Quadratic Reg")) {
					statisticsMode = false;
					linearRegression = false;
					quadraticRegression = true;
					stat.setQuadraticRegression(memoria.getX(), memoria.getY());
				}
				textField.grabFocus();
			}
		});
		
		/* Exibe o coeficiente angular no modo regressão linear
		 * ou 
		 * Exibe o coeficiente A no modo regressão quadrática */
		btnA = new JButton("A");
		btnA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (linearRegression) {
					textField.setText("A = " + String.valueOf(stat.getSlop()));
					textField.grabFocus();
				}
				else if (quadraticRegression) {
					textField.setText("A = " + String.valueOf(stat.getAQuadraticReg()));
					textField.grabFocus();
				}
				else {
					textField.setText("A = NaN");
					textField.grabFocus();
				}
			}
		});
		btnA.setBounds(23, 156, 89, 23);
		frmCalculadora.getContentPane().add(btnA);
		
		/* Exibe o coeficiente linear no modo regressão linear
		 * ou 
		 * Exibe o coeficiente B no modo regressão quadrática */
		btnB = new JButton("B");
		btnB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (linearRegression) {
					textField.setText("B = " + String.valueOf(stat.getIntercept()));
					textField.grabFocus();
				}
				else if (quadraticRegression) {
					textField.setText("B = " + String.valueOf(stat.getBQuadraticReg()));
					textField.grabFocus();
				}
				else {
					textField.setText("B = NaN");
					textField.grabFocus();
				}
			}
		});
		btnB.setBounds(123, 156, 89, 23);
		frmCalculadora.getContentPane().add(btnB);
		
		/* Exibe o coeficiente C, que intercepta o eixo das ordenadas*/
		btnC = new JButton("C");
		btnC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (quadraticRegression) {
					textField.setText("C = " + String.valueOf(stat.getCQuadraticReg()));
					textField.grabFocus();
				}
				else {
					textField.setText("C = NaN");
					textField.grabFocus();
				}
			}
		});
		btnC.setBounds(222, 156, 89, 23);
		frmCalculadora.getContentPane().add(btnC);
		
		/* Exibe o coeficiente de determinação - apenas no modo regressão linear por enquanto */
		btnR = new JButton("R²");
		btnR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (linearRegression) {
					textField.setText("R² = " + String.valueOf(stat.getRSquared()));
					textField.grabFocus();
				}
				else {
					textField.setText("R² = " + String.valueOf("NaN"));
					textField.grabFocus();
				}
			}
		});
		btnR.setBounds(24, 190, 89, 23);
		frmCalculadora.getContentPane().add(btnR);
		
		/* Exibe o coeficiente de correlação - no modo regressão Linear */
		btnR_1 = new JButton("r");
		btnR_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (linearRegression) {
					textField.setText("r = " + String.valueOf(stat.getR()));
					textField.grabFocus();
				}
				else {
					textField.setText("r = " + String.valueOf("NaN"));
					textField.grabFocus();
				}
			}
		});
		btnR_1.setBounds(23, 224, 89, 23);
		frmCalculadora.getContentPane().add(btnR_1);
		
		/* Limpa a memória */
		JButton btnCls = new JButton("M-CLS");
		btnCls.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				memoria.clear();
				stat.clear();
				textField.grabFocus();
			}
		});
		btnCls.setBounds(421, 330, 89, 23);
		frmCalculadora.getContentPane().add(btnCls);
		
		/* Abre arquivo XML e carrega na memória */
		btnAbrir = new JButton("Abrir");
		btnAbrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 JFileChooser openFile = new JFileChooser();
	             openFile.showOpenDialog(null);
	                        
	             final String fileName = String.valueOf(openFile.getSelectedFile());
	             if (fileName != null || openFile.getApproveButtonText().equalsIgnoreCase("cancelar")) {
	            	 if (fileName.contains(".xml")) {
	            		 textField.setText("Arquivo = " + openFile.getName(openFile.getSelectedFile()) + " carregado com sucesso");
	            		 if (xSaveOpenState) {
	            			 ManipXMLX.setNomeArquivo(fileName);
	            			 memoria.setX(ManipXMLX.lerXMLCOM());
	            			 stat.setX(memoria.getX());
	            		 }
	            		 else if (ySaveOpenState) {
	            			 ManipXMLY.setNomeArquivo(fileName);
	            			 memoria.setY(ManipXMLY.lerXMLCOM());
	            			 stat.setY(memoria.getY());	
	            		 }
	            	 }
	             }
	             textField.grabFocus();
			}
		});
		
		btnAbrir.setBounds(23, 330, 89, 23);
		frmCalculadora.getContentPane().add(btnAbrir);
		
		/* Salva dataset */
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser saveFile = new JFileChooser();
                saveFile.showSaveDialog(null);        
                final String fileName = String.valueOf(saveFile.getSelectedFile());
             
                if (fileName != null || saveFile.getApproveButtonText().equalsIgnoreCase("cancelar")) {
                	if (xSaveOpenState && !memoria.getX().isEmpty()) {
                		ManipXMLX.setNomeArquivo(fileName);
                		ManipXMLX.gravarXML(memoria.getX());
                	}
                	if (ySaveOpenState && !memoria.getY().isEmpty()) {
                		ManipXMLY.setNomeArquivo(fileName);
                		ManipXMLY.gravarXML(memoria.getY());
                	}
                }
                textField.grabFocus();
			}
		});
		btnSalvar.setBounds(122, 330, 89, 23);
		frmCalculadora.getContentPane().add(btnSalvar);
		

		btnM_1.setBounds(321, 292, 89, 23);
		frmCalculadora.getContentPane().add(btnM_1);
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Stat", "Linear Reg", "Quadratic Reg"}));
		comboBox.setBounds(321, 330, 90, 22);
		frmCalculadora.getContentPane().add(comboBox);
		
		/* Muda o estado das variáveis que indicam
		 * qual variável deve salvar ou carregar 
		 * um conjunto de dados */
		final JComboBox comboBox_1 = new JComboBox();
		comboBox_1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				String x = String.valueOf(comboBox_1.getSelectedItem());
				if (x.equalsIgnoreCase("x")) {
					xSaveOpenState = true;
					ySaveOpenState = false;
				}
				else if (x.equalsIgnoreCase("y")) {
					xSaveOpenState = false;
					ySaveOpenState = true;
				}
				textField.grabFocus();
			}
		});
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"x", "y"}));
		comboBox_1.setBounds(222, 330, 90, 22);
		frmCalculadora.getContentPane().add(comboBox_1);
		
		
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\Felipe\\Dropbox\\workspace\\ProjetoFinal\\Icones\\background_5.jpg"));
		lblNewLabel.setAutoscrolls(true);
		lblNewLabel.setBounds(0, -31, 1600, 1000);
		frmCalculadora.getContentPane().add(lblNewLabel);
	
	}
	
	
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
