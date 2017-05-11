package gui;

import javax.swing.*;
import java.awt.*;

/**
 * Eingabemaske beim Erstellen eines neuen Themas
 */
public class TopicDialog extends JDialog {
    public TopicDialog(MainFrame mainFrame) {
        super(mainFrame);
        setTitle("Thema hinzufügen");
        setSize(700, 500);
        setResizable(false);
        setLocationRelativeTo(mainFrame);
        initFormPanel();
        initButtonPanel();
        setVisible(true);
    }

    /**
     * Hilfsmethode für die Initialisierung der Schaltflächen
     */
    private void initButtonPanel() {
        JPanel buttonPanel = new JPanel();
        JButton cancelButton = new JButton("Abbrechen");
        cancelButton.addActionListener(e -> dispose());
        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> dispose());
        buttonPanel.add(cancelButton);
        buttonPanel.add(okButton);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Hilfsmethode für die Initialisierung der Maske selbst
     */
    private void initFormPanel() {
        JPanel formPanel = new JPanel();

        JTextField titleField = new JTextField();
        titleField.setPreferredSize(new Dimension(200, 20));
        JLabel titleLabel = new JLabel("Titel des Themas");
        titleLabel.setLabelFor(titleField);
        formPanel.add(titleLabel);
        formPanel.add(titleField);

        getContentPane().add(formPanel, BorderLayout.CENTER);
    }
}
