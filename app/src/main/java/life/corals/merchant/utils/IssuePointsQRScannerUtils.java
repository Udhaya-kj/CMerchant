package life.corals.merchant.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public abstract class IssuePointsQRScannerUtils {


    private static final String TAG = "IssuePointsQRScannerUtils";
    private SurfaceView surfaceView;
    private Context mCtx;

    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;

    private QRHandlerUtils qrCodeParserUtils;

    private final Handler handler = new Handler();
    private Runnable runnable;

    private boolean scanComplete = false;

    private String merchantId;
    private String campaignId;

    private MerchantToast coralsToast;

    int a = 0;

    public IssuePointsQRScannerUtils(SurfaceView surfaceView, Context mCtx) {
        this.surfaceView = surfaceView;
        this.mCtx = mCtx;
        this.merchantId = merchantId;
        this.campaignId = campaignId;
        coralsToast = new MerchantToast(mCtx);
        initScanner();

        runnable = new Runnable() {
            @Override
            public void run() {
                setUpDetector();
            }
        };
    }

    private void initScanner() {

        barcodeDetector = new BarcodeDetector.Builder(mCtx)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();

        cameraSource = new CameraSource.Builder(mCtx, barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true)
                .build();

        qrCodeParserUtils = new QRHandlerUtils() {


            @Override
            public void parsedData(HashMap<String, String> hashMap, String outletId, String authToken, String campaignId) {
                scanComplete = true;
                ((Activity) mCtx).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        parsedScanData(hashMap);
                    }
                });
            }

            @Override
            public void onFailure(final String result) {
                scanComplete = true;
                ((Activity) mCtx).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onFailureScan(result);
                    }
                });

            }
        };

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (barcodeDetector.isOperational()) {
                    startCamera();
                } else {
                    setUpDetector();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                try {
                    final SparseArray<Barcode> barCodes = detections.getDetectedItems();
                    if (barCodes.valueAt(0).displayValue.length() > 0 && !scanComplete) {
                        scanComplete = true;
                        String qrData = barCodes.valueAt(0).displayValue;
                        qrCodeParserUtils.parseMyIssueQR(qrData);
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                    onFailureScan("Invalid QR code - 9");
                }
            }
        });

    }

    public abstract void parsedScanData(HashMap<String, String> stringHashMap);

    public abstract void onFailureScan(String result);

    private void setUpDetector() {
        if (!barcodeDetector.isOperational()) {
            handler.postDelayed(runnable, 100);
        } else {
            handler.removeCallbacks(runnable);
            startCamera();
        }
    }

    private void startCamera() {
        if (ActivityCompat.checkSelfPermission(mCtx, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestCameraPermission();
            return;
        }
        try {
            if (barcodeDetector.isOperational()) {
                cameraSource.start(surfaceView.getHolder());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void requestCameraPermission() {
        Dexter.withActivity(((Activity) mCtx))
                .withPermissions(Manifest.permission.CAMERA)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            startCamera();
                        } else {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown
                            (List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).

                onSameThread()
                .check();

    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", mCtx.getPackageName(), null);
        intent.setData(uri);
        ((Activity) mCtx).startActivityForResult(intent, 101);
    }


    public void cameraSourcePause() {
        if (cameraSource != null) {
            cameraSource.release();
        }
    }
}
