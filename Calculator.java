import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JFrame {
    private JTextField outputField;
    private ArrayList<JButton> digits;
    private JMenuItem plusSign;
    private JMenuItem minusSign;
    private JMenuItem multSign;
    private JMenuItem divSign;
    private JMenuItem clearSign;
    private JButton eqSign;
    private String currentOperation;
    private Double firstOperand;
    private JMenuBar menuBar;
    private JMenu menu1;
    public Calculator() {
        this.currentOperation = "";
        this.firstOperand = 0.0;
        JPanel displayPanel = new JPanel();
        menuBar = new JMenuBar();
        menu1 = new JMenu("Operations Menu");     
        outputField = new JTextField("0", 30);
        displayPanel.add(outputField, BorderLayout.NORTH);
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        JPanel digitButtonPanel = new JPanel(new GridLayout(4, 3));
        digits = new ArrayList<JButton>();
        eqSign = new JButton("=");
        digits.add(new JButton("0"));
        digits.add(new JButton("1"));
        digits.add(new JButton("2"));
        digits.add(new JButton("3"));
        digits.add(new JButton("4"));
        digits.add(new JButton("5"));
        digits.add(new JButton("6"));
        digits.add(new JButton("7"));
        digits.add(new JButton("8"));
        digits.add(new JButton("9"));
        digits.add(new JButton("."));
        digits.add(eqSign);
        digits.get(10).setForeground(Color.MAGENTA);
        digits.get(11).setForeground(Color.MAGENTA);

        digitButtonPanel.add(digits.get(0));
        digitButtonPanel.add(digits.get(1));
        digitButtonPanel.add(digits.get(2));
        digitButtonPanel.add(digits.get(3));
        digitButtonPanel.add(digits.get(4));
        digitButtonPanel.add(digits.get(5));
        digitButtonPanel.add(digits.get(6));
        digitButtonPanel.add(digits.get(7));
        digitButtonPanel.add(digits.get(8));
        digitButtonPanel.add(digits.get(9));
        digitButtonPanel.add(digits.get(10));
        digitButtonPanel.add(digits.get(11));
        buttonPanel.add(digitButtonPanel);
        
        clearSign = new JMenuItem("AC");
        plusSign = new JMenuItem("+");
        minusSign = new JMenuItem("-");
        multSign = new JMenuItem("*");
        divSign = new JMenuItem("/");
       
        plusSign.setForeground(Color.BLUE);
        minusSign.setForeground(Color.BLUE);
        multSign.setForeground(Color.BLUE);
        divSign.setForeground(Color.BLUE);
        clearSign.setForeground(Color.PINK);
        eqSign.setForeground(Color.GREEN);
        
        menu1.add(clearSign);
        menu1.add(plusSign);
        menu1.add(minusSign);
        menu1.add(multSign);
        menu1.add(divSign);
        menuBar.add(menu1);

       buttonPanel.add(menuBar, BorderLayout.WEST);
       displayPanel.add(buttonPanel);
       add(displayPanel);         
        
        clearSign.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                //currentOperation = "";
                //firstOperand = 0.0;
                //outputField.setText("0");
                resetValues();
            }
        });

        digits.get(10).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                String currentText = outputField.getText();
                if(currentText.indexOf(".") < 0){
                    outputField.setText(currentText+".");
                }
            }
        });

        OperatorListener opListener = new OperatorListener();
        plusSign.addActionListener(opListener);
        minusSign.addActionListener(opListener);
        multSign.addActionListener(opListener);
        divSign.addActionListener(opListener);

        for(int i = 0; i <= 9; i ++){
            digits.get(i).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    String currentText = outputField.getText();
                    JButton source = (JButton)event.getSource();
                    String newDigit = "";
                    if (source == digits.get(0)) {
                        newDigit = "0";
                    } else if (source == digits.get(1)) {
                        newDigit = "1";
                    } else if (source == digits.get(2)) {
                        newDigit = "2";
                    } else if (source == digits.get(3)) {
                        newDigit = "3";
                    } else if (source == digits.get(4)) {
                        newDigit = "4";
                    } else if (source == digits.get(5)) {
                        newDigit = "5";
                    } else if (source == digits.get(6)) {
                        newDigit = "6";
                    } else if (source == digits.get(7)) {
                        newDigit = "7";
                    } else if (source == digits.get(8)) {
                        newDigit = "8";
                    } else if (source == digits.get(9)) {
                        newDigit = "9";
                    }

                    currentText = currentText + newDigit;
                    currentText = currentText.replaceFirst("^0+(?!$)", "");
                    outputField.setText(currentText);
                }
            });
        }

        eqSign.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Double result = 0.0;
                String currentText = outputField.getText();
                try{
                    Double secondOperand = new Double(currentText);

                    if(currentOperation == "+"){
                        result = firstOperand + secondOperand;
                    } else if(currentOperation == "-"){
                        result = firstOperand - secondOperand;
                    } else if(currentOperation == "*"){
                        result = firstOperand * secondOperand;
                    } else if(currentOperation == "/"){
                        if(secondOperand != 0.0){
                            result = firstOperand / secondOperand;
                        } else {
                            resetValues();
                            outputField.setBackground(Color.PINK);
                        }
                    }

                    outputField.setText(result.toString());
                    firstOperand = result;
                } catch(NumberFormatException e){
                    resetValues();
                }
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                System.exit(0);
            }
        });

        setTitle("Calculator");
        setSize(500, 200);
        setVisible(true);
    }

    private void resetValues(){
        currentOperation = "";
        firstOperand = 0.0;
        outputField.setText("0");
        outputField.setBackground(Color.WHITE);
    }

    private class OperatorListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            JMenuItem source = (JMenuItem)event.getSource();
            if (source == plusSign) {
                currentOperation = "+";
            }else if (source == minusSign) {
                currentOperation = "-";
            }else if (source == multSign) {
                currentOperation = "*";
            }else if (source == divSign) {
                currentOperation = "/";
            }

            String currentText = outputField.getText();
            try{
                Double currentTextDouble = new Double(currentText);
                firstOperand = currentTextDouble;
                outputField.setText("0");
            } catch(NumberFormatException e){
                resetValues();              
            }
        }
    }

    public static void main(String[]args) {
        new Calculator();
    }
}
