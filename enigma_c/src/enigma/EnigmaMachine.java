package enigma;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

// Classe principal com interface gráfica
public class EnigmaMachine extends JFrame {
    private Rotor[] rotores;
    private Reflector refletor;
    private Plugboard plugboard;
    
    // Componentes da UI
    private JTextArea inputArea;
    private JTextArea outputArea;
    private JComboBox<String> rotor1Combo, rotor2Combo, rotor3Combo;
    private JComboBox<String> reflectorCombo;
    private JTextField pos1Field, pos2Field, pos3Field;
    private JTextField ring1Field, ring2Field, ring3Field;
    private JTextArea plugboardArea;
    private JLabel statusLabel;
    
    public EnigmaMachine() {
        setTitle("Máquina Enigma - Wehrmacht I");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        
        inicializarComponentes();
        configurarPadrao();
    }
    
    private void inicializarComponentes() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        // Painel de configuração
        JPanel configPanel = criarPainelConfiguracao();
        
        // Painel de texto
        JPanel textPanel = criarPainelTexto();
        
        // Painel de botões
        JPanel buttonPanel = criarPainelBotoes();
        
        // Status bar
        statusLabel = new JLabel("Pronto");
        statusLabel.setBorder(new EmptyBorder(5, 5, 5, 5));
        
