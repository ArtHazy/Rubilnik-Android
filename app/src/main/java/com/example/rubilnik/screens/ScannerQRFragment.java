package com.example.rubilnik.screens;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.budiyev.android.codescanner.AutoFocusMode;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.ScanMode;
import com.example.rubilnik.R;


public class ScannerQRFragment extends Fragment  {
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
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

        setupPermissions();

        mCodeScanner.setAutoFocusEnabled(true);
        mCodeScanner.setAutoFocusMode(AutoFocusMode.CONTINUOUS); // CONTINUOUS - постоянно, SAFE - через время
        mCodeScanner.setScanMode(ScanMode.CONTINUOUS); // поиск кода

        mCodeScanner.setFlashEnabled(false);

        mCodeScanner.setDecodeCallback(result -> activity.runOnUiThread(() -> {
            code = result.getText();
            activity.onBackPressed();
        }));

        scannerView.setOnClickListener(view -> mCodeScanner.startPreview());

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

    private void setupPermissions() {
        int permission = requireContext().checkSelfPermission(Manifest.permission.CAMERA);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest();
        }
    }

    private void makeRequest() {
        ActivityCompat.requestPermissions(requireActivity(),
                new String[]{Manifest.permission.CAMERA},
                CAMERA_PERMISSION_REQUEST_CODE);
    }

    // Обработка результата запроса разрешения
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Разрешение на использование камеры предоставлено
            } else {
                // Разрешение на использование камеры не предоставлено, выведите ошибку
                Toast.makeText(requireActivity(), "Чтобы использовать камеру, разрешите использовать камеру", Toast.LENGTH_SHORT).show();
            }
        }
    }
}