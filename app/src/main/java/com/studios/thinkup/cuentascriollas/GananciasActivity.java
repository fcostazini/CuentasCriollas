package com.studios.thinkup.cuentascriollas;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class GananciasActivity extends DrawerMain {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


    }

    @Override
    protected void setView() {
        setContentView(R.layout.activity_ganancias);
    }

    protected void updateAPagar() {
        EditText prodValue = (EditText) findViewById(R.id.inp_dolar);
        EditText cotizacion = (EditText) findViewById(R.id.valor_cotizacion);
        CheckBox isFirst = (CheckBox) findViewById(R.id.conyuge_acargo);
        TextView valorPesos = (TextView) findViewById(R.id.val_prod_peso);
        TextView valorImpuestos = (TextView) findViewById(R.id.val_imp_peso);
        TextView valorAPagar = (TextView) findViewById(R.id.val_a_pagar);
        if (prodValue != null && prodValue.getText() != null && !prodValue.getText().toString().equals("")
                && cotizacion != null && cotizacion.getText() != null && !cotizacion.getText().toString().equals("") &&
                isFirst != null && valorPesos != null && valorImpuestos != null && valorAPagar != null) {
            BigDecimal vProdVal = new BigDecimal(prodValue.getText().toString());
            BigDecimal vCotizacion = new BigDecimal(cotizacion.getText().toString());

            BigDecimal vProdPesos = vProdVal.multiply(vCotizacion);
            BigDecimal vImpuesto = new BigDecimal(0);
            DecimalFormat formatter = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.getDefault());
            DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
            symbols.setCurrencySymbol(""); // Don't use null.
            formatter.setDecimalFormatSymbols(symbols);

            if (isFirst.isChecked()) {
                if (vProdVal.compareTo(new BigDecimal(25)) <= 0) {
                    vImpuesto = new BigDecimal(0);
                } else {
                    vImpuesto = vProdVal.subtract(new BigDecimal(25)).multiply(new BigDecimal(0.5)).multiply(vCotizacion);
                }
            } else {
                vImpuesto = vProdVal.multiply(new BigDecimal(0.5)).multiply(vCotizacion);
            }
            BigDecimal vAPagar = vProdPesos.add(vImpuesto);

            valorPesos.setText(formatter.format(vProdPesos.setScale(2, BigDecimal.ROUND_CEILING)));
            valorImpuestos.setText(formatter.format(vImpuesto.setScale(2, BigDecimal.ROUND_CEILING)));
            valorAPagar.setText(formatter.format(vAPagar.setScale(2, BigDecimal.ROUND_CEILING)));
        }
    }

}
