import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import javax.swing.*;

class Luggage {
    String ownerName;
    String phoneNumber;
    LocalDateTime checkInTime;

    public Luggage(String ownerName, String phoneNumber, LocalDateTime checkInTime) {
        this.ownerName = ownerName;
        this.phoneNumber = phoneNumber;
        this.checkInTime = checkInTime;
    }
}

public class AdvancedClockRoomAppWithCreativeButtons extends JFrame implements ActionListener {
    HashMap<Integer, Luggage> storage = new HashMap<>();
    int currentToken = 1;
    final double hourlyRate = 10.0;
    int totalBags = 0;
    double chargesDue = 0.0; // Track the amount to be paid

    // GUI components
    JTextField nameField, phoneField, tokenField;
    JButton checkInButton, checkOutButton, clearButton, exitButton, printReceiptButton, searchButton, paymentButton;
    JLabel displayLabel, bagCountLabel, storedTimeLabel, imageLabel;
    JCheckBox darkModeToggle;
    Font customFont = new Font("Serif", Font.BOLD, 18); // Preferred font

    public AdvancedClockRoomAppWithCreativeButtons() {
        // Frame settings
        setTitle("Railway Station Clock Room - Advanced");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        // Initialize GridBagConstraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Add an image related to luggage or railway station
        ImageIcon imageIcon = new ImageIcon("luggage_image.png"); // Add your image path here
        imageLabel = new JLabel(imageIcon);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(imageLabel, gbc);

        // Panel for input fields and buttons
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Luggage Information"));
        inputPanel.setBackground(new Color(224, 255, 255));  // Light blue background

        // Owner name label and text field
        JLabel nameLabel = new JLabel("Enter Name:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        inputPanel.add(nameLabel, gbc);

        nameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        inputPanel.add(nameField, gbc);

        // Phone number label and text field
        JLabel phoneLabel = new JLabel("Enter Phone Number:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(phoneLabel, gbc);

        phoneField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 2;
        inputPanel.add(phoneField, gbc);

        // Token label and text field
        JLabel tokenLabel = new JLabel("Enter Token Number:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(tokenLabel, gbc);

        tokenField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 3;
        inputPanel.add(tokenField, gbc);

        // Bag count label (with custom font)
        bagCountLabel = new JLabel("Bags Stored: 0");
        bagCountLabel.setFont(customFont);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        inputPanel.add(bagCountLabel, gbc);

        // Total stored time label
        storedTimeLabel = new JLabel("Total Storage Time: 0 hours");
        storedTimeLabel.setFont(customFont);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        inputPanel.add(storedTimeLabel, gbc);

        // Add the panel to the main layout
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        add(inputPanel, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new GridLayout(2, 5, 10, 10));

        // Check In button
        checkInButton = createStyledButton("Check In", new Color(100, 149, 237), Color.black);
        buttonPanel.add(checkInButton);

        // Check Out button
        checkOutButton = createStyledButton("Check Out", new Color(100, 149, 237), Color.black);
        buttonPanel.add(checkOutButton);

        // Print Receipt button
        printReceiptButton = createStyledButton("Print Receipt", new Color(100, 149, 237), Color.black);
        buttonPanel.add(printReceiptButton);

        // Payment button
        paymentButton = createStyledButton("Make Payment", new Color(100, 149, 237), Color.black);
        paymentButton.setVisible(false); // Initially hidden until payment is required
        buttonPanel.add(paymentButton);

        // Clear button
        clearButton = createStyledButton("Clear", new Color(100, 149, 237), Color.BLACK);
        buttonPanel.add(clearButton);

        // Search by token number
        searchButton = createStyledButton("Search Luggage", new Color(100, 149, 237), Color.BLACK);
        buttonPanel.add(searchButton);

        // Dark mode toggle
        darkModeToggle = new JCheckBox("Dark Mode");
        darkModeToggle.setBackground(new Color(100, 149, 237));
        buttonPanel.add(darkModeToggle);

        // Exit button
        exitButton = createStyledButton("Exit", new Color(100, 149, 237), Color.black);
        buttonPanel.add(exitButton);

        // Add button panel to the main layout
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(buttonPanel, gbc);

        // Label to display results
        displayLabel = new JLabel("");
        displayLabel.setFont(new Font("Monospaced", Font.PLAIN, 14));
        displayLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        displayLabel.setPreferredSize(new Dimension(600, 200));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(displayLabel, gbc);

        // Action listeners
        checkInButton.addActionListener(this);
        checkOutButton.addActionListener(this);
        printReceiptButton.addActionListener(this);
        paymentButton.addActionListener(this);
        clearButton.addActionListener(this);
        searchButton.addActionListener(this);
        exitButton.addActionListener(this);
        darkModeToggle.addActionListener(this);
    }

    // Create a styled button
    private JButton createStyledButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFont(new Font("Arial", Font.BOLD, 16));  // Bold font
        button.setBorder(BorderFactory.createRaisedBevelBorder()); // Raised bevel border
        return button;
    }

    // Method to handle check-in of luggage
    private void checkInLuggage() {
        String ownerName = nameField.getText().trim();
        String phoneNumber = phoneField.getText().trim();
        if (ownerName.isEmpty() || phoneNumber.isEmpty()) {
            displayLabel.setText("Please enter valid name and phone number.");
            return;
        }

        LocalDateTime now = LocalDateTime.now();
        Luggage luggage = new Luggage(ownerName, phoneNumber, now);
        storage.put(currentToken, luggage);
        totalBags++;
        updateBagCount();

        // Display detailed check-in information
        String checkInDetails = String.format("<html><strong>Luggage checked in!<br>Owner: %s<br>Phone: %s<br>Check-In Time: %s<br>Token Number: %d</strong></html>",
                luggage.ownerName, luggage.phoneNumber, luggage.checkInTime, currentToken);
        displayLabel.setText(checkInDetails);
        currentToken++;
        nameField.setText("");
        phoneField.setText("");
    }

    // Method to handle check-out of luggage
    private void checkOutLuggage() {
        String tokenInput = tokenField.getText().trim();
        if (tokenInput.isEmpty()) {
            displayLabel.setText("Please enter a token number.");
            return;
        }

        try {
            int tokenNumber = Integer.parseInt(tokenInput);
            if (storage.containsKey(tokenNumber)) {
                Luggage luggage = storage.get(tokenNumber);
                LocalDateTime checkOutTime = LocalDateTime.now();
                long minutes = ChronoUnit.MINUTES.between(luggage.checkInTime, checkOutTime);

                // Ensure a minimum charge of 1 hour
                long hours = (minutes < 60) ? 1 : (minutes / 60);

                // Calculate the charges based on the hours
                chargesDue = hours * hourlyRate;

                // Display checkout information, including charges and storage time
                displayLabel.setText(String.format("<html><strong>Luggage belongs to: %s<br>Phone: %s<br>Total time stored: %d hour(s)<br>Charges: %.2f currency units</strong></html>",
                        luggage.ownerName, luggage.phoneNumber, hours, chargesDue));

                // Remove the luggage from the storage and update the total bag count
                storage.remove(tokenNumber);
                totalBags--;
                updateBagCount();

                // Clear the token field after successful checkout
                tokenField.setText("");
                paymentButton.setVisible(true); // Show payment button after checkout
            } else {
                displayLabel.setText("Invalid token number!");
            }
        } catch (NumberFormatException e) {
            displayLabel.setText("Please enter a valid token number.");
        }
    }

    // Update the bag count display
    private void updateBagCount() {
        bagCountLabel.setText("Bags Stored: " + totalBags);
    }

    // Method to handle payment
    private void handlePayment() {
        String paymentMethod = (String) JOptionPane.showInputDialog(this, "Choose Payment Method", "Payment",
                JOptionPane.PLAIN_MESSAGE, null, new String[]{"QR Code", "UPI ID"}, "QR Code");

        if (paymentMethod != null) {
            String paymentDetails = "";
            if (paymentMethod.equals("QR Code")) {
                paymentDetails = "Scan this QR Code: [QR Code Placeholder]"; // Placeholder for QR Code
            } else if (paymentMethod.equals("UPI ID")) {
                paymentDetails = JOptionPane.showInputDialog(this, "Enter your UPI ID:");
                if (paymentDetails != null && !paymentDetails.trim().isEmpty()) {
                    // Simulate payment processing
                    JOptionPane.showMessageDialog(this, "Payment of " + chargesDue + " currency units made successfully using UPI ID: " + paymentDetails);
                }
            }
            // Simulate QR Code payment
            JOptionPane.showMessageDialog(this, paymentDetails);
        }
        paymentButton.setVisible(false); // Hide payment button after processing
    }

    // Clear fields and display
    private void clearFields() {
        nameField.setText("");
        phoneField.setText("");
        tokenField.setText("");
        displayLabel.setText("");
        paymentButton.setVisible(false); // Hide payment button
    }

    // Method to toggle dark mode
    private void toggleDarkMode() {
        if (darkModeToggle.isSelected()) {
            getContentPane().setBackground(Color.DARK_GRAY);
            bagCountLabel.setForeground(Color.WHITE);
            storedTimeLabel.setForeground(Color.WHITE);
        } else {
            getContentPane().setBackground(Color.LIGHT_GRAY);
            bagCountLabel.setForeground(Color.BLACK);
            storedTimeLabel.setForeground(Color.BLACK);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == checkInButton) {
            checkInLuggage();
        } else if (e.getSource() == checkOutButton) {
            checkOutLuggage();
        } else if (e.getSource() == printReceiptButton) {
            JOptionPane.showMessageDialog(this, "Receipt Printed!\n" + displayLabel.getText(),
                    "Receipt", JOptionPane.INFORMATION_MESSAGE);
        } else if (e.getSource() == paymentButton) {
            handlePayment();
        } else if (e.getSource() == clearButton) {
            clearFields();
        } else if (e.getSource() == searchButton) {
            // Implement search functionality here (optional)
        } else if (e.getSource() == exitButton) {
            System.exit(0);
        } else if (e.getSource() == darkModeToggle) {
            toggleDarkMode();
        }
    }

    public static void main(String[] args) {
        // Set the look and feel to match the operating system UI
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Launch the application
        SwingUtilities.invokeLater(() -> {
            AdvancedClockRoomAppWithCreativeButtons app = new AdvancedClockRoomAppWithCreativeButtons();
            app.getContentPane().setBackground(Color.LIGHT_GRAY); // Set initial background color
            app.setVisible(true);
        });
    }
}
