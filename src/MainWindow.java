import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class MainWindow {

    private static final Insets insets = new Insets(0, 0, 0, 0);
    private static final String[] author = {"-", "emelichev_melnikov_lekcii_po_tg", "R_Distel_Graph_Theory", "F_Harary_Graph_Theory"};
    private static final String[] node = {"sc_node_not_relation", "sc_node_norole_relation", "sc_node_role_relation"};
    private JFrame frame;
    private JPanel relationPanel;
    private JComboBox sc_node;
    private JTextField nrel_sys_idtf = new JTextField(30);
    private JTextField nrel_main_idtf_RUS = new JTextField(30);
    private JTextField nrel_main_idtf_ENG = new JTextField(30);
    private JTextPane sc_definition_RUS = new JTextPane();
    private JTextPane sc_definition_ENG = new JTextPane();
    private JTextField nrel_using_constants_definition = new JTextField(30);
    private JTextField nrel_using_constants_statement = new JTextField(30);
    private JTextPane sc_statement_RUS = new JTextPane();
    private JTextPane sc_statement_ENG = new JTextPane();
    private JTextField nrel_statement_name_RUS = new JTextField(30);
    private JTextField nrel_statement_name_ENG = new JTextField(30);
    private String authorDefinitionStr = "-";
    private String authorStatementStr = "-";
    private JTextField[] nrel_domain;
    private JCheckBox reflexive_relation = new JCheckBox("reflexive_relation");
    private JCheckBox antireflexive_relation = new JCheckBox("antireflexive_relation");
    private JCheckBox symmetric_relation = new JCheckBox("symmetric_relation");
    private JCheckBox antisymmetric_relation = new JCheckBox("antisymmetric_relation");
    private JCheckBox transitive_relation = new JCheckBox("transitive_relation");
    private JCheckBox antitransitive_relation = new JCheckBox("antitransitive_relation");
    private int arity = 2;

    public MainWindow() {
        frame = new JFrame("Knowledge Base creator GT");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(createPanel(), BorderLayout.NORTH);
        relationPanel = createRelationPanel();
        relationPanel.setVisible(false);
        frame.add(relationPanel, BorderLayout.SOUTH);
        frame.setSize(1000, 550);
        frame.setVisible(true);
    }

    private JPanel createPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        JLabel label = new JLabel(" Knowledge Base creator GT: ");
        label.setHorizontalAlignment(JLabel.CENTER);
        addComponent(panel, label, 0, 0, 4, 1);
        addComponent(panel, new JLabel(" nrel_system_idtf: "), 0, 1, 1, 1);
        addComponent(panel, nrel_sys_idtf, 1, 1, 1, 1);
        addComponent(panel, new JLabel(" type node: "), 2, 1, 1, 1);
        sc_node = new JComboBox(node);
        sc_node.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changeTypeNode(e);
            }
        });
        addComponent(panel, sc_node, 3, 1, 1, 1);
        addComponent(panel, new JLabel(" "), 0, 2, 4, 1);
        addComponent(panel, new JLabel(" nrel_main_idtf RUS: "), 0, 3, 1, 1);
        addComponent(panel, nrel_main_idtf_RUS, 1, 3, 1, 1);
        addComponent(panel, new JLabel(" nrel_main_idtf ENG: "), 2, 3, 1, 1);
        addComponent(panel, nrel_main_idtf_ENG, 3, 3, 1, 1);
        addComponent(panel, new JLabel(" "), 0, 4, 4, 1);
        addComponent(panel, new JLabel(" sc_definition RUS: "), 0, 5, 1, 1);
        sc_definition_RUS.setPreferredSize(new Dimension(0, 100));
        JScrollPane scrollPane = new JScrollPane(sc_definition_RUS);
        addComponent(panel, scrollPane, 1, 5, 1, 1);
        addComponent(panel, new JLabel(" sc_definition ENG: "), 2, 5, 1, 1);
        sc_definition_ENG.setPreferredSize(new Dimension(0, 100));
        scrollPane = new JScrollPane(sc_definition_ENG);
        addComponent(panel, scrollPane, 3, 5, 1, 1);
        addComponent(panel, new JLabel(" using_constants "), 0, 6, 1, 1);
        addComponent(panel, nrel_using_constants_definition, 1, 6, 1, 1);
        addComponent(panel, new JLabel(" Author definition"), 2, 6, 1, 1);
        JComboBox authorDefinition = new JComboBox(author);
        authorDefinition.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changeAuthorDefinition(e);
            }
        });
        addComponent(panel, authorDefinition, 3, 6, 1, 1);
        addComponent(panel, new JLabel(" "), 0, 7, 1, 1);
        addComponent(panel, new JLabel(" sc_statement RUS: "), 0, 8, 1, 1);
        sc_statement_RUS.setPreferredSize(new Dimension(0, 100));
        scrollPane = new JScrollPane(sc_statement_RUS);
        addComponent(panel, scrollPane, 1, 8, 1, 1);
        addComponent(panel, new JLabel(" sc_statement ENG: "), 2, 8, 1, 1);
        sc_statement_ENG.setPreferredSize(new Dimension(0, 100));
        scrollPane = new JScrollPane(sc_statement_ENG);
        addComponent(panel, scrollPane, 3, 8, 1, 1);
        addComponent(panel, new JLabel(" statement RUS"), 0, 9, 1, 1);
        addComponent(panel, nrel_statement_name_RUS, 1, 9, 1, 1);
        addComponent(panel, new JLabel(" statement ENG"), 2, 9, 1, 1);
        addComponent(panel, nrel_statement_name_ENG, 3, 9, 1, 1);
        addComponent(panel, new JLabel(" using_constants "), 0, 10, 1, 1);
        addComponent(panel, nrel_using_constants_statement, 1, 10, 1, 1);
        addComponent(panel, new JLabel(" Author statement"), 2, 10, 1, 1);
        JComboBox authorStatement = new JComboBox(author);
        authorStatement.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changeAuthorStatement(e);
            }
        });
        addComponent(panel, authorStatement, 3, 10, 1, 1);
        JButton generateButton = new JButton("GENERATE");
        generateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                generateSCS(e);
            }
        });
        addComponent(panel, generateButton, 0, 11, 4, 1);
        return panel;
    }

    private JPanel createRelationPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        addComponent(panel, reflexive_relation, 0, 0, 1, 1);
        addComponent(panel, antireflexive_relation, 0, 1, 1, 1);
        addComponent(panel, symmetric_relation, 1, 0, 1, 1);
        addComponent(panel, antisymmetric_relation, 1, 1, 1, 1);
        addComponent(panel, transitive_relation, 2, 0, 1, 1);
        addComponent(panel, antitransitive_relation, 2, 1, 1, 1);
        String[] arityStr = {"2", "3"};
        addComponent(panel, new JLabel(" Arity: "), 0, 0, 1, 1);
        JComboBox nrel_arity_of_relations = new JComboBox(arityStr);
        nrel_arity_of_relations.setSelectedIndex(Arrays.asList(arityStr).indexOf(Integer.toString(arity)));
        nrel_arity_of_relations.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changeArity(e);
            }
        });
        addComponent(panel, nrel_arity_of_relations, 1, 2, 1, 1);
        nrel_domain = new JTextField[arity];
        for (int i = 0; i < arity; i++) {
            JTextField jtfName = new JTextField(30);
            nrel_domain[i] = jtfName;
            addComponent(panel, new JLabel(" Domen: " + (i + 1)), 0, i + 3, 1, 1);
            addComponent(panel, nrel_domain[i], 1, i + 3, 1, 1);
        }
        return panel;
    }

    private void generateSCS(ActionEvent e) {
        JFileChooser fc = new JFileChooser();
        fc.setSelectedFile(new File(nrel_sys_idtf.getText() + ".scs"));
        if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            try ( FileWriter fw = new FileWriter(fc.getSelectedFile()) ) {
                boolean relation = false;
                String sysIdtf = nrel_sys_idtf.getText();
                String attrIdtf = "";
                if (!sc_node.getSelectedItem().equals("sc_node_not_relation")){
                    relation = true;
                    if (sc_node.getSelectedItem().equals("sc_node_norole_relation")){
                        attrIdtf += "*";
                    } else {
                        attrIdtf += "'";
                    }
                }
                String fileText =
                        sc_node.getSelectedItem() + " -> " + sysIdtf + ";;\n" +
                        "\n" +
                        sysIdtf + " => nrel_main_idtf:\n" +
                        "\t[" + nrel_main_idtf_RUS.getText() + attrIdtf + "](* <-lang_ru;; *);\n" +
                        "\t[" + nrel_main_idtf_ENG.getText() + attrIdtf + "](* <-lang_en;; *);;\n" +
                        "\n" +
                        sysIdtf + " <- rrel_key_sc_element: \n" +
                        "\t\t... \n" +
                        "\t\t(*\n" +
                        "\t\t<- sc_definition;;\n" +
                        "\t\t=> nrel_main_idtf: [Определение: " + nrel_main_idtf_RUS.getText() + "] (* <- lang_ru;; *);;\n" +
                        "\t\t=> nrel_main_idtf: [Definition: " + nrel_main_idtf_ENG.getText() + "] (* <- lang_en;; *);;\n" +
                        "\t\t<= nrel_sc_text_translation: ...\n" +
                        "\t\t\t(*\n" +
                        "\t\t\t-> rrel_example: \n" +
                        "\t\t\t\t[" + sc_definition_RUS.getText() + "](*<- lang_ru;;*);;\n" +
                        "\t\t\t-> rrel_example: \n" +
                        "\t\t\t\t[" + sc_definition_ENG.getText() + "](*<- lang_en;;*);;\n" +
                        "\t\t\t*);;\n";
                if (!authorDefinitionStr.equals("-")){
                    fileText += "\t\t<= nrel_bibliographical_source: " + authorDefinitionStr + ";;\n";
                }
                fileText +=
                        "\t\t*);;\n" +
                        "\n";
                fileText +=
                        sysIdtf + " <= nrel_using_constants: " + nrel_using_constants_definition.getText() + ";;\n";
                fileText +=
                        "\n" +
                        sysIdtf + " <- rrel_main_key_sc_element:\n" +
                        "\t\t...\n" +
                        "\t\t(*\n" +
                        "\t\t=> nrel_main_idtf: [Утверждение: " + nrel_statement_name_RUS.getText() + "] (* <- lang_ru;; *);;\n" +
                        "\t\t=> nrel_main_idtf: [Statement: " + nrel_statement_name_ENG.getText() + "] (* <- lang_en;; *);;\n" +
                        "\t\t-> rrel_key_sc_element: " + nrel_using_constants_statement.getText() + ";;\n" +
                        "\t\t<- sc_statement;;\n" +
                        "\t\t<= nrel_sc_text_translation: ...\n" +
                        "\t\t\t(*\n" +
                        "\t\t\t-> rrel_example: \n" +
                        "\t\t\t\t[" + sc_statement_RUS.getText() + "](*<- lang_ru;;*);;\n" +
                        "\t\t\t-> rrel_example: \n" +
                        "\t\t\t\t[" + sc_statement_ENG.getText() + "](*<- lang_en;;*);;\n" +
                        "\t\t\t*);;\n";
                if (!authorStatementStr.equals("-")){
                    fileText += "\t\t<= nrel_bibliographical_source: " + authorDefinitionStr + ";;\n";
                }
                fileText +=
                        "\t\t*);;";
                if (relation){
                    fileText += "\n\n";
                    if (arity == 2) fileText += sysIdtf + " <- binary_relation;;\n";
                    else fileText += sysIdtf + " <- ternary_relation;;\n";
                    if (reflexive_relation.isSelected()) fileText += sysIdtf + " <- reflexive_relation;;\n";
                    if (antireflexive_relation.isSelected()) fileText += sysIdtf + " <- antireflexive_relation;;\n";
                    if (symmetric_relation.isSelected()) fileText += sysIdtf + " <- symmetric_relation;;\n";
                    if (antisymmetric_relation.isSelected()) fileText += sysIdtf + " <- antisymmetric_relation;;\n";
                    if (transitive_relation.isSelected()) fileText += sysIdtf + " <- transitive_relation;;\n";
                    if (antitransitive_relation.isSelected()) fileText += sysIdtf + " <- antitransitive_relation;;\n";
                    fileText +=
                            sysIdtf + " => nrel_arity_of_relations: " + arity + "(* <- number ;;*);;\n" +
                            sysIdtf + " => nrel_first_domain: " + nrel_domain[0].getText() + ";;\n" +
                            sysIdtf + " => nrel_second_domain: " + nrel_domain[1].getText() + ";;\n";
                    if (arity == 3)
                        fileText +=
                                sysIdtf + " => nrel_third_domain: " + nrel_domain[2].getText() + ";;\n";
                    fileText +=
                            sysIdtf + " <= nrel_definitional_domain: \n" +
                            "\t...\n" +
                            "\t(*\n" +
                            "\t<= nrel_combination: \n" +
                            "\t\t{\n" +
                            "\t\t" + nrel_domain[0].getText() + "; \n";
                    if (arity == 3) {
                        fileText +=
                                "\t\t" + nrel_domain[1].getText() +";\n" +
                                "\t\t" + nrel_domain[2].getText() + "\n";
                    } else {
                        fileText +=
                                "\t\t" + nrel_domain[1].getText() +"\n";
                    }
                    fileText +=
                            "\t\t};;\n" +
                            "\t*);;";
                }
                fw.write(fileText);
            }
            catch ( IOException ex ) {
                JOptionPane.showMessageDialog
                        (null,"Невозможно сохранить файл", "Ошибка", JOptionPane.ERROR_MESSAGE|JOptionPane.OK_OPTION);
            }
        }
    }

    private void changeAuthorDefinition(ActionEvent e) {
        JComboBox cb = (JComboBox) e.getSource();
        authorDefinitionStr = (String) cb.getSelectedItem();
    }

    private void changeAuthorStatement(ActionEvent e) {
        JComboBox cb = (JComboBox) e.getSource();
        authorStatementStr = (String) cb.getSelectedItem();
    }

    private void changeTypeNode(ActionEvent e) {
        JComboBox cb = (JComboBox) e.getSource();
        String typeNode = (String) cb.getSelectedItem();
        if (typeNode.equals("sc_node_not_relation")) relationPanel.setVisible(false);
        else relationPanel.setVisible(true);
    }

    private void changeArity(ActionEvent e) {
        JComboBox cb = (JComboBox) e.getSource();
        String arityStr = (String) cb.getSelectedItem();
        arity = Integer.parseInt(arityStr);
        frame.remove(relationPanel);
        relationPanel = createRelationPanel();
        frame.add(relationPanel, BorderLayout.SOUTH);
        frame.revalidate();
        frame.repaint();
    }

    private static void addComponent(Container container, Component component,
                                     int gridx, int gridy, int gridwidth, int gridheight) {
        GridBagConstraints gbc = new GridBagConstraints(gridx, gridy, gridwidth, gridheight, 1.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, insets, 0, 0);
        container.add(component, gbc);
    }

    public static void main(String[] args) {
        new MainWindow();
    }
}
