import org.swixml.SwingEngine;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import java.util.Arrays;

/**
 * Created by Valdis003
 * on 02.09.2017
 * for swing
 */

public class MyCalculator extends SwingEngine {
    private double prevValue;
    private double currValue;
    private String operation;
    private String resultString;

    private JButton btnTotal;
    private JFormattedTextField textField;

    public Action operationClick;
    public Action numberClick;
    public Action clear;
    public Action exit;

    private MyCalculator() throws Exception {

        DecimalFormat format = new DecimalFormat("#.#");

        operationClick = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!textField.getText().isEmpty()) {
                        JButton b = (JButton) e.getSource();
                        operation = b.getText();
                        prevValue = Double.parseDouble(textField.getText());
                        textField.setText("");
                    }
                } catch (NumberFormatException ex) {
                    System.out.println(Arrays.toString(ex.getStackTrace()));
                }
            }
        };

        exit = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        };

        clear = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField.setText("");
                operation = "";
            }
        };

        numberClick = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton a = (JButton) e.getSource();
                String temp = textField.getText() + a.getText();
                textField.setText(String.format(temp, format));
            }
        };

        render("Calculator.xml").setVisible(false);

        btnTotal.addActionListener(e -> {
            try {
                currValue = Integer.parseInt(textField.getText());
                switch (operation) {
                    case "+":
                        resultString = String.valueOf(prevValue + currValue);
                        break;
                    case "-":
                        resultString = format.format(prevValue - currValue);
                        break;
                    case "*":
                        resultString = format.format(prevValue * currValue);
                        break;
                    case "/":
                        resultString = currValue != 0 ? "Деление на ноль!" : format.format(prevValue / currValue);
                        break;
                    default:
                        break;
                }

                textField.setText(resultString);
            } catch (NumberFormatException ex) {
                System.out.println(Arrays.toString(ex.getStackTrace()));
            }
        });
    }

    public static void main(String[] args) throws Exception {
        MyCalculator myCalculator = new MyCalculator();
        myCalculator.getRootComponent().setVisible(true);
    }
}
