import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class Window implements ActionListener {
    public Window() {
        initComponents();
    }

    private JFrame viewForm;
    private int gridy = 0;

    private void initComponents() {
        JFrame.setDefaultLookAndFeelDecorated(false);
        viewForm = new JFrame("Калькулятор");
        viewForm.setSize(320, 320);
        viewForm.setLocationRelativeTo(null);
        viewForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //=== Объект для рассчетов
        Calculation calculation = Calculation.singleCalculation();

        //=== Создание панели с текстовыми полями
        JPanel contents = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        contents.setLayout(layout);
        GridBagConstraints constraints = new GridBagConstraints();

        //=== По умолчанию натуральная высота, максимальная ширина
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.5;

        //=== Текстовое поле
        JTextField smallField = new JTextField(15);
        smallField.setToolTipText("Короткое поле");
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = gridy;
        constraints.gridwidth = 3;
        contents.add(smallField, constraints);

        //=== Кнопки - цифры
        gridy++;

        int btn_gridx = 0;
        for(int i=0; i <= 9; i++) {
            final int j = i;
            JButton button = new JButton(i+"");
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    calculation.setOperand(String.valueOf(j));

                    smallField.setText(calculation.getResultStr());
                }
            });

            constraints.fill = GridBagConstraints.HORIZONTAL;

            constraints.gridx = btn_gridx;
            constraints.gridy = gridy;
            constraints.gridwidth = 1;
            contents.add(button, constraints);

            if (btn_gridx != 0 && btn_gridx % 2 == 0) {
                btn_gridx = 0;
                gridy++;
            } else {
                btn_gridx++;
            }
        }

        //=== Запятая
        gridy++;

        JButton button_separator = new JButton(".");
        button_separator.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calculation.setOperand(".");

                smallField.setText(calculation.getResultStr());
            }
        });
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridwidth = 1;
        constraints.gridy = gridy;
        contents.add(button_separator, constraints);

        //=== Кнопки - операции
        gridy++;

        JButton button_plus = new JButton("+");
        button_plus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                smallField.setText("+");

                calculation.setOperation('+');
            }
        });
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridwidth = 1;
        constraints.gridy = gridy;
        contents.add(button_plus, constraints);

        JButton button_minus = new JButton("-");
        button_minus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                smallField.setText("-");

                calculation.setOperation('-');
            }
        });
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 1;
        constraints.gridwidth = 1;
        constraints.gridy = gridy;
        contents.add(button_minus, constraints);

        //=== Результат
        gridy++;

        JButton button_result = new JButton("=");
        button_result.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calculation.calculation();
                smallField.setText(Calculation.getFloatStr(calculation.getResult()));
            }
        });
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = gridy;
        contents.add(button_result, constraints);

        //=== Очистить
        JButton button_clear = new JButton("c");
        button_clear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Calculation.singleCalculation();
                smallField.setText("");
            }
        });
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 1;
        constraints.gridy = gridy;
        contents.add(button_clear, constraints);

        viewForm.getContentPane().add(contents);
        viewForm.pack();
        viewForm.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}
