package com.alink.mysuperapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.alink.mysuperapp.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private View buttonStart;
    View btnStopTimer;
    HomeViewModel homeViewModel;

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("TAG", "*** onReceive ***" );
            long timeRemaining = intent.getLongExtra(superService.EXTRA_TIME_REMAINING, -1);
            binding.textViewTimer.setText(String.valueOf(timeRemaining));
            homeViewModel.setTimeRemaining(timeRemaining);
            Log.d("TAG", "recive: (!)");

        }
    };

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        homeViewModel.getTimeRemaining().observe(getViewLifecycleOwner(), binding.textViewTimer::setText);
        final TextView textView = binding.textviewFirst;
        //homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;


    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("TAG", "*** View created ***" );
        buttonStart = view.findViewById(R.id.button_start);
        binding.buttonStart.setOnClickListener(v -> {
            Log.d("TAG", "btnStart clicked");
            requireContext()
                    .startService(
                            new Intent(getContext(), superService.class)
                                    .setAction(superService.ACTION_START_TIMER)
                    );
        });

        btnStopTimer = view.findViewById(R.id.button_stop);

        binding.buttonStop.setOnClickListener(v ->{
            //Stop the Timer service
            Log.d("TAG", "btnStopTime: ");
            requireContext()
                    .startService(
                            new Intent(getContext(), superService.class)
                                    .setAction(superService.ACTION_STOP_TIMER)
                    );
        });
        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



    @Override
    public void onResume() {
        super.onResume();
        requireContext().registerReceiver(receiver, new IntentFilter(superService.BROADCAST_ACTION_TIMER_CHANGED));
        new IntentFilter(superService.BROADCAST_ACTION_TIMER_CHANGED);

    }

    @Override
    public void onPause() {
        super.onPause();
        requireContext().unregisterReceiver(receiver);
    }
}