        mainPanel.add(configPanel, BorderLayout.NORTH);
        mainPanel.add(textPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);
    }
    
    private JPanel criarPainelConfiguracao() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Configuração da Máquina"));
        
        // Rotores
        JPanel rotorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rotorPanel.add(new JLabel("Rotores:"));
        
        String[] rotorOptions = {"I", "II", "III", "IV", "V"};
        rotor1Combo = new JComboBox<>(rotorOptions);
        rotor2Combo = new JComboBox<>(rotorOptions);
        rotor3Combo = new JComboBox<>(rotorOptions);
        
        rotor1Combo.setSelectedItem("III");
        rotor2Combo.setSelectedItem("II");
        rotor3Combo.setSelectedItem("I");
        
        rotorPanel.add(new JLabel("Esq:"));
        rotorPanel.add(rotor1Combo);
        rotorPanel.add(new JLabel("Meio:"));
        rotorPanel.add(rotor2Combo);
        rotorPanel.add(new JLabel("Dir:"));
        rotorPanel.add(rotor3Combo);
        
        // Posições iniciais
        rotorPanel.add(Box.createHorizontalStrut(20));
        rotorPanel.add(new JLabel("Posições:"));
        pos1Field = new JTextField("A", 2);
        pos2Field = new JTextField("A", 2);
        pos3Field = new JTextField("A", 2);
        rotorPanel.add(pos1Field);
        rotorPanel.add(pos2Field);
        rotorPanel.add(pos3Field);
        
        // Ring settings
        rotorPanel.add(Box.createHorizontalStrut(20));
        rotorPanel.add(new JLabel("Rings:"));
        ring1Field = new JTextField("A", 2);
        ring2Field = new JTextField("A", 2);
        ring3Field = new JTextField("A", 2);
        rotorPanel.add(ring1Field);
        rotorPanel.add(ring2Field);
        rotorPanel.add(ring3Field);
        
        // Refletor
        JPanel reflectorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        reflectorPanel.add(new JLabel("Refletor:"));
        reflectorCombo = new JComboBox<>(new String[]{"B", "C"});
        reflectorPanel.add(reflectorCombo);
        
        // Plugboard
        JPanel plugboardPanel = new JPanel(new BorderLayout(5, 5));
        plugboardPanel.add(new JLabel("Plugboard (pares, ex: AB CD EF):"), BorderLayout.WEST);
        plugboardArea = new JTextArea(2, 40);
        plugboardArea.setLineWrap(true);
        plugboardArea.setText("AZ BY CX DW EV FU GT HS IR JQ");
        JScrollPane scrollPane = new JScrollPane(plugboardArea);
        plugboardPanel.add(scrollPane, BorderLayout.CENTER);
        
        panel.add(rotorPanel);
        panel.add(reflectorPanel);
        panel.add(plugboardPanel);
        
        return panel;
    }
    
    private JPanel criarPainelTexto() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 0));
        
        // Input
        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Texto de Entrada"));
        inputArea = new JTextArea();
        inputArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        inputArea.setLineWrap(true);
        inputArea.setWrapStyleWord(true);
        JScrollPane inputScroll = new JScrollPane(inputArea);
        inputPanel.add(inputScroll, BorderLayout.CENTER);
        
        // Output
        JPanel outputPanel = new JPanel(new BorderLayout(5, 5));
        outputPanel.setBorder(BorderFactory.createTitledBorder("Texto de Saída"));
        outputArea = new JTextArea();
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setEditable(false);
        outputArea.setBackground(new Color(240, 240, 240));
        JScrollPane outputScroll = new JScrollPane(outputArea);
        outputPanel.add(outputScroll, BorderLayout.CENTER);
        
        panel.add(inputPanel);
        panel.add(outputPanel);
        
        return panel;
    }
    
    private JPanel criarPainelBotoes() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        
        JButton encryptBtn = new JButton("Cifrar");
        encryptBtn.setFont(new Font("Arial", Font.BOLD, 14));
        encryptBtn.setPreferredSize(new Dimension(150, 40));
        encryptBtn.addActionListener(e -> processar());
        
        JButton decryptBtn = new JButton("Decifrar");
        decryptBtn.setFont(new Font("Arial", Font.BOLD, 14));
        decryptBtn.setPreferredSize(new Dimension(150, 40));
        decryptBtn.addActionListener(e -> processar());
        
        JButton resetBtn = new JButton("Resetar");
        resetBtn.setPreferredSize(new Dimension(120, 40));
        resetBtn.addActionListener(e -> resetarPosicoes());
        
        JButton clearBtn = new JButton("Limpar");
        clearBtn.setPreferredSize(new Dimension(120, 40));
        clearBtn.addActionListener(e -> limpar());
        
        JButton swapBtn = new JButton("Trocar Textos");
        swapBtn.setPreferredSize(new Dimension(150, 40));
        swapBtn.addActionListener(e -> trocarTextos());
        
        panel.add(encryptBtn);
        panel.add(decryptBtn);
        panel.add(resetBtn);
        panel.add(clearBtn);
        panel.add(swapBtn);
        
        return panel;
    }
    
    private void configurarPadrao() {
        resetarPosicoes();
    }
    
    private void resetarPosicoes() {
        pos1Field.setText("A");
        pos2Field.setText("A");
        pos3Field.setText("A");
        statusLabel.setText("Posições resetadas para AAA");
    }
    
    private void limpar() {
        inputArea.setText("");
        outputArea.setText("");
        statusLabel.setText("Textos limpos");
    }
    
    private void trocarTextos() {
        String temp = inputArea.getText();
        inputArea.setText(outputArea.getText());
        outputArea.setText(temp);
        statusLabel.setText("Textos trocados");
    }
    
    private void processar() {
        try {
            // Validar entrada
            String input = inputArea.getText();
            if (input.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Por favor, insira um texto para processar.",
                    "Entrada Vazia", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Configurar máquina
            configurarMaquina();
            
            // Processar texto
            String output = criptografar(input);
            outputArea.setText(output);
            
            statusLabel.setText("Processamento concluído - " + output.replaceAll("\\s", "").length() + " caracteres");
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Erro ao processar: " + ex.getMessage(),
                "Erro", JOptionPane.ERROR_MESSAGE);
            statusLabel.setText("Erro: " + ex.getMessage());
        }
    }
    
    private void configurarMaquina() throws Exception {
        // Obter configurações dos rotores
        String r1 = (String) rotor1Combo.getSelectedItem();
        String r2 = (String) rotor2Combo.getSelectedItem();
        String r3 = (String) rotor3Combo.getSelectedItem();
        
        // Validar posições
        char p1 = validarLetra(pos1Field.getText(), "Posição 1");
        char p2 = validarLetra(pos2Field.getText(), "Posição 2");
        char p3 = validarLetra(pos3Field.getText(), "Posição 3");
        
        char ring1 = validarLetra(ring1Field.getText(), "Ring 1");
        char ring2 = validarLetra(ring2Field.getText(), "Ring 2");
        char ring3 = validarLetra(ring3Field.getText(), "Ring 3");
        
        // Criar rotores
        rotores = new Rotor[3];
        rotores[0] = new Rotor(r1, p1, ring1); // Esquerdo
        rotores[1] = new Rotor(r2, p2, ring2); // Meio
        rotores[2] = new Rotor(r3, p3, ring3); // Direito
        
        // Criar refletor
        String reflectorType = (String) reflectorCombo.getSelectedItem();
        refletor = new Reflector(reflectorType);
        
        // Criar plugboard
        String plugs = plugboardArea.getText().toUpperCase().replaceAll("[^A-Z]", "");
        plugboard = new Plugboard(plugs);
    }
    
    private char validarLetra(String text, String campo) throws Exception {
        if (text.length() != 1 || !Character.isLetter(text.charAt(0))) {
            throw new Exception(campo + " deve ser uma única letra (A-Z)");
        }
        return Character.toUpperCase(text.charAt(0));
    }
    
    private String criptografar(String mensagem) {
        StringBuilder resultado = new StringBuilder();
        
        for (char c : mensagem.toCharArray()) {
            if (Character.isLetter(c)) {
                char cifrado = cifrarLetra(Character.toUpperCase(c));
                resultado.append(cifrado);
            } else {
                resultado.append(c); // Manter espaços e pontuação
            }
        }
        
        return resultado.toString();
    }
    
    private char cifrarLetra(char letra) {
        // Rotacionar antes de cifrar
        rotacionarRotores();
        
        // 1. Plugboard (ida)
        letra = plugboard.forward(letra);
        
        // 2. Rotores (direita para esquerda)
        letra = rotores[2].forward(letra);
        letra = rotores[1].forward(letra);
        letra = rotores[0].forward(letra);
        
        // 3. Refletor
        letra = refletor.reflect(letra);
        
        // 4. Rotores (esquerda para direita)
        letra = rotores[0].backward(letra);
        letra = rotores[1].backward(letra);
        letra = rotores[2].backward(letra);
        
        // 5. Plugboard (volta)
        letra = plugboard.forward(letra);
        
        return letra;
    }
    
    private void rotacionarRotores() {
        boolean rotorDireitoNoEntalhe = rotores[2].estaNoEntalhe();
        boolean rotorMeioNoEntalhe = rotores[1].estaNoEntalhe();
        
        // Double-stepping
        if (rotorMeioNoEntalhe) {
            rotores[1].rotar();
            rotores[0].rotar();
        } else if (rotorDireitoNoEntalhe) {
            rotores[1].rotar();
        }
        
        // Rotor direito sempre rotaciona
        rotores[2].rotar();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new EnigmaMachine().setVisible(true);
        });
    }
}