package com.example.rubilnik.screens;

import com.example.rubilnik.screens.MainFragment;
import com.example.rubilnik.MainActivity;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.rubilnik.R;
import com.google.zxing.Result;

import java.util.Objects;


public class ScannerQRFragment extends Fragment  {
    private CodeScanner mCodeScanner;
    public static String code = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final Activity activity = requireActivity();
        View rootView = inflater.inflate(R.layout.scanner_qr_fragment, container, false);
        CodeScannerView scannerView = rootView.findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(activity, scannerView);
        code = "";

        mCodeScanner.setAutoFocusEnabled(true);
        mCodeScanner.setFlashEnabled(false);

        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        code = result.getText();
                        activity.onBackPressed();
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    public void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }


//    private void permission() {
//        int permission = requireContext().checkSelfPermission(Manifest.permission.CAMERA);
//        if (permission!= PackageManager.PERMISSION_GRANTED) {
//            requestPermissions(new String[]{Manifest.permission.CAMERA}, 0);
//        }
//    }
}