package com.iservice.agency.ui.main.account.fragment;

import android.content.Intent;
import android.view.View;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.iservice.agency.BR;
import com.iservice.agency.R;
import com.iservice.agency.databinding.AccountFragmentBinding;
import com.iservice.agency.di.component.FragmentComponent;
import com.iservice.agency.ui.base.fragment.BaseFragment;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.iservice.agency.ui.login.LoginActivity;
import com.iservice.agency.ui.main.MainActivity;

public class AccountFragment extends BaseFragment<AccountFragmentBinding,AccountViewModel> implements View.OnClickListener {

    public static final int ACCOUNT_QR_REQUEST=2308;

    private ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
                IntentResult scanResult = IntentIntegrator.parseActivityResult(result.getResultCode(),result.getData());
            if(scanResult.getContents() == null) {
                viewModel.showErrorMessage("Cancelled");
            } else {
                viewModel.showSuccessMessage(scanResult.getContents());
            }
        }
    });

    @Override
    public int getBindingVariable() {
        return BR.mainAccountViewModel;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.account_fragment;
    }

    @Override
    protected void performDataBinding() {
        // do data binding here
        binding.setMainAccountFragment(this);
    }

    @Override
    protected void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.account_qr_button:
                startQRScanner();
                break;
            case R.id.account_logout:
                viewModel.logout();
                Intent intent = new Intent(requireContext(), LoginActivity.class);
                startActivity(intent);
            default:
                break;
        }
    }

    private void startQRScanner() {
        IntentIntegrator integrator = IntentIntegrator.forSupportFragment(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.setPrompt(getString(R.string.account_qr));
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.setBeepEnabled(false);
        integrator.setOrientationLocked(true);
        integrator.setBarcodeImageEnabled(true);
        integrator.setRequestCode(ACCOUNT_QR_REQUEST);
        launcher.launch(integrator.createScanIntent());
    }

}