package com.example.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        EditText text = (EditText) findViewById(R.id.PersonName);
        String name = text.getText().toString();

        CheckBox add_chocolate = (CheckBox) findViewById(R.id.add_chocolate_checkbox);
        boolean Has_chocolate = add_chocolate.isChecked();

        CheckBox Whipped_cream = (CheckBox) findViewById(R.id.Whipped_cream_checkbox);
        boolean Has_whipped_cream = Whipped_cream.isChecked();

        int price = calculatePrice(Has_whipped_cream,Has_chocolate);
        String priceMessage = createOrderSummary(price,Has_whipped_cream,Has_chocolate,name);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Order from " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);}

//        displayMessage(priceMessage);

    }

    /**
     * Calculates the price of the order.
     *
     * @param has_whipped_cream
     * @param has_chocolate
     */
    private int calculatePrice(boolean has_whipped_cream, boolean has_chocolate) {

        int basePrice= 5;
        if (has_whipped_cream){
            basePrice = basePrice +1;
        }
        if (has_chocolate){
            basePrice = basePrice +2;
        }
        int price = quantity * basePrice;
        return price;
    }

    private String createOrderSummary(int price, boolean whipped_cream, boolean has_chocolate, String name){
        String priceMessage = "Name: " + name;
        priceMessage = priceMessage + "\nadd whipped cream? " + whipped_cream;
        priceMessage = priceMessage + "\nadd chocolate? " + has_chocolate;
        priceMessage = priceMessage + "\nquantity: "+ quantity;
        priceMessage = priceMessage+ "\nPrice $" + (price);
        priceMessage = priceMessage+ "\nthankyou";
        return priceMessage;



    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.order_summary_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    public void increment(View view) {
        if (quantity==100){
            Toast.makeText(this,"you cannot order more than 100 coffee",Toast.LENGTH_SHORT).show();
            return;
        }

        quantity = quantity + 1;
        displayQuantity(quantity);

    }

    public void decrement(View view) {
        if (quantity==1){
            Toast.makeText(this,"you cannot order less than 1 coffee",Toast.LENGTH_SHORT).show();
            return;
        }

        quantity = quantity - 1;
        displayQuantity(quantity);
    }
}