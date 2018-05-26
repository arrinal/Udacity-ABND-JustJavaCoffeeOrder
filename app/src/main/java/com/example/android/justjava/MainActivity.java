package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    int qtyCoffee = 0;

    public void addWhippedCream(View v) {
    }

    /**
     * Increase the quantity of item (increment)
     */
    public void increment(View view) {
        if (qtyCoffee == 100) {
            Toast.makeText(this, getString(R.string.toast_increment), Toast.LENGTH_SHORT).show();
            return;
        }

        qtyCoffee = qtyCoffee + 1;
        display(qtyCoffee);
    }

    /**
     * Decrease the quantity of item (decrement)
     */
    public void decrement(View view) {
        if (qtyCoffee <= 1) {
            Toast.makeText(this, getString(R.string.toast_decrement), Toast.LENGTH_SHORT).show();
            return;
        }

        qtyCoffee = qtyCoffee - 1;
        display(qtyCoffee);
    }

    /**
     * Submit the order
     */
    public void masuk(View v) {

        /**
         * Used for customer name
         */
        EditText nameField = findViewById(R.id.name_field);
        String name = "" + nameField.getText().toString();

        /**
         * Check the Whipped Cream checkbox is checked or not (true or false)
         */
        CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        /**
         * Check the Chocolate checkbox is checked or not (true or false)
         */
        CheckBox chocolateCheckBox = findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        /**
         * Add total price of calculatePrice(); to price variable
         */
        int price = calculatePrice(hasWhippedCream, hasChocolate);

        /**
         * Add order summary of createOrderSummary(); to priceMessage variable
         */
        String priceMessage = "" + createOrderSummary(name, price, hasWhippedCream, hasChocolate);

        /**
         * @string subject used to create subject for email
         * @string content variable use priceMessage variable to interpret Order Summary displayed in email body
         * composeEmail(); to save the order summary to email
         */
        String subject = getString(R.string.email_subject) + name;
        String content = priceMessage;
        composeEmail(subject, content);
    }


    /**
     * Used for calculate the price
     *
     * @param addWhippedCream is Whipped Cream added or not
     * @param addChocolate    is Chocolate added or not
     * @return display total price
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int basePrice = 5;

        if (addWhippedCream) {
            basePrice = basePrice + 1;
        }

        if (addChocolate) {
            basePrice = basePrice + 2;
        }
        return basePrice * qtyCoffee;
    }

    /**
     * Used for create summary for order
     *
     * @param name            interpret the name of Customer
     * @param price           interpret the price of ordered item
     * @param addWhippedCream interpret whether there's Whipped Cream or not
     * @param addChocolate    interpret whether there's Chocolate or not
     * @return interpret order summary
     */
    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate) {
        String local_pesan = getString(R.string.cust_name) + name;
        local_pesan += getString(R.string.quantity) + qtyCoffee;
        local_pesan += getString(R.string.cust_chooseWhippedCream) + addWhippedCream;
        local_pesan += getString(R.string.cust_chooseChocolate) + addChocolate;
        local_pesan += getString(R.string.cust_total) + price + getString(R.string.currency);
        local_pesan += getString(R.string.thank_you);
        return local_pesan;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int numberOfCoffees) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }

    /**
     * Used for copy order to email
     *
     * @param subject used for subject email
     * @param content used for body email
     */
    public void composeEmail(String subject, String content) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, content);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


}