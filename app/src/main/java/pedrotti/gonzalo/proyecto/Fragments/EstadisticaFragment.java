package pedrotti.gonzalo.proyecto.Fragments;


import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import pedrotti.gonzalo.proyecto.Adaptadores.SectionPagerAdapter;
import pedrotti.gonzalo.proyecto.Fragments.ReporteFragments.ActualReporteFragment;
import pedrotti.gonzalo.proyecto.Fragments.ReporteFragments.HistoricoReporteFragment;
import pedrotti.gonzalo.proyecto.Fragments.ReporteFragments.MensualReporteFragment;
import pedrotti.gonzalo.proyecto.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EstadisticaFragment extends Fragment {


    View v;
    ViewPager viewPager;
    TabLayout tableLayout;

    public EstadisticaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_estadistica, container, false);

        viewPager = v.findViewById(R.id.viewPagerReporte);
        tableLayout = v.findViewById(R.id.tabLayout);
//        tableLayout.setTabTextColors(Color.parseColor("#000000"), Color.parseColor("#EB0707"));

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupViewPager(viewPager);
        tableLayout.setupWithViewPager(viewPager);
        tableLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {

        SectionPagerAdapter adapter = new SectionPagerAdapter(getChildFragmentManager());

        adapter.addFragment(new ActualReporteFragment(), "Actual");
        adapter.addFragment(new MensualReporteFragment(), "Mensual");
        adapter.addFragment(new HistoricoReporteFragment(), "Histórico");

        viewPager.setAdapter(adapter);
    }
}
