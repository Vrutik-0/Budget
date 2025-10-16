import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

class Transaction 
{
    String category;
    double amount;
    String dates;

    Transaction(String category, double amount, String dates) // Added dates parameter
    {
        this.category = category;
        this.amount = amount;
        this.dates = dates;
    }
}

class Budget
{
    JFrame frame;
    JTextField incomeField, categoryField, amountField, budgetLimitField, datef; // Text fields for user input
    JLabel totalExpensesLabel, remainingBalanceLabel, warningLabel; // Labels to display summary
    DefaultTableModel tableModel; // Table model to display expenses

    double totalIncome = 0.0;
    double totalExpenses = 0.0;
    double budgetLimit = 0.0;

    List<Transaction> transactions = new ArrayList<>(); // List to store transactions
    boolean limitWarning = false;
    boolean excededWarning = false;

    Font font = new Font("Arial", Font.BOLD, 16);

    Budget() // Constructor to create the GUI
    {
        frame = new JFrame("Budget Management System");
        frame.setSize(1440, 825);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel top = new JPanel(); //Creating Top Panel

        //Creating Labels and Text Fields for Income
        JLabel incomeLabel = new JLabel("Set Income: Rs");
        incomeLabel.setFont(font);
        top.add(incomeLabel);
        incomeField = new JTextField(10);
        incomeField.setFont(font);
        top.add(incomeField);
        JButton incomeButton = new JButton("Set Income");
        incomeButton.setFont(font);
        top.add(incomeButton);

        //Creating Labels and Text Fields for Budget Limit
        JLabel budgetLimitLabel = new JLabel("Set Budget Limit: Rs");
        budgetLimitLabel.setFont(font);
        top.add(budgetLimitLabel);
        budgetLimitField = new JTextField(10);
        budgetLimitField.setFont(font);
        top.add(budgetLimitField);
        JButton budgetLimitButton = new JButton("Set Limit");
        budgetLimitButton.setFont(font);
        top.add(budgetLimitButton);
  
        //Creating Table to display Expenses
        tableModel = new DefaultTableModel(new String[]{"Category", "Amount (Rs)","Dates"}, 0);
        JTable expenseTable = new JTable(tableModel);
        expenseTable.setFont(font);
        expenseTable.setRowHeight(25);
        frame.add(new JScrollPane(expenseTable), BorderLayout.CENTER);

        //Creating Labels and Text Fields for Category and Amount
        JPanel bottom = new JPanel();
        JLabel categoryLabel = new JLabel("Category: ");
        categoryLabel.setFont(font);
        bottom.add(categoryLabel);
        categoryField = new JTextField(10);
        categoryField.setFont(font);
        bottom.add(categoryField);
        JLabel amountLabel = new JLabel("Amount Rs: ");
        amountLabel.setFont(font);
        bottom.add(amountLabel);
        amountField = new JTextField(10);
        amountField.setFont(font);
        bottom.add(amountField);
		
		//adding dates
		JLabel date = new JLabel("Dates ");
        date.setFont(font);
        bottom.add(date);
         datef = new JTextField(10);
        datef.setFont(font);
        bottom.add(datef);
		
        JButton expenseButton = new JButton("Add Expense");
        expenseButton.setFont(font);
        bottom.add(expenseButton);

        //Creating Summary Panel
        JPanel summaryPanel = new JPanel();
        totalExpensesLabel = new JLabel("Total Expenses: Rs 0");
        totalExpensesLabel.setFont(font);
        summaryPanel.add(totalExpensesLabel);

        //Creating Labels for Remaining Balance and Budget Warning
        remainingBalanceLabel = new JLabel("Remaining Balance: Rs 0");
        remainingBalanceLabel.setFont(font);
        summaryPanel.add(remainingBalanceLabel);
        warningLabel = new JLabel("");
        warningLabel.setFont(font);
        summaryPanel.add(warningLabel);

        //Adding Panels to Frame
        frame.add(summaryPanel, BorderLayout.SOUTH);
        frame.add(top, BorderLayout.NORTH);
        frame.add(bottom, BorderLayout.SOUTH);

        JButton scoreButton = new JButton("View Financial Health Score");
        scoreButton.setFont(font);
        bottom.add(scoreButton);

        JButton summaryButton = new JButton("View Summary");
        summaryButton.setFont(font);
        bottom.add(summaryButton);

        JButton resetButton = new JButton("Reset Budget");
        resetButton.setFont(font);
        bottom.add(resetButton);

        // Action Listener for Set Income Button
        incomeButton.addActionListener(e -> { 
            try 
            {
                double income = Double.parseDouble(incomeField.getText());
                if (income < 0) 
                {
                    JOptionPane.showMessageDialog(frame, "Income cannot be negative.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                totalIncome = income;
                updateSummary();
            } 
            catch (Exception ex)
             {
                JOptionPane.showMessageDialog(frame, "Enter a valid income.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Action Listener for Set Budget Limit Button
        budgetLimitButton.addActionListener(e -> { 
            try
             {
                double limit = Double.parseDouble(budgetLimitField.getText());
                if (limit < 0)
                 {
                    JOptionPane.showMessageDialog(frame, "Budget limit cannot be negative.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
				if (limit >= (totalExpenses*0.5))
                 {
                    JOptionPane.showMessageDialog(frame, "Setting Limit more than 50% of Income is not advicable", "Error", JOptionPane.WARNING_MESSAGE);
                }
                budgetLimit = limit;
                updateSummary();
            } 
            catch (Exception ex)
             {
                JOptionPane.showMessageDialog(frame, "Enter a valid budget limit.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Action Listener for Add Expense Button
        expenseButton.addActionListener(e -> {
            try 
            {
                String category = categoryField.getText();
                double amount = Double.parseDouble(amountField.getText());
				String dd=datef.getText();

                if (category.isEmpty())
                 {
                    JOptionPane.showMessageDialog(frame, "Enter a category.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (amount < 0) //Check for negative value
                {
                    JOptionPane.showMessageDialog(frame, "Amount cannot be negative.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                transactions.add(new Transaction(category, amount, dd)); // Added dates parameter
                tableModel.addRow(new Object[]{category, amount, dd});
                totalExpenses += amount;
                updateSummary();
				
				//Showing Warning if Expenses exceded Budget Limit
                if (budgetLimit > 0 && totalExpenses >= budgetLimit && !limitWarning)
                 {
                    JOptionPane.showMessageDialog(frame, "Warning: You have reached or exceeded your budget limit!", "Warning", JOptionPane.WARNING_MESSAGE);
                    limitWarning = true;
                 }
				 
				 //Showing Warning if Expenses exceded Income
                if (totalExpenses > totalIncome && !excededWarning)
                 {
                    JOptionPane.showMessageDialog(frame, "Warning: Your expenses have exceeded your income!", "Warning", JOptionPane.WARNING_MESSAGE);
                    excededWarning = true;
                 }

                categoryField.setText("");
                amountField.setText("");
                datef.setText(""); // Clear date field
            } 
            catch (Exception ex) 
            {
                JOptionPane.showMessageDialog(frame, "Enter a valid amount.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Action Listeners for Reset, View Summary, and View Score Buttons.
        resetButton.addActionListener(e -> resetBudget());

        summaryButton.addActionListener(e -> viewSummary());

        scoreButton.addActionListener(e -> {
            int score = calculateHealthScore();
			
            // Determine feedback based on score
            String feedback = (score >= 80) ? "Excellent" :
                    (score >= 50) ? "Good" : "Needs Improvement";

            JOptionPane.showMessageDialog(frame, String.format("Your Financial Health Score: %d\n(%s)", score, feedback), "Financial Health Score", JOptionPane.INFORMATION_MESSAGE);
        });

        // Display the frame after adding all components
        frame.setVisible(true);
    } // End of Constructor

     int calculateHealthScore()
     {
        double savings = totalIncome - totalExpenses;
        double savingsPercentage = (totalIncome > 0) ? (savings / totalIncome) * 100 : 0;

        if (savingsPercentage == 0)
         {
            return 0;// Poor health
        }
        else if (savingsPercentage >= 50)
         {
            return 90 + (int) (savingsPercentage / 2);// Excellent health
        } 
        else if
         (savingsPercentage >= 20) 
         {
            return 60 + (int) (savingsPercentage / 3);// Good health
        } 
        else
         {
            return 40 + (int) (savingsPercentage / 4);// Needs improvement
        }
    }

     void updateSummary()
     {
        totalExpensesLabel.setText("Total Expenses: Rs " + totalExpenses);
        remainingBalanceLabel.setText("Remaining Balance: Rs " + (totalIncome - totalExpenses));

        // Check if expenses exceed budget limit
        if (budgetLimit > 0 && totalExpenses > budgetLimit) 
        {
            warningLabel.setText("Warning: You have exceeded your budget limit!");
        }
         else 
        {
            warningLabel.setText("");
        }
    }

     void resetBudget() // Reset all fields and clear transactions
    {
        totalIncome = 0;
        totalExpenses = 0;
        budgetLimit = 0;
        transactions.clear();// Clear the list of transactions
        incomeField.setText("");
        budgetLimitField.setText("");
        categoryField.setText("");
        amountField.setText("");
        datef.setText(""); // Clear date field
        tableModel.setRowCount(0);
        limitWarning = false;
        excededWarning = false;
        updateSummary();
        JOptionPane.showMessageDialog(frame, "Budget has been reset.", "Reset", JOptionPane.INFORMATION_MESSAGE);
      
        warningLabel.setText("");
    }

     void viewSummary()
     {
        if (transactions.isEmpty())// Check if transactions are recorded
         {
            JOptionPane.showMessageDialog(frame, "No transactions recorded yet.", "Summary", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder summary = new StringBuilder("Expense Summary:\n\n");
       
        for (Transaction t : transactions) // Display each transaction 
        {
            double percentage = (totalIncome > 0) ? (t.amount / totalIncome) * 100 : 0;
            summary.append(String.format("%s: Rs %.2f (%.2f%% of income)\n", t.category, t.amount, percentage));
        }
        
        summary.append(String.format("\nTotal Income: Rs %.2f\nTotal Expenses: Rs %.2f\nRemaining Balance: Rs %.2f", totalIncome, totalExpenses, totalIncome - totalExpenses));
        JOptionPane.showMessageDialog(frame, summary.toString(), "Summary", JOptionPane.INFORMATION_MESSAGE);
    }
    public static void main(String[] Args)
     {
        new Budget();//Creates an object and runs the constructor
    }
}