package com.example.calcularimc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class MainActivity extends AppCompatActivity {

    private AdView mAdView;
    private TextView txtImc;
    private TextView txtTmb;
    private TextView txtQtdAgua;
    private EditText editPeso;
    private EditText editAltura;
    private EditText editIdade;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdImpression() {
                // Code to be executed when an impression is recorded
                // for an ad.
            }

            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }
        });

        txtImc = findViewById(R.id.txtImc);
        txtTmb = findViewById(R.id.txtTmb);
        txtQtdAgua = findViewById(R.id.txtQtdAgua);
        editPeso = findViewById(R.id.editPeso);
        editAltura = findViewById(R.id.editAltura);
        editIdade = findViewById(R.id.editIdade);

    }

    public void Calcular(View v){
        double peso = Double.parseDouble(editPeso.getText().toString());
        double altura = Double.parseDouble(editAltura.getText().toString());
        double imc = ((peso / altura) / altura) * 10000;
        int idade = Integer.parseInt(editIdade.getText().toString());
        double tmb;
        Double qtdAgua = getQtdAguaDia();
        String sexo = getSexoSelecionado();


        txtQtdAgua.setText("Recomendavel o consumo minimo diário de " + qtdAgua + "L de água!");
        // Cálculo da TMB baseado no sexo selecionado pelo usuário
       if (sexo.equals("masculino")) {
            //tmb = 66 + (13.8 * peso) + (5 * altura) - (6.8 * idade);
            txtTmb.setText("TMB: " + String.format("%.2f",66 + (13.8 * peso) + (5 * altura) - (6.8 * idade)) + " kcal") ;
        } else if (sexo.equals("feminino")) {
            tmb = 655 + (9.6 * peso) + (1.8 * altura) - (4.7 * idade);
            txtTmb.setText("TMB: " + String.format("%.2f",tmb) + " kcal");
        } else {
            // Tratamento para o caso em que o usuário não selecionou o sexo
            txtTmb.setText("TMB: Selecione o sexo!");
            return;
        }


        if (imc < 19){
            txtImc.setText("IMC: " + String.format("%.2f", imc) + " - Abaixo do peso!");
        } else if (imc >= 19 && imc < 25) {
            txtImc.setText("IMC: " + String.format("%.2f", imc) + " - Peso normal!");
        } else if (imc >= 25 || imc < 30 ) {
            txtImc.setText("IMC: " + String.format("%.2f", imc) + " - Sobrepeso!");
        } else if (imc >= 30 || imc < 35 ) {
            txtImc.setText("IMC: " + String.format("%.2f", imc) + " - Obesidade Tipo I! Risco de Saúde");
        } else if (imc >= 35 || imc < 40 ) {
            txtImc.setText("IMC: " + String.format("%.2f", imc) + " - Obesidade Tipo II! Alto risco de Saúde");
        }else if (imc >= 40 ) {
            txtImc.setText("IMC: " + String.format("%.2f", imc) + " - Obesidade Mórbida! Altissimo risco de Saúde");
        }


    }

    public String getSexoSelecionado() {
        RadioGroup radioGroupSexo = findViewById(R.id.radio_group_sexo);

        if (radioGroupSexo.getCheckedRadioButtonId() == R.id.radioM) {
            return "masculino";
        } else if (radioGroupSexo.getCheckedRadioButtonId() == R.id.radioF) {
            return "feminino";
        } else {
            return "";
        }
    }

    public Double getQtdAguaDia(){
        EditText editPeso1 = editPeso;
        double qtdAgua = (Double.parseDouble(editPeso1.getText().toString()) * 35) / 1000;

        return qtdAgua;
    }
}