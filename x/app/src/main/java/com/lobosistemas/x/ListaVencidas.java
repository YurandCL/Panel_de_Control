package com.lobosistemas.x;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.lobosistemas.x.io.LoboVentasApiAdapter;
import com.lobosistemas.x.io.LoboVentasApiService;
import com.lobosistemas.x.model.Empresa;
import com.lobosistemas.x.ui.adapter.EmpresaAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListaVencidas extends Fragment {

    LoboVentasApiService ApiService; //Para conectar con la API

    ArrayList<Empresa> empresas = new ArrayList<>(); //Para consultar las empresas

    private EmpresaAdapter mAdapter; //Adaptador para las empresas//
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout; //Para refrescar la lista

    ProgressBar progressEmpresas, pbMes;

    EditText txtBuscar;
    ImageView alertVencidas;
    TextView lblConexionVencida;
    public ListaVencidas() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lista_vencidas, container, false);

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        progressEmpresas = view.findViewById(R.id.progressEmpresas);
        lblConexionVencida= view.findViewById(R.id.lblConexionVencida);
        txtBuscar = view.findViewById(R.id.txtBuscar1);

        alertVencidas = view.findViewById(R.id.alertVencidas);

        //-------------------------RecyclerView-------------------------//
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_empresas);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        //---------------------------Adaptador del ReciclerView--------------------------//
        mAdapter= new EmpresaAdapter();
        mRecyclerView.setAdapter(mAdapter);

        //------------------------------------Conexión con la API------------------------------------//
        ApiService = LoboVentasApiAdapter.getApiService();

        //------------FUNCIÓN RETROFIT------------//
        cargarEmpresas();

        //-------------------------Actualizar Datos-------------------------//
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Esto se ejecuta cada vez que se realiza el gesto
                Main2Activity main2Activity = new Main2Activity();
                main2Activity.cargarMetaMensual();

                cargarEmpresas();
                txtBuscar.setText("");
                progressEmpresas.setVisibility(View.INVISIBLE);
                lblConexionVencida.setVisibility(TextView.INVISIBLE);
                alertVencidas.setVisibility(ImageView.INVISIBLE);
            }
        });

        return view;
    }

    //-----------------Búsqueda de Empresas-----------------//
    private void filtro(String text) {
        ArrayList<Empresa> filterList = new ArrayList<>();
        for (Empresa item : empresas){
            if (item.getEmpresa_razonsocial().toLowerCase().contains(text.toLowerCase()) ||
                    item.getFactura_numero().toLowerCase().contains(text.toLowerCase())){
                filterList.add(item);
            }
        }
        clickAdaptador(filterList);
        mAdapter.setmDataSet(filterList); //Agregamos el adaptador al Recycler View//
    }

    //-----------------Mostrar Reportes de la Empresa-----------------//
    private void clickAdaptador(final ArrayList<Empresa> empresas) {

        final EmpresaAdapter mAdapter= new EmpresaAdapter(empresas);

        mAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Reporte.class);
                intent.putExtra("empresa_razonsocial", empresas.get(mRecyclerView.getChildAdapterPosition(v)).getEmpresa_razonsocial());
                intent.putExtra("factura_num",empresas.get(mRecyclerView.getChildAdapterPosition(v)).getFactura_numero());
                startActivity(intent);
            }
        });

        mRecyclerView.setAdapter(mAdapter);
    }

    //------------------------------------RetroFit Empresa--------------------------------------//
    public void cargarEmpresas(){
        Call<ArrayList<Empresa>> call = ApiService.getEmpresas("0","0");
        call.enqueue(new Callback<ArrayList<Empresa>>() {
            @Override
            public void onResponse(Call<ArrayList<Empresa>> call, Response<ArrayList<Empresa>> response) {
                if(response.isSuccessful()){
                    empresas = response.body();
                    Log.d("onResponse empresas","Se cargaron "+empresas.size()+" empresas.");
                    mAdapter.setmDataSet(empresas); //Agregamos el adaptador al Recycler View//

                    clickAdaptador(empresas);
                    swipeRefreshLayout.setRefreshing(false);
                    progressEmpresas.setVisibility(View.INVISIBLE);
                    lblConexionVencida.setVisibility(View.INVISIBLE);
                    alertVencidas.setVisibility(ImageView.INVISIBLE);

                    TextWatcher myTextWatcher = new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            txtBuscar.setTextColor(Color.parseColor("#00417d"));
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            filtro(s.toString());

                        }
                    };
                    filtro(""+txtBuscar.getText());
                    txtBuscar.addTextChangedListener(myTextWatcher);
                    Log.d("Vencidas","Se cargaron las empresas");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Empresa>> call, Throwable t) {
                lblConexionVencida.setVisibility(TextView.VISIBLE);
                alertVencidas.setVisibility(ImageView.VISIBLE);
                progressEmpresas.setVisibility(View.INVISIBLE);
                swipeRefreshLayout.setRefreshing(false);
                Log.d("onResponse empresas","Algo salió mal: "+t.getMessage());
            }
        });
    }
}
