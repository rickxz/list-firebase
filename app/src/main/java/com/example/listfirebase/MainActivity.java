package com.example.listfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference DB = FirebaseDatabase.getInstance().getReference();
    private TextView txtNome;
    private TextView txtEmail;
    private Button btnAdicionar;
    private ListView listaPessoas;
    private ContatosAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseReference cont26 = DB.child("cont26listacontatos");
        txtNome = findViewById(R.id.txtNome);
        txtEmail = findViewById(R.id.txtEmail);
        btnAdicionar = findViewById(R.id.btnAdicionar);
        listaPessoas = findViewById(R.id.listaPessoas);
        btnAdicionar.setOnClickListener(new btnAdicionarListener());

        FirebaseListOptions<Contato> options = new FirebaseListOptions.Builder<Contato>().setLayout(R.layout.item_lista).setQuery(cont26, Contato.class).setLifecycleOwner(this).build();
        adapter = new ContatosAdapter(options);
        listaPessoas.setAdapter(adapter);
        listaPessoas.setOnItemClickListener(new ListClickListener());
}

    private class ContatosAdapter extends FirebaseListAdapter<Contato> {
        public ContatosAdapter(FirebaseListOptions options) {
            super(options);
        }

        @Override
        protected void populateView(View v, Contato c, int position) {
            TextView lblNome = v.findViewById(R.id.lblNome);
            TextView lblEmail = v.findViewById(R.id.lblEmail);
            lblNome.setText(c.getNome());
            lblEmail.setText(c.getEmail());
        }
    }

    private class btnAdicionarListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String nome = txtNome.getText().toString();
            String email = txtEmail.getText().toString();
            DatabaseReference cont26 = DB.child("cont26listacontatos");
            Contato contato = new Contato(nome, email);
            String chave = cont26.push().getKey();
            cont26.child(chave).setValue(contato);
            txtNome.setText("");
            txtEmail.setText("");
            txtNome.requestFocus();
        }
    }

    private class ListClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Contato c = adapter.getItem(i);
            Toast.makeText(MainActivity.this, "Nome: " + c.getNome() + "\nEmail: " + c.getEmail(), Toast.LENGTH_SHORT).show();
        }
    }
}